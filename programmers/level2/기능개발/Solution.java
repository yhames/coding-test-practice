import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> answer = new ArrayList<>();
        int index = 0;

        while (index < progresses.length) {
            work(progresses, speeds);
            if (progresses[index] >= 100) {
                int lastIndex = findLastCompletedProgress(progresses, index);
                answer.add(lastIndex - index + 1);
                index = lastIndex + 1;
            }
        }

        return answer.stream().mapToInt(i -> i).toArray();
    }

    private void work(int[] progresses, int[] speeds) {
        for (int i = 0; i < progresses.length; i++) {
            if (progresses[i] < 100) {
                progresses[i] += speeds[i];
            }
        }
    }

    private int findLastCompletedProgress(int[] progresses, int index) {
        int lastIndex = index;
        for (int i = index + 1; i < progresses.length; i++) {
            if (progresses[i] >= 100) {
                lastIndex = i;
            } else {
                break;
            }
        }
        return lastIndex;

    }

    public static void main(String[] args) {
        int[] progresses = {95, 90, 99, 99, 80, 99};
        int[] speeds = {1, 1, 1, 1, 1, 1};
        Solution solution = new Solution();
        int[] results = solution.solution(progresses, speeds);
        for (int result : results) {
            System.out.println(result);
        }
    }
}
