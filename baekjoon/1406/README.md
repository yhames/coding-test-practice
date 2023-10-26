# [백준 1406 - 에디터](https://www.acmicpc.net/problem/1406)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [iterator 순회 중 리스트 추가/삭제](#iterator-순회-중-리스트-추가삭제)
    * [리펙토링](#리펙토링)
      * [for_each()](#foreach)

## 문제분석

⭐ 모든 명령어를 수행하고 난 후 편집기에 입력되어 있는 `문자열을 출력`한다.

### 요구사항

한 줄로 된 간단한 에디터를 구현한다.
* `L` : 커서를 왼쪽으로 한 칸 옮김 (커서가 문장의 맨 앞이면 무시됨)
* `D` : 커서를 오른쪽으로 한 칸 옮김 (커서가 문장의 맨 뒤이면 무시됨)
* `B` : 커서 왼쪽에 있는 문자를 삭제함 (커서가 문장의 맨 앞이면 무시됨), 오른쪽에 있던 문자는 그대로임
* `P $` : `$`라는 문자를 커서 왼쪽에 추가함

명령어가 수행되기 전에 커서는 문장의 맨 뒤에 위치한다.

### 조건

* 입력
  * 초기 문자열 N, `1 ≤ N ≤ 100,000`
  * 명령어 개수 M, `1 ≤ M ≤ 500,000`

```shell
abcd  # 초기 문자열 N
3     # 명령어 개수 M
P x
L
P y
```

* 출력 예시
```shell
abcd|   # 초기 문자열, '|'는 커서를 의미
abcdx|  # P x 수행
abcd|x  # L 수행
abcdy|x # P y 수행
```
```shell
abcdyx  # 최종 결과물
```

### 제한사항

시간제한 : `0.3초`

## 접근방법

연결리스트를 사용하여 커서가 이동할 때마다(`L`, `D`) 노드를 이동한다.  
삭제 명령(`B`)시 이전 노드를 삭제하고,
입력 명령(`P`)시 현재 노드의 앞에 추가한다.

## 의사코드

```C++
int main() {
	cursor = editor.end();
	for (int i = 0; i < m; i++) {
		cmd = 명령어를 입력받는다.
		switch (cmd) {
			case 'L':
				if (커서가 맨 앞 노드가 아니면)
					이전 노드로 이동
			case 'D':
				if (커서가 마지막 노드가 아니면)
					다음 노드로 이동
			case 'B':
				if (커서가 맨 앞이 아니면)
					이전 노드를 삭제한다.
			case 'P':
				x = $를 입력받는다.
				현재 노드 앞에 추가한다.		
		}
	}
	장답 문자열을 출력한다.
}
```

## 코드분석

### iterator 순회 중 리스트 추가/삭제

구조가 간단하여 코드 자체는 어렵지 않았지만 `iterator`를 다루는 부분에서 많이 헷갈렸다.  
가장 많이 헷갈렸던 부분은 다음과 같다.

`iterator` 순회 중 리스트 추가/삭제를 하려면 `insert()`와 `erase()`함수를 사용해야한다.

`insert()`와 `erase()`를 사용하면, 기존 `iterator`는 변경된 리스트를 추적할 수 없다.

따라서 `insert()`와 `erase()`의 반환값을 재할당하여 순회를 진행해야한다.

`insert()`는 <U>**현재 노드의 앞에 삽입**</U>되고, <U>**삽입된 노드**</U>를 가리키는 `iterator`를 반환한다.

`erase()`는 <U>**현재 노드를 삭제**</U>하고, 삭제된 노드의 <U>**다음 노드**</U>를 가리키는 `iterator`를 반환한다.  
또한 `erase()`함수에 `list.end()`를 인자로 넣지 않도록 주의해야한다.


#### 동작방식

[PDF 참고](URL)

### 리펙토링

#### for_each()

리스트의 `char` 원소들을 `string`으로 변환하는 것은 간단한 작업이기 때문에  
`for_each()`와 `람다` 함수를 사용하여 간결하게 작성할 수 있다.

`for_each()`함수는 범위 내 원소에 각각에 대해 함수를 실행한다.

* 헤더파일 : `algorithm`
* 정의 : `for_each(iterator first, iterator last, Function fn)`
```c++
template <class InputIterator, class Function>
Function for_each(InputIterator first, InputIterator last, Function fn);
```

`for_each()`에 대한 자세한 내용은 [모두의 코드 C++](https://modoocode.com/260)를 참고했다.

#### 리펙토링 코드

```c++
#include <algorithm>

int main() {
    // ...
	for_each(editor.begin(), editor.end(),
			 [&answer](char c) { answer.push_back(c); });
	// ...
}
```