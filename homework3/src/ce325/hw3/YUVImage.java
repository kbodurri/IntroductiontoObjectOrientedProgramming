/*
 * Describes a YUV image.
 */
package ce325.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou.
 */
public class YUVImage {
    private YUVPixel [][]image;
    private int width, height;
    
    /* Empty constructor */
    public YUVImage() {};
    
    /* Creates an empty image */
    public YUVImage(int width, int height) {
        this.width = width;
        this.height = height;
        image = new YUVPixel[height][width];
        initImage();
    }
    
    /* Creates an image from copyImg */
    public YUVImage(YUVImage copyImg) {
        this(copyImg.getWidth(), copyImg.getHeight());
        copyImage(copyImg);
    }
    
    /* Creates an image from RGBImg */
    public YUVImage(RGBImage RGBImg) {
        this(RGBImg.getWidth(), RGBImg.getHeight());
        copyImage(RGBImg);
    }
    
    /* Creates an image from the file */
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        readImage(file);
    }
        
    /* Returns the width of the image */
    public int getWidth() {
        return width;
    }

    /* Returns the height of the image */
    public int getHeight() {
        return height;
    }
        
    /* Returns a pixel of the image on position (i,j) */
    public YUVPixel getPixel(int row, int col) {
        return image[row][col];
    }
    
    /* Sets a new pixel to the position (i,j) of the image */
    public void setPixel(int row, int col, YUVPixel pixel) {
        image[row][col] = pixel;
    }
    
    /* Copies the specific image to image */
    private void copyImage(YUVImage copyImg) {
        int i, j;
        
        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                YUVPixel copyPixel = copyImg.getPixel(i, j);
                setPixel(i, j, new YUVPixel(copyPixel));
            }
        }
    }
    
    /* Copies the RGBImage to image */
    private void copyImage(RGBImage copyImg) {
        int i, j;
        
        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                setPixel(i, j, new YUVPixel(copyImg.getPixel(i, j)));
            }
        }
    }
    
    /* Reads the YUV image. */
    private void readImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException {
        /* check if file exists and if it is readable */
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            throw new java.io.FileNotFoundException();
        }
        
        int localWidth=0, localHeight=0, i, j;
        short Y, U, V;
        try (Scanner sc = new Scanner(file)) {
            i=0;
            OUTER:
            while (sc.hasNext()) {
                switch (i) {
                    case 0:
                        String tmp = sc.next();
                        if (!"YUV3".equals(tmp)) {
                            throw new UnsupportedFileFormatException();
                        }   break;
                    case 1:
                        localWidth = sc.nextInt();
                        break;
                    default:
                        localHeight = sc.nextInt();
                        break OUTER;
                }
                i++;
            }
            
            /* chech if image width, height or colordepth are valid */
            if (localWidth == 0 || localHeight == 0) {
                throw new UnsupportedFileFormatException();
            }
            
            /* create the image */
            this.width = localWidth;
            this.height = localHeight;
            image = new YUVPixel[height][width];
            
            /* read the color for each pixel */
            for (i=0; i<height; i++) {
                for (j=0; j<width; j++) {
                    Y = sc.nextShort();
                    U = sc.nextShort();
                    V = sc.nextShort();
                    setPixel(i, j, new YUVPixel(Y,U,V));
                }
            }
        }
    }
    
    /* Uses the histogram equalization method for the YUV image */
    public void equalize() {
        Histogram hist = new Histogram(this);
        hist.equalize();
    }

    @Override
    public String toString() {
        StringBuilder yuv = new StringBuilder(String.format("YUV3\n %d %d\n", width, height));
        int i, j;
        YUVPixel tmp;

        for (i=0; i<height; i++) {
            for (j=0; j<width; j++) {
                tmp = image[i][j];
                yuv.append(String.format("%d %d %d\n", tmp.getY(), tmp.getU(), tmp.getV()));
            }
        }
        return yuv.toString();
    }
    
    /* Creates a ppm file */
    public void toFile(java.io.File file) throws IOException {
        createNewFile(file);
        
        try (PrintWriter outputStream = new  PrintWriter(new FileWriter(file))) {
            outputStream.print(toString());
        } catch (Exception ex) {
            ex.printStackTrace();
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
    
    /* Initializes the image */
    private void initImage() {
        int i,j;
        for (i=0; i<getHeight(); i++) {
            for (j=0; j<getWidth(); j++) {
                setPixel(i, j, new YUVPixel((short)16, (short)128, (short)128));
            }
        }
    }
}
