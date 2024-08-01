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
    protected int attackCounter = 0;

    public Monster(GamePanel gp) {

        super(gp);
    }

    @Override
    protected void setAction() {

        drawCounter++;
        if (drawCounter == 15) {
            drawCounter = 0;
            drawChecker = !drawChecker;
        }

        actionCounter++;
        if (actionCounter == 150) {
            
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

        if (gp.player.getDirection() == Direction.DOWN)
            direction = Direction.UP;
        else if (gp.player.getDirection() == Direction.UP)
            direction = Direction.DOWN;
        else if (gp.player.getDirection() == Direction.RIGHT)
            direction = Direction.LEFT;
        else direction = Direction.RIGHT;

        if (!collisionOn) {
            if (direction == Direction.DOWN) worldY -= speed * 15;
            else if (direction == Direction.UP) worldY += speed * 15;
            else if (direction == Direction.LEFT) worldX += speed * 15;
            else worldX -= speed * 15;
        }
    }

    public void damagePlayer() {
        if (gp.player.getLife() > 0 && !gp.player.isInvincible()) {
            gp.player.setLife(gp.player.getLife() - damage);
            gp.playMusicSE(5);
            if (gp.player.getLife() <= 0) {
                gp.player.alive = false;
                gp.player.dying = true;
            }

            gp.player.setInvincible(true);
        }
    }

    public void attacking() {
        attackImageCounter++;
        if (attackImageCounter <= 5) {
            attackImage = true;
        } else if (attackImageCounter > 5 && attackImageCounter <= 25) {
            attackImage = false;

            // Lưu lại vị trí, vùng va chạm hiện tại của nhân vật
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Cập nhật vị trí của nhân vật khi trong trạng thái tấn công
            // để kiểm tra va chạm của nhân vật có chạm vào quái vật khi
            // đang tấn công không.
            switch (direction) {
                case UP: worldY -= attackArea.height; break;
                case DOWN: worldY += attackArea.height; break;
                case LEFT: worldX -= attackArea.width; break;
                case RIGHT: worldX += attackArea.width; break;
                default:
                    break;
                    
            }

            solidArea.height = attackArea.height;
            solidArea.width = attackArea.width;

            collisionOn = false;
            coChecker.checkCoWithPlayer(gp.player);

            if (collisionOn = true) {
                damagePlayer();
            }

            // Cập nhật lại vị trí của nhân vật sau khi ở trạng thái tấn công.
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else {
            attackImage = false;
            attackImageCounter = 0;
            attacking = false; // Gán nhân vật không ở trong trạng thái tấn công
        }
    }

    public void checkVisibility(double uView, double rView, int size) {

        int pX = gp.player.getWorldX() + GamePanel.tileSize / 2;
        int pY = gp.player.getWorldY() + GamePanel.tileSize / 2;

        int mX = worldX + size;
        int mY = worldY + size;

        if (uView <= rView) {
            if (mY > pY + GamePanel.tileSize / 12) {
                direction = Direction.UP;
                if (!collisionOn) {
                    worldY -= speed;
                }
            }
            else if (mY < pY) {
                direction = Direction.DOWN;
                if (!collisionOn) {
                    worldY += speed;
                }
            }
            else if (mY == pY) {
                if (mX < pX) {
                    direction = Direction.RIGHT;
                    if (!collisionOn) {
                        worldX += speed;
                    }
                }
                else if (mX >= pX) {
                    direction = Direction.LEFT;
                    if (!collisionOn) {
                        worldX -= speed;
                    }
                }
            }
        }
    }

    public void checkAttacking(double uView,  double aView) {
        if (uView <= aView) {
            if (actionCounter == 100) {
                attacking = true;
            }
            attackCounter++;

            if (attackCounter >= 120) {
                attackCounter = 0;
            }
        } else {
            attackCounter = 0;
            attacking = false;
        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

        // Kiểm tra vị trí của nhân vật với vị trí hiến tại của thực thể,
        // nếu vị trí nhỏ hơn một khoảng X mới bắt đầu vẽ ảnh thực thể
        if (worldX + GamePanel.tileSize > gp.player.getWorldX() - gp.player.screenX &&
                worldX - GamePanel.tileSize < gp.player.getWorldX() + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.getWorldY() - gp.player.screenY &&
                worldY - GamePanel.tileSize < gp.player.getWorldY() + gp.player.screenY
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
