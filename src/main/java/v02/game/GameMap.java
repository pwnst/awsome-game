package v02.game;

import lombok.Data;
import lombok.SneakyThrows;
import v02.engine.Renderer;
import v02.engine.gfx.Image;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Data
public class GameMap {

    private Renderer renderer;
    private int tileSize;
    private int[][] map;
    private Obj player;
    private int framePosition = 0;


    @SneakyThrows
    public GameMap(Renderer renderer, String path) {
        this.renderer = renderer;
        tileSize = 10;
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        requireNonNull(
                                getClass()
                                        .getClassLoader()
                                        .getResourceAsStream(path)
                        )
                )
        );

        int[][] rotatedMap = bufferedReader.lines()
                .map(l -> l.split(","))
                .map(row -> Stream.of(row).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        map = new int[rotatedMap[0].length][rotatedMap.length];
        for (int y = 0; y < rotatedMap.length; y++) {
            for (int x = 0; x < rotatedMap[0].length; x++) {
                map[x][y] = rotatedMap[y][x];
            }
        }

        player = new Obj(new Image("/obj/player/player01_walk.png", 5), 30, 30);
    }

    public void draw(int cameraX, int cameraY) {
        int[][] colorMap = Arrays.stream(map)
                .map(row -> Arrays.stream(row).map(this::mapToColor).toArray())
                .toArray(int[][]::new);

        for (int x = 0; x < renderer.getPixelWidth(); x++) {
            for (int y = 0; y < renderer.getPixelHeight(); y++) {
                renderer.setPixel(x, y, colorMap[(x + cameraX) / tileSize][(y + cameraY) / tileSize]);
            }
        }

        player.draw(renderer, cameraX, cameraY);
    }

    public void update() {
        updateFramePosition();
        player.update(this);
    }

    private void updateFramePosition() {
        if (framePosition < 60) {
            framePosition += 1;
        } else {
            framePosition = 0;
        }
    }

    public int mapToColor(int i) {
        switch (i) {
            case 1:
                return 0xff00ff00;
            case 2:
                return 0xff808b96;
            case 3:
                return 0xffa569bd;
            default:
                return 0xffffffff;
        }
    }
}
