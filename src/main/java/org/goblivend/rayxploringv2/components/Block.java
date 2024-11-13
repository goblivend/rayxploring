package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Tuple3;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.orthonormalBase;

public class Block extends AdditiveComponent {
    public Block(Color color, double reflectivity, Vector3D pos, Tuple3<Vector3D, Vector3D, Vector3D> base, Vector3D sizes) {
        super();
        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t1(), 0.5 * sizes.x()), base.t1(), new Vector2D(sizes.y(), sizes.z())));
        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t2(), 0.5 * sizes.y()), base.t2(), new Vector2D(sizes.x(), sizes.z())));
        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t3(), 0.5 * sizes.z()), base.t3(), new Vector2D(sizes.x(), sizes.y())));

        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t1(), -0.5 * sizes.x()), base.t1().reverse(), new Vector2D(sizes.y(), sizes.z())));
        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t2(), -0.5 * sizes.y()), base.t2().reverse(), new Vector2D(sizes.x(), sizes.z())));
        subComponents.add(new Rectangle(color, reflectivity, pos.translate(base.t3(), -0.5 * sizes.z()), base.t3().reverse(), new Vector2D(sizes.x(), sizes.y())));
    }

    public Block(Color color, double reflectivity, Vector3D pos, Vector3D dir, Tuple<Vector3D, Vector3D> base, Vector3D sizes) {
        this(color, reflectivity, pos, new Tuple3<>(dir, base.t1(), base.t2()), sizes);
    }
    public Block(Color color, double reflectivity, Vector3D pos, Vector3D dir, Vector3D sizes) {
        this(color, reflectivity, pos, dir, orthonormalBase(dir), sizes);
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
