package fr.raytracer.raytracer;

import fr.raytracer.geometry.*;
import fr.raytracer.imaging.Color;
import fr.raytracer.lighting.PointLight;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Optional;

/**
 * Tests unitaires pour la classe Scene.
 */
public class SceneTest {
    
    private static final double DELTA = 1e-9;
    
    private Scene createScene() {
        Point lookFrom = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        Camera camera = new Camera(lookFrom, lookAt, up, 60);
        return new Scene(800, 600, camera);
    }
    
    @Test
    public void testConstructeur() {
        Scene scene = createScene();
        
        assertEquals(800, scene.getWidth());
        assertEquals(600, scene.getHeight());
        assertNotNull(scene.getCamera());
    }
    
    @Test
    public void testOutput() {
        Scene scene = createScene();
        
        assertEquals("output.png", scene.getOutput());
        
        scene.setOutput("test.png");
        assertEquals("test.png", scene.getOutput());
    }
    
    @Test
    public void testAmbient() {
        Scene scene = createScene();
        
        scene.setAmbient(new Color(0.1, 0.1, 0.1));
        assertEquals(0.1, scene.getAmbient().getR(), DELTA);
    }
    
    @Test
    public void testAddShape() {
        Scene scene = createScene();
        
        assertEquals(0, scene.getShapes().size());
        
        Sphere sphere = new Sphere(new Point(0, 0, -5), 1,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        scene.addShape(sphere);
        
        assertEquals(1, scene.getShapes().size());
    }
    
    @Test
    public void testAddLight() {
        Scene scene = createScene();
        
        assertEquals(0, scene.getLights().size());
        
        PointLight light = new PointLight(new Point(0, 5, 0), new Color(1, 1, 1));
        scene.addLight(light);
        
        assertEquals(1, scene.getLights().size());
    }
    
    @Test
    public void testFindClosestIntersection() {
        Scene scene = createScene();
        
        // Deux sphères à différentes distances
        Sphere sphere1 = new Sphere(new Point(0, 0, -5), 1,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        Sphere sphere2 = new Sphere(new Point(0, 0, -10), 1,
            new Color(0, 1, 0), new Color(1, 1, 1), 32);
        
        scene.addShape(sphere1);
        scene.addShape(sphere2);
        
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, -1));
        Optional<Intersection> intersection = scene.findClosestIntersection(ray);
        
        assertTrue(intersection.isPresent());
        // Doit toucher la sphère la plus proche (sphere1 à z=-5)
        assertEquals(4, intersection.get().getDistance(), 0.1);
    }
    
    @Test
    public void testNoIntersection() {
        Scene scene = createScene();
        
        Sphere sphere = new Sphere(new Point(0, 0, -5), 1,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        scene.addShape(sphere);
        
        // Rayon qui ne touche pas la sphère
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Optional<Intersection> intersection = scene.findClosestIntersection(ray);
        
        assertFalse(intersection.isPresent());
    }
    
    @Test
    public void testIsInShadow() {
        Scene scene = createScene();
        
        // Sphère qui bloque la lumière
        Sphere blocker = new Sphere(new Point(0, 2, 0), 0.5,
            new Color(1, 0, 0), new Color(1, 1, 1), 32);
        scene.addShape(blocker);
        
        PointLight light = new PointLight(new Point(0, 5, 0), new Color(1, 1, 1));
        
        // Point sous la sphère bloquante
        Point pointInShadow = new Point(0, 0, 0);
        assertTrue(scene.isInShadow(pointInShadow, light));
        
        // Point à côté (pas bloqué)
        Point pointNotInShadow = new Point(5, 0, 0);
        assertFalse(scene.isInShadow(pointNotInShadow, light));
    }
}
