package org.goblivend.rayxploring;

import org.goblivend.rayxploring.components.Component;
import org.goblivend.rayxploring.components.Camera;
import org.goblivend.rayxploring.components.Light;
import org.goblivend.rayxploring.Utils.Vector2D;

import java.awt.image.BufferedImage;
import java.util.*;

public class Scene2D implements Scene {
    private final List<Component<Vector2D>> components;
    private final List<Camera<Vector2D>> cameras;
    private final List<Light<Vector2D>> lights;


    protected Scene2D(List<Component<Vector2D>> components, List<Camera<Vector2D>> cameras, List<Light<Vector2D>> lights) {
        this.components = components;
        this.cameras = cameras;
        this.lights = lights;
    }

    public List<BufferedImage> render() {
        lights.stream().flatMap(Light::emit).forEach(ray -> {
            Optional<Double> firstHit = components.stream()
                    .map(c -> c.intercept(ray))
                    .filter(Objects::nonNull)
                    .sorted()
                    .findFirst();

            for (var cam: cameras) {
                cam.trace(ray, firstHit.map(t -> ray.at(t).pos()));
            }

            if (firstHit.isEmpty()) {
                return;
            }

            for (var cam : cameras) {
                cam.record(ray.at(firstHit.get()));
            }
        });
        return cameras.stream().map(Camera::render).toList();
    }

    public static class Scene2DBuilder {
        private List<Component<Vector2D>> components = new ArrayList<>();
        private List<Camera<Vector2D>> cameras = new ArrayList<>();
        private List<Light<Vector2D>> lights = new ArrayList<>();
        public Scene2DBuilder() {
        }

        public static Scene2DBuilder of() {
            return new Scene2DBuilder();
        }

        @SafeVarargs
        public final Scene2DBuilder withComponents(Component<Vector2D>... components) {
            this.components = Arrays.stream(components).toList();
            return this;
        }

        @SafeVarargs
        public final Scene2DBuilder withCameras(Camera<Vector2D>... cameras) {
            this.cameras = Arrays.stream(cameras).toList();
            return this;
        }

        @SafeVarargs
        public final Scene2DBuilder withLights(Light<Vector2D>... lights) {
            this.lights = Arrays.stream(lights).toList();
            return this;
        }

        public Scene2D build() {
            return new Scene2D(components, cameras, lights);
        }
    }

}
