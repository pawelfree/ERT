package pl.pd.emir.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.resources.EventLogBuilder;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;

@Entity
@Table(name = "VALUATION")
@NamedQueries({
    @NamedQuery(name = "Valuation.importRaport",
            query = "select v FROM Valuation v, Transaction t "
            + "WHERE v.fileName = :file and t.valuation = v.id "
            + "and t.transactionDate = :date"),
    @NamedQuery(name = "Valuation.importLogId",
            query = "Select v FROM Valuation v "
            + "WHERE v.fileName = :file "),
    @NamedQuery(name = "Valuation.getValuationByImportLog",
            query = "Select v FROM Valuation v "
            + "WHERE v.importLog = :importLog ")
})
@Customizer(Valuation.class)
public class Valuation extends Extract implements Historable<Valuation>, Logable<Long>, DescriptorCustomizer {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "valuation_seq_gen")
    @SequenceGenerator(name = "valuation_seq_gen", sequenceName = "SQ_EMIR_VALUATION", allocationSize = 1)
    private Long id;
    /**
     * identyfikator transakcji, ktorej dotyczy wycena
     */
    @Transient
    private String originalId;
    /**
     * data waluty oryginalnej transakcji
     */
    @Transient
    private Date transactionDate;

    @ValidateCompleteness(subjectClass = Valuation.class, entry = true)
    @Embedded
    private ValuationData valuationData;
    /**
     * Wersja ekstraktu z którego wczytano encję.
     */
    @Column(name = "EXTRACT_VERSION")
    private Integer extractVersion;
    /**
     * Nazwa pliku, z którego wczytano ekstrakt.
     */
    @Column(name = "FILE_NAME", length = 512, nullable = true)
    private String fileName;

    public Valuation() {
        super();
        initFields();
    }

    public Valuation(String originalId, Date transactionDate, ValuationData valuationData) {
        super();
        this.originalId = originalId;
        this.transactionDate = transactionDate;
        this.valuationData = valuationData;
        initFields();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ValuationData getValuationData() {
        return valuationData;
    }

    public void setValuationData(ValuationData valuationData) {
        this.valuationData = valuationData;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public Integer getExtractVersion() {
        return extractVersion;
    }

    public void setExtractVersion(Integer extractVersion) {
        this.extractVersion = extractVersion;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getExtractName() {
        return "WYCENA_E";
    }

    @Override
    public final void initFields() {
        if (this.valuationData == null) {
            this.valuationData = new ValuationData();
        }
    }

    public static void checkEntity(List<ChangeLog> result, Valuation oldEntity, Valuation newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }

        if (oldEntity == null) {
            ValuationData.checkEntity(result, null, newEntity.getValuationData(), changeComment);
            checkFieldsEquals(result, null, newEntity.getOriginalId(), EventLogBuilder.EventDetailsKey.ORIGINAL_ID, changeComment);
            checkFieldsEquals(result, null, newEntity.getTransactionDate(), EventLogBuilder.EventDetailsKey.TRANSACTION_DATE, changeComment);
        } else if (newEntity == null) {
            ValuationData.checkEntity(result, oldEntity.getValuationData(), null, changeComment);
            checkFieldsEquals(result, oldEntity.getOriginalId(), null, EventLogBuilder.EventDetailsKey.ORIGINAL_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getTransactionDate(), null, EventLogBuilder.EventDetailsKey.TRANSACTION_DATE, changeComment);
        } else {
            ValuationData.checkEntity(result, oldEntity.getValuationData(), newEntity.getValuationData(), changeComment);
            checkFieldsEquals(result, oldEntity.getOriginalId(), newEntity.getOriginalId(), EventLogBuilder.EventDetailsKey.ORIGINAL_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getTransactionDate(), newEntity.getTransactionDate(), EventLogBuilder.EventDetailsKey.TRANSACTION_DATE, changeComment);
        }
    }

    @Override
    public EventType getDeleteEventType() {
        return EventType.VALUATION_DELETE;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.VALUATION_INSERT;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.VALUATION_MODIFICATION;
    }

    public Valuation fullClone() {
        if (getValuationData().fullClone() == null) {
            ValuationData valuationDat = new ValuationData();
            return new Valuation(originalId, transactionDate, valuationDat);
        }
        return new Valuation(originalId, transactionDate, getValuationData().fullClone());
    }

    @Override
    public void customize(ClassDescriptor descriptor) {
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("valuationData")).setIsNullAllowed(false);
    }

    @Override
    public List<ChangeLog> getChangeLogs(Valuation newEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEmpty() {
        return StringUtil.isEmpty(originalId)
                && Objects.isNull(transactionDate)
                && (Objects.isNull(valuationData) || valuationData.isEmpty())
                && Objects.isNull(extractVersion)
                && Objects.isNull(fileName);
    }

    @Override
    public String toString() {
        return "Valuation{" + "id=" + id + ", originalId=" + originalId + ", transactionDate=" + transactionDate
                + ", valuationData=" + valuationData + ", extractVersion=" + extractVersion + ", fileName=" + fileName + '}';
    }
}
