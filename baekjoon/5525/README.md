# [백준 5525 - IOIOI](https://www.acmicpc.net/problem/26043)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)
      * [규칙성 찾기](#규칙성-찾기)
      * [리펙토링 코드](#리펙토링-코드)


## 문제분석

### 요구사항

`'I'`와 `'O'`로만 이루어진 문자열 `S`와 정수 `N`이 주어졌을 때,
`S`안에 `P_n`이 몇 군데 포함되어 있는지 구하는 프로그램을 작성하시오.


`N+1`개의 `'I'`와 `N`개의 `'O'`로 이루어져 있으면서
`I`와 `O`이 교대로 나오는 문자열을 PN이라고 한다.
* `P_1 IOI`
* `P_2 IOIOI`
* `P_3 IOIOIOI`
* `P_N IOIOI...OI (O가 N개)`

### 조건

> 입력 예시
> ```shell
> 1
> 13
> OOIOIOIOIIOII
> ```

### 제한사항

* 1 ≤ `N` ≤ 1,000,000
* `2N+1` ≤ `M` ≤ 1,000,000
* 문자열 `S` 는 `'I'`와 `'O'`로만 이루어져 있다.

## 접근방법

문자열 `S`를 `0`번째부터 `s.length() - P_n.length()`까지 순회하여  
`P_n`이 만족하는 문자열이 몇 개가 포함되어 있는지 검사한다.

## 의사코드
```C++
int main() {
	while (S의 길이 - P_n의 길이 까지 반복) {
		while (해당 인덱스가 'O'인 경우)
			검사하지 않고 인덱스를 다음으로 넘긴다;
		for (처음 I 이후에 OI가 나타나는 동안 반복) {
			if (OI가 아니면)
				반복 종료
			flag++; // OI의 개수
		}
		if (OI의 개수가 n과 같으면)
			answer++;
	}
	cout << answer << '\n';
	return (0);
}
```

## 코드분석

### 시간복잡도

* 입력값 : `N`(`S`의 길이), `M`(`P_n`의 길이)
* 연산 : 문자 비교 연산
* 시간복잡도 : `O(NM)`
  * 문자열 `S`를 전체 순회하므로 `N` 만큼 비교하고
  * 문자열 `S`를 순회하는 인덱스 `i`에 따라 부분문자열 `M` 만큼 비교하므로
  * 시간복잡도는 `O(N*M)`이 된다.

### 리펙토링

시간복잡도를 줄일 수 있는 방법이 떠오르지 않아 처음에는 `KMP 알고리즘` 등 문자열 탐색 알고리즘으로 접근을 해보았지만  
여전히 문제에서 요구하는 시간복잡도를 만족할 수 없었다.

질문게시판을 확인해보니 `O(N)`까지 줄일 수 있다고 한다.  
이는 부분 문자열 `P_n`을 검사하지 않는다는 의미이기 때문에  
부분 문자열을 검사하지 않고 `"OI"`의 개수를 사용하여
부분 문자열의 개수를 도출하는 방식으로 구현했다.

#### 규칙성 찾기

`n`이 `1`일 때 `S`에 `OI`가 `3`개 있으면 `P_n`은 `3`개이고,
`n`이 `2`일 때 `S`에 `OI`가 `3`개 있으면 `P_n`은 `2`개이다.
> n = 1인 경우 : `OI = 3` → `answer = 3`  
> n = 2인 경우 : `OI = 3` → `answer = 2`  
> n = 3인 경우 : `OI = 3` → `answer = 1`  

이를 일반화 하면 `answer = OI의 개수 - (n - 1)`이 된다.

일반화한 식을 사용하면 부분 문자열을 검사하지 않더라도,  
`OI`의 개수만으로 정답을 도출하기때문에 최종 시간복잡도는 `O(N)`이 된다.

#### 리펙토링 코드

```c
int main() {
	int n, m, i, j, flag, answer;
	string s;

	cin >> n >> m >> s;
	for (i = 0, answer = 0; i < m; i = j) {
		while (s[i] == 'O')     // 'O'인 경우 인덱스 다음으로
			i++;
		for (j = i + 1, flag = 0; j < m; j += 2) {
			if (s[j] != 'O' || s[j + 1] != 'I')
				break;
			flag++;     // "OI"의 개수
		}
		if (flag >= n)
			answer += flag - (n - 1);   // 일반화한 계산식으로 정답 계산
	}
	cout << answer << '\n';
	return (0);
}
```
