
package edu.gatech.arktos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public abstract class ResourcesDispatcher {
    
    public static Image getImage(String fileName) throws ResourceException {
        InputStream streamedImage = ResourcesDispatcher.class.getResourceAsStream(fileName);
        if (streamedImage == null) {
            throw new ResourceException(ResourceException.RESOURCE_IMAGE, fileName, "Resource not found");
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(streamedImage);
        }
        catch (IOException ex) {
            throw new ResourceException(ResourceException.RESOURCE_IMAGE, fileName, ex.getMessage(), ex);
        }
        return bufferedImage;
    }
    
    public static Font getFont(String fileName, int fontType) throws ResourceException {
        InputStream streamedFont = ResourcesDispatcher.class.getResourceAsStream(fileName);
        if (streamedFont == null) {
            throw new ResourceException(ResourceException.RESOURCE_FONT, fileName, "Resource not found");
        }
        Font font = null;
        try {
            font = Font.createFont(fontType, streamedFont);
        } catch (FontFormatException ex) {
            throw new ResourceException(ResourceException.RESOURCE_FONT, fileName, "Font format exception: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new ResourceException(ResourceException.RESOURCE_FONT, fileName, ex.getMessage(), ex);
        }
        return font;
    }
    
}
