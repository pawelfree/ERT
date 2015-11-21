package pl.pd.emir.reports.enums;

public enum ReportFormat {

    XLS(".xls"), XLSMany(".xls");
    private final String extension;

    private ReportFormat(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}
