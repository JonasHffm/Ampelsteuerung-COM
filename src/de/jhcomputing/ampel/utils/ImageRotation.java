package de.jhcomputing.ampel.utils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

// Rotates an image 90 degrees clockwise/counter clockwise using AffineTransform in Java
// Preserves the full image.
public class ImageRotation {
    private static final String PATH_TO_IMAGE="";
    private static final String INPUT_FILE_NAME="AmpelDefault.png";
    private static final String OUTPUT_FILE_NAME = "AmpelDefault.png";
     
    public static void main(String[] args ) throws Exception{
        ImageRotation demo = new ImageRotation();
        demo.rotateImage();
    }
 
    private void rotateImage() throws Exception {
        BufferedImage source = ImageIO.read(new File(PATH_TO_IMAGE+INPUT_FILE_NAME));
         
        BufferedImage output = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
            
        AffineTransformOp op = new AffineTransformOp(rotateCounterClockwise90(source), AffineTransformOp.TYPE_BILINEAR);
        op.filter(source, output);
 
        ImageIO.write(output, "png", new File(OUTPUT_FILE_NAME));
         
    }
     
    // Rotates clockwise 90 degrees. Uses rotation on center and then translating it to origin
    private AffineTransform rotateClockwise90(BufferedImage source) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI/2, source.getWidth()/2, source.getHeight()/2);
        double offset = (source.getWidth()-source.getHeight())/2;
        transform.translate(offset,offset);
        return transform;
    }
     
    // Rotates counter clockwise 90 degrees. Uses rotation on center and then translating it to origin
    private AffineTransform rotateCounterClockwise90(BufferedImage source) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(-Math.PI/2, source.getWidth()/2, source.getHeight()/2);
        double offset = (source.getWidth()-source.getHeight())/2;
        transform.translate(-offset,-offset);
        return transform;
    }
     
}