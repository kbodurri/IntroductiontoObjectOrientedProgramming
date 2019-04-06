/*
 * Implementation of histogram equalization.
 */
package ce325.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou.
 */
public class Histogram {
    private static final int MAXLUMOCITY = 235;
    private static final int MINLUMOCITY = 16;
    private static final int LENGTH = MAXLUMOCITY - MINLUMOCITY + 1; 
    private final int []pixelIntensity; 
    private final float []probability;
    private final float []cumulativeProbability;
    private final int []multipliedCumulativeProbability;
    private final YUVImage img;
    
    /* Initializes the object. */
    public Histogram(YUVImage img) {
        this.cumulativeProbability = new float[LENGTH];
        this.probability = new float[LENGTH];
        this.pixelIntensity = new int[LENGTH];
        this.multipliedCumulativeProbability = new int[LENGTH];
        this.img = img;
        
        intensity();
        probabilityCalculation();
        cumulativeProbabilityCalculation();
        multiplyCumulativeProbability();
    }
    
    /* Apply histogram equalization */
    public void equalize() {
        int i, j;
        short prevY;
        
        for (i=0; i<img.getHeight(); i++) {
            for (j=0; j<img.getWidth(); j++) {
                prevY = (short) (img.getPixel(i, j).getY() - 16);
                img.getPixel(i, j).setY((short) multipliedCumulativeProbability[prevY]);
            }
        }
    }
    
    /* Calculates the intensity of the pixels. */
    private void intensity() {
        int i, j;
        
        for (i=0; i<LENGTH; i++) {
            pixelIntensity[i] = 0;
        }
        
        for (i=0; i<img.getHeight(); i++) {
            for (j=0; j<img.getWidth(); j++) {
                pixelIntensity[img.getPixel(i, j).getY() - MINLUMOCITY] += 1;
            }
        }
    }
    
    /* Calculates the probability of pixels. */
    private void probabilityCalculation() {
        int i, totalPixels;
        
        totalPixels = img.getHeight()*img.getWidth();
        for (i=0; i<LENGTH; i++) {
            probability[i] = ((float) pixelIntensity[i])/totalPixels;
        }
    }
    
    /* Calculates the cumulative probability. */
    private void cumulativeProbabilityCalculation() {
        int i;
        cumulativeProbability[0] = probability[0];
        for (i=1; i<LENGTH; i++) {
            cumulativeProbability[i] = cumulativeProbability[i-1] + probability[i];
        }
    }
    
    /* Multiply the cumulative probability by MAXLUMOCITY */
    private void multiplyCumulativeProbability() {
        int i;
        
        for (i=0; i<LENGTH; i++) {
            multipliedCumulativeProbability[i] = (int) Math.floor(cumulativeProbability[i]*MAXLUMOCITY);
        }
    }
    
    public static void main(String []args) throws FileNotFoundException, UnsupportedFileFormatException, IOException {
         File einFile = new File("/home/klajdi/NetBeansProjects/project3/test/photos/PPM/ein.ppm");
         File einEqualized = new File("/home/klajdi/NetBeansProjects/project3/test/photos/PPM/equalized.ppm");
         
         YUVImage einYUV = new YUVImage(new PPMImage(einFile));
         Histogram einHist = new Histogram(einYUV);
         einHist.equalize();
         PPMImage einPPMequalized = new PPMImage(einYUV);
         einPPMequalized.toFile(einEqualized);
    }
}
