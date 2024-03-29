package org.goblivend.rayxploring.Utils;

public interface Vector<T extends Vector<T>> {
    T translate(T dir, double time);
    double hypot();
}
