package pl.pd.emir.report.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.report.ReportService;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.exception.ReportException;
import pl.pd.emir.reports.exception.ReportServiceException;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.reports.utils.JasperReportsUtils;

public class JasperReportService implements ReportService, Serializable {

    private static final String DEFAULT_REPORTS_LOCATION = "reports/";
    private static final String CSV_FIELD_DELIMITER = ";";
    private static final String CSV_CHARACTER_ENCODING = "CP1250";
    private static final Logger LOGGER = LoggerFactory.getLogger(JasperReportService.class);
    public static final EnumMap<ReportFormat, AbstractReportGenerator> REPORT_GENERATORS = new EnumMap<>(ReportFormat.class);

    static {
        REPORT_GENERATORS.put(ReportFormat.XLS, new XLSReportGenerator());
    }

    @Override
    public void generateReport(ReportFormat reportFormat, String pageTitle, String templateName, Map<String, Object> parameters, Collection reportData, OutputStream outputStream, HttpServletResponse httpServletResponse) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        AbstractReportGenerator generator = REPORT_GENERATORS.get(reportFormat);
        if (generator == null) {
            throw new IllegalArgumentException("Unsupported report type: " + reportFormat);
        } else {
            if (httpServletResponse != null) {
                generator.addHeaders(httpServletResponse, templateName, reportFormat);
            }
            InputStream templateInputStream = null;
            try {
                templateInputStream = getJasperTemplateStream(reportFormat, templateName);
                generator.generateReport(templateInputStream, pageTitle, outputStream, parameters, reportData);

            } catch (Exception ex) {
                throw new ReportServiceException("Template read error", ex);
            } finally {
                if (templateInputStream != null) {
                    try {
                        templateInputStream.close();
                    } catch (IOException ex) {
                        LOGGER.error("Blad zamykania strumienia", ex);
                    }
                }
            }
        }
    }

    @Override
    public InputStream generateReport(ReportFormat reportFormat, String pageTitle, String templateName, Map<String, Object> parameters, Collection reportData) {
        byte[] content = null;
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AbstractReportGenerator generator = REPORT_GENERATORS.get(reportFormat);
        if (generator == null) {
            throw new IllegalArgumentException("Unsupported report type: " + reportFormat);
        } else {
            InputStream templateInputStream = null;
            try {
                templateInputStream = getJasperTemplateStream(reportFormat, templateName);
                generator.generateReport(templateInputStream, pageTitle, outputStream, parameters, reportData);
                content = outputStream.toByteArray();
            } catch (Exception ex) {
                throw new ReportServiceException("Template read error", ex);
            } finally {
                if (templateInputStream != null) {
                    try {
                        templateInputStream.close();
                    } catch (IOException ex) {
                        LOGGER.error("Blad zamykania strumienia", ex);
                    }
                }
            }
        }
        return new ByteArrayInputStream(content);
    }

    @Override
    public InputStream generateManySheetReport(List<ReportData> reportData) {
        byte[] content;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (ReportData oneReportData : reportData) {
            ReportType rt = oneReportData.getReportType();
            InputStream is = getJasperTemplateStream(ReportFormat.XLS, rt.getTemplateName());
            oneReportData.setTemplete(is);
        }
        JasperReportsUtils.toManySheetXlsStream(outputStream, reportData);
        content = outputStream.toByteArray();
        return new ByteArrayInputStream(content);
    }

    private InputStream getJasperTemplateStream(ReportFormat reportFormat, String templateName) {
        Class<? extends ReportService> clazz = getClass();
        LOGGER.info("Location template" + DEFAULT_REPORTS_LOCATION + templateName + "_" + reportFormat.name().toLowerCase() + ".jasper");
        InputStream result = clazz.getClassLoader().getResourceAsStream(DEFAULT_REPORTS_LOCATION + templateName + "_" + reportFormat.name().toLowerCase() + ".jasper");
        /* if (result == null) {
         if (ReportFormat.CSV.equals(reportFormat)) {
         result = clazz.getResourceAsStream(DEFAULT_REPORTS_LOCATION + templateName + "_csv" + ".jasper");
         } else {
         result = clazz.getResourceAsStream(DEFAULT_REPORTS_LOCATION + templateName + ".jasper");
         }
         }
         * */
        if (result == null) {
            result = clazz.getResourceAsStream(DEFAULT_REPORTS_LOCATION + templateName);
        }
        if (result == null) {
            result = clazz.getResourceAsStream(templateName);
        }
        if (result == null) {
            throw new ReportException("Can not resolve report template name:" + templateName);
        }
        return result;
    }

    public static abstract class AbstractReportGenerator {

        public abstract void generateReport(InputStream template, String pageTitle, OutputStream outputStream, Map<String, Object> parameters, Collection reportData);

        public abstract void generateReport(OutputStream outputStream, List<ReportData> reportData);

        protected void addHeaders(HttpServletResponse httpServletResponse, String templateName, ReportFormat reportFormat) {
            if (httpServletResponse != null) {
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + getFileName(templateName, reportFormat));
            }
        }

        protected void throwException(JRException ex) {
            LOGGER.info(ex.getMessage(), ex);
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        /*
         private String getFileName(JasperPrint jasperPrint, String fileName, ReportType reportType) {
         String result = "report" + reportType.getExtension();
         if (fileName != null) {
         result = fileName + reportType.getExtension();
         } else if (!StringUtil.isEmpty(jasperPrint.getName())) {
         result = jasperPrint.getName() + reportType.getExtension();
         }
         return result;
         }
         */

        private String getFileName(String templateName, ReportFormat reportFormat) {
            return templateName + reportFormat.getExtension();
        }
    }

    private static class CSVReportGenerator extends AbstractReportGenerator {

        @Override
        protected void addHeaders(HttpServletResponse httpServletResponse, String templateName, ReportFormat reportFormat) {
            if (httpServletResponse != null) {
                super.addHeaders(httpServletResponse, templateName, reportFormat);
                httpServletResponse.setContentType("application/csv");
            }
        }

        @Override
        public void generateReport(InputStream template, String pageTitle, OutputStream outputStream, Map<String, Object> parameters, Collection reportData) {
            try {
                JasperReportsUtils.toCsvStream(template, outputStream, parameters, reportData, CSV_CHARACTER_ENCODING, CSV_FIELD_DELIMITER);
            } catch (JRException ex) {
                throwException(ex);
            }
        }

        @Override
        public void generateReport(OutputStream outputStream, List<ReportData> reportData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private static class XLSReportGenerator extends AbstractReportGenerator {

        @Override
        protected void addHeaders(HttpServletResponse httpServletResponse, String templateName, ReportFormat reportFormat) {
            if (httpServletResponse != null) {
                super.addHeaders(httpServletResponse, templateName, reportFormat);
                httpServletResponse.setContentType("application/vnd.ms-excel");
            }
        }

        @Override
        public void generateReport(InputStream template, String pageTitle, OutputStream outputStream, Map<String, Object> parameters, Collection reportData) {
            try {
                JasperReportsUtils.toXlsStream(template, pageTitle, outputStream, parameters, reportData);
            } catch (JRException ex) {
                throwException(ex);
            }
        }

        @Override
        public void generateReport(OutputStream outputStream, List<ReportData> reportData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
