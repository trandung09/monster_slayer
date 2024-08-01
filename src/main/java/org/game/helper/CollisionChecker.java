package org.game.helper;

import org.game.character.Entity;
import org.game.frame.GamePanel;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    /**
     * Chức năng kiểm tra cập nhật va chạm giữa nhân vật với các thành phần của 
     * bản đồ.
     * Dựa vào vị trí của nhân vật hiện tại để xác định vị trí của các thành phần đang
     * được xác đinh để kiểm tra va chạm với nhân vật đó, xác định hàng và cột để lấy ra 
     * thành phần đó. Nếu có xáy ra va chạm thì cập nhật collisionOn của nhân vật là true.
     */
    public void checkCoWithTile(Entity entity) {

        int worldLeftX = entity.getWorldX() + entity.solidArea.x;
        int worldRightX = entity.getWorldX() + entity.solidArea.x + entity.solidArea.width;
        int worldTopY = entity.getWorldY() + entity.solidArea.y;
        int worldBottomY = entity.getWorldY() + entity.solidArea.y + entity.solidArea.height;

        int playerLeftCol = worldLeftX / GamePanel.tileSize;
        int playerRightCol = worldRightX / GamePanel.tileSize;
        int playerTopRow = worldTopY / GamePanel.tileSize;
        int playerBottomRow = worldBottomY / GamePanel.tileSize;
        // Thứ tự của mảnh Tile được chọn để cập nhật kiểm tra va chạm
        int tile1, tile2;

        // Xử lý dựa theo hướng đi của thực thể được chọn để kiểm tra
        switch (entity.getDirection()) {
            case UP:
                playerTopRow = (worldTopY - entity.getSpeed()) / GamePanel.tileSize;
                tile1 = gp.tileM.mapNums[playerTopRow][playerLeftCol];
                tile2 = gp.tileM.mapNums[playerTopRow][playerRightCol];
                break;

            case DOWN:
                playerBottomRow = (worldBottomY + entity.getSpeed()) / GamePanel.tileSize;
                tile1 = gp.tileM.mapNums[playerBottomRow][playerLeftCol];
                tile2 = gp.tileM.mapNums[playerBottomRow][playerRightCol];
                break;

            case LEFT:
                playerLeftCol = (worldLeftX - entity.getSpeed()) / GamePanel.tileSize;
                tile1 = gp.tileM.mapNums[playerTopRow][playerLeftCol];
                tile2 = gp.tileM.mapNums[playerBottomRow][playerLeftCol];
                break;

            case RIGHT:
                playerRightCol = (worldRightX + entity.getSpeed()) / GamePanel.tileSize;
                tile1 = gp.tileM.mapNums[playerTopRow][playerRightCol];
                tile2 = gp.tileM.mapNums[playerBottomRow][playerRightCol];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + entity.getDirection());
        }

        // Nếu có va chạm với một thành phần nào đó thì gán collisionOn của thực thể
        // được chọn là true (nghĩa là đang có va chạm xảy ra)
        if (gp.tileM.tiles[tile1].collision || gp.tileM.tiles[tile2].collision) {
            entity.collisionOn = true;
        }  
    }

    /**
     * Kiểm tra va chạm giữa thực thể này với một mảng các thực thể khác, 
     * sử dụng phương thức intersects(Rectangle o) để kiểm tra dựa và thuộc tính
     * solidArea là một đối tượng của lớp Rectangle giữa 2 thực thể.
     * 
     * @param entity thực thể được kiểm tra
     * @param others mảng thực thể kiểm tra
     * @return index là số thứ tự của thực thể thuộc Entity[] others có va chạm
     */
    public int checkCoWithEntity(Entity entity, Entity[] others) {

        int index = -1;

        for (int i = 0; i < others.length; i++) {
            if (others[i] != null) {
                // Xác định lại tọa độ của solidArea (là Rectangle) dựa vào tạo độ thực thể
                entity.solidArea.x += entity.getWorldX();
                entity.solidArea.y += entity.getWorldY();
                // Xác định lại tọa độ của solidArea (là Rectangle) dựa vào tạo độ thực thể
                others[i].solidArea.x += others[i].getWorldX();
                others[i].solidArea.y += others[i].getWorldY();

                // Reset lại vị trí của thực thể được kiểm tra để tránh trường hợp
                // hai thực thể không thể tách rời nhau sau khi chạm nhau
                resetEntityLocation(entity);

                // Kiểm tra va chạm dựa vào phương thức intersects(Rectangle o)
                if (entity != others[i] && entity.solidArea.intersects(others[i].solidArea)) {
                    entity.collisionOn = true; // Cập nhật tình trạng va chạm của thức thể được chọn
                    index = i; // gán index = i là số thứ tự trong mảng thực thể cần kiểm tra
                }

                // Cập nhật lại tọa độ của thuộc tính solidArea (là Rectangle) của đối tượng
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                others[i].solidArea.x = others[i].solidAreaDefaultX;
                others[i].solidArea.y = others[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Kiểm tra va chạm, vị trí giữa thực thể với tập object của game, 
     * cùng sử dụng phương thức intersects(Rectangle o) để kiểm tra dựa và thuộc tính
     * solidArea là một đối tượng của lớp Rectangle giữa 2 thực thể và danh sách objects.
     * 
     * @param entity thực thể chọn để kiểm tra
     * @param player xác định xem thực thể được chọn có là người chơi hay không
     * @return index là số thứ tự của object thuộc danh sách object có va chạm
     */
    public int checkCoWithObject(Entity entity, boolean player) {

        int index = -1;
        
        for (int i = 0; i < gp.objs.length ; i++) {
            if (gp.objs[i] != null) {
                
                entity.solidArea.x += entity.getWorldX();
                entity.solidArea.y += entity.getWorldY();

                gp.objs[i].solidArea.x += gp.objs[i].getWorldX();
                gp.objs[i].solidArea.y += gp.objs[i].getWorldY();

                resetEntityLocation(entity);

                if (entity.solidArea.intersects(gp.objs[i].solidArea)) {
                    entity.collisionOn = true;
                    if (player) index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                gp.objs[i].solidArea.x = gp.objs[i].solidAreaDefaultX;
                gp.objs[i].solidArea.y = gp.objs[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Kiểm tra va chạm của các thực thể mà không phải là người chơi với
     * nhân vật người chơi.
     * 
     * @param entity thực thể được chọn để kiểm tra
     */
    public void checkCoWithPlayer(Entity entity) {

        // Xác định lại tọa độ của solidArea (là Rectangle) dựa vào tạo độ thực thể
        entity.solidArea.x += entity.getWorldX();
        entity.solidArea.y += entity.getWorldY();

        // Xác định lại tọa độ của solidArea (là Rectangle) dựa vào tạo độ player
        gp.player.solidArea.x += gp.player.getWorldX();
        gp.player.solidArea.y += gp.player.getWorldY();

        resetEntityLocation(entity);
        // Kiểm tra va chạm bằng phương thức intersects(Rectangle o)
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
        }

        // Cập nhật lại tọa độ của thuộc tính solidArea (là Rectangle) của đối tượng
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }

    /**
     * Reset vị trí của nhân vật sau khi kiểm tra va chạm giữa 2 thực thể
     * để tránh trường hợp 2 thực thể không thể di chuyển sau khi va chạm
     * 
     * @param entity thực thể chọn để reset
     */
    private void resetEntityLocation(Entity entity) {
        // Xử lý reset vị trí thực thể dựa theo hướng đi nhân vật
        switch (entity.getDirection()) {
            case UP: entity.solidArea.y -= entity.getSpeed(); break;
            case DOWN: entity.solidArea.y += entity.getSpeed(); break;
            case LEFT: entity.solidArea.x -= entity.getSpeed(); break;
            case RIGHT: entity.solidArea.x += entity.getSpeed(); break;
            default: break;
        }
    }
}
