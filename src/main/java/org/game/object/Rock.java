package org.game.object;

import org.game.character.Projectiles;
import org.game.frame.GamePanel;

public class Rock extends Projectiles {
    
    GamePanel gp;

    public Rock(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Rock";
        speed = 3;
        maxLife = 80;
        life = maxLife;
        alive = false;
        damage = 1;

        setImage();
    }
    
    public void setImage() {
        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;
        up1 = getImage("/object/rock_down_1", width, height);
        up2 = getImage("/object/rock_down_1", width, height);
        down1 = getImage("/object/rock_down_1", width, height);
        down2 = getImage("/object/rock_down_1", width, height);
        left1 = getImage("/object/rock_down_1", width, height);
        left2 = getImage("/object/rock_down_1", width, height);
        right1 = getImage("/object/rock_down_1", width, height);
        right2 = getImage("/object/rock_down_1", width, height);
    }
}
