import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class SolutionQueue {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i < progresses.length; i++) {
            int progressDays = (int) Math.ceil((100 - progresses[i]) / (double) speeds[i]);

            if (queue.isEmpty()) {
                queue.offer(progressDays);
                continue;
            }

            if (queue.peek() >= progressDays) {
                queue.offer(progressDays);
            } else {
                answer.add(queue.size());
                queue.clear();
                queue.offer(progressDays);
            }

            if (i == progresses.length - 1) {
                answer.add(queue.size());
            }
        }

        return answer.stream().mapToInt(i -> i).toArray();
    }
}
