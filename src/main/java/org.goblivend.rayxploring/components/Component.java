package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector;

public interface Component<V extends Vector<V>> {
    Double intercept(Ray<V> ray);
}
