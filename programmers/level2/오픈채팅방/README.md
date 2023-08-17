# [프로그래머스 코딩테스트 연습 level2 - 오픈채팅방](https://school.programmers.co.kr/learn/courses/30/lessons/42888)

- [문제분석](#문제분석)
    * [요구사항](#요구사항)
    * [조건](#조건)
    * [제한사항](#제한사항)
- [접근방법](#접근방법)
- [의사코드](#의사코드)

## 문제분석

### 요구사항

> 모든 기록이 처리된 후, 최종적으로 방을 개설한 사람이 보게 되는 메시지를 문자열 배열

**⭐️ 중요 : 사람들이 들어오고 나가는 기록**

* 들어온 순서로 기록을 반환하는 `Queue` 활용

### 조건

> 채팅방에서 닉네임을 변경하는 방법은 다음과 같이 두 가지
> * 채팅방을 나간 후, 새로운 닉네임으로 다시 들어간다.
> * 채팅방에서 닉네임을 변경한다.

* `record[0]` = `Enter`, `Leave`, `Change`

> 닉네임을 변경할 때는 기존에 채팅방에 출력되어 있던 메시지의 닉네임도 전부 변경된다.

> 중복 닉네임을 허용

* `key` = `record[1]` = `uid`, `value` = `nickname` 형태의 `Map` 사용

### 제한사항

> 1 ≤ `record`의 길이 ≤ 100,000

> `record`는 공백으로 구분되는 [구분 유저아이디 닉네임] 순서의 문자열

> 채팅방 입장 : `Enter`
> 채팅방 퇴장 : `Leave`
> 닉네임 변경 : `Change`

> 알파벳 대문자, 소문자, 숫자로만 이루어져있다.
> 유저 아이디와 닉네임은 알파벳 대문자, 소문자를 구별한다.

> 1 ≤ `uid`, `nickname`의 길이 ≤ 10

## 접근방법

입력되는 모든 record의 Enter, Leave에 대한 기록을 순서와 함께 담을 수 있는 Log 클래스를 생성한다.
uid-nickname을 key-value 형태로 저장하는 User 객체를 생성한다.
Logger 클래스를 생성하여 Log를 TreeMap으로 관리하는 logs를 생성하고
User를 HashMap으로 관리하는 users를 생성한다.

Enter시 users에 uid가 없으면 uid-nickname을 추가하고, logs에 기록한다.
Enter시 users에 uid가 있으면 nickname을 비교하고 다를경우 nickname을 변경하고, logs에 기록한다.

Change시 users의 uid를 조회하여 nickname을 변경한다.

Leave시 logs에 기록한다.

## 의사코드

```java
class Solution {
    public String[] solution(String[] record) {
        Logger logger = new Logger();
        for (String log : record) {
            logger.add(log);
        }

        return logger.toArray();
    }
}
```