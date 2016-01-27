package pl.pd.emir.reports.utils;

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
