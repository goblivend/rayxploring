package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Component<V extends Vector<V>> {
    Double intercept(Ray<V> ray);

    Tuple<Stream<Ray<V>>, Function<Stream<Color>, Color>> rebound(Ray<V> ray);
}
