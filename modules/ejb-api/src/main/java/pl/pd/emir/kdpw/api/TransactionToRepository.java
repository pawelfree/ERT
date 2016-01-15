package pl.pd.emir.kdpw.api;

import pl.pd.emir.entity.Sendable;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.kdpw.api.enums.ItemType;

public class TransactionToRepository extends ResultItem {

    /**
     * Transakcja do wysyłki.
     */
    private transient final Sendable registable;

    /**
     * Typ komunikatu z transakcja.
     */
    private transient TransactionMsgType msgType;

    /**
     * Identyfikator komunikatu powiązanego z bieżącym komunikatem.
     */
    private String relatedMsgId;

    /**
     * Czy komunikat anulowania dotyczy anulowania mutacji.
     */
    private boolean cancelMutation;

    private String sndrMsgRef;

    public TransactionToRepository(final Sendable transaction, final boolean cancelMutation, final String relatedMsgId) {
        super(transaction.getOriginalId());
        this.registable = transaction;
        this.msgType = TransactionMsgType.E;
        this.cancelMutation = cancelMutation;
        this.relatedMsgId = relatedMsgId;
    }

    public TransactionToRepository(final Sendable transaction, final TransactionMsgType msgTypeValue) {
        super(transaction.getOriginalId());
        this.registable = transaction;
        this.msgType = msgTypeValue;
    }

    public TransactionToRepository(final Sendable transaction) {
        super(transaction.getOriginalId());
        this.registable = transaction;
    }

    public final TransactionMsgType getMsgType() {
        return msgType;
    }

    public final Sendable getRegistable() {
        return registable;
    }

    public final boolean isCancelMutation() {
        return cancelMutation;
    }

    public String getRelatedMsgId() {
        return relatedMsgId;
    }

    public String getSndrMsgRef() {
        return sndrMsgRef;
    }

    public void setSndrMsgRef(String sndrMsgRef) {
        this.sndrMsgRef = sndrMsgRef;
    }

    @Override
    public ItemType getType() {
        return ItemType.TO_SEND;
    }

}
