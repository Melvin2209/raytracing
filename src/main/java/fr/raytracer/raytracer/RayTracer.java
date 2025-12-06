
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.*;
import fr.raytracer.lighting.AbstractLight;
import java.util.Optional;

/**
 * Moteur de raytracing qui calcule la couleur de chaque pixel.
 */
public class RayTracer {
    /** Scène à rendre. */
    private Scene scene;
    /** Profondeur maximale de récursion. */
    private int maxDepth;

    /**
     * Crée un raytracer.
     * @param scene la scène
     * @param maxDepth la profondeur maximale de réflexion
     */
    public RayTracer(Scene scene, int maxDepth) {
        this.scene = scene;
        this.maxDepth = maxDepth;
    }

    /**
     * Calcule la couleur d'un pixel.
     * @param i colonne du pixel
     * @param j ligne du pixel
     * @return la couleur du pixel
     */
    public Color getPixelColor(int i, int j) {
        Ray ray = computeRay(i, j);
        return traceRay(ray, 0);
    }

    /**
     * Calcule le rayon partant de la caméra pour un pixel donné.
     * @param i colonne
     * @param j ligne
     * @return le rayon
     */
    private Ray computeRay(int i, int j) {
        Camera camera = scene.getCamera();
        Orthonormal ortho = camera.getOrthonormal();
        
        double fovRadians = Math.toRadians(camera.getFov());
        double halfHeight = Math.tan(fovRadians / 2.0);
        double halfWidth = halfHeight * scene.getWidth() / scene.getHeight();
        
        double u = (i + 0.5) / scene.getWidth() - 0.5;
        double v = (j + 0.5) / scene.getHeight() - 0.5;
        
        Vector direction = ortho.getU().multiply(u * 2.0 * halfWidth)
                                .add(ortho.getV().multiply(-v * 2.0 * halfHeight))
                                .add(ortho.getW().negate())
                                .normalize();
        
        return new Ray(camera.getLookFrom(), direction);
    }

    /**
     * Trace un rayon et retourne la couleur.
     * @param ray le rayon
     * @param depth la profondeur de récursion actuelle
     * @return la couleur
     */
    private Color traceRay(Ray ray, int depth) {
        Optional<Intersection> intersection = scene.findClosestIntersection(ray);
        
        if (!intersection.isPresent()) {
            Vector unitDirection = ray.getDirection();
            double t = 0.5 * (unitDirection.getY() + 1.0);
            return new Color(0.05, 0.05, 0.05).multiply(1.0 - t)
                   .add(new Color(0.25, 0.25, 0.25).multiply(t));
        }
        
        return computeColor(intersection.get(), ray, depth);
    }

    /**
     * Calcule la couleur au point d'intersection.
     * @param intersection l'intersection
     * @param ray le rayon incident
     * @param depth la profondeur de récursion
     * @return la couleur
     */
    private Color computeColor(Intersection intersection, Ray ray, int depth) {
        Color ambientLight = scene.getAmbient();
        if (ambientLight.getR() == 0 && ambientLight.getG() == 0 && ambientLight.getB() == 0) {
            ambientLight = new Color(0.15, 0.15, 0.15);
        }
        Color finalColor = ambientLight.multiply(intersection.getShape().getDiffuse());
        
        for (AbstractLight light : scene.getLights()) {
            if (!scene.isInShadow(intersection.getPoint(), light)) {
                Color lightContribution = computeLightContribution(intersection, light, ray);
                finalColor = finalColor.add(lightContribution);
            }
        }
        
        if (depth < maxDepth) {
            Color specular = intersection.getShape().getSpecular();
            Color enhancedSpecular = new Color(
                Math.min(1.0, specular.getR() * 1.5),
                Math.min(1.0, specular.getG() * 1.5),
                Math.min(1.0, specular.getB() * 1.5)
            );
            if (enhancedSpecular.getR() > 0 || enhancedSpecular.getG() > 0 || enhancedSpecular.getB() > 0) {
                Color reflectionColor = computeReflection(intersection, ray, depth);
                finalColor = finalColor.add(enhancedSpecular.multiply(reflectionColor));
            }
        }
        
        return finalColor;
    }

    /**
     * Calcule la contribution d'une lumière.
     * @param intersection l'intersection
     * @param light la lumière
     * @param ray le rayon incident
     * @return la couleur contribuée
     */
    private Color computeLightContribution(Intersection intersection, AbstractLight light, Ray ray) {
        Vector normal = intersection.getNormal();
        Vector lightDir = light.getDirectionFrom(intersection.getPoint());
        Vector eyeDir = ray.getDirection().negate();
        
        double diffuseFactor = Math.max(normal.dot(lightDir), 0);
        Color diffuse = intersection.getShape().getDiffuse()
                                   .multiply(light.getColor())
                                   .multiply(diffuseFactor);
        
        Vector halfVector = lightDir.add(eyeDir).normalize();
        double shininess = intersection.getShape().getShininess();
        double specularFactor = Math.pow(Math.max(normal.dot(halfVector), 0), shininess * 1.2);
        Color specular = intersection.getShape().getSpecular()
                                    .multiply(light.getColor())
                                    .multiply(specularFactor * 1.3);
        
        return diffuse.add(specular);
    }

    /**
     * Calcule la couleur de réflexion.
     * @param intersection l'intersection
     * @param ray le rayon incident
     * @param depth la profondeur actuelle
     * @return la couleur réfléchie
     */
    private Color computeReflection(Intersection intersection, Ray ray, int depth) {
        Vector normal = intersection.getNormal();
        Vector incident = ray.getDirection();
        Vector reflected = incident.subtract(normal.multiply(2 * incident.dot(normal)));
        
        Point reflectionOrigin = intersection.getPoint().add(reflected.multiply(1e-4));
        Ray reflectionRay = new Ray(reflectionOrigin, reflected);
        
        return traceRay(reflectionRay, depth + 1);
    }
}