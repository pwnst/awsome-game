package v02.engine.util;

import org.junit.jupiter.api.Test;
import v02.engine.util.AStar.PointA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static v02.engine.util.AStar.getPath;

class AStarTest {

    @Test
    void testPath1() {
        int[][] map = readMap("map02long");
        PointA start = new PointA(1, 1);
        PointA end = new PointA(19, 24);

        int s = Instant.now().getNano();
        List<PointA> path = getPath(start, end, map);
        System.out.println(Instant.now().getNano() - s);
//        118034000
//        99005000
//        23464000
    }

    @Test
    void testPath() {
        int[][] map = readMap("map01");
        PointA end = new PointA(9, 1);
        PointA start = new PointA(1, 9);

        List<PointA> path = getPath(start, end, map);
        markPath(path, map);

        String actual = mapToString(map);
        String expected = mapToString(readMap("map01solved"));

        assertThat(actual).isEqualTo(expected);
    }

    private String mapToString(int[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                sb.append(map[x][y]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void markPath(List<PointA> path, int[][] map) {
        path.forEach(p -> map[p.getX()][p.getY()] = 7);
    }

    private int[][] readMap(String fileName) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        requireNonNull(
                                getClass()
                                        .getClassLoader()
                                        .getResourceAsStream(fileName)
                        )
                )
        );

        int[][] rotatedMap = bufferedReader.lines()
                .map(l -> l.split(","))
                .map(row -> Stream.of(row).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        int[][] map = new int[rotatedMap[0].length][rotatedMap.length];
        for (int y = 0; y < rotatedMap.length; y++) {
            for (int x = 0; x < rotatedMap[0].length; x++) {
                map[x][y] = rotatedMap[y][x];
            }
        }
        return map;
    }
}
