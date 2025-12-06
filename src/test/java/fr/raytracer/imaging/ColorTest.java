package fr.raytracer.imaging;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Color.
 */
public class ColorTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testConstructeurDefaut() {
        Color c = new Color();
        assertEquals(0, c.getR(), DELTA);
        assertEquals(0, c.getG(), DELTA);
        assertEquals(0, c.getB(), DELTA);
    }
    
    @Test
    public void testConstructeur() {
        Color c = new Color(0.5, 0.3, 0.8);
        assertEquals(0.5, c.getR(), DELTA);
        assertEquals(0.3, c.getG(), DELTA);
        assertEquals(0.8, c.getB(), DELTA);
    }
    
    @Test
    public void testClampingMax() {
        Color c = new Color(1.5, 2.0, 3.0);
        assertEquals(1, c.getR(), DELTA);
        assertEquals(1, c.getG(), DELTA);
        assertEquals(1, c.getB(), DELTA);
    }
    
    @Test
    public void testClampingMin() {
        Color c = new Color(-0.5, -1.0, -2.0);
        assertEquals(0, c.getR(), DELTA);
        assertEquals(0, c.getG(), DELTA);
        assertEquals(0, c.getB(), DELTA);
    }
    
    @Test
    public void testAdd() {
        Color c1 = new Color(0.2, 0.3, 0.4);
        Color c2 = new Color(0.1, 0.2, 0.3);
        Color result = c1.add(c2);
        
        assertEquals(0.3, result.getR(), DELTA);
        assertEquals(0.5, result.getG(), DELTA);
        assertEquals(0.7, result.getB(), DELTA);
    }
    
    @Test
    public void testMultiplyScalar() {
        Color c = new Color(0.5, 0.4, 0.3);
        Color result = c.multiply(2);
        
        assertEquals(1, result.getR(), DELTA); // clampé
        assertEquals(0.8, result.getG(), DELTA);
        assertEquals(0.6, result.getB(), DELTA);
    }
    
    @Test
    public void testMultiplyColor() {
        Color c1 = new Color(0.5, 0.5, 0.5);
        Color c2 = new Color(1.0, 0.5, 0.0);
        Color result = c1.multiply(c2);
        
        assertEquals(0.5, result.getR(), DELTA);
        assertEquals(0.25, result.getG(), DELTA);
        assertEquals(0, result.getB(), DELTA);
    }
    
    @Test
    public void testToRGB() {
        Color black = new Color(0, 0, 0);
        assertEquals(0x000000, black.toRGB());
        
        Color white = new Color(1, 1, 1);
        assertEquals(0xFFFFFF, white.toRGB());
        
        Color red = new Color(1, 0, 0);
        assertEquals(0xFF0000, red.toRGB());
        
        Color green = new Color(0, 1, 0);
        assertEquals(0x00FF00, green.toRGB());
        
        Color blue = new Color(0, 0, 1);
        assertEquals(0x0000FF, blue.toRGB());
    }
    
    @Test
    public void testToString() {
        Color c = new Color(0.5, 0.3, 0.8);
        String str = c.toString();
        // Vérifie juste que le toString retourne une chaîne non vide
        assertNotNull(str);
        assertTrue(str.length() > 0);
        assertTrue(str.contains("Color"));
    }
}
