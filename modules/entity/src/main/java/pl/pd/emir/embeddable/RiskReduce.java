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
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ContractDataChange;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class RiskReduce implements Serializable {

    /*
     * RSKMTGTN_CNFRMTNDTTM,  Znacznik czasu potwierdzenia
     */
    @Column(name = "CONFIRMATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @ContractDataChange
    private Date confirmationDate;
    /*
     * RSKMTGTN_ CNFRMTNTP, Sposób dokonania potwierdzenia
     */
    @Column(name = "CONFIRMATION_TYPE", length = 3)
    @Enumerated(EnumType.STRING)
    @ContractDataChange
    private ConfirmationType confirmationType;

    public RiskReduce() {
        super();
    }

    public RiskReduce(Date confirmationDate, ConfirmationType confirmationType) {
        super();
        this.confirmationDate = confirmationDate;
        this.confirmationType = confirmationType;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public ConfirmationType getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(ConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }

    public static void checkEntity(List<ChangeLog> result, RiskReduce oldEntity, RiskReduce newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null || newEntity == null) {
            if (oldEntity == null) {
                checkFieldsEquals(result, null, newEntity.getConfirmationDate(), EventLogBuilder.EventDetailsKey.CONFIRMATION_DATE, changeComment);
                checkFieldsEquals(result, null, newEntity.getConfirmationType(), EventLogBuilder.EventDetailsKey.CONFIRMATION_TYPE, changeComment);
            } else {
                checkFieldsEquals(result, oldEntity.getConfirmationDate(), null, EventLogBuilder.EventDetailsKey.CONFIRMATION_DATE, changeComment);
                checkFieldsEquals(result, oldEntity.getConfirmationType(), null, EventLogBuilder.EventDetailsKey.CONFIRMATION_TYPE, changeComment);
            }
        } else {
            checkFieldsEquals(result, oldEntity.getConfirmationDate(), newEntity.getConfirmationDate(), EventLogBuilder.EventDetailsKey.CONFIRMATION_DATE, changeComment);
            checkFieldsEquals(result, oldEntity.getConfirmationType(), newEntity.getConfirmationType(), EventLogBuilder.EventDetailsKey.CONFIRMATION_TYPE, changeComment);
        }
    }

    public RiskReduce fullClone() {
        return new RiskReduce(confirmationDate, confirmationType);
    }
}
