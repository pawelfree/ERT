package pl.pd.emir.entity.kdpw.to;

public abstract class KdpwItem {

    private final Long extractId;

    private final String sndrMsgRef;

    public KdpwItem(Long extractId, String sndrMsgRef) {
        this.extractId = extractId;
        this.sndrMsgRef = sndrMsgRef;
    }

    public Long getExtractId() {
        return extractId;
    }

    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

}
