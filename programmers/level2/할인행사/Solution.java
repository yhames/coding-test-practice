import java.util.Map;
import java.util.HashMap;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            map.put(want[i], number[i]);
        }

        Map<String, Integer> shoppingList = new HashMap<>();
        for (int i = 0; i <= discount.length - 10; i++) {
            shoppingList.putAll(map);

            for (int j = i; j < i + 10; j++) {
                if (!shoppingList.containsKey(discount[j])) {
                    break;
                }

                int num = shoppingList.get(discount[j]) - 1;
                if (num < 0) {
                    break;
                }

                shoppingList.put(discount[j], num);
            }

            int remain = shoppingList.values().stream().mapToInt(a -> a).sum();
            if (remain == 0) {
                answer++;
            }
            shoppingList.clear();
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] want = {"banana", "apple", "rice", "pork", "pot"};
        int[] number = {3, 2, 2, 2, 1};
        String[] discount = {"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"};

        Solution solution = new Solution();
        int result = solution.solution(want, number, discount);
        System.out.println("result = " + result);
    }
}
