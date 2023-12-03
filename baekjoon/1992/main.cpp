#include <iostream>

constexpr int MAX_SIZE = 1 << 6;

int n;
char map[MAX_SIZE][MAX_SIZE];
std::string answer;
int memcount = 0;

void divide(int x, int y, int size) {
    memcount++;
    int checksum = 0;
    for (int i = x; i < x + size; ++i) {
        for (int j = y; j < y + size; ++j) {
            checksum += map[i][j] - '0';
        }
    }
    if (checksum == size * size) {
        answer.append("1");
    } else if (checksum == 0) {
        answer.append("0");
    } else {
        answer.append("(");
        divide(x, y, size / 2);
        divide(x, y + size / 2, size / 2);
        divide(x + size / 2, y, size / 2);
        divide(x + size / 2, y + size / 2, size / 2);
        answer.append(")");
    }
}

int main() {
    std::cin >> n;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            std::cin >> map[i][j];
        }
    }
    divide(0, 0, n);
    std::cout << answer << '\n';
    std::cout << memcount << '\n';
    return 0;
}