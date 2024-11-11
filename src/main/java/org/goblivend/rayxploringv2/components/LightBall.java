package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.Objects;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

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

    public double intensity() {
        return intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LightBall lightBall = (LightBall) o;
        return super.equals(lightBall)
                && Double.compare(intensity, lightBall.intensity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius, color, intensity);
    }

    @Override
    public String toString() {
        return "LightBall[" +
                "center=" + center + ", " +
                "radius=" + radius + ", " +
                "color=" + color + ", " +
                "intensity=" + intensity + ']';
    }

}
