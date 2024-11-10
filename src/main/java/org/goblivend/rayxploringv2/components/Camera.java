package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public abstract class Camera<V extends Vector<V>> {
    protected final int width;
    protected final int height;

    protected Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void trace(Ray<V> ray, Optional<V> limit);

    public abstract Stream<Ray<V>> generateRays();

    public BufferedImage render(List<Component<V>> comps) {
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_DEFAULT);

        generateRays().forEach(ray -> {

            Optional<Component<V>> hit;
            Function<Ray<V>, Function<Component<V>, Tuple<Component<V>, Double>>> intercept = r -> (o -> new Tuple<>(o, o.intercept(r)));

            Predicate<Tuple<Component<V>, Double>> filter = t -> t.t2() != null && t.t2() > 1E-5;

            do {
                hit = comps.stream()
                        .map(intercept.apply(ray))
                        .filter(filter)
                        .min(comparing(Tuple::t2))
                        .map(Tuple::t1);

                if (hit.isPresent())
                    ray = hit.get().rebound(ray);

            } while (hit.isPresent());

            image.setRGB((int) ray.imgPos().x(), (int) ray.imgPos().y(), ray.color().getRGB());
        });
        return image;
    }
}
