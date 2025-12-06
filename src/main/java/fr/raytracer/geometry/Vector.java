// ========== Vector.java ==========
package fr.raytracer.geometry;

public class Vector extends AbstractVec3 {
    
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector cross(Vector other) {
        return new Vector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }

    public Vector normalize() {
        double len = length();
        if (len == 0) return new Vector(0, 0, 0);
        return new Vector(x / len, y / len, z / len);
    }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }
}