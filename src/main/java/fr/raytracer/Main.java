
package fr.raytracer;

import fr.raytracer.imaging.ImageWriter;
import fr.raytracer.parsing.SceneFileParser;
import fr.raytracer.raytracer.ImageRenderer;
import fr.raytracer.raytracer.Scene;
import java.awt.image.BufferedImage;

public class Main {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar raytracer.jar <scene_file> [maxdepth]");
            System.exit(1);
        }
        
        String sceneFile = args[0];
        int maxDepth = args.length > 1 ? Integer.parseInt(args[1]) : 5;
        
        try {
            System.out.println("Loading scene from: " + sceneFile);
            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(sceneFile);
            
            if (scene == null) {
                System.err.println("Failed to parse scene file");
                System.exit(1);
            }
            
            ImageRenderer renderer = new ImageRenderer();
            BufferedImage image = renderer.render(scene, maxDepth);
            
            ImageWriter.writeImage(image, scene.getOutput());
            
            System.out.println("Done!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}