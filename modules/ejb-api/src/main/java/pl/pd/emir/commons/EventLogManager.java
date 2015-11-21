package pl.pd.emir.commons;

import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.manager.GenericManager;

public interface EventLogManager extends GenericManager<EventLog> {

    void addEventNonTransactional(EventType eventType, Long referenceId, String eventDetails);

    void addEventNonTransactional(EventType eventType, Long referenceId, String eventDetails, ChangeLog changeLog);

    void addEventTransactional(EventType eventType, Long referenceId, String eventDetails);

    void addEventTransactional(EventType eventType, Long referenceId, String eventDetails, ChangeLog changeLog);

    EventLog getNewesttByEventType(Long entityId, EventType eventType);

}
