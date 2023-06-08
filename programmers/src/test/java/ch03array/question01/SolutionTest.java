package ch03array.question01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;


class SolutionTest {

    @Test
    @DisplayName("입력 예시1")
    void test01() {
        Solution solution = new Solution();
        int[][] input = {{2, -1, 4}, {-2, -1, 4}, {0, -1, 1}, {5, -8, -12}, {5, 8, 12}};
        String[] result = solution.solution(input);
        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    @DisplayName("입력 예시2")
    void test02() {

    }
}