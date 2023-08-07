import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        String[] arr1 = str.toUpperCase().split("");
        return IntStream.range(0, str.length() - 1)
                .mapToObj(i -> arr1[i] + arr1[i + 1])
                .filter(element -> element.replaceAll("[^A-Z]", "").length() == 2)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Map<String, Long> getUnionSection(Map<String, Long> map1, Map<String, Long> map2) {
        Map<String, Long> unionSection = new HashMap<>();

        Set<String> unionSet = new HashSet<>();
        unionSet.addAll(map1.keySet());
        unionSet.addAll(map2.keySet());

        unionSet.forEach((key) -> unionSection.put(key, Math.max(map1.getOrDefault(key, 0L), map2.getOrDefault(key, 0L))));
        return unionSection;
    }

    private Map<String, Long> getInterSection(Map<String, Long> map1, Map<String, Long> map2) {
        Map<String, Long> interSection = new HashMap<>();

        map1.keySet().forEach((key) -> {
            if (map2.containsKey(key)) {
                interSection.put(key, Math.min(map1.get(key), map2.get(key)));
            }
        });
        return interSection;
    }

    private double getJaccard(Map<String, Long> interSection, Map<String, Long> unionSection) {
        long interSize = interSection.values().stream().mapToLong(i -> i).sum();
        long unionSize = unionSection.values().stream().mapToLong(i -> i).sum();

        if (unionSize == 0) {
            return 1;
        }

        return interSize / (double) unionSize;
    }

    public static void main(String[] args) {
        String input = "E=M*C^2";
        String input2 = "e=m*c^2";
        Solution solution = new Solution();
        int solution1 = solution.solution(input, input2);
        System.out.println("result = " + solution1);
    }
}