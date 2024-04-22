package org.game.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.game.character.Entity;
import org.game.character.Oldman;
import org.game.frame.GamePanel;
import org.game.monster.Bat;
import org.game.monster.Boss;
import org.game.monster.GreenSlime;
import org.game.monster.Monster;
import org.game.monster.Sinister;
import org.game.object.Boots;
import org.game.object.Door;
import org.game.object.Key;
import org.game.object.SuperObject;

public class AssetSetter {

    public AssetSetter() {

    }

    public static Boss[] setBoss(GamePanel gp) {
        Boss[] boss = new Boss[1];

        try {
            File file = new File("src/main/resources/setter/bosslocation.txt");
            BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            String[] arr = br.readLine().split(" ");

            boss[0] = new Boss(gp); 
            boss[0].setWorldX(GamePanel.tileSize * Integer.parseInt(arr[0]));
            boss[0].setWorldY(GamePanel.tileSize * Integer.parseInt(arr[1]));

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(boss[0].getWorldX());

        return boss;
    }

    public static Monster[] setMonster(GamePanel gp) {

        Monster[] ents = new Monster[50];
        try {
            File file = new File("src/main/resources/setter/monsterlocation.txt");
            BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            String line; int i = 0;
            while(true) {
                line = br.readLine();
                if (line == null) break;

                String[] arr = line.split(" ");

                if (arr[2].equals("Slime")) {
                    ents[i] = new GreenSlime(gp);
                }
                if (arr[2].equals("Sinister")) {
                    ents[i] = new Sinister(gp);
                }
                else if (arr[2].equals("Bat")) {
                    ents[i] = new Bat(gp);
                }

                ents[i].setWorldX(GamePanel.tileSize * Integer.parseInt(arr[0]));
                ents[i].setWorldY(GamePanel.tileSize * Integer.parseInt(arr[1]));
                
                i++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ents;
    }

    public static Entity[] setNpc(GamePanel gp) {

        Entity[] ents = new Entity[50];
        try {
            File file = new File("src/main/resources/setter/npclocation.txt");
            BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            String line; int i = 0;
            while(true) {
                line = br.readLine();
                if (line == null) break;

                String[] arr = line.split(" ");

                ents[i] = new Oldman(gp);
                ents[i].setWorldX(GamePanel.tileSize * Integer.parseInt(arr[0]));
                ents[i].setWorldY(GamePanel.tileSize * Integer.parseInt(arr[1]));
                i++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ents;
    }

    public static SuperObject[] setObject(GamePanel gp) {

        SuperObject[] objs = new SuperObject[50];
        try {
            File file = new File("src/main/resources/setter/objlocation.txt");
            BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

            String line; int i = 0; 
            while(true) {
                line = br.readLine();
                if (line == null) break;

                String[] arr = line.split(" ");

                if (arr[2].equals("Boots")) {
                    objs[i] = new Boots(gp);
                }
                else if (arr[2].equals("Key")) {
                    objs[i] = new Key(gp);
                }
                else if (arr[2].equals("Door")) 
                    objs[i] = new Door(gp);
                    
                objs[i].setWorldX(GamePanel.tileSize * Integer.parseInt(arr[0]));
                objs[i].setWorldY(GamePanel.tileSize * Integer.parseInt(arr[1]));

                i++;
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
        return objs;
    }
}
