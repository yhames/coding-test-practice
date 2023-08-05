# [프로그래머스 코딩테스트 연습 level2 - 기능개발](https://school.programmers.co.kr/learn/courses/30/lessons/42586)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
        + [큐 사용](#큐-사용)
        + [리펙토링 코드](#리펙토링-코드)
        + [비교 및 결론](#비교-및-결론)

## 문제분석

### 요구사항

각 배포마다 몇 개의 기능이 배포되는지를 return

**⭐️ 키워드 : 각 배포마다**

* 날짜 별이 아니라 각 배포마다 배포되는 기능의 개수를 반환해야하므로, 배포하지 못하는 날도 있을 수 있음.

### 조건

> 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.

* 각 진도가 100인지(개발완료가 되었는지) 확인하는 조건문 필요

> 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고  
> 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

* 앞에 있는 기능이 완료되었는지 확인하는 조건문 필요

> 먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses  
> 각 작업의 개발 속도가 적힌 정수 배열 speeds

* 반복문을 사용하여 현재 개발 진도(`progresses[i]`)에 각 기능 별 속도(`speeds[i]`)를 더함 

### 제한사항

> 1 ≦ `progresses, speeds배열의 길이` ≦ 100

> `progresses` ∈ 100 미만의 자연수 

* 적어도 하루는 소요됨

> `speeds` ∈ 100 이하의 자연수

> 배포는 하루에 한 번, 하루의 끝에 이루어진다고 가정  
> 즉, `95%`의 작업의 개발속도가 `4%`라고 한다면, 다음날 배포됨.

## 접근방법

반복문을 사용하여 개발진도에 각 기능별 개발속도를 더한다.

가장 앞에 있는 개발이 완료되었을 때, 배포할 수 있는 가장 뒤의 기능까지 개수를 구한다.

## 의사코드

```java
List<Integer> answer = new ArrayList<>();
int index = 0;
while(마지막 기능까지 배포될 때까지) {
    work(progresses, speeds)  // 각 기능별 개발속도를 더한다.
    if(progresses[i]==100) {
        int lastIndex = findLastProgress(progresses, index);
        answer.add(index - lastProgressIndex);
        index = lastIndex + 1;
    }
}
return answer.mapToInt(i -> i).toArray();
```

## 코드분석

### 시간복잡도

* 입력값 : `progresses`의 개수 `N`
* 연산 : `progresses` 원소가 `100`인지 검사하는 비교 연산 
* 최악의 경우 : `O(N^2)`
  * 연산이 반복되는 구간은 `work()`와 `findLastCompletedProgress()`이다.
  * 하지만 `findLastCompletedProgress()`가 `N`번 반복하는 경우(모든 작업이 100인 경우)에는 (모든 개발이 완료되었으므로)`work()`는 `1`번만 수행하고
  * `work()`가 `N`번 반복하는 경우(모든 개발이 하루에 1개씩 완료되는 경우)에는 `findLastCompletedProgress()` 또한 `1`번만 수행한다.
  * 따라서 최악의 경우 시간복잡도는 두 가지 상황에서 모두 `O(N(N-1)/2)`이므로 `O(N^2)`이다. 

### 리펙토링


#### 큐 사용

문제 분류가 `큐/스택`이어서 `큐/스택`을 사용해서 다시 풀어보기로 했다.

> 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포된다.

* 앞에 있는 기능이 먼저 배포된다는 것은 `FIFO` 방식이므로 `Queue`를 사용한다.

`progress를 완료하는데 소요되는 일수`(`progressDays`)를 비교하여 같이 배포할 수 있는지 검사한다.    
* 현재 `progressDays`가 `queue.peek()`의 `progressDays`보다 작으면  
→ `queue`에 추가  
* 현재 `progressDays`가 `queue.peek()`의 `progressDays`보다 크면,  
→ `queue`에 들어가있는 개수를 `answer`에 추가하고  
→ `queue`를 비운 후  
→ 현재 `progressDays`를 `queue`에 추가

#### 리펙토링 코드

```java
Queue<Integer> queue = new LinkedList<>();
List<Integer> answer = new ArrayList<>();

for (int i = 0; i < progresses.length; i++) {
    int progressDays = (int) Math.ceil((100 - progresses[i]) / (double) speeds[i]);

    if (queue.isEmpty()) {  // 큐가 비어있으면 추가
        queue.offer(progressDays);
        continue;
    }

    if (queue.peek() >= progressDays) { // 현재 `progressDays`가 `queue.peek()`의 `progressDays`보다 작으면
        queue.offer(progressDays);      // `queue`에 추가
    } else {                            // 현재 `progressDays`가 `queue.peek()`의 `progressDays`보다 작으면
    answer.add(queue.size());           // `queue`에 들어가있는 개수를 `answer`에 추가하고
        queue.clear();                  // `queue`를 비운 후
        queue.offer(progressDays);      // 현재 `progressDays`를 `queue`에 추가
    }

    if (i == progresses.length - 1) {   // 마지막에는 `queue`에 남아있는 모든 원소를 `answer`에 추가 
        answer.add(queue.size());
    }
}

return answer.stream().mapToInt(i -> i).toArray();
```

#### 비교 및 결론

* 입력값 : `progresses`의 개수 `N`
* 연산 : 현재 `progressDays`와 큐의 맨 앞의 `progressDays` 비교 연산
* 최악의 경우: `O(N)`
* for 반복문 안에 비교연산이 3개가 있지만, `O(3N) = O(N)`이므로, 시간복잡도는 `O(N)`이다.  

→ `Queue`를 사용하면 시간복잡도가 `O(N)`이 되기 때문에 성능면에서도 이점이 있다.  
→ 또한 `while` 반복문은 무한루프 가능성이 있기때문에 `queue`를 사용하여 이를 방지하는 것이 좋겠다.

<hr>