package pl.pd.emir.criteria;

import java.util.Date;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterLongTO;
import pl.pd.emir.dao.utils.FilterNullTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.enums.EventLogType;

public class EventLogSC extends AbstractSearchCriteria {

    private Date eventDateFrom;
    private Date eventDateTo;
//    private EventType eventType;
    private EventLogType logType;
    private Long referenceId;
    private Boolean historyView;

//    private User user;
    public Date getEventDateFrom() {
        return eventDateFrom == null ? null : (Date) eventDateFrom.clone();
    }

    public void setEventDateFrom(Date eventDateFrom) {
        this.eventDateFrom = eventDateFrom;
    }

    public Date getEventDateTo() {
        return eventDateTo == null ? null : (Date) eventDateTo.clone();
    }

    public void setEventDateTo(Date eventDateTo) {
        this.eventDateTo = eventDateTo;
    }

//    public EventType getEventType() {
//        return eventType;
//    }
//
//    public void setEventType(EventType eventType) {
//        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "eventType", "=", eventType));
//        this.eventType = eventType;
//    }
    public EventLogType getLogType() {
        return logType;
    }

    public void setLogType(EventLogType logType) {
        this.logType = logType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public boolean isHistoryView() {
        return historyView;
    }

    public void setHistoryView(Boolean historyView) {
        this.historyView = historyView;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
    @Override
    public void clear() {
        super.clear();
        eventDateFrom = null;
        eventDateTo = null;
//        eventType = null;
        logType = null;
        referenceId = null;
//        user = null;
    }

    @Override
    public void addFilters() {
        clearFilters();
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "date", ">=", eventDateFrom));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "date", "<=", eventDateTo));
//        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "eventType", "=", eventType));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "logType", "=", logType));
        getFitrSort().getFilters().add(FilterLongTO.valueOf("", "referenceId", "=", referenceId));
//        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "user", "=", user));
        resolveHistoryView();
    }

    public void resolveHistoryView() {
        if (historyView != null) {
            if (historyView) {
                getFitrSort().getFilters().add(FilterNullTO.valueOf("", "changeLog.fieldName", "IS NOT"));
            } else {
                getFitrSort().getFilters().add(FilterNullTO.valueOf("", "changeLog.fieldName", "IS"));
            }
        }
    }

}
