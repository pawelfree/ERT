package pl.pd.emir.reports.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import pl.pd.emir.reports.enums.ContentTypes;
import pl.pd.emir.reports.enums.ReportFormat;
import pl.pd.emir.reports.enums.ReportType;

public class PrintUtils {

    public final static ResourceBundle BUNDLE = ResourceBundle.getBundle("report-messages-pl");

    public static String generateContentType(ReportFormat reportFormat) {
        return ContentTypes.valueOf(reportFormat.name()).getType();
    }

    public static String generateReportFileName(ReportType reportType, ReportFormat reportFormat) {
        return BUNDLE.getString(reportType.getTemplateName() + "." + reportFormat.name());
    }

    public static boolean compareOnlyDateFromDataTime(Date date1, Date date2) {
        if (date1.getDay() != date2.getDay()) {
            return false;
        }
        if (date1.getMonth() != date2.getMonth()) {
            return false;
        }
        return date1.getYear() == date2.getYear();
    }

    public static Map<String, String> getValuesFromFields(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> listValues = new TreeMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            listValues.put(field.getName(), (String) (value == null ? "" : value));
        }
        return listValues;
    }

    public static List<String> mapToList(Map<String, String> map) {
        return new ArrayList<>(map.values());
    }

    public static String convertRowToCSVLine(List<String> columns) {
        return columns.stream().map((col) -> col.concat(";")).reduce("", String::concat);
    }

    public static List<String> joinHeader(List<String> header1, List<String> header2) {
        List<String> tmp = new ArrayList<>();
        header1.stream().forEach((h1) -> {
            tmp.add(h1);
        });
        header2.stream().forEach((h2) -> {
            tmp.add(h2);
        });
        return tmp;
    }

    public static String getRecordFromLine(String line) {
        Scanner scan = new Scanner(line);
        String tmp = "";
        while (scan.hasNext()) {
            tmp = scan.next();
        }
        if (!tmp.matches("[0-9]+") == true) {
            tmp = "";
        }
        return tmp;
    }
}
