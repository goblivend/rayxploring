package org.goblivend.rayxploringv2;

import org.goblivend.rayxploringv2.Utils.Vector3D;
import org.goblivend.rayxploringv2.cameras.Camera;
import org.goblivend.rayxploringv2.components.Component;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Scene3D implements Scene {
    private final List<Component<Vector3D>> components;
    private final List<Camera<Vector3D>> cameras;

    protected Scene3D(List<Component<Vector3D>> components, List<Camera<Vector3D>> cameras) {
        this.components = components;
        this.cameras = cameras;
    }

    public List<BufferedImage> render() {
        return cameras.stream().map(cam -> cam.render(components)).toList();
    }

    public static class Scene3DBuilder {
        private List<Component<Vector3D>> components;
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
        public final Scene3DBuilder withCameras(Camera<Vector3D>... cameras) {
            this.cameras = Arrays.stream(cameras).toList();
            return this;
        }

        public Scene3D build() {
            return new Scene3D(components, cameras);
        }
    }
}
