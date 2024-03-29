package org.game.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.game.enums.CommandNum;
import org.game.enums.GameState;
import org.game.frame.GamePanel;

public class InputHandler implements KeyListener {

    private GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, lightingPressed;

    public InputHandler(GamePanel gp) {

        this.gp = gp;

        lightingPressed = true;
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        // Lấy ra key code của KeyEvent e
        int kCode = e.getKeyCode();

        // Xử lý nếu trạng thái của game đang trong trạng thái START (đã bắt đầu)
        if (gp.mainstate == GameState.START) {
            switch (kCode) {
                case KeyEvent.VK_W: upPressed = true; break;
                case KeyEvent.VK_S: downPressed = true; break;
                case KeyEvent.VK_A: leftPressed = true; break;
                case KeyEvent.VK_D: rightPressed = true; break;
                case KeyEvent.VK_L: lightingPressed = !lightingPressed; break;
                case KeyEvent.VK_P:
                    if (gp.mainstate == GameState.START) gp.mainstate = GameState.PAUSE;
                    break;
                case KeyEvent.VK_ENTER:
                    enterPressed = true;
                    // gp.playMusicSE(5);
                    gp.player.attacking = true;
                    break;
                case KeyEvent.VK_QUOTE: 
                    gp.mainstate = GameState.CHARACRTER; break;
                case KeyEvent.VK_SPACE: 
                    gp.player.selectedWeapon = !gp.player.selectedWeapon; 
                    gp.playMusicSE(8);
                    gp.screenUI.addMessage("Weapon: " + (gp.player.selectedWeapon ? "Sword" : "Axe"));
                    break;
                default: break;
            }
        }
        // Xử lý nếu trạng thái game đang trong trạng thái WAIT (chờ)
        else if (gp.mainstate == GameState.WAIT) {
            switch (kCode) {
                case KeyEvent.VK_ENTER:
                    if (gp.waitstate == CommandNum.NEW_GAME) gp.mainstate = GameState.START;
                    else if (gp.waitstate == CommandNum.QUIT) System.exit(1);
                    break;
                case KeyEvent.VK_UP:
                    if (gp.waitstate == CommandNum.NEW_GAME) gp.waitstate = CommandNum.QUIT;
                    else if (gp.waitstate == CommandNum.ABOUT) gp.waitstate = CommandNum.NEW_GAME;
                    else if (gp.waitstate == CommandNum.QUIT) gp.waitstate = CommandNum.ABOUT;
                    break;
                case KeyEvent.VK_DOWN:
                    if (gp.waitstate == CommandNum.NEW_GAME) gp.waitstate = CommandNum.ABOUT;
                    else if (gp.waitstate == CommandNum.ABOUT) gp.waitstate = CommandNum.QUIT;
                    else if (gp.waitstate == CommandNum.QUIT) gp.waitstate = CommandNum.NEW_GAME;
                    break;
                default: break;
            }
        }
        else if (gp.mainstate == GameState.CHARACRTER) {
            switch (kCode) {
                case KeyEvent.VK_QUOTE: 
                    gp.mainstate = GameState.START; 
                    break;
                case KeyEvent.VK_ENTER:
                    if (gp.player.current_choose == 0 && gp.player.keys > 0) gp.player.useKey = true;
                    else if (gp.player.current_choose == 2 && gp.player.manas > 0) gp.player.useMana = true;
                    gp.mainstate = GameState.START;
                    break;
                case KeyEvent.VK_UP: 
                    if (gp.player.current_choose == 0) gp.player.current_choose = 2;
                    else gp.player.current_choose--;
                    break;
                case KeyEvent.VK_DOWN: 
                    if (gp.player.current_choose == 2) gp.player.current_choose = 0;
                    else gp.player.current_choose++;
                    break;
                default:
                    break;
            }
        }
        else if (gp.mainstate == GameState.DIALOGUE) {
            if (kCode == KeyEvent.VK_ENTER) {
                gp.mainstate = GameState.START;
            }
        }
        else if (gp.mainstate == GameState.PAUSE) {
            if (kCode == KeyEvent.VK_ENTER) {
                gp.mainstate = GameState.START;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         
        int kCode = e.getKeyCode();

        if (kCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        else if (kCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        else if (kCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        else if (kCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
        else if (kCode == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}