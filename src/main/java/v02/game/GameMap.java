package v02.game;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Data
public class GameMap {

    private int tileSize;
    private int[][] map;
    private Obj player;


    @SneakyThrows
    public GameMap(String path) {
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

        player = new Obj("/numi1.png", 10, 10);
    }
}
