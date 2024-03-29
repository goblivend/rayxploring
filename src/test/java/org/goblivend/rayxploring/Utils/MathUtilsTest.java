package org.goblivend.rayxploring.Utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Double.NaN;
import static java.lang.Math.PI;
import static java.lang.String.format;
import static org.goblivend.rayxploring.Utils.MathUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    private static Stream<Arguments> angle2DTestsProvider() {
        return Stream.of(
                Arguments.of(0d, new Vector2D(1, 0)),
                Arguments.of(PI/2, new Vector2D(0, 1)),
                Arguments.of(PI, new Vector2D(-1, 0)),
                Arguments.of(-PI/2, new Vector2D(0, -1)),
                Arguments.of(PI/4, new Vector2D(0.7, 0.7)),
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
        assertEquals(expected.x(), actual.x(), 0.01d);
        assertEquals(expected.y(), actual.y(), 0.01d);
    }

    private static Stream<Arguments> angle3DTestsProvider() {
        return Stream.of(
                Arguments.of(new Vector2D(0, PI/2), new Vector3D(1, 0, 0)),
                Arguments.of(new Vector2D(0, 0), new Vector3D(0, 0, 1)),
                Arguments.of(new Vector2D(PI/2, PI/2), new Vector3D(0, 1, 0)),
                Arguments.of(new Vector2D(PI, PI/2), new Vector3D(-1, 0, 0)),
                Arguments.of(new Vector2D(PI/4, PI/2), new Vector3D(0.7, 0.7, 0)),
                Arguments.of(new Vector2D(0, PI), new Vector3D(0, 0, -1))
        );
    }

    @ParameterizedTest
    @MethodSource("angle3DTestsProvider")
    void point3DToAngleTests(Vector2D expected, Vector3D point) {
        Vector2D actual = vector3dToAngles(point);
        assertEquals(expected.x(), actual.x(), 0.01d);
        assertEquals(expected.y(), actual.y(), 0.01d);
    }
    @ParameterizedTest
    @MethodSource("angle3DTestsProvider")
    void angleToPoint3DTests(Vector2D angle, Vector3D expected) {
        Vector3D actual = anglesToVector3d(angle);
        assertEquals(expected.x(), actual.x(), 0.01d);
        assertEquals(expected.y(), actual.y(), 0.01d);
        assertEquals(expected.z(), actual.z(), 0.01d);
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
}