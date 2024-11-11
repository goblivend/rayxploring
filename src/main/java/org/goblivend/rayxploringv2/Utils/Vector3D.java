package org.goblivend.rayxploringv2.Utils;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public record Vector3D(double x, double y, double z) implements Vector<Vector3D> {
    public static Vector3D ORIGIN = new Vector3D(0, 0, 0);
    public Vector3D(double[][] m) {
        this(m[0][0], m[1][0], m[2][0]);
    }

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

    @Override
    public Vector3D normalized() {
        double h = hypot();
        return new Vector3D(x/h, y/h, z/h);
    }

    @Override
    public Vector3D plus(Vector3D v) {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    @Override
    public double[][] toMatrix() {
        return new double[][] { {x}, {y}, {z}};
    }
}
