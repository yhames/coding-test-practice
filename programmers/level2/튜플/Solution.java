import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Solution {
    public int[] solution(String s) {
        Map<String, Long> map = Arrays.stream(s.split("[^0-9]"))
                .filter(str -> !str.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return map.keySet().stream()
                .sorted(Comparator.comparing(map::get).reversed())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static void main(String[] args) {
        String input = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        Solution solution = new Solution();
        int[] solution1 = solution.solution(input);
        Arrays.stream(solution1).forEach(System.out::println);
    }
}