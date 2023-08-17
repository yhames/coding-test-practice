public class Record {
    private Category category;
    private String uid;
    private String nickname;

    public Record(String record) {
        String[] row = record.split(" ");
        this.category = Category.valueOf(row[0].toUpperCase());
        this.uid = row[1];
        if (row.length > 2) {
            this.nickname = row[2];
        }
    }

    public Category getCategory() {
        return category;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }
}