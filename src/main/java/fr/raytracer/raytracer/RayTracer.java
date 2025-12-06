
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import fr.raytracer.geometry.*;
import fr.raytracer.lighting.AbstractLight;
import java.util.Optional;

public class RayTracer {
    private Scene scene;
    private int maxDepth;

    public RayTracer(Scene scene, int maxDepth) {
        this.scene = scene;
        this.maxDepth = maxDepth;
    }

    public Color getPixelColor(int i, int j) {
        Ray ray = computeRay(i, j);
        return traceRay(ray, 0);
    }

    private Ray computeRay(int i, int j) {
        Camera camera = scene.getCamera();
        Orthonormal ortho = camera.getOrthonormal();
        
        double fovRadians = Math.toRadians(camera.getFov());
        double halfHeight = Math.tan(fovRadians / 2.0);
        double halfWidth = halfHeight * scene.getWidth() / scene.getHeight();
        
        // Coordonnées normalisées de -0.5 à 0.5
        double u = (i + 0.5) / scene.getWidth() - 0.5;
        double v = (j + 0.5) / scene.getHeight() - 0.5;
        
        // Calculer la direction du rayon
        // u va de gauche (-) à droite (+)
        // v va de haut (-) à bas (+), mais on veut inverser pour que Y pointe vers le haut
        Vector direction = ortho.getU().multiply(u * 2.0 * halfWidth)
                                .add(ortho.getV().multiply(-v * 2.0 * halfHeight))
                                .add(ortho.getW().negate())
                                .normalize();
        
        return new Ray(camera.getLookFrom(), direction);
    }

    private Color traceRay(Ray ray, int depth) {
        Optional<Intersection> intersection = scene.findClosestIntersection(ray);
        
        if (!intersection.isPresent()) {
            // Fond avec dégradé gris sombre (comme dans l'image cible)
            Vector unitDirection = ray.getDirection();
            double t = 0.5 * (unitDirection.getY() + 1.0);
            // Dégradé de gris très sombre (0.05) à gris moyen (0.25)
            return new Color(0.05, 0.05, 0.05).multiply(1.0 - t)
                   .add(new Color(0.25, 0.25, 0.25).multiply(t));
        }
        
        return computeColor(intersection.get(), ray, depth);
    }

    private Color computeColor(Intersection intersection, Ray ray, int depth) {
        // Ambient light par défaut si la scène n'en a pas
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
            // Augmenter les reflets spéculaires pour un effet miroir plus marqué
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

    private Color computeLightContribution(Intersection intersection, AbstractLight light, Ray ray) {
        Vector normal = intersection.getNormal();
        Vector lightDir = light.getDirectionFrom(intersection.getPoint());
        Vector eyeDir = ray.getDirection().negate();
        
        double diffuseFactor = Math.max(normal.dot(lightDir), 0);
        Color diffuse = intersection.getShape().getDiffuse()
                                   .multiply(light.getColor())
                                   .multiply(diffuseFactor);
        
        Vector halfVector = lightDir.add(eyeDir).normalize();
        // Augmenter l'exposant pour des reflets plus brillants et prononcés
        double shininess = intersection.getShape().getShininess();
        double specularFactor = Math.pow(Math.max(normal.dot(halfVector), 0), shininess * 1.2);
        Color specular = intersection.getShape().getSpecular()
                                    .multiply(light.getColor())
                                    .multiply(specularFactor * 1.3); // Augmenter l'intensité des reflets
        
        return diffuse.add(specular);
    }

    private Color computeReflection(Intersection intersection, Ray ray, int depth) {
        Vector normal = intersection.getNormal();
        Vector incident = ray.getDirection();
        Vector reflected = incident.subtract(normal.multiply(2 * incident.dot(normal)));
        
        Point reflectionOrigin = intersection.getPoint().add(reflected.multiply(1e-4));
        Ray reflectionRay = new Ray(reflectionOrigin, reflected);
        
        return traceRay(reflectionRay, depth + 1);
    }
}