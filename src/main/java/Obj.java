import java.util.List;

public class Obj {
    private int x;
    private int y;

    private int[][] map;

    private List<Obj> objects;

    private MyKeyListener keyListener;

    private long lastTimeUpdated;

    private long last;

    private int vx = 0;
    private int vy = 0;

    public Obj() {
        this.lastTimeUpdated = System.nanoTime();
        this.x = 5;
        this.y = 5;
    }

    public Obj(int[][] map, List<Obj> objects, MyKeyListener keyListener) {
        this.lastTimeUpdated = System.nanoTime();
        this.keyListener = keyListener;
        this.map = map;
        this.objects = objects;
        this.x = 5;
        this.y = 5;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update() {
        // gravity
        long current = System.nanoTime();
        // move to move method??
        if (current - lastTimeUpdated > 1000L) {
            moveX(vx);
            moveY(vy);
            moveDown(1);
            lastTimeUpdated = current;
        }
    }

    public boolean hasPosition(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void moveX(int v) {
        this.x = x + v;
    }

    public void moveY(int v) {
        this.y = y + v;
    }

    public void moveRight(int v) {
        // check collision
        long current = System.nanoTime();
        int newX = x + v;
        boolean collisionWithOtherObj = objects.stream().anyMatch(o -> o.hasPosition(newX, y));
        boolean collisionWithMap = map[newX][y] > 0;

        if (!collisionWithOtherObj && !collisionWithMap && current - last > 100000000000000000L) {
            this.x = newX;
            last = current;
        }
    }

    public void moveLeft(int v) {
        // check collision
        int newX = x - v;
        boolean collisionWithOtherObj = objects.stream().anyMatch(o -> o.hasPosition(newX, y));
        boolean collisionWithMap = map[newX][y] > 0;

        if (!collisionWithOtherObj && !collisionWithMap) {
            this.x = newX;
        }
    }

    public void moveUp(int v) {
        // check collision
        int newY = y - v;
        boolean collisionWithOtherObj = objects.stream().anyMatch(o -> o.hasPosition(x, newY));
        boolean collisionWithMap = map[x][newY] > 0;
        boolean groundUnderFeet = map[x][y+1] > 0;

        if (!collisionWithOtherObj && !collisionWithMap && groundUnderFeet) {
            this.y = newY;
        }
    }

    public void moveDown(int v) {
        // check collision
        int newY = y + v;
        boolean collisionWithOtherObj = objects.stream().anyMatch(o -> o.hasPosition(x, newY));
        boolean collisionWithMap = map[x][newY] > 0;

        if (!collisionWithOtherObj && !collisionWithMap) {
            this.y = newY;
        }
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public List<Obj> getObjects() {
        return objects;
    }

    public void setObjects(List<Obj> objects) {
        this.objects = objects;
    }

    public MyKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(MyKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public long getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(long lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
}
