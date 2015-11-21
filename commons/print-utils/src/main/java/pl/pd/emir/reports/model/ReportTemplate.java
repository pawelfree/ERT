package pl.pd.emir.reports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pl.pd.emir.reports.enums.ReportCreateStrategy;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.enums.SupportedReportType;

public class ReportTemplate implements Serializable {

    transient private Long reportId;
    transient private Date createDate;
    transient private final String reportName;
    transient private String reportFileName;
    transient private String templateName;
    transient private final ReportCreateStrategy createStrategy;
    transient private List<SupportedReportType> supportedFormats = new ArrayList<>();
    transient ReportDataCallback dataCallback;
    transient private boolean active = true;

    public ReportTemplate(Long reportId, Date createDate, String reportName, String reportFileName, ReportCreateStrategy createStrategy, List<SupportedReportType> supportedFormats) {
        super();
        this.reportId = reportId;
        this.createDate = createDate;
        this.reportName = reportName;
        this.reportFileName = reportFileName;
        this.createStrategy = createStrategy;
        this.supportedFormats = supportedFormats;
    }

    public ReportTemplate(String reportName, String templateName, ReportCreateStrategy createStrategy, List<SupportedReportType> supportedFormats) {
        super();
        this.reportName = reportName;
        this.templateName = templateName;
        this.createStrategy = createStrategy;
        this.supportedFormats = supportedFormats;
    }

    public ReportTemplate(ReportType reportType, boolean active, ReportDataCallback dataCallback) {
        super();
        this.reportName = reportType.getKey();
        this.templateName = reportType.getTemplateName();
        this.createStrategy = ReportCreateStrategy.AD_HOC;
//        this.supportedFormats = convert(reportType.getSupportedFormats());
        this.active = active;
        this.dataCallback = dataCallback;
    }

    public String getReportName() {
        return reportName;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public ReportCreateStrategy getCreateStrategy() {
        return createStrategy;
    }

    public List<SupportedReportType> getSupportedFormats() {
        return supportedFormats;
    }

    public Long getReportId() {
        return reportId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public boolean isActive() {
        return active;
    }

    public ReportDataCallback getDataCallback() {
        return dataCallback;
    }
}
