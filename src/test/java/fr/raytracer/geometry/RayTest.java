package fr.raytracer.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Ray.
 */
public class RayTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testConstructeur() {
        Point origin = new Point(0, 0, 0);
        Vector direction = new Vector(1, 0, 0);
        Ray ray = new Ray(origin, direction);
        
        assertEquals(origin, ray.getOrigin());
        assertEquals(1, ray.getDirection().length(), DELTA);
    }
    
    @Test
    public void testDirectionNormalisee() {
        Point origin = new Point(0, 0, 0);
        Vector direction = new Vector(3, 4, 0);
        Ray ray = new Ray(origin, direction);
        
        // La direction doit être normalisée
        assertEquals(1, ray.getDirection().length(), DELTA);
        assertEquals(0.6, ray.getDirection().getX(), DELTA);
        assertEquals(0.8, ray.getDirection().getY(), DELTA);
    }
    
    @Test
    public void testPointAt() {
        Point origin = new Point(1, 2, 3);
        Vector direction = new Vector(1, 0, 0);
        Ray ray = new Ray(origin, direction);
        
        Point pointAt5 = ray.pointAt(5);
        assertEquals(6, pointAt5.getX(), DELTA);
        assertEquals(2, pointAt5.getY(), DELTA);
        assertEquals(3, pointAt5.getZ(), DELTA);
    }
    
    @Test
    public void testPointAtZero() {
        Point origin = new Point(1, 2, 3);
        Vector direction = new Vector(0, 1, 0);
        Ray ray = new Ray(origin, direction);
        
        Point pointAt0 = ray.pointAt(0);
        assertEquals(origin, pointAt0);
    }
}
