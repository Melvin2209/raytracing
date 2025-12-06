package fr.raytracer.geometry;

public abstract class AbstractVec3 {
    protected double x, y, z;
    private static final double EPSILON = 1e-10;

    public AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractVec3)) return false;
        AbstractVec3 other = (AbstractVec3) obj;
        return Math.abs(x - other.x) < EPSILON &&
               Math.abs(y - other.y) < EPSILON &&
               Math.abs(z - other.z) < EPSILON;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}
