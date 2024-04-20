package org.game.view;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.InputStream;

import org.game.frame.GamePanel;

public abstract class Interaction {
    
    protected GamePanel gp;
    protected Font maruMonica;
    protected Graphics2D g2D;

    public Interaction(GamePanel gp) {

        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int getXForCenterText(String text) {
        
        return GamePanel.screenWidth / 2 - (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth()/2;
    }

    protected int gteXForAlignToRightText(String text, int tailX ) {

        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        int x = tailX - length;
        return x;
    }
}
