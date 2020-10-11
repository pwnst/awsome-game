package v01;

import lombok.SneakyThrows;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Map {

    private Integer[][] map;

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

    public static void main(String[] args) {
        Map map = new Map();
        map.load("m1");
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[0].length; j++) {
                System.out.print(map.map[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
