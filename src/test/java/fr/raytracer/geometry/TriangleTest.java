package fr.raytracer.geometry;

import fr.raytracer.imaging.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Optional;

/**
 * Tests unitaires pour la classe Triangle.
 */
public class TriangleTest {
    
    private static final double DELTA = 1e-6;
    
    @Test
    public void testIntersectionDirecte() {
        // Triangle dans le plan XY à z=1
        Point v0 = new Point(-1, -1, 1);
        Point v1 = new Point(1, -1, 1);
        Point v2 = new Point(0, 1, 1);
        
        Triangle triangle = new Triangle(v0, v1, v2,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        // Rayon vers le centre du triangle
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Optional<Intersection> intersection = triangle.intersect(ray);
        
        assertTrue(intersection.isPresent());
        assertEquals(1, intersection.get().getDistance(), DELTA);
    }
    
    @Test
    public void testAucuneIntersection() {
        Point v0 = new Point(-1, -1, 1);
        Point v1 = new Point(1, -1, 1);
        Point v2 = new Point(0, 1, 1);
        
        Triangle triangle = new Triangle(v0, v1, v2,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        // Rayon qui passe à côté
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        Optional<Intersection> intersection = triangle.intersect(ray);
        
        assertFalse(intersection.isPresent());
    }
    
    @Test
    public void testRayonParallele() {
        Point v0 = new Point(-1, -1, 1);
        Point v1 = new Point(1, -1, 1);
        Point v2 = new Point(0, 1, 1);
        
        Triangle triangle = new Triangle(v0, v1, v2,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        // Rayon parallèle au triangle
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Optional<Intersection> intersection = triangle.intersect(ray);
        
        assertFalse(intersection.isPresent());
    }
    
    @Test
    public void testNormale() {
        // Triangle dans le plan XY
        Point v0 = new Point(0, 0, 0);
        Point v1 = new Point(1, 0, 0);
        Point v2 = new Point(0, 1, 0);
        
        Triangle triangle = new Triangle(v0, v1, v2,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        Vector normal = triangle.getNormalAt(new Point(0.5, 0.5, 0));
        
        // La normale doit pointer vers Z (+ ou -)
        assertEquals(0, normal.getX(), DELTA);
        assertEquals(0, normal.getY(), DELTA);
        assertEquals(1, Math.abs(normal.getZ()), DELTA);
    }
    
    @Test
    public void testNormaleConstante() {
        Point v0 = new Point(0, 0, 0);
        Point v1 = new Point(1, 0, 0);
        Point v2 = new Point(0, 1, 0);
        
        Triangle triangle = new Triangle(v0, v1, v2,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        
        Vector n1 = triangle.getNormalAt(new Point(0, 0, 0));
        Vector n2 = triangle.getNormalAt(new Point(0.5, 0.5, 0));
        Vector n3 = triangle.getNormalAt(new Point(1, 0, 0));
        
        assertEquals(n1, n2);
        assertEquals(n2, n3);
    }
}
