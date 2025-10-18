package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Tuple;
import org.goblivend.rayxploring.Utils.Vector2D;
import org.goblivend.rayxploring.Utils.Vector3D;

import java.awt.*;

import static org.goblivend.rayxploring.Utils.MathUtils.*;

public record Sphere(Vector3D center, Integer radius, Color color) implements Component<Vector3D> {
    @Override
    public Double intercept(Ray<Vector3D> ray) {
        return interceptSphere(center, radius, ray);
    }

    private Color reflectColor(Color rayColor) {
        return new Color(
                (int) (color.getRed() / 255d * rayColor.getRed()),
                (int) (color.getGreen() / 255d * rayColor.getGreen()),
                (int) (color.getBlue() / 255d * rayColor.getBlue())
                );
    }

    @Override
    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
        Double interceptTime = intercept(ray);
        assert interceptTime != null && interceptTime > 1E-5;


        Vector3D hitPoint = ray.pos().translate(ray.dir(), interceptTime);

        Vector3D Zaxis = new Vector3D(hitPoint.x() - center.x(), hitPoint.y() - center.y(), hitPoint.z() - center.z()).normalized();
        Tuple<Vector3D, Vector3D> XYaxis = orthogonalBase(Zaxis);

        double[][] transition = transitionMatrix(XYaxis.t1(), XYaxis.t2(), Zaxis);

        double[][] dirNewBase = matMul(transpose(transition), ray.dir().reverse().toMatrix());

        Vector2D angles = vector3dToAngles(new Vector3D(dirNewBase));
        Vector3D rnewBase = anglesToVector3d(new Vector2D(angles.x()+Math.PI, angles.y()));

        double[][] rebounded = matMul(transition, rnewBase.toMatrix());

        return new Ray<>(hitPoint, new Vector3D(rebounded), reflectColor(ray.color()));
    }
}
