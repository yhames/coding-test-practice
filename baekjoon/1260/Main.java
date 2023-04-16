import java.util.*;

class Main {

    static int[][] arr;
    static boolean[] isVisit;
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        int n;  // 정점의 개수
        int m;  // 간선의 개수
        int v;  // 시작 노드

        Scanner sc = new Scanner(System.in);
        n = Integer.parseInt(sc.next());
        m = Integer.parseInt(sc.next());
        v = Integer.parseInt(sc.next());

        arr = new int[n + 1][n + 1];
        isVisit = new boolean[n + 1];
        for (int i = 0; i < m; i++) {
            int x = Integer.parseInt(sc.next());
            int y = Integer.parseInt(sc.next());
            arr[x][y] = 1;
            arr[y][x] = 1;
        }

        dfs(n, v);
        System.out.println();
        bfs(n, v);
    }

    static void dfs(int n, int v) {
        if (isVisit[v]) {
            return;
        }

        isVisit[v] = true;
        System.out.print(v + " ");
        for (int i = 1; i <= n; i++) {
            if (arr[v][i] == 1) {
                dfs(n, i);
            }
        }
    }

    static void bfs(int n, int v) {
        isVisit[v] = false;
        queue.add(v);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");
            for (int i = 1; i <= n; i++) {
                if (arr[node][i] == 1 && isVisit[i]) {
                    queue.add(i);
                    isVisit[i] = false;
                }
            }
        }
    }
}
