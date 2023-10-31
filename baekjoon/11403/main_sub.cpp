#include <iostream>

using namespace std;

int **create_arr(int n);

void init_arr(int **arr, int n);

void print_arr(int **arr, int n);

bool dfs(int **arr, int n, int departure, int destination, int depth);

int main() {
	int n;
	int **arr;
	int **answer;

	cin >> n;
	arr = create_arr(n);
	init_arr(arr, n);
	answer = create_arr(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (dfs(arr, n, i, j, 0))
				answer[i][j] = 1;
		}
	}
	print_arr(answer, n);
	return (0);
}

int **create_arr(const int n) {
	int **arr = new int *[n];
	for (int i = 0; i < n; i++)
		arr[i] = new int[n];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
			arr[i][j] = 0;
	}
	return (arr);
}

void init_arr(int **arr, const int n) {
	int v;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> v;
			arr[i][j] = v;
		}
	}
}

void print_arr(int **arr, const int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (j != 0)
				cout << ' ';
			cout << arr[i][j];
		}
		cout << '\n';
	}
}

bool dfs(int **arr, const int n, const int departure, const int destination, int depth) {
	if (depth > n)
		return (false);
	if (depth && departure == destination)
		return (true);
	for (int i = 0; i < n; i++) {
		if (arr[departure][i] && dfs(arr, n, i, destination, depth + 1)) {
			return true;
		}
	}
	return (false);
}
