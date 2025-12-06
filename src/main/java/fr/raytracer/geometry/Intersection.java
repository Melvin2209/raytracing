
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;

public class Intersection {
    private double distance;
    private Point point;
    private Vector normal;
    private Shape shape;

    public Intersection(double distance, Point point, Vector normal, Shape shape) {
        this.distance = distance;
        this.point = point;
        this.normal = normal;
        this.shape = shape;
    }

    public double getDistance() { return distance; }
    public Point getPoint() { return point; }
    public Vector getNormal() { return normal; }
    public Shape getShape() { return shape; }
}