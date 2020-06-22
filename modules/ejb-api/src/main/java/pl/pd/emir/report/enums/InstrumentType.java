package pl.pd.emir.report.enums;

public enum InstrumentType {

    CREDIT("instrument.type.credit", "CR"),
    CURRENCY("instrument.type.currency", "CU"),
    CAPITAL("instrument.type.capital", "EQ"),
    INTERESTRATE("instrument.type.interestrate", "IR"),
    OTHERS("instrument.type.others", "OT");

    private final String msgKey;
    private final String realValue;

    private InstrumentType(final String msgKey, final String realValue) {
        this.msgKey = msgKey;
        this.realValue = realValue;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public String getKey() {
        return this.getClass().getSimpleName() + "." + this.name();
    }

    public String getRealName() {
        return realValue;
    }
}
