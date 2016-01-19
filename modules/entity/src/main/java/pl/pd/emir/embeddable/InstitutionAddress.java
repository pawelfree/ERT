package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import pl.pd.emir.commons.interfaces.Initializable;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.BankDataChange;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;
import static pl.pd.emir.entity.utils.HistoryUtils.checkFieldsEquals;
import pl.pd.emir.resources.EventLogBuilder;

@Embeddable
public class InstitutionAddress implements Serializable, Initializable {

    /*
     * DMCL_PSTCD, kod pocztowy
     */
    @Column(name = "INSTITUTION_POSTAL_CODE", length = 40)
    @BankDataChange
    private String postalCode;
    /*
     * DMCL_TWNNM, miasto
     */
    @Validators({
        //        @ValidateCompleteness(subjectClass = Bank.class), //tylko dla klienta z RPRTID_TP != LEIC
        @ValidateCompleteness(subjectClass = Client.class)
    })
    @Column(name = "INSTITUTION_CITY", length = 60)
    @BankDataChange
    private String city;
    /*
     * DMCL_STRTNM, ulica
     */
    @Column(name = "INSTITUTION_STREET_NAME", length = 150)
    @BankDataChange
    private String streetName;
    /*
     * DMCL_BLDGID, nr budynku
     */
    @Column(name = "INSTITUTION_BUILDING_ID", length = 20)
    @BankDataChange
    private String buildingId;
    /*
     * DMCL_PRMSID, nr lokalu
     */
    @Column(name = "INSTITUTION_PREMISES_ID", length = 20)
    @BankDataChange
    private String premisesId;
    /*
     * DMCL_DTLS, inne informacje
     */
    @Column(name = "INSTITUTION_ADDRESS_DETAILS", length = 208)
    @BankDataChange
    private String details;

    public InstitutionAddress() {
        super();
        initFields();
    }

    public InstitutionAddress(String postalCode, String city, String streetName, String buildingId, String premisesId, String details) {
        super();
        this.postalCode = postalCode;
        this.city = city;
        this.streetName = streetName;
        this.buildingId = buildingId;
        this.premisesId = premisesId;
        this.details = details;
        initFields();
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPremisesId() {
        return premisesId;
    }

    public void setPremisesId(String premisesId) {
        this.premisesId = premisesId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public final void initFields() {
        //EMPTY
    }

    static void checkEntity(List<ChangeLog> result, InstitutionAddress oldEntity, InstitutionAddress newEntity) {
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null) {
            checkFieldsEquals(result, null, newEntity.getBuildingId(), EventLogBuilder.EventDetailsKey.BUILDING_ID);
            checkFieldsEquals(result, null, newEntity.getCity(), EventLogBuilder.EventDetailsKey.CITY);
            checkFieldsEquals(result, null, newEntity.getDetails(), EventLogBuilder.EventDetailsKey.DETAILS);
            checkFieldsEquals(result, null, newEntity.getPostalCode(), EventLogBuilder.EventDetailsKey.POSTAL_CODE);
            checkFieldsEquals(result, null, newEntity.getPremisesId(), EventLogBuilder.EventDetailsKey.PREMISES_ID);
            checkFieldsEquals(result, null, newEntity.getStreetName(), EventLogBuilder.EventDetailsKey.STREET_NAME);
        } else if (newEntity == null) {
            checkFieldsEquals(result, oldEntity.getBuildingId(), null, EventLogBuilder.EventDetailsKey.BUILDING_ID);
            checkFieldsEquals(result, oldEntity.getCity(), null, EventLogBuilder.EventDetailsKey.CITY);
            checkFieldsEquals(result, oldEntity.getDetails(), null, EventLogBuilder.EventDetailsKey.DETAILS);
            checkFieldsEquals(result, oldEntity.getPostalCode(), null, EventLogBuilder.EventDetailsKey.POSTAL_CODE);
            checkFieldsEquals(result, oldEntity.getPremisesId(), null, EventLogBuilder.EventDetailsKey.PREMISES_ID);
            checkFieldsEquals(result, oldEntity.getStreetName(), null, EventLogBuilder.EventDetailsKey.STREET_NAME);
        } else {
            checkFieldsEquals(result, oldEntity.getBuildingId(), newEntity.getBuildingId(), EventLogBuilder.EventDetailsKey.BUILDING_ID);
            checkFieldsEquals(result, oldEntity.getCity(), newEntity.getCity(), EventLogBuilder.EventDetailsKey.CITY);
            checkFieldsEquals(result, oldEntity.getDetails(), newEntity.getDetails(), EventLogBuilder.EventDetailsKey.DETAILS);
            checkFieldsEquals(result, oldEntity.getPostalCode(), newEntity.getPostalCode(), EventLogBuilder.EventDetailsKey.POSTAL_CODE);
            checkFieldsEquals(result, oldEntity.getPremisesId(), newEntity.getPremisesId(), EventLogBuilder.EventDetailsKey.PREMISES_ID);
            checkFieldsEquals(result, oldEntity.getStreetName(), newEntity.getStreetName(), EventLogBuilder.EventDetailsKey.STREET_NAME);
        }
    }

    public InstitutionAddress fullClone() {
        return new InstitutionAddress(postalCode, city, streetName, buildingId, premisesId, details);
    }
}
