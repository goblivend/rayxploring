package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.goblivend.rayxploringv2.Utils.MathUtils.intensifyColor;

public final class LightBall extends Sphere {
    private final double intensity;

    public LightBall(Color color, double reflectivity, Vector3D center, Integer radius, double intensity) {
        super(color, reflectivity, center, radius);
        this.intensity = intensity;
    }

    @Override
    public Tuple<Stream<Ray<Vector3D>>, Function<Stream<Color>, Color>> rebound(Ray<Vector3D> ray) {
        var superRebound = super.rebound(ray);

        return new Tuple<>(
                superRebound.t1(),
                c -> intensifyColor(superRebound.t2().apply(c), intensity)
        );
    }
}
