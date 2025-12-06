
package fr.raytracer.imaging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utilitaire pour écrire des images au format PNG.
 */
public class ImageWriter {
    
    /**
     * Écrit une image dans un fichier PNG.
     * @param image l'image à écrire
     * @param filename le nom du fichier de sortie
     * @throws IOException en cas d'erreur d'écriture
     */
    public static void writeImage(BufferedImage image, String filename) throws IOException {
        File outputFile = new File(filename);
        ImageIO.write(image, "png", outputFile);
        System.out.println("Image saved to: " + filename);
    }
}