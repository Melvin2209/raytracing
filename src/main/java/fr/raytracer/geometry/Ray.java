
package fr.raytracer.geometry;

public class Ray {
    private Point origin;
    private Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    public Point getOrigin() { return origin; }
    public Vector getDirection() { return direction; }

    public Point pointAt(double t) {
        return origin.add(direction.multiply(t));
    }
}