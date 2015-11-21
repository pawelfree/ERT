package pl.pd.emir.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.admin.BankManager;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.EventLogManager;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventLogType;
import static pl.pd.emir.enums.EventLogType.INSTITUTION;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.EventLogWrapper;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.resources.MultipleFilesResourceBundle;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "eventLogListBean")
@SessionScoped
public class EventLogListBean extends AbstractEventLogBean implements Serializable {

    @EJB
    private transient EventLogManager eventLogManager;
    @EJB
    private transient BankManager bankManager;
    @EJB
    private transient ClientManager clientManager;
    @EJB
    private transient TransactionManager transactionManager;

    private final transient ReportData<EventLogWrapper> reportData = new ReportData<>();
    private final transient ReportType reportType = ReportType.EVENT_LOG_TABLE;
    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();
    private final String DEFAULT_SORT_FIELD = "date";

    public ReportType getReportType() {
        return reportType;
    }

    public ReportData getReportData() {
        Collection<EventLogWrapper> data = new ArrayList<>();
        List<EventLog> eventLogs = eventLogManager.findAll(criteria);
        for (EventLog eventLog : eventLogs) {
            EventLogWrapper wrapper = new EventLogWrapper();
            wrapper.setDateTime(eventLog.getDate());
            if (eventLog.getLogType() != null) {
                wrapper.setLogType(eventLog.getLogType().getMsgKey() == null ? "" : BUNDLE.getString(eventLog.getLogType().getMsgKey()));
            }
            if (eventLog.getEventType() != null) {
                wrapper.setEventType(eventLog.getEventType().getMsgKey() == null ? "" : BUNDLE.getString(eventLog.getEventType().getMsgKey()));
            }
            wrapper.setEventSubject(getSubject(eventLog));
            if (eventLog.getUser() != null) {
                wrapper.setUser(eventLog.getUser().getLogin() == null ? "" : eventLog.getUser().getLogin());
            }
            wrapper.setDetails(eventLog.getDetails() == null ? "" : eventLog.getDetails());
            data.add(wrapper);
        }
        ParametersWrapper parameters = new ParametersWrapper("c.datatime", "c.area", "c.typeEvent", "c.eventParty", "c.user", "c.addDetails");
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    @Override
    public EventLogManager getService() {
        return eventLogManager;
    }

    @Override
    public String getAction() {
        return "eventLogList";
    }

    @Override
    public EventLogType getEventLogType() {
        return null;
    }

    @Override
    public Long getReferenceId() {
        return null;
    }

    @Override
    public Boolean isHistory() {
        return false;
    }

    @Override
    public String getSubject(EventLog eventLog) {
        EventLogType logType = eventLog.getEventType().getLogType();
        Long refId = eventLog.getReferenceId();
        if (logType == null || refId == null) {
            return "-";
        }
        switch (logType) {
            case INSTITUTION:
                return bankManager.getFirst().getBankName();
            case CONTRACTOR:
                Client client = clientManager.getById(refId);
                if (client != null) {
                    return client.getOriginalId();
                } else {
                    return "-";
                }
            case TRANSACTION:
                Transaction transaction = transactionManager.getById(refId);
                if (transaction != null) {
                    return String.format("%s, wersja:%s, %s",
                            transaction.getOriginalId(),
                            transaction.getVersion() == null ? "brak" : transaction.getVersion(),
                            DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT));
                } else {
                    return "-";
                }
            default:
                return "-";
        }
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
        getCriteria().setEventDateFrom(DateUtils.getDayBegin(new Date()));
        getCriteria().setEventDateTo(DateUtils.getDayEnd(new Date()));
    }
}
