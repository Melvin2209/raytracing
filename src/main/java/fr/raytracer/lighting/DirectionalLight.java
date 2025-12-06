
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

/**
 * Représente une lumière directionnelle (comme le soleil).
 */
public class DirectionalLight extends AbstractLight {
    /** Direction de la lumière. */
    private Vector direction;

    /**
     * Crée une lumière directionnelle.
     * @param direction la direction de la lumière
     * @param color la couleur de la lumière
     */
    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction.normalize();
    }

    /**
     * Retourne la direction vers la lumière (opposée à la direction de propagation).
     * @param point le point (ignoré pour une lumière directionnelle)
     * @return la direction
     */
    @Override
    public Vector getDirectionFrom(Point point) {
        return direction.negate();
    }

    /**
     * Retourne la distance à la lumière (infini pour une lumière directionnelle).
     * @param point le point (ignoré)
     * @return l'infini positif
     */
    @Override
    public double getDistanceFrom(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
