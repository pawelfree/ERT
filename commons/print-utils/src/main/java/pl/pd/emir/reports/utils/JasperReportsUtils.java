package pl.pd.emir.reports.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.pd.emir.reports.model.ReportData;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class JasperReportsUtils {

    private static final Logger LOG = Logger.getLogger(JasperReportsUtils.class.getName());

    private static JasperPrint generateRaport(InputStream jasperTemplate, Map<String, Object> parameters, Collection reportData) throws JRException {
        JRDataSource dataSource;
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        if (reportData != null && !reportData.isEmpty()) {
            LOG.info("generateRaport not empty");
            dataSource = new JRBeanCollectionDataSource(reportData);
        } else {
            LOG.info("generateRaport empty");
            dataSource = new JREmptyDataSource();
        }
        parameters.put(JRParameter.REPORT_LOCALE, new Locale("pl", "PL"));
        return JasperFillManager.fillReport(jasperTemplate, parameters, dataSource);
    }

    public static void toCsvStream(InputStream jasperTemplate, OutputStream outputStream, Map<String, Object> parameters, Collection reportData, String encoding, String delimeter) throws JRException {
        JasperPrint jasperPrint = generateRaport(jasperTemplate, parameters, reportData);
        JRCsvExporter csvExporter = new JRCsvExporter();
        csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        csvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, delimeter);
        csvExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, encoding);
        csvExporter.exportReport();
    }

    public static void toXlsStream(InputStream jasperTemplate, String pageTitle, OutputStream outputStream, Map<String, Object> parameters, Collection reportData) throws JRException {
        JasperPrint jasperCommon = new JasperPrint();
        JasperPrint jasperPrint = generateRaport(jasperTemplate, parameters, reportData);
        jasperPrint.setName(pageTitle);
        List<JRPrintPage> jprintpages = jasperPrint.getPages();
        jprintpages.stream().forEach((printPage) -> {
            jasperCommon.addPage(printPage);
        });
        JRXlsExporter xlsExporter = new JRXlsExporter();
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        xlsExporter.exportReport();
    }

    public static void toManySheetXlsStream(OutputStream outputStream, List<ReportData> reportData) {
        try {
            JasperPrint jasperCommon = new JasperPrint();
            for (ReportData oneReportData : reportData) {
                JasperPrint jasperPrint = generateRaport(oneReportData.getTemplete(), oneReportData.getParameters(), oneReportData.getReportData());
                LOG.log(Level.INFO, "toManySheetXlsStream reportData size:{0}", reportData.size());
                jasperPrint.setName(oneReportData.getPageTitle());
                List<JRPrintPage> jprintpages = jasperPrint.getPages();
                jprintpages.stream().forEach((printPage) -> {
                    jasperCommon.addPage(printPage);
                });
            }
            JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperCommon);
            xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            xlsExporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(JasperReportsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
