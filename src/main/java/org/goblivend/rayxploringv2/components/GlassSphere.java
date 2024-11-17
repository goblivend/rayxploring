package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class GlassSphere extends Sphere {

    protected final double transparency;
    protected final double refractiveness;
    public GlassSphere(Color color, double reflectivity, Vector3D center, Integer radius, double transparency, double refractiveness) {
        super(color, reflectivity, center, radius);
        this.transparency = transparency;
        this.refractiveness = refractiveness;
    }

    @Override
    public Tuple<Stream<Ray<Vector3D>>, Function<Stream<Color>, Color>> rebound(Ray<Vector3D> ray) {
        return super.rebound(ray);
        // Need 1 ray rebound and 1 ray refract
        // Seems similar than ReboundPlain except when in polar instead of `x + PI/2` we will have an operation on `y` and `refractiveness`
        // and we will need to keep zAxis going to the center => .revert() the one calculated in Sphere::rebound
        // Risk of infinite recursion going `refract -> rebound -> refract [...]`
    }
}
