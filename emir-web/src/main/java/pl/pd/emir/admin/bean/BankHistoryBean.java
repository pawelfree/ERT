package pl.pd.emir.admin.bean;

import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.admin.BankManager;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventLogType;
import org.primefaces.model.SortOrder;

@SessionScoped
@ManagedBean(name = "bankHistoryBean")
public class BankHistoryBean extends AbstractEventLogBean {

    @EJB
    private transient BankManager bankManager;
    private final String DEFAULT_SORT_FIELD = "date";

    @Override
    public String init() {
        return init("bankHistory");
    }

    @Override
    public EventLogType getEventLogType() {
        return EventLogType.INSTITUTION;
    }

    @Override
    public Long getReferenceId() {
        Bank bank = bankManager.getFirst();
        if (Objects.isNull(bank)) {
            return Long.valueOf("0");
        }
        return bank.getId();
    }

    @Override
    public Boolean isHistory() {
        return true;
    }

    @Override
    public String getSubject(EventLog eventLog) {
        Bank refBank = bankManager.getActive();
        return refBank.getExtractName();
    }

    /**
     * Inicjalizacja kryteriów wyszukiwania wykonywana w czasie inicjalizacji bean widoku.
     */
    @Override
    public void initSearchCriteria() {
        super.initSearchCriteria();
        initCriteria();
    }

    /**
     * Inicjalizacja kryteriów wyszukiwania wykonywana po wyczyszczeniu.
     */
    @Override
    public void initSearchCriteriaAfterClean() {
        super.initSearchCriteriaAfterClean();
        initCriteria();
    }

    private void initCriteria() {
        setSortField(DEFAULT_SORT_FIELD);
        setSortOrder(SortOrder.DESCENDING);
    }
}
