package org.goblivend.rayxploring.Utils;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public record Vector3D(double x, double y, double z) implements Vector<Vector3D> {
    @Override
    public Vector3D translate(Vector3D dir, double time) {
        return new Vector3D(x + dir.x*time, y + dir.y*time, z + dir.z*time);
    }

    @Override
    public double hypot() {
        return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
    }

    @Override
    public Vector3D reverse() {
        return new Vector3D(-x, -y, -z);
    }
}
