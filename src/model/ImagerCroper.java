package model;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.imageio.ImageIO;
 
public class ImagerCroper {
 
    static Rectangle clip;
    static boolean isClipAreaAdjusted = false;
    static BufferedImage clipped = null, img = null, bi = null;
    static Dimension size = null;
    static File outputfile;
    public static void main(String args[]) throws Exception {

    }
 
    
    public static void crop(int cropStartX, int cropStartY){
        try {
            String inputFileLocation = "Images/profile.jpeg";
            String outputFileLocation = "C:\\Users\\Lenovo\\Desktop\\desktop\\profile37.jpg";
            
            BufferedImage originalImage = readImage(inputFileLocation);
            
            int cropHeight = 700;
            int cropWidth  = 700;
//            int cropStartX = 370;
//            int cropStartY = 56;
            
            BufferedImage processedImage = cropMyImage(originalImage, cropWidth, cropHeight, cropStartX, cropStartY);
            
            writeImage(processedImage, outputFileLocation, "jpg");
            System.out.println("...Done");
        } catch (Exception ex) {
            Logger.getLogger(ImagerCroper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static BufferedImage cropMyImage(BufferedImage img, int cropWidth, int cropHeight, int cropStartX, int cropStartY) throws Exception {
        size = new Dimension(cropWidth, cropHeight);
        createClip(img, size, cropStartX, cropStartY);

        try {
            int w = clip.width;
            int h = clip.height;
            clipped = img.getSubimage(clip.x, clip.y, w, h);
            return clipped;
        } catch (RasterFormatException rfe) { System.out.println("Raster format error: " + rfe.getMessage()); return null; }
    }

    private static void createClip(BufferedImage img, Dimension size, int clipX, int clipY) throws Exception {
        if (clipX < 0) {
            clipX = 0;
            isClipAreaAdjusted = true;
        }
        
        if (clipY < 0) {
            clipY = 0;
            isClipAreaAdjusted = true;
        }

        if ((size.width + clipX) <= img.getWidth() && (size.height + clipY) <= img.getHeight()) {
            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;
        }
        else {

            if ((size.width + clipX) > img.getWidth())
                size.width = img.getWidth() - clipX;

            if ((size.height + clipY) > img.getHeight())
                size.height = img.getHeight() - clipY;

            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;
            isClipAreaAdjusted = true;
        }
    }

    public static BufferedImage readImage(String fileLocation) {
        try {
            img = ImageIO.read(new File(fileLocation));
            return img;
        } catch (IOException e) { e.printStackTrace(); return null; }
    }

    public static void writeImage(BufferedImage img, String fileLocation, String extension) {
        try {
            bi = img;
            outputfile = new File(fileLocation);
            ImageIO.write(bi, extension, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}