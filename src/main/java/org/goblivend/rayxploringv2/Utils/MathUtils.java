package org.goblivend.rayxploringv2.Utils;

import org.goblivend.rayxploringv2.components.Ray;

import java.awt.*;
import java.util.Random;

import static java.lang.Double.isNaN;
import static java.lang.Math.*;

public class MathUtils {
    public static double vector2dToAngle(Vector2D p) {
        double angle = acos(p.x() / hypot(p.x(), p.y()));
        if (p.y() < 0)
            angle *= -1;

        return angle;
    }

    public static Vector2D angleToVector2d(double angle) {
        if (isNaN(angle))
            return new Vector2D(0, 0);
        return new Vector2D(cos(angle), sin(angle));
    }

    public static Vector2D vector3dToAngles(Vector3D p) {
        double alpha = vector2dToAngle(new Vector2D(p.x(), p.y()));

        double beta = vector2dToAngle(new Vector2D(p.z(), hypot(p.x(), p.y())));

        if (isNaN(alpha))
            alpha = 0;

        if (isNaN(beta))
            beta = 0;

        return new Vector2D(alpha, beta);
    }

    public static Vector3D anglesToVector3d(Vector2D angles) {
        double alpha = angles.x();
        double beta = angles.y();

        double x = cos(alpha) * sin(beta);
        double y = sin(alpha) * sin(beta);
        double z = cos(beta);

        if (isNaN(alpha)) {
            x = 0;
            y = 0;
        }

        if (isNaN(beta))
            z = 0;

        return new Vector3D(x, y, z);
    }

    public static double disperseAngle(Random rd, double angle, double radDisp) {
        return angle + rd.nextDouble() * radDisp - (radDisp / 2);
    }

    public static Vector2D disperseAngles(Random rd, double radDisp) {
        return new Vector2D(rd.nextDouble() * 2 * PI, rd.nextDouble() * radDisp);
    }

    public static Vector2D disperseVector(Random rd, Vector2D dir, double degreesDisp) {
        return angleToVector2d(disperseAngle(rd, vector2dToAngle(dir), toRadians(degreesDisp)));
    }

    public static Vector3D disperseVector(Random rd, Vector3D dir, double degreesDisp) {
        Vector2D angles = disperseAngles(rd, toRadians(degreesDisp));
        Vector2D spherical = vector3dToAngles(dir);

        return anglesToVector3d(new Vector2D(spherical.x() + cos(angles.x()) * angles.y(), spherical.y() + sin(angles.x()) * angles.y()));
    }

    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() * v2.z();
    }

    public static double scalarProduct(Vector3D v1, Vector3D v2) {
        return dotProduct(v1, v2) / v1.hypot() / v2.hypot();
    }

    public static boolean doubleEqual(double d1, double d2) {
        double error = 0.001d;
        return abs(d1 - d2) < error;
    }

    public static Tuple<Vector3D, Vector3D> orthogonalBase(Vector3D v1) {
        Vector2D v1S = vector3dToAngles(v1);
        double alpha1 = v1S.x();
        double beta1 = v1S.y();

        double alpha2 = doubleEqual(sin(beta1), 0) ? 0 : alpha1 + PI / 2; // +- PI/2
        double beta2 = PI / 2;

        double alpha3 = alpha2 + PI / 2; // +- PI/2
        double beta3 = atan2(-cos(beta1), sin(beta1) * cos(alpha1 - alpha3));

        return new Tuple<>(anglesToVector3d(new Vector2D(alpha2, beta2)), anglesToVector3d(new Vector2D(alpha3, beta3)));
    }

    public static Tuple<Vector3D, Vector3D> orthonormalBase(Vector3D v1) {
        var orthogonalBase = orthogonalBase(v1);
        return new Tuple<>(orthogonalBase.t1().normalized(), orthogonalBase.t2().normalized());
    }

    /**
     * Returns the roots of any polynomial of the shape aX^2 + bX + c
     *
     * @param a
     * @param b
     * @param c constant
     * @return The roots
     */
    public static Tuple<Double, Double> roots(double a, double b, double c) {
        // delta = b - 4*a*c
        double delta = Math.pow(b, 2) - 4 * a * c;

        // delta < 0 ? return null
        if (delta < 0)
            return null;

        // t1 = (-b - sqrt(delta)) / (2*a)
        double t1 = (-b - Math.sqrt(delta)) / (2 * a);

        // t2 = (-b + sqrt(delta)) / (2*a)
        double t2 = (-b + Math.sqrt(delta)) / (2 * a);

        return new Tuple<>(t1, t2);
    }


    /**
     * Gets the smallest positive root out of a 2nd degree polynomial
     * of the shape aX^2 + bX + c = 0
     *
     * @param a
     * @param b
     * @param c constant
     * @return smallest positive root or null if not found
     */
    public static Double spRoot(double a, double b, double c) {
        Tuple<Double, Double> roots = roots(a, b, c);

        if (roots == null)
            return null;

        // return min t1 t2
        if (roots.t2() < 0)
            return null;

        if (roots.t1() < 0)
            return roots.t2();

        // t1 < t2
        return roots.t1();
    }

    /**
     * Function calculating the time and coordinate of a ray hitting a plane from a 3d model
     *
     * @param plane  X and Y vectors describing the arrival plane ()
     * @param origin The point of origin of the plane
     * @param ray    The ray
     * @return a 3-tuple containing in first position the time at which the ray reaches the plane, then the coordinates on the plane
     */
    public static Tuple3<Double, Double, Double> intersection(Tuple<Vector3D, Vector3D> plane, Vector3D origin, Ray<Vector3D> ray) {
        double X = ray.pos().x() - origin.x();
        double Y = ray.pos().y() - origin.y();
        double Z = ray.pos().z() - origin.z();

        double dX = ray.dir().x();
        double dY = ray.dir().y();
        double dZ = ray.dir().z();

        double X1 = plane.t1().x();
        double Y1 = plane.t1().y();
        double Z1 = plane.t1().z();

        double X2 = plane.t2().x();
        double Y2 = plane.t2().y();
        double Z2 = plane.t2().z();

        double delta = determinant(new double[][] {
                {X1, X2, -dX},
                {Y1, Y2, -dY},
                {Z1, Z2, -dZ},
        });

        double t = determinant(new double[][]{
                {X1, X2, X},
                {Y1, Y2, Y},
                {Z1, Z2, Z}
        }) / delta;

        double lambda1 = determinant(new double[][]{
                {X, X2, -dX},
                {Y, Y2, -dY},
                {Z, Z2, -dZ}
        }) / delta;

        double lambda2 = determinant(new double[][]{
                {X1, X, -dX},
                {Y1, Y, -dY},
                {Z1, Z, -dZ}
        }) / delta;



        /*
        // Only works if dX != 0 so in such a case rotate the vectors
        if (dX == 0) {
            return intersection(
                    new Tuple<>(
                            new Vector3D(Y1, Z1, X1),
                            new Vector3D(Y2, Z2, X2)),
                    new Vector3D(origin.y(), origin.z(), origin.x()),
                    new Ray<>(
                            new Vector3D(ray.pos().y(), ray.pos().z(), ray.pos().x()),
                            new Vector3D(dY, dZ, dX),
                            ray.color()
                    )
            );
        }


        double A = X2 / dX + (X1 * (X2 * dY - Y2 * dX)) / (dX * (Y1 * dX - X1 * dY));
        double B = -X1 / dX * (X * dY + Y * dX) / (Y1 * dX - X1 * dY) - X / dX;
        double C = (X2 * dY - Y2 * dX) / (Y1 * dX - X1 * dY);
        double D = (X * dY + Y * dX) / (Y1 * dX - X1 * dY);

        double lambda2 = (B * dZ - Z + D * Z1) / (Z2 - A * dZ - C * Z1);
        double lambda1 = (lambda2 * (X2 * dY - Y2 * dX) - X * dY + Y * dX) / (Y1 * dX - X1 * dY);
        double t = (lambda1 * X1 + lambda2 * X2 - X) / dX;*/

        return new Tuple3<>(t, lambda1, lambda2);
    }

    public static double determinant(double[][] m) {
        if (m.length == 0 || m.length != m[0].length) {
            throw new IllegalArgumentException();
        }

        if (m.length == 2) {
            return m[0][0]*m[1][1] - m[0][1]*m[1][0];
        }

        if (m.length == 3) {
            return m[0][0] * determinant(new double[][] {{m[1][1], m[1][2]}, {m[2][1], m[2][2]}})
                    - m[1][0] * determinant(new double[][] {{m[0][1], m[0][2]}, {m[2][1], m[2][2]}})
                    + m[2][0] * determinant(new double[][] {{m[0][1], m[0][2]}, {m[1][1], m[1][2]}});
        }
        throw new IllegalArgumentException("Determinant not defined for n not in [2, 3]");
    }

    public static double[][] matMul(double[][] m1, double[][] m2) {
        assert m1.length > 0 && m1[0].length > 0;
        assert m2.length > 0 && m2[0].length > 0;
        assert m1[0].length == m2.length;

        double[][] res = new double[m1.length][m2[0].length];

        for (int i = 0; i < m1.length; i++) {
            for (int k = 0; k < m2[0].length ; k++) {
                for (int j = 0; j < m2.length; j++) {
                    res[i][k] += m1[i][j] * m2[j][k];
                }
            }
        }

        return res;
    }

    public static double[][] transpose(double[][] m) {
        assert m.length > 0 && m[0].length > 0;

        double[][] res = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                res[j][i] = m[i][j];
            }
        }
        return res;
    }

    public static Double interceptSphere(Vector3D center, double radius, Ray<Vector3D> ray) {
        // (X + tDX - Px)^2 + (Y + tDY - Py)^2  + (Z + tDZ - Pz)^2 = R^2

        // a = DX^2 + DY^2 + DY^2
        double a = pow(ray.dir().x(), 2) + pow(ray.dir().y(), 2)+ pow(ray.dir().z(), 2);
        // b = 2*DX*(X-Px) + 2*DY*(Y-Py) 2*DZ*(Z-Pz)
        double b = 2*ray.dir().x()*(ray.pos().x() - center.x()) + 2*ray.dir().y()*(ray.pos().y() - center.y()) + 2*ray.dir().z()*(ray.pos().z() - center.z());
        // c = (X-Px)^2 + (Y-Py)^2 + (Z-Pz)^2 - R^2
        double c = pow(ray.pos().x() - center.x(), 2) + pow(ray.pos().y() - center.y(), 2) + pow(ray.pos().z() - center.z(), 2) - pow(radius, 2);

        return spRoot(a, b, c);
    }

    public static double[][] transitionMatrix(Vector3D v1, Vector3D v2, Vector3D v3) {
        return new double[][] {
                {v1.x(), v2.x(), v3.x()},
                {v1.y(), v2.y(), v3.y()},
                {v1.z(), v2.z(), v3.z()}
        };
    }

    public static Color reflectColor(Color rayColor, Color materialColor, double reflectivity) {
        assert 0 <= reflectivity && reflectivity <= 1;

//        int r = (int) (rayColor.getRed() * (materialColor.getRed() / 255.));
//        int g = (int) (rayColor.getGreen() * (materialColor.getGreen() / 255.));
//        int b = (int) (rayColor.getBlue() * (materialColor.getBlue() / 255.));
//
        int r = (int) (rayColor.getRed()   * reflectivity + materialColor.getRed()   * (1-reflectivity));
        int g = (int) (rayColor.getGreen() * reflectivity + materialColor.getGreen() * (1-reflectivity));
        int b = (int) (rayColor.getBlue()  * reflectivity + materialColor.getBlue()  * (1-reflectivity));

        return new Color(r, g, b);
    }

    public static double clamp(double mi, double ma, double val) {
        return max(mi, min(ma, val));
    }

    public static Color intensifyColor(Color color, double intensity) {
        int r = (int) clamp(0, 255, color.getRed() * intensity);
        int g = (int) clamp(0, 255, color.getGreen() * intensity);
        int b = (int) clamp(0, 255, color.getBlue() * intensity);

        return new Color(r, g, b);
    }


    public static Vector3D reboundPlane(Vector3D zAxis, Tuple<Vector3D, Vector3D> xyAxis, Vector3D dir) {
        double[][] transition = transitionMatrix(xyAxis.t1(), xyAxis.t2(), zAxis);

        double[][] dirNewBase = matMul(transpose(transition), dir.reverse().toMatrix());

        Vector2D angles = vector3dToAngles(new Vector3D(dirNewBase));
        Vector3D rnewBase = anglesToVector3d(new Vector2D(angles.x() + Math.PI, angles.y()));

        double[][] rebounded = matMul(transition, rnewBase.toMatrix());

        return new Vector3D(rebounded);
    }
}
