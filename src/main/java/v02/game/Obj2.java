package v02.game;

import lombok.Data;
import v02.engine.Renderer;
import v02.engine.gfx.Image;
import v02.engine.util.Point;

@Data
public class Obj2 {

    private int currentX;
    private int currentY;

    private int targetX;
    private int targetY;

    private int xV;
    private int yV;

    private int framePosition = 0;

    private int[][] map;
    private Image image;
    private int moveBoxSide;

    public Obj2(int[][] map, Image image, int mapX, int mapY) {
        this.map = map;
        this.image = image;
        this.moveBoxSide = image.isAnimated()
                ? image.getWidth() / image.getFrames()
                : image.getWidth();

        this.currentX = mapX;
        this.currentY = mapY;
        this.targetX = mapX;
        this.targetY = mapY;

        this.xV = 1;
        this.yV = 1;
    }


    public void setTargetLocation(int x, int y) {
        int xOffSet = moveBoxSide / 2;
        int yOffSet = moveBoxSide / 2;
//        buildPath(x - xOffSet, y - yOffSet);
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

    public void update() {
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
            moveX(newX, currentY);
        }

        if (currentY != targetY) {
            if (targetY - currentY < 0) {
                newY -= yV;
            } else {
                newY += yV;
            }
            moveY(currentX, newY);
        }
    }

    private void moveX(int newX, int currentY) {
        if (isCollide(newX, currentY)) {
            targetX = currentX;
        } else {
            currentX = newX;
        }
    }

    private void moveY(int currentX, int newY) {
        if (isCollide(currentX, newY)) {
            targetY = currentY;
        } else {
            currentY = newY;
        }
    }

    private boolean isCollide(Point point) {
        return isCollide(point.getX(), point.getY());
    }

    private boolean isCollide(int targetX, int targetY) {
        return map[targetX + moveBoxSide - 1][targetY + image.getHeight() - moveBoxSide] > 1
                || map[targetX][targetY + image.getHeight() - 1] > 1
                || map[targetX + moveBoxSide - 1][targetY + image.getHeight() - 1] > 1
                || map[targetX][targetY + image.getHeight() - moveBoxSide] > 1;
    }
}
