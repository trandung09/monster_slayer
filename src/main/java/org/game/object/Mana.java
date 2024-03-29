package org.game.object;

import org.game.frame.GamePanel;

public class Mana extends SuperObject {

    public Mana(GamePanel gp) {

        super(gp);

        name = "Mana";
        image = getImage("/object/mana", GamePanel.tileSize, GamePanel.tileSize);
    }
}
