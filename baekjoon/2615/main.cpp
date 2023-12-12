#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

constexpr int MAX_SIZE = 19;

int map[MAX_SIZE + 2][MAX_SIZE + 2];
vector<pair<int, int>> dirs = {{-1, 1},
                               {0,  1},
                               {1,  1},
                               {1,  0}};

bool dfs(int color, int x, int y, pair<int, int> dir, int depth) {
    if (depth == 5) {
        if (map[x][y] == color)
            return (false);
        return (true);
    }
    if (map[x][y] == color)
        return (dfs(color, x + dir.first, y + dir.second, dir, depth + 1));
    return (false);
}

bool is_ohmok(int color, int x, int y) {
    for (auto &dir: dirs) {
        if (map[x + dir.first][y + dir.second] == color
            && map[x - dir.first][y - dir.second] != color) {
            if (dfs(color, x + dir.first, y + dir.second, dir, 1))
                return (true);
        }
    }
    return (false);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    memset(map, 0, sizeof(map));
    for (int i = 1; i <= MAX_SIZE; ++i) {
        for (int j = 1; j <= MAX_SIZE; ++j) {
            cin >> map[i][j];
        }
    }
    for (int i = 1; i <= MAX_SIZE; ++i) {
        for (int j = 1; j <= MAX_SIZE; ++j) {
            if (map[i][j] && is_ohmok(map[i][j], i, j)) {
                cout << map[i][j] << '\n';
                cout << i << ' ' << j << '\n';
                return (0);
            }
        }
    }
    cout << 0 << '\n';
    return (0);
}
