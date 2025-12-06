// ========== Vector.java ==========
package fr.raytracer.geometry;

/**
 * Représente un vecteur 3D avec opérations usuelles (addition, produit scalaire, normalisation, etc).
 */
public class Vector extends AbstractVec3 {
    
    /**
     * Crée un vecteur 3D.
     * @param x composante x
     * @param y composante y
     * @param z composante z
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Additionne ce vecteur à un autre.
     * @param other le vecteur à additionner
     * @return le résultat de l'addition
     */
    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Soustrait un vecteur à ce vecteur.
     * @param other le vecteur à soustraire
     * @return le résultat de la soustraction
     */
    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Multiplie ce vecteur par un scalaire.
     * @param scalar le scalaire
     * @return le vecteur résultant
     */
    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Produit scalaire avec un autre vecteur.
     * @param other le vecteur
     * @return le résultat du produit scalaire
     */
    public double dot(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Produit vectoriel avec un autre vecteur.
     * @param other le vecteur
     * @return le résultat du produit vectoriel
     */
    public Vector cross(Vector other) {
        return new Vector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }

    /**
     * Normalise ce vecteur (longueur 1).
     * @return le vecteur normalisé
     */
    public Vector normalize() {
        double len = length();
        if (len == 0) return new Vector(0, 0, 0);
        return new Vector(x / len, y / len, z / len);
    }

    /**
     * Retourne l'opposé de ce vecteur.
     * @return le vecteur opposé
     */
    public Vector negate() {
        return new Vector(-x, -y, -z);
    }
}