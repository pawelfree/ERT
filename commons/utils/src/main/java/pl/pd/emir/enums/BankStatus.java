package pl.pd.emir.enums;

public enum BankStatus implements MsgEnum {

    CORRECTED,
    SENT,
    REJECTED,
    CONFIRMED;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
