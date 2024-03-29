package org.game.object;

import org.game.frame.GamePanel;

public class Diamond extends SuperObject {

    public Diamond(GamePanel gp) {

        super(gp);

        name = "Diamond";
        attackValue = 0;

        image = getImage("/object/blueheart", GamePanel.tileSize, GamePanel.tileSize);
    }
    
}
