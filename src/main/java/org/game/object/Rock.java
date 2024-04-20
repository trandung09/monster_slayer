package org.game.object;

import org.game.character.Projectiles;
import org.game.frame.GamePanel;
import org.game.helper.Image;

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

        up1    = Image.getImage("/object/rock_down_1", width, height);
        up2    = Image.getImage("/object/rock_down_1", width, height);
        down1  = Image.getImage("/object/rock_down_1", width, height);
        down2  = Image.getImage("/object/rock_down_1", width, height);
        left1  = Image.getImage("/object/rock_down_1", width, height);
        left2  = Image.getImage("/object/rock_down_1", width, height);
        right1 = Image.getImage("/object/rock_down_1", width, height);
        right2 = Image.getImage("/object/rock_down_1", width, height);
    }
}
