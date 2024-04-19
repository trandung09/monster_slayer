package org.game.object;

import org.game.character.Projectiles;
import org.game.frame.GamePanel;

public class Fireball extends Projectiles {
    
    GamePanel gp;

    public Fireball(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Fireball";
        speed = 6;
        maxLife = 80;
        life = maxLife;
        alive = false; // nếu còn sống thì vẫn còn vẽ 
        damage = 1;

        setImage();
    }
    
    public void setImage() {
        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;
        up1 = getImage("/object/fireball_up_1", width, height);
        up2 = getImage("/object/fireball_up_2", width, height);
        down1 = getImage("/object/fireball_down_1", width, height);
        down2 = getImage("/object/fireball_down_2", width, height);
        left1 = getImage("/object/fireball_left_1", width, height);
        left2 = getImage("/object/fireball_left_2", width, height);
        right1 = getImage("/object/fireball_right_1", width, height);
        right2 = getImage("/object/fireball_right_2", width, height);
    }
}
