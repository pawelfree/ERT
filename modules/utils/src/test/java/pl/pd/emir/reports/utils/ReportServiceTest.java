package pl.pd.emir.reports.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.pd.emir.reports.model.EventLogWrapper;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.reports.utils.JasperReportsUtils;
import net.sf.jasperreports.engine.JRException;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceTest {

    private static final String TEST_PATH = "src/main/resources/reports/";
    private static final File JASPER_FILE_XLS = new File(TEST_PATH + "testXls.jasper");
    private static final File JASPER_EVENT_XLS = new File(TEST_PATH + "event_log_xls.jasper");
//    private static final File JRXML_FILE = new File(TEST_PATH + "test.jrxml");
//    private static final File JASPER_FILE = new File(TEST_PATH + "test_pdf.jasper");
//    private static final File JRXML_FILE_XLS = new File(TEST_PATH + "testXls.jrxml");
//    private static final File JASPER_EVENT_LOG = new File(TEST_PATH + "event_log_pdf.jasper");
//    private static final File JASPER_EVENT_CSV = new File(TEST_PATH + "event_log_csv.jasper");
//    private static final File PDF_FILE = new File(TEST_PATH + "/outputFile/test.pdf");
//    private static final File PDF_FILE1 = new File(TEST_PATH + "/outputFile/test1.pdf");
//    private static final File XLS_FILE = new File(TEST_PATH + "/outputFile/test.xls");
//    private static final File CSV_FILE = new File(TEST_PATH + "/outputFile/test.csv");

    public class Person {

        private String name;
        private String lastname;
        private Integer age;

        public Person(String name, String lastname) {
            this.name = name;
            this.lastname = lastname;
        }

        public Person(String name, String lastname, int age) {
            this.name = name;
            this.lastname = lastname;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

//    @Test
//    public void testPDFRaport() throws IOException, JRException {
//
//        // Parametry
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("reportName", "Raport 1");
//        // Data Source
//        Collection<Person> persons = new ArrayList<Person>();
//        for (Integer i = 0; i < 100; i++) {
//            persons.add(new Person("imię" + i, "nazwisko", i));
//        }
//        //clear();
//        //JasperReportsUtils.compile(JRXML_FILE.getAbsolutePath(), JASPER_FILE.getAbsolutePath());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperReportsUtils.toPdfStream(new FileInputStream(JASPER_FILE.getAbsolutePath()), outputStream, parameters, persons);
//
//        byte[] pdfContent = outputStream.toByteArray();
//        Assert.assertTrue(pdfContent.length > 0);
////        FileOutputStream fos = new FileOutputStream(PDF_FILE);
////        fos.write(pdfContent);
////        fos.close();
//    }
//    @Test
//    public void testEvent_log() throws IOException, JRException {
//        // Data Source
//        Collection<EventLogWrapper> data = new ArrayList();
//        for (int i = 0; i < 10; i++) {
//            data.add(new EventLogWrapper());
//        }
//        // Parametry
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("reportName", "Raport 1");
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperReportsUtils.toPdfStream(new FileInputStream(JASPER_EVENT_LOG.getAbsolutePath()), outputStream, parameters, data);
//        byte[] pdfContent = outputStream.toByteArray();
//        Assert.assertTrue(pdfContent.length > 0);
////        try (FileOutputStream fos = new FileOutputStream(PDF_FILE1)) {
////            fos.write(pdfContent);
////        }
//    }
//    @Test
//    public void testEvent_logCSV() throws IOException, JRException {
//        // Data
//        Collection<EventLogWrapper> data = new ArrayList();
//        for (int i = 0; i < 10; i++) {
//            data.add(new EventLogWrapper());
//        }
//
//        //Parametry
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("reportName", "Raport 1");
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperReportsUtils.toCsvStream(new FileInputStream(JASPER_EVENT_CSV.getAbsolutePath()), outputStream, parameters, data, null, "UTF-8");
//        byte[] pdfContent = outputStream.toByteArray();
//        Assert.assertTrue(pdfContent.length > 0);
////        try (FileOutputStream fos = new FileOutputStream(CSV_FILE)) {
////            fos.write(pdfContent);
////        }
//    }
    @Test
    public void testEvent_logXls() throws IOException, JRException {
        // Data
        Collection<EventLogWrapper> data = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            data.add(new EventLogWrapper());
        }

        //Parametry
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportName", "Raport 1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperReportsUtils.toXlsStream(new FileInputStream(JASPER_EVENT_XLS.getAbsolutePath()), "TEST", outputStream, parameters, data);
        byte[] pdfContent = outputStream.toByteArray();
        Assert.assertTrue(pdfContent.length > 0);
//        try (FileOutputStream fos = new FileOutputStream(XLS_FILE)) {
//            fos.write(pdfContent);
//        }
    }

    @Test
    public void testManyXls() throws IOException, JRException {
        Collection<Person> persons = new ArrayList<>();
        for (Integer i = 0; i < 5; i++) {
            persons.add(new Person("imię" + i, "nazwisko", i));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parameter1", "Raport 1");

        Collection<Person> persons1 = new ArrayList<>();
        for (Integer i = 0; i < 5; i++) {
            persons1.add(new Person("imię1" + i, "nazwisko1", i));
        }

        ReportData reportData = new ReportData();
        reportData.setParameters(parameters);
        reportData.setPageTitle("ARKUSZTEST 1");
        reportData.setReportData(persons);
        reportData.setTemplete(new FileInputStream(JASPER_FILE_XLS.getAbsolutePath()));

        ReportData reportDataPart2 = new ReportData();
        reportDataPart2.setParameters(parameters);
        reportDataPart2.setPageTitle("ARKUSZTEST 1");
        reportDataPart2.setReportData(persons1);
        reportDataPart2.setTemplete(new FileInputStream(JASPER_FILE_XLS.getAbsolutePath()));

        List<ReportData> reportDataList = new ArrayList<>();

        if (!reportDataList.isEmpty()) {
            reportDataList.clear();
        }
        reportDataList.add(reportData);
        reportDataList.add(reportDataPart2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperReportsUtils.toManySheetXlsStream(outputStream, reportDataList);
        byte[] pdfContent = outputStream.toByteArray();
        Assert.assertTrue(pdfContent.length > 0);
//         try (FileOutputStream fos = new FileOutputStream(XLS_FILE)) {
//             fos.write(pdfContent);
//         }
    }
}
