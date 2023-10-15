#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>

using namespace std;

class Student {
private:
    int id;
    int desired;
public:
    Student(int _id, int _desired) {
        id = _id;
        desired = _desired;
    }

    int get_id() const {
        return (id);
    }

    int get_desired() const {
        return (desired);
    }
};

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
    queue<Student> stand;
    vector<int> list_a, list_b, list_c;

    cin >> n;
    for (i = 0; i < n; i++) {
        cin >> type;
        if (type == 1) {        // 유형 1
            cin >> id >> desired;
//            stand.push(Student(id, desired));
            stand.emplace(id, desired); // c++11 added
        } else if (type == 2) { // 유형2
            cin >> supplied;
            if (stand.front().get_desired() == supplied)
                list_a.push_back(stand.front().get_id());
            else if (stand.front().get_desired() != supplied)
                list_b.push_back(stand.front().get_id());
            stand.pop();
        }
    }
    while (!stand.empty()) {
        list_c.push_back(stand.front().get_id());
        stand.pop();
    }
    print_answer(list_a);
    print_answer(list_b);
    print_answer(list_c);
    return (0);
}
