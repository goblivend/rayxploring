package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Tuple3;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploringv2.Utils.MathUtils.orthonormalBase;

public class Cube extends Block{

    public Cube(Color color, double reflectivity, Vector3D pos, Tuple3<Vector3D, Vector3D, Vector3D> base, double size) {
        super(color, reflectivity, pos, base, new Vector3D(size, size, size));
    }

    public Cube(Color color, double reflectivity, Vector3D pos, Vector3D dir, Tuple<Vector3D, Vector3D> base, double size) {
        this(color, reflectivity, pos, new Tuple3<>(dir, base.t1(), base.t2()), size);
    }
    public Cube(Color color, double reflectivity, Vector3D pos, Vector3D dir, double size) {
        this(color, reflectivity, pos, dir, orthonormalBase(dir), size);
    }
}
