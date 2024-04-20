package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Image;

public class Mana extends SuperObject {

    public Mana(GamePanel gp) {

        super(gp);

        name = "Mana";
        image = Image.mana;
    }
}
