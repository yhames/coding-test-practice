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

	bool operator!=(Location op) const {
		return (this->x != op.x || this->y != op.y);
	}
};

constexpr size_t MAX_SIZE = 1000;

int n, m;
int map[MAX_SIZE + 2][MAX_SIZE + 2];
bool visited[MAX_SIZE + 2][MAX_SIZE + 2];
std::vector<std::pair<int, int>> loops = {{1,  0},
										  {0,  1},
										  {-1, 0},
										  {0,  -1}};

int main() {
	char wall;
	int depth, x, y;
	size_t q_size, timeout;
	std::queue<Location> route;

	memset(map, -1, sizeof(map));
	memset(visited, 0, sizeof(visited));
	std::cin >> n >> m;
	for (int i = 1; i <= n; ++i) {
		for (int j = 1; j <= m; ++j) {
			std::cin >> wall;
			map[i][j] = wall - '0';
		}
	}
	timeout = 0;
	depth = 0;
	visited[1][1] = true;
	route.emplace(1, 1);
	while (!route.empty()) {
		q_size = route.size();
		timeout += q_size * 4;
		std::cout << timeout << '\n';
		if (timeout > 200000000) {
			std::cout << "timeout" << '\n';
			return (0);
		}
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
				if (now != Location(x, y)) {
					if (!now.smashed && map[x][y] == 0) {
						route.emplace(x, y, false);
					} else if (!now.smashed && map[x][y] == 1) {
						route.emplace(x, y, true);
					} else if (now.smashed && map[x][y] == 0) {
						route.emplace(x, y, true);
					}
				}
			}
		}
		depth++;
	}
	std::cout << -1 << '\n';
	return (0);
}