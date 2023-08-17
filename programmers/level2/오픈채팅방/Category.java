public enum Category {
    ENTER, LEAVE, CHANGE;

    public String toSuffix() {
        return switch (this) {
            case ENTER -> "님이 들어왔습니다.";
            case LEAVE -> "님이 나갔습니다.";
            default -> "";
        };
    }
}