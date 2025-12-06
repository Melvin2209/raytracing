
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

public class DirectionalLight extends AbstractLight {
    private Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction.normalize();
    }

    @Override
    public Vector getDirectionFrom(Point point) {
        return direction.negate();
    }

    @Override
    public double getDistanceFrom(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
