import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Main {
    static int node;
    static int edge;
    static int[][] arr;
    static boolean[] isVisit;
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        node = Integer.parseInt(sc.next());
        edge = Integer.parseInt(sc.next());
        int root = Integer.parseInt(sc.next());

        arr = new int[node + 1][node + 1];
        isVisit = new boolean[node + 1];

        for (int i = 0; i < edge; i++) {
            int x = Integer.parseInt(sc.next());
            int y = Integer.parseInt(sc.next());
            arr[x][y] = 1;
            arr[y][x] = 1;
        }

        dfs(root);
        System.out.println();
        bfs(root);
    }

    static void dfs(int vertex) {
        System.out.print(vertex + " ");
        isVisit[vertex] = true;
        for (int i = 1; i <= node; i++) {
            if (arr[vertex][i] == 1 && !isVisit[i]) {
                dfs(i);
            }
        }
    }

    static void bfs(int root) {
        queue.add(root);
        isVisit[root] = false;
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            for (int i = 1; i <= node; i++) {
                if (arr[vertex][i] == 1 && isVisit[i]) {
                    queue.add(i);
                    isVisit[i] = false;
                }
            }
        }

    }
}