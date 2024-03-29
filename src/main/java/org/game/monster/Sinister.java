package org.game.monster;

import java.awt.Rectangle;

import org.game.enums.Direction;
import org.game.frame.GamePanel;

public class Sinister extends Monster {
    
    public Sinister(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Sinister";
        speed = 2;

        monsterExp = 5;
        objRan = 2;

        maxLife = 8;
        life = maxLife;

        direction = Direction.RIGHT;

        solidArea = new Rectangle(8, 8, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadSlimeImage();
        loadSinisterAttackImage();
    }

    private void loadSlimeImage() {

        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1 = getImage("/monster/boy_up_1", width, height);
        up2 = getImage("/monster/boy_up_2", width, height);
        down1 = getImage("/monster/boy_down_1", width, height);
        down2 = getImage("/monster/boy_down_2", width, height);
        left1 = getImage("/monster/boy_left_1", width, height);
        left2 = getImage("/monster/boy_left_2", width, height);
        right1 = getImage("/monster/boy_right_1", width, height);
        right2 = getImage("/monster/boy_right_2", width, height);
    }

    private void loadSinisterAttackImage() {

        int width = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        attackUp1 = getImage("/monster/boy_attack_up_1", width / 2, height);
        attackUp2 = getImage("/monster/boy_attack_up_2", width / 2, height);
        attackDown1 = getImage("/monster/boy_attack_down_1", width / 2, height);
        attackDown2 = getImage("/monster/boy_attack_down_2", width / 2, height);
        attackLeft1 = getImage("/monster/boy_attack_left_1", width, height / 2);
        attackLeft2 = getImage("/monster/boy_attack_left_2", width, height / 2);
        attackRight1 = getImage("/monster/boy_attack_right_1", width, height / 2);
        attackRight2 = getImage("/monster/boy_attack_right_2", width, height / 2);
    }
}
