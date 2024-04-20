package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Image;

public class Diamond extends SuperObject {

    public Diamond(GamePanel gp) {

        super(gp);

        name = "Diamond";
        image = Image.diamond;
    }
    
}
