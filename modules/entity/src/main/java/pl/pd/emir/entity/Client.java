package pl.pd.emir.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.commons.interfaces.Validatable;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.ContrPartyIndustry;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.resources.EventLogBuilder;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import pl.pd.emir.entity.annotations.TransactionDataChange;

/**
 * Pojedynczy rekord z ekstraktu CLIENT_E
 */
@NamedQueries({
    @NamedQuery(name = "Client.getByOriginalId", query = "SELECT c FROM Client c WHERE c.originalId = :originalId"),
    @NamedQuery(name = "Client.importRaport", query = "select c FROM Client c WHERE c.fileName = :file "),
    @NamedQuery(name = "Client.getClientBatchByOriginalId", query = "SELECT c FROM Client c WHERE c.originalId IN :originalIdList"),
    @NamedQuery(name = "Client.getAll", query = "SELECT c FROM Client c "),
    @NamedQuery(name = "Client.getClientByImportLog", query = "SELECT c FROM Client c WHERE c.importLog = :importLog")
})
@Entity
@Table(name = "CLIENT")
@Customizer(Client.class)
public class Client extends Extract implements Selectable<Long>, DescriptorCustomizer, Validatable, Historable<Client>, Logable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "client_seq_gen")
    @SequenceGenerator(name = "client_seq_gen", sequenceName = "SQ_EMIR_CLIENT", allocationSize = 1000)
    private Long id;
    /*
     * ID_KLIENTA, Unikalny identyfikator klienta (powiązanie z transakcją) z ekstraktu [0]
     */
    @Column(name = "ORIGINAL_ID", length = 100, unique = true)
    @TransactionDataChange
    private String originalId;
    /**
     * NAZWA_KLIENTA, Nazwa klienta przekazywana w komunikacie [1]
     */
    @ValidateCompleteness(subjectClass = Client.class)
    @Column(name = "CLIENT_NAME", length = 100)
    private String clientName;
    /*
     * COUNTRY, kod kraju instytucji [2]
     */
    //@ValidateCompleteness(subjectClass = Client.class) //tylko dla klienta z RPRTID_TP != LEIC
    @Enumerated(EnumType.STRING)
    @Column(name = "COUNTRY_CODE", length = 3)
    private CountryCode countryCode;
    /*
     * client2 np BTMU raportuje informacje w imieniu kontrahenta
     */
    @Column(name = "REPORTED")
    private Boolean reported;
    
    /*
     * client2 np BTMU raportuje informacje w imieniu kontrahenta
     */
    @Column(name = "EUC_REPORTED")
    private Boolean eucReported;
    
    /*
     * Dane podmiotu raportującego [3-4]
     */
    @Embedded
    private BusinessEntity businessEntity;
    /*
     * Dane instytucji (identyfikacja, dane adresowe) [5-6, 8-13]
     */
    @ValidateCompleteness(subjectClass = Client.class, entry = true)
    @Embedded
    private Institution institution;
    /*
     * CORPSCTR, Branża, do której należy kontrahent [14]
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "CONTR_PARTY_INDUSTRY", length = 3)
    private ContrPartyIndustry contrPartyIndustry;
    /*
     * FINNONFIN_ID, Flaga - Kontrahent finansowy / niefinansowy [15]
     */
    @ValidateCompleteness(subjectClass = Client.class)
    @Column(name = "CONTR_PARTY_TYPE", length = 1)
    private String contrPartyType;

    /*
     * EOG, Flaga - Czy Kontrahent ma siedzibę poza strefą EOG [16]
     */
    @Column(name = "EOG", length = 1)
    private String eog;

    /**
     * Z tym klientem zawierane są transakcje wewnętrzgrupowe
     */
    @Column(name = "INTRAGROUP_TRANS")
    private Boolean intraGroupTransactions;

    /**
     * OSOBA_FIZYCZNA, Flaga [17]
     */
    @Column(name = "NATURAL_PERSON")
    private Boolean naturalPerson;

    /*
     * Status poprawnosci
     */
    @Column(name = "VALIDATION_STATUS")
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    /**
     * Nazwa pliku, z którego wczytano ekstrakt.
     */
    @Column(name = "FILE_NAME", length = 512, nullable = true)
    private String fileName;
    @Transient
    private transient boolean selected;

    @Column(name = "CLIENT_VERSION")
    private Integer clientVersion;

    /**
     * default Constructor
     */
    public Client() {
        super();
        initFields();
    }

    public Client(String originalId, String clientName, CountryCode countryCode, BusinessEntity businessEntity,
            Boolean reported, Boolean eucReported, Institution institution, ContrPartyIndustry contrPartyIndustry, String contrPartyType, String eog,
            Boolean naturalPerson, ValidationStatus validationStatus) {
        this();
        this.originalId = originalId;
        this.clientName = clientName;
        this.countryCode = countryCode;
        this.businessEntity = businessEntity;
        this.reported = reported;
        this.eucReported = eucReported;
        this.institution = institution;
        this.contrPartyIndustry = contrPartyIndustry;
        this.contrPartyType = contrPartyType;
        this.eog = eog;
        this.naturalPerson = naturalPerson;
        this.validationStatus = validationStatus;
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

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public ContrPartyIndustry getContrPartyIndustry() {
        return contrPartyIndustry;
    }

    public void setContrPartyIndustry(ContrPartyIndustry contrPartyIndustry) {
        this.contrPartyIndustry = contrPartyIndustry;
    }

    public String getContrPartyType() {
        return contrPartyType;
    }

    public void setContrPartyType(String contrPartyType) {
        this.contrPartyType = contrPartyType;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getEog() {
        return eog;
    }

    public void setEog(String eog) {
        this.eog = eog;
    }

    public Boolean getNaturalPerson() {
        return naturalPerson;
    }

    public void setNaturalPerson(Boolean naturalPerson) {
        this.naturalPerson = naturalPerson;
    }

    public Boolean getReported() {
        return reported;
    }

    public void setReported(Boolean reported) {
        this.reported = reported;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
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
        return "KLIENCI_E";
    }

    @Override
    public final void initFields() {
        if (this.institution == null) {
            this.institution = new Institution();
        }
        if (this.businessEntity == null) {
            this.businessEntity = new BusinessEntity();
        }
    }

    @Override
    public List<ChangeLog> getChangeLogs(Client newClient) {
        List<ChangeLog> result = new ArrayList<>();
        checkEntity(result, this, newClient);
        return result;
    }

    public static void checkEntity(List<ChangeLog> result, Client oldEntity, Client newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity != null && newEntity != null) {
            BusinessEntity.checkEntity(result, oldEntity.getBusinessEntity(), newEntity.getBusinessEntity());
            Institution.checkEntity(result, oldEntity.getInstitution(), newEntity.getInstitution());
            checkFieldsEquals(result, oldEntity.getOriginalId(), newEntity.getOriginalId(), EventLogBuilder.EventDetailsKey.CLIENT_ID);
            checkFieldsEquals(result, oldEntity.getClientName(), newEntity.getClientName(), EventLogBuilder.EventDetailsKey.CLIENT_NAME);
            checkFieldsEquals(result, oldEntity.getCountryCode(), newEntity.getCountryCode(), EventLogBuilder.EventDetailsKey.COUNTRY_CODE);
            checkFieldsEquals(result, oldEntity.getEog(), newEntity.getEog(), EventLogBuilder.EventDetailsKey.EOG);
            checkFieldsEquals(result, oldEntity.getNaturalPerson(), newEntity.getNaturalPerson(), EventLogBuilder.EventDetailsKey.NATURAL_PERSON);
            checkFieldsEquals(result, oldEntity.getReported(), newEntity.getReported(), EventLogBuilder.EventDetailsKey.REPORTED);
            checkFieldsEquals(result, oldEntity.getContrPartyType(), newEntity.getContrPartyType(), EventLogBuilder.EventDetailsKey.CONTR_PARTY_TYPE);
            checkFieldsEquals(result, oldEntity.getContrPartyIndustry(), newEntity.getContrPartyIndustry(), EventLogBuilder.EventDetailsKey.CONTR_PARTY_INDUSTRY);
            checkFieldsEquals(result, oldEntity.getValidationStatus(), newEntity.getValidationStatus(), EventLogBuilder.EventDetailsKey.VALIDATION_STATUS);
        } else {
            if (oldEntity == null) {
                BusinessEntity.checkEntity(result, null, newEntity.getBusinessEntity());
                Institution.checkEntity(result, null, newEntity.getInstitution());
                checkFieldsEquals(result, null, newEntity.getOriginalId(), EventLogBuilder.EventDetailsKey.CLIENT_ID);
                checkFieldsEquals(result, null, newEntity.getClientName(), EventLogBuilder.EventDetailsKey.CLIENT_NAME);
                checkFieldsEquals(result, null, newEntity.getCountryCode(), EventLogBuilder.EventDetailsKey.COUNTRY_CODE);
                checkFieldsEquals(result, null, newEntity.getEog(), EventLogBuilder.EventDetailsKey.EOG);
                checkFieldsEquals(result, null, newEntity.getNaturalPerson(), EventLogBuilder.EventDetailsKey.NATURAL_PERSON);
                checkFieldsEquals(result, null, newEntity.getReported(), EventLogBuilder.EventDetailsKey.REPORTED);
                checkFieldsEquals(result, null, newEntity.getContrPartyType(), EventLogBuilder.EventDetailsKey.CONTR_PARTY_TYPE);
                checkFieldsEquals(result, null, newEntity.getContrPartyIndustry(), EventLogBuilder.EventDetailsKey.CONTR_PARTY_INDUSTRY);
                checkFieldsEquals(result, null, newEntity.getValidationStatus(), EventLogBuilder.EventDetailsKey.VALIDATION_STATUS);
            } else {
                //TODO do poprawy ....
                BusinessEntity.checkEntity(result, oldEntity.getBusinessEntity(), null);
                Institution.checkEntity(result, oldEntity.getInstitution(), null);
                checkFieldsEquals(result, oldEntity.getOriginalId(), null, EventLogBuilder.EventDetailsKey.CLIENT_ID);
                checkFieldsEquals(result, oldEntity.getClientName(), null, EventLogBuilder.EventDetailsKey.CLIENT_NAME);
                checkFieldsEquals(result, oldEntity.getCountryCode(), null, EventLogBuilder.EventDetailsKey.COUNTRY_CODE);
                checkFieldsEquals(result, oldEntity.getEog(), null, EventLogBuilder.EventDetailsKey.EOG);
                checkFieldsEquals(result, oldEntity.getNaturalPerson(), null, EventLogBuilder.EventDetailsKey.NATURAL_PERSON);
                checkFieldsEquals(result, oldEntity.getReported(), null, EventLogBuilder.EventDetailsKey.REPORTED);
                checkFieldsEquals(result, oldEntity.getContrPartyType(), null, EventLogBuilder.EventDetailsKey.CONTR_PARTY_TYPE);
                checkFieldsEquals(result, oldEntity.getContrPartyIndustry(), null, EventLogBuilder.EventDetailsKey.CONTR_PARTY_INDUSTRY);
                checkFieldsEquals(result, oldEntity.getValidationStatus(), null, EventLogBuilder.EventDetailsKey.VALIDATION_STATUS);
            }
        }
    }

    @Override
    public EventType getDeleteEventType() {
        return EventType.CLIENT_DELETE;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.CLIENT_INSERT;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.CLIENT_MODIFICATION;
    }

    public Client fullClone() {
        return new Client(
                originalId,
                clientName,
                countryCode,
                businessEntity == null ? new BusinessEntity() : businessEntity.fullClone(),
                reported,
                eucReported,
                institution == null ? new Institution() : institution.fullClone(),
                contrPartyIndustry,
                contrPartyType,
                eog,
                naturalPerson,
                validationStatus);
    }

    @Override
    public void customize(ClassDescriptor descriptor) {
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("businessEntity")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("institution")).setIsNullAllowed(false);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public Integer getExtractVersion() {
        //nie dotyczy
        return null;
    }

    public Integer getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(Integer clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Boolean getIntraGroupTransactions() {
        return intraGroupTransactions;
    }

    public void setIntraGroupTransactions(Boolean intraGroupTransactions) {
        this.intraGroupTransactions = intraGroupTransactions;
    }

    public Boolean getEucReported() {
        return eucReported;
    }

    public void setEucReported(Boolean eucReported) {
        this.eucReported = eucReported;
    }

}
