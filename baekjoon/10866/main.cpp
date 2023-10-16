#include <iostream>
#include <deque>

using namespace std;

int main() {
	int n, x;
	string cmd;
	deque<int> deque;

	cin >> n;
	while (n-- > 0) {
		cin >> cmd;
		if (cmd == "push_front") {
			cin >> x;
			deque.push_front(x);
		} else if (cmd == "push_back") {
			cin >> x;
			deque.push_back(x);
		} else if (cmd == "pop_front") {
			if (deque.empty())
				cout << "-1" << endl;
			else {
				cout << deque.front() << endl;
				deque.pop_front();
			}
		} else if (cmd == "pop_back") {
			if (deque.empty())
				cout << "-1" << endl;
			else {
				cout << deque.back() << endl;
				deque.pop_back();
			}
		} else if (cmd == "size")
			cout << deque.size() << endl;
		else if (cmd == "empty")
			cout << deque.empty() << endl;
		else if (cmd == "front") {
			if (deque.empty())
				cout << "-1" << endl;
			else
				cout << deque.front() << endl;
		} else if (cmd == "back") {
			if (deque.empty())
				cout << "-1" << endl;
			else
				cout << deque.back() << endl;
		}
	}
	return (0);
}