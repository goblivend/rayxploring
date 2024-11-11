package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector;
import org.goblivend.rayxploringv2.Utils.Vector2D;

import java.awt.*;
import java.util.function.Function;

public record Ray<V extends Vector<V>>(Vector2D imgPos, V pos, V dir, Function<Color, Color> color) {
    public Ray<V> at(double t) {
        return new Ray<>(imgPos, pos.translate(dir, t), dir, color);
    }
}
