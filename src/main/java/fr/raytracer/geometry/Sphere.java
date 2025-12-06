
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import java.util.Optional;

/**
 * Représente une sphère 3D.
 */
public class Sphere extends Shape {
    /** Centre de la sphère. */
    private Point center;
    /** Rayon de la sphère. */
    private double radius;

    /**
     * Crée une sphère.
     * @param center le centre
     * @param radius le rayon
     * @param diffuse la couleur diffuse
     * @param specular la couleur spéculaire
     * @param shininess la brillance
     */
    public Sphere(Point center, double radius, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess);
        this.center = center;
        this.radius = radius;
    }

    /**
     * Calcule l'intersection avec un rayon.
     * @param ray le rayon
     * @return l'intersection si elle existe
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Vector oc = ray.getOrigin().subtract(center);
        double a = ray.getDirection().dot(ray.getDirection());
        double b = 2.0 * oc.dot(ray.getDirection());
        double c = oc.dot(oc) - radius * radius;
        
        double discriminant = b * b - 4 * a * c;
        
        if (discriminant < 0) {
            return Optional.empty();
        }
        
        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        
        double t;
        if (t1 > 1e-4) {
            t = t1;
        } else if (t2 > 1e-4) {
            t = t2;
        } else {
            return Optional.empty();
        }
        
        Point hitPoint = ray.pointAt(t);
        Vector normal = getNormalAt(hitPoint);
        
        return Optional.of(new Intersection(t, hitPoint, normal, this));
    }

    /**
     * Retourne la normale à la surface de la sphère.
     * @param point le point
     * @return la normale
     */
    @Override
    public Vector getNormalAt(Point point) {
        Vector normal = point.subtract(center);
        return normal.normalize();
    }
}
