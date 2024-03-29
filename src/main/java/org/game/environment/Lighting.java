package org.game.environment;

import org.game.frame.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    private GamePanel gp;
    private BufferedImage darknessFilter;

    private Color[] color = new Color[12];
    private float[] fraction = new float[12];

    public Lighting(GamePanel gp) {

        this.gp = gp;
    }

    public void setUp() {

        darknessFilter = new BufferedImage(GamePanel.screenWidth, GamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) darknessFilter.getGraphics();

        int circleSize = 400;

        Area screenArea = new Area(new Rectangle2D.Double(0, - GamePanel.screenHeight, GamePanel.screenWidth * 2, GamePanel.screenHeight * 2));
        int centerX = gp.player.screenX + GamePanel.tileSize/2;
        int centerY = gp.player.screenY + GamePanel.tileSize/2;

        double x = centerX - circleSize/2;
        double y = centerY - circleSize/2;

        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
        Area lightArea = new Area(circleShape);

        screenArea.subtract(lightArea);

        g2D.setPaint(setLightSource(centerX, centerY, circleSize));

        g2D.fill(lightArea);
        g2D.fill(screenArea);

        g2D.dispose();
    }

    public void setColor() {

       if (gp.keyH.lightingPressed) {
            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.22f);
            color[2] = new Color(0, 0, 0.1f, 0.36f);
            color[3] = new Color(0, 0, 0.1f, 0.51f);
            color[4] = new Color(0, 0, 0.1f, 0.59f);
            color[5] = new Color(0, 0, 0.1f, 0.66f);
            color[6] = new Color(0, 0, 0.1f, 0.72f);
            color[7] = new Color(0, 0, 0.1f, 0.77f);
            color[8] = new Color(0, 0, 0.1f, 0.81f);
            color[9] = new Color(0, 0, 0.1f, 0.84f);
            color[10] = new Color(0, 0, 0.1f, 0.86f);
            color[11] = new Color(0, 0, 0.1f, 0.88f);
       } else{
            for (int i = 0; i < 12 ; i++) {
                color[i] = new Color(0, 0, 0.1f, 0.3f);
            }
       }
    }

    public RadialGradientPaint setLightSource(int centerX, int centerY, int circleSize) {

        setColor();

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, circleSize/2, fraction, color);

        return gPaint;
    }

    public void draw(Graphics2D g2D) {

        setUp();
        g2D.drawImage(darknessFilter, 0, 0, null);
    }
}
