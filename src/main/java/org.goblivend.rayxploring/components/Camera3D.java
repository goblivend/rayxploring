package org.goblivend.rayxploring.components;

import org.goblivend.rayxploring.Utils.Vector2D;
import org.goblivend.rayxploring.Utils.Vector3D;

import java.awt.*;
import java.util.Optional;

// TODO : Use new maths to take into account directions other than (x, 0, 0)
public class Camera3D extends Camera<Vector3D> implements Component<Vector3D> {
    private final Vector3D center;
    private final Vector3D dir;
    private final Vector2D size;

    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public Camera3D(Vector3D center, Vector3D dir, Vector2D size, int width, int height) {
        super(width, height);
        this.center = center;
        this.dir = dir;
        this.size = size;

        minY = center.y() - size.x() / 2;
        maxY = minY + size.x();
        minZ = center.z() - size.y() / 2;
        maxZ = minZ + size.y();
    }

    private Optional<Vector3D> getHitPoint(Ray<Vector3D> ray) {
        double tx = (center.x() - ray.pos().x()) / ray.dir().x();
        if (tx < 0){
            System.out.printf("Ray: Going backward %s\n", ray);
            return Optional.empty();
        }

        double x = ray.pos().x() + ray.dir().x() * tx; // center.x() but cleaner
        double y = ray.pos().y() + ray.dir().y() * tx;
        double z = ray.pos().z() + ray.dir().z() * tx;

        if (y < minY || maxY <= y
            || z < minZ || maxZ <= z) {
            System.out.printf("Ray: Missing Camera %s %s, : %s\n", size, new Vector2D(y, z), ray);
            return Optional.empty();
        }

        return Optional.of(new Vector3D(x, y, z));
    }

    private Point getIndexes(Vector3D hitPoint) {
        int x = (int) ((hitPoint.y() - minY) / size.x() * width);
        int y = height - (int) ((hitPoint.z() - minZ) / size.y() * height);

        return new Point(x, y);
    }

    @Override
    public void record(Ray<Vector3D> ray) {
        Optional<Vector3D> hitPoint = getHitPoint(ray);

        if (hitPoint.isEmpty())
            return;

        Point p = getIndexes(hitPoint.get());

        if (p.x < 0 || width <= p.x
            || p.y < 0 || height <= p.y)
            return;

        img[p.y][p.x].add(ray.color());
    }

    @Override
    public void trace(Ray<Vector3D> ray, Optional<Vector3D> limit) {
    }

    @Override
    public Double intercept(Ray<Vector3D> ray) {
        double tx = (center.x() - ray.pos().x()) / ray.dir().x();
        if (tx < 0){
            System.out.printf("Ray: Going backward %s\n", ray);
            return null;
        }
        return tx;
    }
}
