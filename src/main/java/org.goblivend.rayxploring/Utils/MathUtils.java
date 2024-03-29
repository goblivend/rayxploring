package org.goblivend.rayxploring.Utils;

import java.util.Random;

import static java.lang.Double.*;
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
        return angle + rd.nextDouble()*radDisp - (radDisp / 2);
    }

    public static Vector2D disperseAngles(Random rd, double radDisp) {
        return new Vector2D(rd.nextDouble() * 2*PI, rd.nextDouble() * radDisp);
    }

    public static Vector2D disperseVector(Random rd, Vector2D dir, double degreesDisp) {
        return angleToVector2d(disperseAngle(rd, vector2dToAngle(dir), toRadians(degreesDisp)));
    }

    public static Vector3D disperseVector(Random rd, Vector3D dir, double degreesDisp) {
        Vector2D angles = disperseAngles(rd, toRadians(degreesDisp));
        Vector2D spherical = vector3dToAngles(dir);

        return anglesToVector3d(new Vector2D(spherical.x() + cos(angles.x()) * angles.y(), spherical.y() + sin(angles.x()) * angles.y()));
    }

    public static double scalarProduct(Vector3D v1, Vector3D v2) {
        return v1.x() * v2.x() + v1.y() * v2.y() + v1.z() *v2.z();
    }

    public static boolean doubleEqual(double d1, double d2) {
        double error = 0.001d;
        return abs(d1 - d2) < error;
    }

    public static Tuple<Vector3D, Vector3D> orthogonalBase(Vector3D v1) {
        Vector2D v1S = vector3dToAngles(v1);
        double alpha1 = v1S.x();
        double beta1 = v1S.y();

        double alpha2 = doubleEqual(sin(beta1), 0) ? 0 : alpha1 + PI/2; // +- PI/2
        double beta2 = PI/2;

        double alpha3 = alpha2 + PI/2; // +- PI/2
        double beta3 = atan2(-cos(beta1), sin(beta1) * cos(alpha1 - alpha3));

        return new Tuple<>(anglesToVector3d(new Vector2D(alpha2, beta2)), anglesToVector3d(new Vector2D(alpha3, beta3)));
    }

    /** Returns the roots of any polynomial of the shape aX^2 + bX + c
     * @param a
     * @param b
     * @param c constant
     * @return The roots
     */
    public static Tuple<Double, Double> roots(double a, double b, double c) {
        // delta = b - 4*a*c
        double delta = Math.pow(b, 2) - 4*a*c;

        // delta < 0 ? return null
        if (delta < 0)
            return null;

        // t1 = (-b - sqrt(delta)) / (2*a)
        double t1 = (-b - Math.sqrt(delta)) / (2*a);

        // t2 = (-b + sqrt(delta)) / (2*a)
        double t2 = (-b + Math.sqrt(delta)) / (2*a);

        return new Tuple<>(t1, t2);
    }


    /**
     * Gets the smallest positive root out of a 2nd degree polynomial
     * of the shape aX^2 + bX + c = 0
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
        if (roots.t1() < 0)
            return null;

        if (roots.t2() < 0)
            return roots.t1();

        // t2 < t1
        return roots.t2();
    }
}
