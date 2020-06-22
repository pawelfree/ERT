package pl.pd.emir.enums;

public enum Instrument {

    CD("instrument.cd", "CD"),
    FRA("instrument.fra", "FRA"),
    FUTURE("instrument.future", "FU"),
    FORWARD("instrument.forward", "FW"),
    SWAP("instrument.swap", "SW");

    private final String msgKey;
    private final String realValue;

    private Instrument(final String msgKey, final String realValue) {
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
