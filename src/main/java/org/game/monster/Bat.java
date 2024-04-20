package org.game.monster;

import java.awt.Rectangle;
import org.game.enums.Direction;
import org.game.frame.GamePanel;
import org.game.helper.Image;

public class Bat extends Monster {
    
    public Bat(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Bat";
        speed = 1;

        monsterExp = 2;
        objRan = 1;

        maxLife = 4;
        life = maxLife;

        direction = Direction.RIGHT;

        solidArea = new Rectangle(8, 8, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadBatImage();
    }

    private void loadBatImage() {

        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1    = Image.getImage("/monster/bat_down_1", width, height);
        up2    = Image.getImage("/monster/bat_down_2", width, height);
        down1  = Image.getImage("/monster/bat_down_1", width, height);
        down2  = Image.getImage("/monster/bat_down_2", width, height);
        left1  = Image.getImage("/monster/bat_down_1", width, height);
        left2  = Image.getImage("/monster/bat_down_2", width, height);
        right1 = Image.getImage("/monster/bat_down_1", width, height);
        right2 = Image.getImage("/monster/bat_down_2", width, height);
    }

    @Override
    public void update() {

        setAction();

        collisionOn = false;

        if (!collisionOn) {
            switch (direction) {
                case UP: worldY -= speed; break;
                case DOWN: worldY += speed; break;
                case LEFT: worldX -= speed; break;
                case RIGHT: worldX += speed; break;
            }
        }
        // Cập nhật trạng thái vô địch cho thực thể
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    @Override
    public void resetAction() {
        
        switch (direction) {
            case UP: direction = Direction.DOWN; break;
            case DOWN: direction = Direction.UP; break;
            case LEFT: direction = Direction.LEFT; break;
            case RIGHT: direction = Direction.RIGHT; break;
            default:
                break;
        }
    }
}
