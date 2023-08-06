# [프로그래머스 코딩테스트 연습 level2 - 프로세스](https://school.programmers.co.kr/learn/courses/30/lessons/42587)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)

## 문제분석

### 요구사항

> `location` 프로세스가 몇 번째로 실행되는지 return

### 조건

> 1. 실행 대기 큐(Queue)에서 대기중인 프로세스 하나를 꺼냅니다.
> 2. 큐에 대기중인 프로세스 중 우선순위가 더 높은 프로세스가 있다면 방금 꺼낸 프로세스를 다시 큐에 넣습니다.
> 3. 만약 그런 프로세스가 없다면 방금 꺼낸 프로세스를 실행합니다. (한 번 실행한 프로세스는 다시 큐에 넣지 않고 그대로 종료됩니다.)

> `priorities` : 현재 실행 대기 큐(Queue)에 있는 프로세스의 중요도가 순서대로 담긴 배열   
> `location` : 몇 번째로 실행되는지 알고싶은 프로세스의 위치

### 제한사항

> 1 ≦ `priorities` ≦ 100

> 1 ≦ `priorities`의 원소 ≦ 9

> `priorities`의 원소는 숫자가 클 수록 우선순위가 높습니다.

> 0 ≦ `location` ≦ `priorities.length` - 1

> `priorities`의 가장 앞에 있으면 `0`, 두 번째에 있으면 `1`...

## 접근방법

문제에서 제시된 운영체제 규칙에 맞게 접근한다.  
큐의 목록은 priorities가 아닌 priorities의 index로 한다.

## 의사코드

```java
Queue<Integer> queue = new LinkedList<>();
List<Integer> executedProcess = new ArrayList<>();

while(queue.isEmpty){
    int currentProcess = queue.poll();
    if(isTopPriority()) {
        executedProcess.add(currentProcess);
    } else {
        queue.offer(currentProcess);
    }    
}

return executedProcess.indexOf(location);
```

## 코드분석

### 시간복잡도

입력값 : `priorities`의 길이 N
연산 : 우선순위 비교연산
최악의 경우 : 모든 작업이 오름차순으로 정렬된 경우 `O(N^3)`
* 모든 작업이 오름차순으로 정렬되어있으면 최우선순위 process가 실행되기 위해서 앞의 모든 원소를 큐 뒤로 옮겨야한다.
* 따라서 우선순위 비교연산은 `N + (N-1) + ... + 1` 이므로 `O(N(N+1)/2) = O(N^2)`이다.
* 또한 우선순위 비교연산 자체도 큐의 모든 원소를 순회하므로 `O(N)`이다.
* 따라서 `O(N(N+1)/2 * N)`이므로 시간복잡도는 `O(N^3)`이 된다.