package org.game.character;

import java.util.Random;
import java.awt.Rectangle;

import org.game.enums.Direction;
import org.game.frame.GamePanel;

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
        
        up1 = getImage("/npc/oldman_up_1", width, height);
        up2 = getImage("/npc/oldman_up_2", width, height);
        down1 = getImage("/npc/oldman_down_1", width, height);
        down2 = getImage("/npc/oldman_down_2", width, height);
        left1 = getImage("/npc/oldman_left_1", width, height);
        left2 = getImage("/npc/oldman_left_2", width, height);
        right1 = getImage("/npc/oldman_right_1", width, height);
        right2 = getImage("/npc/oldman_right_2", width, height);
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
}
