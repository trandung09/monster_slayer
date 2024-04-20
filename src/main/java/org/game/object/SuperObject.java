package org.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.frame.GamePanel;

public class SuperObject {
    
    protected GamePanel gp;

    protected BufferedImage image;
    protected String name;             // tên object
    protected int worldX, worldY;
    protected boolean collison;        

    public Rectangle solidArea;     // vùng check va chạm 
    public int solidAreaDefaultX;  
    public int solidAreaDefaultY;   

    public SuperObject(GamePanel gp) {

        collison = true;

        this.gp = gp;
        this.solidArea = new Rectangle(0, 0, GamePanel.tileSize, GamePanel.tileSize);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void draw(Graphics2D g2D) {

        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

        if (worldX + GamePanel.tileSize > gp.player.getWorldX() - gp.player.screenX &&
            worldX - GamePanel.tileSize < gp.player.getWorldX() + gp.player.screenX &&
            worldY + GamePanel.tileSize > gp.player.getWorldY() - gp.player.screenY &&
            worldY - GamePanel.tileSize < gp.player.getWorldY() + gp.player.screenY
        )
            g2D.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public String getName() {
        return name;
    }
}
