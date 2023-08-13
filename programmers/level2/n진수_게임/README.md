# [프로그래머스 코딩테스트 연습 level2 - n진수 게임](https://school.programmers.co.kr/learn/courses/30/lessons/17687)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [리펙토링](#리펙토링)
        + [리펙토링 코드](#리펙토링-코드)

## 문제분석

### 요구사항

> 튜브가 말해야 하는 숫자 `t`개를 공백 없이 차례대로 나타낸 문자열

**⭐️ 중요 : 전체 숫자가 아닌 튜브가 `말해야하는 숫자`**

### 조건

> `n` : 진법  
> `t` : 미리 구할 숫자의 갯수     
> `m` : 게임에 참가하는 인원   
> `p` : 튜브의 순서

### 제한사항

> 2 ≦ `n` ≦ 16  
> 0 ＜ `t` ≦ 1000  
> 2 ≦ `m` ≦ 100  
> 1 ≦ `p` ≦ m

> `10`~`15`는 각각 대문자 `A`~`F`로 출력한다.

## 접근방법

1. n진법 숫자를 1차원 배열로 나열한다.
2. 배열을 m개로 나누고
3. 각 부분배열의 p번째 숫자를 출력한다.

## 의사코드

```java
class Solution {
    public String solution(int n, int t, int m, int p) {
        List<String> answer = new ArrayList<>();

        StringBuilder list = new StringBuilder();
        for (int i = 0; i < m * t; i++) {
            int digit = i;
            StringBuilder sb = new StringBuilder();
            while (digit > 0) {
                sb.append(digit % n);   // n진법 숫자를 1차원 배열로 나열한다.
                digit = digit / n;
            }
            list.append(sb.reverse());
        }

        String order = list.toString();
        for (int i = 0; i < list.length; i += m) {  // 배열을 m개로 나누고
            // 각 부분배열의 p번째 숫자를 출력한다.
            int index = i + p - 1;
            answer.add(order.charAt(index));
        }

        return answer.stream().collect(Collectors.joining());
    }
}
```

## 코드분석

### 리펙토링

`Integer.toString()`에 `radix`에 따른 숫자를 `String`으로 변환해준다.

#### 리펙토링 코드

```java
class Solution {
    public String solution(int n, int t, int m, int p) {
        String string = IntStream.range(0, m * t)
                .mapToObj(i -> Integer.toString(i, n))  // n진법 숫자를 1차원 배열로 나열한다.
                .map(String::toUpperCase)
                .collect(Collectors.joining());

        StringBuilder answers = new StringBuilder();
        for (int i = 0; i < m * t; i += m) {    // 배열을 m개로 나누고
            answers.append(string.charAt(i + p - 1));   // 각 부분배열의 p번째 숫자를 출력한다.
        }
    }
}
```

<hr>