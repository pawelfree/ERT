package pl.pd.emir.reports.model;

import java.io.Serializable;

public class SupportedReportType implements Serializable {

    private ReportFormat reportFormat;

    public SupportedReportType() {
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
