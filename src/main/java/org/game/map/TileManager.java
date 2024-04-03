package org.game.map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.game.control.UtilityTool;
import org.game.frame.GamePanel;

public class TileManager {

    GamePanel gp;

    private UtilityTool uTool;

    public int[][] mapNums = new int[GamePanel.maxWorldRow][GamePanel.maxWorldCol];
    public Tile[] tiles = new Tile[50];

    public TileManager(GamePanel gp) {

        this.gp = gp;
        this.uTool = new UtilityTool();

        loadMap();
        loadTileImage();
    }

    private void setUp(int index, String imagePath, boolean collision) {
        
        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;
        try {
            tiles[index] = new Tile();
            tiles[index].image = uTool.getImage("/newtile/" + imagePath, width, height);
            tiles[index].collision = collision;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTileImage() {

        setUp(10, "grass00", false);
        setUp(11, "grass01", false);
        setUp(12, "water00", true);
        setUp(13, "water01", true);
        setUp(14, "water02", true);
        setUp(15, "water03", true);
        setUp(16, "water04", true);
        setUp(17, "water05", true);
        setUp(18, "water06", true);
        setUp(19, "water07", true);
        setUp(20, "water08", true);
        setUp(21, "water09", true);
        setUp(22, "water10", true);
        setUp(23, "water11", true);
        setUp(24, "water12", true);
        setUp(25, "water13", true);
        setUp(26, "road00", false);
        setUp(27, "road01", false);
        setUp(28, "road02", false);
        setUp(29, "road03", false);
        setUp(30, "road04", false);
        setUp(31, "road05", false);
        setUp(32, "road06", false);
        setUp(33, "road07", false);
        setUp(34, "road08", false);
        setUp(35, "road09", false);
        setUp(36, "road10", false);
        setUp(37, "road11", false);
        setUp(38, "road12", false);
        setUp(39, "earth", false);
        setUp(40, "wall", true);
        setUp(41, "tree", true);
        setUp(42, "tree", false);
    }

    private void loadMap() {

        try {
            File file = new File("src/main/resources/map/map2.txt");
            BufferedReader br = Files.newBufferedReader(file.toPath(), 
                                                        StandardCharsets.UTF_8);
            int row = 0;
            String line;

            while (true) {
                line = br.readLine();
                if (line == null) break;
                
                String[] nums = line.split(" ");
                for (int i = 0; i < GamePanel.maxWorldCol; i++) 
                    mapNums[row][i] = Integer.parseInt(nums[i]);

                row++;
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 50; i ++) {
            for (int j = 0; j < 50; j++) {
                System.out.print(mapNums[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void draw(Graphics2D g2D) {

        int row = 0, col = 0;

        while(row < GamePanel.maxWorldRow && col < GamePanel.maxWorldCol) {

            int tileNum = mapNums[row][col];

            int worldX = col * GamePanel.tileSize;
            int worldY = row * GamePanel.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2D.drawImage(tiles[tileNum].image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);

            col++;
            if (col == GamePanel.maxWorldCol) {
                col = 0;
                row ++;
            }
        }
    }
}
