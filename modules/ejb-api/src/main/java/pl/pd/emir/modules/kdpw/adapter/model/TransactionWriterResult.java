package pl.pd.emir.modules.kdpw.adapter.model;

import pl.pd.emir.kdpw.api.TransactionToRepository;
import java.util.Collections;
import java.util.List;

public class TransactionWriterResult<E extends TransactionToRepository> extends AbstractWriterResult {

    private final List<E> transactions;

    public TransactionWriterResult(final List<E> transactions, final String message) {
        super(message);
        this.transactions = transactions;
    }

    public final List<E> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
