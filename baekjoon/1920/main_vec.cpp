#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n, m, num;
    vector<int> vec;

    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    cin >> n;
    for (int i = 0; i < n; ++i) {
        cin >> num;
        vec.push_back(num);
    }
    sort(vec.begin(), vec.end());
    cin >> m;
    for (int i = 0; i < m; ++i) {
        cin >> num;
        if (binary_search(vec.begin(), vec.end(), num))
            cout << 1 << '\n';
        else
            cout << 0 << '\n';
    }
    return 0;
}
