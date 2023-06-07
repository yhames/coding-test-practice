package ch03array.question01;

import java.util.Arrays;
import java.util.List;

public class Solution {
    public void solution(List<int[]> line) {
        for (int i = 0; i < line.size(); i++) {
            for (int j = i + 1; j < line.size(); j++) {
                Point intersectionPoints = getIntersectionPoints(line.get(i), line.get(j));
            }
        }
        
    }

    public Point getIntersectionPoints(int[] intX, int[] intY) {
        double[] x = intArrToDoubleArr(intX);
        double[] y = intArrToDoubleArr(intY);

        if ((x[0] * y[1] - x[1] * y[0]) == 0) {
            return null;
        }
        double i = (x[1] * y[2] - x[2] * y[1]) / (x[0] * y[1] - x[1] * y[0]);
        double j = (x[2] * y[1] - x[0] * y[2]) / (x[0] * y[1] - x[1] * y[0]);
        double[] vertex = {i, j};
        if (!isInteger(vertex)) {
            return null;
        }
        return new Point(doubleArrToIntArr(vertex));

    }

    public boolean isInteger(double[] intersectionPoint) {
        for (double vertex : intersectionPoint) {
            if (vertex % 1 != 0) {
                return false;
            }
        }
        return true;
    }

    public double[] intArrToDoubleArr(int[] x) {
        return Arrays.stream(x).mapToDouble(i -> i).toArray();
    }

    public int[] doubleArrToIntArr(double[] x) {
        return Arrays.stream(x).mapToInt(d -> (int) d).toArray();
    }

    public class Point {

        public final long x;

        public final long y;

        public Point(int[] line) {
            this.x = line[0];
            this.y = line[1];
        }


    }
}
