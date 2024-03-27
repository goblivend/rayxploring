package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point2D;

import java.util.OptionalDouble;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

public class Camera2D implements Camera<Point2D> {
    private final Point2D center;
    private final Point2D size;
    private final int width;
    private final int height;

    private final List<Color>[][] img;

    public Camera2D(Point2D center, Point2D size, int width, int height) {
        this.center = center;
        this.size = size;
        this.width = width;
        this.height = height;

        this.img = new List[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img[y][x] = new ArrayList<>();
            }
        }
    }

    @Override
    public void record(Ray<Point2D> ray) {
        double minX = center.x() - size.x() / 2;
        double minY = center.y() - size.y() / 2;

        if (ray.pos().x() < minX || minX + size.x() < ray.pos().x() ||
                ray.pos().y() < minY || minY + size.y() < ray.pos().y())
            return;

        int x = (int) ((ray.pos().x() - minX) / size.x() * width);
        int y = (int) ((ray.pos().y() - minY) / size.y() * width);

        img[height - y][x].add(ray.color());
    }

    @Override
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
