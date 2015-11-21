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
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.DerivativesChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CommLoadType;
import pl.pd.emir.enums.CommUnderlDtls;
import pl.pd.emir.enums.CommUnderlType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class CommodityTradeData implements Serializable {

    /*
     * CMMDTYTRAD_CMMDTYBASE, Towarowy instrument bazowy\
     *
     */
    @Column(name = "COMM_UNDERL_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private CommUnderlType commUnderlType;
    /*
     * CMMDTYTRAD_ CMMDTYDTLS, Szczegółowe informacje dotyczące towaru
     *
     */
    @Column(name = "COMM_UNDERL_DTLS", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private CommUnderlDtls commUnderlDtls;
    /*
     * CMMDTYTRAD_DLVRYPNT, Miejsce lub strefa dostawy
     *
     */
    @Column(name = "COMM_VENUE", length = 16)
    @DerivativesChange
    private String commVenue;
    /*
     * CMMDTYTRAD_INTRCNNCTNPNT, Punkt połączenia międzysystemowego
     *
     */
    @Column(name = "COMM_INTRCONN", length = 50)
    @DerivativesChange
    private String commInterconn;
    /*
     * CMMDTYTRAD_LDTP, Rodzaj obciążenia - określenie profilu dostawy produktu dla poszczególnych okresów dostawy w ciągu dnia.
     *
     */
    @Column(name = "COMM_LOAD_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private CommLoadType commLoadType;
    /*
     * CMMDTYTRAD_DLVRYSTARTDTTM, Data i godzina rozpoczęcia dostawy
     *
     */
    @Column(name = "COMM_DELIV_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @DerivativesChange
    private Date commDelivStartFrom;
    /*
     * CMMDTYTRAD_DLVRYENDDTTM, Data i godzina zakończenia dostawy
     *
     */
    @Column(name = "COMM_DELIV_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @DerivativesChange
    private Date commDelivEndFrom;
    /*
     * CMMDTYTRAD_CNTRCTCPCTY, Zakontraktowana ilość przypadająca na okres dostawy
     *
     */
    @Column(name = "COMM_CONTRACT_COUNT", length = 50)
    @DerivativesChange
    private String commContractCount;
    /*
     * CMMDTYTRAD_QTY, Ilość jednostek
     *
     */
    @Column(name = "COMM_RATE_COUNT", precision = 12, scale = 2)
    @DerivativesChange
    private BigDecimal commRateCount;
    /*
     * CMMDTYTRAD_PRIC, Cena za ilość w okresie dostawy
     *
     */
    @Column(name = "COMM_RATE_PRICE", precision = 12, scale = 2)
    @DerivativesChange
    private BigDecimal commRataCount;

    public CommodityTradeData() {
        super();
    }

    public CommodityTradeData(CommUnderlType commUnderlType, CommUnderlDtls commUnderlDtls, String commVenue, String commInterconn, CommLoadType commLoadType, Date commDelivStartFrom, Date commDelivEndFrom, String commContractCount, BigDecimal commRateCount, BigDecimal commRataCount) {
        super();
        this.commUnderlType = commUnderlType;
        this.commUnderlDtls = commUnderlDtls;
        this.commVenue = commVenue;
        this.commInterconn = commInterconn;
        this.commLoadType = commLoadType;
        this.commDelivStartFrom = commDelivStartFrom;
        this.commDelivEndFrom = commDelivEndFrom;
        this.commContractCount = commContractCount;
        this.commRateCount = commRateCount;
        this.commRataCount = commRataCount;
    }

    public String getCommContractCount() {
        return commContractCount;
    }

    public void setCommContractCount(String value) {
        this.commContractCount = StringUtil.getNullOnEmpty(value);
    }

    public Date getCommDelivEndFrom() {
        return commDelivEndFrom;
    }

    public void setCommDelivEndFrom(Date commDelivEndFrom) {
        this.commDelivEndFrom = commDelivEndFrom;
    }

    public Date getCommDelivStartFrom() {
        return commDelivStartFrom;
    }

    public void setCommDelivStartFrom(Date commDelivStartFrom) {
        this.commDelivStartFrom = commDelivStartFrom;
    }

    public String getCommInterconn() {
        return commInterconn;
    }

    public void setCommInterconn(String value) {
        this.commInterconn = StringUtil.getNullOnEmpty(value);
    }

    public CommLoadType getCommLoadType() {
        return commLoadType;
    }

    public void setCommLoadType(CommLoadType commLoadType) {
        this.commLoadType = commLoadType;
    }

    public BigDecimal getCommRataCount() {
        return commRataCount;
    }

    public void setCommRataCount(BigDecimal commRataCount) {
        this.commRataCount = commRataCount;
    }

    public BigDecimal getCommRateCount() {
        return commRateCount;
    }

    public void setCommRateCount(BigDecimal commRateCount) {
        this.commRateCount = commRateCount;
    }

    public CommUnderlDtls getCommUnderlDtls() {
        return commUnderlDtls;
    }

    public void setCommUnderlDtls(CommUnderlDtls commUnderlDtls) {
        this.commUnderlDtls = commUnderlDtls;
    }

    public CommUnderlType getCommUnderlType() {
        return commUnderlType;
    }

    public void setCommUnderlType(CommUnderlType commUnderlType) {
        this.commUnderlType = commUnderlType;
    }

    public String getCommVenue() {
        return commVenue;
    }

    public void setCommVenue(String value) {
        this.commVenue = StringUtil.getNullOnEmpty(value);
    }

    public void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, CommodityTradeData oldEntity, CommodityTradeData newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getCommUnderlType(), EventLogBuilder.EventDetailsKey.COMM_UNDERL_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommUnderlDtls(), EventLogBuilder.EventDetailsKey.COMM_UNDERL_DTLS, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommVenue(), EventLogBuilder.EventDetailsKey.COMM_VENUE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommInterconn(), EventLogBuilder.EventDetailsKey.COMM_INTERCONN, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommLoadType(), EventLogBuilder.EventDetailsKey.COMM_LOAD_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommDelivStartFrom(), EventLogBuilder.EventDetailsKey.COMM_DELIV_START_FROM, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommDelivEndFrom(), EventLogBuilder.EventDetailsKey.COMM_DELIV_END_FROM, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommContractCount(), EventLogBuilder.EventDetailsKey.COMM_CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommRateCount(), EventLogBuilder.EventDetailsKey.COMM_RATE_COUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getCommRataCount(), EventLogBuilder.EventDetailsKey.COMM_RATA_COUNT, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getCommUnderlType(), null, EventLogBuilder.EventDetailsKey.COMM_UNDERL_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommUnderlDtls(), null, EventLogBuilder.EventDetailsKey.COMM_UNDERL_DTLS, changeComment);
            checkFieldsEquals(result, oldEntity.getCommVenue(), null, EventLogBuilder.EventDetailsKey.COMM_VENUE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommInterconn(), null, EventLogBuilder.EventDetailsKey.COMM_INTERCONN, changeComment);
            checkFieldsEquals(result, oldEntity.getCommLoadType(), null, EventLogBuilder.EventDetailsKey.COMM_LOAD_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommDelivStartFrom(), null, EventLogBuilder.EventDetailsKey.COMM_DELIV_START_FROM, changeComment);
            checkFieldsEquals(result, oldEntity.getCommDelivEndFrom(), null, EventLogBuilder.EventDetailsKey.COMM_DELIV_END_FROM, changeComment);
            checkFieldsEquals(result, oldEntity.getCommContractCount(), null, EventLogBuilder.EventDetailsKey.COMM_CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCommRateCount(), null, EventLogBuilder.EventDetailsKey.COMM_RATE_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCommRataCount(), null, EventLogBuilder.EventDetailsKey.COMM_RATA_COUNT, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getCommUnderlType(), newEntity.getCommUnderlType(), EventLogBuilder.EventDetailsKey.COMM_UNDERL_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommUnderlDtls(), newEntity.getCommUnderlDtls(), EventLogBuilder.EventDetailsKey.COMM_UNDERL_DTLS, changeComment);
            checkFieldsEquals(result, oldEntity.getCommVenue(), newEntity.getCommVenue(), EventLogBuilder.EventDetailsKey.COMM_VENUE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommInterconn(), newEntity.getCommInterconn(), EventLogBuilder.EventDetailsKey.COMM_INTERCONN, changeComment);
            checkFieldsEquals(result, oldEntity.getCommLoadType(), newEntity.getCommLoadType(), EventLogBuilder.EventDetailsKey.COMM_LOAD_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getCommDelivStartFrom(), newEntity.getCommDelivStartFrom(), EventLogBuilder.EventDetailsKey.COMM_DELIV_START_FROM, changeComment);
            checkFieldsEquals(result, oldEntity.getCommDelivEndFrom(), newEntity.getCommDelivEndFrom(), EventLogBuilder.EventDetailsKey.COMM_DELIV_END_FROM, changeComment);
            checkFieldsEquals(result, oldEntity.getCommContractCount(), newEntity.getCommContractCount(), EventLogBuilder.EventDetailsKey.COMM_CONTRACT_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCommRateCount(), newEntity.getCommRateCount(), EventLogBuilder.EventDetailsKey.COMM_RATE_COUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCommRataCount(), newEntity.getCommRataCount(), EventLogBuilder.EventDetailsKey.COMM_RATA_COUNT, changeComment);
        }
    }

    public CommodityTradeData fullClone() {
        return new CommodityTradeData(this.commUnderlType,
                this.commUnderlDtls,
                this.commVenue,
                this.commInterconn,
                this.commLoadType,
                this.commDelivStartFrom,
                this.commDelivEndFrom,
                this.commContractCount,
                this.commRateCount,
                this.commRataCount);
    }
}
