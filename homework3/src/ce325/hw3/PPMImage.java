/*
 * Describes a PPM image.
 */
package ce325.hw3;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou
 */
public class PPMImage extends RGBImage {
    
    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        super();
        readImage(file);
    }
    
    public PPMImage(RGBImage image) {
        super(image);
    }
    
    public PPMImage(YUVImage image) {
        super(image);
    }
    
    /* Reads the ppm image. */
    private void readImage(java.io.File file) throws UnsupportedFileFormatException, FileNotFoundException {
        /* check if file exists and if it is readable */
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            throw new java.io.FileNotFoundException();
        }
        
        int width=0, height=0, colordepth=0, i, j;
        short red, green, blue;
        try (Scanner sc = new Scanner(file)) {
            i=0;
            OUTER:
            while (sc.hasNext()) {
                switch (i) {
                    case 0:
                        String tmp = sc.next();
                        if (!"P3".equals(tmp)) {
                            throw new UnsupportedFileFormatException();
                        }   break;
                    case 1:
                        width = sc.nextInt();
                        break;
                    case 2:
                        height = sc.nextInt();
                        break;
                    default:
                        colordepth = sc.nextInt();
                        break OUTER;
                }
                i++;
            }
            
            /* chech if image width, height or colordepth are valid */
            if (width == 0 || height == 0 || colordepth > 255) {
                throw new UnsupportedFileFormatException();
            }
            
            /* create a dummy rgb image */
            super.createDummyImage(width, height, colordepth);
            
            /* read the color for each pixel */
            for (i=0; i<height; i++) {
                for (j=0; j<width; j++) {
                    red = sc.nextShort();
                    green = sc.nextShort();
                    blue = sc.nextShort();
                    super.setPixel(i, j, new RGBPixel(red, green, blue));
                }
            }
        }
    }
    
    /* Returns the data of the ppm image */
    @Override
    public String toString() {
        StringBuilder ppm = new StringBuilder(String.format("P3\n %d %d %d\n", 
                    super.getWidth(), super.getHeight(), super.getColorDepth()));
        int i, j;
        RGBPixel tmp;

        for (i=0; i<super.getHeight(); i++) {
            for (j=0; j<super.getWidth(); j++) {
                tmp = super.getPixel(i, j);
                ppm.append(String.format("%d %d %d\n", tmp.getRed(), tmp.getGreen(), tmp.getBlue()));
            }
        }
        return ppm.toString();
    }
    
    /* Creates a ppm file */
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
    
    public static void main(String []args) throws FileNotFoundException, UnsupportedFileFormatException, IOException {
        File file = new File("/home/klajdi/NetBeansProjects/project3/test/photos/PPM/tmp.ppm");
        PPMImage image = new PPMImage(file);
    }
}
