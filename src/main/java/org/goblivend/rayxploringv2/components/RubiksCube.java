package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Tuple3;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.orthonormalBase;

public class RubiksCube extends AdditiveComponent {
    public RubiksCube(double reflectivity, Vector3D pos, Tuple3<Vector3D, Vector3D, Vector3D> base, double size) {
        super();

        var eltSize = size / 3.2;
        var gapSize = (size - eltSize*3) / 4.;

        subComponents.add(new Pavement(Color.RED, Color.BLACK, reflectivity, pos.translate(base.t1(), 0.5 * size), base.t1(), new Vector2D(size, size), eltSize, gapSize));
        subComponents.add(new Pavement(Color.ORANGE, Color.BLACK, reflectivity, pos.translate(base.t1(), -0.5 * size), base.t1().reverse(), new Vector2D(size, size), eltSize, gapSize));

        subComponents.add(new Pavement(Color.BLUE, Color.BLACK, reflectivity, pos.translate(base.t2(), 0.5 * size), base.t2(), new Vector2D(size, size), eltSize, gapSize));
        subComponents.add(new Pavement(Color.GREEN, Color.BLACK, reflectivity, pos.translate(base.t2(), -0.5 * size), base.t2().reverse(), new Vector2D(size, size), eltSize, gapSize));

        subComponents.add(new Pavement(Color.WHITE, Color.BLACK, reflectivity, pos.translate(base.t3(), 0.5 * size), base.t3(), new Vector2D(size, size), eltSize, gapSize));
        subComponents.add(new Pavement(Color.YELLOW, Color.BLACK, reflectivity, pos.translate(base.t3(), -0.5 * size), base.t3().reverse(), new Vector2D(size, size), eltSize, gapSize));
    }

    public RubiksCube(double reflectivity, Vector3D pos, Vector3D dir, Tuple<Vector3D, Vector3D> base, double size) {
        this(reflectivity, pos, new Tuple3<>(dir, base.t1(), base.t2()), size);
    }
    public RubiksCube(double reflectivity, Vector3D pos, Vector3D dir, double size) {
        this(reflectivity, pos, dir, orthonormalBase(dir), size);
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
