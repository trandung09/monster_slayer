package org.game.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.game.enums.Menu;
import org.game.form.Login;
import org.game.enums.GameState;
import org.game.frame.GamePanel;
import org.game.options.StateOption;

public class InputHandler implements KeyListener {

    private GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, lightingPressed, shootPressed;

    public InputHandler(GamePanel gp) {

        this.gp = gp;

        lightingPressed = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Lấy ra key code của KeyEvent e
        int kCode = e.getKeyCode();

        // Xử lý nếu trạng thái của game đang trong trạng thái START (đã bắt đầu)
        if (gp.mainState == GameState.START) {
            startState(kCode);
        }
        // Xử lý nếu trạng thái game đang trong trạng thái WAIT (chờ)
        else if (gp.mainState == GameState.WAIT) {
            waitState(kCode);
        } else if (gp.mainState == GameState.CHARACRTER) {
            charecterState(kCode);
        } else if (gp.mainState == GameState.DIALOGUE) {
            dialougeState(kCode);
        } else if (gp.mainState == GameState.PAUSE) {
            pauseState(kCode);
        } else if (gp.mainState == GameState.END) {
            endState(kCode);
        } else if (gp.mainState == GameState.WIN) {
            winState(kCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int kCode = e.getKeyCode();

        if (kCode == KeyEvent.VK_W) {
            upPressed = false;
        } else if (kCode == KeyEvent.VK_S) {
            downPressed = false;
        } else if (kCode == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (kCode == KeyEvent.VK_D) {
            rightPressed = false;
        } else if (kCode == KeyEvent.VK_ENTER) {
            enterPressed = false;
        } else if (kCode == KeyEvent.VK_BACK_SLASH) {
            shootPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void startState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_W:
                upPressed = true; break;
            case KeyEvent.VK_S:
                downPressed = true; break;
            case KeyEvent.VK_A:
                leftPressed = true; break;
            case KeyEvent.VK_D:
                rightPressed = true; break;
            case KeyEvent.VK_L:
                lightingPressed = !lightingPressed; 
                break;
            case KeyEvent.VK_BACK_SLASH:
                shootPressed = true; break;
            case KeyEvent.VK_P:
                if (gp.mainState == GameState.START)
                    gp.mainState = GameState.PAUSE;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = true;
                // gp.playMusicSE(5);
                gp.player.setAttacking(true);
                break;
            case KeyEvent.VK_QUOTE:
                gp.mainState = GameState.CHARACRTER;
                break;
            case KeyEvent.VK_SPACE:
                gp.player.setSelectedWeapon(!gp.player.isSelectedWeapon());
                ;
                gp.playMusicSE(8);
                gp.screenUI.addMessage("Weapon: " + (gp.player.isSelectedWeapon() ? "Sword" : "Axe"));
                break;
            default:
                break;
        }
    }

    private void waitState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_ENTER:
                if (StateOption.waitState == Menu.NEW_GAME) {
                    gp.mainState = GameState.LOGIN;

                    Login log = new Login(gp);
                    log.setVisible(true);
                    log.setLocationRelativeTo(null);
                }
                else if (StateOption.waitState == Menu.QUIT)
                    System.exit(1);
                break;
            case KeyEvent.VK_UP:
                if (StateOption.waitState == Menu.NEW_GAME)
                    StateOption.waitState = Menu.QUIT;
                else if (StateOption.waitState == Menu.ABOUT)
                    StateOption.waitState = Menu.NEW_GAME;
                else if (StateOption.waitState == Menu.QUIT)
                    StateOption.waitState = Menu.ABOUT;
                break;
            case KeyEvent.VK_DOWN:
                if (StateOption.waitState == Menu.NEW_GAME)
                    StateOption.waitState = Menu.ABOUT;
                else if (StateOption.waitState == Menu.ABOUT)
                    StateOption.waitState = Menu.QUIT;
                else if (StateOption.waitState == Menu.QUIT)
                    StateOption.waitState = Menu.NEW_GAME;
                break;
            default:
                break;
        }
    }

    private void charecterState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_QUOTE:
                gp.mainState = GameState.START;
                break;
            case KeyEvent.VK_ENTER:
                if (gp.player.currentItemSlected == 0 && gp.player.getKeys() > 0)
                    gp.player.useKey = true;
                else if (gp.player.currentItemSlected == 2 && gp.player.getManas() > 0)
                    gp.player.useMana = true;
                gp.mainState = GameState.START;
                break;
            case KeyEvent.VK_UP:
                if (gp.player.currentItemSlected == 0)
                    gp.player.currentItemSlected = 2;
                else
                    gp.player.currentItemSlected--;
                break;
            case KeyEvent.VK_DOWN:
                if (gp.player.currentItemSlected == 2)
                    gp.player.currentItemSlected = 0;
                else
                    gp.player.currentItemSlected++;
                break;
            default:
                break;
        }
    }

    private void dialougeState(int kCode) {
        if (kCode == KeyEvent.VK_ENTER) {
            if (gp.bossWarning) {
                gp.loadBossMap(gp.tileM.maps[1]);
            }

            gp.mainState = GameState.START;
        }
    }

    private void pauseState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_ENTER:
                if (StateOption.pauseState == Menu.CONTINUE)
                    gp.mainState = GameState.START;
                else if (StateOption.pauseState == Menu.QUIT)
                    System.exit(1);
                break;
            case KeyEvent.VK_UP:
                if (StateOption.pauseState == Menu.CONTINUE)
                    StateOption.pauseState = Menu.QUIT;
                else if (StateOption.pauseState == Menu.QUIT)
                    StateOption.pauseState = Menu.CONTINUE;
            case KeyEvent.VK_DOWN:
                if (StateOption.pauseState == Menu.CONTINUE)
                    StateOption.pauseState = Menu.QUIT;
                else if (StateOption.pauseState == Menu.QUIT)
                    StateOption.pauseState = Menu.CONTINUE;
            default:
                break;
        }
    }

    private void endState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_ENTER:
                if (StateOption.endState == Menu.RETRY) {
                    gp.reInitialize();
                    gp.mainState = GameState.START;
                } else if (StateOption.endState == Menu.QUIT)
                    System.exit(1);
                break;
            case KeyEvent.VK_UP:
                if (StateOption.endState == Menu.RETRY)
                    StateOption.endState = Menu.QUIT;
                else if (StateOption.endState == Menu.QUIT)
                    StateOption.endState = Menu.RETRY;
            case KeyEvent.VK_DOWN:
                if (StateOption.endState == Menu.RETRY)
                    StateOption.endState = Menu.QUIT;
                else if (StateOption.endState == Menu.QUIT)
                    StateOption.endState = Menu.RETRY;
            default:
                break;
        }
    }

    private void winState(int kCode) {
        switch (kCode) {
            case KeyEvent.VK_UP: 
                if (StateOption.winState == Menu.NEW_GAME) 
                    StateOption.winState = Menu.QUIT;
                else if (StateOption.winState == Menu.QUIT)
                    StateOption.winState = Menu.NEW_GAME; 
                break;
            case KeyEvent.VK_DOWN:
                if (StateOption.winState == Menu.NEW_GAME)
                    StateOption.winState = Menu.QUIT;
                else if (StateOption.winState == Menu.QUIT)
                    StateOption.winState = Menu.NEW_GAME;
                break;
            case KeyEvent.VK_ENTER:
                if (StateOption.winState == Menu.NEW_GAME) {
                    gp.reInitialize();
                    gp.mainState = GameState.START;
                } else if (StateOption.winState == Menu.QUIT)
                    System.exit(1);
                break;
            default:
                break;
        }
    }
}