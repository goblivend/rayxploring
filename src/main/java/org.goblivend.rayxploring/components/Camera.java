package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public abstract class Camera<V extends Vector<V>> {
    protected final int width;
    protected final int height;

    protected final List<Color>[][] img;

    protected Camera(int width, int height) {
        this.width = width;
        this.height = height;

        this.img = new List[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img[y][x] = new ArrayList<>();
            }
        }
    }

    public abstract void record(Ray<V> ray);
    public abstract void trace(Ray<V> ray, Optional<V> limit);
    public BufferedImage render() {
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_DEFAULT);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                OptionalDouble rs = img[y][x].stream().map(Color::getRed).mapToInt(v -> v).average();
                OptionalDouble gs = img[y][x].stream().map(Color::getGreen).mapToInt(v -> v).average();
                OptionalDouble bs = img[y][x].stream().map(Color::getBlue).mapToInt(v -> v).average();
                if (rs.isEmpty() || gs.isEmpty() || bs.isEmpty())
                    continue;
                int r = (int) Math.round(rs.getAsDouble());
                int g = (int) Math.round(gs.getAsDouble());
                int b = (int) Math.round(bs.getAsDouble());
                image.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return image;
    }
}
