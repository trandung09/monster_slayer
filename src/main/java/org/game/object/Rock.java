package org.game.object;

import org.game.character.Projectiles;
import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Rock extends Projectiles {
    
    GamePanel gp;

    public Rock(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Rock";
        speed = 2;
        maxLife = 80;
        life = maxLife;
        alive = false;
        damage = 1;

        loadRockImage();
    }
    
    public void loadRockImage() {
        int width  = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1    = Images.getImage("/object/rock_down_1", width, height);
        up2    = Images.getImage("/object/rock_down_1", width, height);
        down1  = Images.getImage("/object/rock_down_1", width, height);
        down2  = Images.getImage("/object/rock_down_1", width, height);
        left1  = Images.getImage("/object/rock_down_1", width, height);
        left2  = Images.getImage("/object/rock_down_1", width, height);
        right1 = Images.getImage("/object/rock_down_1", width, height);
        right2 = Images.getImage("/object/rock_down_1", width, height);
    }
}
