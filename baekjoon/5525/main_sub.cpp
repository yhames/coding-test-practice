#include <iostream>

using namespace std;

int main() {
	int n, m, i, j, flag, answer;
	string s;

	cin >> n >> m >> s;
	answer = 0;
	i = 0;
	while (m-- > n * 2) {
		while (s[i] == 'O')
			i++;
		for (j = i + 1, flag = 0; j < i + 1 + n * 2; j += 2) {
			if (s[j] != 'O' || s[j + 1] != 'I')
				break;
			flag++;
		}
		if (flag == n)
			answer++;
		i++;
	}
	cout << answer << '\n';
	return (0);
}