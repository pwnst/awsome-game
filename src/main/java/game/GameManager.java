package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import gfx.Image;
import gfx.ImageTile;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private Image image;

    private ImageTile imageTile;

    private double tmp = 0;

    public GameManager() {
        image = new Image("/test.png");
        imageTile = new ImageTile("/tile_test.png", 16, 16);
    }

    @Override
    public void update(GameContainer gc, double gt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            System.out.println("A is down");
        }
        tmp += gt * 20;

        if (tmp > 4) {
            tmp = 0;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
//        renderer.drawImage(image, gc.getInput().getMouseX() - 50, gc.getInput().getMouseY() - 50);
        renderer.drawImageTile(imageTile, gc.getInput().getMouseX() - 16, gc.getInput().getMouseY() - 16, (int)tmp, 0);
    }

    public static void main(String[] args) {
        GameContainer container = new GameContainer(new GameManager());
        container.start();
    }
}
