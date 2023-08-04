# [프로그래머스 코딩테스트 연습 level2 - 튜플](https://school.programmers.co.kr/learn/courses/30/lessons/64065)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
        + [String.split()](#String.split())
        + [Collectors.groupingBy()](#Collectors.groupingBy())
        + [Function.identity()](#Function.identity())
        + [리펙토링 코드](#리펙토링-코드)
- [참고자료](#참고자료)

## 문제분석

### 요구사항

특정 튜플을 표현하는 집합이 담긴 문자열 s가 매개변수로 주어질 때, s가 표현하는 튜플을 배열을 return

**⭐️ 키워드 : 튜플의 배열**

### 조건

> 튜플이란 셀수있는 수량의 순서있는 열거 또는 어떤 순서를 따르는 요소들의 모음이며 다음과 같이 표현할 수 있다.  
> `(a1, a2, a3, ..., an)`

> 원소의 개수가 n개이고, 중복되는 원소가 없는 튜플`(a1, a2, a3, ..., an)`이 주어질 때(단, 모든 원소는 자연수)
> 튜플의 집합은 다음과 같이 표현할 수 있다.  
> `{{a1}, {a1, a2}, {a1, a2, a3}, {a1, a2, a3, a4}, ... {a1, a2, a3, a4, ..., an}}`

> 집합은 원소의 순서가 바뀌어도 상관없으므로 튜플 `(2, 1, 3, 4)` 의 집합은 다음과 같다.  
> `{{2}, {2, 1}, {2, 1, 3}, {2, 1, 3, 4}}`  
> `{{2, 1, 3, 4}, {2}, {2, 1, 3}, {2, 1}}`  
> `{{1, 2, 3}, {2, 1}, {1, 2, 4, 3}, {2}}`

* 튜플 `(a1, a2)`의 집합은 `{{a1}, {a1, a2}}` 이며, 이는 `{{a1}, {a2, a1}}`과 같다.

### 제한사항

> `1 ≦ s가 표현하는 튜플의 원소 ≦ 100,000` (모든 원소는 자연수)

> `1 ≦ return 하는 배열의 길이 ≦ 500`

## 접근방법

튜플의 집합은 튜플의 첫번째 원소부터 그 다음원소가 하나씩 추가되는 방식으로 만들어진다.
예를 들어 튜플 (a1, a2, a3)의 집합은 {{a1}, {a1, a2}, {a1, a2, a3}} 이다.
위 예시를 보면 첫번쨰 원소인 a1이 가장 많이 등장하고 마지막 원소인 a3는 1번만 등장한다.
따라서 모든 숫자에 대한 개수를 내림차순으로 정렬하면 튜플 집합이 표현하는 튜플을 얻을 수 있다.

## 의사코드

```java
Map<String, Integer> map = new HashMap<>();

Arrays.stream(s.replaceAll("[^0-9]", " ").split(" "))
        .filter(str -> !str.isEmpty())
        .forEach(str ->{
            if(map.containsKey(str)) {
                map.put(str, map.get(str) + 1);
            } else {
                map.put(str, 1);
            }
        });

return map.keySet()
        .sorted(Comparators.comparing(i -> map.get(i)).reversed())
        .mapToInt(Integer::parseInt)
        .toArray();
```

## 코드분석

### 시간복잡도

입력값 : 튜플 원소의 개수 `N`, 튜플 집합의 원소의 개수 `N(N+1)/2`

최악의 경우 : 모든 튜플 집합의 원소를 순회하는 경우 `O(N^2)`
* 튜플 집합의 원소의 개수는 `N(N+1)/2`이므로,
* 시간복잡도는 `O(N(N+1)/2) = O((1/2)N^2+(1/2)N)/2) = O(N^2)` 이다.

최선의 경우 : 최악의 경우와 동일

### 리펙토링

#### String.split()

`s.split(String regex)`을 사용하면 내용에 공백을 포함하지 않는다는 글을 많이 본 기억이 있어서
당연히 빈 문자열은 제외된다고 생각했는데 아니었다.
`Oracle`에서 `String` 관련 내용을 찾아보니 공백을 포함하지 않는것은 **뒤에오는 빈 문자열**만 해당되는 것이었다.

다음은 `Oracle`의 `String` 관련 내용이다.

`String.split(String regex)`    
`String.split(String regex, int limit)`  
```java
public String[] split(String regex) {
    return this.split(regex, 0);
}
```
```java
public String[] split(String regex, int limit) {
    //...
}
```
Splits this string around matches of the given regular expression.   
→ 주어진 정규식에 일치하는 문자를 기준으로 문자열을 분할한다.

`limit` : 패턴이 적용되는 횟수를 제어, 즉 배열의 길이가 된다.
* `limit > 0`인 경우  
패턴이 최대 n-1번 적용되고 배열의 길이는 n을 넘지 않는다.  
또한 배열의 마지막 원소는 마지막으로 일치하는 구분자 뒤의 모든 문자를 포함한다.
* `limit < -1`인 경우
패턴이 가능한 많이 적용되며, 배열의 길이도 제한되지 않는다.
* `limit == 0`인 경우
패턴이 가능한 많이 적용되며, 길이에 제한이 없으며 **뒤에오는 빈 문자열은 버린다**.  
즉, 마지막에 빈 문자열까지 포함시키려면 `limit`을 음수로 적용해야한다.
* 예시 : 
  ```java
  String s = "boo:and:foo"`;
  s.split(":", 2);    // {"boo", "and:foo"}
  s.split(":", 5);    // {"boo", "and", "foo"}
  s.split(":", -2);   // {"boo", "and", "foo"}
  s.split("o", 5);    // {"b", "", ":and:f", "", ""}
  s.split("o", -2);   // {"b", "", ":and:f", "", ""} -> 후행 빈 문자열 포함
  s.split("o", 0);    // {"b", "", ":and:f"} -> 후행 빈 문자열 버림
  ```

#### Collectors.groupingBy()

* `Collectors.groupingBy(Function<? super T,? extends K> classifier)`  
Returns a Collector implementing a "group by" operation on input elements of type T, grouping elements according to a classification function, and returning the results in a Map.  
→ `T` 타입의 입력값을 분류함수(`classifier`)에 따라 그룹화하여 `Map`으로 반환한다.
```java
public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier) {
    return groupingBy(classifier, toList());
}
```

* `Collectors.groupingBy(Function<? super T, ? super K> classifier, Collector<? super T,A,D) downStream)`  
Returns a Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector.  
→ `T` 타입의 입력값을 분류 함수(`classifier`)에 따라 그룹화하고, 콜렉터(`downStream`)를 사용하여 분류함수에 의해 지정된 `key`에 값을 연결하는 `reduce` 연산을 수행한다.
```java
public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
    return groupingBy(classifier, HashMap::new, downstream);
}
```

* `Collectors.groupingBy(Function<? super T,? extends K> classifier, Supplier<M> mapFactory, Collector<? super T,A,D> downstream)`  
Returns a Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements according to a classification function, and then performing a reduction operation on the values associated with a given key using the specified downstream Collector. The Map produced by the Collector is created with the supplied factory function.  
-> `T` 타입의 입력값을 분류 함수(`classifier`)에 따라 그룹화하고, 콜렉터(`downStream`)를 사용하여 분류함수에 의해 지정된 `key`에 값을 연결하는 `reduce` 연산을 수행한다.
`Collector`가 생성한 `Map`은 매개변수로 지정한 팩토리함수(`mapFactory`)로 생성된다.
```java
public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
    //...
}
```

* Type Parameters:
  * `T` - the type of the input elements  
  * `K` - the type of the keys
  * `A` - the intermediate accumulation type of the downstream collector
  * `D` - the result type of the downstream reduction 
  * `M` - the type of the resulting Map

#### Function.identity()

`Function.identity()`는 `Interface Function<T,R>`에 `static`으로 정의된 함수이다.
많은 참고자료에서 `Function.identity()`를 사용하는데, 이는 `i -> i`와 동일하다. 따라서 가독성이 좋은 쪽으로 선택하면 된다.
```java
@FunctionalInterface
public interface Function<T, R> {
    // ...
    static <T> Function<T, T> identity() {
        return (t) -> {
            return t;
        };
    }
}
```

#### 리펙토링 코드

```java
Map<String, Long> map = Arrays.stream(s.split("[^0-9]"))
    .filter(str -> !str.isEmpty())
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

return map.keySet().stream()
    .sorted(Comparator.comparing(map::get).reversed())
    .mapToInt(Integer::parseInt)
    .toArray();
```

<hr>

## 참고자료

>
> [JavaSE8 - Class String ](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)  
> [JavaSE8 - Class Collectors](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)  
> [JavaSE8 - Interface Comparator\<T\>](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html)  
> [JavaSE8 - Interface Function\<T, R\>](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)  
> [JavaSE8 - Interface Comparable\<T\>](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)  
> [가장 빨리 만나는 자바 8: 10 맵으로 모으기](https://thebook.io/006725/0086/)    
> [Java 8 lambdas, Function.identity() or t->t - StackOverflow](https://stackoverflow.com/questions/28032827/java-8-lambdas-function-identity-or-t-t)  