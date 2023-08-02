# [프로그래머스 코딩테스트 연습 level2 - 의상](https://school.programmers.co.kr/learn/courses/30/lessons/42578?language=java)

- [문제분석](##문제분석)
    * [요구사항](###요구사항)
    * [조건](###조건)
    * [제한사항](###제한사항)
- [접근방법](##접근방법)
- [의사코드](##의사코드)
- [코드분석](##코드분석)
    * [시간복잡도](###시간복잡도)
    * [리펙토링](###리펙토링)
        + [`Collectors.groupingBy`](#-collectorsgroupingby-)
        + [`Collectors.mapping`](#-collectorsmapping-)
        + [`Collectors.counting`](#-collectorscounting-)
        + [코드](####코드)
- [참고자료](##참고자료)

## 문제분석

### 요구사항

* 코니가 가진 의상들이 담긴 2차원 배열 clothes가 주어질 때 서로 다른 옷의 조합의 수를 return

**⭐️ 키워드 : 서로다른 옷의 조합의 수**

### 조건

> 입출력 예시 : `종류 - 이름`

* `Map` 활용

> 코니는 각 종류별로 최대 1가지 의상만 착용할 수 있습니다. 예를 들어 위 예시의 경우 동그란 안경과 검정 선글라스를 동시에 착용할 수는 없습니다.

* `HashMap`을 사용하면 종류 별 중복 해결 가능

> 착용한 의상의 일부가 겹치더라도, 다른 의상이 겹치지 않거나, 혹은 의상을 추가로 더 착용한 경우에는 서로 다른 방법으로 옷을 착용한 것으로 계산합니다.

* 종류 별 중복은 허용하지 않으면서 의상의 이름에 따른 조합의 수 구해야함

> 코니는 하루에 최소 한 개의 의상은 입습니다.

* 하나도 선택하지 않는 경우의 수 제외

### 제한사항

> 1 ≦ `의상의 수` ≦ 30

> 1 ≦ `모든 문자열의 길이` ≦ 20 (문자열 ∈ `알파벳 소문자` 혹은 `_`)

> 같은 이름을 가진 의상은 없음

## 접근방법

각 종류 별 의상을 `선택하지 않는 경우를 포함`하여 경우의 수를 구하고 서로 `곱`하면 된다.
즉, 의상의 종류 `A ... N`과 각 의상의 개수 `a ... n`이 주어질 때 서로 다른 의상의 조합의 수는
`a+1C1 * b+1C1 * ... * n+1C1`이 된다.

## 의사코드

```java
Map<종류, 가짓수> map = new HashMap<>();
for(cloth : clothes){
    if(종류가 이미 있으면){
        map에 해당 종류의 가짓수 + 1
    }else{    // 종류가 없으면
        map에 해당 종류 추가
    }
}

int answer = 1;    // 곱셈연산 초기값은 1
for(key : map.keySet()){
    answer *= map.get(key) + 1
}

return --answer;    // 모두 선택하지 않는 경우의 수 제외
```

## 코드분석

### 시간복잡도

* 입력값 : 의상의 수 `N`
* 최악의 경우 : 모든 의상이 각자 다른 종류인 경우 `O(2N) = O(N)` 
* 최선의 경우 : 모든 의상이 한가지 종류인 경우 `Ω(N)`

### 리펙토링

`Stream`의 분류함수(`Collectors.groupingBy`)를 이용해서 가독성을 높일 수 있다.

#### `Collectors.groupingBy`

```java
public final class Collectors {
    // ...

    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, toList());
    }

    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return groupingBy(classifier, HashMap::new, downstream);
    }

    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        Supplier<A> downstreamSupplier = downstream.supplier();
        // ...
    }
// ...
```

| Parameter  |               Type               | Description |
|:----------:|:--------------------------------:|:-----------:|
| classifier | `Function<? super T, ? extends K>` |    분류 기준    |
| downstream |    `Collector<? super T, A, D>`    |    분류 방식    |
| mapFactory |           `Supplier<M>`            |  Map 구성 방식  |

#### `Collectors.mapping`

```java
public final class Collectors {
    // ...
    public static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl(downstream.supplier(), (r, t) -> {
            downstreamAccumulator.accept(r, mapper.apply(t));
        }, downstream.combiner(), downstream.finisher(), downstream.characteristics());
    }
// ...
```

#### `Collectors.counting`

```java
public final class Collectors {
    // ...
    public static <T> Collector<T, ?, Long> counting() {
        return summingLong((e) -> {
            return 1L;
        });
    }
// ...
```

#### 리펙토링 코드

```java
Arrays.stream(clothes)  // Stream<String[]>
        .collect(Collectors.groupingBy(     // Map<String, Integer>
        p->p[1],  // classifier : 분류 기준
        Collectors.mapping(p->p[0],Collectors.counting())))  //  downstream : 분류 방식
        .values()   // Collection<Long>
        .stream()   // Stream<Long>
        .reduce(1L,(acc,x)->acc*x).intValue();    // int
```

## [참고자료]  
> [경우의 수, 합의 법칙, 곱의 법칙](https://mathbang.net/109#gsc.tab=0)  
> [[Java] Stream 데이터 groupingBy 예제](https://umanking.github.io/2021/07/31/java-stream-grouping-by-example/)  
> [Java stream groupingBy 정리](https://blog.naver.com/PostView.naver?blogId=semtul79&logNo=222209752515&categoryNo=13&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=search)  
> [[Java] Stream 요소를 그룹핑해서 수집하(Collectors.groupingBy)](https://cornswrold.tistory.com/387)
> [Java Stream Collector 반쪽짜리 스트림을 쓰던 그대에게. Advanced Stream!](https://jeong-pro.tistory.com/229)  
> [Collectors: counting, mapping, reducing](https://gajy.tistory.com/27)  
> [모던자바 인 액션 - 6.3 그룹화](https://product.kyobobook.co.kr/detail/S000001810171)  