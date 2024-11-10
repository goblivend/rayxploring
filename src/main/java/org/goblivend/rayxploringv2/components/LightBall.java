package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.Objects;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public final class LightBall extends Sphere {
    private final double intensity;

    public LightBall(Vector3D center, Integer radius, Color color, double intensity) {
        super(center, radius, color);
        this.intensity = intensity;
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        Ray<Vector3D> tmp = super.rebound(ray);

//
//        Double interceptTime = intercept(ray);
//        Vector3D hitPoint = ray.pos().translate(ray.dir(), interceptTime);
//        Vector3D lightDir = hitPoint.plus(center.reverse());
//
//        double scalar = Math.abs(scalarProduct(ray.dir(), lightDir));


        return new Ray<>(tmp.imgPos(), tmp.pos(), tmp.dir(), intensifyColor(tmp.color(),  intensity));
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
