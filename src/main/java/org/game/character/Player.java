package org.game.character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.game.database.ConnectMySQL;
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
    private int level     = 1;
    private int dexterity = 0; 
    private int exp       = 0;
    private int nextLevelExp;
    private int coin      = 0; 
    private int keys      = 0; 
    private int manas     = 0; 
    private int maxManas  = 0;
    private int diamonds  = 0; 
    private int energy    = 0;
    private int maxEnergy = 0;

    private double times  = 0;

    public int currentItemSlected = 0;
    public boolean useKey  = false; 
    public boolean useMana = false; 

    public Player(GamePanel gp) {

        super(gp);
        this.keyH = gp.keyH;

        setPlayerInfo();
        loadPlayerImage();
        loadPlayerCutImage();
        loadPlayerAttackImage();
    }

    private void setPlayerInfo() {

        maxLife = 2;
        life = maxLife;
        maxEnergy = 10;
        energy = maxEnergy;

        worldX = GamePanel.tileSize * 8;
        worldY = GamePanel.tileSize * 7;
        speed = 4;

        damage = 8;

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

    @Override
    public void update() {

        times += 1.0 / gp.FPS;

        if (life <= 0) {
            gp.mainState = GameState.END;
            gp.stopMusic();
            gp.playMusicSE(9);
        }

        if (useMana) {
            useMana = false;
            if (life < maxLife) {
                life += 2;

                if (life > maxLife) life = maxLife;
                manas--;
            }
        }

        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;

            energy = maxEnergy;

            if (damage < 3) {
                damage++;
            }

            gp.screenUI.currentText = "You are level " + level + " now!\n"
                    + "You feel stronger!";

            gp.mainState = GameState.DIALOGUE;
            gp.playMusicSE(7);
        } 

        if (attacking) {
            attacking();
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            
            if (keyH.upPressed)
                direction = Direction.UP;
            else if (keyH.downPressed)
                direction = Direction.DOWN;
            else if (keyH.leftPressed)
                direction = Direction.LEFT;
            else if (keyH.rightPressed)
                direction = Direction.RIGHT;

            collisionOn = false;
            coChecker.checkCoWithTile(this);

            int objIndex = coChecker.checkCoWithObject(this, true);
            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);
            int bossIndex = coChecker.checkCoWithEntity(this, gp.boss);

            tempFunctions(monsterIndex, bossIndex);
            pickObject(objIndex);

            for (Projectiles e : projectiles) {
                int eIndex = coChecker.checkCoWithEntity(e, gp.monsters);
                int bIndex = coChecker.checkCoWithEntity(e, gp.boss);
                damageMonster(eIndex);
                damageBoss(bIndex);
            }

            // Kiểm tra sự kiện va chạm với một số điểm trên bản đồ
            eventH.checkEvent();

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
            
            drawCounter++;
            if (drawCounter > 10) {
                drawChecker = !drawChecker;
                drawCounter = 0;
            }
            
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }

        if (keyH.shootPressed) {
            
            if (projectiles.size() == 0 || projectiles.getLast().life < 72) {
                
                if (energy > 0) {
                    Projectiles pr = new Fireball(gp);
                    pr.set(worldX, worldY, direction, true, this);

                    projectiles.add(pr);

                    energy--;
                }
            }
        }
    }

    
    public void attacking() {
        
        attackImageCounter++;
        if (attackImageCounter <= 5) {
            attackImage = true;
        } 
        else if (attackImageCounter > 5 && attackImageCounter <= 25) {
            attackImage = false;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

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

            int monsterIndex = coChecker.checkCoWithEntity(this, gp.monsters);
            int bossIndex = coChecker.checkCoWithEntity(this, gp.boss);
            damageMonster(monsterIndex);
            damageBoss(bossIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            attackImage = false;
            attackImageCounter = 0;
            attacking = false; 
        }
    }

    private void damageBoss(int index) {
        if (index == -1)
            return;
   
        if (gp.boss[index].life > 0 && gp.boss[index] != null) {
            if (!gp.boss[index].invincible) {
                gp.boss[index].life -= damage;
                gp.boss[index].invincible = true;

                gp.boss[index].resetAction();
                gp.playMusicSE(5);

                gp.screenUI.addMessage("1 damage!");
            }
        } else {
            
            gp.boss[index].dying = true;
            gp.boss[index].alive = false;

            gp.screenUI.addMessage("Exp +" + gp.boss[index].monsterExp + "!");
            gp.screenUI.addMessage("Strengly +1!");
            gp.screenUI.addMessage("Killed the " + gp.boss[index].name + "!");
            exp += gp.boss[index].monsterExp;
        }
    }

    private void damageMonster(int index) {

        if (index == -1)
            return;
  
        if (gp.monsters[index].life > 0 && gp.monsters[index] != null) {
            if (!gp.monsters[index].invincible) {
                gp.monsters[index].life -= damage;
                gp.monsters[index].invincible = true;

                gp.monsters[index].resetAction();
                gp.playMusicSE(5);

                gp.screenUI.addMessage("1 damage!");
            }
        } else {

            gp.monsters[index].dying = true;
            gp.monsters[index].alive = false;

            gp.screenUI.addMessage("Exp +" + gp.monsters[index].monsterExp + "!");
            gp.screenUI.addMessage("Strengly +1!");
            gp.screenUI.addMessage("Killed the " + gp.monsters[index].name + "!");
            exp += gp.monsters[index].monsterExp;
        }
    }

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
                break;
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
                    useKey = false;
                    keys--;
                    gp.objs[index] = null;
                    gp.playMusicSE(10);
                    gp.screenUI.addMessage("Opened the door");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2D) {

        BufferedImage image = null;
        int tempX = screenX; 
        int tempY = screenY; 

        switch (direction) {
            case UP:
                if (!attacking)
                    image = (drawChecker ? up1 : up2);
                else { 
                    if (selectedWeapon)
                        image = (attackImage ? attackUp1 : attackUp2);
                    else
                        image = (attackImage ? cutUp1 : cutUp2);
                    tempY -= GamePanel.tileSize;
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

    public void updateTimesScore() {

        double oldtimes = ConnectMySQL.query(name);
        if (oldtimes <= times) return;

        ConnectMySQL.update(name, times);
    }

    public void tempFunctions(int first, int second) {

    }

    public void reInitialize() {

        maxLife = 12;
        life = maxLife;
        maxEnergy = 10;
        energy = maxEnergy;

        worldX = GamePanel.tileSize * 8;
        worldY = GamePanel.tileSize * 7;
        speed = 4;

        nextLevelExp = 5;
        level = 1;

        damage = 8;

        exp = 0;

        projectiles.clear();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public double getTimes() {
        return times;
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
