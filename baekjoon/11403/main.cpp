#include <iostream>

using namespace std;

int **create_arr(int n);

void init_arr(int **arr, int n);

void print_arr(int **arr, int n);

int main() {
	int n;
	int **arr;

	cin >> n;
	arr = create_arr(n);
	init_arr(arr, n);
	for (int k = 0; k < n; k++) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!arr[i][j] && (arr[i][k] && arr[k][j]))
					arr[i][j] = 1;
			}
		}
	}
	print_arr(arr, n);
	return (0);
}

int **create_arr(const int n) {
	int **arr;

	arr = new int *[n];
	for (int i = 0; i < n; i++)
		arr[i] = new int[n];
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