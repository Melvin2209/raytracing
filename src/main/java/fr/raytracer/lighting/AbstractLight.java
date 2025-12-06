
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

public abstract class AbstractLight {
    protected Color color;

    public AbstractLight(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }
    public abstract Vector getDirectionFrom(Point point);
    public abstract double getDistanceFrom(Point point);
}