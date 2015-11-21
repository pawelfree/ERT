package pl.pd.emir.admin;

import pl.pd.emir.admin.UserManager;
import pl.pd.emir.admin.IExtendedLoggerFacade;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.commons.EventLogManager;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.administration.EventLog;
import pl.pd.emir.enums.EventType;

@Stateless
@Local(EventLogManager.class)
public class EventLogManagerImpl extends AbstractManagerTemplate<EventLog> implements EventLogManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogManagerImpl.class);
    @EJB
    private UserManager userManager;
    @EJB
    private IExtendedLoggerFacade extendedLogger;
    @Resource
    protected SessionContext context;

    public EventLogManagerImpl() {
        super(EventLog.class);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addEventTransactional(EventType eventType, Long referenceId, String eventDetails) {
        LOGGER.debug(String.format("Transactional event: %s", eventType.name()));
        EventLog eventLog = new EventLog(eventType, referenceId, eventDetails, userManager.getCurrentUser(), null);
        entityManager.persist(eventLog);
        logToExternalSystem(eventLog);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addEventNonTransactional(EventType eventType, Long referenceId, String eventDetails) {
        LOGGER.debug(String.format("Nontransactional event: %s", eventType.name()));
        EventLog eventLog = new EventLog(eventType, referenceId, eventDetails, userManager.getCurrentUser(), null);
        entityManager.persist(eventLog);
        logToExternalSystem(eventLog);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addEventTransactional(EventType eventType, Long referenceId, String eventDetails, ChangeLog changeLog) {
        LOGGER.debug(String.format("Transactional event: %s", eventType.name()));
        EventLog eventLog = new EventLog(eventType, referenceId, eventDetails, userManager.getCurrentUser(), changeLog);
        entityManager.persist(eventLog);
        logToExternalSystem(eventLog);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addEventNonTransactional(EventType eventType, Long referenceId, String eventDetails, ChangeLog changeLog) {
        LOGGER.debug(String.format("Nontransactional event: %s", eventType.name()));
        EventLog eventLog = new EventLog(eventType, referenceId, eventDetails, userManager.getCurrentUser(), changeLog);
        entityManager.persist(eventLog);
        logToExternalSystem(eventLog);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EventLog getNewesttByEventType(final Long entityId, final EventType eventType) {
        EventLog result = null;
        try {
            if (entityId == null) {
                result = (EventLog) entityManager.createNamedQuery("EventLog.findByEvent")
                        .setParameter("eventType", eventType)
                        .setMaxResults(1).getSingleResult();
            } else {
                result = (EventLog) entityManager.createNamedQuery("EventLog.findByEntityIdAndEvent")
                        .setParameter("entityId", entityId)
                        .setParameter("eventType", eventType)
                        .setMaxResults(1).getSingleResult();
            }
        } catch (NoResultException nre) {
            LOGGER.info("Cannot find EventLog: " + eventType);
        }
        return result;
    }

    private void logToExternalSystem(EventLog event) {
        //jesli transakcja zapisu logu nie jest oznaczona do wycofania
        if (!context.getRollbackOnly()) {
            extendedLogger.addEvent(event);
        }
    }
}
