package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Door extends SuperObject{

    public Door(GamePanel gp) {
        super(gp);

        name = "Door";
        image = Images.door;
    }
}
