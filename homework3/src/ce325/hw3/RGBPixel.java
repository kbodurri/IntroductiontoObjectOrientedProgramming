/*
 * Describes a pixel (RGB).
 */
package ce325.hw3;

/**
 *
 * @author klajdi bodurri && eirini tsitsopoulou
 */
public class RGBPixel {
    private int pixel = 0;
    
    /* Saves the pixel based on red, green and blue numbers */
    public RGBPixel(short red, short green, short blue) {
        pixel = pixel | (red<<16) | (green << 8) | blue;
    }
    
    /* Copies the value of the given pixel to pixel */
    public RGBPixel(RGBPixel pixel) {
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    /* Returns the red value of the pixel */
    public short getRed() {
        short value = (short)((pixel & (255<<16))>> 16);
        return value;
    }
    
    /* Set the new red value to the pixel */
    public void setRed(short red) {
        pixel = (pixel & 0xFF00FFFF) | (red << 16);
    }
    
    /* Returns the green value of the pixel */
    public short getGreen() {
        short value = (short)((pixel & (255<<8))>>8);
        return value;
    }
    
    /* Set the new green value to the pixel */
    public void setGreen(short green) {
        pixel = (pixel & 0xFFFF00FF) | (green << 8);
    }

    /* Returns the blue value of the pixel */
    public short getBlue() {
        short value = (short)(pixel & 255);
        return value;
    }

    /* Set the new blue value to the pixel */
    public void setBlue(short blue) {
        pixel = (pixel & 0xFFFFFF00) | blue;
    }
}
