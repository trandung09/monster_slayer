package org.game.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;

import javax.swing.JPanel;

import org.game.Sound.Sound;
import org.game.character.Entity;
import org.game.character.Player;
import org.game.character.Projectiles;
import org.game.enums.GameState;
import org.game.environment.EnvironmentManager;
import org.game.event.InputHandler;
import org.game.helper.AssetSetter;
import org.game.map.TileManager;
import org.game.monster.Boss;
import org.game.monster.Monster;
import org.game.object.GoldCoin;
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
    public GameState mainState = GameState.WAIT; // Enum GameState (class)

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
    public Boss[] boss = new Boss[1];

    public boolean bossWarning = false;

    public GamePanel() {

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setFocusable(true);
        this.requestFocusInWindow(true);

        this.addKeyListener(keyH);

        setEntityAndObject();
        startGameThread();
    }

    // Khởi tạo gameThread của game 
    private void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Khởi tạo các Entity và Object của game
    private void setEntityAndObject() {

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
                System.err.println("Game thread interrupted");
            }
        }
    }
    
    // Mọi cập nhật của các đối tượng trong game được cập nhật ở đây
    public void update() {

        if (mainState == GameState.START) {
            for (Boss b : boss) {
                if (b != null && b.alive && !b.dying) {
                    b.update();
                }
            }

            player.update();
            
            for (Entity ent : npcs) {
                if (ent != null) ent.update();
            }
        
            // cập nhật vị trí mảng các đnạ được bắn ra từ nhân vật
            for (int i = 0; i < player.projectiles.size(); i++) {
                if (player.projectiles.get(i).alive) {
                    player.projectiles.get(i).update();
                }
                else {
                    player.projectiles.remove(i);
                }
            }

            for (Entity e : monsters) {
                if (e == null) continue;
                if (Objects.equals(e.getName(), "Green Slime")) {
                    for (int i = 0; i < e.projectiles.size(); i++) {
                        if (e.projectiles.get(i).alive) {
                            e.projectiles.get(i).update();
                        }
                        else {
                            e.projectiles.remove(i);
                        }
                    }
                }
            }

            for (int i = 0; i < monsters.length; i++) {
                // && !monsters[i].getName().equals("Sinister")
                if (monsters[i] != null) {
                    if (monsters[i].alive && !monsters[i].dying) {
                        monsters[i].update();
                    }
                    else if (!monsters[i].alive && monsters[i].dying){
                        int wX = monsters[i].getWorldX();
                        int wY = monsters[i].getWorldY();
                        String name = monsters[i].getName();
                        int objRan = monsters[i].objRan;
                        monsters[i] = null;

                        if (!Objects.equals(name, "Bat")) {
                            for (int k = 0; k < objs.length; k++) {
                                if (objs[k] != null) continue;
    
                                if (objRan == 1) objs[k] = new GoldCoin(this);
                                else if (objRan == 2) objs[k] = new Mana(this);

                                assert objs[k] != null;
                                objs[k].setWorldX(wX);
                                objs[k].setWorldY(wY);
                                break;
                            }

                            if (checkBossWarning()) {

                                mainState = GameState.DIALOGUE;
                                screenUI.currentText = "Warning,\nyou will fight the boss now";

                                bossWarning = true;
                            }
                        }
                    }
                }
            }
        }
    }

    // Mọi hoạt động vẽ được xử lý trong phương thức này
    public void paintComponent(Graphics gr) {

        super.paintComponent(gr);
        
        Graphics2D g2D = (Graphics2D) gr;

        if (mainState == GameState.WAIT){

        }
        else if (mainState != GameState.LOGIN && mainState != GameState.TOP) {
            tileM.draw(g2D);

            for (Entity ent : npcs) if (ent != null) ent.draw(g2D);
            for (SuperObject sobj : objs) 
                if (sobj != null) {
                    sobj.draw(g2D);
                }

            for (Entity ent : monsters) 
                if (ent != null) ent.draw(g2D);

            // vez hình ảnh đạn bắn ra của nhân vật
            for (Projectiles pr : player.projectiles) {
                pr.draw(g2D);
            }

            // vez hình ảnh đạn bắn ra của quái vật
            for (Entity e : monsters) {
                if (e == null) continue;
                if (Objects.equals(e.getName(), "Green Slime")) {
                    for (Projectiles pr : e.projectiles)
                        pr.draw(g2D);
                }
            }

            player.draw(g2D);

            for (Boss b : boss) 
                if (b != null) b.draw(g2D);

            eManager.draw(g2D);
        }
        screenUI.draw(g2D);
        gr.dispose();
    }

    public void reInitialize() {
        player.reInitialize();

        boss = new Boss[1];
        npcs = new Entity[50];
        monsters = new Monster[50];
        objs = new SuperObject[50];

        playMusic(0);

        setEntityAndObject();
        tileM.loadMap(tileM.maps[0]);
    }

    public void loadBossMap(String mapPath) {
        tileM.loadMap(mapPath);
        npcs = new Entity[50];
        objs = new SuperObject[50];
        boss = AssetSetter.setBoss(this);
                
        player.setWorldX(21 * GamePanel.tileSize);
        player.setWorldY(23 * GamePanel.tileSize);
        player.setManas(player.getManas() + 3);
        if (player.getEnergy() < player.getMaxEnergy() / 2) 
            player.setEnergy(player.getMaxEnergy() / 2);

        bossWarning = false;
    }

    public boolean checkBossWarning() {

        for (Monster mon : monsters) {
            if (mon != null && !Objects.equals(mon.getName(), "Bat")) {
                return false;
            }
        }
        return true;
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
