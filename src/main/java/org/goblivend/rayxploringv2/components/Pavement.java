package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class Pavement extends Rectangle {
    protected final Color gapColor;
    protected final double eltSize;
    protected final double gapSize;

    public Pavement(Color color, Color gapColor, double reflectivity, Vector3D pos, Vector3D dir, Vector2D dimensions, double eltSize, double gapSize) {
        super(color, reflectivity, pos, dir, dimensions);

        this.gapColor = gapColor;
        this.eltSize = eltSize;
        this.gapSize = gapSize;
    }

    @Override
    public Tuple<Stream<Ray<Vector3D>>, Function<Stream<Color>, Color>> rebound(Ray<Vector3D> ray) {
        var inter = intersection(base, pos, ray);

        var squareSize = eltSize + gapSize;
        var deltaX = ((inter.t2() + squareSize / 2 % squareSize) + squareSize) % squareSize;
        var deltaY = ((inter.t3() + squareSize / 2 % squareSize) + squareSize) % squareSize;

        Color actualColor = deltaX < gapSize || deltaY < gapSize ? gapColor : color;

        return new Tuple<>(
                Stream.of(
                        new Ray<>(
                                ray.imgPos(),
                                ray.pos().translate(ray.dir(), inter.t1()),
                                reboundPlane(dir, base, ray.dir()),
                                ray.color()
                        )
                ),
                colors -> colors.map(c -> reflectColor(c, actualColor, reflectivity))
                        .findFirst()
                        .orElseThrow()
        );
    }
}
