#include <iostream>
#include <set>

using namespace std;

int main() {
    int n, m, num;
    set<int> container;

    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    cin >> n;
    for (int i = 0; i < n; ++i) {
        cin >> num;
        container.emplace(num);
    }
    cin >> m;
    for (int i = 0; i < m; ++i) {
        cin >> num;
        if (container.find(num) != container.end())
            cout << 1 << '\n';
        else
            cout << 0 << '\n';
    }
    return 0;
}
