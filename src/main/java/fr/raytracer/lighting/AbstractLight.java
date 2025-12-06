
package fr.raytracer.lighting;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

/**
 * Classe abstraite représentant une source de lumière.
 */
public abstract class AbstractLight {
    /** Couleur de la lumière. */
    protected Color color;

    /**
     * Crée une lumière avec une couleur donnée.
     * @param color la couleur de la lumière
     */
    public AbstractLight(Color color) {
        this.color = color;
    }

    /**
     * Retourne la couleur de la lumière.
     * @return la couleur
     */
    public Color getColor() { return color; }

    /**
     * Retourne la direction vers la lumière depuis un point.
     * @param point le point
     * @return la direction normalisée
     */
    public abstract Vector getDirectionFrom(Point point);

    /**
     * Retourne la distance à la lumière depuis un point.
     * @param point le point
     * @return la distance
     */
    public abstract double getDistanceFrom(Point point);
}