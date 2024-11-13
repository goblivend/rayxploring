package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.intensifyColor;

public final class LightBall extends Sphere {
    private final double intensity;

    public LightBall(Color color, double reflectivity, Vector3D center, Integer radius, double intensity) {
        super(color, reflectivity, center, radius);
        this.intensity = intensity;
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        Ray<Vector3D> tmp = super.rebound(ray);

        return new Ray<>(tmp.imgPos(), tmp.pos(), tmp.dir(), c -> intensifyColor(tmp.color().apply(c),  intensity));
    }
}
