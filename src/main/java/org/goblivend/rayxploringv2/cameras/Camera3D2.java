package org.goblivend.rayxploringv2.cameras;

public class Camera3D2 {//extends Camera<Vector3D> implements Component<Vector3D> {
//    private final Vector3D center;
//    private final Vector3D dir;
//    private final Vector2D size;
//
//    private final Double RADIUS = 2d;
//
//    public Camera3D2(Vector3D center, Vector3D dir, Vector2D size, int width, int height) {
//        super(width, height);
//        this.center = center;
//        this.dir = dir;
//        this.size = size;
//    }
//
//    public Optional<Vector3D> getHitPoint(Ray<Vector3D> ray) {
//        Double t1 = interceptSphere(center, RADIUS, ray);
//
//        if (Double.isNaN(t1))
//            return Optional.empty();
//
//        if (t1 < 0) {
//            throw new ArithmeticException();
//        }
//
//        return Optional.of(ray.pos().translate(ray.dir(), t1));
//    }
//
//    private Point getIndexes(Vector3D hitPoint, Vector3D dir) {
////        Vector3D Zaxis = new Vector3D(hitPoint.x() - center.x(), hitPoint.y() - center.y(), hitPoint.z() - center.z()).normalized();
////        Tuple<Vector3D, Vector3D> XYaxis = orthogonalBase(Zaxis);
////
////        double[][] transition = transitionMatrix(XYaxis.t1(), XYaxis.t2(), Zaxis);
////
////        double[][] dirNewBase = matMul(transpose(transition), dir.reverse().toMatrix());
////
////        Vector2D dirAngles = vector3dToAngles(new Vector3D(dirNewBase));
////        Vector3D rnewBase = anglesToVector3d(dirAngles.plus(new Vector2D(Math.PI, 0)));
////
////        Vector3D rebounded = new Vector3D(matMul(transition, rnewBase.toMatrix()));
////
//
//
//
//
////
////        int x = (int) ((hitPoint.y() - minY) / size.x() * width);
////        int y = height - (int) ((hitPoint.z() - minZ) / size.y() * height);
//
////        return new Point(x, y);
//        return new Point();
//    }
//
//    @Override
//    public Ray<Vector3D> rebound(Ray<Vector3D> ray) {
//        Optional<Vector3D> hitPoint = getHitPoint(ray);
//
//        if (hitPoint.isEmpty())
//            return ray;
//
//        Point p = getIndexes(hitPoint.get(), ray.dir());
//
//        if (p.x < 0 || width <= p.x
//            || p.y < 0 || height <= p.y)
//            return ray;
//
//        img[p.y][p.x].add(ray.color());
//
//        return null;
//    }
//
//    @Override
//    public void trace(Ray<Vector3D> ray, Optional<Vector3D> limit) {
//    }
//
//    @Override
//    public Double intercept(Ray<Vector3D> ray) {
//        Double t = interceptSphere(center, RADIUS, ray);
//
//        if (t.isNaN() || t < 0)
//            return null;
//
//        Point p = getIndexes(ray.pos().translate(ray.dir(), t), ray.dir());
//
//        if (p.x < 0 || width <= p.x
//                || p.y < 0 || height <= p.y)
//            return null;
//
//        return t;
//    }
}
