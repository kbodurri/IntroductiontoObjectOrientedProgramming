/*
 * Applies the image stacking method to a directory of images.
 */
package ce325.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou.
 */
public class PPMImageStacker {
    private List<java.io.File> imageFiles;
    private PPMImage stackedImage = null;
    
    /* Initializes/read the images */
    public PPMImageStacker(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        if (!file.exists()) {
            System.out.println("[ERROR] Directory"+file.toString()+" does not exist!");
            return;
        }
        else if (!file.isDirectory()) {
            System.out.println("[ERROR] "+file.toString()+" is not a directory");
            return;
        }
        
        imageFiles = new ArrayList<>();
        getImages(file);
    }
    
    public PPMImage getStackedImage() {
        return stackedImage;
    }
    
    public void stack() {
        RGBImage stackedRGBImage;
        PPMImage tmpImage;
        Iterator<java.io.File> iter;
        iter = imageFiles.iterator();
        int [][]sumRed;
        int [][]sumGreen;
        int [][]sumBlue;
        int i, j, width, height, colorDepth;
        short avgRed, avgGreen, avgBlue;
        
        try{
            // Read the first image and initialize a new RGBImage with the same size.
            tmpImage = new PPMImage(imageFiles.get(0));
            
            width = tmpImage.getWidth();
            height = tmpImage.getHeight();
            colorDepth = tmpImage.getColorDepth();
            stackedRGBImage = new RGBImage(width, height, colorDepth);
            
            sumRed = new int[height][width];
            sumGreen = new int[height][width];
            sumBlue = new int[height][width];
            
            for (i=0; i<height; i++) {
                for (j=0; j<width; j++) {
                    sumRed[i][j] = 0;
                    sumGreen[i][j] = 0;
                    sumBlue[i][j] = 0;
                }
            }
            
            while(iter.hasNext()) {
                //System.out.println("hello");
                tmpImage = new PPMImage(iter.next());
                
                for (i=0; i<height; i++) {
                    for (j=0; j<width; j++) {
                        sumRed[i][j] += tmpImage.getPixel(i, j).getRed();
                        sumGreen[i][j] += tmpImage.getPixel(i, j).getGreen();
                        sumBlue[i][j] += tmpImage.getPixel(i, j).getBlue();
                    }
                } 
            }
            
            for (i=0; i<height; i++) {
                for (j=0; j<width; j++) {
                    avgRed = (short) (sumRed[i][j]/imageFiles.size());
                    avgGreen = (short) (sumGreen[i][j]/imageFiles.size());
                    avgBlue = (short) (sumBlue[i][j]/imageFiles.size());
                    stackedRGBImage.setPixel(i, j, new RGBPixel(avgRed, avgGreen, avgBlue));
                }
            }            
            
            // create the stacked ppm image.
            stackedImage = new PPMImage(stackedRGBImage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void getImages(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException {
         for (File file: dir.listFiles()) {
             if (file.isFile()) {
                 imageFiles.add(new File(file.toString()));
             }
         }
    }
}
