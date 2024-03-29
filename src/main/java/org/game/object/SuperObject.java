package org.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.control.UtilityTool;
import org.game.frame.GamePanel;

public class SuperObject {
    
    protected GamePanel gp;
    protected UtilityTool uTool;
    public BufferedImage image;
    
    public int attackValue;
    public String name;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public int worldX, worldY;
    public boolean collison;

    public SuperObject(GamePanel gp) {

        collison = true;

        this.gp = gp;
        this.uTool = new UtilityTool();
        this.solidArea = new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void draw(Graphics2D g2D) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY
        )
            g2D.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    protected BufferedImage getImage(String imagePath, int width, int height) {

        return uTool.getImage(imagePath, width, height);
    }
}
