package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector;

import java.util.stream.Stream;

public interface Light<V extends Vector<V>> {
    Stream<Ray<V>> emit();
}
