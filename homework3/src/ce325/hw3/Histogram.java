/*
 * Implementation of histogram equalization.
 */
package ce325.hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays; 
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
    
    /* Returns the equalized value of Y (lumocity) */
    public short getEqualizedLuminocity(int luminocity) {
        return (short) multipliedCumulativeProbability[luminocity-16];
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
    
    /* finds the max value of an array of integers */
    private int findMax(int []myArray) {
        int max = -1;
        
        for (int value: myArray) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    
    /* Creates a string with * based on the histogram */
    @Override
    public String toString() {
        StringBuilder hist = new StringBuilder();
        int i, j;
        
        int scale;
        scale = (int) Math.floor(findMax(pixelIntensity)/80);
        System.out.println(scale);
        System.out.println(findMax(pixelIntensity));
        
        for (i=0; i<LENGTH; i++) {
            hist.append(i+16).append(" ");
            for (j=0; j<(int) pixelIntensity[i]/scale; j++) {
                hist.append("*");
            }
            hist.append('\n');
        }
        
        return hist.toString();
    }
    
    /* Save the string from toString method into a file */
    public void toFile(java.io.File file) throws IOException {
        createNewFile(file);
        
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file))) {
            bufferWriter.write(toString());
        }
        
    }
    
    private void createNewFile(java.io.File file) {
        /* delete it */
        if (file.exists()) {
            file.delete();
        }
        
        /* create a new file */
        try {
            file.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
}
