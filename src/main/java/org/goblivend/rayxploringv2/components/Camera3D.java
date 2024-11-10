package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector2D;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.goblivend.rayxploringv2.Utils.MathUtils.orthogonalBase;

public class Camera3D extends Camera<Vector3D> {
    private final Vector3D pos;
    private final Vector3D dir;
    private final Vector2D size;
    private final Tuple<Vector3D, Vector3D> base;

    public Camera3D(int width, int height, Vector3D pos, Vector3D dir, Vector2D size) {
        super(width, height);
        this.pos = pos;
        this.dir = dir;
        this.size = size;

        this.base = orthogonalBase(dir);
    }

    @Override
    public void trace(Ray<Vector3D> ray, Optional<Vector3D> limit) {

    }

    @Override
    public Stream<Ray<Vector3D>> generateRays() {
        return IntStream.range(0, width).mapToObj(x ->
                        IntStream.range(0, height).mapToObj(y -> new Vector2D(x, y)))
                .flatMap(Function.identity())
                .map(imgPos -> {
                    Vector2D relativePos = imgPos.translate(new Vector2D(width >> 1, height >> 1), -1);
                    Tuple<Vector3D, Vector3D> dispersion = new Tuple<>(
                            new Vector3D(0, 0, 0).translate(this.base.t1().normalized().reverse(), size.x() / width),
                            new Vector3D(0, 0, 0).translate(this.base.t2().normalized().reverse(), size.y() / height)
                    );

                    Vector3D worldPos = pos
                            .translate(dispersion.t1(), relativePos.x())
                            .translate(dispersion.t2(), relativePos.y());

                    return new Ray<>(imgPos, worldPos, dir, Color.WHITE);
                });
    }
}
