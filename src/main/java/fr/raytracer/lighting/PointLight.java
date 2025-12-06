
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

public class PointLight extends AbstractLight {
    private Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    @Override
    public Vector getDirectionFrom(Point point) {
        return position.subtract(point).normalize();
    }

    @Override
    public double getDistanceFrom(Point point) {
        return position.subtract(point).length();
    }
}