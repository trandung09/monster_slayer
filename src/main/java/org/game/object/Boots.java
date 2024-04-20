package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Boots extends SuperObject {
    
    public Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        image = Images.boots;
    }
}
