package pl.pd.emir.report.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import pl.pd.emir.report.ReportManagerService;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ReportData;

@Stateless
public class ReportManagerImpl implements ReportManagerService, Serializable {

    private static final Logger LOG = Logger.getLogger(ReportManagerImpl.class.getName());

    private static final long serialVersionUID = 9876543654321L;
    transient final private JasperReportService reportService = new JasperReportService();

    @Override
    public InputStream generateTableReport(ReportType reportType, ReportFormat reportFormat, ReportData reportData) {
        InputStream is = reportService.generateReport(reportFormat, reportData.getPageTitle(), reportType.getTemplateName(), reportData.getParameters(), reportData.getReportData());
        return is;
    }

    @Override
    public InputStream generateTableReport(List<ReportData> reportData) {
        InputStream is;
        LOG.log(Level.INFO, "ReportManagerImpl:ReportData{0}", reportData.size());
        is = reportService.generateManySheetReport(reportData);
        return is;
    }
}
