import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        TreeMap<String, Integer> cumulativeTime = new TreeMap<>();
        Map<String, Integer> parking = new HashMap<>();

        for (String record : records) {
            String[] row = record.split(" ");

            String[] time = row[0].split(":");
            int minute = getTimeInMinute(Integer.parseInt(time[0]), Integer.parseInt(time[1]));

            String id = row[1];

            if (row[2].equals("IN")) {
                parking.put(id, minute);
            } else { // "OUT"
                int duration = minute - parking.get(id);

                Integer previous = cumulativeTime.putIfAbsent(id, duration);
                if (previous != null) {
                    cumulativeTime.replace(id, previous + duration);
                }
                parking.remove(id);
            }
        }

        if (!parking.isEmpty()) {
            int max = getTimeInMinute(23, 59);

            for (String id : parking.keySet()) {
                int duration = max - parking.get(id);

                Integer previous = cumulativeTime.putIfAbsent(id, duration);
                if (previous != null) {
                    cumulativeTime.replace(id, previous + duration);
                }
            }
        }

        // fees = 기본 시간(분)	기본 요금(원)	단위 시간(분)	단위 요금(원)
        return cumulativeTime.values().stream().mapToInt((duration) -> {
            if (duration <= fees[0]) {
                return fees[1];
            } else {
                duration = duration - fees[0];
                return (int) Math.ceil(duration / (double) fees[2]) * fees[3] + fees[1];
            }
        }).toArray();
    }

    private int getTimeInMinute(int hour, int minute) {
        return hour * 60 + minute;
    }


    public static void main(String[] args) {
        int[] fees = {180, 5000, 10, 600};
        String[] records = {
                "05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT",
                "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN",
                "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"
        };

        Solution solution = new Solution();
        int[] solution1 = solution.solution(fees, records);
        for (int i : solution1) {
            System.out.println(i);
        }
    }
}