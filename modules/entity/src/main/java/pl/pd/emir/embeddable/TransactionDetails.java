package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.BaseDataChange;
import pl.pd.emir.entity.annotations.ContractDataChange;
import pl.pd.emir.entity.annotations.DerivativesChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.Compression;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DeliverType;
import pl.pd.emir.enums.OptionExecStyle;
import pl.pd.emir.enums.OptionType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class TransactionDetails implements Serializable {

    //Dane o transakcji (4.1)
    /**
     * TRADID_ID, Kod identyfikacji transakcji [28]
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "SOURCE_TRANS_ID", length = 52)
    private String sourceTransId;
    /**
     * PREV_TRADID_ID, Poprzedni kod identyfikacji transakcji [29]
     */
    @Column(name = "PREV_SOURCE_TRANS_ID", length = 52)
    @BaseDataChange
    private String previousSourceTransId;
    /**
     * TRADID_TRADREFNB, Numer referencyjny transakcji – niepowtarzalny numer transakcji nadawany przez podmiot [30]
     * raporcontractCounttujący.
     */
    @Column(name = "SOURCE_TRANS_REF_NR", length = 40)
    @BaseDataChange
    private String sourceTransRefNr;
    //Szczegoly transakcji 4.3
    /**
     * TRADADDTLINF_VENUEOFEXC, Miejsce realizacji transakcji - jest określane za pomocą niepowtarzalnego kodu tego
     * miejsca. [39]
     */
    @Column(name = "REALIZATION_VENUE", length = 4)
    private String realizationVenue;
    /**
     * TRADADDTLINF_CMPRSSN, Kompresja – wskazanie czy kontrakt jest wynikiem kompresji [40]
     */
    @Column(name = "COMPRESSION", length = 3)
    @Enumerated(EnumType.STRING)
    private Compression compression;
    /**
     * TRANSAKCJE_E.TRADADDTLINF_AMT, Cena jednostkowa instrumentu pochodnego [41]
     */
    @ValidateCompleteness(subjectClass = Transaction.class, orGroup = "transactionDetailsGroup")
    @Column(name = "UNIT_PRICE", precision = 25, scale = 5)
    @ContractDataChange
    private BigDecimal unitPrice;
    /**
     * TRADADDTLINF_AMT_WAL, Waluta instrumentu pochodnego. [42]
     */
    @ValidateCompleteness(subjectClass = Transaction.class, orGroup = "transactionDetailsGroup")
    @Column(name = "UNIT_PRICE_CURRENCY", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private CurrencyCode unitPriceCurrency;
    /*
     * TRADADDTLINF_PRCNTG, Stawka procentowa instrumentu pochodnego. [43]
     */
    @ValidateCompleteness(subjectClass = Transaction.class, orGroup = "transactionDetailsGroup")
    @Column(name = "UNIT_PERCENTAGE_RATE", precision = 25, scale = 7)
    @ContractDataChange
    private BigDecimal unitPriceRate;
    /**
     * TRADADDTLINF_NMNLAMT, Kwota nominalna – pierwotna wartość kontraktu. [44]
     */
    @Column(name = "NOMINAL_AMOUNT", precision = 22, scale = 2)
    @ContractDataChange
    private BigDecimal nominalAmount;
    /**
     * TRADADDTLINF_PRICMLTPLR, Mnożnik ceny. [45]
     */
    @Column(name = "PRICE_MULTIPLIER")
    @ContractDataChange
    private Integer priceMultiplier;
    /**
     * TRADADDTLINF_QTY, Ilość. [46]
     */
    @Column(name = "CONCTRACT_COUNT")
    @ContractDataChange
    private Integer contractCount;
    /**
     * TRADADDTLINF_UPPMT, Płatność z góry. [47]
     */
    @Column(name = "IN_ADVANCE_PAYMENT_AMOUNT", precision = 12, scale = 2)
    @ContractDataChange
    private BigDecimal inAdvanceAmount;
    /**
     * TRADADDTLINF_DLVRYTP, Typ dostawy. [48]
     */
    @Column(name = "DELIV_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private DeliverType delivType;
    /**
     * TRADADDTLINF_EXECDTTM, Data i czas realizacji transakcji. [49]
     */
    @Column(name = "EXECUTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionDate;
    /**
     * TRADADDTLINF_FCTYDT, Data wejścia w życie obowiązków wynikających z kontraktu. [50]
     */
    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @ContractDataChange
    private Date effectiveDate;
    /**
     * TRADADDTLINF_MTRTYDT, Termin zapadalności. [51]
     */
    @Column(name = "MATURITY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maturityDate;
    /**
     * TRADADDTLINF_TRMNTNDT, Data rozwiązania zgłaszanego kontraktu. [52]
     */
    @Column(name = "TERMINATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date terminationDate;
    /**
     * TRADADDTLINF_STTLMTDT, Data rozrachunku instrumentu bazowego. [53]
     */
    @Column(name = "SETTLEMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @ContractDataChange
    private Date settlementDate;
    /**
     * TRADADDTLINF_MSTRAGRMNTTP, Rodzaj umowy ramowej. [54]
     */
    @Column(name = "FRAMEWORK_AGGR_TYPE", length = 50)
    @ContractDataChange
    private String frameworkAggrType;
    /**
     * TRADADDTLINF_MSTRAGRMNTVRSN, Wersja umowy ramowej. [55]
     */
    @Column(name = "FRAMEWORK_AGGR_VER")
    @ContractDataChange
    private Integer frameworkAggrVer;
    //Pozostale wlasciwosci (4.9)
    /**
     * OPTNTRAD_OPTNTP, Rodzaj opcji. [85]
     *
     */
    @Column(name = "OPTION_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private OptionType optionType;
    /**
     * OPTNTRAD_EXRCSTYLE, Rodzaj opcji (sposób wykonania). [86]
     *
     */
    @Column(name = "OPTION_EXEX_STYLE", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private OptionExecStyle optionExecStyle;
    /**
     * OPTNTRAD_STRKPRIC, Cena wykonania (górny/dolny pułap). [87]
     *
     */
    @Column(name = "OPTION_EXEC_PRICE", precision = 12, scale = 2)
    @DerivativesChange
    private BigDecimal optionExecPrice;

    public TransactionDetails() {
        super();
    }

    public TransactionDetails(String sourceTransId, String previousSourceTransId, String sourceTransRefNr, String realizationVenue,
            Compression compression, BigDecimal unitPrice, CurrencyCode unitPriceCurrency, BigDecimal unitPriceRate,
            BigDecimal nominalAmount, Integer priceMultiplier, Integer contractCount, BigDecimal inAdvanceAmount,
            DeliverType delivType, Date executionDate, Date effectiveDate, Date maturityDate, Date terminationDate,
            Date settlementDate, String frameworkAggrType, Integer frameworkAggrVer, OptionType optionType,
            OptionExecStyle optionExecStyle, BigDecimal optionExecPrice) {
        super();
        this.sourceTransId = sourceTransId;
        this.previousSourceTransId = previousSourceTransId;
        this.sourceTransRefNr = sourceTransRefNr;
        this.realizationVenue = realizationVenue;
        this.compression = compression;
        this.unitPrice = unitPrice;
        this.unitPriceCurrency = unitPriceCurrency;
        this.unitPriceRate = unitPriceRate;
        this.nominalAmount = nominalAmount;
        this.priceMultiplier = priceMultiplier;
        this.contractCount = contractCount;
        this.inAdvanceAmount = inAdvanceAmount;
        this.delivType = delivType;
        this.executionDate = executionDate;
        this.effectiveDate = effectiveDate;
        this.maturityDate = maturityDate;
        this.terminationDate = terminationDate;
        this.settlementDate = settlementDate;
        this.frameworkAggrType = frameworkAggrType;
        this.frameworkAggrVer = frameworkAggrVer;
        this.optionType = optionType;
        this.optionExecStyle = optionExecStyle;
        this.optionExecPrice = optionExecPrice;
    }

    public Compression getCompression() {
        return compression;
    }

    public void setCompression(Compression compression) {
        this.compression = compression;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public DeliverType getDelivType() {
        return delivType;
    }

    public void setDelivType(DeliverType delivType) {
        this.delivType = delivType;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getFrameworkAggrType() {
        return frameworkAggrType;
    }

    public void setFrameworkAggrType(String value) {
        this.frameworkAggrType = StringUtil.getNullOnEmpty(value);
    }

    public Integer getFrameworkAggrVer() {
        return frameworkAggrVer;
    }

    public void setFrameworkAggrVer(Integer frameworkAggrVer) {
        this.frameworkAggrVer = frameworkAggrVer;
    }

    public BigDecimal getInAdvanceAmount() {
        return inAdvanceAmount;
    }

    public void setInAdvanceAmount(BigDecimal inAdvanceAmount) {
        this.inAdvanceAmount = inAdvanceAmount;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public BigDecimal getNominalAmount() {
        return nominalAmount;
    }

    public void setNominalAmount(BigDecimal nominalAmount) {
        this.nominalAmount = nominalAmount;
    }

    public Integer getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(Integer priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public String getRealizationVenue() {
        return realizationVenue;
    }

    public void setRealizationVenue(String value) {
        this.realizationVenue = StringUtil.getNullOnEmpty(value);
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSourceTransId() {
        return sourceTransId;
    }

    public void setSourceTransId(String sourceTransId) {
        this.sourceTransId = sourceTransId;
    }

    public String getPreviousSourceTransId() {
        return previousSourceTransId;
    }

    public void setPreviousSourceTransId(String previousSourceTransId) {
        this.previousSourceTransId = previousSourceTransId;
    }

    public String getSourceTransRefNr() {
        return sourceTransRefNr;
    }

    public void setSourceTransRefNr(String sourceTransRefNr) {
        this.sourceTransRefNr = sourceTransRefNr;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public CurrencyCode getUnitPriceCurrency() {
        return unitPriceCurrency;
    }

    public void setUnitPriceCurrency(CurrencyCode unitPriceCurrency) {
        this.unitPriceCurrency = unitPriceCurrency;
    }

    public BigDecimal getUnitPriceRate() {
        return unitPriceRate;
    }

    public void setUnitPriceRate(BigDecimal unitPriceRate) {
        this.unitPriceRate = unitPriceRate;
    }

    public BigDecimal getOptionExecPrice() {
        return optionExecPrice;
    }

    public void setOptionExecPrice(BigDecimal optionExecPrice) {
        this.optionExecPrice = optionExecPrice;
    }

    public OptionExecStyle getOptionExecStyle() {
        return optionExecStyle;
    }

    public void setOptionExecStyle(OptionExecStyle optionExecStyle) {
        this.optionExecStyle = optionExecStyle;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, TransactionDetails oldEntity, TransactionDetails newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }

        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getSourceTransId(), EventLogBuilder.EventDetailsKey.SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, null, newEntity.getPreviousSourceTransId(), EventLogBuilder.EventDetailsKey.PREVIOUS_SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, null, newEntity.getSourceTransRefNr(), EventLogBuilder.EventDetailsKey.SOURCE_TRANS_REF_NR, changeComment);
            checkFieldsEquals(result, null, newEntity.getRealizationVenue(), EventLogBuilder.EventDetailsKey.REALIZATION_VENUE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCompression(), EventLogBuilder.EventDetailsKey.COMPRESSION, changeComment);
            checkFieldsEquals(result, null, newEntity.getUnitPrice(), EventLogBuilder.EventDetailsKey.UNIT_PRICE, changeComment);
            checkFieldsEquals(result, null, newEntity.getUnitPriceRate(), EventLogBuilder.EventDetailsKey.UNIT_PRICE_RATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getNominalAmount(), EventLogBuilder.EventDetailsKey.NOMINAL_AMOUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getPriceMultiplier(), EventLogBuilder.EventDetailsKey.PRICE_MULTIPLIER, changeComment);
            checkFieldsEquals(result, null, newEntity.getContractCount(), EventLogBuilder.EventDetailsKey.CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getInAdvanceAmount(), EventLogBuilder.EventDetailsKey.IN_ADVANCE_AMOUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getDelivType(), EventLogBuilder.EventDetailsKey.DELIV_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getExecutionDate(), EventLogBuilder.EventDetailsKey.EXECUTION_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getEffectiveDate(), EventLogBuilder.EventDetailsKey.EFFECTIVE_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getMaturityDate(), EventLogBuilder.EventDetailsKey.MATURITY_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getTerminationDate(), EventLogBuilder.EventDetailsKey.TERMINATION_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getSettlementDate(), EventLogBuilder.EventDetailsKey.SETTLEMENT_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getFrameworkAggrType(), EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getFrameworkAggrVer(), EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_VER, changeComment);
            checkFieldsEquals(result, null, newEntity.getOptionType(), EventLogBuilder.EventDetailsKey.OPTION_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getOptionExecStyle(), EventLogBuilder.EventDetailsKey.OPTION_EXEC_STYLE, changeComment);
            checkFieldsEquals(result, null, newEntity.getOptionExecPrice(), EventLogBuilder.EventDetailsKey.OPTION_EXEC_PRICE, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getSourceTransId(), null, EventLogBuilder.EventDetailsKey.SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getPreviousSourceTransId(), null, EventLogBuilder.EventDetailsKey.PREVIOUS_SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getSourceTransRefNr(), null, EventLogBuilder.EventDetailsKey.SOURCE_TRANS_REF_NR, changeComment);
            checkFieldsEquals(result, oldEntity.getRealizationVenue(), null, EventLogBuilder.EventDetailsKey.REALIZATION_VENUE, changeComment);
            checkFieldsEquals(result, oldEntity.getCompression(), null, EventLogBuilder.EventDetailsKey.COMPRESSION, changeComment);
            checkFieldsEquals(result, oldEntity.getUnitPrice(), null, EventLogBuilder.EventDetailsKey.UNIT_PRICE, changeComment);
            checkFieldsEquals(result, oldEntity.getUnitPriceRate(), null, EventLogBuilder.EventDetailsKey.UNIT_PRICE_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getNominalAmount(), null, EventLogBuilder.EventDetailsKey.NOMINAL_AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getPriceMultiplier(), null, EventLogBuilder.EventDetailsKey.PRICE_MULTIPLIER, changeComment);
            checkFieldsEquals(result, oldEntity.getContractCount(), null, EventLogBuilder.EventDetailsKey.CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getInAdvanceAmount(), null, EventLogBuilder.EventDetailsKey.IN_ADVANCE_AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getDelivType(), null, EventLogBuilder.EventDetailsKey.DELIV_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getExecutionDate(), null, EventLogBuilder.EventDetailsKey.EXECUTION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getEffectiveDate(), null, EventLogBuilder.EventDetailsKey.EFFECTIVE_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getMaturityDate(), null, EventLogBuilder.EventDetailsKey.MATURITY_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getTerminationDate(), null, EventLogBuilder.EventDetailsKey.TERMINATION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getSettlementDate(), null, EventLogBuilder.EventDetailsKey.SETTLEMENT_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getFrameworkAggrType(), null, EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getFrameworkAggrVer(), null, EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_VER, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionType(), null, EventLogBuilder.EventDetailsKey.OPTION_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionExecStyle(), null, EventLogBuilder.EventDetailsKey.OPTION_EXEC_STYLE, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionExecPrice(), null, EventLogBuilder.EventDetailsKey.OPTION_EXEC_PRICE, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getSourceTransId(), newEntity.getSourceTransId(), EventLogBuilder.EventDetailsKey.SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getPreviousSourceTransId(), newEntity.getPreviousSourceTransId(), EventLogBuilder.EventDetailsKey.PREVIOUS_SOURCE_TRANS_ID, changeComment);
            checkFieldsEquals(result, oldEntity.getSourceTransRefNr(), newEntity.getSourceTransRefNr(), EventLogBuilder.EventDetailsKey.SOURCE_TRANS_REF_NR, changeComment);
            checkFieldsEquals(result, oldEntity.getRealizationVenue(), newEntity.getRealizationVenue(), EventLogBuilder.EventDetailsKey.REALIZATION_VENUE, changeComment);
            checkFieldsEquals(result, oldEntity.getCompression(), newEntity.getCompression(), EventLogBuilder.EventDetailsKey.COMPRESSION, changeComment);
            checkFieldsEquals(result, oldEntity.getUnitPrice(), newEntity.getUnitPrice(), EventLogBuilder.EventDetailsKey.UNIT_PRICE, changeComment);
            checkFieldsEquals(result, oldEntity.getUnitPriceRate(), newEntity.getUnitPriceRate(), EventLogBuilder.EventDetailsKey.UNIT_PRICE_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getNominalAmount(), newEntity.getNominalAmount(), EventLogBuilder.EventDetailsKey.NOMINAL_AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getPriceMultiplier(), newEntity.getPriceMultiplier(), EventLogBuilder.EventDetailsKey.PRICE_MULTIPLIER, changeComment);
            checkFieldsEquals(result, oldEntity.getContractCount(), newEntity.getContractCount(), EventLogBuilder.EventDetailsKey.CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getInAdvanceAmount(), newEntity.getInAdvanceAmount(), EventLogBuilder.EventDetailsKey.IN_ADVANCE_AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getDelivType(), newEntity.getDelivType(), EventLogBuilder.EventDetailsKey.DELIV_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getExecutionDate(), newEntity.getExecutionDate(), EventLogBuilder.EventDetailsKey.EXECUTION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getEffectiveDate(), newEntity.getEffectiveDate(), EventLogBuilder.EventDetailsKey.EFFECTIVE_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getMaturityDate(), newEntity.getMaturityDate(), EventLogBuilder.EventDetailsKey.MATURITY_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getTerminationDate(), newEntity.getTerminationDate(), EventLogBuilder.EventDetailsKey.TERMINATION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getSettlementDate(), newEntity.getSettlementDate(), EventLogBuilder.EventDetailsKey.SETTLEMENT_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getFrameworkAggrType(), newEntity.getFrameworkAggrType(), EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getFrameworkAggrVer(), newEntity.getFrameworkAggrVer(), EventLogBuilder.EventDetailsKey.FRAMEWORK_AGGR_VER, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionType(), newEntity.getOptionType(), EventLogBuilder.EventDetailsKey.OPTION_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionExecStyle(), newEntity.getOptionExecStyle(), EventLogBuilder.EventDetailsKey.OPTION_EXEC_STYLE, changeComment);
            checkFieldsEquals(result, oldEntity.getOptionExecPrice(), newEntity.getOptionExecPrice(), EventLogBuilder.EventDetailsKey.OPTION_EXEC_PRICE, changeComment);
        }
    }

    public TransactionDetails fullClone() {
        return new TransactionDetails(
                sourceTransId,
                previousSourceTransId,
                sourceTransRefNr,
                realizationVenue,
                compression,
                unitPrice,
                unitPriceCurrency,
                unitPriceRate,
                nominalAmount,
                priceMultiplier,
                contractCount,
                inAdvanceAmount,
                delivType,
                executionDate,
                effectiveDate,
                maturityDate,
                terminationDate,
                settlementDate,
                frameworkAggrType,
                frameworkAggrVer,
                optionType,
                optionExecStyle,
                optionExecPrice);
    }
}
