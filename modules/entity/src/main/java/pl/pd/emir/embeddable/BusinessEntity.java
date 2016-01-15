package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import pl.pd.emir.commons.interfaces.Initializable;
import pl.pd.emir.entity.administration.ChangeLog;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class BusinessEntity implements Serializable, Initializable {

    /*
     * TRRPRTID_PMRYID, Wewnętrzny identyfikator podmiotu raportującego: NIP
     */

    @Column(name = "SUBJECT_NIP", length = 18)
    private String subjectNip;

    /*
     * TRRPRTID_SCNDRYID, Wewnętrzny identyfikator podmiotu raportującego: REGON
     */
    @Column(name = "SUBJECT_REGON", length = 18)
    private String subjectRegon;

    public BusinessEntity() {
        super();
        initFields();
    }

    public BusinessEntity(String subjectNip, String subjectRegon) {
        super();
        this.subjectNip = subjectNip;
        this.subjectRegon = subjectRegon;
        initFields();
    }

    public String getSubjectNip() {
        return subjectNip;
    }

    public void setSubjectNip(String subjectNip) {
        this.subjectNip = subjectNip;
    }

    public String getSubjectRegon() {
        return subjectRegon;
    }

    public void setSubjectRegon(String subjectRegon) {
        this.subjectRegon = subjectRegon;
    }

    @Override
    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, BusinessEntity oldEntity, BusinessEntity newEntity, String changeComment) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null && newEntity != null) {
            checkFieldsEquals(result, null, newEntity.getSubjectNip(), EventLogBuilder.EventDetailsKey.SUBJECT_NIP, changeComment);
            checkFieldsEquals(result, null, newEntity.getSubjectRegon(), EventLogBuilder.EventDetailsKey.SUBJECT_REGON, changeComment);
        } else if (oldEntity != null && newEntity == null) {
            checkFieldsEquals(result, oldEntity.getSubjectNip(), null, EventLogBuilder.EventDetailsKey.SUBJECT_NIP, changeComment);
            checkFieldsEquals(result, oldEntity.getSubjectRegon(), null, EventLogBuilder.EventDetailsKey.SUBJECT_REGON, changeComment);
        } else if (oldEntity != null && newEntity != null) {
            checkFieldsEquals(result, oldEntity.getSubjectNip(), newEntity.getSubjectNip(), EventLogBuilder.EventDetailsKey.SUBJECT_NIP, changeComment);
            checkFieldsEquals(result, oldEntity.getSubjectRegon(), newEntity.getSubjectRegon(), EventLogBuilder.EventDetailsKey.SUBJECT_REGON, changeComment);
        }
    }

    public BusinessEntity fullClone() {
        return new BusinessEntity(subjectNip, subjectRegon);
    }
}
