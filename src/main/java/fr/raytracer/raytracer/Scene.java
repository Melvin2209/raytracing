
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.*;
import fr.raytracer.lighting.AbstractLight;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png";
    private Color ambient = new Color();
    private List<AbstractLight> lights = new ArrayList<>();
    private List<Shape> shapes = new ArrayList<>();

    public Scene(int width, int height, Camera camera) {
        this.width = width;
        this.height = height;
        this.camera = camera;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Camera getCamera() { return camera; }
    public String getOutput() { return output; }
    public Color getAmbient() { return ambient; }
    public List<AbstractLight> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }

    public void setOutput(String output) { this.output = output; }
    public void setAmbient(Color ambient) { this.ambient = ambient; }
    public void addLight(AbstractLight light) { lights.add(light); }
    public void addShape(Shape shape) { shapes.add(shape); }

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

    public boolean isInShadow(Point point, AbstractLight light) {
        Vector lightDir = light.getDirectionFrom(point);
        double lightDistance = light.getDistanceFrom(point);
        
        Ray shadowRay = new Ray(point, lightDir);
        
        // Pour les lumières directionnelles, lightDistance est infini
        // Pour les point lights, on vérifie si un obstacle est entre le point et la lumière
        for (Shape shape : shapes) {
            Optional<Intersection> intersection = shape.intersect(shadowRay);
            if (intersection.isPresent()) {
                double t = intersection.get().getDistance();
                // Utiliser un epsilon pour éviter l'auto-intersection (shadow acne)
                // et vérifier que l'obstacle est avant la lumière
                if (t > 0.001 && t < lightDistance - 0.001) {
                    return true;
                }
            }
        }
        
        return false;
    }
}