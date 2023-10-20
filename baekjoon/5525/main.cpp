#include <iostream>

using namespace std;

int main() {
	int n, m, i, j, flag, answer;
	string s;

	cin >> n >> m >> s;
	for (i = 0, answer = 0; i < m; i = j) {
		while (s[i] == 'O')
			i++;
		for (j = i + 1, flag = 0; j < m; j += 2) {
			if (s[j] != 'O' || s[j + 1] != 'I')
				break;
			flag++;
		}
		if (flag >= n)
			answer += flag - (n - 1);
	}
	cout << answer << '\n';
	return (0);
}