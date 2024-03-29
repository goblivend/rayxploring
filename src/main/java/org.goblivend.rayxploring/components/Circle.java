package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector2D;

import static java.lang.Math.pow;
import static org.goblivend.rayxploring.Utils.MathUtils.spRoot;

public record Circle(Vector2D center, Integer radius) implements Component<Vector2D> {
    @Override
    public Double intercept(Ray<Vector2D> ray) {
        // (X + tDX - Px)^2 + (Y + tDY - Py)^2 = R^2

        // a = DX^2 + DY^2
        double a = pow(ray.dir().x(), 2) + pow(ray.dir().y(), 2);
        // b = 2*(DX)*(X-Px) + 2*(DY)*(Y-Py)
        double b = 2*ray.dir().x()*(ray.pos().x() - center.x()) + 2*ray.dir().y()*(ray.pos().y() - center.y());
        // c = (X-Px)^2 + (Y-Py)^2 - R^2
        double c = pow(ray.pos().x()- center.x(), 2) + pow(ray.pos().y() - center.y(), 2) - pow(radius, 2);

        return spRoot(a, b, c);
    }
}
