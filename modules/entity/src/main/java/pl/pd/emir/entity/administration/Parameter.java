package pl.pd.emir.entity.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.enums.ParameterKey;

@Entity
@Table(name = "SYSTEM_PARAMETER")
public class Parameter implements Identifiable<Long>, Selectable<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system_parameter_seq_gen")
    @SequenceGenerator(name = "system_parameter_seq_gen", sequenceName = "SQ_SYSTEM_PARAMETER", allocationSize = 1)
    private Long id;

    @Column(name = "PARAMETER_KEY", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private ParameterKey key;

    @Column(name = "PARAMETER_VALUE", length = 256, nullable = false)
    private String value;

    @Column(name = "PARAMETER_DESCRIPTION", length = 256, nullable = true)
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ParameterKey getKey() {
        return key;
    }

    public void setKey(ParameterKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    private transient boolean selected;

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
