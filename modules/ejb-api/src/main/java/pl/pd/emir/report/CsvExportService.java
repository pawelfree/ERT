package pl.pd.emir.report;

import java.util.List;
import javax.ejb.Local;

@Local
public interface CsvExportService {

    public void generateCSV(List<Object> listObjects);

    public int getPackageSize();

    public String getExportContent();

    public void addHeader(Object header);

    public void addHeader(List<String> header);

    public void clear();
}
