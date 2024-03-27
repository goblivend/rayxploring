package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point3D;

public record Sphere(Point3D center, Integer radius) implements Component<Point3D> {
    @Override
    public Double intercept(Ray<Point3D> ray) {
        // (X + tDX - Px)^2 + (Y + tDY - Py)^2  + (Z + tDZ - Pz)^2 = R^2

        // a = DX^2 + DY^2 + DY^2
        double a = Math.pow(ray.dir().x(), 2) + Math.pow(ray.dir().y(), 2)+ Math.pow(ray.dir().z(), 2);
        // b = 2*DX*(X-Px) + 2*DY*(Y-Py) 2*DZ*(Z-Pz)
        double b = 2*ray.dir().x()*(ray.pos().x() - center.x()) + 2*ray.dir().y()*(ray.pos().y() - center.y()) + 2*ray.dir().z()*(ray.pos().z() - center.z());
        // c = (X-Px)^2 + (Y-Py)^2 + (Z-Pz)^2 - R^2
        double c = Math.pow(ray.pos().x() - center.x(), 2) + Math.pow(ray.pos().y() - center.y(), 2) + Math.pow(ray.pos().z() - center.z(), 2) - Math.pow(radius, 2);

        // delta = b - 4*a*c
        double delta = Math.pow(b, 2) - 4*a*c;

        // delta < 0 ? return null
        if (delta < 0)
            return null;

        // t1 = (-b + sqrt(delta)) / (2*a)
        double t1 = (-b + Math.sqrt(delta)) / (2*a);

        // t2 = (-b - sqrt(delta)) / (2*a)
        double t2 = (-b - Math.sqrt(delta)) / (2*a);

        // return min t1 t2
        if (t1 < 0)
            return null;

        if (t2 < 0)
            return t1;

        // t2 < t1
        return t2;
    }
}
