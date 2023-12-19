#include <iostream>

using namespace std;

constexpr int MAX_SIZE = 50;

int n, m;
string a[MAX_SIZE + 1], b[MAX_SIZE + 1];

bool is_equivalent() {
	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < m; ++j) {
			if (a[i][j] != b[i][j])
				return (false);
		}
	}
	return (true);
}

void filp(int x, int y) {
	for (int i = x; i < x + 3; ++i) {
		for (int j = y; j < y + 3; ++j) {
			if (i < n && j < m)
				a[i][j] = a[i][j] == '1' ? '0' : '1';
		}
	}
}

int main() {
	cin >> n >> m;
	for (int i = 0; i < n; ++i)
		cin >> a[i];
	for (int i = 0; i < n; ++i)
		cin >> b[i];
	int answer = 0;
	if (n >= 3 && m >= 3) {
		for (int i = 0; i < n - 3 + 1; ++i) {
			for (int j = 0; j < m - 3 + 1; ++j) {
				if (a[i][j] != b[i][j]) {
					answer++;
					filp(i, j);
				}
			}
		}
	}
	cout << (is_equivalent() ? answer : -1) << '\n';
	return (0);
}