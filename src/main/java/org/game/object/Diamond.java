package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Diamond extends SuperObject {

    public Diamond(GamePanel gp) {

        super(gp);

        name = "Diamond";
        image = Images.diamond;
    }
    
}
