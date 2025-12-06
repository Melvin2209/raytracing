package fr.raytracer.geometry;

/**
 * Représente un point 3D dans l'espace.
 * Hérite d'AbstractVec3.
 */
public class Point extends AbstractVec3 {
    
    /**
     * Crée un point 3D.
     * @param x abscisse
     * @param y ordonnée
     * @param z cote
     */
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Retourne le vecteur allant d'un autre point vers ce point.
     * @param other l'autre point
     * @return le vecteur résultant
     */
    public Vector subtract(Point other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Déplace ce point selon un vecteur.
     * @param vector le vecteur de déplacement
     * @return le nouveau point
     */
    public Point add(Vector vector) {
        return new Point(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }
}
