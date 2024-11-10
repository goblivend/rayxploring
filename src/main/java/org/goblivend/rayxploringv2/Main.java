package org.goblivend.rayxploringv2;


import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;
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
//                        new LightBall(new Vector3D(1, 0, 0), 1, Color.RED, 2),
//                        new Sphere(new Vector3D(-8, 8, 0), 2, Color.BLUE),
                        new Sphere(new Vector3D(0, 0, 0), 1, Color.WHITE)
                ).withCameras(
                        new Camera3D(1600, 1600, new Vector3D(-5, 5, 0), new Vector3D(1, -1, 0), new Vector2D(2, 2))
                ).build();
        renderScene(scene);
    }
}