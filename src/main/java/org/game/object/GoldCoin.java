package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Image;

public class GoldCoin extends SuperObject {
    
    public GoldCoin(GamePanel gp) {

        super(gp);

        name = "GoldCoin";
        image = Image.coin;
    }
}
