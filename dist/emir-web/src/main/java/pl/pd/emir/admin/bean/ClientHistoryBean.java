package pl.pd.emir.admin.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.SortOrder;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventLogType;
import pl.pd.emir.register.ClientManager;

@SessionScoped
@ManagedBean(name = "clientHistoryBean")
public class ClientHistoryBean extends AbstractEventLogBean {

    private final String DEFAULT_SORT_FIELD = "date";
    @EJB
    private transient ClientManager clientManager;

    private Client client;

    public String init(Client client) {
        this.client = client;
        return init("clientHistory");
    }

    @Override
    public EventLogType getEventLogType() {
        return EventLogType.CONTRACTOR;
    }

    @Override
    public Long getReferenceId() {
        return client.getId();
    }

    @Override
    public Boolean isHistory() {
        return true;
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

    public Long getClientId() {
        return client.getId();
    }

    public String getOriginalId() {
        return client.getOriginalId();
    }

    @Override
    public String getSubject(EventLog eventLog) {
        Client refClient = clientManager.getById(eventLog.getReferenceId());
        return refClient.getExtractName();
    }

}
