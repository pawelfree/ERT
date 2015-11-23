package pl.pd.emir.reports.model;

public enum ReportFormat {

    PDF(".pdf"), CSV(".csv"), XLS(".xls"), XML(".xml");
    private final String extension;

    private ReportFormat(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}
