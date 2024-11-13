package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

public class MirrorSphere extends Sphere {
    public MirrorSphere(Vector3D center, Integer radius) {
        super(Color.WHITE, 1, center, radius);
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return super.intercept(ray);
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        return super.rebound(ray);
    }
}
