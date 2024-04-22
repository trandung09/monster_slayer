package org.game.environment;

import java.awt.Graphics2D;

import org.game.frame.GamePanel;

public class EnvironmentManager {

    private Lighting lighting;

    public EnvironmentManager(GamePanel gp) {

        setUp(gp);
    }

    private void setUp(GamePanel gp) {

        lighting = new Lighting(gp);
    }
    
    public void draw(Graphics2D g2D) {

        lighting.draw(g2D);
    }
}
