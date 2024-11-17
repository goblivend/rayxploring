package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector;
import org.goblivend.rayxploringv2.Utils.Vector2D;

import java.awt.*;

public record Ray<V extends Vector<V>>(Vector2D imgPos, V pos, V dir, Color color) {
    public Ray<V> at(double t) {
        return new Ray<>(imgPos, pos.translate(dir, t), dir, color);
    }
}
