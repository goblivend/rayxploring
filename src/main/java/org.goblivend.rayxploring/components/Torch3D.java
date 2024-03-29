package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector3D;

import java.awt.*;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.goblivend.rayxploring.Utils.MathUtils.disperseVector;

public record Torch3D(int lightLevel, Vector3D pos, Vector3D dir, double dispersion, Color color) implements Light<Vector3D> {
    public Stream<Ray<Vector3D>> emit() {
        Random rd = new Random();
        return IntStream.range(0, lightLevel)
                .mapToObj(i -> new Ray<>(pos, disperseVector(rd, dir, dispersion), color));
    }
}
