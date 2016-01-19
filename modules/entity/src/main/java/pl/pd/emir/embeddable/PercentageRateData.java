package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.TransactionDataChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class PercentageRateData implements Serializable {

    /*
     * IRTRAD_FXDRATELG1, Stała stopa procentowa części („nogi”) 1
     *
     */
    @Column(name = "FIXED_RATE_LEG1", precision = 25, scale = 5)
    @TransactionDataChange
    private BigDecimal fixedRateLeg1;
    /*
     * IRTRAD_FXDRATELG2, Stała stopa procentowa części („nogi”) 2
     */
    @Column(name = "FIXED_RATE_LEG2", precision = 25, scale = 5)
    @TransactionDataChange
    private BigDecimal fixedRateLeg2;
    /*
     * IRTRAD_ FXDRATEDAYCNT, Długość okresu stosowania stałej stopy procentowej
     *
     */
    @Column(name = "FIXED_RATE_DAY_COUNT ", length = 10)
    @TransactionDataChange
    private String fixedRateDayCount;
    /*
     * IRTRAD_FXDLGPMTFRQCY, Częstotliwość płatności – część („noga”) o stałym oprocentowaniu
     *
     */
    @Column(name = "FIXED_PAYMENT_FREQ", length = 10)
    @TransactionDataChange
    private String fixedPaymentFreq;
    /*
     * IRTRAD_FLTGLGPMTFRQCY, Częstotliwość płatności – część o zmiennym oprocentowaniu
     *
     */
    @Column(name = "FLOAT_PAYMENT_FREQ", length = 10)
    @TransactionDataChange
    private String floatPaymentFreq;
    /*
     * IRTRAD_ FLTGLGRSTFRQCY, Częstotliwość ustalania na nowo zmiennej stopy procentowej\
     *
     */
    @Column(name = "NEW_PAYMENT_FREQ", length = 10)
    @TransactionDataChange
    private String newPaymentFreq;
    /*
     * IRTRAD_FLTGRATELG1, Zmienna stopa procentowa części („nogi”) 1
     *
     */
    @Column(name = "FLOAT_RATE_LEG1", length = 20)
    @TransactionDataChange
    private String floatRateLeg1;
    /*
     * IRTRAD_FLTGRATELG2, Zmienna stopa procentowa części („nogi”) 2
     *
     */
    @Column(name = "FLOAT_RATE_LEG2", length = 20)
    @TransactionDataChange
    private String floatRateLeg2;

    public PercentageRateData() {
        super();
        initFields();
    }

    public PercentageRateData(BigDecimal fixedRateLeg1, BigDecimal fixedRateLeg2, String fixedRateDayCount, String fixedPaymentFreq, String floatPaymentFreq, String newPaymentFreq, String floatRateLeg1, String floatRateLeg2) {
        super();
        this.fixedRateLeg1 = fixedRateLeg1;
        this.fixedRateLeg2 = fixedRateLeg2;
        this.fixedRateDayCount = fixedRateDayCount;
        this.fixedPaymentFreq = fixedPaymentFreq;
        this.floatPaymentFreq = floatPaymentFreq;
        this.newPaymentFreq = newPaymentFreq;
        this.floatRateLeg1 = floatRateLeg1;
        this.floatRateLeg2 = floatRateLeg2;
        initFields();
    }

    public String getFixedPaymentFreq() {
        return fixedPaymentFreq;
    }

    public void setFixedPaymentFreq(String value) {
        this.fixedPaymentFreq = StringUtil.getNullOnEmpty(value);
    }

    public String getFixedRateDayCount() {
        return fixedRateDayCount;
    }

    public void setFixedRateDayCount(String value) {
        this.fixedRateDayCount = StringUtil.getNullOnEmpty(value);
    }

    public BigDecimal getFixedRateLeg1() {
        return fixedRateLeg1;
    }

    public void setFixedRateLeg1(BigDecimal fixedRateLeg1) {
        this.fixedRateLeg1 = fixedRateLeg1;
    }

    public BigDecimal getFixedRateLeg2() {
        return fixedRateLeg2;
    }

    public void setFixedRateLeg2(BigDecimal fixedRateLeg2) {
        this.fixedRateLeg2 = fixedRateLeg2;
    }

    public String getFloatPaymentFreq() {
        return floatPaymentFreq;
    }

    public void setFloatPaymentFreq(String value) {
        this.floatPaymentFreq = StringUtil.getNullOnEmpty(value);
    }

    public String getFloatRateLeg1() {
        return floatRateLeg1;
    }

    public void setFloatRateLeg1(String value) {
        this.floatRateLeg1 = StringUtil.getNullOnEmpty(value);
    }

    public String getFloatRateLeg2() {
        return floatRateLeg2;
    }

    public void setFloatRateLeg2(String value) {
        this.floatRateLeg2 = StringUtil.getNullOnEmpty(value);
    }

    public String getNewPaymentFreq() {
        return newPaymentFreq;
    }

    public void setNewPaymentFreq(String value) {
        this.newPaymentFreq = StringUtil.getNullOnEmpty(value);
    }

    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, PercentageRateData oldEntity, PercentageRateData newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null || newEntity == null) {
            if (oldEntity == null) {
                checkFieldsEquals(result, null, newEntity.getFixedRateLeg1(), EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG1);
                checkFieldsEquals(result, null, newEntity.getFixedRateLeg2(), EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG2);
                checkFieldsEquals(result, null, newEntity.getFixedRateDayCount(), EventLogBuilder.EventDetailsKey.FIXED_RATE_DAY_COUNT);
                checkFieldsEquals(result, null, newEntity.getFixedPaymentFreq(), EventLogBuilder.EventDetailsKey.FIXED_PAYMENT_FREQ);
                checkFieldsEquals(result, null, newEntity.getFloatPaymentFreq(), EventLogBuilder.EventDetailsKey.FLOAT_PAYMENT_FREQ);
                checkFieldsEquals(result, null, newEntity.getNewPaymentFreq(), EventLogBuilder.EventDetailsKey.NEW_PAYMENT_FREQ);
                checkFieldsEquals(result, null, newEntity.getFloatRateLeg1(), EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG1);
                checkFieldsEquals(result, null, newEntity.getFloatRateLeg2(), EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG2);
            } else {
                checkFieldsEquals(result, oldEntity.getFixedRateLeg1(), null, EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG1);
                checkFieldsEquals(result, oldEntity.getFixedRateLeg2(), null, EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG2);
                checkFieldsEquals(result, oldEntity.getFixedRateDayCount(), null, EventLogBuilder.EventDetailsKey.FIXED_RATE_DAY_COUNT);
                checkFieldsEquals(result, oldEntity.getFixedPaymentFreq(), null, EventLogBuilder.EventDetailsKey.FIXED_PAYMENT_FREQ);
                checkFieldsEquals(result, oldEntity.getFloatPaymentFreq(), null, EventLogBuilder.EventDetailsKey.FLOAT_PAYMENT_FREQ);
                checkFieldsEquals(result, oldEntity.getNewPaymentFreq(), null, EventLogBuilder.EventDetailsKey.NEW_PAYMENT_FREQ);
                checkFieldsEquals(result, oldEntity.getFloatRateLeg1(), null, EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG1);
                checkFieldsEquals(result, oldEntity.getFloatRateLeg2(), null, EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG2);
            }
        } else {
            checkFieldsEquals(result, oldEntity.getFixedRateLeg1(), newEntity.getFixedRateLeg1(), EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG1);
            checkFieldsEquals(result, oldEntity.getFixedRateLeg2(), newEntity.getFixedRateLeg2(), EventLogBuilder.EventDetailsKey.FIXED_RATE_LEG2);
            checkFieldsEquals(result, oldEntity.getFixedRateDayCount(), newEntity.getFixedRateDayCount(), EventLogBuilder.EventDetailsKey.FIXED_RATE_DAY_COUNT);
            checkFieldsEquals(result, oldEntity.getFixedPaymentFreq(), newEntity.getFixedPaymentFreq(), EventLogBuilder.EventDetailsKey.FIXED_PAYMENT_FREQ);
            checkFieldsEquals(result, oldEntity.getFloatPaymentFreq(), newEntity.getFloatPaymentFreq(), EventLogBuilder.EventDetailsKey.FLOAT_PAYMENT_FREQ);
            checkFieldsEquals(result, oldEntity.getNewPaymentFreq(), newEntity.getNewPaymentFreq(), EventLogBuilder.EventDetailsKey.NEW_PAYMENT_FREQ);
            checkFieldsEquals(result, oldEntity.getFloatRateLeg1(), newEntity.getFloatRateLeg1(), EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG1);
            checkFieldsEquals(result, oldEntity.getFloatRateLeg2(), newEntity.getFloatRateLeg2(), EventLogBuilder.EventDetailsKey.FLOAT_RATE_LEG2);
        }
    }

    public PercentageRateData fullClone() {
        return new PercentageRateData(fixedRateLeg1,
                fixedRateLeg2,
                fixedRateDayCount,
                fixedPaymentFreq,
                floatPaymentFreq,
                newPaymentFreq,
                floatRateLeg1,
                floatRateLeg2);
    }
}
