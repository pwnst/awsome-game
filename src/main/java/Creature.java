import java.awt.*;

public class Creature {

    private Graphics2D backgroundGraphics;

    private int tileSize = 10;

    private long lastTime = System.nanoTime();

    private int x;
    private int y;

    public Creature(Graphics2D backgroundGraphics) {
        this.backgroundGraphics = backgroundGraphics;
        this.x = 5;
        this.y = 5;
    }

    public void draw() {
        long current = System.nanoTime();

        if (current - lastTime > 1000000000) {
            this.y = y + 1;
            lastTime = current;
        }

        backgroundGraphics.setColor(Color.GREEN);
        backgroundGraphics.fillRect(x * tileSize, y * tileSize, tileSize , tileSize );
    }
}
