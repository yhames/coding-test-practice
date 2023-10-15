#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>
#include <utility>

using namespace std;

void print_answer(vector<int> list) {
    size_t len, i;

    if (list.empty()) {
        cout << "None" << endl;
        return;
    }
    sort(list.begin(), list.end());
    len = list.size();
    for (i = 0; i < len; i++) {
        if (i != 0)
            cout << " ";
        cout << list[i];
    }
    cout << endl;
}

int main() {
    int n, type, i;
    int id, desired, supplied;
    queue<pair<int, int>> stand;    // pair 사용
    vector<int> list_a, list_b, list_c;

    cin >> n;
    for (i = 0; i < n; i++) {
        cin >> type;
        if (type == 1) {        // 유형 1
            cin >> id >> desired;
            stand.emplace(id, desired); // emplace 사용
        } else if (type == 2) { // 유형2
            cin >> supplied;
            if (stand.front().second == supplied)
                list_a.push_back(stand.front().first);
            else if (stand.front().second != supplied)
                list_b.push_back(stand.front().first);
            stand.pop();
        }
    }
    while (!stand.empty()) {
        list_c.push_back(stand.front().first);
        stand.pop();
    }
    print_answer(list_a);
    print_answer(list_b);
    print_answer(list_c);
    return (0);
}
