package org.game.object;

import java.awt.image.BufferedImage;

import org.game.frame.GamePanel;
import org.game.helper.UtilityTool;

public class Heart {
    
    private static final int width = GamePanel.tileSize;
    private static final int height = GamePanel.tileSize;

    private String name = "Heart";
    private static UtilityTool uTool = new UtilityTool();
    
    public static BufferedImage 
        _blank = uTool.getImage("/object/heart_blank", width, height),
        _half  = uTool.getImage("/object/heart_half", width, height),  
        _full  = uTool.getImage("/object/heart_full", width, height);
                                
    public String name() { return name; }
}
