# [백준 11403 - 경로 찾기](https://www.acmicpc.net/problem/11403)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
      * [플로이드-와샬 알고리즘](#플로이드-와샬-알고리즘)
      * [리펙토링 코드](#리펙토링-코드)


## 문제분석

### 요구사항

**가중치 없는 방향 그래프** `G`가 주어졌을 때,  
모든 정점 `(i, j)`에 대해서,
`i`에서 `j`로 가는 길이가 양수인 경로가 있는지 구하라.

* 문제의 정답을 `인접행렬` 형식으로 출력
* `i`에서 `j`로 가는 경로가 있으면 `1`,
* `i`에서 `j`로 가는 경로가 없으면 `0`

### 조건

* 정점의 개수 `N` : `1 ≤ N ≤ 100`
* 그래프 인접 행렬 `1` → 간선 있음
* 그래프 인접 행렬 `0` → 간선 없음

## 접근방법

가중치 없는 방향 그래프 인접행렬을 `DFS` 방식으로 탐색한다.  

`방문한 노드`는 `간선이 연결`되어있다는 의미이므로 `1`을 표시한다.

## 의사코드

```c++
bool dfs(int **arr, const int n, const int departure, const int destination, int depth) {
	if (depth > n)
		return (false);
	if (destination에 도달시)
		return (true);
	for (int i = 0; i < n; i++) {
		if ((departure → i && i → destination) 이면)
			return (true);
	}
	return (false);
}

int main() {
	visited = create_arr(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (dfs(arr, n, i, j, 0))
				visited[i][j] = 1;
		}
	}
	return (0);
}
```

## 코드분석

### 리펙토링

간선에 대한 정보가 인접행렬 형태로 주어지므로

굳이 DFS를 통해 탐색할 필요없이 인접행렬만 가지고 간선이 존재하는지 확인할 수 있다.

이를 위해 최단 경로를 구하는 플로이드-와샬 알고리즘을 활용할 수 있다. 

#### 플로이드-와샬 알고리즘

플로이드-와샬 알고리즘은 

`start`에서 `end`까지 가는 데 걸리는 `최단거리`를 구하기 위해,

`start`에서 `mid`까지 가는 데 걸리는 `최단거리`와

`mid`에서 `end`까지 가는 데 걸리는 `최단거리`를 이용한다.

즉, 모든 노드 에 대하여 `min((start → k) + (k → end))`를 구했을 때
가장 값이 작은 것이 `최단 경로`가 되는 것이다.

이를 활용하여 `start → end` 간선이 존재하지 않는 경우,   
`(start → k) + (k → end)`가 존재한다면 경로가 존재한다는 의미이므로    
`arr[start][end]`을 `1`로 변경한다.

#### 리펙토링 코드

```c++
int main() {
	for (int k = 0; k < n; k++) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!arr[i][j] && (arr[i][k] && arr[k][j]))
					arr[i][j] = 1;
			}
		}
	}
	print_arr(arr, n);
	return (0);
}
```