/*
 * An interface that has basic operations on images.
 */
package ce325.hw3;

/**
 *
 * @author klajdi bodurri && eirini tsitsopoulou
 */
public interface Image {
    /* converts the image to black and white */
    public void grayscale();
    
    /* doubles the size of the image */
    public void doublesize();
    
    /* decreases the size of the image to the half */
    public void halfsize();
    
    /* rotates the image 90degrees to the right */
    public void ratateClockwise();
}
