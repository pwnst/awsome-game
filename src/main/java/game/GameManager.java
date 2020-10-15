package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import gfx.Image;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private Image image;

    public GameManager() {
        image = new Image("/test.png");
    }

    @Override
    public void update(GameContainer gc, double gt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            System.out.println("A is down");
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawImage(image, gc.getInput().getMouseX() - 50, gc.getInput().getMouseY() - 50);
    }

    public static void main(String[] args) {
        GameContainer container = new GameContainer(new GameManager());
        container.start();
    }
}
