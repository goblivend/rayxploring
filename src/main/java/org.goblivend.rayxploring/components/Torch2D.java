package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector2D;

import java.awt.*;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.goblivend.rayxploring.Utils.MathUtils.*;

public record Torch2D(int lightLevel, Vector2D pos, Vector2D dir, double dispersion, Color color) implements Light<Vector2D> {
    public Stream<Ray<Vector2D>> emit() {
        Random rd = new Random();
        return IntStream.range(0, lightLevel)
                .mapToObj(i -> new Ray<>(pos, disperseVector(rd, dir, dispersion), color));
    }
}
