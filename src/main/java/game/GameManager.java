package game;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.audio.SoundClip;
import engine.gfx.Image;
import engine.gfx.ImageTile;

import java.awt.event.MouseEvent;

public class GameManager extends AbstractGame {

    private Image image;

    private SoundClip soundClip;

    private ImageTile imageTile;

    private double tmp = 0;

    public GameManager() {
        image = new Image("/test.png");
        imageTile = new ImageTile("/tile_test.png", 16, 16);
        soundClip = new SoundClip("/reload.wav");
    }

    @Override
    public void update(GameContainer gc, double gt) {
        if (gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            soundClip.play();
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
//        renderer.drawRect(30, 30, 30, 30, 0xffffffff);
//        renderer.drawFillRect(gc.getInput().getMouseX() - 10, gc.getInput().getMouseY() - 10, 320, 240, 0xffcccccc);
    }

    public static void main(String[] args) {
        GameContainer container = new GameContainer(new GameManager());
        container.start();
    }
}
