package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Vector;

public interface Component<V extends Vector<V>> {
    Double intercept(Ray<V> ray);

    Ray<V> rebound(Ray<V> ray);
}
