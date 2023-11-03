#include <iostream>
#include <cstring>
#include <vector>
#include <queue>

class Location {
public:
	int x;
	int y;
	bool smashed;

	Location(int x, int y) {
		this->x = x;
		this->y = y;
		this->smashed = false;
	}

	Location(int x, int y, bool smashed) {
		this->x = x;
		this->y = y;
		this->smashed = smashed;
	}
};

constexpr size_t MAX_SIZE = 1000;

int n, m;
int map[MAX_SIZE + 2][MAX_SIZE + 2];
bool visited[MAX_SIZE + 2][MAX_SIZE + 2];
bool smashed[MAX_SIZE + 2][MAX_SIZE + 2];
std::vector<std::pair<int, int>> loops = {{1,  0},
										  {0,  1},
										  {-1, 0},
										  {0,  -1}};

int main() {
	char wall;
	int depth, x, y;
	size_t q_size;
	std::queue<Location> route;

	memset(map, -1, sizeof(map));
	memset(visited, 0, sizeof(visited));
	memset(smashed, 0, sizeof(smashed));
	std::cin >> n >> m;
	for (int i = 1; i <= n; ++i) {
		for (int j = 1; j <= m; ++j) {
			std::cin >> wall;
			map[i][j] = wall - '0';
		}
	}

	depth = 0;
	visited[1][1] = true;
	route.emplace(1, 1);
	while (!route.empty()) {
		q_size = route.size();
		for (size_t i = 0; i < q_size; ++i) {
			Location now = route.front();
			route.pop();
			if (now.x == n && now.y == m) {
				std::cout << depth + 1 << '\n';
				return (0);
			}
			for (auto &loop: loops) {
				x = now.x + loop.first;
				y = now.y + loop.second;
				if (!now.smashed && map[x][y] == 0 && !visited[x][y]) {
					visited[x][y] = true;
					route.emplace(x, y, false);
				}
				if (!now.smashed && map[x][y] == 1) {
					smashed[x][y] = true;
					route.emplace(x, y, true);
				}
				if (now.smashed && map[x][y] == 0 && !smashed[x][y]) {
					smashed[x][y] = true;
					route.emplace(x, y, true);
				}
			}
		}
		depth++;
	}
	std::cout << -1 << '\n';
	return (0);
}
