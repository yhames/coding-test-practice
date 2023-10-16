# [백준 1620 - 나는야 포켓몬 마스터 이다솜](https://www.acmicpc.net/problem/1620)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)
- [코드분석](#코드분석)
    * [시간복잡도](#시간복잡도)
    * [리펙토링](#리펙토링)

## 문제분석

### 요구사항

도감에 `N`마리의 포켓몬이 저장되어있을 때 `M`개의 문제에 대한 답 출력

### 조건

입력값
* `N` : 도감에 저장된 포켓몬 수
* `M` : 문제의 개수
> 입력 예시
> ```shell
> 26 5      # 포켓몬의 수 N, 문제 개수 M
> Bulbasaur # 포켓몬 시작, 1번
> Ivysaur
> ...
> Pikachu
> Raichu    # 포켓몬 끝, 26번
> 25        # 문제 시작
> Raichu
> 3
> Pidgey
> Kakuna    # 문제 끝
> ```

출력
* 각 문제에 대한 정답
    * 문제가 번호인 경우 `이름` 출력
    * 문제가 이름인 경우 `번호` 출력
> 출력 예시
> ```shell
> Pikachu   # 25
> 26        # Raichu
> Venusaur  # 3
> 16        # Pidgey
> 14        # Kakuna
> ```

### 제한사항

> 시간 제한 : 2초

> 포켓몬의 이름은 모두 영어로만 이루어져있다.

## 접근방법

도감을 `key-value` 형식의 `map`에 저장하여  
문제를 받으면 정답을 참조하여 출력

## 의사코드

```C
int main() {
    int n, name;
    int m, question;
    map<int, string> map;

    cin >> n >> m;
    for (1 번부터 n 번까지) {
        cin >> name;
        map.push(pair<int, string>(id, name));
    }

    for (m번 반복) {
        cin >> question;
        if (question이 숫자이면)
            cout << map.find(question) << endl;
        else if (question이 문자이면) {
            for(map을 순회) {
                if (포켓몬 이름 발견하면)
                    cout << iter.second << endl;
            }
        }
    }
}
```

## 코드분석

### 시간복잡도

문제가 `포켓몬 이름`인 경우 `map`을 순회하면서 찾기때문에
탐색 시간은 최악의 경우 시간복잡도는 `O(N)`이 된다.

따라서 시간복잡도를 줄이기 위해 `포켓몬 이름`을 `key`로 저장하는
`map`을 추가로 생성하여 시간복잡도를 줄일 수 있다.

최종적으로 탐색 시간복잡도는 `O(1)`이 된다.

### 리펙토링

```C
int main() {
    int n, name;
    int m, question;
    map<int, string> map;
    map<string, int> map_reverse;

    cin >> n >> m;
    for (1 번부터 n 번까지) {
        cin >> name;
        map.emplace(id, name);
        map_reverse.emplace(name, id);  // 추가
    }

    for (m번 반복) {
        cin >> question;
        if (question이 숫자이면)
            cout << map.find(question) << endl;
        else if (question이 문자이면)
            cout << map_reverse.find(question) << endl;
    }
}
```