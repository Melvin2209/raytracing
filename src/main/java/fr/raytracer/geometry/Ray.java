
package fr.raytracer.geometry;

/**
 * Représente un rayon 3D avec une origine et une direction normalisée.
 */
public class Ray {
    /** Origine du rayon. */
    private Point origin;
    /** Direction normalisée du rayon. */
    private Vector direction;

    /**
     * Crée un rayon.
     * @param origin l'origine du rayon
     * @param direction la direction du rayon (sera normalisée)
     */
    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction.normalize();
    }

    /**
     * Retourne l'origine du rayon.
     * @return l'origine
     */
    public Point getOrigin() { return origin; }

    /**
     * Retourne la direction normalisée du rayon.
     * @return la direction
     */
    public Vector getDirection() { return direction; }

    /**
     * Retourne le point à la distance t le long du rayon.
     * @param t la distance
     * @return le point correspondant
     */
    public Point pointAt(double t) {
        return origin.add(direction.multiply(t));
    }
}