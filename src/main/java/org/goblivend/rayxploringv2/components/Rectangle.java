package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class Rectangle extends Plane {

    protected final Vector2D dimensions;

    public Rectangle(Color color, double reflectivity, Vector3D pos, Vector3D dir, Vector2D dimensions) {
        super(color, reflectivity, pos, dir);
        this.dimensions = dimensions;
    }

    protected boolean inBounds(Vector2D pos) {
        return -dimensions.x() / 2 <= pos.x()
                && pos.x() < dimensions.x() / 2
                && -dimensions.y() / 2 <= pos.y()
                && pos.y() < dimensions.y() / 2;
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        var t = intersection(base, pos, ray);
        return inBounds(new Vector2D(t.t2(), t.t3())) ? t.t1() : null;
    }
}
