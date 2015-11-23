package pl.pd.emir.admin.bean;

import javax.ejb.EJB;
import pl.pd.emir.admin.EventLogManager;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.criteria.EventLogSC;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventLogType;

public abstract class AbstractEventLogBean extends AbstractListBean<EventLog, EventLogManager, EventLogSC> {

    @EJB
    private transient EventLogManager eventLogManager;

    public AbstractEventLogBean() {
        super(EventLogSC.class);
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
    public void initSearchCriteria() {
        criteria.setLogType(getEventLogType());
        criteria.setReferenceId(getReferenceId());
        criteria.setHistoryView(isHistory());
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        criteria.setLogType(getEventLogType());
        criteria.setReferenceId(getReferenceId());
        criteria.setHistoryView(isHistory());
    }

    public EventLogType[] getEventLogTypes() {
        return EventLogType.values();
    }

    public abstract EventLogType getEventLogType();

    public abstract Long getReferenceId();

    public abstract Boolean isHistory();

    public abstract String getSubject(EventLog eventLog);

}
