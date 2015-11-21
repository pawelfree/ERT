package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.DerivativesChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class CurrencyTradeData implements Serializable {

    /*
     * FXTRAD_CCY2, Waluta 2 - inna waluta transakcji (cross currency), jeżeli różna od waluty dostawy.
     *
     */
    @Column(name = "CURR_TRADE_CODE", length = 3)
    @Enumerated(EnumType.STRING)
    @DerivativesChange
    private CurrencyCode currencyTradeCode;
    /*
     * FXTRAD_XCHGRATE1, Kurs walutowy 1 – ustalony umownie kurs walutowy
     *
     */
    @Column(name = "CURR_TRADE_EXCH_RATE", precision = 15, scale = 5)
    @DerivativesChange
    private BigDecimal currTradeExchRate;
    /*
     * FXTRAD_FRWRDXCHGRATE, Terminowy kurs walutowy w dacie waluty
     *
     */
    @Column(name = "CURR_TRADE_FRWD_RATE", precision = 15, scale = 5)
    @DerivativesChange
    private BigDecimal currTradeFrwdRate;
    /*
     * FXTRAD_XCHGRATEBSIS, Podstawa kursu walutowego - podstawa notowań dla kursu walutowego.
     *
     */
    @Column(name = "CURR_TRADE_BASIS", length = 10)
    @DerivativesChange
    private String currTradeBasis;

    public CurrencyTradeData() {
        super();
        initFields();
    }

    public CurrencyTradeData(CurrencyCode currencyTradeCode, BigDecimal currTradeExchRate, BigDecimal currTradeFrwdRate, String currTradeBasis) {
        super();
        this.currencyTradeCode = currencyTradeCode;
        this.currTradeExchRate = currTradeExchRate;
        this.currTradeFrwdRate = currTradeFrwdRate;
        this.currTradeBasis = currTradeBasis;
        initFields();
    }

    public String getCurrTradeBasis() {
        return currTradeBasis;
    }

    public void setCurrTradeBasis(String value) {
        this.currTradeBasis = StringUtil.getNullOnEmpty(value);
    }

    public BigDecimal getCurrTradeExchRate() {
        return currTradeExchRate;
    }

    public void setCurrTradeExchRate(BigDecimal currTradeExchRate) {
        this.currTradeExchRate = currTradeExchRate;
    }

    public BigDecimal getCurrTradeFrwdRate() {
        return currTradeFrwdRate;
    }

    public void setCurrTradeFrwdRate(BigDecimal currTradeFrwdRate) {
        this.currTradeFrwdRate = currTradeFrwdRate;
    }

    public CurrencyCode getCurrencyTradeCode() {
        return currencyTradeCode;
    }

    public void setCurrencyTradeCode(CurrencyCode currencyTradeCode) {
        this.currencyTradeCode = currencyTradeCode;
    }

    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, CurrencyTradeData oldEntity, CurrencyTradeData newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }

        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getCurrencyTradeCode(), EventLogBuilder.EventDetailsKey.CURRENCY_TRADE_CODE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCurrTradeExchRate(), EventLogBuilder.EventDetailsKey.CURR_TRADE_EXCHR_RATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCurrTradeFrwdRate(), EventLogBuilder.EventDetailsKey.CURR_TRADE_FRWD_RATE, changeComment);
            checkFieldsEquals(result, null, newEntity.getCurrTradeBasis(), EventLogBuilder.EventDetailsKey.CURR_TRADE_BASIS, changeComment);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getCurrencyTradeCode(), null, EventLogBuilder.EventDetailsKey.CURRENCY_TRADE_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeExchRate(), null, EventLogBuilder.EventDetailsKey.CURR_TRADE_EXCHR_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeFrwdRate(), null, EventLogBuilder.EventDetailsKey.CURR_TRADE_FRWD_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeBasis(), null, EventLogBuilder.EventDetailsKey.CURR_TRADE_BASIS, changeComment);
        } else {
            checkFieldsEquals(result, oldEntity.getCurrencyTradeCode(), newEntity.getCurrencyTradeCode(), EventLogBuilder.EventDetailsKey.CURRENCY_TRADE_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeExchRate(), newEntity.getCurrTradeExchRate(), EventLogBuilder.EventDetailsKey.CURR_TRADE_EXCHR_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeFrwdRate(), newEntity.getCurrTradeFrwdRate(), EventLogBuilder.EventDetailsKey.CURR_TRADE_FRWD_RATE, changeComment);
            checkFieldsEquals(result, oldEntity.getCurrTradeBasis(), newEntity.getCurrTradeBasis(), EventLogBuilder.EventDetailsKey.CURR_TRADE_BASIS, changeComment);
        }
    }

    public CurrencyTradeData fullClone() {
        return new CurrencyTradeData(currencyTradeCode, currTradeExchRate, currTradeFrwdRate, currTradeBasis);
    }
}
