package org.game.character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.enums.Direction;
import org.game.enums.GameState;
import org.game.event.InputHandler;
import org.game.frame.GamePanel;
import org.game.helper.Images;
import org.game.object.Fireball;

public class Player extends Entity {

    // CAMERA SETTINGS
    public final int screenX = GamePanel.screenWidth / 2 - GamePanel.tileSize / 2;
    public final int screenY = GamePanel.screenHeight / 2 - GamePanel.tileSize;

    private InputHandler keyH; // KeyEvent

    private boolean selectedWeapon = true; // true = kiếm, false = rìu

    // PLAYER ATTRIBUTES
    private int level = 1;
    private int dexterity = 0; // the more dexterity he has, the less damage he recicives
    private int exp = 0; // Kinh nghiệm nhân vật
    private int nextLevelExp;
    private int coin = 0; // Sô tiền nhân vật hiện có
    private int keys = 0; // Số chìa khóa hiện có
    private int manas = 0; // Số năng lượng có thể sử dụng hiện tại
    private int maxManas = 0;
    private int diamonds = 0; // Số kim cương hiện có
    private int energy = 0;
    private int maxEnergy = 0;

    public int currentItemSlected = 0; // Vật phẩm được chọn hiện tại
    public boolean useKey = false; // Đánh dấu có chọn sử dụng khóa hay không
    public boolean useMana = false; // Đánh dấu có chọn sử dụng mana hay không

    public Player(GamePanel gp) {

        super(gp);
        this.keyH = gp.keyH;

        setPlayerInfo();
        loadPlayerImage();
        loadPlayerCutImage();
        loadPlayerAttackImage();
    }

    private void setPlayerInfo() {

        maxLife = 8;
        life = maxLife;
        maxEnergy = 10;
        energy = maxEnergy;

        worldX = GamePanel.tileSize * 23;
        worldY = GamePanel.tileSize * 21;
        speed = 6;

        nextLevelExp = 5;

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

        up1 = Images.getImage("/player/boy_up_1", width, height);
        up2 = Images.getImage("/player/boy_up_2", width, height);
        down1 = Images.getImage("/player/boy_down_1", width, height);
        down2 = Images.getImage("/player/boy_down_2", width, height);
        left1 = Images.getImage("/player/boy_left_1", width, height);
        left2 = Images.getImage("/player/boy_left_2", width, height);
        right1 = Images.getImage("/player/boy_right_1", width, height);
        right2 = Images.getImage("/player/boy_right_2", width, height);
    }

    private void loadPlayerAttackImage() {

        int width = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        attackUp1 = Images.getImage("/player/boy_attack_up_1", width / 2, height);
        attackUp2 = Images.getImage("/player/boy_attack_up_2", width / 2, height);
        attackDown1 = Images.getImage("/player/boy_attack_down_1", width / 2, height);
        attackDown2 = Images.getImage("/player/boy_attack_down_2", width / 2, height);
        attackLeft1 = Images.getImage("/player/boy_attack_left_1", width, height / 2);
        attackLeft2 = Images.getImage("/player/boy_attack_left_2", width, height / 2);
        attackRight1 = Images.getImage("/player/boy_attack_right_1", width, height / 2);
        attackRight2 = Images.getImage("/player/boy_attack_right_2", width, height / 2);
    }

    private void loadPlayerCutImage() {

        int width = GamePanel.tileSize * 2;
        int height = GamePanel.tileSize * 2;

        cutUp1 = Images.getImage("/player/boy_axe_up_1", width / 2, height);
        cutUp2 = Images.getImage("/player/boy_axe_up_2", width / 2, height);
        cutDown1 = Images.getImage("/player/boy_axe_down_1", width / 2, height);
        cutDown2 = Images.getImage("/player/boy_axe_down_2", width / 2, height);
        cutLeft1 = Images.getImage("/player/boy_axe_left_1", width, height / 2);
        cutLeft2 = Images.getImage("/player/boy_axe_left_2", width, height / 2);
        cutRight1 = Images.getImage("/player/boy_axe_right_1", width, height / 2);
        cutRight2 = Images.getImage("/player/boy_axe_right_2", width, height / 2);
    }

    /*
     * Cập nhật hoạt động của nhân vật sau khi nhận các sự kiện từ bàn phím,
     * sự kiện từ hoạt động trong game. Cập nhật hướng di chuyển, vị trí và va
     * chạm hiện tại của player với các thực thể trong game.
     */
    @Override
    public void update() {

        if (life <= 0) {
            gp.mainState = GameState.END;
            gp.stopMusic();
            gp.playMusicSE(9);
        }
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

            maxEnergy = maxEnergy + 5;
            energy = maxEnergy;

            damage++;

            gp.screenUI.currentText = "You are level " + level + " now!\n"
                    + "You feel stronger!";

            gp.mainState = GameState.DIALOGUE;
            gp.playMusicSE(7);
        } // tăng cấp độ người chơi lên 1

        if (attacking) {
            // Trạng thái khi tấn công của nhân vật
            attacking();
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Xử lý cập nhật hướng di chuyển của player khi nhận input từ bàn phím
            if (keyH.upPressed)
                direction = Direction.UP;
            else if (keyH.downPressed)
                direction = Direction.DOWN;
            else if (keyH.leftPressed)
                direction = Direction.LEFT;
            else if (keyH.rightPressed)
                direction = Direction.RIGHT;

            // Kiểm tra va chạm với các thực thể, đối tượng khác
            collisionOn = false;
            coChecker.checkCoWithTile(this);

            int objIndex = coChecker.checkCoWithObject(this, true);
            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);
            int bossIndex = coChecker.checkCoWithEntity(this, gp.boss);

            // Các phương thức xử lý khi va chạm với một đối tượng khác cụ thể
            pickObject(objIndex);

            for (Projectiles e : projectiles) {
                int eIndex = coChecker.checkCoWithEntity(e, gp.monsters);
                int bIndex = coChecker.checkCoWithEntity(e, gp.boss);
                damageMonster(eIndex);
                damageBoss(bIndex);
            }

            // Kiểm tra sự kiện va chạm với một số điểm trên bản đồ
            eventH.checkEvent();

            // Cập nhật vị trí của nhân vật theo hướng di chuyển của nhân vật
            if (collisionOn == false) {
                switch (direction) {
                    case UP:
                        worldY -= speed; break;
                    case DOWN:
                        worldY += speed; break;
                    case LEFT:
                        worldX -= speed; break;
                    case RIGHT: worldX += speed;
                        break;
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

        if (keyH.shootPressed) {
            // chưa có quả nào,
            if (projectiles.size() == 0 || projectiles.getLast().life < 72) {
                // tạo độ trễ giữ 2 quả đạn
                if (energy > 0) {
                    Projectiles pr = new Fireball(gp);
                    pr.set(worldX, worldY, direction, true, this);

                    projectiles.add(pr);

                    energy--;
                }
            }
        }
    }

    /* Xử lý hình ảnh, hoạt động của nhân vật khi ở trong trạng thái tấn công. */
    public void attacking() {
        // Biến đếm để cập nhật hình ảnh tạo hoạt ảnh nhân vật
        attackImageCounter++;
        if (attackImageCounter <= 5) {
            attackImage = true;
        } else if (attackImageCounter > 5 && attackImageCounter <= 25) {
            attackImage = false;

            // Lưu lại vị trí, vùng va chạm hiện tại của nhân vật
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Cập nhật vị trí của nhân vật khi trong trạng thái tấn công
            // để kiểm tra va chạm của nhân vật có chạm vào quái vật khi
            // đang tấn công không.
            switch (direction) {
                case UP:
                    worldY -= attackArea.height;
                    break;
                case DOWN:
                    worldY += attackArea.height;
                    break;
                case LEFT:
                    worldX -= attackArea.width;
                    break;
                case RIGHT:
                    worldX += attackArea.width;
                    break;
                default:
                    break;
            }

            solidArea.height = attackArea.height;
            solidArea.width = attackArea.width;

            // Kiểm tra và lấy ra chỉ số của quái vật bị nhân vật đánh trúng khi đang trong
            // trạng thái tấn công.
            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);
            int bossIndex = coChecker.checkCoWithEntity(this, gp.boss);
            damageMonster(monsterIndex);
            damageBoss(bossIndex);

            // Cập nhật lại vị trí của nhân vật sau khi ở trạng thái tấn công.
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            attackImage = false;
            attackImageCounter = 0;
            attacking = false; // Gán nhân vật không ở trong trạng thái tấn công
        }
    }

    private void damageBoss(int index) {
        if (index == -1)
            return;
        // Nếu quái vật không ở trong trạng thái vô địch và life > 0
        // thì life -= 1 và gán quái vật ở trong trạng thái vô địch
        if (gp.boss[index].life > 0 && gp.boss[index] != null) {
            if (!gp.boss[index].invincible) {
                gp.boss[index].life -= damage;
                gp.boss[index].invincible = true; // Sau khi nhận sát thương thì quái vật trong trạng thái vô địch

                gp.boss[index].resetAction();
                gp.playMusicSE(5);

                gp.screenUI.addMessage("1 damage!");
            }
        } else {
            // Nếu như máu quái vật = 0 thì xóa quái vật đi
            gp.boss[index].dying = true;
            gp.boss[index].alive = false;

            gp.screenUI.addMessage("Exp +" + gp.boss[index].monsterExp + "!");
            gp.screenUI.addMessage("Strengly +1!");
            gp.screenUI.addMessage("Killed the " + gp.boss[index].name + "!");
            exp += gp.boss[index].monsterExp;
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
        } else {
            // Nếu như máu quái vật = 0 thì xóa quái vật đi
            gp.monsters[index].dying = true;
            gp.monsters[index].alive = false;

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
     *               index = -1 thì không có object nào hiện tại cần xử lý
     */
    private void pickObject(int index) {

        if (index == -1)
            return;
        String name = gp.objs[index].getName();

        switch (name) {
            case "GoldCoin":
                coin++;
                gp.objs[index] = null;
                gp.playMusicSE(1);
                gp.screenUI.addMessage("Gold coin +1!");
            case "Boots":
                speed += 1;
                gp.objs[index] = null;
                gp.playMusicSE(2);
                gp.screenUI.addMessage("Speed up!");
                break;
            case "Diamond":
                diamonds++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Diamond +1!");
                break;
            case "Mana":
                manas++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Mana +1!");
                break;
            case "Key":
                keys++;
                gp.objs[index] = null;
                gp.screenUI.addMessage("Key +1!");
                break;
            case "Door":
                if (useKey == true) {
                    gp.objs[index] = null;
                    gp.playMusicSE(2);
                    gp.screenUI.addMessage("Opened the door");
                }
            default:
                break;
        }
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
         * Xác định hình ảnh cần vẽ, cập nhật tọa độ vẽ ảnh của nhân vật ở các trạng
         * thái.
         * Xử lý dựa theo hướng nhân vật và trạng thái nhân vật
         */
        switch (direction) {
            case UP:
                // Lấy hình ảnh nhân vật khi không trong trạng thái tấn công
                if (!attacking)
                    image = (drawChecker ? up1 : up2);
                else { // Lấy hình ảnh nhân vật khi ở trong trạng thái tấn công
                    if (selectedWeapon)
                        image = (attackImage ? attackUp1 : attackUp2);
                    else
                        image = (attackImage ? cutUp1 : cutUp2);
                    tempY -= GamePanel.tileSize;
                    // Cập nhật tọa độ Y của vị trí vẽ hình ảnh
                }
                break;
            case DOWN:
                if (!attacking)
                    image = (drawChecker ? down1 : down2);
                else {
                    if (selectedWeapon)
                        image = (attackImage ? attackDown1 : attackDown2);
                    else
                        image = (attackImage ? cutDown1 : cutDown2);
                }
                break;
            case LEFT:
                if (!attacking)
                    image = (drawChecker ? left1 : left2);
                else {
                    if (selectedWeapon)
                        image = (attackImage ? attackLeft1 : attackLeft2);
                    else
                        image = (attackImage ? cutLeft1 : cutLeft2);
                    tempX -= GamePanel.tileSize;
                }
                break;
            case RIGHT:
                if (!attacking)
                    image = (drawChecker ? right1 : right2);
                else {
                    if (selectedWeapon)
                        image = (attackImage ? attackRight1 : attackRight2);
                    else
                        image = (attackImage ? cutRight1 : cutRight2);
                }
                break;
            default:
                break;
        }
        // Vẽ hình ảnh nhân vật tại tọa độ X = tempX, Y = tempY
        if (invincible) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2D.drawImage(image, tempX, tempY, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void reInitialize() {

        maxLife = 8;
        life = maxLife;
        maxEnergy = 10;
        energy = maxEnergy;

        worldX = GamePanel.tileSize * 23;
        worldY = GamePanel.tileSize * 21;
        speed = 3;

        nextLevelExp = 5;
        level = 1;

        exp = 0;

        direction = Direction.DOWN;
        drawChecker = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public int getManas() {
        return manas;
    }

    public void setManas(int manas) {
        this.manas = manas;
    }

    public int getMaxManas() {
        return maxManas;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(boolean selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

}
