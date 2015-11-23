package pl.pd.emir.reports.model;

public class ImportCsvReportWrapper {

    private String field0;
    private String field1;
    private String field2;
    private String field3;
    private String field4;

    public String getImportDate() {
        return field0;
    }

    public void setImportDate(String importDate) {
        this.field0 = importDate;
    }

    public String getImportScope() {
        return field1;
    }

    public void setImportScope(String importScope) {
        this.field1 = importScope;
    }

    public String getErrorCategory() {
        return field2;
    }

    public void setErrorCategory(String errorCategory) {
        this.field2 = errorCategory;
    }

    public String getErrorNr() {
        return field4;
    }

    public void setErrorNr(String errorNr) {
        this.field4 = errorNr;
    }

    public String getErrorDescription() {
        return field3;
    }

    public void setErrorDescription(String errorDescription) {
        this.field3 = errorDescription;
    }

}
