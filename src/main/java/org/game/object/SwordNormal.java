package org.game.object;

import org.game.frame.GamePanel;

/**
 *  Thanh kiem
 */
public class SwordNormal extends SuperObject {
    
    public SwordNormal(GamePanel gp) {

        super(gp);

        name = "sword";
        attackValue = 1;
        image = getImage("/object/sword_normal", GamePanel.tileSize, GamePanel.tileSize);
    }


}
