package org.game.object;

import org.game.frame.GamePanel;

public class Boots extends SuperObject {
    
    public Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        image = getImage("/object/boots", GamePanel.tileSize, GamePanel.tileSize);
    }
}
