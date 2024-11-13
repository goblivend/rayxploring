package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AdditiveComponent implements Component<Vector3D> {
    protected final List<Component<Vector3D>> subComponents;

    public AdditiveComponent() {
        subComponents = new ArrayList<>();
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return subComponents.stream()
                .map(c -> c.intercept(ray))
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Function.identity()))
                .orElse(null);
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        return subComponents.stream()
                .map(c -> new Tuple<>(c, c.intercept(ray)))
                .filter(t -> Objects.nonNull(t.t2()))
                .min(Comparator.comparing(Tuple::t2))
                .map(Tuple::t1)
                .map(c -> c.rebound(ray))
                .orElse(null);
    }
}
