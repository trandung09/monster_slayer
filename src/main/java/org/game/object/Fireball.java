package org.game.object;

import org.game.character.Projectiles;
import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Fireball extends Projectiles {

    public Fireball(GamePanel gp) {
        super(gp);

        name = "Fireball";
        speed = 6;
        maxLife = 80;
        life = maxLife;
        alive = false; // nếu còn sống thì vẫn còn vẽ 
        damage = 3;

        loadFireballImage();
    }
    
    public void loadFireballImage() {
        int width  = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1    = Images.getImage("/object/fireball_up_1", width, height);
        up2    = Images.getImage("/object/fireball_up_2", width, height);
        down1  = Images.getImage("/object/fireball_down_1", width, height);
        down2  = Images.getImage("/object/fireball_down_2", width, height);
        left1  = Images.getImage("/object/fireball_left_1", width, height);
        left2  = Images.getImage("/object/fireball_left_2", width, height);
        right1 = Images.getImage("/object/fireball_right_1", width, height);
        right2 = Images.getImage("/object/fireball_right_2", width, height);
    }
}
