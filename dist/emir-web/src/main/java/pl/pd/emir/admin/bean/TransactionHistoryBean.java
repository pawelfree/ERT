package pl.pd.emir.admin.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.SortOrder;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventLogType;
import pl.pd.emir.register.TransactionManager;

@SessionScoped
@ManagedBean(name = "transactionHistoryBean")
public class TransactionHistoryBean extends AbstractEventLogBean {

    private final String DEFAULT_SORT_FIELD = "date";

    @EJB
    private transient TransactionManager transactionManager;
    private Transaction transaction;

    public String init(Transaction transaction) {
        this.transaction = transaction;
        return init("transactionHistory");
    }

    @Override
    public EventLogType getEventLogType() {
        return EventLogType.TRANSACTION;
    }

    @Override
    public Long getReferenceId() {
        return transaction.getId();
    }

    @Override
    public Boolean isHistory() {
        return true;
    }

    @Override
    public String getSubject(EventLog eventLog) {
        Transaction refTransaction = transactionManager.getById(eventLog.getReferenceId());
        return refTransaction.getExtractName();
    }

    @Override
    public void initSearchCriteria() {
        super.initSearchCriteria();
        initCriteria();
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        super.initSearchCriteriaAfterClean();
    }

    private void initCriteria() {
        setSortField(DEFAULT_SORT_FIELD);
        setSortOrder(SortOrder.DESCENDING);
    }
}
