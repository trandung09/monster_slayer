package org.game.event;

import org.game.enums.Direction;
import org.game.frame.GamePanel;

public class EventHandler {
    
    GamePanel gp;
    EventRect[][] eventRects;

    public EventHandler(GamePanel gp) {

        this.gp = gp;
        eventRects = new EventRect[GamePanel.maxWorldRow][GamePanel.maxWorldCol];

        int row = 0, col = 0;
        while(row < GamePanel.maxWorldRow && col < GamePanel.maxWorldCol) {

            eventRects[row][col] = new EventRect(23, 23, 2, 2);
            eventRects[row][col].envetRectDefaultX = eventRects[row][col].x;
            eventRects[row][col].envetRectDefaultY = eventRects[row][col].y;

            col++;
            if (col == GamePanel.maxWorldCol) {
                row++;
                col = 0;
            }
        }
    }

    public void checkEvent() {

        if (hit(23, 21, Direction.UP)) {
            if (!gp.player.isInvincible() && gp.player.getLife() > 1) {
                gp.player.setLife(gp.player.getLife() - 1);
                gp.player.setInvincible(true);
            }
        }

        if (hit(10, 18, Direction.UP)) {
            if (!gp.player.isInvincible() && gp.player.getLife() > 1) {
                gp.player.setLife(gp.player.getLife() - 1);
                gp.player.setInvincible(true);
            }
        }

        if (hit(40, 32, Direction.UP)) {
            if (!gp.player.isInvincible() && gp.player.getLife() > 1) {
                gp.player.setLife(gp.player.getLife() - 1);
                gp.player.setInvincible(true);
            }
        }
    }

    public boolean hit(int row, int col, Direction direction) {

        boolean hit = false;

        gp.player.solidArea.x += gp.player.getWorldX();
        gp.player.solidArea.y += gp.player.getWorldY();

        eventRects[row][col].x += row * GamePanel.tileSize;
        eventRects[row][col].y += col * GamePanel.tileSize;

        if (gp.player.solidArea.intersects(eventRects[row][col])) {
            if (gp.player.getDirection() == direction)
                hit = true;
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        eventRects[row][col].x = eventRects[row][col].envetRectDefaultX;
        eventRects[row][col].y = eventRects[row][col].envetRectDefaultY;

        return hit;
    }
}
