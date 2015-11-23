package pl.pd.emir.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.commons.interfaces.Validatable;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.CommodityTradeData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.BaseDataChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEqualsMsg;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.resources.EventLogBuilder;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;

@Entity
@Table(name = "CONTRACT")
@NamedQueries({
    @NamedQuery(name = "Transaction.getByOriginalIdAndData",
            query = "select t FROM Transaction t WHERE t.originalId = :originalId and t.transactionDate = :date"),
    @NamedQuery(name = "Transaction.findNewestConfirmed",
            query = "select t FROM Transaction t "
            + "WHERE t.transactionDetails.sourceTransId=:sourceTransId"
            + " and not t.id = :currentId"
            + " and t.processingStatus=pl.pd.emir.enums.ProcessingStatus.CONFIRMED ORDER BY t.dateSupply DESC"),
    @NamedQuery(name = "Transaction.countDistinctTransactionForDay",
            query = "select count(distinct(t.transactionDetails.sourceTransId)) FROM Transaction t "
            + "WHERE t.client.id = :idClient"
            + " and t.contractDetailedData.contractData.prod1Code = :instrumentType"
            + " and t.transactionDate = :day"),
    @NamedQuery(name = "Transaction.sumAmountNewestTransactionForDay",
            query = "select distinct(t.transactionDetails.sourceTransId), t.transactionDetails.nominalAmount FROM Transaction t "
            + "WHERE t.client.id = :idClient"
            + " and t.contractDetailedData.contractData.prod1Code = :instrumentType"
            + " and t.transactionDate = :day "
            + " order by t.id desc"),
    @NamedQuery(name = "Transaction.importRaport",
            query = "select t FROM Transaction t "
            + "WHERE t.fileName = :file "
            + "and t.transactionDate = :date"),
    @NamedQuery(name = "Transaction.getByDateAndOriginalId",
            query = "select t FROM Transaction t "
            + "WHERE t.originalId = :originalId "
            + "AND t.transactionDate BETWEEN :transactionDateFrom AND :transactionDateTo "
            + "ORDER BY t.extractVersion"),
    @NamedQuery(name = "Transaction.getByDate",
            query = "select t FROM Transaction t "
            + "WHERE t.transactionDate BETWEEN :transactionDateFrom AND :transactionDateTo"),
    @NamedQuery(name = "Transaction.findOtherProcessingNew",
            query = "SELECT t FROM Transaction t WHERE t.id != :currentId "
            + " AND t.processingStatus = pl.pd.emir.enums.ProcessingStatus.NEW"
            + " AND t.transactionDate <= :transactionDate"
            + " AND t.transactionDetails.sourceTransId = :sourceTransId "),
    @NamedQuery(name = "Transaction.getByOriginalId",
            query = "select t FROM Transaction t WHERE t.originalId = :originalId "),
    @NamedQuery(name = "Transaction.getByOriginalIdMaxVersion",
            query = "select t FROM Transaction t WHERE t.originalId = :originalId and t.extractVersion = (SELECT MAX(r.extractVersion) FROM Transaction r WHERE r.originalId = t.originalId)"),
    @NamedQuery(name = "Transaction.getBySourceNrRefMaxVersion",
            query = "select t FROM Transaction t WHERE t.transactionDetails.sourceTransRefNr = :sourceNrRef and t.extractVersion = (SELECT MAX(r.extractVersion) FROM Transaction r WHERE r.transactionDetails.sourceTransRefNr = t.transactionDetails.sourceTransRefNr)"),
    @NamedQuery(name = "Transaction.getFullByOriginalId",
            query = "select t FROM Transaction t LEFT JOIN FETCH t.relatedItems WHERE t.originalId = :originalId "),
    @NamedQuery(name = "Transaction.getSentByOriginalIdAndDate",
            query = "select t FROM Transaction t"
            + " WHERE t.originalId = :originalId"
            + " AND t.transactionDate = :transactionDate"
            + " AND t.processingStatus = pl.pd.emir.enums.ProcessingStatus.SENT"),
    @NamedQuery(name = "Transaction.getValidMaxDate",
            query = "SELECT MAX(t.transactionDate) FROM Transaction t WHERE"
            + " t.validationStatus = pl.pd.emir.enums.ValidationStatus.VALID"
            + " AND (t.backloading IS NULL OR t.backloading = FALSE)"),
    @NamedQuery(name = "Transaction.getValidMinDate",
            query = "SELECT MIN(t.transactionDate) FROM Transaction t WHERE"
            + " t.validationStatus = pl.pd.emir.enums.ValidationStatus.VALID"
            + " AND (t.backloading IS NULL OR t.backloading = FALSE)"),
    @NamedQuery(name = "Transaction.isPossibilityDeleteTransaction",
            query = "SELECT MAX(t.id), MAX(t.transactionDate), MAX(t.extractVersion) FROM Transaction t WHERE t.originalId = :originalId"),
    @NamedQuery(name = "Transaction.getNewerMutationsCount",
            query = "SELECT COUNT(t.id) FROM Transaction t"
            + " WHERE t.originalId = :originalId"
            + " AND (t.transactionDate > :transactionDate"
            + " OR (t.transactionDate = :transactionDate"
            + " AND t.extractVersion > :extractVersion))"
            + " AND t.processingStatus != :processingStatus"),
    @NamedQuery(name = "Transaction.getNewerVersionCount",
            query = "SELECT COUNT(t.id) FROM Transaction t"
            + " WHERE t.originalId = :originalId"
            + " AND t.transactionDate = :transactionDate"
            + " AND t.extractVersion > :extractVersion"
            + " AND t.processingStatus != :processingStatus"),
    @NamedQuery(name = "Transaction.getNewestVersionTransaction",
            query = "SELECT MAX(t.extractVersion) FROM Transaction t WHERE t.originalId = :originalId"
            + " AND t.transactionDate = :transactionDate"),
    @NamedQuery(name = "Transaction.getPreviousCount",
            query = "SELECT COUNT (t.id) FROM Transaction t WHERE t.originalId = :originalId and t.transactionDate < :date"),
    /**
     * Zwraca najnowszą datę transakcji dla określonych statusów procesowania.
     */
    @NamedQuery(name = "Transaction.getMaxDateToSend",
            query = "SELECT MAX(t.transactionDate) FROM Transaction t WHERE t.dataToSend = TRUE"),
    /**
     * Zwraca najstarszą datę transakcji dla określonych statusów procesowania.
     */
    @NamedQuery(name = "Transaction.getMinDateToSend",
            query = "SELECT MIN(t.transactionDate) FROM Transaction t WHERE t.dataToSend = TRUE"),
    @NamedQuery(name = "Transaction.getByMaxVersion",
            query = "SELECT t FROM Transaction t WHERE"
            + " t.transactionDate = :transactionDate"
            + " AND t.originalId = :originalId"
            + " AND t.extractVersion = (SELECT MAX(s.extractVersion) FROM Transaction s WHERE s.transactionDate = :transactionDate AND s.originalId = :originalId )"),
    @NamedQuery(name = "Transaction.getByNewestVersion",
            query = "SELECT t FROM Transaction t WHERE t.transactionDate = :transactionDate AND t.originalId = :originalId AND t.newestVersion = TRUE"),
    @NamedQuery(name = "Transaction.getTransactionByImportLog",
            query = "SELECT t FROM Transaction t WHERE t.importLog = :importLog"),
    @NamedQuery(name = "Transaction.getCountTransactionByClient",
            query = "SELECT COUNT(t.id) FROM Transaction t WHERE t.client = :client"),
    @NamedQuery(name = "Transaction.getForStatusTr",
            query = "SELECT COUNT(t.id) FROM Transaction t WHERE t.originalId = :originalId AND t.transactionDate < :reportingDate AND t.processingStatus NOT IN :statusList"),
    @NamedQuery(name = "Transaction.findProcessedByKDPW",
            query = "SELECT t FROM Transaction t WHERE t.transactionDetails.sourceTransId = :tradeIdId"
            + " AND t.newestVersion = TRUE"
            + " AND t.transactionDate ="
            + " (SELECT MAX(t1.transactionDate) FROM Transaction t1 WHERE t1.transactionDetails.sourceTransId = :tradeIdId AND t1.transactionDate <= :transactionDate)"),
    @NamedQuery(name = "Transaction.findNewestVersion",
            query = "SELECT t FROM Transaction t WHERE t.transactionDetails.sourceTransId = :tradeIdId"
            + " AND t.newestVersion = TRUE"
            + " AND (t.transactionDate = :transactionDate"
            + " OR t.transactionDate = :transactionDate1) "
            + " ORDER BY t.transactionDate DESC"),
    @NamedQuery(name = "Transaction.countImported",
            query = "SELECT count(t.id) FROM Transaction t WHERE t.transactionDate = :transactionDate"),
    @NamedQuery(name = "Transaction.countImportedNew",
            query = "SELECT count(t.id) FROM Transaction t WHERE t.transactionDate = :transactionDate AND t.dataType = pl.pd.emir.enums.DataType.NEW"),
    @NamedQuery(name = "Transaction.countImportedMature",
            query = "SELECT count(t.id) FROM Transaction t WHERE t.transactionDate = :transactionDate AND CAST(t.transactionDetails.maturityDate AS date) = :transactionDate"),
    @NamedQuery(name = "Transaction.countKdpwClientReports",
            query = "select count(t.id)*2 from Transaction t where t.alreadySent = TRUE and t.transactionDate = :transactionDate and t.clientData.commercialActity = pl.pd.emir.enums.CommercialActity.Y"),
    @NamedQuery(name = "Transaction.countKdpwBankReports",
            query = "select count(t.id) from Transaction t where t.alreadySent = TRUE and t.transactionDate = :transactionDate and t.clientData.commercialActity IS NULL")
})
@Customizer(Transaction.class)
public class Transaction extends Extract implements Logable<Long>, Selectable<Long>, DescriptorCustomizer, Validatable, Historable<Transaction>, Sendable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "commision_seq_gen")
    @SequenceGenerator(name = "commision_seq_gen", sequenceName = "SQ_EMIR_COMMISION", allocationSize = 1000)
    private Long id;

    /**
     * Typ danych: nowa | trwajaca(mutacja) | zakonczona
     */
    @Column(name = "DATA_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    /**
     * Opcjonalne szczegółowe informacje dotyczące transakcji (zmiany)
     */
    @Column(name = "ACTION_TYPE_DETAILS", length = 50, nullable = true)
    private String actionTypeDetails;

    /**
     * Status przetwarzania
     */
    @Column(name = "PROCESSING_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;

    /**
     * Status poprawnosci
     */
    @Column(name = "VALIDATION_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    /**
     * ID_TR, Unikalny identyfikator transakcji z ekstraktu [0]
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "ORIGINAL_ID", length = 50)
    private String originalId;

    /**
     * DATA_TR, Data waluty [1]
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    /**
     * STATUS_TR, Status transakcji – jaki typ danych ma zostać przekazany w komunikacie [2]
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "ORIGINAL_STATUS", length = 3)
    @Enumerated(EnumType.STRING)
    private OriginalStatus originalStatus;

    @Transient
    private String originalClientId;

    /**
     * Klient, którego dotyczy transakcja Pole moze byc puste. Zaleznosc ustalana na podstawie pola CLIENT_ID z
     * ekstrakty TRANSAKCJE_E.
     */
    @ValidateCompleteness(subjectClass = Transaction.class)
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    /**
     * STRONA_TR, Strona, po której znajduje się kontrahent: z punktu widzenia banku [4]
     */
    @Column(name = "TRANSACTION_PARTY", length = 3)
    @Enumerated(EnumType.STRING)
    @BaseDataChange
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
    @ValidateCompleteness(subjectClass = Transaction.class, entry = true)
    @Embedded
    private ContractDataDetailed contractDetailedData;

    /**
     * Pozostale dane transakcji: ogolne(4.1), szczegoly(4.3), pozostale (4.9) [28-30, 39-55, 85-87]
     */
    @ValidateCompleteness(subjectClass = Transaction.class, entry = true)
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
    @ValidateCompleteness(subjectClass = Transaction.class, entry = true)
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
    @ValidateCompleteness(subjectClass = Transaction.class, checkValuationReporting = true, entry = true)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROTECTION_ID")
    private Protection protection;

    /**
     * Wycena transakcji
     */
    @ValidateCompleteness(subjectClass = Transaction.class, checkValuationReporting = true, entry = true)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "VALUATION_ID")
    private Valuation valuation;

    /**
     * Data zasilenia
     */
    @Column(name = "DATE_SUPPLY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSupply;

    /**
     * Wersja ekstraktu z którego wczytano encję.
     */
    @Column(name = "EXTRACT_VERSION", nullable = false)
    private Integer extractVersion;

    /**
     * Czy najnowsza wersja transakcji w dniu.
     */
    @Column(name = "NEWEST_VERSION", nullable = false)
    private Boolean newestVersion;

    /**
     * Dane do wysyłki.
     */
    @Column(name = "DATA_TO_SEND", nullable = false)
    private Boolean dataToSend;

    /**
     * Nazwa pliku, z którego wczytano ekstrakt.
     */
    @Column(name = "FILE_NAME", length = 512, nullable = true)
    private String fileName;

    @Transient
    private transient boolean selected;

    @Column(name = "ALREADY_SENT")
    private Boolean alreadySent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TRANSACTION_KDPW_MSG",
            joinColumns
            = @JoinColumn(name = "TRANS_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "KDPW_ITEM_ID", referencedColumnName = "ID"))
    private List<KdpwMsgItem> relatedItems;

    @Version
    private Long version;

    @Column(name = "CLIENT_VERSION")
    private Integer clientVersion;

    /**
     * Flaga określająca czy transakcja pochodzi z backloadingu.
     */
    @Column(name = "BACKLOADING")
    private Boolean backloading;

    @Transient
    private transient String changeComment;

    public Transaction() {
        super();
        initFields();
        setDataToSend();
    }

    public Transaction(String originalId, Date transactionDate, OriginalStatus originalStatus, String originalClientId, TransactionParty transactionParty,
            String confirmed, BusinessEntityData bankData, BusinessEntityData clientData,
            ContractDataDetailed contractDetailedData, TransactionDetails transactionDetails,
            RiskReduce riskReduce, TransactionClearing transactionClearing, PercentageRateData percentageRateData, CurrencyTradeData currencyTradeData,
            CommodityTradeData commodityTradeData, DataType dataType, ProcessingStatus processingStatus, ValidationStatus validationStatus, Client client) {
        super();
        setFields(originalId, transactionDate, originalStatus, originalClientId, transactionParty,
                StringUtil.isEmpty(confirmed) || confirmed.equals("0") ? ConfirmedStatus.UNCONFIRMED : ConfirmedStatus.CONFIRMED,
                bankData, clientData, contractDetailedData, transactionDetails,
                riskReduce, transactionClearing, percentageRateData, currencyTradeData,
                commodityTradeData, dataType, processingStatus, validationStatus, client);
        setDataToSend();
    }

    public Transaction(String originalId, Date transactionDate, OriginalStatus originalStatus, String originalClientId, TransactionParty transactionParty,
            ConfirmedStatus confirmed, BusinessEntityData bankData, BusinessEntityData clientData,
            ContractDataDetailed contractDetailedData, TransactionDetails transactionDetails,
            RiskReduce riskReduce, TransactionClearing transactionClearing, PercentageRateData percentageRateData, CurrencyTradeData currencyTradeData,
            CommodityTradeData commodityTradeData, DataType dataType, ProcessingStatus processingStatus, ValidationStatus validationStatus, Client client) {
        super();
        setFields(originalId, transactionDate, originalStatus, originalClientId, transactionParty,
                confirmed, bankData, clientData, contractDetailedData, transactionDetails,
                riskReduce, transactionClearing, percentageRateData, currencyTradeData,
                commodityTradeData, dataType, processingStatus, validationStatus, client);
        setDataToSend();

    }

    public Transaction fullClone() {
        Transaction tmp = new Transaction();
        tmp.dataType = this.dataType;
        tmp.processingStatus = this.processingStatus;
        tmp.validationStatus = this.validationStatus;
        tmp.confirmed = this.confirmed;
        tmp.dateSupply = this.dateSupply;
        tmp.originalClientId = this.originalClientId;
        tmp.originalId = this.originalId;
        tmp.originalStatus = this.originalStatus;
        tmp.processingStatus = this.processingStatus;
        tmp.transactionDate = this.transactionDate;
        tmp.transactionParty = this.transactionParty;
        //złożone - należy nie przypoisaywać referencji dla osobnych encji.
        //relacje
        try {
            tmp.client = this.client.fullClone();
        } catch (NullPointerException e) {
            Client newClient = new Client();
            tmp.client = newClient.fullClone();
        }
        try {
            tmp.valuation = this.valuation.fullClone();
        } catch (NullPointerException e) {
            Valuation newValuation = new Valuation();
            ValuationData valueData = new ValuationData();
            newValuation.setValuationData(valueData);
            tmp.valuation = newValuation.fullClone();
        }
        try {
            tmp.protection = this.protection.fullClone();
        } catch (NullPointerException e) {
            Protection newProtection = new Protection();
            tmp.protection = newProtection.fullClone();
        }
        //Embedded
        tmp.commodityTradeData = this.commodityTradeData == null ? new CommodityTradeData() : this.commodityTradeData.fullClone();
        tmp.bankData = this.bankData == null ? new BusinessEntityData() : this.bankData.fullClone();
        tmp.clientData = this.clientData == null ? new BusinessEntityData() : this.clientData.fullClone();
        tmp.contractDetailedData = this.contractDetailedData == null ? new ContractDataDetailed() : this.contractDetailedData.fullClone();
        tmp.currencyTradeData = this.currencyTradeData == null ? new CurrencyTradeData() : this.currencyTradeData.fullClone();
        tmp.percentageRateData = this.percentageRateData == null ? new PercentageRateData() : this.percentageRateData.fullClone();
        tmp.riskReduce = this.riskReduce == null ? new RiskReduce() : this.riskReduce.fullClone();
        tmp.transactionClearing = this.transactionClearing == null ? new TransactionClearing() : this.transactionClearing.fullClone();
        tmp.transactionDetails = this.transactionDetails == null ? new TransactionDetails() : this.transactionDetails.fullClone();

        tmp.setDataToSend();
        return tmp;
    }

    @Override
    public void customize(ClassDescriptor descriptor) {
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("commodityTradeData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("bankData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("clientData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("contractDetailedData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("currencyTradeData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("percentageRateData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("riskReduce")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("transactionClearing")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("transactionDetails")).setIsNullAllowed(false);
    }

    private void setFields(String originalId, Date transactionDate, OriginalStatus originalStatus, String originalClientId, TransactionParty transactionParty,
            ConfirmedStatus confirmed, BusinessEntityData bankData, BusinessEntityData clientData,
            ContractDataDetailed contractDetailedData, TransactionDetails transactionDetails,
            RiskReduce riskReduce, TransactionClearing transactionClearing, PercentageRateData percentageRateData, CurrencyTradeData currencyTradeData,
            CommodityTradeData commodityTradeData, DataType dataType, ProcessingStatus processingStatus, ValidationStatus validationStatus, Client client) {
        this.originalId = originalId;
        this.transactionDate = transactionDate;
        this.originalStatus = originalStatus;
        this.originalClientId = originalClientId;
        this.transactionParty = transactionParty;
        this.confirmed = confirmed;
        this.bankData = bankData;
        this.clientData = clientData;
        this.transactionDetails = transactionDetails;
        this.riskReduce = riskReduce;
        this.transactionClearing = transactionClearing;
        this.percentageRateData = percentageRateData;
        this.currencyTradeData = currencyTradeData;
        this.commodityTradeData = commodityTradeData;
        this.dataType = dataType;
        this.processingStatus = processingStatus;
        this.validationStatus = validationStatus;
        this.client = client;
        this.contractDetailedData = contractDetailedData;
        if (this.dateSupply == null) {
            this.dateSupply = new Date();
        }
    }

    @Override
    public final void initFields() {
        if (this.client == null) {
            this.client = new Client();
        }
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
            this.protection = new Protection();
        }
        if (this.valuation == null) {
            this.valuation = new Valuation();
        }
        if (this.relatedItems == null) {
            this.relatedItems = new ArrayList<>();
        }
    }

    @Override
    public List<ChangeLog> getChangeLogs(Transaction newEntity) {
        List<ChangeLog> result = new ArrayList<>();
        if (newEntity.client != null) {
            newEntity.getClient().setChangeComment(newEntity.getChangeComment());
        }
        //złożone pola
        Client.checkEntity(result, client, newEntity.client);
        BusinessEntityData.checkEntity(result, bankData, newEntity.bankData, newEntity.getChangeComment(), true);
        BusinessEntityData.checkEntity(result, clientData, newEntity.clientData, newEntity.getChangeComment(), false);
        ContractDataDetailed.checkEntity(result, contractDetailedData, newEntity.contractDetailedData, newEntity.getChangeComment());
        TransactionDetails.checkEntity(result, transactionDetails, newEntity.transactionDetails, newEntity.getChangeComment());
        RiskReduce.checkEntity(result, riskReduce, newEntity.riskReduce, newEntity.getChangeComment());
        TransactionClearing.checkEntity(result, transactionClearing, newEntity.transactionClearing, newEntity.getChangeComment());
        PercentageRateData.checkEntity(result, percentageRateData, newEntity.percentageRateData, newEntity.getChangeComment());
        CurrencyTradeData.checkEntity(result, currencyTradeData, newEntity.currencyTradeData, newEntity.getChangeComment());
        CommodityTradeData.checkEntity(result, commodityTradeData, newEntity.commodityTradeData, newEntity.getChangeComment());
        Protection.checkEntity(result, protection, newEntity.protection, newEntity.getChangeComment());
        Valuation.checkEntity(result, valuation, newEntity.valuation, newEntity.getChangeComment());
        //pols proste
        checkFieldsEquals(result, originalId, newEntity.originalId, EventLogBuilder.EventDetailsKey.ORIGINAL_ID, newEntity.getChangeComment());
        checkFieldsEquals(result, originalClientId, newEntity.originalClientId, EventLogBuilder.EventDetailsKey.ORIGINAL_CLIENT_ID, newEntity.getChangeComment());
        checkFieldsEquals(result, transactionParty, newEntity.transactionParty, EventLogBuilder.EventDetailsKey.TRANSACTION_PARTY, newEntity.getChangeComment());
        if (Objects.nonNull(confirmed) && Objects.nonNull(newEntity.confirmed)) {
            checkFieldsEqualsMsg(result, confirmed.getMsgKey(), newEntity.confirmed.getMsgKey(), EventLogBuilder.EventDetailsKey.CONFIRMED, newEntity.getChangeComment());
        }
        checkFieldsEquals(result, originalStatus, newEntity.originalStatus, EventLogBuilder.EventDetailsKey.ORIGINAL_STATUS, newEntity.getChangeComment());
        if (Objects.nonNull(dataType) && Objects.nonNull(newEntity.dataType)) {
            checkFieldsEqualsMsg(result, dataType.getMsgKey(), newEntity.dataType.getMsgKey(), EventLogBuilder.EventDetailsKey.DATA_TYPE, newEntity.getChangeComment());
        }
        //pola dataset
        checkFieldsEquals(result, dateSupply, newEntity.dateSupply, EventLogBuilder.EventDetailsKey.DATE_SUPPLY, newEntity.getChangeComment());
        checkFieldsEquals(result, transactionDate, newEntity.transactionDate, EventLogBuilder.EventDetailsKey.TRANSACTION_DATE, newEntity.getChangeComment());
        if (Objects.nonNull(processingStatus) && Objects.nonNull(newEntity.processingStatus)) {
            checkFieldsEqualsMsg(result, processingStatus.getMsgKey(), newEntity.processingStatus.getMsgKey(), EventLogBuilder.EventDetailsKey.PROCESSING_STATUS, newEntity.getChangeComment());
        }
        if (Objects.nonNull(getValidationStatus()) && Objects.nonNull(newEntity.getValidationStatus())) {
            checkFieldsEqualsMsg(result, getValidationStatus().getMsgKey(), newEntity.getValidationStatus().getMsgKey(), EventLogBuilder.EventDetailsKey.VALIDATION_STATUS, newEntity.getChangeComment());
        }
        checkFieldsEquals(result, backloading, newEntity.backloading, EventLogBuilder.EventDetailsKey.BACKLOADING, newEntity.getChangeComment());

        return result;
    }

    /**
     * Zmiana statusu procesowania transakcji na: WYSŁANA.
     */
    public final void sent() {
        this.processingStatus = ProcessingStatus.SENT;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: WYSŁANE ANULOWANIE.
     */
    public final void cancellationSent() {
        this.processingStatus = ProcessingStatus.CANCELLATION_SENT;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: NIEWYSŁANA.
     */
    public final void unsent() {
        this.processingStatus = ProcessingStatus.UNSENT;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: PRZYJĘTA.
     */
    public final void confirm() {
        this.processingStatus = ProcessingStatus.CONFIRMED;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: ODRZUCONA.
     */
    public final void reject() {
        this.processingStatus = ProcessingStatus.REJECTED;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: CZĘŚCIOWO ODRZUCONA.
     */
    public final void partlyReject() {
        this.processingStatus = ProcessingStatus.PARTLY_REJECTED;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: ANULOWANA.
     */
    public final void cancel() {
        this.processingStatus = ProcessingStatus.CANCELED;
        setDataToSend();
    }

    /**
     * Zmiana statusu procesowania transakcji na: ANULOWANA CZĘŚCIOWO.
     */
    public final void partlyCanceled() {
        this.processingStatus = ProcessingStatus.PARTLY_CANCELED;
        setDataToSend();
    }

    public void addKdpwItem(final KdpwMsgItem item) {
        relatedItems.add(item);
        this.alreadySent = Boolean.TRUE;
    }

    public boolean isConfirmed() {
        return ProcessingStatus.CONFIRMED.equals(this.processingStatus);
    }

    public boolean isPartlyCanceled() {
        return ProcessingStatus.PARTLY_CANCELED.equals(this.processingStatus);
    }

    public final String getInfo() {
        final StringBuilder result = new StringBuilder();
        result.append("[ ");
        result.append("Id = ").append(id);
        result.append(", ");
        result.append("originalId = ").append(originalId);
        result.append(", ");
        result.append("transactionDate = ").append(DateUtils.formatDate(transactionDate, DateUtils.DATE_FORMAT));
        result.append(" ]");
        return result.toString();
    }

    protected final void setDataToSend() {
        dataToSend = (processingStatus == ProcessingStatus.NEW || processingStatus == ProcessingStatus.CORRECTED)
                && validationStatus == ValidationStatus.VALID
                && Boolean.TRUE.equals(newestVersion);
    }

    public void setDataToSend(Boolean dataToSend) {
        this.dataToSend = dataToSend;
    }

    public final boolean isNewestExtractVersion() {
        return newestVersion;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    @Override
    public Integer getExtractVersion() {
        return extractVersion;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getExtractName() {
        return "TRANSAKCJE_E";
    }

    @Override
    public EventType getDeleteEventType() {
        return EventType.TRANSACTION_DELETE;
    }

    @Override
    public EventType getInsertEventType() {
        return EventType.TRANSACTION_INSERT;
    }

    @Override
    public EventType getModifyEventType() {
        return EventType.TRANSACTION_MODIFICATION;
    }

    // getters
    public EventType getAddMutationEventType() {
        return EventType.TRANSACTION_ADD_MUTATION;
    }

    public EventType getAddValuationEventType() {
        return EventType.TRANSACTION_ADD_VALUATION;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public TransactionParty getTransactionParty() {
        return transactionParty;
    }

    public OriginalStatus getOriginalStatus() {
        return originalStatus;
    }

    public Protection getProtection() {
        return protection;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    public ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public Valuation getValuation() {
        return valuation;
    }

    @Override
    public Client getClient() {
        return client;
    }

    public ContractDataDetailed getContractDetailedData() {
        return contractDetailedData;
    }

    public RiskReduce getRiskReduce() {
        return riskReduce;
    }

    public Boolean getDataToSend() {
        return dataToSend;
    }

    //getters
    public TransactionClearing getTransactionClearing() {
        return transactionClearing;
    }

    public Date getDateSupply() {
        return dateSupply;
    }

    public ConfirmedStatus getConfirmed() {
        return confirmed;
    }

    public BusinessEntityData getBankData() {
        return bankData;
    }

    public BusinessEntityData getClientData() {
        return clientData;
    }

    public String getOriginalClientId() {
        return originalClientId;
    }

    public CommodityTradeData getCommodityTradeData() {
        return commodityTradeData;
    }

    public CurrencyTradeData getCurrencyTradeData() {
        return currencyTradeData;
    }

    @Override
    public String getOriginalId() {
        return originalId;
    }

    public PercentageRateData getPercentageRateData() {
        return percentageRateData;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public List<KdpwMsgItem> getRelatedItems() {
        return relatedItems;
    }

    public Long getVersion() {
        return version;
    }

    public Boolean getAlreadySent() {
        return alreadySent;
    }

    // setters
    public void setExtractVersion(Integer extractVersion) {
        this.extractVersion = extractVersion;
        setDataToSend();
    }

    public void setNewestVersion(Boolean newestVersion) {
        this.newestVersion = newestVersion;
        setDataToSend();
    }

    public void setFileName(final String value) {
        this.fileName = value;
    }

    public void setDateSupply(final Date value) {
        this.dateSupply = value;
    }

    public void setTransactionClearing(final TransactionClearing value) {
        this.transactionClearing = value;
    }

    public void setRiskReduce(final RiskReduce value) {
        this.riskReduce = value;
    }

    public void setContractDetailedData(final ContractDataDetailed value) {
        this.contractDetailedData = value;
    }

    public void setTransactionDate(final Date value) {
        this.transactionDate = value;
        setDataToSend();
    }

    public void setTransactionDetails(final TransactionDetails value) {
        this.transactionDetails = value;
    }

    public void setTransactionParty(final TransactionParty value) {
        this.transactionParty = value;
    }

    public void setOriginalStatus(final OriginalStatus value) {
        this.originalStatus = value;
    }

    public void setProtection(final Protection value) {
        this.protection = value;
    }

    public void setDataType(final DataType value) {
        this.dataType = value;
    }

    public void setProcessingStatus(final ProcessingStatus value) {
        this.processingStatus = value;
        setDataToSend();
    }

    public void setValidationStatus(final ValidationStatus value) {
        this.validationStatus = value;
        setDataToSend();
    }

    public void setValuation(final Valuation value) {
        this.valuation = value;
    }

    public void setClient(final Client value) {
        this.client = value;
    }

    public void setConfirmed(final ConfirmedStatus value) {
        this.confirmed = value;
    }

    public void setBankData(final BusinessEntityData value) {
        this.bankData = value;
    }

    public void setClientData(final BusinessEntityData value) {
        this.clientData = value;
    }

    public void setOriginalClientId(final String value) {
        this.originalClientId = value;
    }

    public void setCommodityTradeData(final CommodityTradeData value) {
        this.commodityTradeData = value;
    }

    public void setCurrencyTradeData(final CurrencyTradeData value) {
        this.currencyTradeData = value;
    }

    public void setOriginalId(final String value) {
        this.originalId = value;
    }

    public void setPercentageRateData(final PercentageRateData value) {
        this.percentageRateData = value;
    }

    public void setRelatedItems(final List<KdpwMsgItem> values) {
        this.relatedItems = values;
    }

    public void setVersion(final Long value) {
        this.version = value;
    }

    public Boolean getNewestVersion() {
        return newestVersion;
    }

    public Integer getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(Integer clientVersion) {
        this.clientVersion = clientVersion;
    }

    public Boolean getBackloading() {
        return backloading;
    }

    public void setBackloading(Boolean backloading) {
        this.backloading = backloading;
    }

    public String getChangeComment() {
        return changeComment;
    }

    public void setChangeComment(String changeComment) {
        this.changeComment = changeComment;
    }

    @Override
    public Transaction getTransaction() {
        return this;
    }

    public String getActionTypeDetails() {
        return actionTypeDetails;
    }

    public void setActionTypeDetails(String actionTypeDetails) {
        this.actionTypeDetails = actionTypeDetails;
    }

}
