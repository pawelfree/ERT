package pl.pd.emir.reports.utils;


import java.util.List;
import java.util.Map;
import pl.pd.emir.reports.model.TransactionCSVWrapper;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrintUtilsTest {

    public PrintUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testReflectionGetValuesFromObjects() throws IllegalArgumentException, IllegalAccessException {

        TransactionCSVWrapper wrapper = new TransactionCSVWrapper();
        wrapper.setField("vfield");
        wrapper.setField0("vfield0");
        wrapper.setField1("vfield1");
        wrapper.setField2("vfield2");
        wrapper.setField3("vfield3");
        wrapper.setField4("vfield4");
        wrapper.setField5("vfield5");
        Map<String, String> values = PrintUtils.getValuesFromFields(wrapper);
        assertTrue(0 == values.get("field").compareTo("vfield"));
        assertTrue(0 == values.get("field0").compareTo("vfield0"));
        assertTrue(0 == values.get("field01").compareTo("vfield1"));
        assertTrue(0 == values.get("field02").compareTo("vfield2"));
        assertTrue(0 == values.get("field03").compareTo("vfield3"));

    }

    @Test
    public void testReflectionMapToString() throws IllegalArgumentException, IllegalAccessException {

        TransactionCSVWrapper wrapper = new TransactionCSVWrapper();
        wrapper.setField("vfield");
        wrapper.setField0("vfield0");
        wrapper.setField1("vfield1");
        wrapper.setField2("vfield2");
        wrapper.setField3("vfield3");
        wrapper.setField4("vfield4");
        wrapper.setField5("vfield5");
        Map<String, String> values = PrintUtils.getValuesFromFields(wrapper);
        List<String> list = PrintUtils.mapToList(values);
        assertTrue(0 == list.get(0).compareTo("vfield"));
        assertTrue(0 == list.get(1).compareTo("vfield0"));
        assertTrue(0 == list.get(2).compareTo("vfield1"));
        assertTrue(0 == list.get(3).compareTo("vfield2"));
        assertTrue(0 == list.get(4).compareTo("vfield3"));
    }

    @Test
    public void testConvertRowToCSVLine() throws IllegalArgumentException, IllegalAccessException {

        TransactionCSVWrapper wrapper = new TransactionCSVWrapper();
        wrapper.setField("vfield");
        wrapper.setField0("vfield0");
        wrapper.setField1("vfield1");
        wrapper.setField2("vfield2");
        wrapper.setField3("vfield3");
        wrapper.setField4("vfield4");
        wrapper.setField5("vfield5");
        Map<String, String> values = PrintUtils.getValuesFromFields(wrapper);
        List<String> list = PrintUtils.mapToList(values);
        String tmp = PrintUtils.convertRowToCSVLine(list);
        assertTrue(0 == "vfield;vfield0;vfield1;vfield2;vfield3;vfield4;vfield5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;".compareTo(tmp));

    }

    @Test
    public void testgetRecordFromLine() {
        assertTrue(PrintUtils.getRecordFromLine("Brak obowiązkowej wartości pola BENEFICIARY_CODE / BCTRPTYDTLS_BNFCRYID_ID dla rekordu 1173122").compareTo("1173122") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Klient jest niepoprawny dla rekordu 1173122").compareTo("1173122") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Klient jest niepoprawny dla rekordu 1173124").compareTo("1173124") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Klient jest niepoprawny dla rekordu 866955").compareTo("866955") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Brak obowiązkowej wartości pola BENEFICIARY_CODE / BCTRPTYDTLS_BNFCRYID_ID dla rekordu 1173114").compareTo("1173114") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Brak obowiązkowej wartości pola BENEFICIARY_CODE / BCTRPTYDTLS_BNFCRYID_ID dla rekordu ").compareTo("") == 0);
        assertTrue(PrintUtils.getRecordFromLine("Brak obowiązkowej wartości pola BENEFICIARY_CODE / BCTRPTYDTLS_BNFCRYID_ID dla rekordu").compareTo("") == 0);
    }
}
