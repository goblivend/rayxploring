package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point2D;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public record Torch2D(int lightLevel, Point2D pos, Point2D dir, double dispersion, Color color) implements Light<Point2D> {

    public List<Ray<Point2D>> emit() {
        Random rd = new Random();

        return IntStream.range(0, lightLevel)
                .mapToObj(i -> new Ray<>(pos, disperse(rd), color))
                .toList();
    }

    public Point2D disperse(Random rd) {

        double angle = Math.acos(dir.x() / Math.hypot(dir.x(), dir.y()));
        if (dir.y() < 0)
            angle *= -1;

        double radDisp = dispersion / 180 * Math.PI;
        double newAngle = angle + rd.nextDouble()* radDisp - (radDisp / 2);

        return new Point2D(Math.cos(newAngle), Math.sin(newAngle));
    }
}
