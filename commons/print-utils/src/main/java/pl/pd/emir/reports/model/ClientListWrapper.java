package pl.pd.emir.reports.model;

public class ClientListWrapper {

    private String originalId;
    private String clientName;
    private String businessEntitySubjectNip;
    private String businessEntitySubjectRegon;
    private String institutionInstitutionDataInstitutionId;
    private String validationStatusMsgKey;

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBusinessEntitySubjectNip() {
        return businessEntitySubjectNip;
    }

    public void setBusinessEntitySubjectNip(String businessEntitySubjectNip) {
        this.businessEntitySubjectNip = businessEntitySubjectNip;
    }

    public String getBusinessEntitySubjectRegon() {
        return businessEntitySubjectRegon;
    }

    public void setBusinessEntitySubjectRegon(String businessEntitySubjectRegon) {
        this.businessEntitySubjectRegon = businessEntitySubjectRegon;
    }

    public String getInstitutionInstitutionDataInstitutionId() {
        return institutionInstitutionDataInstitutionId;
    }

    public void setInstitutionInstitutionDataInstitutionId(String institutionInstitutionDataInstitutionId) {
        this.institutionInstitutionDataInstitutionId = institutionInstitutionDataInstitutionId;
    }

    public String getValidationStatusMsgKey() {
        return validationStatusMsgKey;
    }

    public void setValidationStatusMsgKey(String validationStatusMsgKey) {
        this.validationStatusMsgKey = validationStatusMsgKey;
    }
}
