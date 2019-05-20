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
    
    //new collateral values 1.11.2017
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "IMP", precision = 25, scale = 5)
    @ProtectionChange    
    private BigDecimal initlMrgnPstd;
    
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "IMR", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal initlMrgnRcvd;
    
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "VMP", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal vartnMrgnPstd;
    
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "VMR", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal vartnMrgnRcvd;
    
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "XCP", precision = 25, scale = 5)
    @ProtectionChange    
    private BigDecimal xcssCollPstd;
    
    @ValidateCompleteness(subjectClass = Protection.class)
    @Column(name = "XCR", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal xcssCollRcvd;    
    
    //end new collateral valuse 1.11.2017  
    
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

    public Protection(String originalId, Date transactionDate, DoProtection commision, YesNo walletProtection, 
            String walletId, BigDecimal amount, CurrencyCode currencyCode, BigDecimal clientAmount,
            BigDecimal initlMrgnPstd, BigDecimal initlMrgnRcvd, 
            BigDecimal vartnMrgnPstd, BigDecimal vartnMrgnRcvd,
            BigDecimal xcssCollPstd , BigDecimal xcssCollRcvd) {
        super();
        this.originalId = originalId;
        this.transactionDate = transactionDate;
        this.isProtection = commision;
        this.walletProtection = walletProtection;
        this.walletId = walletId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.clientAmount = clientAmount;
        this.initlMrgnPstd = initlMrgnPstd;
        this.initlMrgnRcvd = initlMrgnRcvd;
        this.xcssCollPstd = xcssCollPstd;
        this.xcssCollRcvd = xcssCollRcvd;
        this.vartnMrgnPstd = vartnMrgnPstd;
        this.vartnMrgnRcvd = vartnMrgnRcvd;
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

    public static void checkEntity(List<ChangeLog> result, Protection oldEntity, Protection newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getProtection(), EventLogBuilder.EventDetailsKey.PROTECTION);
            checkFieldsEquals(result, null, newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
            checkFieldsEquals(result, null, newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID);
            checkFieldsEquals(result, null, newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT);
            checkFieldsEquals(result, null, newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
            checkFieldsEquals(result, null, newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);          
            checkFieldsEquals(result, null, newEntity.getInitlMrgnPstd(), EventLogBuilder.EventDetailsKey.INITLMRGNPSTD);
            checkFieldsEquals(result, null, newEntity.getInitlMrgnRcvd(), EventLogBuilder.EventDetailsKey.INITLMRGNRCVD);
            checkFieldsEquals(result, null, newEntity.getVartnMrgnPstd(), EventLogBuilder.EventDetailsKey.VARTNMRGNPSTD);
            checkFieldsEquals(result, null, newEntity.getVartnMrgnRcvd(), EventLogBuilder.EventDetailsKey.VARTNMRGNRCVD);
            checkFieldsEquals(result, null, newEntity.getXcssCollPstd(), EventLogBuilder.EventDetailsKey.XCSSCOLLPSTD);
            checkFieldsEquals(result, null, newEntity.getXcssCollRcvd(), EventLogBuilder.EventDetailsKey.XCSSCOLLRCVD);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getProtection(), null, EventLogBuilder.EventDetailsKey.PROTECTION);
            checkFieldsEquals(result, oldEntity.getWalletProtection(), null, EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
            checkFieldsEquals(result, oldEntity.getWalletId(), null, EventLogBuilder.EventDetailsKey.WALLET_ID);
            checkFieldsEquals(result, oldEntity.getAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), null, EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
            checkFieldsEquals(result, oldEntity.getClientAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);
            checkFieldsEquals(result, oldEntity.getInitlMrgnPstd(), null, EventLogBuilder.EventDetailsKey.INITLMRGNPSTD);
            checkFieldsEquals(result, oldEntity.getInitlMrgnRcvd(), null, EventLogBuilder.EventDetailsKey.INITLMRGNRCVD);
            checkFieldsEquals(result, oldEntity.getVartnMrgnPstd(), null, EventLogBuilder.EventDetailsKey.VARTNMRGNPSTD);
            checkFieldsEquals(result, oldEntity.getVartnMrgnRcvd(), null, EventLogBuilder.EventDetailsKey.VARTNMRGNRCVD);
            checkFieldsEquals(result, oldEntity.getXcssCollPstd(), null, EventLogBuilder.EventDetailsKey.XCSSCOLLPSTD);
            checkFieldsEquals(result, oldEntity.getXcssCollRcvd(), null, EventLogBuilder.EventDetailsKey.XCSSCOLLPSTD);            
        } else {
            checkFieldsEquals(result, oldEntity.getProtection(), newEntity.getProtection(), EventLogBuilder.EventDetailsKey.PROTECTION);
            checkFieldsEquals(result, oldEntity.getWalletProtection(), newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
            checkFieldsEquals(result, oldEntity.getWalletId(), newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID);
            checkFieldsEquals(result, oldEntity.getAmount(), newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
            checkFieldsEquals(result, oldEntity.getClientAmount(), newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);
            checkFieldsEquals(result, oldEntity.getInitlMrgnPstd(), newEntity.getInitlMrgnPstd(), EventLogBuilder.EventDetailsKey.INITLMRGNPSTD);
            checkFieldsEquals(result, oldEntity.getInitlMrgnRcvd(), newEntity.getInitlMrgnRcvd(), EventLogBuilder.EventDetailsKey.INITLMRGNRCVD);
            checkFieldsEquals(result, oldEntity.getVartnMrgnPstd(), newEntity.getVartnMrgnPstd(), EventLogBuilder.EventDetailsKey.VARTNMRGNPSTD);
            checkFieldsEquals(result, oldEntity.getVartnMrgnRcvd(), newEntity.getVartnMrgnRcvd(), EventLogBuilder.EventDetailsKey.VARTNMRGNRCVD);
            checkFieldsEquals(result, oldEntity.getXcssCollPstd(), newEntity.getXcssCollPstd(), EventLogBuilder.EventDetailsKey.XCSSCOLLPSTD);
            checkFieldsEquals(result, oldEntity.getXcssCollRcvd(), newEntity.getXcssCollRcvd(), EventLogBuilder.EventDetailsKey.XCSSCOLLPSTD);            
        }
    }

    public Protection fullClone() {
        return new Protection(originalId, transactionDate, isProtection, walletProtection, walletId, amount,
                currencyCode, clientAmount, initlMrgnPstd, initlMrgnRcvd, vartnMrgnPstd, vartnMrgnRcvd,
                xcssCollPstd, xcssCollRcvd);
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
                && Objects.isNull(fileName)
                && Objects.isNull(initlMrgnPstd)
                && Objects.isNull(initlMrgnRcvd)
                && Objects.isNull(vartnMrgnPstd)
                && Objects.isNull(vartnMrgnRcvd)
                && Objects.isNull(xcssCollPstd)
                && Objects.isNull(xcssCollRcvd);
    }

    public BigDecimal getInitlMrgnPstd() {
        if (null == initlMrgnPstd) return BigDecimal.ZERO;
        return initlMrgnPstd;
    }

    public void setInitlMrgnPstd(BigDecimal initlMrgnPstd) {
        this.initlMrgnPstd = initlMrgnPstd;
    }

    public BigDecimal getInitlMrgnRcvd() {
        if (null == initlMrgnRcvd) return BigDecimal.ZERO;
        return initlMrgnRcvd;
    }

    public void setInitlMrgnRcvd(BigDecimal initlMrgnRcvd) {
        this.initlMrgnRcvd = initlMrgnRcvd;
    }

    public BigDecimal getVartnMrgnPstd() {
        if (null == vartnMrgnPstd) return BigDecimal.ZERO;
        return vartnMrgnPstd;
    }

    public void setVartnMrgnPstd(BigDecimal vartnMrgnPstd) {
        this.vartnMrgnPstd = vartnMrgnPstd;
    }

    public BigDecimal getVartnMrgnRcvd() {
        if (null == vartnMrgnRcvd) return BigDecimal.ZERO;        
        return vartnMrgnRcvd;
    }

    public void setVartnMrgnRcvd(BigDecimal vartnMrgnRcvd) {
        this.vartnMrgnRcvd = vartnMrgnRcvd;
    }

    public BigDecimal getXcssCollPstd() {
        if (null == xcssCollPstd) return BigDecimal.ZERO;
        return xcssCollPstd;
    }

    public void setXcssCollPstd(BigDecimal xcssCollPstd) {
        this.xcssCollPstd = xcssCollPstd;
    }

    public BigDecimal getXcssCollRcvd() {
        if (null == xcssCollRcvd) return BigDecimal.ZERO;        
        return xcssCollRcvd;
    }

    public void setXcssCollRcvd(BigDecimal xcssCollRcvd) {
        this.xcssCollRcvd = xcssCollRcvd;
    }
}
