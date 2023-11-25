#include <iostream>
#include <vector>

constexpr int MAX_SIZE = 100000;

int n;
int inorder_tree[MAX_SIZE + 1];
int postorder_tree[MAX_SIZE + 1];
std::vector<int> preorder_tree;

void divide(int i_start, int i_end, int p_start, int p_end) {
    if (i_start > i_end || p_start > p_end)
        return;

    int root = postorder_tree[p_end];
    preorder_tree.push_back(root);

    int i_root = -1;
    for (int i = i_start; i <= i_end; ++i) {
        if (inorder_tree[i] == root) {
            i_root = i;
            break;
        }
    }

    int left_size = i_root - i_start;
    divide(i_start, i_root - 1, p_start, p_start + left_size - 1);
    divide(i_root + 1, i_end, p_start + left_size, p_end - 1);
}

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);

    std::cin >> n;
    for (int i = 1; i <= n; ++i)
        std::cin >> inorder_tree[i];
    for (int i = 1; i <= n; ++i)
        std::cin >> postorder_tree[i];

    divide(1, n, 1, n);

    for (auto num: preorder_tree) {
        if (num != *preorder_tree.begin())
            std::cout << ' ';
        std::cout << num;
    }

    return (0);
}