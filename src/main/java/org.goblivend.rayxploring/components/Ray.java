package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point;

import java.awt.*;

public record Ray<P extends Point<P>>(P pos, P dir, Color color) {


    public Ray<P> at(double t) {
        return new Ray<>(pos.translate(dir, t), dir, color);
    }
}
