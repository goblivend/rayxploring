package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point;

import java.awt.image.BufferedImage;

public interface Camera<P extends Point<P>> {

    void record(Ray<P> ray);
    BufferedImage render();
}
