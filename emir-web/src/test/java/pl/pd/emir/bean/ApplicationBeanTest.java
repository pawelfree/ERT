package pl.pd.emir.bean;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationBeanTest {

    @Test
    public void testGetDataTablePaginatorTemplate() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("getDataTablePaginatorTemplate", instance.getDataTablePaginatorTemplate());
    }

    @Test
    public void testGetDataTableRowsPerTableTemplate() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDataTableRowsPerTableTemplate", instance.getDataTableRowsPerTableTemplate());
    }

    @Test
    public void testGetDataTableRows() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("getDataTableRows", instance.getDataTableRows());
    }

    @Test
    public void testGetDateFormat() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("getDateFormat", instance.getDateFormat());
    }

    @Test
    public void testGetDateYearFormat() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDateYearFormat", instance.getDateYearFormat());
    }

    @Test
    public void testGetMonthDateFormat() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetMonthDateFormat", instance.getMonthDateFormat());
    }

    @Test
    public void testGetDateTimeFormat() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDateTimeFormat", instance.getDateTimeFormat());
    }

    @Test
    public void testGetDateTimeWithoutSecondsFormat() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDateTimeWithoutSecondsFormat", instance.getDateTimeWithoutSecondsFormat());
    }

    @Test
    public void testGetTimeZone() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetTimeZone", instance.getTimeZone());
    }

    @Test
    public void testGetDetailIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDetailIcon", instance.getDetailIcon());
    }

    @Test
    public void testGetPrintIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetPrintIcon", instance.getPrintIcon());
    }

    @Test
    public void testGetSignIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetSignIcon", instance.getSignIcon());
    }

    @Test
    public void testGetTransferIcon() {
        ApplicationBean instance = new ApplicationBean();
        String expResult = "ui-icon-arrowthick-1-e";
        String result = instance.getTransferIcon();
        assertEquals("getTransferIcon", expResult, result);
    }

    @Test
    public void testGetAcceptIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetAcceptIcon", instance.getAcceptIcon());
    }

    @Test
    public void testGetRejectIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetRejectIcon", instance.getRejectIcon());
    }

    @Test
    public void testGetCancelIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetCancelIcon", instance.getCancelIcon());
    }

    @Test
    public void testGetDeleteIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDeleteIcon", instance.getDeleteIcon());
    }

    @Test
    public void testGetNewMoneyReceiptIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetNewMoneyReceiptIcon", instance.getNewMoneyReceiptIcon());
    }

    @Test
    public void testGetNewMoneySendIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetNewMoneySendIcon", instance.getNewMoneySendIcon());
    }

    @Test
    public void testGetNewOrderIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetNewOrderIcon", instance.getNewOrderIcon());
    }

    @Test
    public void testGetEditIcon() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetEditIcon", instance.getEditIcon());
    }

    @Test
    public void testGetFacesMessagesFormID() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetFacesMessagesFormID", instance.getFacesMessagesFormID());
    }

    @Test
    public void testGetDateToday() {
        ApplicationBean instance = new ApplicationBean();
        assertNotNull("GetDateToday", instance.getDateToday());
    }

    @Test
    public void testGetLastDataTableRows() {
        ApplicationBean instance = new ApplicationBean();
        String expResult = "";
        instance.setLastDataTableRows(expResult);
        String result = instance.getLastDataTableRows();
        assertEquals("GetLastDataTableRows", expResult, result);
    }

    @Test
    public void testIsSsoVersion() {
        ApplicationBean instance = new ApplicationBean();
        boolean expResult = false;
        boolean result = instance.isSsoVersion();
        assertEquals("isSsoVersion", expResult, result);
    }

    @Test
    public void testFormatDate() {
        Date date = null;
        String pattern = "yyyy-MM-dd";
        ApplicationBean instance = new ApplicationBean();
        String expResult = "";
        String result = instance.formatDate(date, pattern);
        assertEquals("formatDate", expResult, result);
        date = new Date();
        result = instance.formatDate(date, pattern);
        assertNotNull("formatDate", result);
    }
}
