package ch03array.question01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        int width = maxPoint.x - minPoint.x + 1;
        int height = maxPoint.y - minPoint.y + 1;

        char[][] arr = new char[height][width];
        for (char[] chars : arr) {
            Arrays.fill(chars, '.');
        }

        for (Point point : points) {
            int movedX = maxPoint.x - point.x;
            int movedY = maxPoint.y - point.y;
            arr[movedY][movedX] = '*';
        }

        String[] answer = new String[arr.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = String.valueOf(arr[i]);
        }

        return answer;
    }

    private Point getMaxPoint(List<Point> points) {
        int identity = Integer.MIN_VALUE;
        int maximumX = points.stream().mapToInt(p -> p.x).reduce(identity, Math::max);
        int maximumY = points.stream().mapToInt(p -> p.y).reduce(identity, Math::max);
        return new Point(maximumX, maximumY);
    }

    private Point getMinPoint(List<Point> points) {
        int identity = Integer.MAX_VALUE;
        int minimumX = points.stream().mapToInt(p -> p.x).reduce(identity, Math::min);
        int minimumY = points.stream().mapToInt(p -> p.y).reduce(identity, Math::min);
        return new Point(minimumX, minimumY);
    }

    private Point getIntersectionPoints(int a, int b, int e, int c, int d, int f) {
        if ((a * d - b * c) == 0) {
            return null;
        }

        double x = (double) (b * f - e * d) / (a * d - b * c);
        double y = (double) (e * c - a * f) / (a * d - b * c);

        if (isInteger(x, y)) {
            return new Point((int) x, (int) y);
        }

        return null;
    }

    private boolean isInteger(double x, double y) {
        return x % 1 == 0 && y % 1 == 0;
    }

    private class Point {

        public final int x;

        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }
}
