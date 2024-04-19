package org.game.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.game.enums.CommandNum;
import org.game.frame.GamePanel;
import org.game.object.Heart;

public class ScreenUI extends Interaction {

    public ArrayList<String> message = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();

    private BufferedImage player_image;
    private boolean drawChecker = false;
    private int drawCounter = 0;

    public String curreString;

    public ScreenUI(GamePanel gp) {

        super(gp);

    }

    public void draw(Graphics2D g2D) {

        this.g2D = g2D;

        g2D.setFont(maruMonica.deriveFont(Font.BOLD, 54f));

        switch (gp.mainstate) {
            case WAIT: drawWaitScreen(); break;
            case START: drawPlayScreen(); drawPlayerLife(); break;
            case PAUSE: drawPauseScreen(); break;
            case END: drawEndScreen(); break;
            case CHARACRTER:drawCharacterScreen(); break;
            case DIALOGUE: drawDialogueScreen(); break;
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
       
        int x = GamePanel.tileSize * 2;
        int y = GamePanel.tileSize / 2;

        int width = GamePanel.screenWidth - GamePanel.tileSize*4;
        int height = GamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += GamePanel.tileSize/2;
        y += GamePanel.tileSize;

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30f));
        for (String line : curreString.split("\n")) {
            g2D.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawPlayerLife() {

        int X = GamePanel.tileSize / 2;
        int Y = GamePanel.tileSize / 2;
        int i = 0;

        // Draw blank heart
        while(i < gp.player.getMaxLife() / 2) {
            g2D.drawImage(Heart._blank, X, Y, null);
            X += GamePanel.tileSize;
            i++;
        }
        // Reset X and Y
        X = GamePanel.tileSize / 2;
        Y = GamePanel.tileSize / 2;
        i = 0;
        // Draw current life
        while(i < gp.player.getLife()) {
            g2D.drawImage(Heart._half, X, Y, null);
            i++;
            if (i < gp.player.getLife()) {
                g2D.drawImage(Heart._full, X, Y, null);
            }
            i++;
            X += GamePanel.tileSize;
        }
    }

    private void drawCharacterScreen() {

        // Create a frame
        final int X = (GamePanel.tileSize * 3) / 4 ;
        final int Y = GamePanel.tileSize;
        final int height = GamePanel.tileSize * 8;
        final int width = GamePanel.tileSize * 5;

        // Draw sub window
        drawSubWindow(X, Y, width, height);

        g2D.setColor(Color.WHITE);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 32f));

        int textX = X + 20;
        int textY = Y + GamePanel.tileSize;
        int lineHeight = 35;

        // Names
        g2D.drawString("Level", textX, textY);
        textY += lineHeight;
        g2D.drawString("Life", textX, textY);
        textY += lineHeight;
        g2D.drawString("Strength", textX, textY);
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
        int tailX = X + width - 30;
        textY = Y + GamePanel.tileSize;
        String value;

        value = String.valueOf(gp.player.getLevel());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getLife() + "/" + gp.player.getMaxLife());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getStrengly());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.getDexterity());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getDamage());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getExp());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getNextLevelExp());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getCoin());
        textX = gteXForAlignToRightText(value, tailX);
        g2D.drawString(value, textX, textY);
        textY += lineHeight / 2;

<<<<<<< HEAD
        g2D.drawImage(gp.player.isSelectedWeapon() ? gp.player.currentWeaponSword : gp.player.currentWeaponAxe, 
=======
        g2D.drawImage(gp.player.selectedWeapon ? gp.player.currentWeaponSword : gp.player.currentWeaponAxe, 
>>>>>>> 0bddc306652b30dc1659755560572a986096e4aa
                    tailX - GamePanel.tileSize / 2 - 5, textY, 40, 38, null);

        // Item image
        lineHeight += 10;
        int usedItemX = GamePanel.screenWidth - X - width;
        int usedItemY = Y;

        drawSubWindow(usedItemX, usedItemY, width - GamePanel.tileSize - 32, height / 2 + 30);
        drawSubWindow(usedItemX, usedItemY + 30 + height / 2 + 12, width, height / 4 - 12);

        g2D.drawString("[ENTER] to use!", usedItemX + 20, usedItemY + height / 2 + 90);

        usedItemX += 25;
        usedItemY += 25;

        if (gp.player.current_choose == 0) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - GamePanel.tileSize * 2 - 5, 70);
        }
        g2D.drawImage(gp.player.key, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getKeys(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (gp.player.current_choose == 1) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - GamePanel.tileSize * 2 - 5, 70);
        }
        g2D.drawImage(gp.player.diamond, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getDiamonds(), usedItemX + 65, usedItemY + 32);
        usedItemY += lineHeight + 15;

        if (gp.player.current_choose == 2) {
            drawSubWindow(usedItemX - 15, usedItemY - 15, width - GamePanel.tileSize * 2 - 5, 70);
        }
        g2D.drawImage(gp.player.mana, usedItemX + 5, usedItemY, 40, 40, null);
        g2D.drawString("x" + gp.player.getManas(), usedItemX + 66, usedItemY + 32);
    }   

    private void drawWaitScreen() {

        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 76));
        g2D.setColor(Color.GRAY);

        // Draw string: "PIXEL HUNTER" in panel
        String text = "PIXEL HUNTER";
        int X = getXForCenterText(text);
        int Y = GamePanel.tileSize * 2;

        g2D.drawString(text, X + 3, Y - 3);

        g2D.setColor(Color.WHITE);
        g2D.drawString(text, X, Y);

        // Draw image.gif (player image) in panel
        drawCounter++;
        if (drawCounter > 10) {
            drawCounter = 0;
            drawChecker = !drawChecker;
        }
        player_image = gp.player.getRightImage(drawChecker);

        X = GamePanel.screenWidth / 2 - (GamePanel.tileSize * 3)  / 4;
        Y += GamePanel.tileSize;

        g2D.drawImage(player_image, X, Y, (GamePanel.tileSize * 3) / 2, (GamePanel.tileSize * 3) / 2, null);
        g2D.drawLine(GamePanel.screenWidth / 6, Y + (GamePanel.tileSize * 3) / 2, GamePanel.screenWidth - GamePanel.screenWidth / 6, Y + (GamePanel.tileSize * 3) / 2);

        // Draw menu choose (new game : load_game : quit)
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48f));

        text = "NEW GAME";
        X = getXForCenterText(text);
        Y = Y + (GamePanel.tileSize * 7) / 2;

        g2D.drawString(text, X, Y);
        if (gp.waitstate == CommandNum.NEW_GAME) 
            g2D.drawString(">", X - GamePanel.tileSize, Y);

        text = "ABOUT";
        X = getXForCenterText(text);
        Y = Y + (GamePanel.tileSize * 4) / 3;

        g2D.drawString(text, X, Y);
        if (gp.waitstate == CommandNum.ABOUT) 
            g2D.drawString(">", X - GamePanel.tileSize, Y);

        text = "QUIT";
        X = getXForCenterText(text);
        Y = Y + (GamePanel.tileSize * 4) / 3;

        g2D.drawString(text, X, Y);
        if (gp.waitstate == CommandNum.QUIT) 
            g2D.drawString(">", X - GamePanel.tileSize, Y);
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

        // drawSubWindow(100, 100, 400, 200);
   
        // g2D.setColor(Color.GRAY);
        // g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,76f));
        // String text = "PAUSE";
        // int drawX = getXForCenterText(text);
        // int drawY = GamePanel.tileSize * 2;
        
        // g2D.drawString(text, drawX, drawY);

        // g2D.setColor(Color.WHITE);
        // g2D.drawString(text, drawX - 5, drawY + 5);
        // //Continew,Quit
        // text = "CONTINEW";
        // drawX = getXForCenterText(text);
        // drawY = drawY + (GamePanel.tileSize * 3) / 2;

        // if (gp.waitstate == CommandNum.) {
        //     g2D.drawString(">", drawX - GamePanel.tileSize, drawY);
        // }
        // g2D.drawString(text, drawX, drawY);

        // text = "QUIT";
        // drawX = getXForCenterText(text);
        // drawY = drawY + (GamePanel.tileSize * 3) / 2;

        // if (gp.waitstate == CommandNum.QUIT) {
        //     g2D.drawString(">", drawX - GamePanel.tileSize, drawY);
        // }
        // g2D.drawString(text, drawX, drawY);

    }

    private void drawEndScreen() {
        

    }
}
