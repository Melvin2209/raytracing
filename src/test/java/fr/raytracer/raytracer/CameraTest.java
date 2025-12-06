package fr.raytracer.raytracer;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Camera.
 */
public class CameraTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testConstructeur() {
        Point lookFrom = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        double fov = 60;
        
        Camera camera = new Camera(lookFrom, lookAt, up, fov);
        
        assertEquals(lookFrom, camera.getLookFrom());
        assertEquals(lookAt, camera.getLookAt());
        assertEquals(up, camera.getUp());
        assertEquals(fov, camera.getFov(), DELTA);
    }
    
    @Test
    public void testOrthonormalNotNull() {
        Point lookFrom = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        
        Camera camera = new Camera(lookFrom, lookAt, up, 60);
        
        assertNotNull(camera.getOrthonormal());
    }
    
    @Test
    public void testOrthonormalVectors() {
        Point lookFrom = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        
        Camera camera = new Camera(lookFrom, lookAt, up, 60);
        Orthonormal ortho = camera.getOrthonormal();
        
        // Les vecteurs doivent être normalisés
        assertEquals(1, ortho.getU().length(), DELTA);
        assertEquals(1, ortho.getV().length(), DELTA);
        assertEquals(1, ortho.getW().length(), DELTA);
        
        // Les vecteurs doivent être orthogonaux
        assertEquals(0, ortho.getU().dot(ortho.getV()), DELTA);
        assertEquals(0, ortho.getU().dot(ortho.getW()), DELTA);
        assertEquals(0, ortho.getV().dot(ortho.getW()), DELTA);
    }
}
