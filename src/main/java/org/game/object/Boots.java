package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Image;

public class Boots extends SuperObject {
    
    public Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        image = Image.boots;
    }
}
