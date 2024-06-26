package org.game.monster;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.enums.Direction;
import org.game.frame.GamePanel;
import org.game.helper.Images;

public class Sinister extends Monster {

    public Sinister(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Sinister";
        speed = 1;

        monsterExp = 5;
        objRan = 1;
        damage = 1;

        maxLife = 15;
        life = maxLife;

        direction = Direction.RIGHT;

        solidArea = new Rectangle(8, 8, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea = new Rectangle(0, 0, 45, 45);

        loadSinisterImage();
        loadSinisterAttackImage();
    }

    private void loadSinisterImage() {

        int width  = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1    = Images.getImage("/monster/orc_up_1", width, height);
        up2    = Images.getImage("/monster/orc_up_2", width, height);
        down1  = Images.getImage("/monster/orc_down_1", width, height);
        down2  = Images.getImage("/monster/orc_down_2", width, height);
        left1  = Images.getImage("/monster/orc_left_1", width, height);
        left2  = Images.getImage("/monster/orc_left_2", width, height);
        right1 = Images.getImage("/monster/orc_right_1", width, height);
        right2 = Images.getImage("/monster/orc_right_2", width, height);
    }

    private void loadSinisterAttackImage() {

        int width  = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        attackUp1    = Images.getImage("/monster/orc_attack_up_1", width / 2, height);
        attackUp2    = Images.getImage("/monster/orc_attack_up_2", width / 2, height);
        attackDown1  = Images.getImage("/monster/orc_attack_down_1", width / 2, height);
        attackDown2  = Images.getImage("/monster/orc_attack_down_2", width / 2, height);
        attackLeft1  = Images.getImage("/monster/orc_attack_left_1", width, height / 2);
        attackLeft2  = Images.getImage("/monster/orc_attack_left_2", width, height / 2);
        attackRight1 = Images.getImage("/monster/orc_attack_right_1", width, height / 2);
        attackRight2 = Images.getImage("/monster/orc_attack_right_2", width, height / 2);
    }

    public void update() {

        super.update();

        int pX = (worldX + GamePanel.tileSize) - (gp.player.getWorldX() + GamePanel.tileSize / 2);
        int pY = (worldY + GamePanel.tileSize) - (gp.player.getWorldY() + GamePanel.tileSize / 2);

        double rView = 200;
        double aView = 80;
        double uView = Math.sqrt(pX * pX + pY * pY);

        if (uView < rView && uView > aView) {
            checkVisibility(uView, rView, GamePanel.tileSize);
        }

        checkAttacking(uView, aView);

        if (attacking == true) {
            attacking();
        }
    }

    public void draw(Graphics2D g2D) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

        // Kiểm tra vị trí của nhân vật với vị trí hiến tại của thực thể,
        // nếu vị trí nhỏ hơn một khoảng X mới bắt đầu vẽ ảnh thực thể
        if (worldX + GamePanel.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.getWorldY() + gp.player.screenY) {

            BufferedImage image = null;
            int tempX = screenX;
            int tempY = screenY;

            // Xử lý dựa vào hướng di chuyển của thực thể
            switch (direction) {
                case UP:
                    // Lấy hình ảnh nhân vật khi không trong trạng thái tấn công
                    if (!attacking)
                        image = (drawChecker ? up1 : up2);
                    else { // Lấy hình ảnh nhân vật khi ở trong trạng thái tấn công
                        image = (attackImage ? attackUp1 : attackUp2);
                        tempY -= GamePanel.tileSize;
                        // Cập nhật tọa độ Y của vị trí vẽ hình ảnh
                    }
                    break;
                case DOWN:
                    if (!attacking)
                        image = (drawChecker ? down1 : down2);
                    else {
                        image = (attackImage ? attackDown1 : attackDown2);
                    }
                    break;
                case LEFT:
                    if (!attacking)
                        image = (drawChecker ? left1 : left2);
                    else {
                        image = (attackImage ? attackLeft1 : attackLeft2);
                        tempX -= GamePanel.tileSize;
                    }
                    break;
                case RIGHT:
                    if (!attacking)
                        image = (drawChecker ? right1 : right2);
                    else {
                        image = (attackImage ? attackRight1 : attackRight2);
                    }
                    break;
                default:
                    break;
            }

            if (hpBarOn) {
                double oneScale = (double) GamePanel.tileSize / maxLife;
                double hpBar = oneScale * life;

                g2D.setColor(new Color(35, 35, 35));
                g2D.fillRect(screenX - 1, screenY - 16, GamePanel.tileSize + 2, 12);

                g2D.setColor(new Color(255, 35, 35));
                g2D.fillRect(screenX, screenY - 15, (int) hpBar, 10);

                hpBarCounter++;
                if (hpBarCounter > 500) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            // Vẽ hình ảnh của thực thể
            if (invincible) {
                hpBarOn = true;
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2D.drawImage(image, tempX, tempY, null);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
