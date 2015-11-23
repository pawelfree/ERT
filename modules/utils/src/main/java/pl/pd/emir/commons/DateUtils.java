package pl.pd.emir.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public final class DateUtils {

    public static final String ISO_DATE_FORMAT = "yyyyMMdd";
    public static final String ISO_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_WITHOUT_SECONDS = "HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.sss";
    public static final String DATE_TIME_FORMAT_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT_DD_MM_YYYY = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_MM_DD_YYYY = "MM/dd/yy";
    public static final String DATE_TIME_FILE_FORMAT = "yyyyMMdd_HHmmss_SSS";
    public static final String TIME_ZONE = "Poland";
    public static final TimeZone TIMEZONE = TimeZone.getTimeZone(TIME_ZONE);
    public static final String DATA_MONTH_FORMAT = "yyyyMM";
    public static final String DATA_YEAR_FORMAT = "yyyy";
    private static final Calendar NOW = Calendar.getInstance();
    private static final Calendar temp = Calendar.getInstance();
    public static final int ACTUAL_YEAR = NOW.get(Calendar.YEAR);
    public static final int ACTUAL_MONTH = NOW.get(Calendar.MONTH);
    public static final int ACTUAL_DAY_OF_MONTH = NOW.get(Calendar.DAY_OF_MONTH);

    public static String getDateFormatted(String pattern, int move) {
        return getDateFormatted(pattern, move, NOW);
    }

    public static String getDateFormatted(String pattern, int move, Calendar custom) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        custom.add(Calendar.MONTH, move);
        String ret = simpleDateFormat.format(custom.getTime());
        custom.add(Calendar.MONTH, move * (-1));
        return ret;
    }

    public static int monthBaseToNo(Date monthBase) {
        Calendar tcal = new GregorianCalendar();
        tcal.setTime(monthBase);
        return (tcal.get(Calendar.YEAR) - 1996) * 12 - 12 + tcal.get(Calendar.MONTH) + 1;

    }

    public static Date noToMonthBase(int noMonthBaseOfReserve) {
        int month = noMonthBaseOfReserve % 12;
        int year = noMonthBaseOfReserve / 12 + 1997;
        temp.set(Calendar.YEAR, year);
        temp.set(Calendar.MONTH, month - 1);
        temp.set(Calendar.DAY_OF_MONTH, temp.getActualMaximum(Calendar.DAY_OF_MONTH));
        return temp.getTime();
    }

    public static int compareMonthBase(Date monthBase, Date checkDate) {
        int yearMonthBase;
        int yearMonthCheck;
        temp.setTime(monthBase);
        yearMonthBase = temp.get(Calendar.YEAR) * 100 + temp.get(Calendar.MONTH);
        temp.setTime(checkDate);
        yearMonthCheck = temp.get(Calendar.YEAR) * 100 + temp.get(Calendar.MONTH);
        if (yearMonthBase < yearMonthCheck) {
            return 1;
        } else if (yearMonthBase == yearMonthCheck) {
            return 0;
        }
        return -1;
    }

    public static String formatDate(final Date date, final String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date getDateFromString(String Date, String pattern) throws ParseException {
        if (Date != null && !"".equals(Date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(pattern);
            return simpleDateFormat.parse(Date);
        }
        return null;

    }

    public static List<Calendar> getFreeDays(int yearS) {
        //zapytania do bazy w sumie tylko raz bo w  bazie sa stale
        Calendar east = getEasterDate(yearS);
        Calendar christ = getEasterDate(yearS);
        List<Calendar> freeDaysTemp = new ArrayList<>();
        east.add(Calendar.DAY_OF_YEAR, 1);
        freeDaysTemp.add(east);
        christ.add(Calendar.DAY_OF_YEAR, 60);
        freeDaysTemp.add(christ);
        return freeDaysTemp;
    }

    public static List<Calendar> getFreeDaysAll() {
        int yearS = Calendar.getInstance().get(Calendar.YEAR);
        Calendar easterSecondDay = getEasterDate(yearS);
        Calendar christ = getEasterDate(yearS);
        List<Calendar> freeDaysTemp = new ArrayList<>();
        easterSecondDay.add(Calendar.DAY_OF_YEAR, 1);
        freeDaysTemp.add(getDayBeginning(easterSecondDay));
        Calendar easterFirstDay = (Calendar) easterSecondDay.clone();
        easterFirstDay.add(Calendar.DATE, -1);
        freeDaysTemp.add(getDayBeginning(easterFirstDay));
        Calendar greenDay = (Calendar) easterSecondDay.clone();
        greenDay.add(Calendar.DATE, 48);
        freeDaysTemp.add(getDayBeginning(greenDay));
        christ.add(Calendar.DAY_OF_YEAR, 60);
        freeDaysTemp.add(getDayBeginning(christ));
        freeDaysTemp.add(new GregorianCalendar(yearS, 0, 1));
        freeDaysTemp.add(new GregorianCalendar(yearS, 0, 6));
        freeDaysTemp.add(new GregorianCalendar(yearS, 4, 1));
        freeDaysTemp.add(new GregorianCalendar(yearS, 4, 3));
        freeDaysTemp.add(new GregorianCalendar(yearS, 7, 15));
        freeDaysTemp.add(new GregorianCalendar(yearS, 10, 1));
        freeDaysTemp.add(new GregorianCalendar(yearS, 10, 11));
        freeDaysTemp.add(new GregorianCalendar(yearS, 11, 25));
        freeDaysTemp.add(new GregorianCalendar(yearS, 11, 26));
        return freeDaysTemp;
    }

    public static Calendar getDayBeginning(Calendar calendar) {
        if (Objects.nonNull(calendar)) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar;
    }

    public static Calendar getCalendar() {
        return NOW;
    }

    public static Calendar getCalendarMovedWorkDays(Calendar custom, int moved, List<Calendar> freeDays) {

        for (int i = 21; i < (moved + 20); i++) {
            for (Calendar e : freeDays) {
                Calendar buf = (Calendar) e.clone();
                if (buf.get(Calendar.MONTH) == custom.get(Calendar.MONTH) && buf.get(Calendar.YEAR) == custom.get(Calendar.YEAR)
                        && buf.get(Calendar.DAY_OF_MONTH) == custom.get(Calendar.DAY_OF_MONTH)) {
                    i--;
                }
            }
            if (1 == custom.get(Calendar.DAY_OF_WEEK)) {
                i--;
            }
            if (7 == custom.get(Calendar.DAY_OF_WEEK)) {
                custom.add(Calendar.DATE, 1);
                i--;
            }
            if (6 == custom.get(Calendar.DAY_OF_WEEK)) {
                custom.add(Calendar.DATE, 2);
            }
            custom.add(Calendar.DATE, 1);

        }

        return custom;
    }

    public static long countDaysBetween(Calendar start, Calendar end) {
        int msInDay = 1000 * 60 * 60 * 24;
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        long startTime = start.getTimeInMillis();
        end.set(Calendar.HOUR_OF_DAY, 1);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        long endTime = end.getTimeInMillis();
        if (endTime < startTime) {

            endTime = startTime;
            startTime = endTime;
        }
        return ((endTime - startTime) / msInDay + 1);
    }

    public static Date moveDateMonth(Date date, int ammount) {
        if (date != null) {
            temp.setTime(date);
            temp.add(Calendar.MONTH, ammount);
            return temp.getTime();
        }
        return null;
    }

    public static String getEasterDate(String year) {
        return getDateFormatted("yyyyMMdd", 0, getEasterDate(Integer.valueOf(year)));
    }

    public static Calendar getEasterDate(int year) {

        int C, G, H, I, J, L, EasterMonth, EasterDay;
        G = year % 19;

        C = year / 100;
        H = (C - C / 4 - ((8 * C + 13) / 25) + 19 * G + 15) % 30;
        I = H - (H / 28) * (1 - (H / 28) * (29 / (H + 1)) * ((21 - G) / 11));
        J = (year + (year / 4) + I + 2 - C + C / 4) % 7;

        L = I - J;
        EasterMonth = 3 + ((L + 40) / 44);
        EasterDay = L + 28 - 31 * (EasterMonth / 4);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, EasterDay);
        cal.set(Calendar.MONTH, EasterMonth - 1);
        cal.set(Calendar.YEAR, year);

        return cal;

    }

    public static List<Date> prepareListDate(final Date start, final Date end) {
        GregorianCalendar calendar = new GregorianCalendar();
        List<Date> result = new ArrayList<>();
        if (null == start || null == end) {
            return result;
        }
        int i = 0;
        do {
            calendar.setTime(start);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, i++);
            result.add(calendar.getTime());
        } while (calendar.getTime().before(end));
        return result;
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date addDaysInMonth(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (!((cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DATE)) && (days > 0))
                && !((cal.get(Calendar.DATE) == cal.getActualMinimum(Calendar.DATE)) && (days < 0))) {
            cal.add(Calendar.DATE, days);
        }
        return cal.getTime();
    }

    public static List<Date> prepareListDate(final Date start, final Date end, List<Date> nonWorkingDays) {
        Calendar call = Calendar.getInstance();
        return prepareListDate(start, end)
                .stream()
                .map((day) -> {
                    call.setTime(day);
                    return day;
                })
                .filter((day) -> !(Calendar.SATURDAY == call.get(Calendar.DAY_OF_WEEK)
                        || Calendar.SUNDAY == call.get(Calendar.DAY_OF_WEEK)
                        || nonWorkingDays.contains(day)))
                .collect(Collectors.toList());
    }

    public static Date getNextWorkingDay(Date today) {
        if (null == today) {
            today = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE, 3);
                break;
            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE, 2);
                break;
            default:
                calendar.add(Calendar.DATE, 1);
                break;
        }

        return calendar.getTime();
    }

    public static Date getDayBeginning(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getFirstUnixDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDayBeginning(new Date()));
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDayBeginning(new Date()));
        calendar.add(Calendar.YEAR, 200);
        return calendar.getTime();
    }

    public static Date getDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static boolean isSameDay(Date baseDate, Date compareDate) {
        return (getDayBegin(baseDate).compareTo(getDayBegin(compareDate)) == 0);
    }

    public static Date createSpecifiedDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.clear();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date tomorrow() {
        return nextDay(null);
    }

    public static Date yesterday() {
        return prevDay(null);
    }

    public static Date nextDay(Date d) {
        return addToCalendarField(d, Calendar.DAY_OF_MONTH, 1);
    }

    public static Date prevDay(Date d) {
        return addToCalendarField(d, Calendar.DAY_OF_MONTH, -1);
    }

    private static Calendar addToCalendarField(Calendar c, int field, int value) {
        c.set(field, c.get(field) + value);
        return c;
    }

    private static Date addToCalendarField(Date d, int field, int value) {
        if (d == null) {
            d = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        addToCalendarField(c, field, value);
        return c.getTime();
    }

    public static Date getPreviousWorkingDay(Date today) {
        if (null == today) {
            today = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                calendar.add(Calendar.DATE, -3);
                break;
            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE, -2);
                break;
            default:
                calendar.add(Calendar.DATE, -1);
                break;
        }
        return calendar.getTime();
    }

    public static Date getPreviousWorkingDay(Date today, List<Calendar> freeDays) {
        if (null == today) {
            today = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar = getDayBeginning(calendar);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                calendar.add(Calendar.DATE, -3);
                break;
            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE, -2);
                break;
            default:
                calendar.add(Calendar.DATE, -1);
                break;
        }
        for (Calendar c : freeDays) {
            if (c.compareTo(calendar) == 0) {
                return getPreviousWorkingDay(calendar.getTime(), freeDays);
            }
        }
        return calendar.getTime();
    }

    public static Date getPreviousWorkingDayWithFreeDays(Date today) {
        return getPreviousWorkingDay(today, getFreeDaysAll());
    }

    public static Date getInitCriteriaDateFrom() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date tempDate = cal.getTime();
        return tempDate;
    }

    public static Date getInitCriteriaDateTo() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date tempDate = cal.getTime();
        return tempDate;
    }

    public static boolean isWeekend(Date currencyDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
