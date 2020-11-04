package v02.game;

import lombok.Data;
import v02.engine.Renderer;
import v02.engine.gfx.Image;

import java.security.cert.X509Certificate;

@Data
public class Obj {

    private int currentX;
    private int currentY;

    private int targetX;
    private int targetY;

    private int xV;
    private int yV;

    private int framePosition = 0;

    private Image image;
    private int moveBoxSide;

    public Obj(Image image, int mapX, int mapY) {
        this.image = image;
        this.moveBoxSide = image.isAnimated()
                ? image.getWidth() / image.getFrames() - 4
                : image.getWidth() - 4;

        this.currentX = mapX;
        this.currentY = mapY;
        this.targetX = mapX;
        this.targetY = mapY;

        this.xV = 1;
        this.yV = 1;
    }

    public void setTargetLocation(int x, int y) {
        System.out.println(image.getWidth() / image.getFrames());
        int xOffSet = (image.getWidth() / (image.getFrames() + 1)) / 2;
        int yOffSet = (image.getHeight() / 10) * 9;
        this.targetX = x - xOffSet;
        this.targetY = y - yOffSet;
    }

    public boolean isMoving() {
        return (currentX != targetX)
                || (currentY != targetY);
    }

    public boolean isAnimated() {
        return image.isAnimated();
    }

    public void draw(Renderer renderer, int cameraX, int cameraY) {
        int frame = isMoving() && isAnimated() ? getFrame() : 0;
        renderer.drawImage(image, currentX - cameraX, currentY - cameraY, frame);
    }

    private int getFrame() {
        return (framePosition / image.getFrames()) % image.getFrames();
    }

    public void update(GameMap gameMap) {
        if (isAnimated() && isMoving() && framePosition < 60) {
            framePosition += 1;
        } else {
            framePosition = 0;
        }


        int newX = currentX;
        int newY = currentY;
        if (currentX != targetX) {
            if (targetX - currentX < 0) {
                newX -= xV;
            } else {
                newX += xV;
            }
        }
        if (currentY != targetY) {
            if (targetY - currentY < 0) {
                newY -= yV;
            } else {
                newY += yV;
            }
        }
        move(gameMap, newX, newY);
    }

    private void move(GameMap gameMap, int newX, int newY) {
        if (isCollide(gameMap.getMap(), gameMap.getTileSize(), newX, newY)) {
            targetX = currentX;
            targetY = currentY;
        } else {
            currentX = newX;
            currentY = newY;
        }
    }

    private boolean isCollide(int[][] map, int tileSize, int targetX, int targetY) {
        if(map[(targetX + moveBoxSide) / tileSize][(targetY + image.getHeight() - moveBoxSide) / tileSize] > 1) {
            System.out.println("1!");
        }
        if(map[(targetX) / tileSize][(targetY + image.getHeight()) / tileSize] > 1) {
            System.out.println("2!");
        }
        return map[(targetX + moveBoxSide) / tileSize][(targetY + image.getHeight() - moveBoxSide) / tileSize] > 1
                || map[(targetX) / tileSize][(targetY + image.getHeight()) / tileSize] > 1;
    }
}
