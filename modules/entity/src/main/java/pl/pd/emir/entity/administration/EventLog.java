package pl.pd.emir.entity.administration;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.EventLogType;
import pl.pd.emir.enums.EventType;

@Entity
@Table(name = "EVENT_LOG")
@NamedQueries({
    @NamedQuery(name = "EventLog.findByEntityIdAndEvent",
            query = "SELECT e FROM EventLog e "
            + "WHERE e.referenceId = :entityId"
            + " and e.eventType = :eventType ORDER BY e.date DESC"),
    @NamedQuery(name = "EventLog.findByEvent",
            query = "SELECT e FROM EventLog e "
            + "WHERE e.eventType = :eventType ORDER BY e.date DESC")
})
public class EventLog implements Selectable<Long> {

    /**
     * Id.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "event_log_seq_gen")
    @SequenceGenerator(name = "event_log_seq_gen", sequenceName = "SQ_EVENT_LOG", allocationSize = 1)
    private Long id;

    /**
     * Data i czas zdarzenia.
     */
    @Column(name = "EVENT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Rodzaj zdarzenia.
     */
    @Column(name = "EVENT_TYPE", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    /**
     * Rodzaj logu zdarzeń.
     */
    @Column(name = "LOG_TYPE", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private EventLogType logType;

    /**
     * Szczegóły zdarzenia.
     */
    @Column(name = "DETAILS", nullable = true, length = 1024)
    private String details;

    /**
     * Informacja o modyfikacji rekordu.
     */
    @Embedded
    private ChangeLog changeLog;

    /**
     * Id referencyjne - odniesienie do rekordu transakcji/zbioru danowego/kontrachenta.
     */
    @Column(name = "REFERENCE_ID")
    private Long referenceId;

    /**
     * Użytkownik.
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;

    @Transient
    private transient boolean selected;

    public EventLog() {
        super();
        this.date = new Date();
    }

    public EventLog(EventType eventType, Long referenceId, String details, User user, ChangeLog changeLog) {
        super();
        this.date = new Date();
        this.eventType = eventType;
        this.logType = eventType.getLogType();
        this.referenceId = referenceId;
        this.details = details;
        this.user = user;
        this.changeLog = changeLog;
    }

    public EventLog(EventType eventType, EventLogType logType, Long referenceId, String details, User user, ChangeLog changeLog) {
        super();
        this.date = new Date();
        this.eventType = eventType;
        this.logType = logType;
        this.referenceId = referenceId;
        this.details = details;
        this.user = user;
        this.changeLog = changeLog;
    }

    /**
     * Pobranie identyfikatora zdarzenia.
     *
     * @return identyfikator zdarzenia
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Ustawienie identyfikatora zdarzenia.
     *
     * @param id identyfikator zdarzenia
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Pobranie daty i czasu zdarzenia.
     *
     * @return data i czas zdarzenia
     */
    public Date getDate() {
        return date;
    }

    /**
     * Ustawienie daty i czasu zdarzenia.
     *
     * @param date data i czas zdarzenia
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Pobranie rodzaju zdarzenia.
     *
     * @return rodzaj zdarzenia
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Ustawienie rodzaju zdarzenia.
     *
     * @param eventType rodzaj zdarzenia
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Pobranie rodzaju logu dziennika zdarzeń.
     *
     * @return rodzaj logu dziennika zdarzeń
     */
    public EventLogType getLogType() {
        return logType;
    }

    /**
     * Ustawienie rodzaju logu dziennika zdarzeń.
     *
     * @param logType rodzaj logu dziennika zdarzeń
     */
    public void setLogType(EventLogType logType) {
        this.logType = logType;
    }

    /**
     * Pobranie szczegółów zdarzenia.
     *
     * @return szczegóły zdarzenia
     */
    public String getDetails() {
        return details;
    }

    /**
     * Ustawienie szczegółów zdarzenia.
     *
     * @param details szczegóły zdarzenia
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Pobranie informacji o modyfikacji rekordu.
     *
     * @return informacje o modyfikacji rekordu
     */
    public ChangeLog getChangeLog() {
        return changeLog;
    }

    /**
     * Ustawienie informacji o modyfikacji rekordu.
     *
     * @param changeLog informacje o modyfikacji rekordu
     */
    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    /**
     * Pobranie identyfikatora referencyjnego (odniesienia do rekordu transakcji/zbioru danowego/kontrachenta).
     *
     * @return id referencyjne
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Ustawienie identyfikatora referencyjnego (odniesienia do rekordu transakcji/zbioru danowego/kontrachenta).
     *
     * @param referenceId id referencyjne
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Pobranie użytkownika.
     *
     * @return użytkownik
     */
    public User getUser() {
        return user;
    }

    /**
     * Ustawienie użytkownika.
     *
     * @param user użytkownik
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initFields() {
        //TODO
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
