# [프로그래머스 코딩테스트 연습 level2 - k진수에서 소수 개수 구하기](https://school.programmers.co.kr/learn/courses/30/lessons/92335)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
        + [제한조건에 따른 자료형 범위 예측](#제한조건에-따른-자료형-범위-예측)
        + [Integer.toString() with radix](#integertostring-with-radix)
- [참고자료](#참고자료)

## 문제분석

### 요구사항

> 위 조건에 맞는 소수의 개수

**⭐️ 중요 : 조건에 맞는 소수의 `개수`**

### 조건

> 주어진 양의 정수 `n`을 `k`진수로 변환했을 때 다음을 만족하는 `P`
> * `0P0`처럼 소수 양쪽에 `0`이 있는 경우
> * `P0`처럼 소수 오른쪽에만 `0`이 있고 왼쪽에는 아무것도 없는 경우
> * `0P`처럼 소수 왼쪽에만 `0`이 있고 오른쪽에는 아무것도 없는 경우
> * `P`처럼 소수 양쪽에 아무것도 없는 경우

> `k`진법으로 보았을 때가 아닌, `10`진법으로 보았을 때 소수여야 한다

### 제한사항

> 1 ≤ n ≤ 1,000,000

> 3 ≤ k ≤ 10

## 접근방법

10진법 n을 k진수로 변환하기 위해 n을 k로 나눈 나머지를 stack에 저장한다.

변환된 k진법 n_k를 0을 기준으로 나누고 소수의 개수를 구한다.

## 의사코드

```java
import java.util.Arrays;

class Solution {
    public int solution(int n, int k) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % k);
            n = n / k;
        }

        String n_k = sb.reverse().toString();
        return (int) Arrays.stream(n_k.split("0"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .filter(this::isPrime)
                .count();
    }

    private boolean isPrime(int number) {
        if (number == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
```

## 코드분석

### 시간복잡도

* 입력값 : 양의 정수 `n`을 `k`진수로 변환한 수를 0으로 분리했을 때의 개수 `N`
* 연산 : 소수 판별을 위한 비교연산
* 최악의경우 : `O(NlogN)`
    * 변환한 수를 `0`으로 분리했을 때 개수가 최대인 경우 `N/2`이고
    * 소수 판별을 위한 비교연산을 최대로 했을 때 `logN`이다.
    * 따라서 시간복잡도는 `O(NlogN)`이 된다.

### 리펙토링

#### 제한조건에 따른 자료형 범위 예측

`n`의 최댓값이 `1000000`이므로 이를 `2`진수로 변환했을 때 `11110100001001000000`이 된다.
따라서 소수 판별을 위해 `"0"`으로 분리했을 때 최댓값이 `int` 범위를 벗어난다.  
따라서 `Integer`가 아닌 `Long`을 사용해야한다.

```java
class Solution {
    public int solution(int n, int k) {
        //...
        String n_k = sb.reverse().toString();
        return (int) Arrays.stream(n_k.split("0"))
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong) // Long 자료형 사용
                .filter(this::isPrime)
                .count();
    }

    private boolean isPrime(long number) {  // long 자료형 사용
        //...
    }
}
```

#### Integer.toString() with radix 

> Integer.toString(int n, int radix)

n진수 게임 문제를 풀이하다가
`Integer.toString()`에 `radix`에 따른 숫자를 `String`으로 변환해준다는 것을 알게되었다.
이것을 활용하면 코드를 훨씬 줄일 수 있겠다.

```java
class Solution {
    public int solution(int n, int k) {
        return (int) Arrays.stream(Integer.toString(n, k).split("0"))
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong)
                .filter(this::isPrime)
                .count();
    }
    // ...
}
```

<hr>