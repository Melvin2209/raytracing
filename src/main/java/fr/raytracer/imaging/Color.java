
package fr.raytracer.imaging;

public class Color {
    private double r, g, b;

    public Color() {
        this(0, 0, 0);
    }

    public Color(double r, double g, double b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    public double getR() { return r; }
    public double getG() { return g; }
    public double getB() { return b; }

    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    public Color multiply(double scalar) {
        return new Color(r * scalar, g * scalar, b * scalar);
    }

    public Color multiply(Color other) {
        return new Color(r * other.r, g * other.g, b * other.b);
    }

    public int toRGB() {
        int red = (int) Math.round(r * 255);
        int green = (int) Math.round(g * 255);
        int blue = (int) Math.round(b * 255);
        return ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }

    @Override
    public String toString() {
        return String.format("Color(%.3f, %.3f, %.3f)", r, g, b);
    }
}