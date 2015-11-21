package pl.pd.emir.enums;

public enum DataType implements MsgEnum {

    NEW,
    ONGOING,
    COMPLETED,
    CANCELLED;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
