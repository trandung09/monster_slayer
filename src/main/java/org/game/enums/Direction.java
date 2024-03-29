package org.game.enums;

/**
 * Enum đánh dấu các hướng di chuyển cho thực thể
 * 
 * @author trandung09
 */

public enum Direction {
    
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");

    private String value;

    private Direction(String value) {

        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
