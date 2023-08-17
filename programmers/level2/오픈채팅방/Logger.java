import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {
    private long seq = 0L;
    private final TreeSet<Log> logs = new TreeSet<>();
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public void add(String log) {
        Record record = new Record(log);
        switch (record.getCategory()) {
            case ENTER -> enter(record);
            case LEAVE -> leave(record);
            case CHANGE -> change(record);
        }
    }

    public void enter(Record record) {
        logs.add(new Log(++seq, record.getUid(), record.getCategory()));
        users.put(record.getUid(), record.getNickname());
    }

    public void leave(Record record) {
        logs.add(new Log(++seq, record.getUid(), record.getCategory()));
    }

    public void change(Record record) {
        users.replace(record.getUid(), record.getNickname());
    }

    public String[] toArray() {
        String[] output = new String[logs.size()];
        for (int i = 0; i < output.length; i++) {
            Log log = logs.pollFirst();
            if (log != null) {
                output[i] = users.get(log.getUid()) + log.getCategory().toSuffix();
            }
        }
        return output;
    }
}