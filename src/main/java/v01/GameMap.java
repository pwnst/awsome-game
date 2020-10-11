package v01;

import lombok.Getter;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;

@Getter
public class GameMap {

    private Integer[][] map;

    private Map<Integer, Color> colorMap = Map.of(
            0, GREEN,
            1, BLUE
    );

    private int tileSize = 5;

    @SneakyThrows
    public void load(String fileName) {
        InputStream resourceAsStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        assert resourceAsStream != null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

        String line;
        List<String> lines = new LinkedList<>();
        while ((line = bufferedReader.readLine()) != null ) {
            lines.add(line);
        }

        map = lines.stream()
                .map(s -> Arrays.stream(s.split(",")).map(Integer::valueOf).toArray(Integer[]::new))
                .toArray(Integer[][]::new);
    }

    public void draw(Graphics2D backgroundGraphics, int mapX, int mapY, int xShift, int yShift) {
        if (xShift == 0 && yShift == 0) {
            backgroundGraphics.setColor(colorMap.get(map[mapX][mapY]));
            backgroundGraphics.fillRect(mapX * tileSize, mapY * tileSize, tileSize, tileSize);
        } else {
            backgroundGraphics.setColor(colorMap.get(map[mapX][mapY]));
            backgroundGraphics.fillRect(mapX * tileSize + xShift, mapY * tileSize + yShift, tileSize - xShift, tileSize - yShift);
            if (xShift != 0) {
                backgroundGraphics.setColor(colorMap.get(map[mapX + 1][mapY]));
                backgroundGraphics.fillRect((mapX + 1) * tileSize, mapY * tileSize + yShift, xShift, tileSize - yShift);
            }
            if (yShift != 0) {
                backgroundGraphics.setColor(colorMap.get(map[mapX][mapY + 1]));
                backgroundGraphics.fillRect(mapX * tileSize, (mapY + 1) * tileSize, tileSize - xShift, yShift);
            }
            if (xShift != 0 && yShift != 0) {
                backgroundGraphics.setColor(colorMap.get(map[mapX + 1][mapY + 1]));
                backgroundGraphics.fillRect((mapX + 1) * tileSize, (mapY + 1) * tileSize, xShift, yShift);
            }
        }
    }

    public static void main(String[] args) {
        GameMap map = new GameMap();
        map.load("m1");
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                System.out.print(map.map[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
