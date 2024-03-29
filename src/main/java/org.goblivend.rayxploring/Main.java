package org.goblivend.rayxploring;


import org.goblivend.rayxploring.Utils.Vector3D;
import org.goblivend.rayxploring.components.*;
import org.goblivend.rayxploring.Utils.Vector2D;

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

    // Example scene in 2d
    private static void scene2d() throws IOException {
        Scene2D scene = Scene2D.Scene2DBuilder.of()
                .withComponents(
                        new Circle(new Vector2D(5, 0), 3)
                ).withCameras(
                        new Camera2D(new Vector2D(5, 0), new Vector2D(15, 15), 1000, 1000)
                ).withLights(
                        new Torch2D(1_000, new Vector2D(0, 0), new Vector2D(1, 0), 70, new Color(0, 255, 0)),
                        new Torch2D(1_000, new Vector2D(10, 0), new Vector2D(-11, 0), 130, new Color(0, 0, 255)),
                        new Torch2D(10_000, new Vector2D(5, 5), new Vector2D(0, -1), 60, new Color(255, 0, 0))
                ).build();
        renderScene(scene);
    }

    // Example scene in 3d
    private static void scene3d() throws IOException {
        Scene3D scene = Scene3D.Scene3DBuilder.of()
                .withLights(
                        new Torch3D(300_000, new Vector3D(0, 0, 0), new Vector3D(1, 0, 0), 20, new Color(255, 0, 0))
                ).withComponents(
                        new Camera3D(new Vector3D(5, 0, 0), new Vector3D(-1, 0, 0), new Vector2D(16, 9), 1920, 1080)
                ).build();
        renderScene(scene);
    }
}