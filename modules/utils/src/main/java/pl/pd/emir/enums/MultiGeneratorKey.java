package pl.pd.emir.enums;

public enum MultiGeneratorKey {

    KDPW_MSG_NUMBER(1000000000L),
    KDPW_FILE_NUMBER(1000000L);

    private final Long startNumber;

    private MultiGeneratorKey(Long startNumber) {
        this.startNumber = startNumber;
    }

    public Long getStartNumber() {
        return startNumber;
    }
}
