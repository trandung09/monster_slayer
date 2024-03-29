package org.game.event;

import java.awt.Rectangle;

/**
 * Kế thừa các lớp Rectangle với chức năng của lớp là tương tự
 * Mỗi EventRect đại diện cho một ô vuông trong bản đồ của game
 * 
 * @author trandung09
 */
public class EventRect extends Rectangle {
    
    // Giá trị mặc định của x và y của EventRect
    int envetRectDefaultX, envetRectDefaultY;
    boolean eventDone = false;

    public EventRect(int x, int y, int width, int height) {

        super(x, y, width, height);
    }
}
