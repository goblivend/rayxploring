package org.goblivend.rayxploringv2.components;

import org.goblivend.rayxploringv2.Utils.Tuple;
import org.goblivend.rayxploringv2.Utils.Vector3D;

import java.awt.*;
import java.util.Objects;

import static org.goblivend.rayxploringv2.Utils.MathUtils.*;

public class MirrorSphere extends Sphere {
    public MirrorSphere(Vector3D center, Integer radius) {
        super(Color.WHITE, 1, center, radius);
    }

//    @Override
//    public Double intercept(Ray<Vector3D> ray) {
//        return interceptSphere(center, radius, ray);
//    }
//
//    @Override
//    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
//        Double interceptTime = intercept(ray);
//        assert interceptTime != null && interceptTime > 1E-5;
//
//
//        Vector3D hitPoint = ray.pos().translate(ray.dir(), interceptTime);
//
//        Vector3D zAxis = new Vector3D(hitPoint.x() - center.x(), hitPoint.y() - center.y(), hitPoint.z() - center.z()).normalized();
//
//        Tuple<Vector3D, Vector3D> xyAxis = orthogonalBase(zAxis);
//
//        Vector3D reboundedDir = reboundPlane(zAxis, xyAxis, ray.dir());
//
//
//
//        Vector3D lightDir = hitPoint.plus(center.reverse());
//        double scalar = Math.abs(scalarProduct(ray.dir(), lightDir));
//
//        return new Ray<>(ray.imgPos(), hitPoint, reboundedDir, intensifyColor(ray.color(), scalar));
//    }
}
