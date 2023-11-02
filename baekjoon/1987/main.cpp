#include <iostream>
#include <cstring>
#include <vector>

constexpr int MAX_R = 20;
constexpr int MAX_C = 20;
constexpr int AL_COUNT = 26;

using location = std::pair<int, int>;

std::vector<location> loops = {{1,  0},
							   {0,  1},
							   {-1, 0},
							   {0,  -1}};
int r, c, max_depth;
char board[MAX_R + 2][MAX_C + 2];
bool visited_board[MAX_R + 2][MAX_C + 2];
bool visited_alpha[AL_COUNT];

int dfs(location loc, int depth) {
	int ret;
	int i, j;
	for (auto &loop: loops) {
		i = loc.first + loop.first;
		j = loc.second + loop.second;
		if (board[i][j] && !visited_board[i][j] && !visited_alpha[board[i][j] - 'A']) {
			visited_board[i][j] = 1;
			visited_alpha[board[i][j] - 'A'] = 1;
			ret = dfs({i, j}, depth + 1);
			visited_board[i][j] = 0;
			visited_alpha[board[i][j] - 'A'] = 0;
			max_depth = std::max(max_depth, ret);
		}
	}
	return (std::max(max_depth, depth));
}

int main() {
	char alpha;

	std::cin >> r >> c;
	memset(board, 0, sizeof(board));
	memset(visited_board, 0, sizeof(visited_board));
	memset(visited_alpha, 0, sizeof(visited_alpha));

	for (int i = 1; i <= r; ++i) {
		for (int j = 1; j <= c; ++j) {
			std::cin >> alpha;
			board[i][j] = alpha;
		}
	}

	visited_board[1][1] = true;
	visited_alpha[board[1][1] - 'A'] = true;
	std::cout << dfs({1, 1}, 1) << '\n';
	return (0);
}