package pl.pd.emir.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.enums.MultiGeneratorKey;

@Entity
@Table(name = "MULTI_NUMBER")
@NamedQueries({
    @NamedQuery(name = "MultiNumber.findNumberForDate",
            query = "SELECT t FROM MultiNumber t WHERE t.numberType = :numberType"
            + " AND t.actualDate = :forDate")
})
public class MultiNumber implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "multi_number_seq_gen")
    @SequenceGenerator(name = "multi_number_seq_gen", sequenceName = "SQ_MULTI_NUMBER", allocationSize = 50)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER_TYPE", length = 256, nullable = false)
    @Enumerated(EnumType.STRING)
    private MultiGeneratorKey numberType;

    @Column(name = "ACTUAL_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date actualDate;

    @Column(name = "ACTUAL_NUMBER")
    private Long actualNumber;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public MultiNumber() {
        super();
    }

    public MultiNumber(MultiGeneratorKey numberType, Date actualDate, Long actualNumber, Long version) {
        super();
        this.numberType = numberType;
        this.actualDate = actualDate;
        this.actualNumber = actualNumber;
        this.version = version;
    }

    @Override
    public Long getId() {
        return id;
    }

    public long increase(long withValue) {
        actualNumber += withValue;
        return actualNumber;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public Long getActualNumber() {
        return actualNumber;
    }

    public MultiGeneratorKey getNumberType() {
        return numberType;
    }

    @Override
    public void setId(Long id) {
        //
    }

    @Override
    public void initFields() {
    }
}
