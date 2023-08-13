import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    Map<Integer, String> hexadecimal = new HashMap<>();

    {
        for (int i = 0; i <= 16; i++) {
            if (i < 10) {
                hexadecimal.put(i, String.valueOf(i));
            } else {
                hexadecimal.put(i, String.valueOf((char) ('A' + i - 10)));
            }
        }
    }

    public String solution(int n, int t, int m, int p) {
        List<String> answer = new ArrayList<>();
        StringBuilder list = new StringBuilder();

        list.append("0");
        for (int i = 1; i < m * t; i++) {
            int index = i;
            StringBuilder sb = new StringBuilder();
            while (index > 0) {
                sb.append(hexadecimal.get(index % n));
                index = index / n;
            }
            list.append(sb.reverse());
        }

        String order = list.toString();
        for (int i = 0; i < m * t; i += m) {  // 배열을 m개로 나누고
            int index = i + p - 1;
            answer.add(String.valueOf(order.charAt(index)));
        }

        return String.join("", answer);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String solution1 = solution.solution(16, 16, 2, 1);
        System.out.println("solution1 = " + solution1);
    }
}