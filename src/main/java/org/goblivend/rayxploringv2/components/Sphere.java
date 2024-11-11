package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.Objects;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class Sphere implements Component<Vector3D> {
    protected final Color color;
    protected final double reflecivity;
    protected final Vector3D center;
    protected final Integer radius;

    public Sphere(Color color, double reflectivity, Vector3D center, Integer radius) {
        this.color = color;
        this.reflecivity = reflectivity;
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return interceptSphere(center, radius, ray);
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        Double interceptTime = intercept(ray);
        assert interceptTime != null && interceptTime > 1E-5;


        Vector3D hitPoint = ray.pos().translate(ray.dir(), interceptTime);

        Vector3D zAxis = new Vector3D(hitPoint.x() - center.x(), hitPoint.y() - center.y(), hitPoint.z() - center.z()).normalized();

        Tuple<Vector3D, Vector3D> xyAxis = orthogonalBase(zAxis);

        Vector3D reboundedDir = reboundPlane(zAxis, xyAxis, ray.dir());



        Vector3D lightDir = hitPoint.plus(center.reverse());
        double scalar = Math.abs(scalarProduct(ray.dir(), lightDir));

        return new Ray<>(ray.imgPos(), hitPoint, reboundedDir, c -> ray.color().apply(intensifyColor(reflectColor(c, color, reflecivity), scalar / 2 + 0.5)));
    }

    public Vector3D center() {
        return center;
    }

    public Integer radius() {
        return radius;
    }

    public Color color() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Sphere) obj;
        return Objects.equals(this.center, that.center) &&
                Objects.equals(this.radius, that.radius) &&
                Objects.equals(this.color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius, color);
    }

    @Override
    public String toString() {
        return "Sphere[" +
                "center=" + center + ", " +
                "radius=" + radius + ", " +
                "color=" + color + ']';
    }

}
