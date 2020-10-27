package v02.game;


import v02.engine.AbstractGame;
import v02.engine.GameContainer;
import v02.engine.Renderer;
import v02.engine.audio.SoundClip;
import v02.engine.gfx.Image;
import v02.engine.gfx.ImageTile;

import java.awt.event.MouseEvent;

import static java.awt.event.KeyEvent.VK_A;

public class GameManager extends AbstractGame {

    private Image image;
    private Image image2;
    private SoundClip soundClip;
    private ImageTile imageTile;
    private double tmp = 0;

    public GameManager() {
        image = new Image("/test_ab.png");
        image.setAlpha(true);
        image2 = new Image("/test_ab.png");
        image2.setAlpha(true);
        imageTile = new ImageTile("/tile_test.png", 16, 16);
        soundClip = new SoundClip("/reload.wav");
    }

    @Override
    public void update(GameContainer gc, double gt) {
        Camera camera = gc.getCamera();
        if (gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            soundClip.play();
            System.out.println("BUTTON1 is pressed");
            int mapXCoord = camera.getMapXCoord(gc.getInput().getMouseX());
            int mapYCoord = camera.getMapYCoord(gc.getInput().getMouseY());
            Obj player = camera.getGameMap().getPlayer();
            player.setTargetLocation(mapXCoord, mapYCoord);

        }

        if (gc.getInput().isKeyDown(VK_A)) {
            System.out.println("A is Pressed");
        }




        tmp += gt * 20;

        if (tmp > 4) {
            tmp = 0;
        }
        camera.update();
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        gc.getCamera().display();
    }

    public static void main(String[] args) {
        GameContainer container = new GameContainer(new GameManager());
        container.start();
    }
}
