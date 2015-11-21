package pl.pd.emir.report;

import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ReportData;

@Local
public interface ReportManagerService {

    InputStream generateTableReport(ReportType reportType, ReportFormat reportFormat, ReportData reportData);

    InputStream generateTableReport(List<ReportData> reportData);
}
