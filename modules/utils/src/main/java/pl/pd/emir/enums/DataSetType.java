package pl.pd.emir.enums;

public enum DataSetType {

    /**
     * Wycena.
     */
    VALUATION("register.sets.group.type.valuation", "register.sets.group.type.valuation.plural"),
    /**
     * Zabezpieczenie.
     */
    PROTECTION("register.sets.group.type.protection", "register.sets.group.type.protection.plural");

    private final String msgKey;

    private final String msgKeyPlural;

    private DataSetType(String msgKey, String msgKeyPlural) {
        this.msgKey = msgKey;
        this.msgKeyPlural = msgKeyPlural;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public String getMsgKeyPlural() {
        return msgKeyPlural;
    }
}
