
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import java.util.Optional;

/**
 * Représente un triangle 3D défini par trois sommets.
 */
public class Triangle extends Shape {
    /** Sommet 0. */
    private Point v0;
    /** Sommet 1. */
    private Point v1;
    /** Sommet 2. */
    private Point v2;
    /** Normale précalculée du triangle. */
    private Vector normal;

    /**
     * Crée un triangle.
     * @param v0 premier sommet
     * @param v1 deuxième sommet
     * @param v2 troisième sommet
     * @param diffuse couleur diffuse
     * @param specular couleur spéculaire
     * @param shininess brillance
     */
    public Triangle(Point v0, Point v1, Point v2, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        
        // Précalculer la normale du triangle
        Vector edge1 = v1.subtract(v0);
        Vector edge2 = v2.subtract(v0);
        this.normal = edge1.cross(edge2).normalize();
    }

    /**
     * Calcule l'intersection avec un rayon (algorithme de Möller-Trumbore).
     * @param ray le rayon
     * @return l'intersection si elle existe
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        final double EPSILON = 1e-8;
        
        Vector edge1 = v1.subtract(v0);
        Vector edge2 = v2.subtract(v0);
        
        Vector h = ray.getDirection().cross(edge2);
        double a = edge1.dot(h);
        
        // Le rayon est parallèle au triangle
        if (Math.abs(a) < EPSILON) {
            return Optional.empty();
        }
        
        double f = 1.0 / a;
        Vector s = ray.getOrigin().subtract(v0);
        double u = f * s.dot(h);
        
        // Le point d'intersection est en dehors du triangle
        if (u < 0.0 || u > 1.0) {
            return Optional.empty();
        }
        
        Vector q = s.cross(edge1);
        double v = f * ray.getDirection().dot(q);
        
        // Le point d'intersection est en dehors du triangle
        if (v < 0.0 || u + v > 1.0) {
            return Optional.empty();
        }
        
        // Calculer t pour trouver le point d'intersection
        double t = f * edge2.dot(q);
        
        // Intersection valide seulement si t > epsilon
        if (t > EPSILON) {
            Point hitPoint = ray.pointAt(t);
            return Optional.of(new Intersection(t, hitPoint, normal, this));
        }
        
        return Optional.empty();
    }

    /**
     * Retourne la normale du triangle (constante sur toute la surface).
     * @param point le point (ignoré)
     * @return la normale
     */
    @Override
    public Vector getNormalAt(Point point) {
        return normal;
    }
}
