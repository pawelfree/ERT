package pl.pd.emir.report.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.pd.emir.report.CsvExportService;
import pl.pd.emir.reports.utils.PrintUtils;

@Stateless
public class CsvExportServiceImpl implements CsvExportService, Serializable {

    private static final Logger LOG = Logger.getLogger(CsvExportServiceImpl.class.getName());

    public final int PACKAGE_SIZE = 750;
    private final String SEPLINE = System.getProperty("line.separator");
    private String exportContent;

    @Override
    public void addHeader(List<String> header) {
        StringBuilder partOfFile = new StringBuilder();
        LOG.info("Add header to export...");
        String line = PrintUtils.convertRowToCSVLine(header);
        partOfFile.append(line);
        partOfFile.append(SEPLINE);
        exportContent += partOfFile.toString();
    }

    @Override
    public void addHeader(Object header) {
        try {

            LOG.info("Add header to export...");
            StringBuilder partOfFile = new StringBuilder();
            Map<String, String> columnsMap = PrintUtils.getValuesFromFields(header);
            List<String> columns = PrintUtils.mapToList(columnsMap);
            String line = PrintUtils.convertRowToCSVLine(columns);
            partOfFile.append(line);
            partOfFile.append(SEPLINE);
            exportContent += partOfFile.toString();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(CsvExportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void generateCSV(List<Object> listObjects) {
        LOG.log(Level.INFO, "Add part of data to export file, listSize: {0}", listObjects.size());
        StringBuilder partOfFile = new StringBuilder();
        for (Object o : listObjects) {
            try {
                Map<String, String> columnsMap = PrintUtils.getValuesFromFields(o);
                List<String> columns = PrintUtils.mapToList(columnsMap);
                String line = PrintUtils.convertRowToCSVLine(columns);
                partOfFile.append(line);
                partOfFile.append(SEPLINE);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(CsvExportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        exportContent += partOfFile.toString();
        listObjects.clear();
    }

    @Override
    public int getPackageSize() {
        return PACKAGE_SIZE;
    }

    @Override
    public String getExportContent() {
        return exportContent;
    }

    @Override
    public void clear() {
        exportContent = "";
    }

}
