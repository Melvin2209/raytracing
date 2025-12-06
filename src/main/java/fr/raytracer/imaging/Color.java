
package fr.raytracer.imaging;

/**
 * Représente une couleur RGB avec des composantes entre 0 et 1.
 */
public class Color {
    /** Composante rouge. */
    private double r;
    /** Composante verte. */
    private double g;
    /** Composante bleue. */
    private double b;

    /**
     * Crée une couleur noire (0, 0, 0).
     */
    public Color() {
        this(0, 0, 0);
    }

    /**
     * Crée une couleur avec les composantes données.
     * @param r rouge (0-1)
     * @param g vert (0-1)
     * @param b bleu (0-1)
     */
    public Color(double r, double g, double b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    /**
     * Clampe une valeur entre 0 et 1.
     * @param value la valeur
     * @return la valeur clampée
     */
    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    /**
     * Retourne la composante rouge.
     * @return r
     */
    public double getR() { return r; }

    /**
     * Retourne la composante verte.
     * @return g
     */
    public double getG() { return g; }

    /**
     * Retourne la composante bleue.
     * @return b
     */
    public double getB() { return b; }

    /**
     * Additionne cette couleur à une autre.
     * @param other l'autre couleur
     * @return la couleur résultante
     */
    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    /**
     * Multiplie cette couleur par un scalaire.
     * @param scalar le scalaire
     * @return la couleur résultante
     */
    public Color multiply(double scalar) {
        return new Color(r * scalar, g * scalar, b * scalar);
    }

    /**
     * Multiplie cette couleur par une autre (composante par composante).
     * @param other l'autre couleur
     * @return la couleur résultante
     */
    public Color multiply(Color other) {
        return new Color(r * other.r, g * other.g, b * other.b);
    }

    /**
     * Convertit la couleur en entier RGB (format 0xRRGGBB).
     * @return la valeur RGB
     */
    public int toRGB() {
        int red = (int) Math.round(r * 255);
        int green = (int) Math.round(g * 255);
        int blue = (int) Math.round(b * 255);
        return ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }

    /**
     * Retourne une représentation textuelle de la couleur.
     * @return la chaîne formatée
     */
    @Override
    public String toString() {
        return String.format("Color(%.3f, %.3f, %.3f)", r, g, b);
    }
}