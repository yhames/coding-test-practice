#include <iostream>
#include <map>

using namespace std;

int main() {
	int n, m, i, id;
	string name;
	string question;
	map<int, string> pokedex;
	map<string, int> pokedex_r;

	ios_base::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);
	cin >> n >> m;
	for (id = 1; id <= n; id++) {
		cin >> name;
		pokedex.emplace(id, name);
		pokedex_r.emplace(name, id);
	}
	for (i = 0; i < m; i++) {
		cin >> question;
		if (isdigit(question[0]))
			cout << pokedex.find(stoi(question, 0, 10))->second << '\n';
		else
			cout << pokedex_r.find(question)->second << '\n';
	}
	return (0);
}