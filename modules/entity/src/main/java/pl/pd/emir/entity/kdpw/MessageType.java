package pl.pd.emir.entity.kdpw;

public enum MessageType {

    TRANSACTION("trar.ins.001.02", "kdpw.message.type.transaction"),
    VALUATION("trar.ins.002.02", "kdpw.message.type.valuation"),
    PROTECTION("trar.ins.003.02", "kdpw.message.type.protection"),
    TRANSACTION_RESPONSE("trar.sts.001.02", "kdpw.message.type.transaction.response"),
    DATA_SET_RESPONSE("trar.sts.002.02", "kdpw.message.type.dataSet.response"),
    BANK("trar.ins.005.01", "kdpw.message.type.bank"),
    RECONCILIATION("trar.rcn.001.01", "kdpw.message.type.reconciliation"),
    TRANSACTION_RESPONSE_2("trar.sts.003.01", "kdwp.message.type.transaction.response.2");

    private final String msgName;

    private final String msgKey;

    private MessageType(final String name, final String msgKey) {
        this.msgName = name;
        this.msgKey = msgKey;
    }

    public String getMsgName() {
        return msgName;
    }

    public String getMsgKey() {
        return msgKey;
    }

    @Override
    public final String toString() {
        return name();
    }
}
