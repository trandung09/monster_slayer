package org.game.monster;

import java.awt.Rectangle;
import org.game.enums.Direction;
import org.game.frame.GamePanel;

public class GreenSlime extends Monster {
    
    public GreenSlime(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Green Slime";
        speed = 1;

        monsterExp = 3;
        objRan = 2;

        maxLife = 5;
        life = maxLife;

        direction = Direction.RIGHT;

        solidArea = new Rectangle(8, 8, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadSlimeImage();
    }

    private void loadSlimeImage() {
        
        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1 = getImage("/monster/greenslime_down_1", width, height);
        up2 = getImage("/monster/greenslime_down_2", width, height);
        down1 = getImage("/monster/greenslime_down_1", width, height);
        down2 = getImage("/monster/greenslime_down_2", width, height);
        left1 = getImage("/monster/greenslime_down_1", width, height);
        left2 = getImage("/monster/greenslime_down_2", width, height);
        right1 = getImage("/monster/greenslime_down_1", width, height);
        right2 = getImage("/monster/greenslime_down_2", width, height);
    }
}
