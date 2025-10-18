package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Tuple;
import org.goblivend.rayxploring.Utils.Tuple3;
import org.goblivend.rayxploring.Utils.Vector2D;
import org.goblivend.rayxploring.Utils.Vector3D;

import java.awt.*;
import java.util.Optional;

import static org.goblivend.rayxploring.Utils.MathUtils.*;

public class Camera3D extends Camera<Vector3D> implements Component<Vector3D> {
    private final Vector3D center;
    private final Vector3D dir;
    private final Vector2D size;

    public final Tuple<Vector3D, Vector3D> base;
//    private final double minY;
//    private final double maxY;
//    private final double minZ;
//    private final double maxZ;

    public Camera3D(Vector3D center, Vector3D dir, Vector2D size, int width, int height) {
        super(width, height);
        this.center = center;
        this.dir = dir;
        this.size = size;

        base = orthogonalBase(dir);

//        minX = size.x() / 2;
//        maxY = minY + size.x();
//        minZ = center.z() - size.y() / 2;
//        maxZ = minZ + size.y();
    }

    public Optional<Vector3D> getHitPoint(Ray<Vector3D> ray) {
        Tuple3<Double, Double, Double> intercepted = intersection(base, center, ray);

        if (Double.isNaN(intercepted.t1()))
            return Optional.empty();

        if (intercepted.t1() < 0) {
            throw new ArithmeticException();
        }

        return Optional.of(ray.pos().translate(ray.dir(), intercepted.t1()));
    }

    private Point getIndexes(Vector3D hitPoint) {
        double[][] transition = new double[][] {
                {base.t1().x(), base.t2().x(), dir.x()},
                {base.t1().y(), base.t2().y(), dir.y()},
                {base.t1().z(), base.t2().z(), dir.z()}
        };

        Vector3D newPointHit = new Vector3D(matMul(transpose(transition), hitPoint.toMatrix()));
        Vector3D newCenter = new Vector3D(matMul(transpose(transition), center.toMatrix()));

        // TODO : FIX                              HERE NOT CENTER.X()
        int x =          (int) ((newPointHit.x() - newCenter.x() + size.x()/2) / size.x() * width);
        int y = height - (int) ((newPointHit.y() - newCenter.y() + size.y()/2) / size.y() * height);


        return new Point(x, y);
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        Optional<Vector3D> hitPoint = getHitPoint(ray);

        if (hitPoint.isEmpty())
            return ray;

        Point p = getIndexes(hitPoint.get());

        if (p.x < 0 || width <= p.x
            || p.y < 0 || height <= p.y)
            return ray;

        img[p.y][p.x].add(ray.color());

        return null;
    }

    @Override
    public void trace(Ray<Vector3D> ray, Optional<Vector3D> limit) {
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        Double t = intersection(base, center, ray).t1();

        if (t.isNaN() || t < 0)
            return null;

        Point p = getIndexes(ray.pos().translate(ray.dir(), t));

        if (p.x < 0 || width <= p.x
                || p.y < 0 || height <= p.y)
            return null;

        return t;
    }
}
