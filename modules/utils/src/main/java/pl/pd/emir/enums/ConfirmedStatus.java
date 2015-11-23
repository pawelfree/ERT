package pl.pd.emir.enums;

public enum ConfirmedStatus implements MsgEnum {

    EMPTY,
    CONFIRMED,
    UNCONFIRMED;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }

    public static ConfirmedStatus fromString(String value) {
        for (ConfirmedStatus status : ConfirmedStatus.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }
        return null;
    }

}
