#include <iostream>
#include <list>

using namespace std;

int main() {
	int i;
	string name;
	list<string> list;

	for (i = 1; i <= 5; i++) {
		cin >> name;
		if (name.find("FBI") != string::npos) {
			cout << i << '\n';
			list.push_back(name);
		}
	}
	if (list.empty())
		cout << "HE GOT AWAY!" << "\n";
	return (0);
}