package org.game.character;

import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.enums.Direction;
import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Oldman extends Entity {
    
    public Oldman(GamePanel gp) {

        super(gp);

        setOldmanInfor();
        loadOlmanImage();
    }

    private void setOldmanInfor() {

        name = "Old man";
        maxLife = 6;
        life = maxLife;

        speed = 1;
        
        direction = Direction.LEFT;
        drawChecker = true;

        solidArea = new Rectangle(12, 8, 24, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void loadOlmanImage() {

        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;
        
        up1    = Images.getImage("/npc/oldman_up_1", width, height);
        up2    = Images.getImage("/npc/oldman_up_2", width, height);
        down1  = Images.getImage("/npc/oldman_down_1", width, height);
        down2  = Images.getImage("/npc/oldman_down_2", width, height);
        left1  = Images.getImage("/npc/oldman_left_1", width, height);
        left2  = Images.getImage("/npc/oldman_left_2", width, height);
        right1 = Images.getImage("/npc/oldman_right_1", width, height);
        right2 = Images.getImage("/npc/oldman_right_2", width, height);
    }

    /**
     * Cập nhật hành động của nhân vật
     */
    @Override
    protected void setAction() {
        // Cập nhật hình ảnh nhân vật cần vẽ ở mỗi thời điểm để tạo hoạt ảnh nhân vật
        drawCounter++;
        if (drawCounter == 10) {
            drawChecker = !drawChecker;
            drawCounter = 0;
        }

        // Cập nhật hành động, hướng di chuyển của nhân vật
        actionCounter++;
        if (actionCounter == 120) {
            Random random = new Random();
            int n = random.nextInt(100) + 1;
            
            if (n > 0 && n <= 25) direction = Direction.UP;
            else if (n > 25 && n <= 50) direction = Direction.DOWN;
            else if (n < 50 && n <= 75) direction = Direction.LEFT;
            else direction = Direction.RIGHT;
            
            actionCounter = 0;
        }
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
    }
    
    @Override
    public void draw(Graphics2D g2D) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Kiểm tra vị trí của nhân vật với vị trí hiến tại của thực thể,
        // nếu vị trí nhỏ hơn một khoảng X mới bắt đầu vẽ ảnh thực thể
        if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY
        ) {

            BufferedImage image = null;
            // Xử lý dựa vào hướng di chuyển của thực thể
            switch (direction) {
                case UP: image = (drawChecker ? up1 : up2); break;
                case DOWN: image = (drawChecker ? down1 : down2); break;
                case LEFT: image = (drawChecker ? left1 : left2); break;
                case RIGHT: image = (drawChecker ? right1 : right2); break;
                default:
                    throw new IllegalStateException("Unexcept value" + direction);
            }
            // Vẽ hình ảnh của thực thể
            g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
            g2D.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
        }
    }
}
