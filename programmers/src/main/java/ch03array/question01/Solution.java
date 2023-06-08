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

        int width = (int) (maxPoint.x - minPoint.x + 1);
        int height = (int) (maxPoint.y - minPoint.y + 1);

        char[][] arr = new char[height][width];
        for (char[] chars : arr) {
            Arrays.fill(chars, '.');
        }

        for (Point point : points) {
            int movedX = (int) (point.x - minPoint.x);
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
        long identity = Long.MIN_VALUE;
        long maximumX = points.stream().mapToLong(p -> p.x).reduce(identity, Math::max);
        long maximumY = points.stream().mapToLong(p -> p.y).reduce(identity, Math::max);
        return new Point(maximumX, maximumY);
    }

    private Point getMinPoint(List<Point> points) {
        long identity = Long.MAX_VALUE;
        long minimumX = points.stream().mapToLong(p -> p.x).reduce(identity, Math::min);
        long minimumY = points.stream().mapToLong(p -> p.y).reduce(identity, Math::min);
        return new Point(minimumX, minimumY);
    }

    private Point getIntersectionPoints(int a, int b, int e, int c, int d, int f) {
        if ((a * d - b * c) == 0) {
            return null;
        }

        double x = (double) (b * f - e * d) / (a * d - b * c);
        double y = (double) (e * c - a * f) / (a * d - b * c);

        if (isInteger(x, y)) {
            return new Point((long) x, (long) y);
        }

        return null;
    }

    private boolean isInteger(double x, double y) {
        return x % 1 == 0 && y % 1 == 0;
    }

    private class Point {

        public final long x;

        public final long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }
}
