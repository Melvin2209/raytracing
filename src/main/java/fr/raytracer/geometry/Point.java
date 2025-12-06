package fr.raytracer.geometry;

public class Point extends AbstractVec3 {
    
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector subtract(Point other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    public Point add(Vector vector) {
        return new Point(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }
}
