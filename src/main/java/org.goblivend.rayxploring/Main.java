package org.goblivend.rayxploring;


import org.goblivend.rayxploring.components.Camera2D;
import org.goblivend.rayxploring.components.Circle;
import org.goblivend.rayxploring.Utils.Point2D;
import org.goblivend.rayxploring.components.Torch2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Scene<Point2D> scene = Scene.Scene2DBuilder.of()
                .withComponents(new Circle(new Point2D(5, 0), 3))
                .withCameras(new Camera2D(new Point2D(5, 0), new Point2D(10, 10), 200, 200))
                .withLights(
                        new Torch2D(1_000_000, new Point2D(0, 0), new Point2D(1, 0), 90, new Color(0, 255, 0)),
                        new Torch2D(1_000_000, new Point2D(10, 0), new Point2D(-11, 0), 90, new Color(0, 0, 255)),
                        new Torch2D(1_000_000, new Point2D(5, 5), new Point2D(0, -1), 90, new Color(255, 0, 0)))
                .build();
        int i = 0;
        for (BufferedImage img : scene.render()) {
            File outputfile = new File(String.format("image%d.png", i));
            ImageIO.write(img, "png", outputfile);
            i++;
        }
    }
}