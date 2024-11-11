package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class Plane implements Component<Vector3D> {
    protected final Color color;
    protected final double reflectivity;
    protected final Vector3D pos;
    protected final Vector3D dir;
    protected final Tuple<Vector3D, Vector3D> base;

    public Plane(Color color, double reflectivity, Vector3D pos, Vector3D dir) {
        assert 0 <= reflectivity && reflectivity <= 1;
        this.color = color;
        this.reflectivity = reflectivity;
        this.pos = pos;
        this.dir = dir;
        this.base = orthonormalBase(dir);
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return intersection(base, pos, ray).t1();
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        return new Ray<>(
                ray.imgPos(),
                ray.pos().translate(ray.dir(), intersection(base, pos, ray).t1()),
                reboundPlane(dir, base, ray.dir()),
                c -> ray.color().apply(reflectColor(c, color, reflectivity)));
    }
}
