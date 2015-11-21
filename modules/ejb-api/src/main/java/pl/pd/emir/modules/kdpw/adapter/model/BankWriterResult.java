package pl.pd.emir.modules.kdpw.adapter.model;

public class BankWriterResult extends AbstractWriterResult {

    private static final long serialVersionUID = 3549592728184920904L;

    private final Long extractId;

    private final String msgId;

    public BankWriterResult(Long extractId, String msgId, String message) {
        super(message);
        this.extractId = extractId;
        this.msgId = msgId;
    }

    public Long getExtractId() {
        return extractId;
    }

    public String getMsgId() {
        return msgId;
    }

}
