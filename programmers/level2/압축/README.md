# [프로그래머스 코딩테스트 연습 level2 - 압축](https://school.programmers.co.kr/learn/courses/30/lessons/17684)

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
- [참고자료](#참고자료)

## 문제분석

### 요구사항

> 주어진 문자열을 압축한 후의 사전 색인 번호를 배열로 출력

**⭐️ 문자열의 사전 색인번호를 배열을 출력**

### 조건

> `LZW(Lempel–Ziv–Welch)` 압축을 구현
> 1. 길이가 `1`인 모든 단어를 포함하도록 사전을 초기화
> 2. 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
> 3. `w`에 해당하는 사전의 색인 번호를 출력하고, 입력에서 `w`를 제거
> 4. 입력에서 처리되지 않은 다음 글자 `c`가 남아있다면, `w+c`에 해당하는 단어를 사전에 등록

### 제한사항

> `msg` : 영문 대문자로만 이뤄진 문자열

> 1 ≤ `msg`의 길이 ≤ 1,000,000

## 접근방법

소개된 `LZW(Lempel–Ziv–Welch)` 압축을 구현한다.

요구사항이 사전 색인번호를 배열로 출력하는 것이므로
사전 색인번호를 `LZW` `3번` 과정에서 저장하여 반환한다.

## 의사코드

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    Map<String, Integer> dict = new HashMap<>();

    {
        // 길이가 1인 모든 단어를 포함하도록 사전을 초기화한다
    }

    public int[] solution(String msg) {
        List<Integer> answer = new ArrayList<>();
        int index = msg.length();
        while ({msg가 끝날때 까지}) {
            // 사전에서 현재 입력과 일치하는 가장 긴 문자열 w를 찾는다.
            String w = msg.substring(0, index);
            if ("dict에 w가 있으면") {
                // w에 해당하는 사전의 색인 번호를 출력하고
                // 입력에서 w를 제거
                if ("입력에서 처리되지 않은 다음 글자가 남아있다면") {
                    // w+c에 해당하는 단어를 사전에 등록
                }
                index = msg.length();   // 다음 msg의 가장 긴 문자열부터 찾는다.
            } else {
                index--;    // 다음으로 긴 문자열을 찾는다.
            }
        }

        return answer.stream().mapToInt(a -> a).toArray();
    }
}
```

## 코드분석

### 시간복잡도

* 입력값 : `msg`의 길이 `n`
* 연산 : `dict`에 문자열 `w`가 있는지 확인하는 연산
* 최악의 경우 :
    * `index`가 `1`씩 줄어들면서 문자열 `w`가 `dict`에 있는지 확인하므로
    * `msg`의 첫 문자까지 모든 단어가 `dict`에 없는 경우 `dict.containsKey`는 n번 수행되고
    * 모든 `while` 반복에서 첫 문자만 제거가 된다면, 그 다음 반복에서는 `n-1`번 수행된다.
    * 따라서 최악의 경우 모든 `w`가 `dict`에 없다면 연산은 총 `n(n+1)/2`번 수행된다.
    * 따라서 시간복잡도는 `O(N^2)`이다.

### 리펙토링

문제에서 `LZW 압축 알고리즘`에 대한 설명이 단계별로 나와있어서
문제를 푸는데는 어려움이 없었지만,
`의사코드`를 명확하게 작성했다면 풀이시간을 단축할 수 있을 것 같다.
따라서 의사코드를 단계별로 구체화하여 작성해보고
어떤식으로 접근해야할지 고민하고자 한다.

1. 줄 글로 작성

```markdown
1. 길이가 `1`인 모든 단어를 포함하도록 사전을 초기화
2. 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
3. `w`에 해당하는 사전의 색인 번호를 출력하고, 입력에서 `w`를 제거
4. 입력에서 처리되지 않은 다음 글자 `c`가 남아있다면, `w+c`에 해당하는 단어를 사전에 등록
```

2. 입력값 작성

```java
class Solution {
    public int[] solution(String msg) {
        // 길이가 `1`인 모든 단어를 포함하도록 사전을 초기화
        // 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
        // `w`에 해당하는 사전의 색인 번호를 출력하고, 입력에서 `w`를 제거
        // 입력에서 처리되지 않은 다음 글자 `c`가 남아있다면, `w+c`에 해당하는 단어를 사전에 등록
    }
}
```

3. 반복문 작성

```java
class Solution {
    public int[] solution(String msg) {
        Map<String, Integer> dict = new HashMap<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            dict.add(글자, 색인);   // 길이가 `1`인 모든 단어를 포함하도록 사전을 초기화

        }
        
        while (!msg.isEmpty()) {
            String w = msg.substring(0, index);
            if (사전에_w가_있으면) {   // 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
                w에_해당하는_사전의_색인_번호를_출력;
                입력에서_w를_제거;
                if (입력에서_처리되지_않은_다음_글자_c가_남아있다면) {
                    (w + c) 에_해당하는_단어를_사전에_등록;
                }
                index = msg.length();
            } else {
                index--;
            }
        }
    }
}
```

4. 반환값 작성

```java
class Solution {
    public int[] solution(String msg) {
        List<Integer> answer = new ArrayList<>();

        Map<String, Integer> dict = new HashMap<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            dict.add(글자, 색인);      // 길이가 `1`인 모든 단어를 포함하도록 사전을 초기화

        }

        while (!msg.isEmpty()) {
            msg.substring(0, index);
            if (사전에_w가_있으면) {     // 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
                answer.add(색인);     // w에_해당하는_사전의_색인_번호를_출력
                msg = msg.substring(index); // 입력에서_w를_제거
                if (입력에서_처리되지_않은_다음_글자_c가_남아있다면) {
                    dict.add(w + c, dict.size() + 1);   // (w + c) 에_해당하는_단어를_사전에_등록
                }
                index = msg.length();
            } else {
                index--;
            }
        }

        return answer.stream().mapToInt(a -> a).toArray();
    }
}
```

<hr>
