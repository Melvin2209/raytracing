package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import java.util.Optional;

/**
 * Représente un plan infini dans l'espace 3D.
 * Un plan est défini par un point et une normale.
 */
public class Plane extends Shape {
    /** Point sur le plan. */
    private Point point;
    /** Normale du plan. */
    private Vector normal;

    /**
     * Crée un plan.
     * @param point un point sur le plan
     * @param normal la normale du plan (sera normalisée)
     * @param diffuse la couleur diffuse
     * @param specular la couleur spéculaire
     * @param shininess l'exposant de brillance
     */
    public Plane(Point point, Vector normal, Color diffuse, Color specular, double shininess) {
        super(diffuse, specular, shininess);
        this.point = point;
        this.normal = normal.normalize();
    }

    /**
     * Calcule l'intersection entre un rayon et ce plan.
     * Utilise la formule : t = (point - origin) · normal / (direction · normal)
     * @param ray le rayon
     * @return l'intersection si elle existe
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        double denom = normal.dot(ray.getDirection());
        
        // Si le rayon est parallèle au plan (ou presque)
        if (Math.abs(denom) < 1e-6) {
            return Optional.empty();
        }
        
        // Calculer le vecteur de l'origine du rayon au point du plan
        Vector diff = new Vector(
            point.getX() - ray.getOrigin().getX(),
            point.getY() - ray.getOrigin().getY(),
            point.getZ() - ray.getOrigin().getZ()
        );
        
        double t = diff.dot(normal) / denom;
        
        // L'intersection doit être devant le rayon
        if (t < 1e-4) {
            return Optional.empty();
        }
        
        Point hitPoint = ray.pointAt(t);
        
        // La normale pointe toujours vers le rayon
        Vector hitNormal = denom < 0 ? normal : normal.negate();
        
        return Optional.of(new Intersection(t, hitPoint, hitNormal, this));
    }

    /**
     * Retourne la normale du plan.
     * @param point le point (ignoré pour un plan)
     * @return la normale
     */
    @Override
    public Vector getNormalAt(Point point) {
        return normal;
    }

    /**
     * Retourne le point définissant le plan.
     * @return le point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Retourne la normale du plan.
     * @return la normale
     */
    public Vector getNormalVector() {
        return normal;
    }
}
