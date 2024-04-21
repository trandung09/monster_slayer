package org.game.map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.game.frame.GamePanel;
import org.game.helper.Images;

public class TileManager {

    private GamePanel gp;
    public String[] maps = new String[] {"dungeon01", "dungeon02"};

    public int[][] mapNums = new int[GamePanel.maxWorldRow][GamePanel.maxWorldCol];
    public Tile[] tiles = new Tile[50];

    public TileManager(GamePanel gp) {

        this.gp = gp;

        loadMap(maps[0]);
        loadTileImage();
    }

    private void setUp(int index, String imagePath, boolean collision) {
        
        int width = GamePanel.tileSize;
        int height = GamePanel.tileSize;
        try {
            tiles[index] = new Tile();
            tiles[index].image = Images.getImage("/newtile/" + imagePath, width, height);
            tiles[index].collision = collision;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTileImage() {

        setUp(0, "000", false);
        setUp(1, "001", false);
        setUp(2, "002", false);
        setUp(3, "003", false);
        setUp(4, "004", false);
        setUp(5, "005", false);
        setUp(6, "006", false);
        setUp(7, "007", false);
        setUp(8, "008", false);
        setUp(9, "009", false);
        setUp(10, "010", false);
        setUp(11, "011", false);
        setUp(12, "012", false);
        setUp(13, "013", false);
        setUp(14, "014", false);
        setUp(15, "015", false);
        setUp(16, "016", true);
        setUp(17, "017", false);
        setUp(18, "018", true);
        setUp(19, "019", true);
        setUp(20, "020", true);
        setUp(21, "021", true);
        setUp(22, "022", true);
        setUp(23, "023", true);
        setUp(24, "024", true);
        setUp(25, "025", true);
        setUp(26, "026", true);
        setUp(27, "027", true);
        setUp(27, "028", true);
        setUp(29, "029", true);
        setUp(30, "030", true);
        setUp(31, "031", true);
        setUp(32, "032", true);
        setUp(33, "033", true);
        setUp(34, "034", true);
        setUp(35, "035", true);
        setUp(36, "036", true);
        setUp(37, "037", true);
    }

    public void loadMap(String mapLevel) {

        try {
            File file = new File("src/main/resources/map/" + mapLevel + ".txt");
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
    }

    public void draw(Graphics2D g2D) {

        int row = 0, col = 0;

        while(row < GamePanel.maxWorldRow && col < GamePanel.maxWorldCol) {

            int tileNum = mapNums[row][col];

            int worldX = col * GamePanel.tileSize;
            int worldY = row * GamePanel.tileSize;
            int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
            int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

            g2D.drawImage(tiles[tileNum].image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);

            col++;
            if (col == GamePanel.maxWorldCol) {
                col = 0;
                row ++;
            }
        }
    }
}
