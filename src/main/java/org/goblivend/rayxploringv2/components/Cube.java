package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;

public class Cube extends Block{
    public Cube(Color color, double reflectivity, Vector3D pos, Vector3D dir, double size) {
        super(color, reflectivity, pos, dir, new Vector3D(size, size, size));
    }
}
