# [프로그래머스 코딩테스트 연습 level2 - 피로도](https://school.programmers.co.kr/learn/courses/30/lessons/87946)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [오답노트](#오답노트)
      + [요구사항을 제대로 확인하지 않음](#요구사항을-제대로-확인하지-않음)

## 문제분석

### 요구사항

> 유저가 탐험할수 있는 최대 던전 수를 return

**⭐️ 키워드 : 최대 던전 수**

### 조건

> 피로도는 0 이상의 정수로 표현

> `최소 필요 피로도`는 해당 던전을 탐험하기 위해 가지고 있어야 하는 최소한의 피로도

> `소모 피로도`는 던전을 탐험한 후 소모되는 피로도

> 던전은 하루에 한 번씩 탐험할 수 있음

> "최소 필요 피로도", "소모 피로도"가 담긴 2차원 배열 dungeons

> 이 던전들을 최대한 많이 탐험

* nPn 중 현재 피로도로 가능한 순열

### 제한사항

> 1 ≦ 현재 피로도 `k` ≦ 5,000 (k는 자연수)

> 1 ≦ `dungeons`의 개수 ≦ 8

* 던전의 개수가 많지 않으므로 재귀호출 고려

> `dungeons`의 각 행은 각 던전의 [`최소 필요 피로도`, `소모 피로도`]

> `소모 피로도` ≦ `최소 필요 피로도`

> 1 ≦ `최소 필요 피로도`, `소모 피로도` ≦ 1,000 (자연수)

> 서로 다른 던전의 [`최소 필요 피로도`, `소모 피로도`]가 서로 같을 수 있음

* `Map` 사용 제한

## 접근방법

DFS(Depth First Search) 방식으로 구현
* 종료조건
  * 마지막 노드 인 경우
  * 피로도가 1 미만인 경우
* 재귀호출
  * 현재 피로도가 다음 던전의 최소 필요도보다 적을 경우, 던전을 방문하지 않고 넘어감
  * 현재 피로도가 다음 던전의 최소 필요도보다 많거나 같은 경우, 던전을 방문함

## 의사코드

```java
class Solution {

    private int maxDungeonCount = 0;

    public int solution(int k, int[][] dungeons) {
        boolean[] visited = new boolean[dungeons.length];
        dfs(k, dungeons, visited, 0, 0);
        return maxDungeonCount;
    }

    private void dfs(int k, int[][] dungeons, boolean visited, int dungeonCount, int depth) {
        if (depth == dungeons.length) {
            maxDungeonCount = Math.max(maxDungeonCount, depth);
        }

        if (k < 1) {
            maxDungeonCount = Math.max(maxDungeonCount, depth);
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visitied[i]) {
                visitied[i] = true;
                if (k >= dungeons[i][0]) {
                    // 던전을 탐험
                    dfs(k - dungeons[i][0], dungeons, visitied, dungeonCount + 1, depth + 1);
                } else {
                    // 던전 탐험하지 않고 넘어감
                    dfs(k, dungeons, visitied, dungeonCount, depth + 1);
                }
                visitied[i] = false;
            }
        }
    }
}
```

## 코드분석

### 시간복잡도

* 입력값 : 던전의 개수 `N`
* 연산 : 현재 피로도와 던전 최소 피로도 비교연산
* 시간복잡도 : `O(n!)`
  * 현재 피로도와 던전 최소 피로도 비교연산은 방문하는 모든 노드에서 발생한다.
  * 방문하는 노드의 가짓수는 `nPn`이므로 `O(n!)`이다.

### 오답노트

#### 요구사항을 제대로 확인하지 않음

처음에 요구사항에서 `탐험할 수 있는 최대 던전의 수`를 구하라고 별표까지 했는데
막상 의사코드를 작성하니 `모든 던전을 탐험할 수 있는 경우의 수`를 구했다.
아마 예전에 풀었던 많은 문제에서 가능한 경우의 수를 구하는 문제들이 많아서 관성으로 코드를 작성한 것 같은데,
항상 요구사항을 제대로 기억해서 코드를 2번 작성하는 일이 없도록 해야겠다.

<hr>