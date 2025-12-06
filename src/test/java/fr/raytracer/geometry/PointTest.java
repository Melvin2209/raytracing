package fr.raytracer.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Point.
 */
public class PointTest {
    
    private static final double DELTA = 1e-9;
    
    @Test
    public void testConstructeur() {
        Point p = new Point(1, 2, 3);
        assertEquals(1, p.getX(), DELTA);
        assertEquals(2, p.getY(), DELTA);
        assertEquals(3, p.getZ(), DELTA);
    }
    
    @Test
    public void testAddVector() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(4, 5, 6);
        Point result = p.add(v);
        
        assertEquals(5, result.getX(), DELTA);
        assertEquals(7, result.getY(), DELTA);
        assertEquals(9, result.getZ(), DELTA);
    }
    
    @Test
    public void testSubtractPoint() {
        Point p1 = new Point(5, 7, 9);
        Point p2 = new Point(1, 2, 3);
        Vector result = p1.subtract(p2);
        
        assertEquals(4, result.getX(), DELTA);
        assertEquals(5, result.getY(), DELTA);
        assertEquals(6, result.getZ(), DELTA);
    }
    
    @Test
    public void testEquals() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 2, 3);
        Point p3 = new Point(1, 2, 4);
        
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }
    
    @Test
    public void testToString() {
        Point p = new Point(1.5, 2.5, 3.5);
        String str = p.toString();
        // Le format est "(x.xxx, y.yyy, z.zzz)"
        assertNotNull(str);
        assertTrue(str.length() > 0);
    }
}
