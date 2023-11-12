#include <cstring>
#include <iostream>

void heap_sort(int n);

void build_min_heap(int n);

void heapify(int k, int n);

void swap(int i, int j);

constexpr int MAX_SIZE = 500000;

int answer;
int swap_count;
int arr[MAX_SIZE + 1];

int main() {
    int n, num;

    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);

    std::cin >> n >> answer;
    memset(arr, 0, sizeof(arr));
    for (int i = 1; i <= n; ++i) {
        std::cin >> num;
        arr[i] = num;
    }
    swap_count = 0;
    heap_sort(n);
    std::cout << -1 << '\n';
    return 0;
}

void heap_sort(int n) { // A[1..n]을 정렬한다.
    build_min_heap(n);
    for (int i = n; i > 1; --i) {
        swap(1, i); // 원소 교환
        heapify(1, i - 1);
    }
}

void build_min_heap(int n) {
    for (int i = n / 2; i > 0; --i)
        heapify(i, n);
}

// A[k]를 루트로 하는 트리를 최소 힙 성질을 만족하도록 수정한다.
// A[k]의 두 자식을 루트로 하는 서브 트리는 최소 힙 성질을 만족하고 있다.
// n은 배열 A의 전체 크기이며 최대 인덱스를 나타낸다.
void heapify(int k, int n) {
    int smaller;

    int left = 2 * k;
    int right = 2 * k + 1;
    if (right <= n) { // 자식 노드가 2개인 경우
        if (arr[left] < arr[right]) smaller = left;
        else smaller = right;
    } else if (left <= n) smaller = left; // 자식 노드가 1개인 경우
    else return; // 자식 노드가 없는 경우

    // 최소 힙 성질을 만족하지 못하는 경우 재귀적으로 수정한다.
    if (arr[smaller] < arr[k]) {
        swap(k, smaller);
        heapify(smaller, n);
    }
}

void swap(int i, int j) {
    int tmp;

    if (++swap_count >= answer) {
        std::cout << std::min(arr[i], arr[j]) << ' ' << std::max(arr[i], arr[j]) << '\n';
        exit(0);
    }
    tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
}
