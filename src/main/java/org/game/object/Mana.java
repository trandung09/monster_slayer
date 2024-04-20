package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Mana extends SuperObject {

    public Mana(GamePanel gp) {

        super(gp);

        name = "Mana";
        image = Images.mana;
    }
}
