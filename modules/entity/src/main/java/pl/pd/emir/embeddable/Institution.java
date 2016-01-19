package pl.pd.emir.embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import pl.pd.emir.commons.interfaces.Initializable;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;

@Embeddable
@Customizer(Institution.class)
public class Institution implements Serializable, Initializable, DescriptorCustomizer {

    /*
     * Identyfikacja instytucji
     * RPRTID_ID,  Identyfikator wystawcy komunikatu (identyfikator instytucji)
     * RPRTID_TP,  Typ użytego identyfikatora instytucji
     */
    @Validators({
        @ValidateCompleteness(subjectClass = Client.class)
    })
    @Embedded
    private InstitutionData institutionData;
    /*
     * Dane adresowe instytucji
     */

    @Validators({
        @ValidateCompleteness(subjectClass = Client.class)
    })
    @Embedded
    private InstitutionAddress institutionAddr;

    public Institution() {
        super();
        initFields();
    }

    public Institution(InstitutionData institutionData, InstitutionAddress institutionAddr) {
        super();
        this.institutionData = institutionData;
        this.institutionAddr = institutionAddr;
        initFields();
    }

    public InstitutionAddress getInstitutionAddr() {
        return institutionAddr;
    }

    public void setInstitutionAddr(InstitutionAddress institutionAddr) {
        this.institutionAddr = institutionAddr;
    }

    public InstitutionData getInstitutionData() {
        return institutionData;
    }

    public void setInstitutionData(InstitutionData institutionData) {
        this.institutionData = institutionData;
    }

    @Override
    public final void initFields() {
        if (this.institutionAddr == null) {
            this.institutionAddr = new InstitutionAddress();
        }
        if (this.institutionData == null) {
            this.institutionData = new InstitutionData();
        }
    }

    public static void checkEntity(List<ChangeLog> result, Institution oldEntity, Institution newEntity) {
        // walidacja not null
        if (oldEntity == null && newEntity == null) {
            return;
        }
        if (oldEntity == null && newEntity != null) {
            InstitutionData.checkEntity(result, null, newEntity.getInstitutionData());
            InstitutionAddress.checkEntity(result, null, newEntity.getInstitutionAddr());
        } else if (oldEntity != null && newEntity == null) {
            InstitutionData.checkEntity(result, oldEntity.getInstitutionData(), null);
            InstitutionAddress.checkEntity(result, oldEntity.getInstitutionAddr(), null);
        } else if (oldEntity != null && newEntity != null) {
            InstitutionData.checkEntity(result, oldEntity.getInstitutionData(), newEntity.getInstitutionData());
            InstitutionAddress.checkEntity(result, oldEntity.getInstitutionAddr(), newEntity.getInstitutionAddr());
        }
        //
    }

    public Institution fullClone() {
        return new Institution(institutionData.fullClone(), institutionAddr.fullClone());
    }

    @Override
    public void customize(ClassDescriptor descriptor) {
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("institutionData")).setIsNullAllowed(false);
        ((AggregateObjectMapping) descriptor.getMappingForAttributeName("institutionAddr")).setIsNullAllowed(false);
    }
}
