package 완전탐색.최소직각사각형;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    Solution solution = new Solution();

    @Test
    @DisplayName("완전탐색 - 최소직각사각형 - 1")
    void test() {
        int[][] sizes = {
                {60, 50},
                {30, 70},
                {60, 30},
                {80, 40}
        };
        int result = solution.solution(sizes);
        assertEquals(4000, result);
    }

    @Test
    @DisplayName("완전탐색 - 최소직각사각형 - 2")
    void test2() {
        int[][] sizes = {
                {10, 7},
                {12, 3},
                {8, 15},
                {14, 7},
                {5, 15}
        };
        int result = solution.solution(sizes);
        assertEquals(120, result);
    }

    @Test
    @DisplayName("완전탐색 - 최소직각사각형 - 3")
    void test3() {
        int[][] sizes = {
                {14, 4},
                {19, 6},
                {6, 16},
                {18, 7},
                {7, 11}
        };
        int result = solution.solution(sizes);
        assertEquals(133, result);
    }
}