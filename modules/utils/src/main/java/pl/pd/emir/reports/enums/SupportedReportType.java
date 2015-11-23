package pl.pd.emir.reports.enums;

import java.io.Serializable;

public class SupportedReportType implements Serializable {

    private ReportFormat reportFormat;

    public SupportedReportType() {
        super();
    }

    public SupportedReportType(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }
}
