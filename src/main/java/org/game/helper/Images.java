package org.game.helper;

import java.awt.image.BufferedImage;

import org.game.frame.GamePanel;

public class Images {

    private static int width  = GamePanel.tileSize;
    private static int height = GamePanel.tileSize;
    private static UtilityTool uTool = new UtilityTool();
    
    public static BufferedImage playerRight1 = getImage("/player/boy_right_1", width, height);
    public static BufferedImage playerRight2 = getImage("/player/boy_right_2", width, height);
    public static BufferedImage key          = getImage("/object/key", width, height);
    public static BufferedImage mana         = getImage("/object/mana", width, height);
    public static BufferedImage diamond      = getImage("/object/blueheart", width, height);
    public static BufferedImage coin         = getImage("/object/coin", width, height);
    public static BufferedImage sword        = getImage("/object/sword_normal", width, height);
    public static BufferedImage axe          = getImage("/object/axe", width, height);
    public static BufferedImage boots        = getImage("/object/boots", width, height);
    public static BufferedImage door         = getImage("/object/door_iron", width, height);
    public static BufferedImage _blank       = getImage("/object/heart_blank", width, height);
    public static BufferedImage _half        = getImage("/object/heart_half", width, height); 
    public static BufferedImage _full        = getImage("/object/heart_full", width, height);
    

    public static BufferedImage getImage(String imagePath, int width, int height) {
        return uTool.getImage(imagePath, width, height);
    }
}
