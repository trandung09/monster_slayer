package org.game.monster;

import java.awt.Rectangle;
import org.game.enums.Direction;
import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Bat extends Monster {
    
    public Bat(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Bat";
        speed = 2;

        monsterExp = 1;
        objRan = 1;

        maxLife = 2;
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

        up1    = Images.getImage("/monster/bat_down_1", width, height);
        up2    = Images.getImage("/monster/bat_down_2", width, height);
        down1  = Images.getImage("/monster/bat_down_1", width, height);
        down2  = Images.getImage("/monster/bat_down_2", width, height);
        left1  = Images.getImage("/monster/bat_down_1", width, height);
        left2  = Images.getImage("/monster/bat_down_2", width, height);
        right1 = Images.getImage("/monster/bat_down_1", width, height);
        right2 = Images.getImage("/monster/bat_down_2", width, height);
    }

    @Override
    public void update() {

        setAction();

        collisionOn = false;

        if (!collisionOn) {
            int width  = GamePanel.tileSize * GamePanel.maxScreenCol;
            int height = GamePanel.tileSize * GamePanel.maxScreenRow;
            switch (direction) {
                case UP: 
                    if (worldY - speed > 0) 
                        worldY -= speed;
                    break;
                case DOWN: 
                    if (worldY - speed < width)
                        worldY += speed; 
                    break;
                case LEFT:
                    if (worldX - speed > 0) 
                        worldX -= speed;
                    break;
                case RIGHT:   if (worldX - speed < height) 
                    worldX += speed; 
                break;
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
