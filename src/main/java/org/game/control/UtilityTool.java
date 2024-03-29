package org.game.control;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UtilityTool {

    public BufferedImage getImage(String imagePath, int width, int height) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaleImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2D = scaleImage.createGraphics();

        g2D.drawImage(original, 0, 0, width, height, null);
        g2D.dispose();

        return scaleImage;
    }
}
