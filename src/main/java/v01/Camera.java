package v01;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class Camera {

    private int x;
    private int y;

    private int vX;
    private int vY;

    private int frameWidth;
    private int frameHeight;

    private int tileSize = 5;

    private int frameTileWidth = frameWidth / tileSize;
    private int frameTileHeight = frameHeight / tileSize;

    private Graphics2D backgroundGraphics;

    private long lastTimeUpdated;

    private GameMap map;

    public Camera(GameMap map, Graphics2D backgroundGraphics) {
        this.lastTimeUpdated = System.nanoTime();
        this.backgroundGraphics = backgroundGraphics;
        this.map = map;
        this.frameWidth = 320;
        this.frameWidth = 240;
    }

    public void update() {
        long current = System.nanoTime();
        // move to move method??
        if (current - lastTimeUpdated > 1000L) {
            moveX(vX);
            moveY(vY);
            lastTimeUpdated = current;
        }
    }

    public void draw() {
        Integer[][] map = this.map.getMap();

        for (int i = x; i < frameWidth; i = i + tileSize) {
            for (int j = y; j < frameHeight; j++) {
//                this.map.draw(backgroundGraphics, );
            }
        }
    }

    public void moveX(int v) {
        this.x = x + v;
    }

    public void moveY(int v) {
        this.y = y + v;
    }
}
