package org.goblivend.rayxploring;

import org.goblivend.rayxploring.Utils.Vector3D;
import org.goblivend.rayxploring.components.*;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class Scene3D implements Scene {
    private final List<Component<Vector3D>> components;
    private final List<Light<Vector3D>> lights;

    protected Scene3D(List<Component<Vector3D>> components, List<Light<Vector3D>> lights) {
        this.components = components;
        this.lights = lights;
    }

    // TODO : Add reflection of rays on surfaces
    public List<BufferedImage> render() {
        Stream<Ray<Vector3D>> rays = lights.stream().flatMap(Light::emit);

        rays.forEach(ray -> {
            Optional<Component<Vector3D>> hit = components.stream()
                    .min(comparing(o -> o.intercept(ray)));

            if (hit.isPresent() && hit.get() instanceof Camera3D c) {
                c.record(ray);
            }
        });

        List<BufferedImage> list = new ArrayList<>();
        for (Component<Vector3D> c : components) {
            if (c instanceof Camera3D cam)
                list.add(cam.render());
        }
        return list;
    }

    public static class Scene3DBuilder {
        private List<Component<Vector3D>> components;
        private List<Light<Vector3D>> lights;

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

        public Scene3D build() {
            return new Scene3D(components, lights);
        }
    }
}
