package pl.pd.emir.resources;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.pd.emir.commons.DateUtils;
import org.slf4j.LoggerFactory;

public class ResourceMask {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResourceMask.class);

    private final static String FULL_YEAR_PATTERN = "%RRRR%";
    private final static String SHORT_YEAR_PATTERN = "%RR%";
    private final static String MONTH_PATTERN = "%MM%";
    private final static String DAY_PATTERN = "%DD%";

    private final static String SHORT_DATE_PATTERN = "yyMMdd";

    private final String mask;

    public ResourceMask(String mask) {
        this.mask = mask;
    }

    public boolean matches(Path target, boolean caseSensitive) {
        String targetName = target.getFileName().toString();
        String maskRegexp = getRegexp(mask);
        if (!caseSensitive) {
            targetName = targetName.toLowerCase();
            maskRegexp = maskRegexp.toLowerCase();
        }
        return targetName.matches(maskRegexp);
    }

    private String getRegexp(String mask) {
        String regexp;
        if (mask != null) {
            regexp = mask;
            regexp = regexp.replaceAll(FULL_YEAR_PATTERN, "[0-9]{4}").replaceAll(SHORT_YEAR_PATTERN, "[0-9]{2}");
            regexp = regexp.replaceAll(MONTH_PATTERN, "[0-9]{2}");
            regexp = regexp.replaceAll(DAY_PATTERN, "[0-9]{2}");
            regexp = regexp.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*").replaceAll("\\?", ".{1}");
        } else {
            regexp = ".*";
        }
        return regexp;
    }

    public String getRegexp() {
        return getRegexp(mask);
    }

    public Date getDateFromName(Path target, boolean fillEmptyDateSection) {
        String name = target.getFileName().toString();
        String year = null, month = null, day = null;
        Pattern pattern = Pattern.compile(getRegexp(mask.replaceAll(FULL_YEAR_PATTERN, "([0-9]{4})")), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find() && matcher.groupCount() > 0) {
            year = matcher.group(1).substring(2);
        }
        pattern = Pattern.compile(getRegexp(mask.replaceAll(SHORT_YEAR_PATTERN, "([0-9]{2})")), Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find() && matcher.groupCount() > 0) {
            year = matcher.group(1);
        }
        pattern = Pattern.compile(getRegexp(mask.replaceAll(MONTH_PATTERN, "([0-9]{2})")), Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find() && matcher.groupCount() > 0) {
            month = matcher.group(1);
        }
        pattern = Pattern.compile(getRegexp(mask.replaceAll(DAY_PATTERN, "([0-9]{2})")), Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find() && matcher.groupCount() > 0) {
            day = matcher.group(1);
        }

        if (fillEmptyDateSection) {
            year = year == null ? "70" : year;
            month = month == null ? "01" : month;
            day = day == null ? "01" : day;
        } else if (year == null || month == null || day == null) {
            return null;
        }

        try {
            String stringDate = String.format("%s%s%s", year, month, day);
            return new SimpleDateFormat(SHORT_DATE_PATTERN).parse(stringDate);
        } catch (ParseException ex) {
            LOGGER.error("Parse date exception", ex);
            return null;
        }
    }

    public boolean checkAccordingToDate(Path target, Date date) {
        Date dateFromName = getDateFromName(target, true);
        if (date == null || dateFromName == null) {
            return false;
        }

        Calendar resultantCalendar = Calendar.getInstance();
        resultantCalendar.setTime(DateUtils.getFirstUnixDate());

        Calendar temporaryCalendar = Calendar.getInstance();
        temporaryCalendar.setTime(date);

        if (mask.contains(FULL_YEAR_PATTERN) || mask.contains(SHORT_YEAR_PATTERN)) {
            resultantCalendar.set(Calendar.YEAR, temporaryCalendar.get(Calendar.YEAR));
        }
        if (mask.contains(MONTH_PATTERN)) {
            resultantCalendar.set(Calendar.MONTH, temporaryCalendar.get(Calendar.MONTH));
        }
        if (mask.contains(DAY_PATTERN)) {
            resultantCalendar.set(Calendar.DAY_OF_MONTH, temporaryCalendar.get(Calendar.DAY_OF_MONTH));
        }

        return dateFromName.equals(resultantCalendar.getTime());
    }

    @Override
    public String toString() {
        return mask;
    }

}
