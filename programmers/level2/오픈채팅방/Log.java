public class Log implements Comparable<Log> {
    private Long seq;
    private String uid;
    private Category category;

    public Log(long seq, String uid, Category category) {
        this.seq = seq;
        this.uid = uid;
        this.category = category;
    }

    public Long getSeq() {
        return seq;
    }

    public String getUid() {
        return uid;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public int compareTo(Log log) {
        return this.seq.compareTo(log.getSeq());
    }
}