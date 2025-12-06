
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

/**
 * Représente une lumière ponctuelle.
 */
public class PointLight extends AbstractLight {
    /** Position de la lumière. */
    private Point position;

    /**
     * Crée une lumière ponctuelle.
     * @param position la position de la lumière
     * @param color la couleur de la lumière
     */
    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    /**
     * Retourne la direction vers la lumière depuis un point.
     * @param point le point
     * @return la direction normalisée
     */
    @Override
    public Vector getDirectionFrom(Point point) {
        return position.subtract(point).normalize();
    }

    /**
     * Retourne la distance à la lumière depuis un point.
     * @param point le point
     * @return la distance
     */
    @Override
    public double getDistanceFrom(Point point) {
        return position.subtract(point).length();
    }
}