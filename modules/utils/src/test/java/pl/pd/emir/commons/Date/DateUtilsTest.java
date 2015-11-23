package pl.pd.emir.commons.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import pl.pd.emir.commons.DateUtils;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtilsTest {

    final static Logger LOG = LoggerFactory.getLogger(DateUtilsTest.class);

    @Test
    public void testGetPreviousWorkingDayWithFreeDays() {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1);
        calendar2.set(Calendar.MONTH, 11);
        calendar2.set(Calendar.DATE, 31);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        Date date2 = calendar2.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar1.set(Calendar.MONTH, 0);
        calendar1.set(Calendar.DATE, 2);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date date1 = calendar1.getTime();

        date1 = DateUtils.getPreviousWorkingDayWithFreeDays(date1);

        Assert.assertEquals(date1, date2);
    }

    /**
     * Test of getDateFormatted method, of class DateUtils.
     */
    @Test
    public void testGetDateFormatted_3args() {
        LOG.info("getDateFormatted");
        String format = pl.pd.emir.commons.DateUtils.formatDate(new GregorianCalendar(2013, 10, 5).getTime(), "yyyyMMdd");
        LOG.info(format);
        assertTrue(format, "20131105".equals(format));
        format = pl.pd.emir.commons.DateUtils.formatDate(null, "yyyyMMdd");
        assertTrue(format, "".equals(format));

    }

    /**
     * Test of getFreeDaysAll method, of class DateUtils.
     */
    @Test
    public void testGetFreeDays() {
        int year = DateUtils.ACTUAL_YEAR;
        LOG.info("getFreeDaysAll");
        List<Calendar> freeDays = pl.pd.emir.commons.DateUtils.getFreeDaysAll();
        assertTrue(freeDays.contains(new GregorianCalendar(year, 0, 6)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 4, 1)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 4, 3)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 7, 15)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 10, 1)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 10, 11)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 11, 25)));
        assertTrue(freeDays.contains(new GregorianCalendar(year, 11, 26)));
        assertTrue(freeDays.size() == 13);
    }

    @Test
    public void noToMonthBase() {
        boolean bol = true;
        //        for (int x = 199; x < 205; x++) {
        //            Date monthBase = DateUtils.noToMonthBase(x);
        //            System.err.println(" "+monthBase.toString()+ "  nr " + x);
        //            int monthBaseToNo = DateUtils.monthBaseToNo(monthBase);
        //            if (x != monthBaseToNo) {
        //                System.err.println("Wygenerwowany " +monthBaseToNo+" zadany "+x);
        //                bol = false;
        //            }
        //        }
//        Calendar instance30 = new GregorianCalendar(2013, 6, 30);
//        Calendar instance29 = new GregorianCalendar(2013, 6, 29);
//        Calendar instance28 = new GregorianCalendar(2013, 6, 28);
//        Calendar instance27 = new GregorianCalendar(2013, 6, 27);
//        Calendar instance26 = new GregorianCalendar(2013, 6, 26);
//        Date date = instance30.getTime();
//        int get = instance30.get(Calendar.DAY_OF_WEEK);
//        int get1 = instance29.get(Calendar.DAY_OF_WEEK);
//        int get2 = instance28.get(Calendar.DAY_OF_WEEK);
//        int get3 = instance27.get(Calendar.DAY_OF_WEEK);
//        int get4 = instance26.get(Calendar.DAY_OF_WEEK);
//        //        instance29.get(Calendar.DAY_OF_WEEK);
//
//                int fri= Calendar.FRIDAY;
        assertTrue(bol);
    }

    /**
     * Test of getEasterDate method, of class DateUtils.
     */
    @Test
    public void testPrepareListDate() {
        LOG.info("prepareListDate");
        GregorianCalendar calendar = new GregorianCalendar();
        Date now = calendar.getTime();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 20);
        Date futureDay = calendar.getTime();
        List<Date> result = pl.pd.emir.commons.DateUtils.prepareListDate(now, futureDay);
        assertNotNull(result);
        assertEquals("Błędna zwartość listy", 21, result.size());
    }

    /**
     * Test of getEasterDate method, of class DateUtils.
     */
    @Test
    public void testPrepareListWorkingDate() {
        LOG.info("testPrepareListWorkingDate");
        GregorianCalendar calendar = new GregorianCalendar();
        Date now = calendar.getTime();
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 20);
        Date futureDay = calendar.getTime();
        List<Date> nonWorkingDate = new ArrayList<>();
        nonWorkingDate.add(pl.pd.emir.commons.DateUtils.getNextWorkingDay(now));
        List<Date> result = pl.pd.emir.commons.DateUtils.prepareListDate(now, futureDay, nonWorkingDate);
        assertNotNull(result);
        assertEquals("Błędna zwartość listy", 14, result.size());
    }

    /**
     * Test of getEasterDate method, of class DateUtils.
     */
    @Test
    public void testGetNexWorkingDate() {
        LOG.info("testGetNexWorkingDate");
        GregorianCalendar calendar = new GregorianCalendar();
        Date now = calendar.getTime();
        Date next = pl.pd.emir.commons.DateUtils.getNextWorkingDay(now);
        calendar.setTime(next);
        assertNotSame("Dzień niepracujący -- sobota", Calendar.SATURDAY, calendar.get(Calendar.SATURDAY));
        assertNotSame("Dzień niepracujący -- niedziela", Calendar.SUNDAY, calendar.get(Calendar.SUNDAY));
    }
}
