import java.util.Arrays;

class Solution {
    public boolean solution(String[] phone_book) {
        Arrays.sort(phone_book);

        boolean answer = true;
        for (int i = 0; i < phone_book.length - 1; i++) {
            if (!answer) {
                return false;
            }
            answer = !phone_book[i + 1].startsWith(phone_book[i]);
        }
        return answer;
    }

    private boolean isPrefix(String prefix, String phone_number) {
        return phone_number.startsWith(prefix);
    }

    public static void main(String[] args) {
        String[] input = {"119", "97674223", "1195524421"};
        String[] input2 = {"123", "345", "456"};

        Solution solution = new Solution();
        boolean solution1 = solution.solution(input);
        boolean solution2 = solution.solution(input2);
        System.out.println("solution1 = " + solution1);
        System.out.println("solution2 = " + solution2);
    }
}