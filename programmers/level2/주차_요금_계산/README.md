# [프로그래머스 코딩테스트 연습 level2 - 주차 요금 계산](https://school.programmers.co.kr/learn/courses/30/lessons/92341)

- [프로그래머스 코딩테스트 연습 level2 - 주차 요금 계산](#프로그래머스-코딩테스트-연습-level2---주차-요금-계산)
  - [문제분석](#문제분석)
    - [요구사항](#요구사항)
    - [조건](#조건)
    - [제한사항](#제한사항)
  - [접근방법](#접근방법)
  - [의사코드](#의사코드)
  - [코드분석](#코드분석)
    - [시간복잡도](#시간복잡도)
    - [리펙토링](#리펙토링)
      - [ConcurrentModificationException](#concurrentmodificationexception)
      - [for-each loop](#for-each-loop)
      - [HashMap.remove(Object key)](#hashmapremoveobject-key)
      - [해결 1 : for loop](#해결-1--for-loop)
      - [해결 2 : iterator.remove()](#해결-2--iteratorremove)
      - [해결 3 : ConcurrentHashMap](#해결-3--concurrenthashmap)
      - [리펙토링 코드](#리펙토링-코드)
  - [참고자료](#참고자료)

## 문제분석

### 요구사항

> 차량 번호가 작은 자동차부터 청구할 주차 요금을 차례대로 담은 정수 배열

**⭐️ 중요 : 배열의 원소는 주차요금, 정렬은 차량번호가 작은 자동차 순서**

### 조건

> 00:00부터 23:59까지의 입/출차 내역을 바탕으로 차량별 누적 주차 시간을 계산하여 요금을 일괄로 정산

> 누적 주차 시간이 `기본 시간`이하라면, `기본 요금`을 청구

> 누적 주차 시간이 `기본 시간`을 초과하면,  
> `기본 요금`에 더해서, 초과한 시간에 대해서 `단위 시간` 마다 `단위 요금`을 청구

> 초과한 시간이 단위 시간으로 나누어 떨어지지 않으면, `올림`

> 어떤 차량이 입차된 후에 출차된 내역이 없다면, 23:59에 출차된 것으로 간주한다.

> 매개변수
> * `fees` : 주차 요금을 나타내는 정수 배열
> * `records` : 자동차의 입/출차 내역을 나타내는 문자열 배열

### 제한사항

> `fees`의 길이 = 4
> * `fees[0]` = 기본 시간(분), 1 ≤ `fees[0]` ≤ 1,439
> * `fees[1]` = 기본 요금(원), 0 ≤ `fees[1]` ≤ 100,000
> * `fees[2]` = 단위 시간(분), 1 ≤ `fees[2]` ≤ 1,439
> * `fees[3]` = 단위 요금(원), 1 ≤ `fees[3]` ≤ 10,000

> `records`의 각 원소는 "`시각` `차량번호` `내역`" 형식의 문자열
> 1 ≤ `records`의 길이 ≤ 1,000

> `시각`, `차량번호`, `내역`은 하나의 공백으로 구분되어 있습니다  
> `시각`은 차량이 입차되거나 출차된 시각을 나타내며, `HH:MM` 형식의 길이 5인 문자열입니다.
> `HH:MM`은 00:00부터 23:59까지 주어집니다.

> `차량번호`는 자동차를 구분하기 위한, `0`~`9`로 구성된 길이 4인 문자열입니다.

> `내역`은 길이 2 또는 3인 문자열로, `IN` 또는 `OUT`입니다. `IN`은 입차를, `OUT`은 출차를 의미합니다.

> `records`의 원소들은 시각을 기준으로 오름차순으로 정렬되어 주어집니다.

> `records`는 하루 동안의 입/출차된 기록만 담고 있으며, 입차된 차량이 다음날 출차되는 경우는 입력으로 주어지지 않습니다.  
> 잘못된 `시각`("25:22", "09:65" 등)은 입력으로 주어지지 않습니다.  
> 같은 시각에, 같은 차량번호의 내역이 2번 이상 나타내지 않습니다.  
> 마지막 시각(23:59)에 입차되는 경우는 입력으로 주어지지 않습니다.    
> 주차장에 없는 차량이 출차되는 경우는 입력으로 주어지지 않습니다.  
> 주차장에 이미 있는 차량(차량번호가 같은 차량)이 다시 입차되는 경우는 입력으로 주어지지 않습니다.

## 접근방법

차량번호가 작은 순서로 정렬해야하므로 `TreeMap<String, Integer> cumulativeTime`을 활용한다.

시간은 분단위로 요금을 부과하므로 `HH:MM` 형식의 시간을 분단위로 변환하여 저장한다.

차량이 들어온 내역을 임시로 저장하는 `Map<String, Integer> parking`을 사용하여,
출차시 주차시간을 `cumulativeTime`에 누적한다.

모든 `records`를 순회하여 누적시간을 계산하고,
`parking`에 차량이 남아있는 경우 출차시간을 23:59으로 계산하여 `cumulativeTime`에 누적한다.

`cumulativeTime`의 누적시간을 요금표를 기준으로 환산하여 정수형 배열로 반환한다.

## 의사코드

```java
class Solution {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};
        TreeMap<String, Integer> cumulativeTime = new TreeMap<>(); // id, cumulativeTime
        Map<String, Integer> parking = new HashMap<>(); // id, arrivalTime(MM)

        for (String record : records) { // 모든 `records`를 순회
            if (입차) {
                parking에_arrival을_분단위로_저장;
            } else if (출차) {
                duration = departure - arrival;

                if (cumulativeTime에_원소가_있는_경우) {
                    누적시간에_duration을_더한_값을_저장;
                } else if (cumulativeTime에_원소가_없는는_경우) {
                    duration을_저장;
                    parking에서_차량_삭제;
                }
            }
        }

        if (pakring에_차랑이_있으면) {
            maxDeparture = 23 * 60 + 59;    // 출차시간을 23:59으로 간주

            for (String id : parking.keySet()) {
                maxDuration = maxDeparture - parking.get(id);

                if (cumulativeTime에_원소가_있는_경우) {
                    누적시간에_duration을_더한_값을_저장;
                } else if (cumulativeTime에_원소가_없는는_경우) {
                    duration을_저장;
                    parking에서_차량_삭제;
                }
            }
        }

        return cumulativeTime을_요금표기준으로_환산하여_배열로_반환;
    }
}
```

## 코드분석

### 시간복잡도

### 리펙토링

로직이 너무 복잡하고 가독성이 떨어진다.
주차장과 자동차 클래스로 분리하고 각 클래스에 맞는 기능을 추가했다.

하지만 테스트케이스 `1`, `3`~`12`번에서 런타임 오류가 발생했다.

#### ConcurrentModificationException

테스트케이스 `1`, `3`~`12`번에서 런타임 오류에 대해
확인결과 `ConcurrentModificationException`이 발생했다는 것을 알 수 있었다.

다음은 예외 발생한 코드이다.

```java
private class Parking {
    private final TreeMap<String, Car> cars = new TreeMap<>();
    private final Map<String, Integer> lots = new HashMap<>();

    // ...

    public int[] getParkingFeeArray(int[] fees) {
        if (!lots.isEmpty()) {
            for (String id : lots.keySet()) {   // ConcurrentModificationException
                departure(id, DEFAULT_DEPARTURE_TIME);
            }
        }

        //...
    }

    private void departure(String id, int departTime) {
        //...
        lots.remove(id);
    }
}
```

차량이 출차하지 않았을 경우 `ParkingLots`에 남아있는 차량에 대해
`23:59`에 출차한 것으로 간주하여 주차요금을 구하는 코드이다.

주차 중인 차량을 담는 `lots`는 `HashMap`을 사용했고,
`keySet()`을 사용하여 `for-each` 반분문을 통해 `ParkingLots`에 남아있는 차량을 순회하여,
`departure()` 메서드를 통해 `lots`에서 삭제한다.

#### for-each loop

예외에 대해 알아보기 전에 먼저 `for-each` 반복문에 대해 알아야한다.

다음은 공식문서의 `iterable` 인터페이스에 대한 설명이다.
> Implementing this interface allows an object to be the target of the <U>"for-each loop" statement</U>.

`for-each` 반복문을 사용하기 위해서는 `iterable` 인터페이스를 구현해야한다.  
참고로 `Collection` 인터페이스는 `iterable`을 구현하고 있기 때문에
대부분의 자료구조(`List`, `Set` 등)는 `iterable`을 구현하고 있다.

`iterable` 인터페이스는 `iterator` 인터페이스를 추상메서드로 선언한다.

```java
public interface Iterable<T> {

    Iterator<T> iterator();

    // ...
}
```

또한 `iterator`는 다음과 같은 메서드를 선언하고 있다.

```java
public interface Iterator<E> {
    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);

        while (this.hasNext()) {
            action.accept(this.next());
        }

    }
}
```

`Iterator`의 기본적인 사용방법은
`hasNext()` 메서드를 통해 남아있는 원소가 있는지 확인하고,
남아있는 원소가 있으면 `next()` 메서드를 통해 원소를 가져오는 것이다.

다음은 [For-Each Loop에 대한 자바 공식문서](https://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html)의 예시이다.

```java
class Example {
    void cancelAll(Collection<TimerTask> c) {
        for (Iterator<TimerTask> i = c.iterator(); i.hasNext(); )
            i.next().cancel();
    }
}
```

위 코드는 for-each 반복문을 통해 다음과 같이 리펙토링될 수 있다.

```java
class Example {
    void cancelAll(Collection<TimerTask> c) {
        for (TimerTask t : c)
            t.cancel();
    }
}
```

`for-each` 반복문은 원소를 순회하기 위해서
내부적으로 `iterator`의 `next()` 메서드를 사용하여 순회한다.

> ...
> As you can see, the for-each construct combines beautifully with generics.
> It preserves all of the type safety, while removing the remaining clutter.
> Because you don't have to declare the iterator, you don't have to provide a generic declaration for it.
> (<U>**The compiler does this for you behind your back**</U>, but you need not concern yourself with it.)

`for-each` 반복문은 `iterator`을 내부로 숨기기때문에 코드를 가독성있게 만들어준다.
하지만 공식문서의 말미에 다음과 같은 주의사항을 언급한다.

> ...
> Unfortunately, you cannot use it everywhere. Consider, for example, the expurgate method.
> The program needs access to the iterator in order to remove the current element.
> The <U>**for-each loop hides the iterator**</U>, so <U>**you cannot call remove**</U>.

`for-each` 반복문을 사용할 때 순회하는 원소를 삭제하면 안된다.
그 이유는 `for-each` 반복문이 내부적으로 `iterator`를 사용하기 때문이다.

#### HashMap.remove(Object key)

`for-each` 반복문을 사용할 때 원소를 삭제하면 안된다는 것에 대해 알아보기 전에
`HashMap`의 `remove()`와 `modCount`에 대해 알아야한다.

다음은 `HashMap`의 `remove()`와 `removeNode()` 메서드 이다.

```java
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    public V remove(Object key) {
        Node e;
        return (e = this.removeNode(hash(key), key, (Object) null, false, true)) == null ? null : e.value;
    }

    final Node<K, V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable) {
        Node[] tab;
        Node p;
        int n;
        int index;
        if ((tab = this.table) != null && (n = tab.length) > 0 && (p = tab[index = n - 1 & hash]) != null) {
            // ...
            if (node != null && (!matchValue || (v = ((Node) node).value) == value || value != null && value.equals(v))) {
                // ... 
                ++this.modCount;    // here!
                // ...
                return (Node) node;
            }
        }

        return null;
    }
}
```

실질적으로 삭제 연산을 수행하고 있는 `removeNode()`를 보면
해당 노드 삭제 후에 `modCount`를 증가시키는 것을 볼 수 있다.

다음은 공식문서의 [AbstractList의 modCount](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html#field.detail)에
대한 내용이다.
`HashMap`의 `Field에` 관한 내용을 찾지 못해서 `AbstractList`를 참고했다.

위 내용에 의하면 `삽입` 혹은 `삭제` 연산이 수행될 때마다 `modCount`는 증가되고,
`Iterator`를 통한 반복 중에 `modCount`를 비교하여 값이 변경되면
`ConcurrentModificationException`이 발생힌다.

다음은 `KeyIterator`의 `next()` 메서드이다.

```java
final class KeyIterator extends HashMap<K, V>.HashIterator implements Iterator<K> {
    KeyIterator() {
        super();
    }

    public final K next() {
        return this.nextNode().key;
    }
}

abstract class HashIterator {
    //...
    final Node<K, V> nextNode() {
        Node<K, V> e = this.next;
        if (HashMap.this.modCount != this.expectedModCount) {   // here!
            throw new ConcurrentModificationException();
        } else if (e == null) {
            throw new NoSuchElementException();
        } else {
            Node[] t;
            if ((this.next = (this.current = e).next) == null && (t = HashMap.this.table) != null) {
                while (this.index < t.length && (this.next = t[this.index++]) == null) {
                }
            }

            return e;
        }
    }
}
```

`KeyIterator`에서 `next()`를 호출하면 부모인 `HashIterator`의 `nextNode()`를 호출한다.
그리고 `nextNode()`에서 `expectedModCount`와 `modCount`를 비교하여 `ConcurrentModificationException`을 던진다.

결론적으로 `Iterator`를 통한 반복에서 원소를 추가하거나 삭제한다면
다음 원소를 반환하는 `HashIterator.nextNode()`에서 `ConcurrentModificationException`가 발생하는 것이다.

이를 해결하기 위해서 다음과 같은 방법을 사용할 수 있다.

#### 해결 1 : for loop

가장 쉬운 방법은 `Iterator`를 사용하지 않는 것이다.
`keySet`을 `Array`로 받아 일반적인 `for loop`를 사용하면
`ConcurrentModificationException`가 발생하지 않는다.

```java
private class Parking {
    public int[] getParkingFeeArray(int[] fees) {
        Object[] objects = lots.keySet().stream().toArray();
        for (Object id : objects) {
            departure((String) id, DEFAULT_DEPARTURE_TIME);
        }
        //...
    }
}
```

하지만 이렇게 사용하면 의도치 않은 수정이 발생했을 때,
혹은 `ConcurrentModificationException`이 필요할 때조차
발생하지 않게 된다는 단점이 있다.

이는 오류가 발생할 수 있는 코드이므로 지양하는게 좋아보인다.

#### 해결 2 : iterator.remove()

두번째 방법은 `Iterator`의 `remove()`를 `Override`한 메서드를 사용하는 방법이다.

다음은 공식 문서의 `HashMap`에 대한 설명이다.

> The iterators returned by all of this class's "collection view methods" are fail-fast:
> if the map is structurally modified at any time after the iterator is created,
> <U>**in any way except through the iterator's own remove method**</U>,
> the iterator will throw a ConcurrentModificationException.

또한 [Collection](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html)에
대한 공식 튜토리얼 문서에서 Iterator에 대해 다음과 같이 설명한다.

> Note that <U>**`Iterator.remove` is the only safe way to modify a collection during iteration**</U>;
> the behavior is unspecified if the underlying collection is modified in any other way
> while the iteration is in progress.

즉, `Iterator`는 `iterator` 내부에서 변경하는 것 외에
외부에서 변경되는 모든 상황에 대해 `ConcurrentModificationException`을 던진다.

따라서 `for-each loop`에서 원소를 삭제하기 위해서는 반드시 `Iterator.remove()` 메서드를 사용해야한다.

다음은 `HashMap`안에 있는 `HashIterator` 클래스의 `remove()` 메서드 이다.

```java
abstract class HashIterator {
    public final void remove() {
        Node<K, V> p = this.current;
        if (p == null) {
            throw new IllegalStateException();
        } else if (HashMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
        } else {
            this.current = null;
            HashMap.this.removeNode(p.hash, p.key, (Object) null, false, false);
            this.expectedModCount = HashMap.this.modCount;  // here!
        }
    }
}
```

`HashIterator.remove()` 메서드는 [`HashMap.remove(Object key)`](#hashmapremoveobject-key)와 동일하게
내부적으로 `HashMap.removeNode()`를 사용한다.

하지만 `HashIterator.remove()`는 호출될 때마다 `변경된 modCount`를 `expectedModCount`로 갱신한다.

즉, 삭제 연산이 발생해도 다음 `next()` 메서드 호출시 `ConcurrentModificationException`가 발생하지 않는 것이다.

하지만 문제의 코드는 `departure()` 메서드를 호출하고
`departure()` 메서드 안에서 원소를 삭제한다.
따라서 `HashIterator.remove()`를 사용하면
`departure()` 메서드를 활용하지 못하게 된다.

#### 해결 3 : ConcurrentHashMap

`departure()` 메서드를 활용하기 위해서는 `ConcurrentHashMap`을 사용해야한다.

`ConcurrentHashMap`은 `modCount`를 사용하지 않는 대신 `synchronized()`를 사용하여 동시성을 보장한다.

```java
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
    public V remove(Object key) {
        return this.replaceNode(key, (Object) null, (Object) null);
    }

    final V replaceNode(Object key, V value, Object cv) {
        int hash = spread(key.hashCode());
        Node<K, V>[] tab = this.table;

        Node f;
        int n;
        int i;
        while (tab != null && (n = tab.length) != 0 && (f = tabAt(tab, i = n - 1 & hash)) != null) {
            int fh;
            if ((fh = f.hash) == -1) {
                tab = this.helpTransfer(tab, f);
            } else {
                V oldVal = null;
                boolean validated = false;
                synchronized (f) {  // here!
                    // ...
                }

                if (validated) {
                    if (oldVal != null) {
                        if (value == null) {
                            this.addCount(-1L, -1);
                        }

                        return oldVal;
                    }
                    break;
                }
            }
        }

        return null;
    }
}
```

`ConcurrentHashMap`은 다른 쓰레드에서 접근하지 못하도록
`synchronized()`를 사용하여 락을 잡기 때문에 `thread-safe`하다.

또한 `modCount`가 없고
`KeyIterator.next()` 메서드에서 `ConcurrentModificationException`을 던지지도 않기 때문에
외부에서 `HashMap.remove(Object key)`를 호출해도 모든 원소를 순회하게 되는 것이다.

```java
static final class KeyIterator<K, V> extends BaseIterator<K, V> implements Iterator<K>, Enumeration<K> {
    KeyIterator(Node<K, V>[] tab, int size, int index, int limit, ConcurrentHashMap<K, V> map) {
        super(tab, size, index, limit, map);
    }

    public final K next() { // here!
        Node p;
        if ((p = this.next) == null) {
            throw new NoSuchElementException();
        } else {
            K k = p.key;
            this.lastReturned = p;
            this.advance();
            return k;
        }
    }

    public final K nextElement() {
        return this.next();
    }
}
```

추가로 학슴해야할 내용은 다음과 같다.
* `ConcurrentHashMap`의 동작방식(`Compare and Swap`)
* `Fail Fast`와 `Weakly Consistent`
* `Fail Safe`와 `Weakly Consistent`

#### 리펙토링 코드

```java
private class Parking {
    
    private final Map<String, Integer> lots = new ConcurrentHashMap<>();
    
    public int[] getParkingFeeArray(int[] fees) {
        if (!lots.isEmpty()) {
            for (String id : lots.keySet()) {
                departure(id, DEFAULT_DEPARTURE_TIME);
            }
        }

        return cars.values().stream()
                .mapToInt(car -> car.getParkingFee(fees))
                .toArray();
    }
}
```

<hr>

## 참고자료

> [ConcurrentModificationException와 iterator : 탐색 도중 리스트가 변경되는 경우](https://keykat7.blogspot.com/2022/02/java-concurrentmodificationexception.html)    
> [ConcurrentHashMap은 어떻게 동시성을 보장할까](https://ggam-nyang.tistory.com/72)  
> [fail-fast vs weakly consistent iterator](https://june0122.tistory.com/5)  
> [ConcurrentHashMap 동기화 방식](https://pplenty.tistory.com/17)  
> [Java HashMap은 어떻게 동작하는가?](https://d2.naver.com/helloworld/831311)
> [JavaSE8 - Interface Iterable\<T\>](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html)  
> [JavaSE8 - The For-Each Loop](https://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html)  
> [ModCount in map and list](https://stackoverflow.com/questions/11833058/modcount-in-map-and-list)    
> [JavaSE8 - Class AbstractList\<E\>](https://docs.oracle.com/javase/8/docs/api/java/util/AbstractList.html#field.detail)  
> [JavaTutorial - Interface Collection](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html)  
> [Understanding concurrentModificationException and implementation of ArrayList](https://stackoverflow.com/questions/34629478/understanding-concurrentmodificationexception-and-implementation-of-arraylist)  