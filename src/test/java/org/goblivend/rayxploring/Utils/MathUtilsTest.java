package org.goblivend.rayxploring.Utils;

import org.goblivend.rayxploring.components.Ray;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.PI;
import static java.lang.String.format;
import static org.goblivend.rayxploring.Utils.MathUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    private static Stream<Arguments> angle2DTestsProvider() {
        return Stream.of(
                Arguments.of(0d, new Vector2D(1, 0)),
                Arguments.of(PI / 2, new Vector2D(0, 1)),
                Arguments.of(PI, new Vector2D(-1, 0)),
                Arguments.of(-PI / 2, new Vector2D(0, -1)),
                Arguments.of(PI / 4, new Vector2D(0.7, 0.7)),
                Arguments.of(NaN, new Vector2D(0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("angle2DTestsProvider")
    void point2DToAngleTests(Double expected, Vector2D point) {
        double actual = vector2dToAngle(point);
        assertEquals(expected, actual, 0.01d);
    }

    @ParameterizedTest
    @MethodSource("angle2DTestsProvider")
    void angleToPoint2DTests(Double angle, Vector2D expected) {
        Vector2D actual = angleToVector2d(angle);
        assertVectors(expected, actual);
    }

    private static Stream<Arguments> angle3DTestsProvider() {
        return Stream.of(
                Arguments.of(new Vector2D(0, PI / 2), new Vector3D(1, 0, 0)),
                Arguments.of(new Vector2D(0, 0), new Vector3D(0, 0, 1)),
                Arguments.of(new Vector2D(PI / 2, PI / 2), new Vector3D(0, 1, 0)),
                Arguments.of(new Vector2D(PI, PI / 2), new Vector3D(-1, 0, 0)),
                Arguments.of(new Vector2D(PI / 4, PI / 2), new Vector3D(0.7, 0.7, 0)),
                Arguments.of(new Vector2D(0, PI), new Vector3D(0, 0, -1))
        );
    }

    @ParameterizedTest
    @MethodSource("angle3DTestsProvider")
    void point3DToAngleTests(Vector2D expected, Vector3D point) {
        Vector2D actual = vector3dToAngles(point);
        assertVectors(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("angle3DTestsProvider")
    void angleToPoint3DTests(Vector2D angle, Vector3D expected) {
        Vector3D actual = anglesToVector3d(angle);

        assertVectors(expected, actual);
    }

    private static Stream<Arguments> scalarProduct3DTestsProvider() {
        return Stream.of(
                Arguments.of(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0), 0),
                Arguments.of(new Vector3D(1, 0, 0), new Vector3D(0, 0, 1), 0),
                Arguments.of(new Vector3D(1, 0, 0), new Vector3D(0, 1, 1), 0),
                Arguments.of(new Vector3D(1, 0, 0), new Vector3D(1, 0, 0), 1),
                Arguments.of(new Vector3D(2, -1, 3), new Vector3D(2, 0, 1), 7)
        );
    }

    @ParameterizedTest
    @MethodSource("scalarProduct3DTestsProvider")
    void scalarProductTests(Vector3D v1, Vector3D v2, double expected) {
        double actual = scalarProduct(v1, v2);
        assertEquals(expected, actual, 0.01d);
    }


    private static Stream<Arguments> orthoBaseTestsProvider() {
        return Stream.of(
                Arguments.of(new Vector3D(1, 0, 0)),
                Arguments.of(new Vector3D(0, 1, 0)),
                Arguments.of(new Vector3D(0, 0, 1)),
                Arguments.of(new Vector3D(1, 1, 1)),
                Arguments.of(new Vector3D(1, 1, 0)),
                Arguments.of(new Vector3D(0, 1, 1)),
                Arguments.of(new Vector3D(1, 0, 1)),
                Arguments.of(new Vector3D(1, 2, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("orthoBaseTestsProvider")
    void orthogonalBaseFromVectorTest(Vector3D v) {
        Tuple<Vector3D, Vector3D> base = orthogonalBase(v);

//        System.out.println(v);
//        System.out.println(base.t1());
//        System.out.println(base.t2());


        assertEquals(0, scalarProduct(base.t1(), base.t2()), 0.01d, format("\nfrom v: %s,\nv1: %s,\nv2:%s\n", v, base.t1(), base.t2()));
        assertEquals(0, scalarProduct(v, base.t2()), 0.01d);
        assertEquals(0, scalarProduct(base.t1(), v), 0.01d);
    }

    private static Stream<Arguments> planeIntersectionValidTestsProvider() {
        return Stream.of(
                Arguments.of(new Tuple<>(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0)), new Vector3D(0, 0, 5), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(0, 0, 1), Color.BLACK)),
                Arguments.of(new Tuple<>(new Vector3D(0, 1, 0), new Vector3D(1, 0, 0)), new Vector3D(0, 0, 5), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(0, 0, 1), Color.BLACK)),
                Arguments.of(new Tuple<>(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1)), new Vector3D(5, 0, 0), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(1, 0, 0), Color.BLACK)),
                Arguments.of(new Tuple<>(new Vector3D(0, 0, 1), new Vector3D(1, 0, 0)), new Vector3D(0, 5, 0), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(0, 1, 0), Color.BLACK)),
                Arguments.of(new Tuple<>(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0)), new Vector3D(0, 0, 5), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(0, 0, -1), Color.BLACK)),
                Arguments.of(new Tuple<>(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0)), new Vector3D(0, 0, 5), new Ray<>(new Vector3D(0, 0, 0), new Vector3D(0, 0, 1), Color.BLACK))
        );
    }

    @ParameterizedTest
    @MethodSource("planeIntersectionValidTestsProvider")
    void intersectPlaneValidTest(Tuple<Vector3D, Vector3D> plane, Vector3D origin, Ray<Vector3D> ray) {
        Tuple3<Double, Double, Double> actual = intersection(plane, origin, ray);

        double t = actual.t1();
        double lambda1 = actual.t2();
        double lambda2 = actual.t3();

        assertFalse(isNaN(t));
        assertFalse(isNaN(lambda1));
        assertFalse(isNaN(lambda2));

        Vector3D rayArrived = ray.pos().translate(ray.dir(), t);
        Vector3D planePosition = origin.translate(plane.t1(), lambda1).translate(plane.t2(), lambda2);

        assertVectors(rayArrived, planePosition);
    }

    private static Stream<Arguments> determinantTestsProvider() {
        return Stream.of(
                Arguments.of(new Double[][] {
                        {1d, 0d}, {0d, 1d}
                }, 1d),
                Arguments.of(new Double[][] {
                        {0d, 1d}, {1d, 0d}
                }, -1d),
                Arguments.of(new Double[][] {
                        {1d, 1d}, {1d, 1d}
                }, 0d),
                Arguments.of(new Double[][] {
                        {1d, 2d}, {3d, 4d}
                }, -2d),
                Arguments.of(new Double[][] {
                        {1d, 0d, 0d}, {0d, 1d, 0d}, {0d, 0d, 1d}
                }, 1d),
                Arguments.of(new Double[][] {
                        {1d, 5d, 3d}, {2d, 4d, 7d}, {4d, 6d, 2d}
                }, 74d)
        );
    }

    @ParameterizedTest
    @MethodSource("determinantTestsProvider")
    void determinantValidTest(Double[][] m, Double d) {
        assertEquals(d, determinant(m), 0.01d);
    }





    private void assertVectors(Vector2D v1, Vector2D v2) {
        assertEquals(v1.x(), v2.x(), 0.01d);
        assertEquals(v1.y(), v2.y(), 0.01d);
    }

    private void assertVectors(Vector3D v1, Vector3D v2) {
        assertEquals(v1.x(), v2.x(), 0.01d);
        assertEquals(v1.y(), v2.y(), 0.01d);
        assertEquals(v1.z(), v2.z(), 0.01d);
    }
}