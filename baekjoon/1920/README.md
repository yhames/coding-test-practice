# [백준 1920 - 수 찾기](https://www.acmicpc.net/problem/1920)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
      * [Set을 이용한 방법](#set을-이용한-방법)
      * [Vector를 이용한 방법](#vector를-이용한-방법)

## 문제분석

### 요구사항

`N`개의 정수 안에 정수 `X`가 존재하는지 출력

존재하면 `1`, 존재하지 않으면 `0`을 출력

### 조건

정수의 개수 `N` : `1 ≤ N ≤ 100,000`

찾고자 하는 정수의 개수 `M` : `1 ≤ N ≤ 100,000`

모든 정수의 범위 : `2^-31 ≤ N < 2^31`

### 제한사항

시간 제한 : `1초`

메모리 제한 : `128MB`

## 접근방법

단순 탐색 알고리즘(`O(N)`)을 사용하면

`10^5 * 10^5 = 10^10` 이므로, 약 `100억` 번의 연산이 발생한다.

따라서 이분탐색(`O(logN)`)을 사용한다.

## 의사코드

```c++
int main() {
    for (int i = 0; i < N; ++i)
        배열에 정수 A[1..N]을 저장;
    이분탐색을 위해 배열을 정렬한다;
    for (int i = 0; i < m; ++i) {
        if (이분탐색 결과 존재하는 경우)
            cout << 1 << '\n';
        else if (이분탐색 결과 존재하지 않는 경우)
            cout << 0 << '\n';
    }
    return 0;
}

```

## 코드분석

### 시간복잡도

* 입력값 : 배열의 원소 N, 찾을 정수의 개수 M
* 연산 : 비교연산 
  * STL의 퀵정렬을 사용하여 이분탐색을 위해 정렬하면 O(logN)의 연산이 발생한다.
  * 이진탐색의 시간복잡도는 O(logN)이므로
  * 최종 시간복잡도는 `logN + M * logN = O((M+1)logN) = O(NlogN)`이 된다.
  * 따라서, `10^5 * log(10^5) = 500000`번의 연산이 발생한다. 

### 리펙토링

#### Set을 이용한 방법

c++의 STL(algorithm)에 이진탐색을 위한 binary_search() 함수가 정의되어 있지만

STL에 Set 자료구조 또한 내부적으로 이진탐색을 사용한다.

하지만 코드를 제출했을 때 사용되는 메모리와 시간을 봤을 때

vector를 sort()하고 binary_search()하는 것이 더 빠르다.

```c++
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
```