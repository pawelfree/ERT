package pl.pd.emir.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.BankDataChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.enums.BankStatus;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.resources.EventLogBuilder.EventDetailsKey;
import static pl.pd.emir.resources.EventLogBuilder.fieldsNotEquals;
import static pl.pd.emir.resources.EventLogBuilder.getChangeLogData;

@NamedQueries({
    @NamedQuery(name = "Bank.getActive", query = "SELECT b FROM Bank b WHERE b.active = true"),
    @NamedQuery(name = "Bank.getAll", query = "SELECT b FROM Bank b ORDER BY b.id"),
    @NamedQuery(name = "Bank.getByBankNr", query = "SELECT b FROM Bank b WHERE b.bankNr = :bankNr ORDER BY b.id"),
    @NamedQuery(name = "Bank.getHistryBankByDate", query = "SELECT b FROM Bank b WHERE b.modificationDate = (SELECT max(b2.modificationDate) from Bank b2 WHERE b2.modificationDate < :activeDate)")
})
@Entity
@Table(name = "BANK")
public class Bank extends Extract implements Historable<Bank>, Logable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bank_seq_gen")
    @SequenceGenerator(name = "bank_seq_gen", sequenceName = "SQ_EMIR_BANK", allocationSize = 1000)
    private Long id;

    /**
     * NR_BANKU, nr rozliczeniowy banku/instytucji [0]
     */
    @Column(name = "BANK_NR", length = 8)
    private String bankNr;

    /*
     * NAZWA_BANKU, Nazwa banku/instytucji [1]
     */
    @Column(name = "BANK_NAME", length = 100)
    private String bankName;

    /*
     * NAZWA_BANKU_KOMUNIKAT, Nazwa banku przekazywana w komunikacie [2]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Column(name = "XML_BANK_NAME", length = 100, nullable = true)
    private String xmlBankName;

    /*
     * SENDER_ID, Identyfikator banku / instytucji  dla raportowania transakcji do ESMA [3]
     */
    @Column(name = "SENDER_ID", length = 4)
    private String senderId;

    /*
     * SENDER_ID_KDPW, Identyfikator banku / instytucji nadany przez KDPW_TR [4]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Column(name = "SENDER_ID_KDPW", length = 4, nullable = false)
    private String senderIdKdpw;

    /*
     * COUNTRY, Kod kraju (siedziby banku) [5]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "COUNTRY_CODE", length = 2, nullable = true)
    private CountryCode countryCode;

    /*
     * Dane podmiotu raportujacego:
     * TRRPRTID_PMRYID,   Wewnetrzny identyfikator podmiotu raportujacego: NIP
     * TRRPRTID_SCNDRYID, Wewnetrzny identyfikator podmiotu raportujacego: REGON
     * [6-7]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Embedded
    private BusinessEntity businessEntity;

    /*
     * Dane instytucji (identyfikacja, dane adresowe) [8-15]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Embedded
    @Column(name = "institution", nullable = false)
    private Institution institution;

    /*
     * CORPSCTR, Branża, do której należy kontrahent [16]
     */
    @Column(name = "CONTR_PARTY_INDUSTRY", length = 1)
    @BankDataChange
    private String contrPartyIndustry;

    /*
     * FINNONFIN_ID, Flaga: Kontrahent finansowy / niefinansowy z nieznaną dziedziną wartości [17]
     */
    @ValidateCompleteness(subjectClass = Bank.class)
    @Column(name = "CONTR_PARTY_TYPE", length = 1)
    @BankDataChange
    private String contrPartyType;

    @Column(name = "IS_ACTIVE", length = 1, nullable = false)
    private boolean active;

    /**
     * Nazwa pliku, z którego wczytano ekstrakt.
     */
    @Column(name = "FILE_NAME", length = 512, nullable = true)
    private String fileName;

    /**
     * Data raportowania wyceny.
     */
    @Column(name = "VALUATION_REPORTING_DATE")
    @Temporal(TemporalType.DATE)
    private Date valuationReportingDate;

    /*
     * COUNTRY, Kod kraju (siedziby banku) [5]
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "BANK_STATUS", length = 9, nullable = true)
    private BankStatus bankStatus;

    /**
     * Data modyfikacji/dodania Banku
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Transient
    private transient String changeComment;

    public Bank() {
        super();
    }

    public Bank(String bankNr, String bankName, String xmlBankName, String senderId, String senderIdKdpw,
            CountryCode country, BusinessEntity businessEntity, Institution institution, String contrPartyIndustry,
            String contrPartyType) {
        super();
        this.bankNr = bankNr;
        this.bankName = bankName;
        this.xmlBankName = xmlBankName;
        this.senderId = senderId;
        this.senderIdKdpw = senderIdKdpw;
        this.countryCode = country;
        this.businessEntity = businessEntity;
        this.institution = institution;
        this.contrPartyIndustry = contrPartyIndustry;
        this.contrPartyType = contrPartyType;
        this.active = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNr() {
        return bankNr;
    }

    public void setBankNr(String bankNr) {
        this.bankNr = bankNr;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getContrPartyIndustry() {
        return contrPartyIndustry;
    }

    public void setContrPartyIndustry(String contrPartyIndustry) {
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

    public void setCountryCode(CountryCode country) {
        this.countryCode = country;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderIdKdpw() {
        return senderIdKdpw;
    }

    public void setSenderIdKdpw(String senderIdKdpw) {
        this.senderIdKdpw = senderIdKdpw;
    }

    public String getXmlBankName() {
        return xmlBankName;
    }

    public void setXmlBankName(String xmlBankName) {
        this.xmlBankName = xmlBankName;
    }

    @Override
    public String getExtractName() {
        return "BANK_E";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BankStatus getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(BankStatus bankStatus) {
        this.bankStatus = bankStatus;
    }

    @Override
    public void initFields() {
        this.businessEntity = new BusinessEntity();
        this.businessEntity.initFields();
        this.institution = new Institution();
        this.institution.initFields();
    }

    @Override
    public List<ChangeLog> getChangeLogs(Bank newBank) {
        List<ChangeLog> result = new ArrayList<>();

        if (fieldsNotEquals(bankNr, newBank.bankNr)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.BANK_NR, bankNr, newBank.bankNr, newBank.changeComment)));
        }
        if (fieldsNotEquals(bankName, newBank.bankName)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.BANK_NAME, bankName, newBank.bankName, newBank.changeComment)));
        }
        if (fieldsNotEquals(xmlBankName, newBank.xmlBankName)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.XML_BANK_NAME, xmlBankName,
                    newBank.xmlBankName, newBank.changeComment)));
        }
        if (fieldsNotEquals(senderId, newBank.senderId)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.SENDER_ID, senderId, newBank.senderId, newBank.changeComment)));
        }
        if (fieldsNotEquals(senderIdKdpw, newBank.senderIdKdpw)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.SENDER_ID_KDPW, senderIdKdpw,
                    newBank.senderIdKdpw, newBank.changeComment)));
        }
        if (fieldsNotEquals(countryCode, newBank.countryCode)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.COUNTRY_CODE, countryCode, newBank.countryCode, newBank.changeComment)));
        }
        if (fieldsNotEquals(contrPartyIndustry, newBank.contrPartyIndustry)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.CONTR_PARTY_INDUSTRY, contrPartyIndustry,
                    newBank.contrPartyIndustry, newBank.changeComment)));
        }
        if (fieldsNotEquals(contrPartyType, newBank.contrPartyType)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.CONTR_PARTY_TYPE, contrPartyType,
                    newBank.contrPartyType, newBank.changeComment)));
        }
        if (fieldsNotEquals(valuationReportingDate, newBank.valuationReportingDate)) {
            result.add(new ChangeLog(getChangeLogData(EventDetailsKey.VALUATION_RAPORTING_DATE, valuationReportingDate,
                    newBank.valuationReportingDate, newBank.changeComment)));
        }

        BusinessEntity.checkEntity(result, businessEntity, newBank.businessEntity, newBank.changeComment);

        Institution.checkEntity(result, institution, newBank.institution, newBank.changeComment);

        return result;
    }

    @Override
    public EventType getDeleteEventType() {
        return null;
    }

    @Override
    public EventType getInsertEventType() {
        return null;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.BANK_MODIFICATION;
    }

    @Override
    public Integer getExtractVersion() {
        //nie dotyczy
        return null;
    }

    //
    //    public Boolean getValuationReporting() {
    //    }
    //    }
    public Date getValuationReportingDate() {
        return valuationReportingDate;
    }

    public void setValuationReportingDate(Date valuationReportingDate) {
        this.valuationReportingDate = valuationReportingDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getChangeComment() {
        return changeComment;
    }

    public void setChangeComment(String changeComment) {
        this.changeComment = changeComment;
    }
}
