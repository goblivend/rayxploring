package org.goblivend.rayxploringv2.Utils;

public record Vector2D(double x, double y) implements Vector<Vector2D> {
    @Override
    public Vector2D translate(Vector2D dir, double time) {
        return new Vector2D(x + dir.x*time, y + dir.y*time);
    }

    @Override
    public double hypot() {
        return Math.hypot(x, y);
    }

    @Override
    public Vector2D reverse() {
        return new Vector2D(-x, -y);
    }

    @Override
    public Vector2D normalized() {
        double h = hypot();
        return new Vector2D(x/h, y/h);
    }

    @Override
    public Vector2D plus(Vector2D v) {
        return new Vector2D(x() + v.x, y() + v.y);
    }

    @Override
    public double[][] toMatrix() {
        return new double[][] { {x}, {y}};
    }
}
