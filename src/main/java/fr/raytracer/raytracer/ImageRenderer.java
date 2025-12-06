
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import java.awt.image.BufferedImage;

/**
 * Effectue le rendu d'une scène en image.
 */
public class ImageRenderer {
    
    /**
     * Génère une image à partir d'une scène.
     * @param scene la scène à rendre
     * @param maxDepth la profondeur maximale de récursion (réflexions)
     * @return l'image générée
     */
    public BufferedImage render(Scene scene, int maxDepth) {
        int width = scene.getWidth();
        int height = scene.getHeight();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        RayTracer rayTracer = new RayTracer(scene, maxDepth);
        
        System.out.println("Rendering image " + width + "x" + height + "...");
        
        for (int j = 0; j < height; j++) {
            if (j % 50 == 0) {
                System.out.println("Progress: " + (j * 100 / height) + "%");
            }
            
            for (int i = 0; i < width; i++) {
                Color color = rayTracer.getPixelColor(i, j);
                image.setRGB(i, j, color.toRGB());
            }
        }
        
        System.out.println("Rendering complete!");
        return image;
    }
}
