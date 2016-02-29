package pl.pd.emir.report.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pl.pd.emir.report.ReportManagerService;
import pl.pd.emir.reports.enums.ContentTypes;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ReportData;

@ManagedBean(name = "reportBean")
@SessionScoped
public class ReportBean extends ValidationReport implements Serializable {

    @EJB
    transient private ReportManagerService reportService;
    transient private static final long serialVersionUID = 1234567654321L;
    transient private StreamedContent file;

    public void generateTableReport(ReportType reportType, ReportFormat reportFormat, ReportData reportData) {
        InputStream is = reportService.generateTableReport(reportType, reportFormat, reportData);
        String filename = BUNDLE.getString(reportType.getTemplateName() + "." + reportFormat.name());
        file = new DefaultStreamedContent(is, generateContentType(reportFormat), filename);
    }

    private String generateContentType(ReportFormat reportFormat) {
        ContentTypes content = ContentTypes.valueOf(reportFormat.name());
        return content.getType();
    }

    public void generateManySheetReport(List<ReportData> reportData) {
        InputStream is = reportService.generateTableReport(reportData);
        String filename = BUNDLE.getString(ReportType.IMPORT_LIST_REPORT + "." + ReportFormat.XLS.toString());
        file = new DefaultStreamedContent(is, generateContentType(ReportFormat.XLS), filename);
    }

    public StreamedContent getFile() {
        return file;
    }

    public ReportFormat getXlsFormat() {
        return ReportFormat.XLS;
    }
}
