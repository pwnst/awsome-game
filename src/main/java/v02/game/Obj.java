package v02.game;

import lombok.Data;
import v02.engine.Renderer;
import v02.engine.gfx.Image;
import v02.engine.util.Point;

import java.util.*;

import static java.util.Arrays.asList;

@Data
public class Obj {

    private int currentX;
    private int currentY;

    private int targetX;
    private int targetY;

    private int xV;
    private int yV;

    private int framePosition = 0;

    private int[][] collisionMap;
    private Image image;
    private int moveBoxSide;

    public Obj(int[][] collisionMap, Image image, int mapX, int mapY) {
        this.collisionMap = collisionMap;
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

    public void buildPath(int targetX, int targetY) {
        Point current = new Point(currentX, currentY);
        Point target = new Point(targetX, targetY);
        System.out.println(current);
        System.out.println(target);
        boolean found = false;

        Set<Point> explored = new HashSet<>();
        Stack<Point> explore = new Stack<>();
        explore.push(current);

        while (!explore.empty() || found) {
            current = explore.pop();
            if (current.equals(target)) {
                found = true;
            } else {
                explored.add(current);
                newPoints(current).forEach(
                        point -> {
                            if (!explored.contains(point)) {
                                explore.add(point);
                            }
                        }
                );
            }
        }
        System.out.println(found);
    }

    private List<Point> newPoints(Point current) {
        int currX = current.getX();
        int currY = current.getY();
        List<Point> newPoints = new LinkedList<>();

        if (currX + 1 < collisionMap.length) {
            newPoints.add(new Point(currX + 1, currY, current));
        }
        if (currX - 1 >= 0) {
            newPoints.add(new Point(currX - 1, currY, current));
        }
        if (currY + 1 < collisionMap[0].length) {
            newPoints.add(new Point(currX, currY + 1, current));
        }
        if (currY - 1 >= 0) {
            newPoints.add(new Point(currX, currY - 1, current));
        }
//        if (currX + 1 < collisionMap.length && currY + 1 < collisionMap[0].length) {
//            newPoints.add(new Point(currX + 1, currY + 1));
//        }
//        if (currX - 1 >= 0 && currY - 1 >= 0) {
//            newPoints.add(new Point(currX - 1, currY - 1));
//        }
//        if (currY + 1 < collisionMap[0].length && currX - 1 >= 0) {
//            newPoints.add(new Point(currX - 1, currY + 1));
//        }
//        if (currY - 1 >= 0 && currX + 1 < collisionMap.length) {
//            newPoints.add(new Point(currX + 1, currY - 1));
//        }
        return newPoints;
    }

    public void setTargetLocation(int x, int y) {
        int xOffSet = moveBoxSide / 2;
        int yOffSet = moveBoxSide / 2;
        buildPath(x - xOffSet, y - yOffSet);
//        this.targetX = x - xOffSet;
//        this.targetY = y - yOffSet;
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
        return collisionMap[targetX + moveBoxSide - 1][targetY + image.getHeight() - moveBoxSide] > 1
                || collisionMap[targetX][targetY + image.getHeight() - 1] > 1
                || collisionMap[targetX + moveBoxSide - 1][targetY + image.getHeight() - 1] > 1
                || collisionMap[targetX][targetY + image.getHeight() - moveBoxSide] > 1;
    }
}
