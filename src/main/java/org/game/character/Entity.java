package org.game.character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.game.enums.Direction;
import org.game.event.EventHandler;
import org.game.frame.GamePanel;
import org.game.helper.CollisionChecker;

public abstract class Entity {
    
    protected String name;

    protected GamePanel gp;

    public boolean hpBarOn  = false;
    public int hpBarCounter = 0;  

    // CHARECTER STAGE (Draw)
    protected boolean drawChecker = true; 
    protected int drawCounter     = 0;       
    protected int actionCounter   = 0;     

    // CHARACTER IMAGE
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    protected BufferedImage cutUp1, cutUp2, cutDown1, cutDown2, cutLeft1, cutLeft2, cutRight1, cutRight2;

    // FOR COLLISION CHECK
    protected CollisionChecker coChecker; 
    
    // FOR EVENT CHECK
    protected EventHandler eventH;

    protected int speed;          
    protected int worldX, worldY; 
    public Rectangle solidArea;
    public Rectangle attackArea; 
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY; 
    protected Direction direction = Direction.DOWN; 
    public boolean collisionOn = false;

    // CHARECTER STATUS
    protected boolean invincible = false; 

    protected int invincibleCounter = 0;  
    protected int maxLife           = 0;                
    protected int life   = 0;     
    protected int damage = 1;
    public boolean alive = true;    
    public boolean dying = false;
    protected int dyingCounter = 0 ; 

    protected boolean attacking = false;   
    protected int attackImageCounter = 0;     
    protected boolean attackImage = false;  
    
    public int projectilesCounter = 0;
    public ArrayList<Projectiles> projectiles = new ArrayList<>();

    protected Entity() {
    }

    protected Entity(GamePanel gp) {
        this.gp = gp;
        this.eventH = new EventHandler(gp);
        this.coChecker = new CollisionChecker(gp);
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
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void resetAction() {

    }

    public void reInitialize() {
        
    }

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
                    break;
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

    public void attacking() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
