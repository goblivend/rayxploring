package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class Plane implements Component<Vector3D> {
    protected final Color color;
    protected final double reflectivity;
    protected final Vector3D pos;
    protected final Vector3D dir;
    protected final Tuple<Vector3D, Vector3D> base;

    public Plane(Color color, double reflectivity, Vector3D pos, Vector3D dir) {
        assert 0 <= reflectivity && reflectivity <= 1;
        this.color = color;
        this.reflectivity = reflectivity;
        this.pos = pos;
        this.dir = dir;
        this.base = orthonormalBase(dir);
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return intersection(base, pos, ray).t1();
    }

    @Override
    public Tuple<Stream<Ray<Vector3D>>, Function<Stream<Color>, Color>> rebound(Ray<Vector3D> ray) {
        return new Tuple<>(

                Stream.of(
                        new Ray<>(
                                ray.imgPos(),
                                ray.pos().translate(ray.dir(), intersection(base, pos, ray).t1()),
                                reboundPlane(dir, base, ray.dir()),
                                ray.color()
                        )),
                        colors -> colors.map(c -> reflectColor(c, color, reflectivity))
                                .findFirst()
                                .orElseThrow()

        );
    }
}
