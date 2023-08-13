import java.util.Arrays;

class Solution {
    public int solution(int n, int k) {
        return (int) Arrays.stream(Integer.toString(n, k).split("0"))
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong)
                .filter(this::isPrime)
                .count();
    }

    private boolean isPrime(long number) {
        if (number == 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int solution1 = solution.solution(1000000, 2);
        System.out.println("solution1 = " + solution1);
    }
}