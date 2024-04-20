package org.game.monster;

import java.awt.Rectangle;

import org.game.character.Projectiles;
import org.game.enums.Direction;
import org.game.frame.GamePanel;
import org.game.helper.Images;
import org.game.object.Rock;

public class GreenSlime extends Monster {

    private int shotCounter = 0;

    public GreenSlime(GamePanel gp) {

        super(gp);

        invincible = false;
        name = "Green Slime";
        speed = 1;

        monsterExp = 3;
        objRan = 2;

        maxLife = 5;
        life = maxLife;

        direction = Direction.RIGHT;

        solidArea = new Rectangle(8, 8, 40, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadSlimeImage();
    }

    public void update() {

        // call function: setAction 
        super.setAction();
        // call function: update from sup class
        super.update();

        shotCounter++;
        if (shotCounter == (int) 1e9)
            shotCounter = 0;

        // nhaan vật và quái vật đứng theo một hướnh theo chiều dọc
        if (Math.abs(gp.player.getWorldX() - worldX) < GamePanel.tileSize) {
            if ((gp.player.getWorldY() > worldY && direction == Direction.DOWN) || (gp.player.getWorldY() <= worldY && direction == Direction.UP)) {
                if (shotCounter > 300 && Math.abs(gp.player.getWorldY() - worldY) <= 300) {
                    Projectiles pr = new Rock(gp);
                    pr.set(worldX, worldY, direction, true, this);

                    projectiles.add(pr);

                    shotCounter = 0;
                }
            }
        }

        // nhaan vật và quái vật đứng theo một hướnh theo chiều ngang
        if (Math.abs(gp.player.getWorldY() - worldY) < GamePanel.tileSize) {
            if ((gp.player.getWorldX() > worldX && direction == Direction.RIGHT
                    || (gp.player.getWorldX() <= worldX && direction == Direction.LEFT))) {
                if (shotCounter > 300 && Math.abs(gp.player.getWorldY() - worldY) <= 300) {
                    Projectiles pr = new Rock(gp);
                    pr.set(worldX, worldY, direction, true, this);

                    projectiles.add(pr);

                    shotCounter = 0;
                }
            }
        }

        for (Projectiles pr : projectiles) {
            pr.collisionOn = false;
            coChecker.checkCoWithPlayer(pr);

            if (pr.collisionOn == true) {
                damagePlayer();
            }
        }
    }

    private void loadSlimeImage() {

        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1    = Images.getImage("/monster/greenslime_down_1", width, height);
        up2    = Images.getImage("/monster/greenslime_down_2", width, height);
        down1  = Images.getImage("/monster/greenslime_down_1", width, height);
        down2  = Images.getImage("/monster/greenslime_down_2", width, height);
        left1  = Images.getImage("/monster/greenslime_down_1", width, height);
        left2  = Images.getImage("/monster/greenslime_down_2", width, height);
        right1 = Images.getImage("/monster/greenslime_down_1", width, height);
        right2 = Images.getImage("/monster/greenslime_down_2", width, height);
    }
}
