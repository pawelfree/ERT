package pl.pd.emir.enums;

public enum ImportStatus implements MsgEnum {

    OK,
    ERROR,
    WARRNING,
    DURING_PROCESSING,
    NOT_EXIST;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
