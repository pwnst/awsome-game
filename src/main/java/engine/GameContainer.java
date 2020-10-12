package engine;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;

    private boolean isRunning = false;
    private int width = 320;
    private int height = 240;
    private int scale = 4;
    private String title = "TTT";

    private final double UPDATE_CAP = 1.0 / 60.0;

    public void stop() {}

    public void start() {
        window = new Window(this);
        thread = new Thread(this);
        renderer = new Renderer(this);

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

                if (frameTime > 1.0) {
                    System.out.println("FPS: " + frames);
                    frameTime = 0;
                    frames = 0;
                }

                // TODO: UPDATE GAME
            }

            if (render) {
                renderer.clear();
                window.update();
                frames++;
                // TODO: render
            } else {
                Thread.sleep(1);
            }

        }

        dispose();
    }

    public static void main(String[] args) {
        GameContainer container = new GameContainer();
        container.start();
    }
}
