package org.goblivend.rayxploringv2;


import org.goblivend.rayxploringv2.Utils.Tuple3;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;
import org.goblivend.rayxploringv2.cameras.Camera3D;
import org.goblivend.rayxploringv2.components.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

//        scene2d();
        scene3d();

    }

    private static void renderScene(Scene scene) throws IOException {
        int i = 0;
        for (BufferedImage img : scene.render()) {
            File outputfile = new File(String.format("image%d.png", i));
            ImageIO.write(img, "png", outputfile);
            i++;
        }
    }

    // Example scene in 3d
    private static void scene3d() throws IOException {
        Scene3D scene = Scene3D.Scene3DBuilder.of()
                .withComponents(
                        new Plane(Color.LIGHT_GRAY, 0.2, Vector3D.ORIGIN, new Vector3D(0, 0, 1)),
                        new MirrorSphere(new Vector3D(0, 0, 1), 1),
                        new RubiksCube(0, new Vector3D(2, 2, 0.55), new Vector3D(1, 0, 0), 1),
                        new RubiksCube(0, new Vector3D(10, 10, 10), new Vector3D(1, 0, 0), 2),
                        new Cube(Color.BLUE, 0, new Vector3D(16, 16, 16), new Vector3D(1, 0, 0), 1)
                ).withCameras(
                        new Camera3D(4000, 4000, new Vector3D(8, 8, 8), new Vector3D(-1, -1, -1), new Vector2D(8, 8), false, true)
                ).build();
        renderScene(scene);
    }
}