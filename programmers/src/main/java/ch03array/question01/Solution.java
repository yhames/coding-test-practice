package ch03array.question01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public void solution(List<int[]> line) {
        List<int[]> intersectionPoints = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            for (int j = i + 1; j < line.size(); j++) {
                intersectionPoints.add(getIntersectionPoint(line.get(i), line.get(j)));
            }
        }

        int[] widthAndHeight = getWidthAndHeight(intersectionPoints);
        char[][] graph = new char[widthAndHeight[0]][widthAndHeight[1]];
        for (char[] chars : graph) {
            Arrays.fill(chars, '.');
        }

    }

    private int[] getWidthAndHeight(List<int[]> vertexes) {
        int[] maxVertex = getMaxVertex(vertexes);
        int[] minVertex = getMinVertex(vertexes);
        int width = maxVertex[0] - minVertex[0];
        int height = maxVertex[1] - minVertex[1];
        return new int[]{width, height};
    }

    private int[] getMaxVertex(List<int[]> vertexes) {
        int[] maxVertex = {0, 0};
        for (int[] vertex : vertexes) {
            if (vertex[0] > maxVertex[0]) {
                maxVertex[0] = vertex[0];
            }
            if (vertex[1] > maxVertex[1]) {
                maxVertex[1] = vertex[1];
            }
        }
        return maxVertex;
    }

    private int[] getMinVertex(List<int[]> vertexes) {
        int[] minVertex = {0, 0};
        for (int[] vertex : vertexes) {
            if (vertex[0] < minVertex[0]) {
                minVertex[0] = vertex[0];
            }
            if (vertex[1] < minVertex[1]) {
                minVertex[1] = vertex[1];
            }
        }
        return minVertex;
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

    public int[] getIntersectionPoint(int[] intX, int[] intY) {
        double[] x = intArrToDoubleArr(intX);
        double[] y = intArrToDoubleArr(intY);

        if ((x[0] * y[1] - x[1] * y[0]) == 0) {
            return null;
        }
        double i = (x[1] * y[2] - x[2] * y[1]) / (x[0] * y[1] - x[1] * y[0]);
        double j = (x[2] * y[1] - x[0] * y[2]) / (x[0] * y[1] - x[1] * y[0]);
        double[] vertex = {i, j};
        if (isInteger(vertex)) {
            return doubleArrToIntArr(vertex);
        }
        return null;
    }
}
