class Solution {

    public int solution(int[] numbers, int target) {
        return dfs(0, 0, numbers, target);
    }

    private int dfs(int index, int current, int[] numbers, int target) {
        if (index >= numbers.length) {
            if (current == target) {
                return 1;
            }
            return 0;
        }

        return dfs(index + 1, current + numbers[index], numbers, target)
                + dfs(index + 1, current - numbers[index], numbers, target);
    }

    public static void main(String[] args) {
        int[] numbers = {1, 1, 1, 1, 1};
        Solution solution = new Solution();
        int result = solution.solution(numbers, 3);
        System.out.println("result = " + result);
    }
}