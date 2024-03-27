package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Point;

import java.util.List;

public interface Light<P extends Point<P>> {

    List<Ray<P>> emit();
}
