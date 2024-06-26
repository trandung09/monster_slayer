package org.game.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import org.game.database.ConnectMySQL;
import org.game.enums.Menu;
import org.game.frame.GamePanel;
import org.game.helper.Images;
import org.game.options.StateOption;

public class ScreenUI extends Interaction {

    public ArrayList<String> message = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentText;

    public LinkedList<String[]> list = ConnectMySQL.query();

    private boolean drawChecker = false;
    private int drawCounter = 0;

    public ScreenUI(GamePanel gp) {

        super(gp);
    }

    public void draw(Graphics2D g2D) {

        this.g2D = g2D;

        g2D.setFont(maruMonica.deriveFont(Font.BOLD, 54f));

        switch (gp.mainState) {
            case TOP: drawTopState(); break;
            case WAIT: drawWaitScreen(); break;
            case LOGIN: drawWaitScreen(); break;
            case START: drawPlayScreen(); drawPlayerLife(); break;
            case PAUSE: drawPauseScreen(); break;
            case END: drawEndScreen(); break;
            case CHARACRTER:drawCharacterScreen(); break;
            case DIALOGUE: drawDialogueScreen(); break;
            case WIN: drawWinScreen();
            default: break;
        }
    }

    private void drawSubWindow(int X, int Y, int width, int height) {

        Color color = new Color(0, 0, 0, 210);

        g2D.setColor(color);
        g2D.fillRoundRect(X, Y, width, height, 35, 35);

        color = new Color(255, 255, 255);

        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRoundRect(X + 5, Y + 5, width - 10, height - 10,35, 35);
    }

    public void addMessage(String message) {

        this.message.add(message);
        messageCounter.add(0);
    }

    private void drawDialogueScreen() {

        int screenX = GamePanel.tileSize * 2;
        int screenY = GamePanel.tileSize / 2;

        int width = GamePanel.screenWidth - GamePanel.tileSize*4;
        int height = GamePanel.tileSize * 4;

        drawSubWindow(screenX, screenY, width, height);

        screenX += GamePanel.tileSize / 2;
        screenY += GamePanel.tileSize;

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30f));
        for (String line : currentText.split("\n")) {
            g2D.drawString(line, screenX, screenY);
            screenY += 40;
        }
    }

    private void drawPlayerLife() {

        int screenX = GamePanel.tileSize;
        int screenY = GamePanel.tileSize;

        // draw player life and energy
        double lifeOnScale = (double) GamePanel.tileSize / gp.player.getMaxLife();
        double hpBar = lifeOnScale * gp.player.getLife();

        g2D.setColor(new Color(255, 255, 255));
        g2D.fillRect(screenX - 1, screenY - 16, GamePanel.tileSize * 3 + 2, 24);

        g2D.setColor(new Color(255, 35, 35));
        g2D.fillRect(screenX, screenY - 15, (int)hpBar * 3, 20);

        double manaOnScale = (double) GamePanel.tileSize / gp.player.getMaxEnergy();
        double manaBar = manaOnScale * gp.player.getEnergy();

        screenY += (GamePanel.tileSize * 3) / 4;
        g2D.setColor(new Color(255, 255, 255));
        g2D.fillRect(screenX - 1, screenY - 16, GamePanel.tileSize * 3 + 2, 24);

        g2D.setColor(new Color(255, 255, 153));
        g2D.fillRect(screenX, screenY - 15, (int)manaBar * 3, 20);

        screenX = GamePanel.screenWidth - GamePanel.tileSize * 3;
        screenY = GamePanel.tileSize;

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30f));

        // draw user name
        g2D.drawString("user: " + gp.player.getName(), screenX, screenY);

        // draw player times 
        double times = gp.player.getTimes();
        g2D.drawString(String.format("%.2f s", times), screenX, screenY + GamePanel .tileSize);
    }

    private void drawCharacterScreen() {

        // Create a frame
        final int frameX = (GamePanel.tileSize * 3) / 4 ;
        final int frameY = GamePanel.tileSize;
        final int height = GamePanel.tileSize * 8;
        final int width = GamePanel.tileSize * 5;

        // Draw sub window
        drawSubWindow(frameX, frameY, width, height);

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 32f));

        int textX = frameX + 20;
        int textY = frameY + GamePanel.tileSize;
        int lineHeight = 35;

        // Names
        g2D.drawString("Level", textX, textY);
        textY += lineHeight;
        g2D.drawString("Life", textX, textY);
        textY += lineHeight;
        g2D.drawString("Energy", textX, textY);
        textY += lineHeight;
        g2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2D.drawString("Damage", textX, textY);
        textY += lineHeight;
        g2D.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2D.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2D.drawString("Coin", textX, textY);
        textY += lineHeight;
        g2D.drawString("Weapon", textX, textY);
        textY += lineHeight;

        // Values
        int valueX = frameX + width - 30;
        textY = frameY + GamePanel.tileSize;

        String value;

        value = String.valueOf(gp.player.getLevel());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getLife() + "/" + gp.player.getMaxLife());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getEnergy() + "/" + gp.player.getMaxEnergy());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.getDexterity());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getDamage());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getExp());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getNextLevelExp());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getCoin());
        textX = gteXForAlignToRightText(value, valueX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight / 2;

        g2D.drawImage(gp.player.isSelectedWeapon() ? Images.sword : Images.axe, 
                    valueX - 29, textY, 40, 38, null);

        // Item image
        lineHeight += 10;
        int usedItemX = GamePanel.screenWidth - frameX - width;
        int usedItemY = frameY;

        drawSubWindow(usedItemX, usedItemY, width - 80, height / 2 + 30);
        drawSubWindow(usedItemX, usedItemY + height / 2 + 42, width, height / 4 - 12);

        g2D.drawString("[ENTER] to use!", usedItemX + 20, usedItemY + height / 2 + 90);

        usedItemX += 25;
        usedItemY += 25;

        if (gp.player.currentItemSlected == 0) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }
        g2D.drawImage(Images.key, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getKeys(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (gp.player.currentItemSlected == 1) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }
        g2D.drawImage(Images.coin, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getCoin(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (gp.player.currentItemSlected == 2) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - 101, 70);
        }
        g2D.drawImage(Images.mana, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getManas(), usedItemX + 66, usedItemY + 32);
    }   

    private void drawWaitScreen() {

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 76));
        g2D.setColor(Color.GRAY);

        // Draw string: "PIXEL HUNTER" in panel
        String text = "PIXEL HUNTER";
        int screenX = getXForCenterText(text);
        int screenY = GamePanel.tileSize * 2;

        g2D.drawString(text, screenX + 3, screenY - 3);

        g2D.setColor(Color.WHITE);
        g2D.drawString(text, screenX, screenY);

        // Draw image.gif (player image) in panel
        drawCounter++;
        if (drawCounter > 10) {
            drawCounter = 0;
            drawChecker = !drawChecker;
        }

        screenX = GamePanel.screenWidth / 2 - (GamePanel.tileSize * 3)  / 4;
        screenY += GamePanel.tileSize;

        g2D.drawImage((drawChecker ? Images.playerRight1 : Images.playerRight2), screenX, screenY, 72, 72, null);
        g2D.drawLine(GamePanel.screenWidth / 6, screenY + 72, (GamePanel.screenWidth * 5 ) / 6, screenY + 72);

        // Draw menu choose (new game : load_game : quit)
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 45f));

        text = "PLAY";
        screenX = getXForCenterText(text);
        screenY = screenY + (GamePanel.tileSize * 7) / 2;

        g2D.drawString(text, screenX, screenY);
        if (StateOption.waitState == Menu.NEW_GAME) 
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);

        text = "ACCOUNT";
        screenX = getXForCenterText(text);
        screenY = screenY + (GamePanel.tileSize * 4) / 3;

        g2D.drawString(text, screenX, screenY);
        if (StateOption.waitState == Menu.ABOUT) 
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);

        text = "QUIT";
        screenX = getXForCenterText(text);
        screenY = screenY + (GamePanel.tileSize * 4) / 3;

        g2D.drawString(text, screenX, screenY);
        if (StateOption.waitState == Menu.QUIT) 
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
    }

    private void drawPlayScreen() {

        // Draw message
        int X = GamePanel.tileSize;
        int Y = GamePanel.tileSize * 5;

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size() ; i++) {

            if (message.get(i) != null) {

                g2D.drawString(message.get(i), X, Y);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                Y += 50;

                int num = (message.get(i) == "1 damage!") ? 60 : 150;
                if (message.get(i) == "Weapon: Axe" || message.get(i) == "Weapon: Sword") num = 60;
                if (messageCounter.get(i) > num) {
                    message.remove(i);
                    messageCounter.remove(i);
                }   
            }
        }
    }

    private void drawPauseScreen() {
        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 85f));

        String text = "Game Pause";

        int screenX = getXForCenterText(text);
        int screenY = GamePanel.tileSize * 4 - (GamePanel.tileSize * 3) / 4;

        g2D.drawString(text, screenX, screenY);

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40f));

        text = "Continue";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize * 4;

        if (StateOption.pauseState == Menu.CONTINUE) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);

        text = "Quit";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize;

        if (StateOption.pauseState == Menu.QUIT) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);
    }

    private void drawEndScreen() {

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 85f));

        String text = "Game Over";

        int screenX = getXForCenterText(text);
        int screenY = GamePanel.tileSize * 4 - (GamePanel.tileSize * 3) / 4;

        g2D.drawString(text, screenX, screenY);

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40f));

        text = "Retry";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize * 3;

        if (StateOption.endState == Menu.RETRY) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);

        text = "Quit";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize;

        if (StateOption.endState == Menu.QUIT) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);
    }

    public void drawTopState() {

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50f));

        String string = "Top account";        
        int screenX = getXForCenterText(string);
        int screenY = GamePanel.tileSize * 2 + GamePanel.tileSize / 2;

        g2D.drawString(string, screenX, screenY);

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 35f));
        screenY += GamePanel.tileSize * 2;

        for (String[] s : list) {
            if (list.indexOf(s) == 3) {
                break;
            }
            string = String.format("%s  %s s", s[0], s[1]);
            screenX = getXForCenterText(string);
            g2D.drawString(string, screenX, screenY);
            screenY += GamePanel.tileSize;
        }

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 45));
        string = "Back";
        screenX = getXForCenterText(string);
        screenY += GamePanel.tileSize;
        g2D.drawString(string, screenX, screenY);
        g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
    }

    private void drawWinScreen() {
        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 65f));

        String text = "Congratulations";

        int screenX = getXForCenterText(text);
        int screenY = GamePanel.tileSize * 2 + GamePanel.tileSize / 2;

        g2D.drawString(text, screenX, screenY);

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 50f));

        text = "on winning";
        screenX = getXForCenterText(text);
        screenY = screenY + GamePanel.tileSize;
        g2D.drawString(text, screenX, screenY);

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40f));

        text = "New Game";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize * 4;

        if (StateOption.winState == Menu.NEW_GAME) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);

        text = "Quit";
        screenX = getXForCenterText(text);
        screenY += GamePanel.tileSize;

        if (StateOption.winState == Menu.QUIT) {
            g2D.drawString(">", screenX - GamePanel.tileSize, screenY);
        }
        g2D.drawString(text, screenX, screenY);
    }
}
