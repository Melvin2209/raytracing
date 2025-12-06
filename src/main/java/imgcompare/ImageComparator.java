
package imgcompare;

import java.awt.image.BufferedImage;

public class ImageComparator {
    private static final int THRESHOLD = 1000;
    
    public int countDifferentPixels(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            throw new IllegalArgumentException("Images must have the same dimensions");
        }
        
        int differentPixels = 0;
        
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    differentPixels++;
                }
            }
        }
        
        return differentPixels;
    }
    
    public BufferedImage generateDiffImage(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            throw new IllegalArgumentException("Images must have the same dimensions");
        }
        
        BufferedImage diffImage = new BufferedImage(
            img1.getWidth(), 
            img1.getHeight(), 
            BufferedImage.TYPE_INT_RGB
        );
        
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                
                if (rgb1 == rgb2) {
                    diffImage.setRGB(x, y, 0x000000); // Black for identical pixels
                } else {
                    int r1 = (rgb1 >> 16) & 0xFF;
                    int g1 = (rgb1 >> 8) & 0xFF;
                    int b1 = rgb1 & 0xFF;
                    
                    int r2 = (rgb2 >> 16) & 0xFF;
                    int g2 = (rgb2 >> 8) & 0xFF;
                    int b2 = rgb2 & 0xFF;
                    
                    int diffR = Math.abs(r1 - r2);
                    int diffG = Math.abs(g1 - g2);
                    int diffB = Math.abs(b1 - b2);
                    
                    int diffRGB = (diffR << 16) | (diffG << 8) | diffB;
                    diffImage.setRGB(x, y, diffRGB);
                }
            }
        }
        
        return diffImage;
    }
    
    public boolean areImagesIdentical(int differentPixels) {
        return differentPixels < THRESHOLD;
    }
}