import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

class SolutionObject {
    public int[] solution(int[] fees, String[] records) {
        Parking parking = new Parking(fees);
        for (String record : records) {
            parking.add(record);
        }

        return parking.getParkingFeeArray();
    }

    private class Parking {

        private static final int DEFAULT_DEPARTURE_TIME = 23 * 60 + 59;

        private final int[] defaultFees;

        private final TreeMap<String, Car> cars = new TreeMap<>();

        private final Map<String, Integer> lots = new ConcurrentHashMap<>();

        public Parking(int[] fees) {
            this.defaultFees = fees;
        }

        public int[] getParkingFeeArray() {
            return getParkingFeeArray(this.defaultFees);
        }

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

        public void add(String record) {
            String[] row = record.split(" ");

            String[] time = row[0].split(":");
            int minutes = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);

            String id = row[1];

            if (row[2].equals("IN")) {
                arrival(id, minutes);
            } else {    // "OUT"
                departure(id, minutes);
            }
        }

        private void arrival(String id, int minutes) {
            lots.put(id, minutes);
        }

        private void departure(String id, int departTime) {
            Integer arrivalTime = lots.get(id);

            int duration = departTime - arrivalTime;
            if (cars.containsKey(id)) {
                cars.get(id).cumulate(duration);
            } else {
                cars.put(id, new Car(id, duration));
            }

            lots.remove(id);
        }
    }

    private class Car implements Comparable<String> {

        private final String id;

        private int cumulativeTime;

        public Car(String id, int cumulativeTime) {
            this.id = id;
            this.cumulativeTime = cumulativeTime;
        }

        public void cumulate(int duration) {
            this.cumulativeTime += duration;
        }

        public int getParkingFee(int[] fees) {
            if (cumulativeTime <= fees[0]) {
                return fees[1];
            } else {
                return (int) Math.ceil((cumulativeTime - fees[0]) / (double) fees[2]) * fees[3] + fees[1];
            }
        }

        @Override
        public int compareTo(String id) {
            return this.id.compareTo(id);
        }
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