package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.goblivend.rayxploringv2.Utils.MathUtils.orthonormalBase;

public class Block implements Component<Vector3D> {
    protected final Color color;
    protected final double reflectivity;
    protected final List<Component<Vector3D>> subComponents;


    public Block(Color color, double reflectivity, Vector3D pos, Tuple3<Vector3D, Vector3D, Vector3D> base, Vector3D sizes) {
        this.color = color;
        this.reflectivity = reflectivity;

        subComponents = new ArrayList<>();
        subComponents.add(new Rectangle(Color.RED, reflectivity, pos.translate(base.t1(), 0.5 * sizes.x()), base.t1(), new Vector2D(sizes.y(), sizes.z())));
        subComponents.add(new Rectangle(Color.ORANGE, reflectivity, pos.translate(base.t1(), -0.5 * sizes.x()), base.t1().reverse(), new Vector2D(sizes.y(), sizes.z())));


        subComponents.add(new Rectangle(Color.BLUE, reflectivity, pos.translate(base.t2(), 0.5 * sizes.y()), base.t2(), new Vector2D(sizes.x(), sizes.z())));
        subComponents.add(new Rectangle(Color.GREEN, reflectivity, pos.translate(base.t2(), -0.5 * sizes.y()), base.t2().reverse(), new Vector2D(sizes.x(), sizes.z())));

        subComponents.add(new Rectangle(Color.WHITE, reflectivity, pos.translate(base.t3(), 0.5 * sizes.z()), base.t3(), new Vector2D(sizes.x(), sizes.y())));
        subComponents.add(new Rectangle(Color.YELLOW, reflectivity, pos.translate(base.t3(), -0.5 * sizes.z()), base.t3().reverse(), new Vector2D(sizes.x(), sizes.y())));
    }

    public Block(Color color, double reflectivity, Vector3D pos, Vector3D dir, Tuple<Vector3D, Vector3D> base, Vector3D sizes) {
        this(color, reflectivity, pos, new Tuple3<>(dir, base.t1(), base.t2()), sizes);
    }
    public Block(Color color, double reflectivity, Vector3D pos, Vector3D dir, Vector3D sizes) {
        this(color, reflectivity, pos, dir, orthonormalBase(dir), sizes);
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
