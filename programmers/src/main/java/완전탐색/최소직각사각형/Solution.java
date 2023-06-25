package 완전탐색.최소직각사각형;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int solution(int[][] sizes) {
        List<Card> list = new ArrayList<>();
        int maxWidth = Integer.MIN_VALUE;
        int maxHeight = Integer.MIN_VALUE;

        // 카드 배열을 생성자를 이용해 가로가 세로보다 길게 초기화
        for (int[] size : sizes) {
            list.add(new Card(size[0], size[1]));
        }

        // 가로와 세로의 가장 큰 값으로 지갑 크기 결정
        for (int i = 0; i < list.size(); i++) {
            Card card = list.get(i);
            maxWidth = Math.max(card.width, maxWidth);
            maxHeight = Math.max(card.height, maxHeight);
        }

        return maxWidth * maxHeight;
    }

    private class Card {
        private int width;
        private int height;

        // 카드는 가로가 긴 방향으로 돌림
        public Card(int width, int height) {
            this.width = Math.max(width, height);
            this.height = Math.min(width, height);
        }
    }

}
