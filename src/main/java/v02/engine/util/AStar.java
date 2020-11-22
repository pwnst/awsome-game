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

    private final static double DIAGONAL_DISTANCE = 1.4142135623730951d;

    private final static double STRAIGHT_DISTANCE = 1d;

    public static List<PointA> getPath(PointA start, PointA end, int[][] map) {
        return getPath(start, end, map, 0);
    }

    public static List<PointA> getPath(PointA start, PointA end, int[][] map, int collisionMaxValue) {
        Queue<PointA> queue = new PriorityQueue<>(comparator(end));
        Set<PointA> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            PointA current = queue.poll();
//            System.out.println("visited " + visited.size() + " q " + queue.size() + " dist " + current.getDistance());
            if (current.equals(end)) {
                return buildPath(current);
            }
            visited.add(current);
            queue.addAll(getNeighbors(visited, current, map, collisionMaxValue));
        }
        return emptyList();
    }

//    private static Comparator<? super PointA> comparator(PointA end) {
//        return (a, b) -> compare(
//                a.getDistance() + a.distanceTo(end),
//                b.getDistance() + b.distanceTo(end),
//                Double::compareTo
//        );
//    }

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

    private static List<PointA> getNeighbors(Set<PointA> visited, PointA current, int[][] map, int collisionMaxValue) {
        List<PointA> newPoints = new LinkedList<>();
        int x = current.getX();
        int y = current.getY();
        if (map.length > x + 1 && map[x + 1][y] > collisionMaxValue) {
            PointA newPoint = new PointA(x + 1, y);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, STRAIGHT_DISTANCE);
            }
        }
        if (x - 1 >= 0 && map[x - 1][y] > collisionMaxValue) {
            PointA newPoint = new PointA(x - 1, y);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, STRAIGHT_DISTANCE);
            }
        }
        if (map[0].length > y + 1 && map[x][y + 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, STRAIGHT_DISTANCE);
            }
        }
        if (y - 1 >= 0 && map[x][y - 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, STRAIGHT_DISTANCE);
            }
        }
        if (map.length > x + 1 && map[0].length > y + 1 && map[x + 1][y + 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x + 1, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, DIAGONAL_DISTANCE);
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x - 1, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, DIAGONAL_DISTANCE);
            }
        }
        if (x - 1 >= 0 && map[0].length > y + 1 && map[x - 1][y + 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x - 1, y + 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, DIAGONAL_DISTANCE);
            }
        }
        if (map.length > x + 1 && y - 1 >= 0 && map[x + 1][y - 1] > collisionMaxValue) {
            PointA newPoint = new PointA(x + 1, y - 1);
            if (!visited.contains(newPoint)) {
                addPoint(newPoint, current, newPoints, DIAGONAL_DISTANCE);
            }
        }
        return newPoints;
    }

    private static void addPoint(PointA newPoint, PointA currentPoint, List<PointA> newPoints, double distance) {
        newPoint.addDistance(currentPoint.getDistance() + distance);
        newPoint.setFrom(currentPoint);
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
