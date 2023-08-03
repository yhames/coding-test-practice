# [프로그래머스 코딩테스트 연습 level2 - 할인행사](https://school.programmers.co.kr/learn/courses/30/lessons/131127?language=java)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
        + [불필요한 객체 생성](#불필요한-객체-생성)
        + [Collection 객체와 static final](#Collection-객체와-static-final)
        + [리펙토링 코드](#리펙토링-코드)
- [참고자료](#참고자료)

## 문제분석

### 요구사항

* 회원등록시 원하는 제품을 모두 할인 받을 수 있는 회원등록 날짜의 총 일수

**⭐️ 키워드 : 모두 할인받을 수 있는 날짜의 총 일수**

### 조건

> 회원 자격은 **10일간** 유지됨 

* 연속하는 10개의 원소를 순회해야함

> 매일 **1가지 제품만** 할인하고, 할인하는 제품은 하루에 **1개만 구매가 가능**함

* `discount`배열에서 제품에 대한 중복을 허용하지 않음

> 원하는 제품을 **모두** 할인받아야함

* 순회하는 연속된 10개의 `discount` 부분배열에 `want[i] * number[i]`의 원소가 모두 포함되어야함
* 예를 들어 바나나 10개를 사야한다고 하면, discount 부분배열에 바나나가 10개가 있어야함.

### 제한사항

> 1 ≦ `want`,`number`의 길이 ≦ 10

> `number` 원소의 합은 항상 10

* 1개의 품목만을 할인하고, 1개만 구매할 수 있으며, 회원자격은 10일간만 유지되기 때문에
* 할인하는 제품을 반드시 구매하지 않을 시 모든 제품을 할인받을 수 없음 

> 1 ≦ `dicount`의 길이 ≦ 100,000

* `100,000 - 10 = 99,990` 이므로 단순 반복 가능

> `want`와 `discount`는 모두 알파벳 소문자로만 이루어져 있다.

* 제품 이름의 대소문자 처리 소요 없음

## 접근방법

`dicount` 배열을 10개씩 가져와서 `want`의 원소가 모두 포함되는지 확인한다.  
원하는 제품 목록은 제품과 수량이 다른 배열로 주어지기 때문에 `Map` 형태(`제품 - 수량`)로 저장해서  
`dicount` 배열을 순회할 때마다 적절성 검사를 하고, 해당 제품의 수량을 감소시킨다.  
마지막 적절성 검사를 통해 모든 제품의 수량이 `0`인 경우 `answer + 1`을 한다.

## 의사코드

```java
private static final Map<String, Integer> map = new HashMap<>();

for(i=0; i<want; i++) {
    map.put(want[i], number[i]);
}

for(i=0; i<=discount-10; i++) {
    // discount 순회시 새로운 Map 객체 생성하여 이전 계산이 현재에 영향을 못미치게 함.
    Map<String, Integer> shoppingList = new HashMap<>(map);
    
    for(j=i; j<i+10; j++) { // i부터 i+10 까지 연속된 10개의 원소를 순회
        if(!shoppingList.containsKey(discount[j])) {    // 적절성검사(1)
            break;                                      // 단 하루라도 할인하는 제품을 구매하지 않으면 
        }                                               // 원하는 제품을 `모두` 구매할 수 없음

        int num = shoppingList.get(discount[j]) - 1
        if(num < 0) {   // 적절성검사(2)
            break;      // 원하는 제품을 원하는 수량만큼만 구매해야함
        }
        
        shoppingList.put(discount[j], num); // 할인 제품을 구매 시 해당 value를 1 감소
    }
    
    // 모든 제품에 대한 value가 0이면 answer에 포함
    int remain = shoppingList.values().stream().mapToInt(i->i).sum();
    if(remain==0) {
        answer++;
    }
}

return answer;
```

## 코드분석

### 시간복잡도

* 입력값 : `discount` 원소의 수 `N`  
* 최악의 경우 : 모든 부분배열이 적절성검사를 통과하는 경우 `O(N)`
  * `discount` 순회시 `N-10`이고, `discount` 부분배열 순회시 10개의 원소를 순회하므로, `(N-10)*10 = 10N-100` 
  * 따라서 최악의 경우 시간복잡도는 `O(10N-100) = O(10N) = O(N)`이다.
* 최선의 경우 : 모든 부분배열이 적절성검사를 통과하지 못하는 경우 `O(N)`
  * 모든 `discount`를 순회하고, `discount` 부분배열 순회시 1번째 원소에서 통과하지 못하는 경우 `N-10`
  * 따라서 최선의 경우 시간복잡도는 `O(N-10) = O(N)`

### 리펙토링

#### 불필요한 객체 생성

* `new HashMap<>()`
  * 객체 생성 비용
    * 메모리 할당 및 초기화
    * 객체 생명주기에 따른 추가작업 (생성시 생성자 호출, 초기화시 필드 초기화, 소멸시 소멸자 호출 등)
    * 레퍼런스 카운트 관리
  * GC 비용
    * 객체는 `Heap`에 생성되며, `Heap`영역은 `GC` 대상이다.

* `Map.clear()`
  * 내부적으로 **반복문**을 사용해서 `key`와 `value`를 초기화하기때문에
  * `key`값의 수 `M`인 경우 시간복잡도는 `O(M)`이 된다.

```java
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    // ...
    public void clear() {
        ++this.modCount;
        Node[] tab;
        if ((tab = this.table) != null && this.size > 0) {
            this.size = 0;

            for (int i = 0; i < tab.length; ++i) {
                tab[i] = null;
            }
        }

    }
    //...
}
```

* 결론
  * `shoppingList`의 `key`값의 수는 `want.length`이고,
  위 제한조건에 따라 `1 ≦ want.length ≦ 10` 이므로`shoppingList.clear()`의 시간복잡도는 `O(1)`이다.
  * 반면 `new` 연산자를 사용하여 객체를 생성할 경우 `객체 생성 비용`과 `GC 비용`(물론 현재 상황에서 그 차이가 미미하지만)이 발생하고,
  * `discount`의 부분배열을 순회할때마다 매번 새로운 객체를 메모리에 할당해야하므로 공간복잡도가 `O(n)`이 된다.
  * 따라서 현재의 상황에서는 `Map.clear()`를 사용하는 것이 합당하다.

```java
Map<String, Integer> shoppingList = new HashMap<>();
for(i=0; i<=discount-10; i++) {
    shoppingList.putAll(map);
    //...
    shoppingList.clear();
```

`이펙티브 자바`에서는 객체의 생성에 관련해서 종합적으로 고려할 것을 얘기한다.
즉, 객체 생성을 무조건적으로 지양해서는 안된다는 것이다.
`item6-불필요한 객체 생성을 피하라`에서는 객체를 재사용한다면 새로운 객체를 생성하지 말라고 하지만
`item50-적시에 방어적 복사본을 만들라`에서는 새로운 객체를 생성해야한다면 객체를 재사용하지 말라고 한다.

오직 성능 향상만을 위해 객체 생성을 무조건적으로 지양하는 태도를 가져서는 안된다.
최근 `JVM`은 `GC`성능이 고도로 최적화 되어있어서
생성자에서 일을 거의 하지 않는 작은 객체의 생성과 재사용은 비용이 거의 들지 않는다.
따라서 프로그램의 명확성과 간결함을 위해서라면 객체를 추가로 생성하는 것도 고려해야한다.
또한 직접 객체 풀을 만드는 것은 코드 작성이 어렵고 메모리 할당과 해지가 늘어나며 오히려 성능이 저하될 수 있다.
`DB Connection`과 같이 특정한 경우에만 객체 풀을 유지하는 것이 도움이 된다.

마지막으로 방어복사가 필요한 경우에는 오히려 객체를 재사용하지 말라고 강조한다.
불필요한 객체를 생성하는 것은 코드 구성이나 성능에만 영향을 주지만,
방어복사에 실패하면 프로그램의 안정성을 해치고 보안에 치명적이기 때문이다.

#### Collection 객체와 static final

`want`와 `number`를 `Map` 형태로 저장하는 `map` 객체를 `static final`로 선언했다.

`static final`로 선언한 이유는 다음과 같다.
* shoppingList에서 map을 지속적으로 참조해야한다. 
* map 객체는 메서드가 종료될 때까지 변하지 않는다. 
* map 객체는 메서드가 종료될 때까지 계속 사용한다.

하지만 `Collection` 객체를 `static final`로 선언하는 경우 메모리 릭(`Memory Leak`)이 발생할 가능성이 생긴다.
`static`으로 선언된 객체는 `GC`에서 제외되기 때문에, 지속적으로 데이터가 쌓인다면 `OutOfMemoryError`가 발생하게 된다.

또한 `Collection` 객체를 `final`로 선언할 경우 객체의 원소가 불변이 되는 것이 아니라 
객체가 재할당이 안되는 것 뿐이기 때문에 `Collection` 내 원소들은 수정이 가능하다.

#### 리펙토링 코드
```java

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;

        Map<String, Integer> map = new HashMap<>(); // 수정
        for (int i = 0; i < want.length; i++) {
            map.put(want[i], number[i]);
        }

        Map<String, Integer> shoppingList = new HashMap<>(); // 수정
        for (int i = 0; i <= discount.length - 10; i++) {
            shoppingList.putAll(map);
            // ...
            shoppingList.clear(); // 재사용에 따른 객체 초기화
        }

        return answer;
    }
}
```

<hr>

## 참고자료
> [이펙티브 자바 3/E](https://product.kyobobook.co.kr/detail/S000001033066)  
> [아이템 6. 불필요한 객체 생성을 피하라](https://velog.io/@banjjoknim/%EC%95%84%EC%9D%B4%ED%85%9C-6.-%EB%B6%88%ED%95%84%EC%9A%94%ED%95%9C-%EA%B0%9D%EC%B2%B4-%EC%83%9D%EC%84%B1%EC%9D%84-%ED%94%BC%ED%95%98%EB%9D%BC)  
> [Effective Java - 객체 생성과 파괴(6) : 불필요한 객체 생성을 피하라](https://velog.io/@shinmj1207/Effective-Java-%EA%B0%9D%EC%B2%B4-%EC%83%9D%EC%84%B1%EA%B3%BC-%ED%8C%8C%EA%B4%B46)  
> [Better Kotlin — 객체 생성 비용 줄이기](https://jaehochoe.medium.com/betterkotlin-%ED%9A%A8%EC%9C%A8%EC%84%B1-f88e8b5f81b0)  
> [[BE/JAVA] HashMap clear() vs new HashMap<>()](https://limreus.tistory.com/86)  
> [clear vs new 성능비교](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=bestdriver94&logNo=220421196647)  
> [Map.clear() vs new Map : Which one will be better?](https://stackoverflow.com/questions/6757868/map-clear-vs-new-map-which-one-will-be-better)  

> [[자바성능] static의 올바른 사용](https://12bme.tistory.com/94)   
> [static에 대한 고찰. 퍼옴.](https://roynus.tistory.com/304)  