package v02.engine.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Objects.compare;
import static java.util.Objects.isNull;

public class AStar {

    public static List<PointA> getPath(PointA start, PointA end, int[][] map) {
        Queue<PointA> queue = new PriorityQueue<>(comparator(end));
        Set<PointA> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            PointA current = queue.poll();
            visited.add(current);
            if (current.equals(end)) {
                return buildPath(current);
            }
            queue.addAll(getNeighbors(visited, current, map));
        }
        return emptyList();
    }

    private static Comparator<? super PointA> comparator(PointA end) {
        return (a, b) -> compare(
                a.getDistance() + a.distanceTo(end),
                b.getDistance() + b.distanceTo(end),
                Double::compareTo
        );
    }

    private static List<PointA> buildPath(PointA pointA) {
        return buildPath(pointA, new LinkedList<>());
    }

    private static List<PointA> buildPath(PointA pointA, List<PointA> path) {
        if (isNull(pointA.getFrom())) {
            return path;
        } else {
            path.add(pointA);
            return buildPath(pointA.getFrom(), path);
        }
    }

    private static List<PointA> getNeighbors(Set<PointA> visited, PointA current, int[][] map) {
        List<PointA> newPoints = new LinkedList<>();
        int x = current.getX();
        int y = current.getY();
        if (map.length > x + 1 && map[x + 1][y] == 0) {
            PointA newPoint = new PointA(x + 1, y);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (x - 1 >= 0 && map[x - 1][y] == 0) {
            PointA newPoint = new PointA(x - 1, y);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (map[0].length > y + 1 && map[x][y + 1] == 0) {
            PointA newPoint = new PointA(x, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (y - 1 >= 0 && map[x][y - 1] == 0) {
            PointA newPoint = new PointA(x, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (map.length > x + 1 && map[0].length > y + 1 && map[x + 1][y + 1] == 0) {
            PointA newPoint = new PointA(x + 1, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == 0) {
            PointA newPoint = new PointA(x - 1, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (x - 1 >= 0 && map[0].length > y + 1 && map[x - 1][y + 1] == 0) {
            PointA newPoint = new PointA(x - 1, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        if (map.length > x + 1 && y - 1 >= 0 && map[x + 1][y - 1] == 0) {
            PointA newPoint = new PointA(x + 1, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints);
            }
        }
        return newPoints;
    }

    private static void addPoint(PointA newPoint, PointA current, List<PointA> newPoints) {
        newPoint.addDistance(current.getDistance());
        newPoint.addDistanceTo(current);
        newPoint.setFrom(current);
        newPoints.add(newPoint);
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = {"x", "y"})
    public static class PointA {

        private final int x;
        private final int y;
        private PointA from;
        private double distance;


        public PointA(int x, int y) {
            this.x = x;
            this.y = y;
            distance = 0d;
        }

        public void addDistanceTo(PointA other) {
            distance += distanceTo(other);
        }

        public void addDistance(double distance) {
            this.distance += distance;
        }

        public double distanceTo(PointA other) {
            int xDiff = Math.abs(this.x - other.getX());
            int yDiff = Math.abs(this.y - other.getY());
            return Math.sqrt(Math.pow(xDiff, 2d) + Math.pow(yDiff, 2d));
        }
    }
}
