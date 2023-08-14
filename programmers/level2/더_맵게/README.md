# [프로그래머스 코딩테스트 연습 level2 - 더 맵게](https://school.programmers.co.kr/learn/courses/30/lessons/42626)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [Priority Queue](#Priority-Queue)
      * [삽입연산](#삽입연산)
      * [삭제연산](#삭제연산)
- [참고자료](#참고자료)

## 문제분석

### 요구사항

> 모든 음식의 스코빌 지수를 `K` 이상으로 만들기 위해 섞어야 하는 최소 횟수

**⭐️ 최소 횟수 구하기**

### 조건

> 스코빌 지수를 담은 배열 `scoville`
> 원하는 스코빌 지수 `K`

> `섞은 음식의 스코빌 지수` = `가장 맵지 않은 음식의 스코빌 지수` + `(두 번째로 맵지 않은 음식의 스코빌 지수 * 2)`

> 모든 음식의 스코빌 지수를 `K` 이상으로 만들 수 없는 경우에는 `-1`

### 제한사항

> 2 ≦ `scoville`의 길이 ≦ 1,000,000

> 0 ≦ `K` ≦ 1,000,000,000

> 0 ≦ 각 `scoville`의 원소 ≦ 1,000,000.

## 접근방법

섞은 음식이 추가될때마다 정렬해야하므로 <U>**우선순위 큐**</U>를 활용한다.
스코빌지수가 가장 낮은 음식을 K와 비교한 후
K보다 작을 경우 음식을 섞고, K보다 클 경우 횟수를 반환한다.

## 의사코드

1. 줄 글로 작성

```java
class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        // 음식과 스코빌지수를 이진트리로 저장한다.
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();

        // 스코빌지수가 가장 낮은 음식을 꺼낸 후
        // 스코빌지수를 K와 비교한다.
        // K 보다 작은경우,
        // 두번째로 맵지않은 음식의 스코빌지수를 꺼내서 음식을 섞고 이진트리에 추가한다.
        // 그리고 answer에 1을 더한다.
        // K 보다 큰 경우
        // answer을 return한다.
        return answer;
    }
}
```

## 코드분석

### 시간복잡도

* 입력값 : `scoville`의 길이 `N`
* 연산 : 스코빌 지수 비교연산, 우선순위 큐 삽입 연산
* 최악의 경우 : `O(Nlog(N))`
    * 스코빌 지수를 모든 음식을 순회하여 비교하면 `n-1`번 수행하고
    * 우선순위 큐에서 삽입연산의 시간복잡도는 `log(N)`이므로
    * 최악의 경우 시간복잡도는 `O(Nlog(N))`이다.

### Priority Queue

`우선순위 큐`는 일반적인 `큐`와 동작방식은 비슷하지만 우선순위가 높은 데이터부터 추출한다는 차이점이 있다.  
만약 `우선순위 큐`의 우선순위를 `데이터가 들어온 순서`로 지정하면 일반적인 `큐`와 동일하게 동작할 것이다.

`우선순위 큐`는 `배열`, `연결 리스트`, `힙` 등의 여러가지 방법으로 구현이 가능하다.

`배열`과 `연결 리스트` 방식은 `삽입` 혹은 `삭제` 연산 시간복잡도가 `O(N)`인 반면,  
`힙`은 `삽입`과 `삭제` 모두 시간복잡도가 `O(log(N))`이기 때문에 가장 효율이 좋다.

#### 삽입연산

`PriorityQueue`는 `최소 힙`으로 구현되어있다.  
`최소 힙`으로 구현되어있는 것을 확인하기 위해 `삽입 연산`을 살펴보았다.  
다음은 `PriorityQueue`의 `offer()` 메서드이다.

```java
public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable {

    // ...

    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else {
            ++this.modCount;
            int i = this.size;
            if (i >= this.queue.length) {
                this.grow(i + 1);
            }

            this.siftUp(i, e);
            this.size = i + 1;
            return true;
        }
    }

    // ...

    private void siftUp(int k, E x) {
        if (this.comparator != null) {
            siftUpUsingComparator(k, x, this.queue, this.comparator);
        } else {
            siftUpComparable(k, x, this.queue);
        }

    }

    // ...

    private static <T> void siftUpComparable(int k, T x, Object[] es) {
        Comparable key;
        int parent;
        for (key = (Comparable) x; k > 0; k = parent) {
            parent = k - 1 >>> 1;
            Object e = es[parent];
            if (key.compareTo(e) >= 0) {
                break;
            }

            es[k] = e;
        }

        es[k] = key;
    }
}
```

`PriorityQueue`의 `삽입 연산`이 이뤄지는 실질적인 메서드는 `siftUpComparable()`이다.
`offer()` 메서드를 통해 원소가 추가되면 `siftUp()` 메서드를 통해 `siftUpComparable()` 메서드를 호출한다.

`grow()`는 원소들의 배열인 `queue`의 공간을 확보하는 메서드이고  
`siftUpUsingComparator()`는 지정된 `Comparator`가 있으면 해당 `comparator`를 사용하여 비교하는 메서드이다.

`siftUpComparable()`를 자세히 보면 `최소 힙`을 사용하고 있는 것을 알 수 있다.

```java
public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable {
    // ...
    private static <T> void siftUpComparable(int k, T x, Object[] es) {
        Comparable key;
        int parent;
        for (key = (Comparable) x; k > 0; k = parent) {
            parent = k - 1 >>> 1;
            Object e = es[parent];
            if (key.compareTo(e) >= 0) {
                break;
            }

            es[k] = e;
        }

        es[k] = key;
    }
}
```

k는 추가될 노드의 인덱스이고, x는 삽입되는 원소이다.

`힙`은 `완전이진트리`이기 때문에 부모의 인덱스는
`왼쪽 자식의 인덱스 * 2` 혹은 `오른쪽 자식의 인덱스 * 2 - 1` 이다.
여기서는 `자식의 인덱스 >>> 1`로 표현했다.

`key.compareTo(e)`를 통해 `삽입되는 원소`(`x`)가 `부모의 원소`(`es[parent]`)보다 작으면
`자식 노드`(`k`)에 `부모의 원소`를 저장하고
`부모 노드의 인덱스`를 `k`에 저장하여 다음 부모 원소와 비교한다.  
이때 `key`는 변하지 않기 때문에 비교연산의 주체는 항상 삽입되는 원소 `x` 이다.

`삽입되는 원소`가 `부모의 원소`보다 크거나 같으면 반복문은 종료하고
현재 노드(`es[k]`)에 삽입되는 원소 `x`가 저장된다.
`k`가 음수가 되면 `root 노드`(`k=1`)까지 조회하여 `삽입되는 원소`가 `최우선순위`가 된 것이다.

#### 삭제연산

`삭제연산`도 동일하게 `최소 힙` 구현을 따른다.

```java
public class PriorityQueue<E> extends AbstractQueue<E> implements Serializable {
    private static <T> void siftDownComparable(int k, T x, Object[] es, int n) {
        Comparable<? super T> key = (Comparable) x;

        int child;
        for (int half = n >>> 1; k < half; k = child) {
            child = (k << 1) + 1;
            Object c = es[child];
            int right = child + 1;
            if (right < n && ((Comparable) c).compareTo(es[right]) > 0) {
                child = right;
                c = es[right];
            }

            if (key.compareTo(c) <= 0) {
                break;
            }

            es[k] = c;
        }

        es[k] = key;
    }
}
```

가장 마지막 노드의 원소 `x`와 마지막 노드의 인덱스 `n`이 매개변수로 주어진다.  

최초 `child`는 `root 노드`의 왼쪽 노드(`1`)이며, `right`은 오른쪽 노드(`2`)이다.
`왼쪽 노드`와 `오른쪽 노드`를 비교하여 작은 쪽을 `child`로 선택한다.

가장 마지막 노드의 원소 `x`가 자식 노드의 원소 `c` 보다 크면
`자식 노드의 원소`를 `현재 노드`(`es[k]`)에 저장하고
`자식 노드의 인덱스`를 `k`에 저장하여 다음 자식 노드와 비교한다.

가장 마지막 노드의 원소 `x`가 자식 노드의 원소 `c` 보다 작으면 반복문을 종료하고
`현재 노드`(`k`)에 가장 마지막 노드의 원소 `x`를 저장한다.

위 과정을 거치면 가장 상단에 `최소값`이 오게 된다.

<hr>

## 참고자료
> [C언어로 쉽게 풀어쓴 자료구조(개정3판)](https://product.kyobobook.co.kr/detail/S000001076349)