package org.goblivend.rayxploring.Utils;

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
}
