#include <iostream>
#include <list>

using namespace std;

using pair<int, int> zombie;

int main() {
	int l, ml, mk, c, z, i;
	list<zombie>::iterator iter;
	list<zombie> zombies;    // hp, damaged

	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);

	cin >> l >> ml >> mk >> c;
	for (i = 1; i <= l; i++) {
		cin >> z;
		zombies.emplace_back(z, i < ml ? i * mk : ml * mk);
	}

	while (!zombies.empty()) {
		if (zombies.front().second >= zombies.front().first)
			zombies.pop_front();
		else if (c > 0) {
			zombies.pop_front();
			c--;
			for (i = 0, iter = zombies.begin(); i < ml - 1 && iter != zombies.end(); i++, iter++)
				iter->second -= mk;
		} else {
			cout << "NO" << '\n';
			return (0);
		}
	}
	cout << "YES" << '\n';

	return (0);
}