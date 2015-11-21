package pl.pd.emir.register.bean;

import javax.annotation.PostConstruct;
import pl.pd.emir.criteria.TransactionSC;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.register.TransactionManager;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

public abstract class AbstractTransactionListBaseBean extends AbstractTransactionListBean<Transaction, TransactionManager, TransactionSC> {

    private static final long serialVersionUID = 42L;
    /**
     * Domyślne pole, po którym posortowane są transakcje.
     */
    private final String DEFAULT_SORT_FIELD = "transactionDate";

    /**
     * default constructor
     */
    public AbstractTransactionListBaseBean() {
        super(TransactionSC.class);
    }

    @PostConstruct
    protected void initialize() {
        init();
    }

    @Override
    public DataType[] getDataType() {
        return new DataType[]{DataType.NEW, DataType.ONGOING, DataType.COMPLETED};
    }

    @Override
    public void onRowToggle(final ToggleEvent event) {
        msgItems.clear();
        if (Visibility.VISIBLE.equals(event.getVisibility())) {
            msgItems.addAll(((Transaction) event.getData()).getRelatedItems());
        }
    }

    public Boolean canRemove(final ProcessingStatus status) {
        return status == ProcessingStatus.NEW;
    }

    public String getInfoWindow() {
        return BUNDLE.getString("register.transaction.isPossibilityDeleteTransaction");
    }

    @Override
    protected final String getDefaultSortField() {
        return DEFAULT_SORT_FIELD;
    }

    protected abstract String removeById(Long id, String originalId);

    protected abstract boolean isPossibilityDeleteTransaction(Long id, String originalId);

}
