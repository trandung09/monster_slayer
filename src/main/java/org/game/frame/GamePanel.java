package org.game.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.game.character.Entity;
import org.game.character.Player;
import org.game.control.AssetSetter;
import org.game.control.Sound;
import org.game.enums.CommandNum;
import org.game.enums.GameState;
import org.game.environment.EnvironmentManager;
import org.game.event.InputHandler;
import org.game.map.TileManager;
import org.game.monster.Monster;
import org.game.object.Diamond;
import org.game.object.Mana;
import org.game.object.SuperObject;
import org.game.view.ScreenUI;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private static final int scare = 3;
    private static final int edgeTileSize = 16;

    public static int maxWorldCol = 50;
    public static int maxWorldRow = 50;
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    
    public static final int tileSize = scare * edgeTileSize;
    public static final int screenWidth = maxScreenCol * tileSize;
    public static final int screenHeight = maxScreenRow * tileSize;

    // FPS SETTINGS
    public final int FPS = 60;

    // GAME STATE SETTINGS
    public GameState mainstate = GameState.WAIT; // Enum GameState (class)
    public CommandNum waitstate = CommandNum.NEW_GAME; // Enum CommandNum (class)

    // OBJECTS OF THE GAME
    Thread gameThread;
    
    public Sound sound = new Sound();
    public EnvironmentManager eManager = new EnvironmentManager(this);

    public InputHandler keyH = new InputHandler(this);
    public ScreenUI screenUI = new ScreenUI(this);
    public Player player = new Player(this);
    public Entity[] npcs = new Entity[50];
    public Monster[] monsters = new Monster[50];
    public SuperObject[] objs = new SuperObject[50];
    public TileManager tileM = new TileManager(this);

    public GamePanel() {

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setFocusable(true);
        this.requestFocusInWindow(true);

        this.addKeyListener(keyH);

        setNpcs();
        startGameThread();
        playMusic(0);
    }

    // Khởi tạo gameThread của game 
    private void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Khởi tạo các Entity và Object của game
    private void setNpcs() {

        npcs = AssetSetter.setNpc(this);
        objs = AssetSetter.setObject(this);
        monsters = AssetSetter.setMonster(this);
    }

    // Phương thức được luồng gameThread sẽ gọi tới khi chạy
    @Override
    public void run() {
        
        double drawInterval = (double) 1000000000 / FPS; // nano seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();
            repaint(); // Gọi lại phương thức: paintComponent(Graphics gr)

            // Thread sẽ chạy cứ mỗi sau 1/FPS (s)
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; // milis second

                if (remainingTime < 0) remainingTime = 0;
                
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Mọi cập nhật của các đối tượng trong game được cập nhật ở đây
    public void update() {

        if (mainstate == GameState.START) {
            player.update();
            eManager.update();
            for (Entity ent : npcs) {
                if (ent != null) ent.update();
            }

            for (int i = 0; i < monsters.length; i++) {
                if (monsters[i] != null) {
                    if (monsters[i].alive == true && monsters[i].dying == false) {
                        monsters[i].update();
                    }
                    else if (monsters[i].alive == false && monsters[i].dying == true){
                        int wX = monsters[i].worldX;
                        int wY = monsters[i].worldY;

                        for (int k = 0; k < objs.length; k++) {
                            if (objs[k] != null) continue;

                            if (monsters[i].objRan == 1) objs[k] = new Diamond(this);
                            else if (monsters[i].objRan == 2) objs[k] = new Mana(this);

                            objs[k].worldX = wX;
                            objs[k].worldY = wY;
                            break;
                        }

                        monsters[i] = null;
                    }
                }
            }
        }
    }

    // Mọi hoạt động vẽ được xử lý trong phương thức này
    public void paintComponent(Graphics gr) {

        super.paintComponent(gr);

        

        Graphics2D g2D = (Graphics2D) gr;

        if (mainstate == GameState.WAIT){

        }
        else {
            tileM.draw(g2D);

            for (Entity ent : npcs) if (ent != null) ent.draw(g2D);
            for (SuperObject sobj : objs) 
                if (sobj != null){
                    sobj.draw(g2D);
                }
            for (Entity ent : monsters) 
                if (ent != null) ent.draw(g2D);

            player.draw(g2D);
            eManager.draw(g2D);
        }
        screenUI.draw(g2D);
        gr.dispose();
    }

    public void playMusic(int index) {

        sound.setFile(index);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {

        sound.stop();
    }

    public void playMusicSE(int index) {

        Sound soundSE = new Sound();
        soundSE.setFile(index);
        soundSE.play();
    }
}
