package pl.pd.emir.enums;

public enum ImportScope implements MsgEnum {

    VALUATION_E,
    PROTECTION_E,
    TRANSACTION_E;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }
}
