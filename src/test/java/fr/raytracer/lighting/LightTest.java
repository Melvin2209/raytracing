package fr.raytracer.lighting;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;
import fr.raytracer.imaging.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour les classes de lumière.
 */
public class LightTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testPointLightConstructeur() {
        Point position = new Point(0, 5, 0);
        Color color = new Color(1, 1, 1);
        PointLight light = new PointLight(position, color);
        
        assertEquals(color, light.getColor());
    }
    
    @Test
    public void testPointLightDirection() {
        Point lightPos = new Point(0, 10, 0);
        PointLight light = new PointLight(lightPos, new Color(1, 1, 1));
        
        Point surfacePoint = new Point(0, 0, 0);
        Vector direction = light.getDirectionFrom(surfacePoint);
        
        // Direction doit pointer vers le haut (vers la lumière)
        assertEquals(0, direction.getX(), DELTA);
        assertEquals(1, direction.getY(), DELTA);
        assertEquals(0, direction.getZ(), DELTA);
    }
    
    @Test
    public void testPointLightDistance() {
        Point lightPos = new Point(0, 10, 0);
        PointLight light = new PointLight(lightPos, new Color(1, 1, 1));
        
        Point surfacePoint = new Point(0, 0, 0);
        double distance = light.getDistanceFrom(surfacePoint);
        
        assertEquals(10, distance, DELTA);
    }
    
    @Test
    public void testDirectionalLightConstructeur() {
        Vector direction = new Vector(0, -1, 0);
        Color color = new Color(1, 1, 1);
        DirectionalLight light = new DirectionalLight(direction, color);
        
        assertEquals(color, light.getColor());
    }
    
    @Test
    public void testDirectionalLightDirection() {
        Vector lightDir = new Vector(0, -1, 0);
        DirectionalLight light = new DirectionalLight(lightDir, new Color(1, 1, 1));
        
        Point surfacePoint = new Point(0, 0, 0);
        Vector direction = light.getDirectionFrom(surfacePoint);
        
        // Direction doit être l'opposé (vers la lumière)
        assertEquals(0, direction.getX(), DELTA);
        assertEquals(1, direction.getY(), DELTA);
        assertEquals(0, direction.getZ(), DELTA);
    }
    
    @Test
    public void testDirectionalLightDistanceInfinie() {
        Vector lightDir = new Vector(0, -1, 0);
        DirectionalLight light = new DirectionalLight(lightDir, new Color(1, 1, 1));
        
        Point surfacePoint = new Point(0, 0, 0);
        double distance = light.getDistanceFrom(surfacePoint);
        
        assertEquals(Double.POSITIVE_INFINITY, distance, DELTA);
    }
    
    @Test
    public void testDirectionalLightDirectionNormalisee() {
        Vector lightDir = new Vector(3, -4, 0); // Non normalisé
        DirectionalLight light = new DirectionalLight(lightDir, new Color(1, 1, 1));
        
        Point surfacePoint = new Point(0, 0, 0);
        Vector direction = light.getDirectionFrom(surfacePoint);
        
        // La direction retournée doit être normalisée
        assertEquals(1, direction.length(), DELTA);
    }
}
