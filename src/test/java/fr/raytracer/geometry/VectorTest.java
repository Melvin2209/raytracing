package fr.raytracer.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Vector.
 */
public class VectorTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testConstructeur() {
        Vector v = new Vector(1, 2, 3);
        assertEquals(1, v.getX(), DELTA);
        assertEquals(2, v.getY(), DELTA);
        assertEquals(3, v.getZ(), DELTA);
    }
    
    @Test
    public void testAddition() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector result = v1.add(v2);
        
        assertEquals(5, result.getX(), DELTA);
        assertEquals(7, result.getY(), DELTA);
        assertEquals(9, result.getZ(), DELTA);
    }
    
    @Test
    public void testSubtract() {
        Vector v1 = new Vector(5, 7, 9);
        Vector v2 = new Vector(1, 2, 3);
        Vector result = v1.subtract(v2);
        
        assertEquals(4, result.getX(), DELTA);
        assertEquals(5, result.getY(), DELTA);
        assertEquals(6, result.getZ(), DELTA);
    }
    
    @Test
    public void testMultiply() {
        Vector v = new Vector(1, 2, 3);
        Vector result = v.multiply(2);
        
        assertEquals(2, result.getX(), DELTA);
        assertEquals(4, result.getY(), DELTA);
        assertEquals(6, result.getZ(), DELTA);
    }
    
    @Test
    public void testDot() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        double result = v1.dot(v2);
        
        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        assertEquals(32, result, DELTA);
    }
    
    @Test
    public void testCross() {
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        Vector result = v1.cross(v2);
        
        assertEquals(0, result.getX(), DELTA);
        assertEquals(0, result.getY(), DELTA);
        assertEquals(1, result.getZ(), DELTA);
    }
    
    @Test
    public void testLength() {
        Vector v = new Vector(3, 4, 0);
        assertEquals(5, v.length(), DELTA);
    }
    
    @Test
    public void testNormalize() {
        Vector v = new Vector(3, 4, 0);
        Vector normalized = v.normalize();
        
        assertEquals(0.6, normalized.getX(), DELTA);
        assertEquals(0.8, normalized.getY(), DELTA);
        assertEquals(0, normalized.getZ(), DELTA);
        assertEquals(1, normalized.length(), DELTA);
    }
    
    @Test
    public void testNegate() {
        Vector v = new Vector(1, -2, 3);
        Vector negated = v.negate();
        
        assertEquals(-1, negated.getX(), DELTA);
        assertEquals(2, negated.getY(), DELTA);
        assertEquals(-3, negated.getZ(), DELTA);
    }
    
    @Test
    public void testEquals() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);
        Vector v3 = new Vector(1, 2, 4);
        
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }
}
