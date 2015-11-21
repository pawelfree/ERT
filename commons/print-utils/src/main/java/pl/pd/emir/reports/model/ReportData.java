package pl.pd.emir.reports.model;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import pl.pd.emir.reports.enums.ReportType;

public class ReportData<T> {

    private String pageTitle = "Arkusz";
    private ReportType reportType;
    private Map<String, Object> parameters;
    private Collection<T> reportData;
    private InputStream templete;

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Collection<T> getReportData() {
        return reportData;
    }

    public void setReportData(Collection<T> reportData) {
        this.reportData = reportData;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public InputStream getTemplete() {
        return templete;
    }

    public void setTemplete(InputStream templete) {
        this.templete = templete;
    }
}
