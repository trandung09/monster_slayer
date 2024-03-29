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
            if (!gp.player.invincible) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
    }

    public boolean hit(int row, int col, Direction direction) {

        boolean hit = false;

        gp.player.solidArea.x += gp.player.worldX;
        gp.player.solidArea.y += gp.player.worldY;

        eventRects[row][col].x += row * GamePanel.tileSize;
        eventRects[row][col].y += col * GamePanel.tileSize;

        if (gp.player.solidArea.intersects(eventRects[row][col])) {
            if (gp.player.direction == direction)
                hit = true;
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        eventRects[row][col].x = eventRects[row][col].envetRectDefaultX;
        eventRects[row][col].y = eventRects[row][col].envetRectDefaultY;

        return hit;
    }

    // private void damagePit() {

    // }

    // private void teleport() {

    // }

    // private void healingPool() {

    // }
}
