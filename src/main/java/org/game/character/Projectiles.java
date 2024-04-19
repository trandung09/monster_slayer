package org.game.character;

import java.awt.Rectangle;

import org.game.enums.Direction;
import org.game.frame.GamePanel;

// projectiles = dan
public class Projectiles extends Entity {
    
    Entity user;

    public Projectiles(GamePanel gp) {
        super(gp);

        solidArea = new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void set(int worldX, int worldY, Direction direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
    }

    public void update() {
        switch (direction) {
            case UP: worldY -= speed; break;
            case DOWN: worldY += speed; break;
            case LEFT: worldX -= speed; break;
            case RIGHT: worldX += speed; break;
        }

        life--;

        if (life == 0) {
            alive = false;
        }

        drawCounter++;
        if (drawCounter > 12) {
            drawChecker = !drawChecker;
            drawCounter = 0;
        }
    }
}
