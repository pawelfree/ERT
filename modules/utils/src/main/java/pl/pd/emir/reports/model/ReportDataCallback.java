package pl.pd.emir.reports.model;

import java.util.Collection;
import java.util.Map;
import pl.pd.emir.reports.enums.ReportType;

public abstract class ReportDataCallback {

    private transient final ReportType reportType;

    public ReportDataCallback(final ReportType reportType) {
        this.reportType = reportType;
    }

    public abstract Map<String, Object> getParameters();

    public abstract Collection<Object> getReportData();

    public ReportType getReportType() {
        return reportType;
    }
}
