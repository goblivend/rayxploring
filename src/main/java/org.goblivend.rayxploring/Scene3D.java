package org.goblivend.rayxploring;

import org.goblivend.rayxploring.Utils.Vector3D;
import org.goblivend.rayxploring.components.Camera;
import org.goblivend.rayxploring.components.Component;
import org.goblivend.rayxploring.components.Light;
import org.goblivend.rayxploring.components.Ray;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class Scene3D implements Scene {
    private final List<Component<Vector3D>> components;
    private final List<Light<Vector3D>> lights;
    private final List<Camera<Vector3D>> cameras;

    protected Scene3D(List<Component<Vector3D>> components, List<Light<Vector3D>> lights, List<Camera<Vector3D>> cameras) {
        this.components = components;
        this.lights = lights;
        this.cameras = cameras;
    }

    // TODO : Add reflection of rays on surfaces
    public List<BufferedImage> render() {
        Stream<Ray<Vector3D>> rays = lights.stream().flatMap(Light::emit);

        rays.forEach(ray -> {
            Optional<Component<Vector3D>> hit;
            Function<Ray<Vector3D>, Predicate<Component<Vector3D>>> filter = r -> (o -> {
                Double t = o.intercept(r);
                return t != null && t > 1E-5;
            });
            Function<Ray<Vector3D>, Function<Component<Vector3D>, Double>> intercept = r -> (o -> o.intercept(r));

            do {
                hit = components.stream()
                        .filter(filter.apply(ray))
                        .min(comparing(intercept.apply(ray)));

                if (hit.isPresent())
                    ray = hit.get().rebound(ray);

            } while (hit.isPresent() && !(hit.get() instanceof Camera));
        });

        System.out.println("Finished rays, rendering image");

        List<BufferedImage> list = new ArrayList<>();
        for (Camera<Vector3D> cam : cameras) {
            list.add(cam.render());
        }
        return list;
    }

    public static class Scene3DBuilder {
        private List<Component<Vector3D>> components;
        private List<Light<Vector3D>> lights;
        private List<Camera<Vector3D>> cameras;

        public Scene3DBuilder() {

        }

        public static Scene3DBuilder of() {
            return new Scene3DBuilder();
        }

        @SafeVarargs
        public final Scene3DBuilder withComponents(Component<Vector3D>... components) {
            this.components = Arrays.stream(components).toList();
            return this;
        }

        @SafeVarargs
        public final Scene3DBuilder withLights(Light<Vector3D>... lights) {
            this.lights = Arrays.stream(lights).toList();
            return this;
        }

        @SafeVarargs
        public final Scene3DBuilder withCameras(Camera<Vector3D>... cameras) {
            this.cameras = Arrays.stream(cameras).toList();
            return this;
        }

        public Scene3D build() {
            return new Scene3D(components, lights, cameras);
        }
    }
}
