package v02.engine;

import lombok.Data;
import lombok.SneakyThrows;
import v02.game.Camera;
import v02.game.GameMap;

@Data
public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;
    private Camera camera;

    private boolean isRunning = false;
    private int width = 320;
    private int height = 240;
    private int scale = 4;
    private String title = "TTT";

    private final double UPDATE_CAP = 1.0 / 60.0;

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void stop() {}

    public void start() {
        window = new Window(this);
        thread = new Thread(this);
        input = new Input(this);
        renderer = new Renderer(this);
        camera = new Camera(8, 8, 0, 0, new GameMap("map01"), this, renderer);
        thread.run();
    }

    public void dispose() {}

    @Override
    @SneakyThrows
    public void run() {
        isRunning = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1_000_000_000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (isRunning) {
            render = false;
            firstTime = System.nanoTime() / 1_000_000_000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP) {
                render = true;
                unprocessedTime -= UPDATE_CAP;

                game.update(this, UPDATE_CAP);
                input.update();

                if (frameTime > 1.0) {
                    frameTime = 0;
                    fps = frames;
                    System.out.println("FPS: " + fps);
                    frames = 0;
                }
            }

            if (render) {
                renderer.clear();
                game.render(this, renderer);
                window.update();
                frames++;
            } else {
                Thread.sleep(1);
            }

        }

        dispose();
    }
}
