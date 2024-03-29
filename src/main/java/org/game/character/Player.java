package org.game.character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.enums.Direction;
import org.game.enums.GameState;
import org.game.event.InputHandler;
import org.game.frame.GamePanel;

public class Player extends Entity {
    
    // CAMERA SETTINGS
    public final int screenX = GamePanel.screenWidth/2 - GamePanel.tileSize/2; 
    public final int screenY = GamePanel.screenHeight/2 - GamePanel.tileSize/2;

    private InputHandler keyH;          // KeyEvent
    private int playerCounter = 0;      // Biến đếm để cập nhật biến playerNum
    private boolean playerNum = false;  // Biến kiểm tra chọn hình ảnh của nhân vật khi trong trạng thái tấn công
    public boolean attacking = false;   // enter -> attacking = true
    public boolean selectedWeapon = true; // true = kiếm, false = rìu

    // PLAYER ATTRIBUTES
    public int damage = 1;
    public int level = 1;
    public int strengly = 0; // the more strengly he has, the more damage he gives
    public int dexterity = 0; // the more dexterity he has, the less damage he recicives 
    public int attack; // the total attack value is decided by strengly and dexterity
    public int exp = 0; // Kinh nghiệm nhân vật
    public int nextLevelExp;
    public int coin = 0; // Sô tiền nhân vật hiện có
    public int keys = 0;  // Số chìa khóa hiện có
    public int manas = 0; // Số năng lượng có thể sử dụng hiện tại
    public int diamonds = 0; // Số kim cương hiện có
    public BufferedImage currentWeaponAxe; // Vũ khí hiện tại rìu
    public BufferedImage currentWeaponSword; // Vũ khí hiện tại là kiếm
    public BufferedImage key, diamond, mana;

    public int current_choose = 0;  // Vật phẩm được chọn hiện tại
    public boolean useKey = false;  // Đánh dấu có chọn sử dụng khóa hay không
    public boolean useMana = false; // Đánh dấu có chọn sử dụng mana hay không

    // Nhân vật tấn công với vũ khí là rìu
    private BufferedImage cutUp1, cutUp2, cutDown1, cutDown2, cutLeft1, cutLeft2, cutRight1, cutRight2;

    public Player(GamePanel gp) {

        super(gp);
        this.keyH = gp.keyH;
        
        setPlayerInfo();
        loadPlayerImage();
        loadPlayerCutImage();
        loadPlayerAttackImage();
    }

    private void setPlayerInfo() {

        maxLife = 6;
        life = maxLife;

        worldX = GamePanel.tileSize * 23;
        worldY = GamePanel.tileSize * 21;
        speed = 3;

        nextLevelExp = 5;

        currentWeaponSword = getImage("/object/sword_normal", GamePanel.tileSize, GamePanel.tileSize);
        currentWeaponAxe = getImage("/object/axe", GamePanel.tileSize, GamePanel.tileSize);
        key = getImage("/object/key", GamePanel.tileSize, GamePanel.tileSize);
        mana = getImage("/object/mana", GamePanel.tileSize, GamePanel.tileSize);
        diamond = getImage("/object/blueheart", GamePanel.tileSize, GamePanel.tileSize);

        attack = getAttack();

        direction = Direction.DOWN;
        drawChecker = true;

        solidArea = new Rectangle(12, 8, 24, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea = new Rectangle(0, 0, 40, 40);
    }

    private void loadPlayerImage() {

        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;

        up1 = getImage("/player/boy_up_1", width, height);
        up2 = getImage("/player/boy_up_2", width, height);
        down1 = getImage("/player/boy_down_1", width, height);
        down2 = getImage("/player/boy_down_2", width, height);
        left1 = getImage("/player/boy_left_1", width, height);
        left2 = getImage("/player/boy_left_2", width, height);
        right1 = getImage("/player/boy_right_1", width, height);
        right2 = getImage("/player/boy_right_2", width, height);
    }

    private void loadPlayerAttackImage() {

        int width = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        attackUp1 = getImage("/player/boy_attack_up_1", width / 2, height);
        attackUp2 = getImage("/player/boy_attack_up_2", width / 2, height);
        attackDown1 = getImage("/player/boy_attack_down_1", width / 2, height);
        attackDown2 = getImage("/player/boy_attack_down_2", width / 2, height);
        attackLeft1 = getImage("/player/boy_attack_left_1", width, height / 2);
        attackLeft2 = getImage("/player/boy_attack_left_2", width, height / 2);
        attackRight1 = getImage("/player/boy_attack_right_1", width, height / 2);
        attackRight2 = getImage("/player/boy_attack_right_2", width, height / 2);
    }

    private void loadPlayerCutImage() {

        int width = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        cutUp1 = getImage("/player/boy_axe_up_1", width / 2, height);
        cutUp2 = getImage("/player/boy_axe_up_2", width / 2, height);
        cutDown1 = getImage("/player/boy_axe_down_1", width / 2, height);
        cutDown2 = getImage("/player/boy_axe_down_2", width / 2, height);
        cutLeft1 = getImage("/player/boy_axe_left_1", width, height / 2);
        cutLeft2 = getImage("/player/boy_axe_left_2", width, height / 2);
        cutRight1 = getImage("/player/boy_axe_right_1", width, height / 2);
        cutRight2 = getImage("/player/boy_axe_right_2", width, height / 2);
    }

    /* Cập nhật hoạt động của nhân vật sau khi nhận các sự kiện từ bàn phím,
     * sự kiện từ hoạt động trong game. Cập nhật hướng di chuyển, vị trí và va 
     * chạm hiện tại của player với các thực thể trong game. 
     */
    @Override
    public void update() {

        
        // Sát thương nhân vật gây ra tăng lên khi giết được quái vật

        if (useMana) {

            useMana = false;
            if (life < maxLife) {
                life++;
                manas--;
            }
        }

        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;

            gp.screenUI.curreString = "You are level " + level + " now!\n"
                                        + "You feel stronger!";

            gp.mainstate = GameState.DIALOGUE;
            gp.playMusicSE(7);
        } // tăng cấp độ người chơi lên 1

        if (attacking) {
            // Trạng thái khi tấn công của nhân vật
            attacking();
        }   
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Xử lý cập nhật hướng di chuyển của player khi nhận input từ bàn phím
            if (keyH.upPressed) direction = Direction.UP;
            else if (keyH.downPressed) direction = Direction.DOWN;
            else if (keyH.leftPressed) direction = Direction.LEFT;
            else if (keyH.rightPressed) direction = Direction.RIGHT;

            // Kiểm tra va chạm với các thực thể, đối tượng khác
            collisionOn = false;
            coChecker.checkCoWithTile(this); 
            int objIndex = coChecker.checkCoWithObject(this, true);
            int npcIndex = coChecker.checkCoWithEntity(this, gp.npcs);
            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);

            // Các phương thức xử lý khi va chạm với một đối tượng khác cụ thể
            pickObject(objIndex);
            interactNpc(npcIndex);
            contactMonster(monsterIndex);

            // Kiểm tra sự kiện va chạm với một số điểm trên bản đồ
            eventH.checkEvent();

            // Cập nhật vị trí của nhân vật theo hướng di chuyển của nhân vật
            if (!collisionOn) {
                switch (direction) {
                    case UP: worldY -= speed; break;
                    case DOWN: worldY += speed; break;
                    case LEFT: worldX -= speed; break;
                    case RIGHT: worldX += speed; break;
                }
            }
            // Cập nhật hoạt ảnh chuyển động của nhân vật
            drawCounter++;
            if (drawCounter > 10) {
                drawChecker = !drawChecker;
                drawCounter = 0;
            }
            // Cập nhật trạng thái vô địch của nhân vật
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public int getAttack() {

        return attack = strengly * 2;
    }

    /* Xử lý hình ảnh, hoạt động của nhân vật khi ở trong trạng thái tấn công.*/
    public void attacking() {
        // Biến đếm để cập nhật hình ảnh tạo hoạt ảnh nhân vật
        playerCounter++; 
        if (playerCounter <= 5) {
            playerNum = true; 
        } 
        else if (playerCounter > 5 && playerCounter <= 25) {
            playerNum = false;

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
                case DOWN:worldY += attackArea.height; break;
                case LEFT: worldX-= attackArea.width; break;
                case RIGHT: worldX += attackArea.width; break;
                default: break;
            }

            solidArea.height = attackArea.height;
            solidArea.width = attackArea.width;

            // Kiểm tra và lấy ra chỉ số của quái vật bị nhân vật đánh trúng khi đang trong
            // trạng thái tấn công.
            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);
            damageMonster(monsterIndex);
            
            // Cập nhật lại vị trí của nhân vật sau khi ở trạng thái tấn công.
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        else{
            playerNum = false;
            playerCounter = 0;
            attacking = false; // Gán nhân vật không ở trong trạng thái tấn công
        }
    }

    /* Xử lý các sự kiện của ra khi quái vật bị tấn công bởi nhân vật */
    private void damageMonster(int index) {
        
        if (index == -1) 
            return;
        // Nếu quái vật không ở trong trạng thái vô địch và life > 0
        // thì life -= 1 và gán quái vật ở trong trạng thái vô địch
        if (gp.monsters[index].life > 0 && gp.monsters[index] != null) {
            if (!gp.monsters[index].invincible) {
                gp.monsters[index].life -= damage;
                gp.monsters[index].invincible = true; // Sau khi nhận sát thương thì quái vật trong trạng thái vô địch
                
                gp.monsters[index].resetAction();
                gp.playMusicSE(5);

                gp.screenUI.addMessage("1 damage!");
            }
        }
        else {
            // Nếu như máu quái vật = 0 thì xóa quái vật đi
            gp.monsters[index].dying = true;
            gp.monsters[index].alive = false;

            strengly++; // tăng chỉ số sát thương nhân vật lên 1
            if (strengly - dexterity > 0) {
                damage++;
            }
            else {
                damage = 1;
            }

            gp.screenUI.addMessage("Exp +" + gp.monsters[index].monsterExp + "!");
            gp.screenUI.addMessage("Strengly +1!");
            gp.screenUI.addMessage("Killed the " + gp.monsters[index].name + "!");
            exp += gp.monsters[index].monsterExp;
        }
    }

    /**
     * Xử lý các sự kiện xảy ra khi nhân vật va chạm với các object,
     * mỗi object riêng sẽ có các cách xử lý khác nhau tùy và chức năng 
     * của object đó.
     * 
     * @param index: chỉ số của object đang được nhân vât chạm , nếu 
     * index = -1 thì không có object nào hiện tại cần xử lý
     */
    private void pickObject(int index) {

        if (index == -1) return;
        String name = gp.objs[index].name;

        switch (name) {
            case "Boots": 
                speed += 2; gp.objs[index] = null; 
                gp.playMusicSE(2);
                gp.screenUI.addMessage("Speed up!");
                break;
            case "Diamond":
                gp.player.diamonds++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Diamond +1!");
                break;
            case "Mana": 
                gp.player.manas++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Mana +1!");
                break;
            case "Key": 
                gp.player.keys++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Key +1!");
                break;
            default:
                break;
        }
    }

    private void interactNpc(int index) {

        if (index == -1) return;
        if (gp.keyH.enterPressed) {
            
        }
    }

    /**
     * Xử lý sự kiện khi nhân vật bị quái vật chạm vào. Nếu nhân vật đang không
     * ở trong trạng thái vô địch thì máu của nhân vật bị trừ đi 1 và gán trạng
     * thái vô địch của nhân vật là true
     * 
     * @param index: chỉ số của quái vật đang chạm vào nhân vật hiện tại, nếu
     * index = -1 thì không có quái vật nào hiện tại đang chạm vào nhân vật
     */
    private void contactMonster(int index) {
        if (index == -1) return;
        if (invincible == false) {
            if (!attacking) {
                gp.playMusicSE(5);
                life -= 1;
                dexterity++; // chỉ số giảm sát thương nhân vật

                if (damage > 2) damage -= 1;
                invincible = true;

                gp.monsters[index].resetAction();
            }
        }
    }

    public void cutTree() {

    }
    /**
     * Phương thức thực hiện chức năng vẽ hình ảnh nhân vật ở mỗi trạng thái của 
     * nhân vật lên panel chính tại vị trí tempX, tempY.
     * 
     * @param g2D là một đối tượng Graphics với các phương thức vẽ hình ảnh..
     */
    @Override
    public void draw(Graphics2D g2D) {

        BufferedImage image = null;
        int tempX = screenX; // Tọa độ X để vẽ ảnh 
        int tempY = screenY; // Tọa độ Y để vẽ ảnh

        /**
         * Xác định hình ảnh cần vẽ, cập nhật tọa độ vẽ ảnh của nhân vật ở các trạng thái.
         * Xử lý dựa theo hướng nhân vật và trạng thái nhân vật
         */
        switch (direction) {
            case UP:
                // Lấy hình ảnh nhân vật khi không trong trạng thái tấn công
                if (!attacking) image = (drawChecker ? up1 : up2); 
                else { // Lấy hình ảnh nhân vật khi ở trong trạng thái tấn công
                    if (selectedWeapon) image = (playerNum ? attackUp1 : attackUp2);
                    else image = (playerNum ? cutUp1 : cutUp2);
                    tempY -= GamePanel.tileSize;
                    // Cập nhật tọa độ Y của vị trí vẽ hình ảnh
                }
                break;
            case DOWN: 
                if (!attacking) image = (drawChecker ? down1 : down2);
                else {
                    if (selectedWeapon) image = (playerNum ? attackDown1 : attackDown2);
                    else image = (playerNum ? cutDown1 : cutDown2);
                }
                break;
            case LEFT: 
                if (!attacking) image = (drawChecker ? left1 : left2); 
                else {
                    if (selectedWeapon) image = (playerNum ? attackLeft1 : attackLeft2);
                    else image = (playerNum ? cutLeft1 : cutLeft2);
                    tempX -= GamePanel.tileSize;
                }
                break;
            case RIGHT: 
                if (!attacking) image = (drawChecker ? right1 : right2);
                else {
                    if (selectedWeapon) image = (playerNum ? attackRight1 : attackRight2);
                    else image = (playerNum ? cutRight1 : cutRight2);
                }
                break;
            default: break;
        }
        // Vẽ hình ảnh nhân vật tại tọa độ X = tempX, Y = tempY
        if (invincible) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2D.drawImage(image, tempX, tempY, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
    }
}
