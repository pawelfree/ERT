package pl.pd.emir.criteria;

import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.enums.ValidationStatus;

public class ClientSC extends AbstractSearchCriteria {

    String originalId;

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }
    ValidationStatus validationStatus;

    @Override
    public void clear() {
        super.clear();
        setOriginalId(null);
        setValidationStatus(null);
    }

    @Override
    public void addFilters() {
        clearFilters();

        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "validationStatus", "=", validationStatus));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "originalId", "%.%", originalId));
    }
}
