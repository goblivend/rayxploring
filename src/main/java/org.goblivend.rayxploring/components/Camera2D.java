package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector2D;

import java.util.Optional;
import java.awt.*;
import java.util.function.Predicate;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Camera2D extends Camera<Vector2D> {
    private final Vector2D center;
    private final Vector2D size;

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public Camera2D(Vector2D center, Vector2D size, int width, int height) {
        super(width, height);
        this.center = center;
        this.size = size;

        minX = center.x() - size.x() / 2;
        minY = center.y() - size.y() / 2;
        maxX = center.x() + size.x() / 2;
        maxY = center.y() + size.y() / 2;
    }

    private Point getIndexes(Vector2D pos) {
        int x = (int) ((pos.x() - minX) / size.x() * width);
        int y = height - (int) ((pos.y() - minY) / size.y() * height);

        return new Point(x, y);
    }

    @Override
    public void record(Ray<Vector2D> ray) {
        Point p = getIndexes(ray.pos());

        if (p.x < 0 || width <= p.x
            || p.y < 0 || height <= p.y)
            return;

        img[p.y][p.x].add(ray.color());
    }

    private boolean inFrame(Vector2D pos, Vector2D dir) {
        if ((dir.x() == 0 && (pos.x() < minX || maxX <= pos.x())) ||
                (dir.y() == 0 && (pos.y() < minY || maxY <= pos.y())))
            return false;

        double tx1 = (minX - pos.x()) / dir.x();
        double tx2 = (maxX - pos.x()) / dir.x();

        double ty1 = (minY - pos.y()) / dir.y();
        double ty2 = (maxY - pos.y()) / dir.y();

        return max(tx1, tx2) >= min(ty1, ty2) && max(ty1, ty2) >= min(tx1, tx2);
    }

    private static Color traceColor(Color color, double dist) {
        float distMult =  (float) max(0, (5 - dist) / 5) / 255 / 2;
        return new Color(
                ((float) color.getRed())   * distMult,
                ((float) color.getGreen()) * distMult,
                ((float) color.getBlue())  * distMult);
    }

    @Override
    public void trace(Ray<Vector2D> ray, Optional<Vector2D> limit) {
        if (! inFrame(ray.pos(), ray.dir()))
            return;

        Vector2D dir = ray.dir();

        Point diridxs = getIndexes(dir);
        double hypot = Math.hypot(diridxs.x, diridxs.y);
        if (hypot > 1) {
            dir = new Vector2D(dir.x() / hypot, dir.y() / hypot);
        }

        Vector2D pos = ray.pos();
        while (!inFrame(pos, new Vector2D(0, 0)))
            pos = pos.translate(dir, 1);

        Predicate<Vector2D> cmp = null;

        if (limit.isPresent()) {
            cmp = pos.x() < limit.get().x()
                    ? (p) -> p.x() >= limit.get().x()
                    : (p) -> p.x() < limit.get().x();
        }

        while (inFrame(pos, new Vector2D(0, 0))) {
            if (limit.isPresent() && cmp.test(pos))
                break;

            Point p = getIndexes(pos);

            if (p.x < 0 || width <= p.x
                    || p.y < 0 || height <= p.y)
                break;

            img[p.y][p.x].add(traceColor(ray.color(), Math.hypot(pos.x() - ray.pos().x(), pos.y() - ray.pos().y())));
            pos = pos.translate(dir, 1);
        }
    }
}
