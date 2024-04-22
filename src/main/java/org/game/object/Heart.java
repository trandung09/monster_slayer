package org.game.object;

import java.awt.image.BufferedImage;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Heart {
    
    private static final int width = GamePanel.tileSize;
    private static final int height = GamePanel.tileSize;

    private String name = "Heart";
    
    public static BufferedImage 
        _blank = Images.getImage("/object/heart_blank", width, height),
        _half  = Images.getImage("/object/heart_half", width, height),  
        _full  = Images.getImage("/object/heart_full", width, height);
                                
    public String name() { return name; }
}
