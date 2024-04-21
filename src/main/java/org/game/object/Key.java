package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Key extends SuperObject {
    
    public Key(GamePanel gp) {

        super(gp);

        name = "Key";
        image = Images.key;
    }
}
