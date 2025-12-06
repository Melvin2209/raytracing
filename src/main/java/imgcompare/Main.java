
package imgcompare;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java ImageCompare <image1.png> <image2.png>");
            System.exit(1);
        }
        
        String file1 = args[0];
        String file2 = args[1];
        
        try {
            BufferedImage img1 = ImageIO.read(new File(file1));
            BufferedImage img2 = ImageIO.read(new File(file2));
            
            if (img1 == null || img2 == null) {
                System.err.println("Error: Could not load images");
                System.exit(1);
            }
            
            ImageComparator comparator = new ImageComparator();
            
            int differentPixels = comparator.countDifferentPixels(img1, img2);
            boolean identical = comparator.areImagesIdentical(differentPixels);
            
            if (identical) {
                System.out.println("OK");
            } else {
                System.out.println("KO");
            }
            
            System.out.println("Les deux images diffèrent de " + differentPixels + " pixels.");
            
            if (differentPixels > 0) {
                BufferedImage diffImage = comparator.generateDiffImage(img1, img2);
                String diffFilename = "diff_" + new File(file1).getName();
                ImageIO.write(diffImage, "png", new File(diffFilename));
                System.out.println("Image différentielle sauvegardée: " + diffFilename);
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}