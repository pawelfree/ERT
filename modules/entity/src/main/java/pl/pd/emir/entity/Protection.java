package pl.pd.emir.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Transient;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ProtectionChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.resources.EventLogBuilder;

@Entity
@Table(name = "PROTECTION")
@NamedQueries({
    @NamedQuery(name = "Protection.importRaport",
            query = "select p FROM Protection p, Transaction t "
            + "WHERE p.fileName = :file and t.protection = p.id "
            + "and t.transactionDate = :date"),
    @NamedQuery(name = "Protection.getProtectionByImportLog",
            query = "Select p FROM Protection p "
            + "WHERE p.importLog = :importLog ")
})
public class Protection extends Extract implements Historable<Protection>, Logable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "protection_seq_gen")
    @SequenceGenerator(name = "protection_seq_gen", sequenceName = "SQ_EMIR_PROTECTION", allocationSize = 1)
    private Long id;
    /**
     * oryginalny identyfikator transakcji, ktorej dotyczy zabezpieczenie
     */
    @Transient
    private String originalId;
    /**
     * data transakcji, ktorej dotyczy zabezpieczenie
     */
    @Transient
    private Date transactionDate;
    /*
     * COLLTN, Wskazanie, czy dokonano zabezpieczenia [2]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "PROTECTION", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private DoProtection isProtection;
    /*
     * PRTFCOLL, Wskazanie, czy dokonano zabezpieczenia na poziomie portfela. [3]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "WALLET_PROTECTION", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private YesNo walletProtection;
    /*
     * PRTFID, Kod portfela [4]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "WALLET_ID", length = 35)
    @ProtectionChange
    private String walletId;
    /*
     * COLLVAL, Wartość zabezpieczenia [5]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "AMOUNT", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal amount;
    /*
     * COLLCCY, Waluta zabezpieczenia [6]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "CURRENCY_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private CurrencyCode currencyCode;
    /*
     * COLLVAL_C, Wartość zabezpieczenia dla drugiej strony transackji [7]
     */
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "AMOUNT_C", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal clientAmount;
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

    public Protection() {
        super();
        initFields();
    }

    public Protection(String originalId, Date transactionDate, DoProtection commision, YesNo walletProtection, String walletId, BigDecimal amount, CurrencyCode currencyCode, BigDecimal clientAmount) {
        super();
        this.originalId = originalId;
        this.transactionDate = transactionDate;
        this.isProtection = commision;
        this.walletProtection = walletProtection;
        this.walletId = walletId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.clientAmount = clientAmount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public DoProtection getProtection() {
        return isProtection;
    }

    public void setProtection(DoProtection protection) {
        this.isProtection = protection;
    }

    public YesNo getWalletProtection() {
        return walletProtection;
    }

    public void setWalletProtection(YesNo walletProtection) {
        this.walletProtection = walletProtection;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getClientAmount() {
        return clientAmount;
    }

    public void setClientAmount(BigDecimal clientAmount) {
        this.clientAmount = clientAmount;
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
        return "ZABEZ_E";
    }

    @Override
    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, Protection oldEntity, Protection newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getProtection(), EventLogBuilder.EventDetailsKey.PROTECTION, changeComment);
            checkFieldsEquals(result, null, newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION, changeComment);
            checkFieldsEquals(result, null, newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID, changeComment);
            checkFieldsEquals(result, null, newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, null, newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getProtection(), null, EventLogBuilder.EventDetailsKey.PROTECTION, changeComment);
            checkFieldsEquals(result, oldEntity.getWalletProtection(), null, EventLogBuilder.EventDetailsKey.WALLET_PROTECTION, changeComment);
            checkFieldsEquals(result, oldEntity.getWalletId(), null, EventLogBuilder.EventDetailsKey.WALLET_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), null, EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getClientAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getProtection(), newEntity.getProtection(), EventLogBuilder.EventDetailsKey.PROTECTION, changeComment);
            checkFieldsEquals(result, oldEntity.getWalletProtection(), newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION, changeComment);
            checkFieldsEquals(result, oldEntity.getWalletId(), newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getAmount(), newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getClientAmount(), newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        }
    }

    public Protection fullClone() {
        return new Protection(originalId, transactionDate, isProtection, walletProtection, walletId, amount, currencyCode, clientAmount);
    }

    @Override
    public EventType getDeleteEventType() {
        return EventType.PROTECTION_DELETE;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.PROTECTION_INSERT;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.PROTECTION_MODIFICATION;
    }

    @Override
    public List<ChangeLog> getChangeLogs(Protection newEntity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEmpty() {
        return StringUtil.isEmpty(originalId)
                && Objects.isNull(transactionDate)
                && Objects.isNull(isProtection)
                && Objects.isNull(walletProtection)
                && StringUtil.isEmpty(walletId)
                && Objects.isNull(amount)
                && Objects.isNull(currencyCode)
                && Objects.isNull(clientAmount)
                && Objects.isNull(extractVersion)
                && Objects.isNull(fileName);
    }
}
