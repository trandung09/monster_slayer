package org.game.monster;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.game.character.Entity;
import org.game.enums.Direction;
import org.game.frame.GamePanel;

public class Monster extends Entity {

    public int objRan = 0;          // Random ra kim cương hay mana khi quái vật chết đi
    public int monsterExp = 0;      // Kinh nghiệm có được khi quái vật chết đi
    public boolean alive = true;    // Kiểm tra sự tồn tại của thực thể
    public boolean dying = false;
    private int dyingCounter = 0 ;

    public Monster(GamePanel gp) {

        super(gp);
    }

    @Override
    protected void setAction() {

        drawCounter++;
        if (drawCounter == 10) {
            drawCounter = 0;
            drawChecker = !drawChecker;
        }

        actionCounter++;
        if (actionCounter == 120) {
            
            Random random = new Random();
            int n = random.nextInt(100) + 1;

            if (n > 0 && n < 26) direction = Direction.UP;
            else if (n > 25 && n < 51) direction = Direction.DOWN;
            else if (n > 50 && n < 76) direction = Direction.LEFT;
            else direction = Direction.RIGHT;

            actionCounter = 0;
        }
    }

    @Override
    public void resetAction() {

        actionCounter = 0;

        direction = gp.player.direction;
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
            // Moster HP bar

            if (hpBarOn) {
                double oneScale = (double) GamePanel.tileSize / maxLife;
                double hpBar = oneScale * life;

                g2D.setColor(new Color(35, 35, 35));
                g2D.fillRect(screenX - 1, screenY - 16, GamePanel.tileSize + 2, 12);

                g2D.setColor(new Color(255, 35, 35));
                g2D.fillRect(screenX, screenY - 15, (int)hpBar, 10);

                hpBarCounter++;
                if (hpBarCounter > 500) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            // Vẽ hình ảnh của thực thể
            if (invincible) {
                hpBarOn = true;
                g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
            }
            if (alive == false && dying == true) {
                dyingAnimation(g2D);
            }
            g2D.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
            g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void changeAlpha(Graphics2D g2D, float alphaValue) {

        g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alphaValue));
    }

    public void dyingAnimation(Graphics2D g2D) {

        dyingCounter++;

        if (dyingCounter <= 5) {
            changeAlpha(g2D, 0f);
        }
        if (dyingCounter > 5 && dyingCounter <= 10) {
            changeAlpha(g2D, 1f);
        }
        if (dyingCounter > 10 && dyingCounter <= 15) {
            changeAlpha(g2D, 0f);
        }
        if (dyingCounter > 15 && dyingCounter <= 20) {
            changeAlpha(g2D, 1f);
        }
        if (dyingCounter > 20 && dyingCounter <= 25) {
            changeAlpha(g2D, 0f);
        }
        if (dyingCounter > 25 && dyingCounter <= 30) {
            changeAlpha(g2D, 1f);
        }
        if (dyingCounter > 30 && dyingCounter <= 35) {
            changeAlpha(g2D, 0f);
        }
        if (dyingCounter > 30 && dyingCounter <= 40) {
            changeAlpha(g2D, 1f);
        }
        if (dyingCounter > 40) {
            dying = false;
            alive = false;
        }
    }
}
