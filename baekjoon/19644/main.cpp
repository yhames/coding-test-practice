#include <iostream>
#include <list>

using namespace std;

int main() {
	int l, ml, mk, c, zombie, damage;
	list<int> damaged;

	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);

	cin >> l >> ml >> mk >> c;
	for (int i = 0; i < ml && i < l; i++) {
		cin >> zombie;
		if (zombie > damaged.back() + mk) {
			if (c > 0) {
				c--;
				damaged.push_back(damaged.back());
			} else {
				cout << "NO" << '\n';
				return (0);
			}
		} else {
			damaged.push_back(damaged.back() + mk);
		}
	}

	for (int i = ml; i < l; i++) {
		cin >> zombie;
		damage = damaged.back() - damaged.front();
		damaged.pop_front();
		if (zombie <= damage + mk) {
			damaged.push_back(damaged.back() + mk);
		} else if (c > 0) {
			c--;
			damaged.push_back(damaged.back());
		} else {
			cout << "NO" << '\n';
			return (0);
		}
	}
	cout << "YES" << '\n';
	return (0);
}