package pl.pd.emir.reports.model;

import java.util.Date;

public class EventLogWrapper {

    private Date dateTime;
    private String logType;
    private String eventType;
    private String eventSubject;
    private String user;
    private String details;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSubject() {
        return eventSubject;
    }

    public void setEventSubject(String eventSubject) {
        this.eventSubject = eventSubject;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
