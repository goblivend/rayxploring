package org.goblivend.rayxploringv2.Utils;

public interface Vector<T extends Vector<T>> {
    T translate(T dir, double time);
    double hypot();
    T reverse();
    T normalized();
    T plus(T v);
    double[][] toMatrix();
}
