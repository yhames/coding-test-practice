#include <iostream>
#include <queue>

using namespace std;

using location = pair<int, int>;

int **create_arr(const int n, const int m) {
	int **arr;

	arr = new int *[n];
	for (int i = 0; i < n; i++)
		arr[i] = new int[m];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++)
			arr[i][j] = 0;
	}
	return (arr);
}

void init_arr(int **arr, const int n, const int m) {
	char v;

	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= m; j++) {
			cin >> v;
			arr[i][j] = v - '0';
		}
	}
}

void destroy_arr(int **arr, const int n) {
	for (int i = 0; i < n; i++)
		delete[] arr[i];
	delete arr;
}

int main() {
	int n, m, depth;
	unsigned int size;
	int **arr;
	int **visited;
	location v;
	queue<location> queue;

	cin >> n >> m;
	arr = create_arr(n + 2, m + 2);
	visited = create_arr(n + 2, m + 2);
	init_arr(arr, n, m);
	depth = 0;
	queue.emplace(1, 1);
	while (!queue.empty()) {
		size = queue.size();
		depth++;
		for (int i = 0; i < size; i++) {
			v = queue.front();
			queue.pop();
			if (v.first == n && v.second == m) {
				cout << depth << '\n';
				destroy_arr(arr, n + 2);
				destroy_arr(visited, n + 2);
				return (0);
			}
			visited[v.first][v.second] = 1;
			if (arr[v.first - 1][v.second] && !visited[v.first - 1][v.second])
				queue.emplace(v.first - 1, v.second);
			if (arr[v.first][v.second - 1] && !visited[v.first][v.second - 1])
				queue.emplace(v.first, v.second - 1);
			if (arr[v.first + 1][v.second] && !visited[v.first + 1][v.second])
				queue.emplace(v.first + 1, v.second);
			if (arr[v.first][v.second + 1] && !visited[v.first][v.second + 1])
				queue.emplace(v.first, v.second + 1);
		}
	}
	destroy_arr(arr, n + 2);
	destroy_arr(visited, n + 2);
	return (0);
}