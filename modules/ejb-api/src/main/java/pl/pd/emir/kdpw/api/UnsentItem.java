package pl.pd.emir.kdpw.api;

import pl.pd.emir.entity.Transaction;
import pl.pd.emir.kdpw.api.enums.ItemType;
import pl.pd.emir.kdpw.api.enums.SendingError;

public class UnsentItem extends ErrorItem {

    private final transient Transaction transaction;

    public UnsentItem(String orignalId, SendingError errorReason, String errorDetails, Transaction transaction) {
        super(orignalId, errorReason, errorDetails);
        this.transaction = transaction;
    }

    @Override
    public ItemType getType() {
        return ItemType.UNSENT;
    }

    public Transaction getTransaction() {
        return transaction;
    }

}
