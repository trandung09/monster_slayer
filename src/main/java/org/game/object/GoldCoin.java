package org.game.object;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class GoldCoin extends SuperObject {
    
    public GoldCoin(GamePanel gp) {

        super(gp);

        name = "GoldCoin";
        image = Images.coin;
    }
}
