package v02.game;

import lombok.Data;
import v02.engine.gfx.Image;

@Data
public class Obj {
    private int mapX;
    private int mapY;

    private int targetX;
    private int targetY;

    private int xV;
    private int yV;

    private Image image;

    public Obj(String path, int mapX, int mapY) {
        this.image = new Image(path);

        this.mapX = mapX;
        this.mapY = mapY;
        this.targetX = mapX;
        this.targetY = mapY;

        this.xV = 1;
        this.yV = 1;
    }

    public void setTargetLocation(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public void update() {
        if (mapX != targetX) {
            if (targetX - mapX < 0) {
                mapX -= xV;
            } else {
                mapX += xV;
            }
        }
        if (mapY != targetY) {
            if (targetY - mapY < 0) {
                mapY -= yV;
            } else {
                mapY += yV;
            }
        }
    }

}
