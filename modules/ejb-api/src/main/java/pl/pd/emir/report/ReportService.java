package pl.pd.emir.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.model.ReportData;

public interface ReportService {

    InputStream generateReport(ReportFormat reportFormat, String pageTitle, String reportTemplateName, Map<String, Object> parameters, Collection reportData);

    void generateReport(ReportFormat reportFormat, String pageTitle, String reportTemplateName, Map<String, Object> parameters, Collection reportData, OutputStream outputStream, HttpServletResponse httpServletResponse);

    InputStream generateManySheetReport(List<ReportData> reportData);
}
