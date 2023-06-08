package ch03array.question01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public class Solution {
    public String[] solution(int[][] line) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < line.length; i++) {
            for (int j = i + 1; j < line.length; j++) {
                Point point = getIntersectionPoints(line[i][0], line[i][1], line[i][2],
                        line[j][0], line[j][1], line[j][2]);
                if (point != null) {
                    points.add(point);
                }
            }
        }

        Point maxPoint = getMaxPoint(points);
        Point minPoint = getMinPoint(points);
        int width = (maxPoint.x - minPoint.x);
        int height = (maxPoint.y - minPoint.y);

        char[][] arr = new char[width][height];
        Arrays.fill(arr, ".");

        for (Point point : points) {
            int movedX = (int) (maxPoint.x - point.x);
            int movedY = (int) (maxPoint.y - point.y);
            arr[movedY][movedX] = '*';
        }

        String[] answer = new String[arr.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = String.valueOf(arr[i]);
        }

        return answer;
    }

    private Point getMaxPoint(List<Point> points) {
        OptionalInt maximumX = points.stream().mapToInt(p -> p.x).max();
        OptionalInt maximumY = points.stream().mapToInt(p -> p.y).max();
        if (maximumX.isPresent() && maximumY.isPresent()) {
            return new Point(maximumX.getAsInt(), maximumY.getAsInt());
        }
        return null;
    }

    private Point getMinPoint(List<Point> points) {
        OptionalInt minimumX = points.stream().mapToInt(p -> p.x).min();
        OptionalInt minimumY = points.stream().mapToInt(p -> p.y).min();
        if (minimumX.isPresent() && minimumY.isPresent()) {
            return new Point(minimumX.getAsInt(), minimumY.getAsInt());
        }
        return null;
    }

    public Point getIntersectionPoints(int a, int b, int c, int d, int e, int f) {
        if ((a * d - b * c) == 0) {
            return null;
        }

        double x = (double) (b * f - e * d) / (a * d - b * c);
        double y = (double) (e * c - a * f) / (a * d - b * c);

        if (!isInteger(x, y)) {
            return null;
        }

        return new Point((int) x, (int) y);
    }

    public boolean isInteger(double x, double y) {
        return x % 1 == 0 || y % 1 == 0;
    }

    public class Point {

        public final int x;

        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }


    }
}
