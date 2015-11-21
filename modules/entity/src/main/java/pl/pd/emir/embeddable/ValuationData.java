package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import pl.pd.emir.entity.annotations.ValuationChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class ValuationData implements Serializable {

    /*
     * MTMVAL, Wartość wyceny [2]
     *
     */
    @Validators({
        @ValidateCompleteness(subjectClass = ValuationData.class, andGroup = "valuationGroup"),})
    @Column(name = "AMOUNT", precision = 20, scale = 5)
    @ValuationChange
    private BigDecimal amount;
    /*
     * CCY, Waluta wyceny [3]
     */

    @Validators({
        @ValidateCompleteness(subjectClass = ValuationData.class, andGroup = "valuationGroup"),})
    @Column(name = "CURRENCY_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    @ValuationChange
    private CurrencyCode currencyCode;

    /*
     * VALTNDTTM, Data i godzina wyceny [4]
     *
     */
    @Validators({
        @ValidateCompleteness(subjectClass = ValuationData.class, andGroup = "valuationGroup"),})
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUATION_DATE")
    @ValuationChange
    private Date valuationDate;

    /*
     * VALTNTP, Rodzaj wyceny [5]
     *
     */
    @Validators({
        @ValidateCompleteness(subjectClass = ValuationData.class, andGroup = "valuationGroup"),})
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
     * Wycena.
     */
    public ValuationData() {
        super();
        initFields();
    }

    public ValuationData(BigDecimal amount, CurrencyCode currencyCode, Date valuationDate, ValuationType valuationType) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.valuationDate = valuationDate;
        this.valuationType = valuationType;
    }

    public ValuationData(BigDecimal amount, CurrencyCode currencyCode, Date valuationDate, ValuationType valuationType, BigDecimal clientAmount) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.valuationDate = valuationDate;
        this.valuationType = valuationType;
        this.clientAmount = clientAmount;
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

    public final void initFields() {
        //to do
        //currencyCode=CurrencyCode.ERR;
    }

    public static void checkEntity(List<ChangeLog> result, ValuationData oldEntity, ValuationData newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, null, newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, null, newEntity.getValuationDate(), EventLogBuilder.EventDetailsKey.VALUATION_DATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getValuationType(), EventLogBuilder.EventDetailsKey.VALUATION_TYPE, changeComment);
            checkFieldsEquals(result, null, newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), null, EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getValuationDate(), null, EventLogBuilder.EventDetailsKey.VALUATION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getValuationType(), null, EventLogBuilder.EventDetailsKey.VALUATION_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getClientAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getAmount(), newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getValuationDate(), newEntity.getValuationDate(), EventLogBuilder.EventDetailsKey.VALUATION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getValuationType(), newEntity.getValuationType(), EventLogBuilder.EventDetailsKey.VALUATION_TYPE, changeComment);
            checkFieldsEquals(result, oldEntity.getClientAmount(), newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT, changeComment);
        }
    }

    public ValuationData fullClone() {
        return new ValuationData(amount, currencyCode, valuationDate, valuationType, clientAmount);
    }

    public boolean isEmpty() {
        return Objects.isNull(amount)
                && Objects.isNull(currencyCode)
                && Objects.isNull(valuationDate)
                && Objects.isNull(valuationType)
                && Objects.isNull(clientAmount);
    }
}
