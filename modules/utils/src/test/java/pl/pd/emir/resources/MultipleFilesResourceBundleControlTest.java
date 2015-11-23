package pl.pd.emir.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultipleFilesResourceBundleControlTest {

    public MultipleFilesResourceBundleControlTest() {
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
    public void testNewBundle() throws Exception {
        String baseName = "messages";
        Locale locale = Locale.ROOT;
        String format = "";
        ClassLoader loader = null;
        boolean reload = false;
        MultipleFilesResourceBundleControl instance = new MultipleFilesResourceBundleControl("properties");
        ResourceBundle result = instance.newBundle(baseName, locale, format, loader, reload);
        assertTrue(result.containsKey("test"));
        assertEquals(result.getString("test"), "TEST text");
    }

    @Test
    public void testNewBundleFalse() throws Exception {
        String baseName = "messages_test";
        Locale locale = Locale.ROOT;
        String format = "";
        ClassLoader loader = null;
        boolean reload = false;
        MultipleFilesResourceBundleControl instance = new MultipleFilesResourceBundleControl("properties");
        ResourceBundle result = instance.newBundle(baseName, locale, format, loader, reload);
        assertNull(result);
    }
}
