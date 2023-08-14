import java.util.PriorityQueue;

class Solution {

    public int solution(int[] scoville, int K) {
        int answer = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int scale : scoville) {
            queue.add(scale);
        }

        Integer first = queue.poll();
        while (first < K) {  // 스코빌지수를 K와 비교한다.
            if (queue.isEmpty()) {
                return -1;  // 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1
            }

            Integer second = queue.poll();  // 두번째로 맵지않은 음식의 스코빌지수를 꺼내서
            queue.add(first + (second * 2));  // 음식을 섞고 이진트리에 추가한다.
            answer++;  // 그리고 answer에 1을 더한다.

            first = queue.poll();  // 스코빌지수가 가장 낮은 음식을 꺼낸 후
        }
        return answer;  // K 보다 큰 경우 answer을 return한다.
    }

    public static void main(String[] args) {
        int[] input = {12, 10, 9, 9, 2, 1};
        Solution solution = new Solution();
        int solution1 = solution.solution(input, 7);
        System.out.println("solution1 = " + solution1);
    }
}