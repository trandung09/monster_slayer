package org.game.enums;

/**
 * Enum đánh dấu các lựa chọn của game trước khi game bắt đầu
 * 
 * @author trandung09
 */

public enum Menu {
    
    NEW_GAME(0), ABOUT(1), QUIT(2), RETRY(3), CONTINUE(4);

    private int value; 
    private Menu(int value) {
        
        this.value = value;
    }

    public int value() { return this.value; }
}
