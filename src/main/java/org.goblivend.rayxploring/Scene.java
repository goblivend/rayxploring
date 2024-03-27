package org.goblivend.rayxploring;

import org.goblivend.rayxploring.Utils.Point;
import org.goblivend.rayxploring.components.Component;
import org.goblivend.rayxploring.components.Camera;
import org.goblivend.rayxploring.components.Light;
import org.goblivend.rayxploring.Utils.Point2D;
import org.goblivend.rayxploring.Utils.Point3D;
import org.goblivend.rayxploring.components.Ray;

import java.awt.image.BufferedImage;
import java.util.*;

public class Scene<P extends Point<P>> {
    private final List<Component<P>> components;
    private final List<Camera<P>> cameras;
    private final List<Light<P>> lights;


    protected Scene(List<Component<P>> components, List<Camera<P>> cameras, List<Light<P>> lights) {
        this.components = components;
        this.cameras = cameras;
        this.lights = lights;
    }

    public List<BufferedImage> render() {
        List<Ray<P>> rays = lights.stream().flatMap(l -> l.emit().stream()).toList();

        for (Ray<P> ray : rays) {
            Optional<Double> firstHit = components.stream()
                    .map(c -> c.intercept(ray))
                    .filter(Objects::nonNull)
                    .sorted()
                    .findFirst();
            if (firstHit.isEmpty())
                continue;

            for (var cam : cameras) {
                cam.record(ray.at(firstHit.get()));
            }
        }

        return cameras.stream().map(Camera::render).toList();
    }

    public static class Scene2DBuilder {
        private List<Component<Point2D>> components = new ArrayList<>();
        private List<Camera<Point2D>> cameras = new ArrayList<>();
        private List<Light<Point2D>> lights = new ArrayList<>();
        public Scene2DBuilder() {
        }

        public static Scene2DBuilder of() {
            return new Scene2DBuilder();
        }

        @SafeVarargs
        public final Scene2DBuilder withComponents(Component<Point2D>... components) {
            this.components = Arrays.stream(components).toList();
            return this;
        }

        @SafeVarargs
        public final Scene2DBuilder withCameras(Camera<Point2D>... cameras) {
            this.cameras = Arrays.stream(cameras).toList();
            return this;
        }

        @SafeVarargs
        public final Scene2DBuilder withLights(Light<Point2D>... lights) {
            this.lights = Arrays.stream(lights).toList();
            return this;
        }

        public Scene<Point2D> build() {
            return new Scene<>(components, cameras, lights);
        }
    }
    public static class Scene3DBuilder {
        private List<Component<Point3D>> components;
        private List<Camera<Point3D>> cameras;
        private List<Light<Point3D>> lights;
        public Scene3DBuilder() {

        }

        public static Scene3DBuilder of() {
            return new Scene3DBuilder();
        }

        @SafeVarargs
        public final Scene3DBuilder withComponents(Component<Point3D>... components) {
            this.components = Arrays.stream(components).toList();
            return this;
        }

        @SafeVarargs
        public final Scene3DBuilder withCameras(Camera<Point3D>... cameras) {
            this.cameras = Arrays.stream(cameras).toList();
            return this;
        }

        @SafeVarargs
        public final Scene3DBuilder withLights(Light<Point3D>... lights) {
            this.lights = Arrays.stream(lights).toList();
            return this;
        }

        public Scene<Point3D> build() {
            return new Scene<>(components, cameras, lights);
        }
    }
}
