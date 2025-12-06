package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Optional;

/**
 * Tests unitaires pour la classe Sphere.
 */
public class SphereTest {
    
    private static final double DELTA = 1e-4;
    
    @Test
    public void testIntersectionDirecte() {
        Point center = new Point(0, 0, 5);
        Sphere sphere = new Sphere(center, 1, 
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Optional<Intersection> intersection = sphere.intersect(ray);
        
        assertTrue(intersection.isPresent());
        assertEquals(4, intersection.get().getDistance(), DELTA);
    }
    
    @Test
    public void testAucuneIntersection() {
        Point center = new Point(0, 0, 5);
        Sphere sphere = new Sphere(center, 1, 
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        // Rayon qui passe à côté
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        Optional<Intersection> intersection = sphere.intersect(ray);
        
        assertFalse(intersection.isPresent());
    }
    
    @Test
    public void testNormale() {
        Point center = new Point(0, 0, 0);
        Sphere sphere = new Sphere(center, 1, 
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        // Normale au point (1, 0, 0) doit être (1, 0, 0)
        Vector normal = sphere.getNormalAt(new Point(1, 0, 0));
        assertEquals(1, normal.getX(), DELTA);
        assertEquals(0, normal.getY(), DELTA);
        assertEquals(0, normal.getZ(), DELTA);
    }
    
    @Test
    public void testNormaleNormalisee() {
        Point center = new Point(0, 0, 0);
        Sphere sphere = new Sphere(center, 2, 
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        Vector normal = sphere.getNormalAt(new Point(2, 0, 0));
        assertEquals(1, normal.length(), DELTA);
    }
    
    @Test
    public void testProprietesMateriaux() {
        Color diffuse = new Color(1, 0, 0);
        Color specular = new Color(1, 1, 1);
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1, diffuse, specular, 64);
        
        assertEquals(diffuse, sphere.getDiffuse());
        assertEquals(specular, sphere.getSpecular());
        assertEquals(64, sphere.getShininess(), DELTA);
    }
}
