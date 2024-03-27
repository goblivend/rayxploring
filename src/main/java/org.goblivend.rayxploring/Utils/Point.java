package org.goblivend.rayxploring.Utils;

public interface Point<T extends Point<T>> {

    T translate(T dir, double time);
}
