package fr.raytracer.geometry;

/**
 * Classe abstraite représentant un vecteur ou point 3D.
 * Fournit les coordonnées x, y, z et des méthodes utilitaires.
 */
public abstract class AbstractVec3 {
    /** Composante x. */
    protected double x;
    /** Composante y. */
    protected double y;
    /** Composante z. */
    protected double z;
    /** Epsilon pour la comparaison. */
    private static final double EPSILON = 1e-10;

    /**
     * Crée un vecteur ou point 3D.
     * @param x composante x
     * @param y composante y
     * @param z composante z
     */
    public AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Retourne la composante x.
     * @return x
     */
    public double getX() { return x; }

    /**
     * Retourne la composante y.
     * @return y
     */
    public double getY() { return y; }

    /**
     * Retourne la composante z.
     * @return z
     */
    public double getZ() { return z; }

    /**
     * Retourne la longueur (norme) du vecteur.
     * @return la longueur
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Vérifie l'égalité avec un autre objet (à epsilon près).
     * @param obj l'objet à comparer
     * @return true si égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractVec3)) return false;
        AbstractVec3 other = (AbstractVec3) obj;
        return Math.abs(x - other.x) < EPSILON &&
               Math.abs(y - other.y) < EPSILON &&
               Math.abs(z - other.z) < EPSILON;
    }

    /**
     * Retourne une représentation textuelle du vecteur.
     * @return la chaîne formatée
     */
    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}
