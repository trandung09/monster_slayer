package org.game.character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.control.CollisionChecker;
import org.game.control.UtilityTool;
import org.game.enums.Direction;
import org.game.event.EventHandler;
import org.game.frame.GamePanel;

public class Entity {
    
    protected GamePanel gp;
    protected String name;

    // CHARECTER STATUS
    public boolean invincible = false; // Trạng thái vô địch của thực thể
    public int invincibleCounter = 0;  // Biến đếm cập nhật trạng thái vô địch của thực thể
    public int maxLife = 0;                // Giá trị lớn nhất máu thực thể
    public int life = 0;                   // Giá trị sự sống hiện tại của thực thể

    protected boolean hpBarOn = false; // Biến để cập nhật thời gian vẽ thanh máu của quái vật
    protected int hpBarCounter = 0;    // Biến đếm để cập nhật hpBarOn

    // CHARECTER STAGE (Draw)
    protected boolean drawChecker = true; // Biến sử dụng cập nhật hình ảnh nhân vật khi không trong trạng thái tấn công
    protected int drawCounter = 0;        // Biến đếm cập nhật drawChecker
    protected int actionCounter = 0;      // Biến đếm sử dụng để cập nhật hành động của các thực thể

    // CHARACTER IMAGE
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    protected UtilityTool uTool; 

    // FOR COLLISION CHECK
    protected CollisionChecker coChecker; // Đối tượng kiêm tra va chạm cho thực thể
    
    // FOR EVENT CHECK
    protected EventHandler eventH;

    public int speed;          // Tốc độ của thực thể
    public int worldX, worldY; // Vị trí của thực thể
    public Rectangle solidArea;// Vùng đặc để kiểm tra va chạm
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0); // Vùng đặc kiểm tra va chạm khi tấn công
    public int solidAreaDefaultX; // Giá trị X mặc định ban đầu của vùng đặc va chạm
    public int solidAreaDefaultY; // Giá trị Y mặc định ban đầu của vùng đặc va chạm
    public Direction direction = Direction.DOWN; // Enum Direction (class)
    public boolean collisionOn = false;

    protected Entity() {
    }

    protected Entity(GamePanel gp) {

        this.gp = gp;
        this.uTool = new UtilityTool();
        this.eventH = new EventHandler(gp);
        this.coChecker = new CollisionChecker(gp);
    }

    protected BufferedImage getImage(String imagePath, int width, int height) {

        return uTool.getImage(imagePath, width, height);
    }

    protected void setAction() {
        // Override by subclass
    }

    /* Cập nhật hoạt động của thực thể sau khi nhận các sự kiện từ bàn phím,
    * sự kiện từ hoạt động trong game. Cập nhật hướng di chuyển, vị trí và va 
    * chạm hiện tại của thực thể với các thực thể khác trong game. 
    */
    public void update() {

        setAction();

        // Kiểm tra va chạm với các đối tượng trong game
        collisionOn = false;
        coChecker.checkCoWithTile(this);
        coChecker.checkCoWithPlayer(this);
        coChecker.checkCoWithEntity(this, gp.monsters);
        coChecker.checkCoWithEntity(this, gp.npcs);

        // Cập nhật vị trí của thực thể nếu không xảy ra va chạm
        if (!collisionOn) {
            switch (direction) {
                case UP: worldY -= speed; break;
                case DOWN: worldY += speed; break;
                case LEFT: worldX -= speed; break;
                case RIGHT: worldX += speed; break;
            }
        }
        // Cập nhật trạng thái vô địch cho thực thể
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void resetAction() {

    }

    /**
     * Phương thức thực hiện chức năng vẽ hình ảnh thực thể ở mỗi trạng thái của 
     * thực thể lên panel chính tại vị trí tempX, tempY.
     * 
     * @param g2D là một đối tượng Graphics với các phương thức vẽ hình ảnh..
     */
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
            // Vẽ hình ảnh của thực thể
            if (invincible) {
                hpBarOn = true;
                g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f));
            }
            g2D.drawImage(image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
            g2D.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f));
        }
    }

    public BufferedImage getRightImage(boolean checker) {

        return checker ? right1 : right2; 
    }
}
