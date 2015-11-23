package pl.pd.emir.resources;

import pl.pd.emir.resources.ResourceBundleURLComparator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourceBundleURLComparatorTest {

    public ResourceBundleURLComparatorTest() {
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

    /**
     * Test of compare method, of class ResourceBundleURLComparator.
     */
    @Test
    public void testCompare() {
        try {
            URL o1 = new URL("http://test/url");
            URL o2 = new URL("http://test/url");
            URL o3 = new URL("ftp://test/url");
            ResourceBundleURLComparator instance = new ResourceBundleURLComparator(o1);
            // 1 = the same
            int expResult = 1;
            int result = instance.compare(o1, o2);
            assertEquals(expResult, result);
            // 0
            expResult = 0;
            result = instance.compare(null, null);
            assertEquals(expResult, result);
            // -1
            expResult = -1;
            result = instance.compare(null, o2);
            assertEquals(expResult, result);
            // -1
            expResult = -1;
            result = instance.compare(o3, o2);
            assertEquals(expResult, result);

        } catch (MalformedURLException ex) {
            Logger.getLogger(ResourceBundleURLComparatorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
}
