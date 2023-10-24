#include <iostream>
#include <list>
#include <algorithm>

using namespace std;

int main() {
	int m;
	char cmd, x;
	string input, answer;
	list<char> editor;
	list<char>::iterator cursor;

	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	cin >> input >> m;
	for (char c: input)
		editor.push_back(c);

	cursor = editor.end();
	for (int i = 0; i < m; i++) {
		cin >> cmd;
		switch (cmd) {
			case 'L':
				if (cursor != editor.begin())
					cursor--;
				break;
			case 'D':
				if (cursor != editor.end())
					cursor++;
				break;
			case 'B':
				if (cursor != editor.begin())
					cursor = editor.erase(--cursor);
				break;
			case 'P':
				cin >> x;
				cursor = editor.insert(cursor, x);
				cursor++;
				break;
			default:
				break;
		}
	}
	for_each(editor.begin(), editor.end(),
			 [&answer](char c) { answer.push_back(c); });
	cout << answer << '\n';
	return (0);
}

