package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector;

import java.awt.*;

public record Ray<V extends Vector<V>>(V pos, V dir, Color color) {
    public Ray<V> at(double t) {
        return new Ray<>(pos.translate(dir, t), dir, color);
    }
}
