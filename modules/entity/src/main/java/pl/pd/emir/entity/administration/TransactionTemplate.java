package pl.pd.emir.entity.administration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.CommodityTradeData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.Protect;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.entity.annotations.ValuationChange;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.enums.ValuationType;

@Entity
@Table(name = "TRANSACTION_TEMPLATE")
public class TransactionTemplate implements Identifiable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "commision_seq_gen")
    @SequenceGenerator(name = "commision_seq_gen", sequenceName = "SQ_EMIR_COMMISION", allocationSize = 1000)
    private Long id;

    /**
     * Status poprawnosci
     */
    @Column(name = "VALIDATION_STATUS")
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    /**
     * STATUS_TR, Status transakcji – jaki typ danych ma zostać przekazany w komunikacie [2]
     */
    @Column(name = "ORIGINAL_STATUS", length = 3)
    @Enumerated(EnumType.STRING)
    private OriginalStatus originalStatus;

    /**
     * ID_KLIENTA,
     *
     */
    @Column(name = "ORIGINAL_CLIENTID", length = 100)
    private String originalClientId;

    /**
     * STRONA_TR, Strona, po której znajduje się kontrahent: z punktu widzenia banku [4]
     */
    @Column(name = "TRANSACTION_PARTY", length = 3)
    @Enumerated(EnumType.STRING)
    private TransactionParty transactionParty;

    /**
     * POTWIERDZONA, Transakcja potwierdzona lub niepotwierdzona przez klienta [5]
     */
    @Column(name = "CONFIRMED")
    @Enumerated(EnumType.STRING)
    private ConfirmedStatus confirmed;

    /**
     * Dane kontrahenta(banku) [6-16]
     */
    @Column(name = "INITFIELD")
    private String initField;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "idCode", column
                = @Column(name = "BANK_ID_CODE")),
        @AttributeOverride(name = "idCodeType", column
                = @Column(name = "BANK_ID_CODE_TYPE")),
        @AttributeOverride(name = "memberId", column
                = @Column(name = "BANK_MEMBER_ID")),
        @AttributeOverride(name = "memberIdType", column
                = @Column(name = "BANK_MEMBER_ID_TYPE")),
        @AttributeOverride(name = "settlingAccout", column
                = @Column(name = "BANK_SETTLING_ACCOUNT")),
        @AttributeOverride(name = "beneficiaryCode", column
                = @Column(name = "BANK_BENEFICIARY_CODE")),
        @AttributeOverride(name = "beneficiaryCodeType", column
                = @Column(name = "BANK_BENEFICIARY_CODE_TYPE")),
        @AttributeOverride(name = "transactionType", column
                = @Column(name = "BANK_TRANSACTION_TYPE")),
        @AttributeOverride(name = "commercialActity", column
                = @Column(name = "BANK_COMMERCIAL_ACTIVITI")),
        @AttributeOverride(name = "settlementThreshold", column
                = @Column(name = "BANK_SETTLEMENT_THRESHOLD")),
        @AttributeOverride(name = "commWalletCode", column
                = @Column(name = "BANK_COMM_WALLET_CODE"))})
    private BusinessEntityData bankData;

    /**
     * Dane kontrahenta(klienta) [17-27]
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "idCode", column
                = @Column(name = "CLIENT_ID_CODE")),
        @AttributeOverride(name = "idCodeType", column
                = @Column(name = "CLIENT_ID_CODE_TYPE")),
        @AttributeOverride(name = "memberId", column
                = @Column(name = "CLIENT_MEMBER_ID")),
        @AttributeOverride(name = "memberIdType", column
                = @Column(name = "CLIENT_MEMBER_ID_TYPE")),
        @AttributeOverride(name = "settlingAccout", column
                = @Column(name = "CLIENT_SETTLING_ACCOUNT")),
        @AttributeOverride(name = "beneficiaryCode", column
                = @Column(name = "CLIENT_BENEFICIARY_CODE")),
        @AttributeOverride(name = "beneficiaryCodeType", column
                = @Column(name = "CLIENT_BENEFICIARY_CODE_TYPE")),
        @AttributeOverride(name = "transactionType", column
                = @Column(name = "CLIENT_TRANSACTION_TYPE")),
        @AttributeOverride(name = "commercialActity", column
                = @Column(name = "CLIENT_COMMERCIAL_ACTIVITI")),
        @AttributeOverride(name = "settlementThreshold", column
                = @Column(name = "CLIENT_SETTLEMENT_THRESHOLD")),
        @AttributeOverride(name = "commWalletCode", column
                = @Column(name = "CLIENT_COMM_WALLET_CODE"))})
    private BusinessEntityData clientData;

    /**
     * Szczegolowe dane kontraktu (4.2) [30-37]
     */
    @Embedded
    private ContractDataDetailed contractDetailedData;

    /**
     * Pozostale dane transakcji: ogolne(4.1), szczegoly(4.3), pozostale (4.9) [28-29, 38-55, 85-87]
     */
    @Embedded
    private TransactionDetails transactionDetails;

    /**
     * Ograniczenie/zglaszanie ryzyka (4.4) [56-57]
     */
    @Embedded
    private RiskReduce riskReduce;

    /**
     * Rozliczanie (4.5) [58-62]
     */
    @Embedded
    private TransactionClearing transactionClearing;

    /**
     * Stopy procentowe (4.6) [63-70]
     */
    @Embedded
    private PercentageRateData percentageRateData;

    /**
     * Transakcje walutowe (4.7) [71-74]
     */
    @Embedded
    private CurrencyTradeData currencyTradeData;

    /**
     * Transakcje towarowe (4.8) [75-84]
     *
     */
    @Embedded
    private CommodityTradeData commodityTradeData;

    /**
     * Zabezpieczenie transakcji
     */
    @Embedded
    private Protect protection;

    /**
     * Wycena transakcji
     */
    /*
     * MTMVAL, Wartość wyceny [2]
     *
     */
    @Column(name = "AMOUNT", precision = 20, scale = 5)
    @ValuationChange
    private BigDecimal amount;

    /*
     * CCY, Waluta wyceny [3]
     */
    @Column(name = "CURRENCY_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    @ValuationChange
    private CurrencyCode currencyCode;

    /*
     * VALTNDTTM, Data i godzina wyceny [4]
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUATION_DATE")
    @ValuationChange
    private Date valuationDate;

    /*
     * VALTNTP, Rodzaj wyceny [5]
     *
     */
    @Column(name = "VALUATION_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @ValuationChange
    private ValuationType valuationType;

    /*
     * MTMVAL_C, Wartość wyceny dla drugiej strony transakcji [6]
     *
     */
    @Column(name = "AMOUNT_C", precision = 20, scale = 5)
    @ValuationChange
    private BigDecimal clientAmount;

    /**
     * Data zasilenia
     */
    @Column(name = "DATE_SUPPLY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSupply;

    @Column(name = "CONFIRMATION_DATE_MAIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmationDate;

    public TransactionTemplate() {
        this(true);
    }

    public TransactionTemplate(boolean initFields) {
        if (initFields) {
            initFields();
        }
    }

    public ConfirmedStatus getConfirmed() {
        if (Objects.isNull(confirmed)) {
            confirmed = ConfirmedStatus.EMPTY;
        }
        return confirmed;
    }

    public void setConfirmed(ConfirmedStatus confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BusinessEntityData getBankData() {
        return bankData;
    }

    public void setBankData(BusinessEntityData bankData) {
        this.bankData = bankData;
    }

    public BusinessEntityData getClientData() {
        if (Objects.isNull(clientData)) {
            clientData = new BusinessEntityData();
        }
        return clientData;
    }

    public void setClientData(BusinessEntityData clientData) {
        this.clientData = clientData;
    }

    public String getOriginalClientId() {
        if (Objects.isNull(originalClientId)) {
            originalClientId = "";
        }
        return originalClientId;
    }

    public void setOriginalClientId(String originalClientId) {
        this.originalClientId = originalClientId;
    }

    public CommodityTradeData getCommodityTradeData() {
        if (Objects.isNull(commodityTradeData)) {
            commodityTradeData = new CommodityTradeData();
        }
        return commodityTradeData;
    }

    public void setCommodityTradeData(CommodityTradeData commodityTradeData) {
        this.commodityTradeData = commodityTradeData;
    }

    public CurrencyTradeData getCurrencyTradeData() {
        if (Objects.isNull(currencyTradeData)) {
            currencyTradeData = new CurrencyTradeData();
        }
        return currencyTradeData;
    }

    public void setCurrencyTradeData(CurrencyTradeData currencyTradeData) {
        this.currencyTradeData = currencyTradeData;
    }

    public PercentageRateData getPercentageRateData() {
        if (Objects.isNull(percentageRateData)) {
            percentageRateData = new PercentageRateData();
        }
        return percentageRateData;
    }

    public void setPercentageRateData(PercentageRateData percentageRateData) {
        this.percentageRateData = percentageRateData;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public TransactionParty getTransactionParty() {
        return transactionParty;
    }

    public void setTransactionParty(TransactionParty transactionParty) {
        this.transactionParty = transactionParty;
    }

    public OriginalStatus getOriginalStatus() {
        return originalStatus;
    }

    public void setOriginalStatus(OriginalStatus originalStatus) {
        this.originalStatus = originalStatus;
    }

    public Protect getProtection() {
        return protection;
    }

    public void setProtection(Protect protect) {
        this.protection = protect;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public ContractDataDetailed getContractDetailedData() {
        if (Objects.isNull(contractDetailedData)) {
            contractDetailedData = new ContractDataDetailed();
        }
        return contractDetailedData;
    }

    public void setContractDetailedData(ContractDataDetailed contractDetailedData) {
        this.contractDetailedData = contractDetailedData;
    }

    public RiskReduce getRiskReduce() {
        return riskReduce;
    }

    public void setRiskReduce(RiskReduce riskReduce) {
        this.riskReduce = riskReduce;
    }

    public TransactionClearing getTransactionClearing() {
        if (Objects.isNull(transactionClearing)) {
            transactionClearing = new TransactionClearing();
        }
        return transactionClearing;
    }

    public void setTransactionClearing(TransactionClearing transactionClearing) {
        this.transactionClearing = transactionClearing;
    }

    public Date getDateSupply() {
        return dateSupply;
    }

    public void setDateSupply(Date dateSupply) {
        this.dateSupply = dateSupply;
    }

    @Override
    public final void initFields() {
        if (this.bankData == null) {
            this.bankData = new BusinessEntityData();
        }
        if (this.clientData == null) {
            this.clientData = new BusinessEntityData();
        }
        if (this.contractDetailedData == null) {
            this.contractDetailedData = new ContractDataDetailed();
        }
        if (this.transactionDetails == null) {
            this.transactionDetails = new TransactionDetails();
        }
        if (this.riskReduce == null) {
            this.riskReduce = new RiskReduce();
        }
        if (this.transactionClearing == null) {
            this.transactionClearing = new TransactionClearing();
        }
        if (this.percentageRateData == null) {
            this.percentageRateData = new PercentageRateData();
        }
        if (this.currencyTradeData == null) {
            this.currencyTradeData = new CurrencyTradeData();
        }
        if (this.commodityTradeData == null) {
            this.commodityTradeData = new CommodityTradeData();
        }
        if (this.protection == null) {
            this.protection = new Protect();
        }
    }

    public String getInitField() {
        return initField;
    }

    public void setInitField(String initField) {
        this.initField = initField;
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

    public Date getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(Date valuationDate) {
        this.valuationDate = valuationDate;
    }

    public ValuationType getValuationType() {
        return valuationType;
    }

    public void setValuationType(ValuationType valuationType) {
        this.valuationType = valuationType;
    }

    public BigDecimal getClientAmount() {
        return clientAmount;
    }

    public void setClientAmount(BigDecimal clientAmount) {
        this.clientAmount = clientAmount;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
}
