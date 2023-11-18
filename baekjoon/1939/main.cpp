#include <iostream>
#include <cstring>
#include <queue>
#include <map>

constexpr int MAX_SIZE = 10000;

int n, m;
int start, end;
std::map<int, int> edges[MAX_SIZE + 1];

bool is_valid(int max_load) {
    int now;
    size_t size;
    std::queue<int> transit;
    bool visited[MAX_SIZE + 1];

    memset(visited, 0, sizeof(visited));
    transit.push(start);
    while (!transit.empty()) {
        size = transit.size();
        for (int i = 0; i < size; ++i) {
            now = transit.front();
            transit.pop();
            if (now == end)
                return (true);
            for (auto edge: edges[now]) {
                if (!visited[edge.first] && edge.second >= max_load) {
                    visited[edge.first] = true;
                    transit.emplace(edge.first);
                }
            }
        }
    }
    return (false);
}

int main() {
    int a, b, c;
    int left, right, mid;

    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    left = 0;
    right = 0;
    std::cin >> n >> m;
    for (int i = 0; i < m; ++i) {
        std::cin >> a >> b >> c;
        right = std::max(right, c);
        auto iter = edges[a].find(b);
        if (iter == edges[a].end()) {
            edges[a].emplace(b, c);
            edges[b].emplace(a, c);

        } else {
            if (iter->second < c) {
                iter->second = c;
                edges[b].find(a)->second = c;
            }
        }
    }
    std::cin >> start >> end;

    while (left < right) {
        mid = (left + right) / 2;
        if (right - left <= 1) {
            if (is_valid(right))
                std::cout << right << '\n';
            else
                std::cout << left << '\n';
            return (0);
        }
        if (is_valid(mid))
            left = mid;
        else
            right = mid;
    }
    std::cout << 0 << '\n';
    return (0);
}
