package org.goblivend.rayxploring.Utils;

public record Point2D(double x, double y) implements Point<Point2D>{
    @Override
    public Point2D translate(Point2D dir, double time) {
        return new Point2D(x + dir.x*time, y + dir.y*time);
    }
}
