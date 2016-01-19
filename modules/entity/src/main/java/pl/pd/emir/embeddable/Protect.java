package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ProtectionChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class Protect implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * COLLTN, Wskazanie, czy dokonano zabezpieczenia [2]
     */
    @Column(name = "PROTECTION", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private DoProtection isProtection;
    /*
     * PRTFCOLL, Wskazanie, czy dokonano zabezpieczenia na poziomie portfela. [3]
     */
    @Column(name = "WALLET_PROTECTION", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private YesNo walletProtection;
    /*
     * PRTFID, Kod portfela [4]
     */
    @Column(name = "WALLET_ID", length = 35)
    @ProtectionChange
    private String walletId;
    /*
     * COLLVAL, Wartość zabezpieczenia [5]
     */
    @Column(name = "AMOUNT_PROTECT", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal amount;
    /*
     * COLLCCY, Waluta zabezpieczenia [6]
     */
    @Column(name = "CURRENCY_CODE_PROTECT", length = 3)
    @Enumerated(EnumType.STRING)
    @ProtectionChange
    private CurrencyCode currencyCode;
    /*
     * COLLVAL_C, Wartość zabezpieczenia dla drugiej strony transackji [7]
     */
    @Column(name = "AMOUNT_C_PROTECT", precision = 25, scale = 5)
    @ProtectionChange
    private BigDecimal clientAmount;

    public Protect() {
        super();
        initFields();
    }

    public Protect(DoProtection commision, YesNo walletProtection, String walletId, BigDecimal amount, CurrencyCode currencyCode, BigDecimal clientAmount) {
        super();
        this.isProtection = commision;
        this.walletProtection = walletProtection;
        this.walletId = walletId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.clientAmount = clientAmount;
        initFields();
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

    public YesNo getWalletProtection() {
        return walletProtection;
    }

    public void setWalletProtection(YesNo walletProtection) {
        this.walletProtection = walletProtection;
    }

    public String getWalletId() {
        if (Objects.isNull(walletId)) {
            walletId = "";
        }
        return walletId;
    }

    public void setWalletId(String value) {
        this.walletId = StringUtil.getNullOnEmpty(value);
    }

    public BigDecimal getClientAmount() {
        return clientAmount;
    }

    public void setClientAmount(BigDecimal clientAmount) {
        this.clientAmount = clientAmount;
    }

    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, Protect oldEntity, Protect newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null || newEntity == null) {
            if (oldEntity == null) {

                checkFieldsEquals(result, null, newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
                checkFieldsEquals(result, null, newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID);
                checkFieldsEquals(result, null, newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT);
                checkFieldsEquals(result, null, newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
                checkFieldsEquals(result, null, newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);
            } else {

                checkFieldsEquals(result, oldEntity.getWalletProtection(), null, EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
                checkFieldsEquals(result, oldEntity.getWalletId(), null, EventLogBuilder.EventDetailsKey.WALLET_ID);
                checkFieldsEquals(result, oldEntity.getAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT);
                checkFieldsEquals(result, oldEntity.getCurrencyCode(), null, EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
                checkFieldsEquals(result, oldEntity.getClientAmount(), null, EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);
            }
        } else {

            checkFieldsEquals(result, oldEntity.getWalletProtection(), newEntity.getWalletProtection(), EventLogBuilder.EventDetailsKey.WALLET_PROTECTION);
            checkFieldsEquals(result, oldEntity.getWalletId(), newEntity.getWalletId(), EventLogBuilder.EventDetailsKey.WALLET_ID);
            checkFieldsEquals(result, oldEntity.getAmount(), newEntity.getAmount(), EventLogBuilder.EventDetailsKey.AMOUNT);
            checkFieldsEquals(result, oldEntity.getCurrencyCode(), newEntity.getCurrencyCode(), EventLogBuilder.EventDetailsKey.CURRENCY_CODE);
            checkFieldsEquals(result, oldEntity.getClientAmount(), newEntity.getClientAmount(), EventLogBuilder.EventDetailsKey.AMOUNT_CLIENT);
        }
    }

    public DoProtection getIsProtection() {
        return isProtection;
    }

    public void setIsProtection(DoProtection isProtection) {
        this.isProtection = isProtection;
    }

}
