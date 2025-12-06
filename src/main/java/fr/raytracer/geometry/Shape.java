
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import java.util.Optional;

/**
 * Classe abstraite représentant une forme géométrique.
 */
public abstract class Shape {
    /** Couleur diffuse de la forme. */
    protected Color diffuse;
    /** Couleur spéculaire de la forme. */
    protected Color specular;
    /** Brillance (shininess) de la forme. */
    protected double shininess;

    /**
     * Crée une forme avec les propriétés de matériau.
     * @param diffuse la couleur diffuse
     * @param specular la couleur spéculaire
     * @param shininess la brillance
     */
    public Shape(Color diffuse, Color specular, double shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    /**
     * Calcule l'intersection avec un rayon.
     * @param ray le rayon
     * @return l'intersection si elle existe
     */
    public abstract Optional<Intersection> intersect(Ray ray);

    /**
     * Retourne la normale à la surface en un point donné.
     * @param point le point
     * @return la normale
     */
    public abstract Vector getNormalAt(Point point);

    /**
     * Retourne la couleur diffuse.
     * @return la couleur diffuse
     */
    public Color getDiffuse() { return diffuse; }

    /**
     * Retourne la couleur spéculaire.
     * @return la couleur spéculaire
     */
    public Color getSpecular() { return specular; }

    /**
     * Retourne la brillance.
     * @return la brillance
     */
    public double getShininess() { return shininess; }
}