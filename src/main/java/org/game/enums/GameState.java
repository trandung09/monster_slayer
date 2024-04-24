package org.game.enums;

/**
 * Enum đánh dấu các trạng thái của game
 * 
 * @author trandung09
 */
public enum GameState {
    
    WAIT(0), START(1), PAUSE(2), END(3), DIALOGUE(4), CHARACRTER(5), WIN(7), LOGIN(8);

    private int value;

    private GameState(int value) {

        this.value = value;
    }

    public int value() {

        return this.value;
    }
}
