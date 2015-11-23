package pl.pd.emir.enums;

public enum EventLogType implements MsgEnum {

    INFO,
    TRANSACTION,
    VALUATION_GROUP,
    PROTECTION_GROUP,
    CONTRACTOR,
    INSTITUTION,;

    @Override
    public String getMsgKey() {
        return String.format("%s.%s", this.getClass().getSimpleName(), name());
    }

}
