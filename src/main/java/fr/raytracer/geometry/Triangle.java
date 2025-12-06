
package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import java.util.Optional;

public class Triangle extends Shape {
    private Point v0, v1, v2;
    private Vector normal;

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

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        // Algorithme de Möller-Trumbore pour l'intersection rayon-triangle
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
        
        // Intersection valide seulement si t > epsilon (pas derrière la caméra)
        if (t > EPSILON) {
            Point hitPoint = ray.pointAt(t);
            return Optional.of(new Intersection(t, hitPoint, normal, this));
        }
        
        return Optional.empty();
    }

    @Override
    public Vector getNormalAt(Point point) {
        return normal;
    }
}
