package pl.pd.emir.entity.administration;

import java.io.Serializable;
import javax.persistence.*;
import pl.pd.emir.enums.RoleName;

@Entity
@Table(name = "SYSTEM_ROLE")
@NamedQueries({
    @NamedQuery(name = "Role.getAll", query = "select r from Role r order by r.id asc"),
    @NamedQuery(name = "Role.getByName", query = "select r from Role r where r.name = :name"),})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_ROLE_GEN")
    @SequenceGenerator(name = "SYSTEM_ROLE_GEN", sequenceName = "SYSTEM_ROLE_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 100)
    @Enumerated(EnumType.STRING)
    private RoleName name;
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
