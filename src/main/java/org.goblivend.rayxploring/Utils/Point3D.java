package org.goblivend.rayxploring.Utils;

public record Point3D(double x, double y, double z) implements Point<Point3D> {

    @Override
    public Point3D translate(Point3D dir, double time) {
        return new Point3D(x + dir.x*time, y + dir.y*time, z + dir.z*time);
    }
}
