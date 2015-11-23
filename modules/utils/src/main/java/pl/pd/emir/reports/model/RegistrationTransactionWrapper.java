package pl.pd.emir.reports.model;

import java.util.Date;

public class RegistrationTransactionWrapper {

    private String transactionDetailsSourceTransId;
    private String originalId;
    private Date transactionDate;
    private String originalClientId;
    private String dataTypeDescription;
    private String processingStatusDescription;
    private String validationStatusDescription;
    private Date terminationDateSupply;
    private String comfirmed;

    public String getTransactionDetailsSourceTransId() {
        return transactionDetailsSourceTransId;
    }

    public void setTransactionDetailsSourceTransId(String transactionDetailsSourceTransId) {
        this.transactionDetailsSourceTransId = transactionDetailsSourceTransId;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOriginalClientId() {
        return originalClientId;
    }

    public void setOriginalClientId(String originalClientId) {
        this.originalClientId = originalClientId;
    }

    public String getDataTypeDescription() {
        return dataTypeDescription;
    }

    public void setDataTypeDescription(String dataTypeDescription) {
        this.dataTypeDescription = dataTypeDescription;
    }

    public String getProcessingStatusDescription() {
        return processingStatusDescription;
    }

    public void setProcessingStatusDescription(String processingStatusDescription) {
        this.processingStatusDescription = processingStatusDescription;
    }

    public String getValidationStatusDescription() {
        return validationStatusDescription;
    }

    public void setValidationStatusDescription(String validationStatusDescription) {
        this.validationStatusDescription = validationStatusDescription;
    }

    public Date getTerminationDateSupply() {
        return terminationDateSupply;
    }

    public void setTerminationDateSupply(Date terminationDateSupply) {
        this.terminationDateSupply = terminationDateSupply;
    }

    public String getComfirmed() {
        return comfirmed;
    }

    public void setComfirmed(String comfirmed) {
        this.comfirmed = comfirmed;
    }
}
