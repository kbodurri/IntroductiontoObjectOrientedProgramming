/*
 * Describes a YUV pixel.
 */
package ce325.hw3;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou. 
 */
public class YUVPixel {
    private int pixel = 0;
    
    /* Saves the pixel based on Y, U and V numbers */
    public YUVPixel(short Y, short U, short V) {
        pixel = pixel | (Y<<16) | (U << 8) | V;
    }
    
    /* Copies the value of the given pixel to pixel */
    public YUVPixel(YUVPixel pixel) {
        this(pixel.getY(), pixel.getU(), pixel.getV());
    }
    
    public YUVPixel(RGBPixel pixel) {
        RGBtoYUV(pixel);
    }
    
    /* Set the Y value to pixel */
    public void setY(short Y) {
        pixel = (pixel & 0xFF00FFFF) | (Y << 16);
    }
    
    /* Set the U value to the pixel */
    public void setU(short U) {
        pixel = (pixel & 0xFFFF00FF) | (U << 8);
    }
    
    /* Set the V value to the pixel */
    public void setV(short V) {
        pixel = (pixel & 0xFFFFFF00) | V;
    }
    
    /* Returns the Y value of the pixel */
    public short getY() {
        short value = (short)((pixel & (255<<16))>> 16);
        return value;
    }
    
    /* Returns the green value of the pixel */
    public short getU() {
        short value = (short)((pixel & (255<<8))>>8);
        return value;
    }
    
    /* Returns the blue value of the pixel */
    public short getV() {
        short value = (short)(pixel & 255);
        return value;
    }
    
    /* Converts a RGBPixel to YUV. */
    private void RGBtoYUV(RGBPixel pixel) {
        short Y, U, V;
        
        Y = (short) (((66 * pixel.getRed() + 129 * pixel.getGreen() + 25 * 
                pixel.getBlue() + 128) >> 8) + 16);
        
        U = (short) (((-38 * pixel.getRed() - 74 * pixel.getGreen() + 112 * 
                pixel.getBlue() + 128) >> 8) + 128);
        
        V = (short) (((112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128);
        
        setY(Y);
        setU(U);
        setV(V);
    }
    
    public String toString() {
        return "("+getY()+","+getU()+","+getV()+")";
    }
}
