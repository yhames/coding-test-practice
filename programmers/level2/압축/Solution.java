import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    Map<String, Integer> dict = new HashMap<>();

    {
        int index = 1;
        for (char i = 'A'; i <= 'Z'; i++) {  // 길이가 1인 모든 단어를 포함하도록 사전을 초기화한다
            dict.put(String.valueOf(i), index++);
        }
    }

    public int[] solution(String msg) {
        List<Integer> answer = new ArrayList<>();

        int index = msg.length();
        while (!msg.isEmpty()) {
            String w = msg.substring(0, index);    // 사전에서 현재 입력과 일치하는 가장 긴 문자열 w를 찾는다.
            if (dict.containsKey(w)) {
                answer.add(dict.get(w));    // w에 해당하는 사전의 색인 번호를 출력하고
                msg = msg.substring(index); // 입력에서 w를 제거
                if (!msg.isEmpty()) {       // 입력에서 처리되지 않은 다음 글자가 남아있다면,
                    String word = w + msg.charAt(0);
                    dict.put(word, dict.size() + 1);    // w+c에 해당하는 단어를 사전에 등록한다
                }
                index = msg.length();
            } else {
                index--;
            }
        }

        return answer.stream().mapToInt(a -> a).toArray();
    }

    public static void main(String[] args) {
        String msg = "TOBEORNOTTOBEORTOBEORNOT";
        Solution solution = new Solution();
        int[] solution1 = solution.solution(msg);
        for (int i : solution1) {
            System.out.println(i);
        }
    }
}