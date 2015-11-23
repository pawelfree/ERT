package pl.pd.emir.commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringUtilTest {

    public StringUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testPrintCollection() {
        Collection collection = Arrays.asList("One", "Two", "Three");
        String expResult = "[One, Two, Three]";
        String result = StringUtil.printCollection(collection);
        assertEquals(expResult, result);
    }

    @Test
    public void testPrintCollectionEmpty() {
        Collection collection = new HashSet();
        String expResult = "[]";
        String result = StringUtil.printCollection(collection);
        assertEquals(expResult, result);
    }
}
