package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point;

public interface Component<P extends Point<P>> {
    Double intercept(Ray<P> ray);
}
