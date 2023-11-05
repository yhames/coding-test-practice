#include <iostream>
#include <vector>
#include <cstring>

using location = std::pair<int, int>;

std::vector<location> loops = {{1,  0},
                               {0,  1},
                               {-1, 0},
                               {0,  -1}};

constexpr int MAX_SIZE = 100;

int n;
char paper[MAX_SIZE + 2][MAX_SIZE + 2];
int visited[MAX_SIZE + 2][MAX_SIZE + 2];

void dfs_rgb(int i, int j, int k) {
    int x, y;

    for (auto &loop: loops) {
        x = i + loop.first;
        y = j + loop.second;
        if (paper[x][y] && !visited[x][y] && paper[i][j] == paper[x][y]) {
            visited[x][y] = k;
            dfs_rgb(x, y, k);
        }
    }
}

void dfs_gb(int i, int j, int k) {
    int x, y;

    for (auto &loop: loops) {
        x = i + loop.first;
        y = j + loop.second;
        if (paper[x][y] && !visited[x][y] &&
            (paper[i][j] == paper[x][y] || std::abs(paper[i][j] - paper[x][y]) == 11)) {
            visited[x][y] = k;
            dfs_gb(x, y, k);
        }
    }
}

int main() {
    char color;
    int k;

    memset(paper, 0, sizeof(paper));
    memset(visited, 0, sizeof(visited));
    std::cin >> n;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; ++j) {
            std::cin >> color;
            paper[i][j] = color;
        }
    }

    k = 1;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; ++j) {
            if (!visited[i][j]) {
                visited[i][j] = k;
                dfs_rgb(i, j, k);
                k++;
            }
        }
    }
    std::cout << k - 1 << ' ';

    memset(visited, 0, sizeof(visited));
    k = 1;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; ++j) {
            if (!visited[i][j]) {
                visited[i][j] = k;
                dfs_gb(i, j, k);
                k++;
            }
        }
    }
    std::cout << k - 1 << '\n';
    return (0);
}