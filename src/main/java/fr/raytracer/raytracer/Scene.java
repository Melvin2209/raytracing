
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.*;
import fr.raytracer.lighting.AbstractLight;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Représente une scène 3D avec caméra, lumières et formes.
 */
public class Scene {
    /** Largeur de l'image. */
    private int width;
    /** Hauteur de l'image. */
    private int height;
    /** Caméra de la scène. */
    private Camera camera;
    /** Fichier de sortie. */
    private String output = "output.png";
    /** Couleur ambiante. */
    private Color ambient = new Color();
    /** Liste des lumières. */
    private List<AbstractLight> lights = new ArrayList<>();
    /** Liste des formes. */
    private List<Shape> shapes = new ArrayList<>();
    /** Activation des réflexions. */
    private boolean reflectionEnabled = false;

    /**
     * Crée une scène.
     * @param width largeur de l'image
     * @param height hauteur de l'image
     * @param camera la caméra
     */
    public Scene(int width, int height, Camera camera) {
        this.width = width;
        this.height = height;
        this.camera = camera;
    }

    /** @return la largeur */
    public int getWidth() { return width; }
    /** @return la hauteur */
    public int getHeight() { return height; }
    /** @return la caméra */
    public Camera getCamera() { return camera; }
    /** @return le fichier de sortie */
    public String getOutput() { return output; }
    /** @return la couleur ambiante */
    public Color getAmbient() { return ambient; }
    /** @return la liste des lumières */
    public List<AbstractLight> getLights() { return lights; }
    /** @return la liste des formes */
    public List<Shape> getShapes() { return shapes; }
    /** @return si les réflexions sont activées */
    public boolean isReflectionEnabled() { return reflectionEnabled; }

    /**
     * Définit le fichier de sortie.
     * @param output le nom du fichier
     */
    public void setOutput(String output) { this.output = output; }

    /**
     * Définit la couleur ambiante.
     * @param ambient la couleur
     */
    public void setAmbient(Color ambient) { this.ambient = ambient; }

    /**
     * Active ou désactive les réflexions.
     * @param enabled true pour activer
     */
    public void setReflectionEnabled(boolean enabled) { this.reflectionEnabled = enabled; }

    /**
     * Ajoute une lumière à la scène.
     * @param light la lumière
     */
    public void addLight(AbstractLight light) { lights.add(light); }

    /**
     * Ajoute une forme à la scène.
     * @param shape la forme
     */
    public void addShape(Shape shape) { shapes.add(shape); }

    /**
     * Trouve l'intersection la plus proche avec un rayon.
     * @param ray le rayon
     * @return l'intersection la plus proche, si elle existe
     */
    public Optional<Intersection> findClosestIntersection(Ray ray) {
        Optional<Intersection> closest = Optional.empty();
        double minDistance = Double.POSITIVE_INFINITY;

        for (Shape shape : shapes) {
            Optional<Intersection> intersection = shape.intersect(ray);
            if (intersection.isPresent()) {
                double distance = intersection.get().getDistance();
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = intersection;
                }
            }
        }

        return closest;
    }

    /**
     * Vérifie si un point est dans l'ombre par rapport à une lumière.
     * @param point le point
     * @param light la lumière
     * @return true si le point est dans l'ombre
     */
    public boolean isInShadow(Point point, AbstractLight light) {
        Vector lightDir = light.getDirectionFrom(point);
        double lightDistance = light.getDistanceFrom(point);
        
        Ray shadowRay = new Ray(point, lightDir);
        
        for (Shape shape : shapes) {
            Optional<Intersection> intersection = shape.intersect(shadowRay);
            if (intersection.isPresent()) {
                double t = intersection.get().getDistance();
                // Epsilon pour éviter l'auto-intersection (shadow acne)
                if (t > 0.001 && t < lightDistance - 0.001) {
                    return true;
                }
            }
        }
        
        return false;
    }
}