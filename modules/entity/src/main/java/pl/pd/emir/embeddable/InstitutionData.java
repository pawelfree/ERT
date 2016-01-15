package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pl.pd.emir.commons.interfaces.Initializable;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class InstitutionData implements Serializable, Initializable {

    /*
     * RPRTID_ID, Identyfikator wystawcy komunikatu(id instytucji)
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Client.class),})
    @Column(name = "INSTITUTION_ID", length = 50)
    private String institutionId;

    /*
     * RPRTID_TP, Typ użytego identyfikatora instytucji
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Client.class),})
    @Column(name = "INSTITUTION_ID_TYPE", length = 4)
    @Enumerated(EnumType.STRING)
    private InstitutionIdType institutionIdType;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public InstitutionIdType getInstitutionIdType() {
        return institutionIdType;
    }

    public void setInstitutionIdType(InstitutionIdType institutionIdType) {
        this.institutionIdType = institutionIdType;
    }

    public InstitutionData(String institutionId, InstitutionIdType institutionIdType) {
        super();
        this.institutionId = institutionId;
        this.institutionIdType = institutionIdType;
        initFields();
    }

    public InstitutionData() {
        super();
        initFields();
    }

    @Override
    public final void initFields() {
        //EMPTY
    }

    public static void checkEntity(List<ChangeLog> result, InstitutionData oldEntity, InstitutionData newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }

        if (oldEntity == null && newEntity != null) {
            checkFieldsEquals(result, null, newEntity.getInstitutionId(), EventLogBuilder.EventDetailsKey.INSTITUTION_ID);
            checkFieldsEquals(result, null, newEntity.getInstitutionIdType(), EventLogBuilder.EventDetailsKey.INSTITUTION_ID_TYPE);
        } else if (oldEntity != null && newEntity == null) {
            checkFieldsEquals(result, oldEntity.getInstitutionId(), null, EventLogBuilder.EventDetailsKey.INSTITUTION_ID);
            checkFieldsEquals(result, oldEntity.getInstitutionIdType(), null, EventLogBuilder.EventDetailsKey.INSTITUTION_ID_TYPE);
        } else {
            checkFieldsEquals(result, oldEntity.getInstitutionId(), newEntity.getInstitutionId(), EventLogBuilder.EventDetailsKey.INSTITUTION_ID);
            checkFieldsEquals(result, oldEntity.getInstitutionIdType(), newEntity.getInstitutionIdType(), EventLogBuilder.EventDetailsKey.INSTITUTION_ID_TYPE);
        }
    }

    public InstitutionData fullClone() {
        return new InstitutionData(institutionId, institutionIdType);
    }
}
