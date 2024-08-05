package org.game.frame;

import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame {
    
    public GameFrame() {

        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.addKeyListener(gamePanel.keyH);

        this.setTitle("Pixel Hunter");
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new GameFrame().setVisible(true);
        });
    }
}
