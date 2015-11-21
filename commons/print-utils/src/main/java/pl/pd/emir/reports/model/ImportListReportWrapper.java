package pl.pd.emir.reports.model;

public class ImportListReportWrapper {

    private String category;

    private String description;

    private String fieldNumber;

    public ImportListReportWrapper(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public ImportListReportWrapper(String category, String description, String fieldNumber) {
        this.category = category;
        this.description = description;
        this.fieldNumber = fieldNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

}
