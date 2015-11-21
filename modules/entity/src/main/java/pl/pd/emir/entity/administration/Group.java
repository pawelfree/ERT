package pl.pd.emir.entity.administration;

import java.util.List;
import javax.persistence.*;
import pl.pd.emir.commons.interfaces.Identifiable;

@Entity
@Table(name = "SYSTEM_GROUP")
@NamedQueries({
    @NamedQuery(name = "Group.getAll", query = "select g from Group g"),
    @NamedQuery(name = "Group.getAllActive", query = "select g from Group g where g.isActive = true"),
    @NamedQuery(name = "Group.getByName", query = "select g from Group g where g.name = :name"),
    @NamedQuery(name = "Group.getByNameWithRoles", query = "select g from Group g join fetch g.roles where g.name = :name"),})
public class Group implements Identifiable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_GROUP_GEN")
    @SequenceGenerator(name = "SYSTEM_GROUP_GEN", sequenceName = "SYSTEM_GROUP_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 100)
    private String name;
    @Column(name = "DESCRIPTION", length = 350)
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYSTEM_GROUP_SYSTEM_ROLE",
            joinColumns
            = @JoinColumn(name = "SYSTEM_GROUP_ID", nullable = false),
            inverseJoinColumns
            = @JoinColumn(name = "SYSTEM_ROLE_ID", nullable = false))
    private List<Role> roles;
    @Column(name = "IS_ACTIVE", columnDefinition = "int")
    private Boolean isActive = Boolean.TRUE;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public boolean hasRole(Role role) {
        boolean result = false;
        if (roles != null && !roles.isEmpty()) {
            for (Role value : roles) {
                if (value.getName().equals(role.getName())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void initFields() {
    }
}
