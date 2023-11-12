# 힙 정렬

* [힙 정렬 시간복잡도](#힙-정렬-시간복잡도)
* [힙 자료구조 표현방법](#힙-자료구조-표현방법)
* [힙 정렬](#힙-정렬-알고리즘)
  * [최소 힙](#최소-힙)
  * [값 추출](#값-추출)
* [c++ 예시 코드](#c-예시-코드)

## 힙(Heap) 이란?

힙(`Heap`)은 `완전이진트리`의 일종으로서  
부모 노드와 자식 노드의 대소 관계에 따라 `최대 힙`과 `최소 힙`이 있다. 

> * 완전이진트리 : 자식 노드를 `최대 2개`까지만 가질 수 있고, 리프 노드를 제외한 `모든 노드가 채워져있는` 트리 구조
> * 최대 힙 : 부모 노드의 값이 자식 노드의 값보다 항상 `큰` 힙 
> * 최소 힙 : 부모 노드의 값이 자식 노드의 값보다 항상 `작은` 힙

## 힙 자료구조 표현방법

`힙(Heap)`을 연결리스트로 구현할 수도 있지만
1차원 `배열`의 형태로 간단하게 표현할 수 있다.

배열에서 현재 노드 `k`의 자식 노드는 인덱스는 `2k`와 `2k+1`으로 표현할 수 있다.  
또한 `depth`에 따라 `2의 거듭제곱`만큼의 노드가 존재하게 된다.

자세한 내용은 [PDF 참고](/baekjoon/24173/힙_정렬.pdf)

## 힙 정렬 알고리즘

### 최소 힙

```python
heap_sort(A[1..n]) { # A[1..n]을 정렬한다.
    build_min_heap(A, n);
    // ...
}
```
`힙 정렬` 알고리즘을 사용하기 위해서는
`root` 노드가 `최소`(혹은 `최대`) 값이라는 것이 보장되어야한다.    
따라서 배열을 `최소 힙` 형태로 초기화(`build_min_heap()`) 해야한다.

```python
heapify(A[], k, n) {
    left <- 2k; right <- 2k + 1;
    if (right ≤ n) then {
        if (A[left] < A[right]) then smaller <- left;
                                else smaller <- right;
    }
    else if (left ≤ n) then smaller <- left;
    else return;

    if (A[smaller] < A[k]) then {
        A[k] <-> A[smaller];
        heapify(A, smaller, n);
    }
}
```
`최소 힙`을 만족시키기 위해
먼저 자식 노드 `left`와 `right`를 구한 후  
만약 `부모 노드`가 `자식 노드`보다 크다면,
더 자식 노드 중 `더 작은 값`(`smaller`)과 `교환`(`swap`)한다.

```python
build_min_heap(A[], n) {
    for i <- ⌊n / 2⌋ downto 1
        heapify(A, i, n)
}
```
`leaf 노드 + 1`(`⌊n / 2⌋`)에서부터 `root` 노드(`1`)까지 반복하면
`최소 힙`을 만족하는 배열을 구할 수 있다.  
(`leaf 노드`는 참조할 자식 노드가 없기 때문에 탐색에서 제외된다.)

### 값 추출

```python
heap_sort(A[1..n]) { # A[1..n]을 정렬한다.
    // ...
    for i <- n downto 2 {
        A[1] <-> A[i];  # 원소 교환
        heapify(A, 1, i - 1);
    }
}
```
최소 힙의 `root 노드`는 항상 최소 값이므로
`root 노드`의 `값`을 배열의 가장 `마지막 원소`와 `교환`(`swap`)한 후  
`마지막을 제외한 나머지 배열`을 다시 `최소 힙` 형태로 초기화한다.

자세한 내용은 [PDF 참고](/baekjoon/24173/힙_정렬.pdf)

## 힙 정렬 시간복잡도

배열을 최소 힙으로 초기화 하는 시간복잡도는 `O(logN)`이다.  
값을 추출하면서 값을 정렬하게 된다면 `N`번 만큼 `최소 힙` 초기화를 반복해야하므로  
최종 `시간복잡도`는 `O(NlogN)`이 된다.

## c++ 예시 코드

```c++
void heap_sort(int n) { // A[1..n]을 정렬한다.
    build_min_heap(n);
    for (int i = n; i > 1; --i) {
        swap(1, i); // 원소 교환
        heapify(1, i - 1);
    }
}
```

```c++
void build_min_heap(int n) {
    for (int i = n / 2; i > 0; --i)
        heapify(i, n);
}
```

```c++
void heapify(int k, int n) {
    int smaller;

    int left = 2 * k;
    int right = 2 * k + 1;
    if (right <= n) {
        if (arr[left] < arr[right]) smaller = left;
        else smaller = right;
    } else if (left <= n) smaller = left;
    else return;

    if (arr[smaller] < arr[k]) {
        swap(k, smaller);
        heapify(smaller, n);
    }
}
```
