package org.game.frame;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public final GamePanel gamePanel = new GamePanel();
    
    public GameFrame() {

        this.add(gamePanel);
        this.addKeyListener(gamePanel.keyH);

        this.setTitle("Pixel Hunter");
        this.setVisible(true);
        this.setResizable(true);
        this.setLocation(400, 70);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
    }

    public static void main(String[] args) {
        
        new GameFrame();
    }
}
