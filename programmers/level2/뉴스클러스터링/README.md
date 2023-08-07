# [프로그래머스 코딩테스트 연습 level2 - 뉴스클러스터링](https://school.programmers.co.kr/learn/courses/30/lessons/17677)

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

> 두 문자열의 자카드 유사도를 출력

**⭐️ 키워드 : 유사도 값은 0에서 1 사이의 실수이므로,
이를 다루기 쉽도록 65536을 곱한 후에
소수점 아래를 버리고 정수부만 출력**

### 조건

> 자카드 유사도 :  "두 집합의 교집합 크기"를 "두 집합의 합집합 크기"로 나눈 값

> 자카드 유사도는 원소의 중복을 허용하는 다중집합에 대해서 확장할 수 있다.

> 이를 이용하여 문자열 사이의 유사도를 계산

> 두 글자씩 끊어서 다중집합을 만들고 자카드 유사도 계산

> 집합 A와 집합 B가 모두 공집합일 경우에는 나눗셈이 정의되지 않으니,
> 자카드 유사도 J는 `J(A, B) = 1`로 정의

### 제한사항

> 2 ≦ `str1`, `str2`의 길이 ≦ 1000

> 공백이나 숫자, 특수 문자가 들어있는 경우는 그 글자 쌍을 버린다

> 다중집합 원소 사이를 비교할 때, 대문자와 소문자의 차이는 무시한다.

## 접근방법

입출력 예제에서 `J(aa1+aa2, AAAA12) = 43690` 이다.
영문을 제외한 문자를 모두 제거하고 유사도를 검사하면 `65536`이 나와야하는데 유사도가 `43690`이므로,
다중집합을 먼저 만들고 영문을 제외한 문자가 포함된 문자열을 버린다.

1. 두글자씩 끊어서 다중집합을 만든다.
2. 두 배열의 교집합을 구한다.
3. 두 배열의 합집합을 구한다.
4. 교집합 / 합집합을 구한 후 65536을 곱하고 정수부만 출력한다.

## 의사코드

```java
import java.util.HashMap;
import java.util.HashSet;

class Solution {
    public int solution(String str1, String str2) {
        // str1의 다중집합
        String[] a = str1.toUpperCase().split("");
        Map<String, Integer> map1 = IntStream.range(0, a.length - 1)
                .mapToObject(index -> a[index] + a[index + 1])
                .filter(str -> str.replaceAll("[^A-Z]", "").length() == 2)
                .collect(Collectors.groupingBy(t -> t, Comparator.counting));

        // str2의 다중집합
        String[] b = str2.toUpperCase().split("");
        Map<String, Integer> map2 = IntStream.range(0, a.length - 1)
                .mapToObject(index -> a[index] + a[index + 1])
                .filter(str -> str.replaceAll("[^A-Z]", "").length() == 2)
                .collect(Collectors.groupingBy(t -> t, Comparator.counting));

        // 교집합
        Map<String, Integer> 교집합 = new HashMap<>();
        map1.keySet().stream()
                .forEach((key) -> {
                    if (map2.contains(key)) {
                        교집합.put(key, Math.min(map1.get(key), map2.get(key)));
                    }
                });

        // 합집합
        Map<String, Integer> 합집합 = new HashMap<>();
        Set<String> 합집합_원소 = new HashSet<>();
        합집합_원소.addAll(map1.keySet());
        합집합_원소.addAll(map2.keySet());
        합집합_원소.keySet().stream()
                .forEach((key) -> {
                    합집합.put(key, Math.max(map1.getOrDefault(key, 0), map2.getOrDefault(key, 0)));
                });


        // 자카드 유사도 계산
        교집합_크기 = 교집합.values().stream().sum();
        합집합_크기 = 합집합.values().stream().sum();

        return (int) 교집합_크기 / 합집합_크기 * 65536;
    }
}
```

## 코드분석

### 시간복잡도

* 입력값 : `str1`의 길이 `N`, `str2`의 길이 `M`
* 연산 :
    * `다중집합에 공백, 숫자, 특수문자가 있는지 확인하는 연산`
    * `교집합 원소인지 확인하는 비교연산`
    * `합집합의 다중원소에 대한 max연산`
* 최악의 경우 : `str1`과 `str2`의 다중집합에 교집합이 없는 경우 `O(N)`
    * `다중집합에 공백, 숫자, 특수문자가 있는지 확인하는 연산` → `(N-1)+(M-1)`
    * `교집합 원소인지 확인하는 비교연산` → `N`
    * `합집합의 다중원소에 대한 max연산` → `N+M`
    * `O((N-1) + (M-1) + N + N + M) = O(3N + 2M - 2) = O(5N) = O(N)`

### 리펙토링

연산의 종류가 많고 가독성이 떨어지므로 각 연산을 메서드로 `extract` 한다.

또한 자카드 유사도를 구할 때 `long` 타입을 `double` 타입으로 캐스팅해야하고
위 공집합 조건에 따라, 합집합의 크기가 `0`인 경우 `1`을 반환해야하므로
메서드로 `extract`해서 `type casting`과 공집합을 처리한다.

#### 리펙토링 코드

```java
class Solution {
    public int solution(String str1, String str2) {
        // 다중집합
        Map<String, Long> map1 = getMultiSet(str1);
        Map<String, Long> map2 = getMultiSet(str2);

        // 교집합
        Map<String, Long> interSection = getInterSection(map1, map2);

        // 합집합
        Map<String, Long> unionSection = getUnionSection(map1, map2);

        // 자카드 유사도 계산
        return (int) (getJaccard(interSection, unionSection) * 65536);
    }

    private Map<String, Long> getMultiSet(String str) {
        // ...
    }

    private Map<String, Long> getUnionSection(Map<String, Long> map1, Map<String, Long> map2) {
        // ...
    }

    private Map<String, Long> getInterSection(Map<String, Long> map1, Map<String, Long> map2) {
        // ...

    }
    
    private double getJaccard(Map<String, Long> interSection, Map<String, Long> unionSection) {
        // ...
    }
}
```

<hr>

## 참고자료