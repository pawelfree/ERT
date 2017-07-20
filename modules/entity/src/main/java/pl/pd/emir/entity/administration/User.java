package pl.pd.emir.entity.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.EventType;

@Entity
@Table(name = "SYSTEM_USERS", uniqueConstraints
        = @UniqueConstraint(columnNames = "LOGIN"))
@NamedQueries(value = {
    @NamedQuery(name = "User.getByLogin", query = "select u from User u where u.login = :login"),})
public class User implements Logable<Long>, Selectable<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system_user_seq_gen")
    @SequenceGenerator(name = "system_user_seq_gen", sequenceName = "SQ_SYSTEM_USER", allocationSize = 1, initialValue = 50)
    private Long id;
    /**
     * Login użytkownika.
     */
    @Column(name = "LOGIN", nullable = false, length = 32)
    private String login;
    /**
     * Imię użytkownika.
     */
    @Column(name = "FIRST_NAME", nullable = true, length = 64)
    private String firstName;
    /**
     * Nazwisko użytkownika.
     */
    @Column(name = "LAST_NAME", nullable = true, length = 64)
    private String lastName;
    @Column(name = "ACTIVE", nullable = false)
    private boolean active;

    @Transient
    private boolean selected;

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Pobranie identyfikatora użytkownika.
     *
     * @return identyfikator użytkownika
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Ustawienie identyfikatora użytkownika.
     *
     * @param id identyfikator użytkownika
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Pobranie loginu użytkownika.
     *
     * @return login użytkownika
     */
    public String getLogin() {
        return login;
    }

    /**
     * Ustawienie loginu użytkownika.
     *
     * @param login login użytkownika
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Pobranie imienia użytkownika.
     *
     * @return imię użytkownika
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Ustawienie imienia użytkownika.
     *
     * @param firstName imię użytkownika
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Pobranie nazwiska użytkownika.
     *
     * @return nazwisko użytkownika
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ustawienie nazwiska użytkownika.
     *
     * @param lastName nazwisko użytkownika
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void initFields() {
        //TODO
    }

    @Override
    public EventType getDeleteEventType() {
        return EventType.USER_DELETE;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.USER_MODIFICATION;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.USER_MODIFICATION;
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
