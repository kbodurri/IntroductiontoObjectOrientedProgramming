/*
 * Implements the basic operations of the Image interface.
 */
package ce325.hw3;
import java.lang.Math;
import java.util.Random;
/**
 *
 * @author klajdi bodurri && eirini tsitsopoulou
 */
public class RGBImage implements Image{
    private RGBPixel [][]image;
    private int maxColorDepth, width, height;
    
    /* Empty constructor */
    public RGBImage() {};
    
    /* Creates a new image */
    public RGBImage(int width, int height, int colordepth) {
        maxColorDepth = colordepth;
        this.width = width;
        this.height = height;
        image = new RGBPixel[height][width];
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
    public RGBPixel getPixel(int row, int col) {
        return image[row][col];
    }
    
    /* Set a specific pixel to the position (i,j) of the image */
    public void setPixel(int row, int col, RGBPixel pixel) {
        image[row][col] = new RGBPixel(pixel);
    }
    
    /* Copies the specific image to image */
    private void copyImage(RGBImage copyImg) {
        int i, j;
        
        for (i=0; i<height; i++) {
            for (j=0; j<height; j++) {
                RGBPixel copyPixel = copyImg.getPixel(i, j);
                image[i][j] = new RGBPixel(copyPixel);
            }
        }
    }
    
    /* converts the image to black and white */
    public void grayscale() {
        int i, j;
        short gray;
        RGBPixel currPixel;
        
        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                currPixel = image[i][j];
                gray = (short)(currPixel.getRed() * 0.3 + currPixel.getGreen() * 0.59 + currPixel.getBlue() * 0.11); 
                currPixel.setRed((gray));
                currPixel.setGreen((gray));
                currPixel.setBlue((gray));
            }
        }
    }
    
    /* doubles the size of the image */
    public void doublesize(){
        int i, j;
        RGBPixel [][]newImage = new RGBPixel[height*2][width*2];
        
        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                newImage[2*i][2*j] = new RGBPixel(image[i][j]);
                newImage[2*i+1][2*j] = new RGBPixel(image[i][j]);
                newImage[2*i][2*j+1] = new RGBPixel(image[i][j]);
                newImage[2*i+1][2*j+1] = new RGBPixel(image[i][j]);
            }
        }
        
        updateImage(newImage, width*2, height*2);
    }
    
    /* decreases the size of the image to the half */
    public void halfsize(){
        int i,j, halfHeight, halfWidth;
        short avgRed, avgGreen, avgBlue;
        
        /* half the size of the image */
        halfHeight = (int)Math.floor((double)height/2);
        halfWidth = (int)Math.floor((double)width/2);
        
        RGBPixel [][]newImage = new RGBPixel[halfHeight][halfWidth];
        
        /* calculate the average colour for each pixel */
        for (i=0; i<halfHeight; i++) {
            for (j=0; j<halfWidth; j++) {
                if (i == halfHeight-1 && halfHeight != 1) {
                    avgRed = calculateAvgColor(new short [] {image[2*i][2*j].getRed(), 
                                                            image[2*i][2*j+1].getRed()});
                    avgGreen = calculateAvgColor(new short [] {image[2*i][2*j].getGreen(),
                                                               image[2*i][2*j+1].getGreen()});
                    avgBlue = calculateAvgColor(new short[] {image[2*i][2*j].getBlue(),
                                                               image[2*i][2*j+1].getBlue()});
                } else {
                    avgRed = calculateAvgColor(new short [] {image[2*i][2*j].getRed(),
                        image[2*i][2*j+1].getRed(), image[2*i+1][2*j].getRed(),
                        image[2*i+1][2*j+1].getRed()});
                    
                    avgGreen = calculateAvgColor(new short [] {image[2*i][2*j].getGreen(),
                        image[2*i][2*j+1].getGreen(), image[2*i+1][2*j].getGreen(),
                        image[2*i+1][2*j+1].getGreen()});
                    
                    avgBlue = calculateAvgColor(new short [] {image[2*i][2*j].getBlue(),
                        image[2*i][2*j+1].getBlue(), image[2*i+1][2*j].getBlue(),
                        image[2*i+1][2*j+1].getBlue()});
                }
                newImage[i][j] = new RGBPixel(avgRed, avgGreen, avgBlue);
            }
        }
        updateImage(newImage, halfWidth, halfHeight);
    }
    
    /* rotates the image 90degrees to the right */
    public void ratateClockwise(){
        int i, j, pos, tmp;
        RGBPixel currPixel = null;
        RGBPixel [][]rotatedImage = new RGBPixel[width][height];
        
        for (i=0; i<height; i++) {
            
            /* find the proper column in the rotated image */
            if (i == 0){ 
                pos = height - 1;
            }
            else if (i == height - 1) {
                pos = 0;
            }
            else {
                pos = i;
            }
            
            /* save the i-th row of the image to the pos-th column of the rotated image */
            for (j=0; j<width; j++) {
                rotatedImage[j][pos] = image[i][j];
            }
        }
        
        /* Update the image */
        updateImage(rotatedImage, height, width);
    }
    
    /* Create a new empty image */
    public void createDummyImage(int width, int height, int colordepth) {
        maxColorDepth = colordepth;
        this.width = width;
        this.height = height;
        image = new RGBPixel[this.height][this.width];
    }
    
    public String toString() {
        String toStringImage = "";
        int i, j;
        
        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                toStringImage += image[i][j].getRed() + " ";
            }
            toStringImage += '\n';
        }
        return toStringImage;
    }
    
    /* Update image and its size */
    private void updateImage(RGBPixel [][]updatedImage, int updatedWidth, int updatedHeight) {
        image = updatedImage;
        width = updatedWidth;
        height = updatedHeight;
    }
    
    /* Calculates the avg value of an array of colours */
    private short calculateAvgColor(short []array) {
        int i;
        int sum=0;
        for (i=0; i<array.length; i++){
            sum += array[i];
        }
        return (short)(sum/array.length);
    } 
    
    /*
    public static void main(String []args) {
        Random rand = new Random();
        RGBImage img = new RGBImage(2,2, 255);
        int i, j;
        short red, green, blue;
        
        for (i=0; i<2; i++) {
            for (j=0; j<2; j++) {
                red = (short)rand.nextInt(255);
                green = (short)rand.nextInt(255);
                blue = (short)rand.nextInt(255);
                RGBPixel tmp = new RGBPixel(red, green, blue);
                img.setPixel(tmp, i, j);
            }
        }
        System.out.println(img.toString());
        img.halfsize();
        System.out.println(img.toString());
    }
    */
}
