package pl.pd.emir.enums;

public enum ProcessingStatus implements MsgEnum {

    NEW,
    SENT,
    UNSENT,
    REJECTED,
    PARTLY_REJECTED,
    CORRECTED,
    CONFIRMED,
    CANCELLATION_SENT,
    CANCELED,
    PARTLY_CANCELED;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
