class Solution {

    private int maxDungeonCount = 0;

    public int solution(int k, int[][] dungeons) {
        boolean[] visited = new boolean[dungeons.length];
        dfs(k, dungeons, visited, 0, 0);
        return maxDungeonCount;
    }

    private void dfs(int k, int[][] dungeons, boolean[] visited, int dungeonCount, int depth) {
        if (depth == dungeons.length) {
            maxDungeonCount = Math.max(maxDungeonCount, dungeonCount);
            return;
        }

        if (k < 1) {
            maxDungeonCount = Math.max(maxDungeonCount, dungeonCount);
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                if (k >= dungeons[i][0]) {
                    dfs(k - dungeons[i][1], dungeons, visited, dungeonCount + 1, depth + 1);
                } else {
                    dfs(k, dungeons, visited, dungeonCount, depth + 1);
                }
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        int[][] dungeons = {{80, 20}, {50, 40}, {30, 10}};
        Solution solution = new Solution();
        int solution1 = solution.solution(80, dungeons);
        System.out.println("result = " + solution1);
    }
}