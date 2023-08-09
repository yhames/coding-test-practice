# [프로그래머스 코딩테스트 연습 level2 - 타겟넘버](https://school.programmers.co.kr/learn/courses/30/lessons/43165)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
        + [리펙토링 코드](#리펙토링-코드)

## 문제분석

### 요구사항

> 타겟 넘버를 만드는 방법의 수

**⭐️ 키워드 : 경우의 수**

### 조건

> `numbers` : 사용할 수 있는 숫자가 담긴 배열  
> `target` : 타겟 넘버

> 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수

* 탐색 → `DFS`/`BFS`

### 제한사항

> 2 ≦ `numbers`의 길이 ≦ 20

> 1 ≦ `numbers`의 원소 ≦ 50

> 1 ≦ `target` ≦ 1000

## 접근방법

각 숫자를 노드로 가정하고 다음 노드를 더하는 경우와 빼는 경우의 수를 탐색
depth가 최종 노드에 도달할 시 적절성 검사를 통해 가능한 경우의 수 추가

## 의사코드

```java
class Solution {

    private static int answer = 0;

    public int solution(int[] numbers, int target) {
        dfs(0, 0, numbers, target, 0);
        return answer;
    }

    private void dfs(int index, int current, int[] numbers, int target) {
        if (index >= numbers.length) {
            if (current == target) {
                answer++;
            }
            return;
        }

        dfs(i + 1, current + numbers[i], numbers, target);
        dfs(i + 1, current - numbers[i], numbers, target);
    }
}


```

## 코드분석

### 시간복잡도

* 입력값 : `numbers`의 길이 `N`
* 연산 : 탐색 노드 방문
* 시간복잡도 : `O(2^N)`
  * 방문해야하는 노드는 각 자리수 `n`일 때 `+n`, `-n` 이므로
  * 방문해야하는 모든 노드의 수는 `2의 거듭제곱`이다.
  * 따라서 시간복잡도는 `O(2^N)`이 된다.

### 리펙토링

static 변수를 선언하지 않아도 마지막 노드에서 적절성검사를 통과하는 경우 `1`을 반환하게 한 다음,
각 dfs(더하는 경우와 빼는 경우)의 반환값을 더하여 반환하면 모든 정답인 경우를 반환하게 된다.

#### 리펙토링 코드

```java
class Solution {
    public int solution(int[] numbers, int target) {
        return dfs(0, 0, numbers, target);
    }

    private int dfs(int index, int current, int[] numbers, int target) {
        if (index >= numbers.length) {
            if (current == target) {    // 적설정 검사 통과시
                return 1;   // 1을 반환한다.
            }
            return 0;
        }

        // 적절성 검사를 통과한 모든 경우의 수를 더한다.
        return dfs(index + 1, current + numbers[index], numbers, target)    
                + dfs(index + 1, current - numbers[index], numbers, target);
    }
}
```

<hr>
