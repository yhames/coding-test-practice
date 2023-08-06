import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int solution(int[] priorities, int location) {
        Queue<Integer> processes = new LinkedList<>();
        List<Integer> executed = new ArrayList<>();

        for (int i = 0; i < priorities.length; i++) {
            processes.offer(i);
        }

        while (!processes.isEmpty()) {
            int currentProcess = processes.poll();

            boolean isTopPriority = processes.stream()
                    .allMatch(process -> priorities[currentProcess] >= priorities[process]);

            if (isTopPriority) {
                executed.add(currentProcess);
            } else {
                processes.offer(currentProcess);
            }
        }

        return executed.indexOf(location) + 1;
    }

    public static void main(String[] args) {
        int[] priorities = {1, 2, 3, 4, 5};
        int location = 2;
        Solution solution = new Solution();
        int result = solution.solution(priorities, location);
        System.out.println("result = " + result);
    }
}
