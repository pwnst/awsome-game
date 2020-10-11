import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private MyKeyListener keyListener;
    private int[][] map;
    private List<Obj> objects;
    private Obj player;

    private int w = 320;
    private int h = 240;

    private int tileSize = 5;

    private int tileWidth = w / tileSize;
    private int tileHeight = h / tileSize;

    public Game(MyKeyListener keyListener) {
        this.map = new int[tileWidth][tileHeight];
        for (int i = 0; i < tileWidth; i++) {
            for (int j = 0; j < tileHeight; j++) {
                if (j == 15 && i > 7 && i < 20) {
                    map[i][j] = 1;
                } else if (j < 18) {
                    map[i][j] = 0;
                } else {
                    map[i][j] = 1;
                }
            }
        }
        this.keyListener = keyListener;
        this.player = new Obj();
        this.objects = new LinkedList<>();
        this.objects.add(this.player);
        this.player.setMap(this.map);
        this.player.setObjects(this.objects);
        this.player.setKeyListener(this.keyListener);
        this.keyListener.setPlayer(this.player);
    }

    public void draw(Graphics2D backgroundGraphics) {
        for (int i = 0; i < tileWidth; i++) {
            for (int j = 0; j < tileHeight; j++) {
                int i1 = map[i][j];
                Color color = i1 == 0 ? Color.GREEN : Color.BLUE;
                backgroundGraphics.setColor(color);
                backgroundGraphics.fillRect(i * tileSize , j * tileSize , tileSize , tileSize );
            }
        }

        for (Obj o : objects) {
            o.update();
            backgroundGraphics.setColor(Color.GRAY);
            backgroundGraphics.fillRect(o.getX() * tileSize , o.getY() * tileSize , tileSize , tileSize );
        }

    }

    public MyKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(MyKeyListener keyListener) {
        this.keyListener = keyListener;
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

    public Obj getPlayer() {
        return player;
    }

    public void setPlayer(Obj player) {
        this.player = player;
    }
}
