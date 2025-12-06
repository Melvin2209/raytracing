
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;

/**
 * Représente une intersection entre un rayon et une forme.
 */
public class Intersection {
    /** Distance de l'intersection depuis l'origine du rayon. */
    private double distance;
    /** Point d'intersection. */
    private Point point;
    /** Normale à la surface au point d'intersection. */
    private Vector normal;
    /** Forme intersectée. */
    private Shape shape;

    /**
     * Crée une intersection.
     * @param distance la distance depuis l'origine du rayon
     * @param point le point d'intersection
     * @param normal la normale à la surface
     * @param shape la forme intersectée
     */
    public Intersection(double distance, Point point, Vector normal, Shape shape) {
        this.distance = distance;
        this.point = point;
        this.normal = normal;
        this.shape = shape;
    }

    /**
     * Retourne la distance de l'intersection.
     * @return la distance
     */
    public double getDistance() { return distance; }

    /**
     * Retourne le point d'intersection.
     * @return le point
     */
    public Point getPoint() { return point; }

    /**
     * Retourne la normale à la surface.
     * @return la normale
     */
    public Vector getNormal() { return normal; }

    /**
     * Retourne la forme intersectée.
     * @return la forme
     */
    public Shape getShape() { return shape; }
}