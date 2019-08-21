/*
 * Custom exception.
 */
package ce325.hw3;

/**
 *
 * @author Klajdi Bodurri && Eirini Tsitsopoulou
 */
public class UnsupportedFileFormatException extends java.lang.Exception {
    public UnsupportedFileFormatException(){};
    public UnsupportedFileFormatException(String msg){
        super(msg);
    };
}
