package v02.game;

import lombok.Data;
import lombok.SneakyThrows;
import v02.engine.Renderer;
import v02.engine.gfx.Image;
import v02.engine.util.AStar;
import v02.engine.util.AStar.PointA;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

@Data
public class Obj {

    private int currentX;
    private int currentY;

    private int targetX;
    private int targetY;

    private int xV;
    private int yV;

    private int framePosition = 0;

    // test
    private int framePosTest = 0;

    private int[][] map;
    private Image image;
    private int moveBoxSide;

    private List<PointA> path;
    private FutureTask<List<PointA>> futureTask;

    public Obj(int[][] map, Image image, int mapX, int mapY) {
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

        this.path = emptyList();
    }


    @SneakyThrows
    public void setTargetLocation(int x, int y) {
//        long start = Instant.now().toEpochMilli();
//        this.path = AStar.getPath(
//                new PointA(currentX, currentY),
//                new PointA(x / 10, y /10),
//                map,
//                0
//        );
//        System.out.println(Instant.now().toEpochMilli() - start);
        Callable<List<PointA>> task = () -> AStar.getPath(
                new PointA(currentX, currentY),
                new PointA(x / 10, y / 10),
                map,
                0
        );
        futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
//        List<PointA> pointAS = futureTask.get();

//        this.targetX = x / 10;
//        this.targetY = y / 10;
    }

    public boolean isMoving() {
        return (currentX != targetX)
                || (currentY != targetY);
    }

    public boolean isAnimated() {
        return image.isAnimated();
    }

    @SneakyThrows
    public void draw(Renderer renderer, int cameraX, int cameraY) {
        int frame = isMoving() && isAnimated() ? getFrame() : 0;
        renderer.drawImage(image, currentX * 10 - cameraX, currentY * 10 - cameraY, frame);

        if (nonNull(futureTask) && futureTask.isDone()) {
            path = futureTask.get();
        }
        Image pathImg = new Image("/path_indicator2.png", 4);
        path.forEach(p -> renderer.drawImage(
                pathImg,
                p.getX() * 10 - cameraX,
                p.getY() * 10 - cameraY,
                (framePosTest / pathImg.getFrames()) % pathImg.getFrames())
        );
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

        // test
        framePosTest += 1;

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

    private boolean isCollide(int targetX, int targetY) {
        return map[targetX][targetY] > 1;
    }
}
