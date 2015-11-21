package pl.pd.emir.entity.kdpw;

public enum MessageStatus {

    GENERATED("kdpw.message.status.generated"),
    ACCEPTED("kdpw.message.status.accepted"),
    REJECTED("kdpw.message.status.rejected");

    private final String msgKey;

    private MessageStatus(String msg) {
        this.msgKey = msg;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
