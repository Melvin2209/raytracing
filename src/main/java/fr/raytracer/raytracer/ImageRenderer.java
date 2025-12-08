
package fr.raytracer.raytracer;

import fr.raytracer.imaging.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Effectue le rendu d'une scène en image avec multithreading.
 */
public class ImageRenderer {
    
    /**
     * Génère une image à partir d'une scène en utilisant tous les cœurs CPU disponibles.
     * @param scene la scène à rendre
     * @param maxDepth la profondeur maximale de récursion (réflexions)
     * @return l'image générée
     */
    public BufferedImage render(Scene scene, int maxDepth) {
        int width = scene.getWidth();
        int height = scene.getHeight();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        RayTracer rayTracer = new RayTracer(scene, maxDepth);
        
        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Rendering image " + width + "x" + height + " with " + numThreads + " threads...");
        
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger completedRows = new AtomicInteger(0);
        
        // Soumettre chaque ligne comme une tâche séparée
        for (int j = 0; j < height; j++) {
            final int row = j;
            executor.submit(() -> {
                // Tableau pour stocker les couleurs de la ligne
                int[] rowColors = new int[width];
                for (int i = 0; i < width; i++) {
                    Color color = rayTracer.getPixelColor(i, row);
                    rowColors[i] = color.toRGB();
                }
                
                // Écriture synchronisée dans l'image
                synchronized (image) {
                    for (int i = 0; i < width; i++) {
                        image.setRGB(i, row, rowColors[i]);
                    }
                }
                
                // Afficher la progression
                int completed = completedRows.incrementAndGet();
                if (completed % 50 == 0 || completed == height) {
                    System.out.println("Progress: " + (completed * 100 / height) + "%");
                }
            });
        }
        
        // Attendre la fin de toutes les tâches
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Rendering interrupted");
        }
        
        System.out.println("Rendering complete!");
        return image;
    }
}
