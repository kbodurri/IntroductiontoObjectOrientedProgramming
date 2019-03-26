/*
 * Implements the basic operations of the Image interface.
 */
package ce325.hw3;

/**
 *
 * @author klajdi bodurri && eirini tsitsopoulou
 */
public class RGBImage{
    private RGBPixel [][]image;
    private int maxColorDepth, width, height;
    
    /* Creates a new image */
    public RGBImage(int width, int height, int colordepth) {
        maxColorDepth = colordepth;
        this.width = width;
        this.height = height;
        image = new RGBPixel[width][height];
    }
    
    /* Copies a specific image to image */
    public RGBImage(RGBImage copyImg) {
        this(copyImg.getWidth(), copyImg.getHeight(), copyImg.getColorDepth());
        copyImage(copyImg);
    }
    
    /* Returns the width of the image */
    public int getWidth() {
        return width;
    }

    /* Returns the height of the image */
    public int getHeight() {
        return height;
    }
    
    /* Returns the color depth of the image */
    public int getColorDepth() {
        return maxColorDepth;
    }
    
    /* Returns a pixel of the image on position (i,j) */
    public RGBPixel getPixelOfImage(int i, int j) {
        return image[i][j];
    }
    
    /* Set a specific pixel to the position (i,j) of the image */
    public void setPixeltoImage(RGBPixel pixel, int i, int j) {
        image[i][j] = new RGBPixel(pixel);
    }
    
    /* Copies the specific image to image */
    private void copyImage(RGBImage copyImg) {
        int i, j;
        
        for (i=0; i<width; i++) {
            for (j=0; j<height; j++) {
                RGBPixel copyPixel = copyImg.getPixelOfImage(i, j);
                image[i][j] = new RGBPixel(copyPixel);
            }
        }
    }
    
    public static void main(String []args) {
        RGBImage tmp = new RGBImage(3,3, 255);
        System.out.println(tmp.getPixelOfImage(2,2));
    }
}
