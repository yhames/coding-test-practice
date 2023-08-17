class Solution {
    public String[] solution(String[] record) {
        Logger logger = new Logger();

        for (String log : record) {
            logger.add(log);
        }

        return logger.toArray();
    }

    public static void main(String[] args) {
        String[] record = {
                "Enter uid1234 Muzi",
                "Enter uid4567 Prodo",
                "Leave uid1234",
                "Enter uid1234 Prodo",
                "Change uid4567 Ryan"
        };
        Solution solution = new Solution();
        String[] results = solution.solution(record);
        for (String result : results) {
            System.out.println(result);
        }
    }
}