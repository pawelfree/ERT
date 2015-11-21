package pl.pd.emir.embeddable;

import java.io.Serializable;
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
import pl.pd.emir.entity.annotations.ContractDataChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.ClearingOblig;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class TransactionClearing implements Serializable {
    /*
     * CLRGINF_CLROBLGTN, Wskazanie cyz zglaszany kontrakt podlega obowiazkowi rozliczania
     */

    @ValidateCompleteness(subjectClass = Transaction.class)
    @Column(name = "CLEARING_OBLIGN", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private ClearingOblig clearingOblig;
    /*
     * CLRGINF_CLRD, czy dokonano rozliczenia
     */
    @Column(name = "CLEARED", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private Cleared cleared;
    /*
     * CLRGINF_CLRDTTM, znacznik czasu rozliczenia
     * cleared
     */
    @Column(name = "CLEARING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @ContractDataChange
    private Date clearingDate;
    /*
     * CLRGINF_CCP, niepowtarzalny kod CCP, ktory dokonal rozliczenia
     */
    @Column(name = "CCP_CODE", length = 20)
    @ContractDataChange
    private String ccpCode;
    /*
     * CLRGINF_INTRGRP, wskazanie czy kontrakt zawarto jako transakcje wewnatrzgrupowa
     */
    @Column(name = "INTERGROUP_TRANS", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private IntergropuTrans intergropuTrans;

    public TransactionClearing() {
        super();
        initFields();
    }

    public TransactionClearing(ClearingOblig clearingOblig, Cleared cleared, Date clearingDate, String ccpCode, IntergropuTrans intergropuTrans) {
        super();
        this.clearingOblig = clearingOblig;
        this.cleared = cleared;
        this.clearingDate = clearingDate;
        this.ccpCode = ccpCode;
        this.intergropuTrans = intergropuTrans;
        initFields();
    }

    public String getCcpCode() {
        return ccpCode;
    }

    public void setCcpCode(String value) {
        this.ccpCode = StringUtil.getNullOnEmpty(value);
    }

    public IntergropuTrans getIntergropuTrans() {
        return intergropuTrans;
    }

    public void setIntergropuTrans(IntergropuTrans intergropuTrans) {
        this.intergropuTrans = intergropuTrans;
    }

    public Cleared getCleared() {
        return cleared;
    }

    public void setCleared(Cleared cleared) {
        this.cleared = cleared;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public ClearingOblig getClearingOblig() {
        return clearingOblig;
    }

    public void setClearingOblig(ClearingOblig clearingOblig) {
        this.clearingOblig = clearingOblig;
    }

    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, TransactionClearing oldEntity, TransactionClearing newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null || newEntity == null) {
            if (oldEntity == null) {
                checkFieldsEquals(result, null, newEntity.getClearingOblig(), EventLogBuilder.EventDetailsKey.SETTLEMENT_OBLIG, changeComment);
                checkFieldsEquals(result, null, newEntity.getCleared(), EventLogBuilder.EventDetailsKey.SETTLED, changeComment);
                checkFieldsEquals(result, null, newEntity.getClearingDate(), EventLogBuilder.EventDetailsKey.CLEARING_DATE, changeComment);
                checkFieldsEquals(result, null, newEntity.getCcpCode(), EventLogBuilder.EventDetailsKey.CCP_CODE, changeComment);
                checkFieldsEquals(result, null, newEntity.getIntergropuTrans(), EventLogBuilder.EventDetailsKey.INTERGROPU_TRANS, changeComment);
            } else {
                checkFieldsEquals(result, oldEntity.getClearingOblig(), null, EventLogBuilder.EventDetailsKey.SETTLEMENT_OBLIG, changeComment);
                checkFieldsEquals(result, oldEntity.getCleared(), null, EventLogBuilder.EventDetailsKey.SETTLED, changeComment);
                checkFieldsEquals(result, oldEntity.getClearingDate(), null, EventLogBuilder.EventDetailsKey.CLEARING_DATE, changeComment);
                checkFieldsEquals(result, oldEntity.getCcpCode(), null, EventLogBuilder.EventDetailsKey.CCP_CODE, changeComment);
                checkFieldsEquals(result, oldEntity.getIntergropuTrans(), null, EventLogBuilder.EventDetailsKey.INTERGROPU_TRANS, changeComment);
            }
        } else {
            checkFieldsEquals(result, oldEntity.getClearingOblig(), newEntity.getClearingOblig(), EventLogBuilder.EventDetailsKey.SETTLEMENT_OBLIG, changeComment);
            checkFieldsEquals(result, oldEntity.getCleared(), newEntity.getCleared(), EventLogBuilder.EventDetailsKey.SETTLED, changeComment);
            checkFieldsEquals(result, oldEntity.getClearingDate(), newEntity.getClearingDate(), EventLogBuilder.EventDetailsKey.CLEARING_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getCcpCode(), newEntity.getCcpCode(), EventLogBuilder.EventDetailsKey.CCP_CODE, changeComment);
            checkFieldsEquals(result, oldEntity.getIntergropuTrans(), newEntity.getIntergropuTrans(), EventLogBuilder.EventDetailsKey.INTERGROPU_TRANS, changeComment);
        }
    }

    public TransactionClearing fullClone() {
        return new TransactionClearing(clearingOblig, cleared, clearingDate, ccpCode, intergropuTrans);
    }
}
