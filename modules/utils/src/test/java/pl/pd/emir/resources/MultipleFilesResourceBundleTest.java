package pl.pd.emir.resources;

import pl.pd.emir.resources.MultipleFilesResourceBundle;
import java.util.Enumeration;
import java.util.Locale;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultipleFilesResourceBundleTest {

    public MultipleFilesResourceBundleTest() {
    }

    @Test
    public void testHandleGetObject() {
        FacesContext context = ContextMocker.mockFacesContext();
        try {
            Application app = mock(Application.class);
            when(context.getApplication()).thenReturn(app);
            when(app.getDefaultLocale()).thenReturn(Locale.ROOT);
            String key = "test";
            MultipleFilesResourceBundle instance = new MultipleFilesResourceBundle();
            Object expResult = "TEST text";
            Object result = instance.handleGetObject(key);
            assertEquals(expResult.toString(), result.toString());
            result = instance.handleGetObject(null);
            assertTrue(result.toString().isEmpty());
            MultipleFilesResourceBundle instance2 = new MultipleFilesResourceBundle("messages");
            Object expResult2 = "TEST text";
            Object result2 = instance2.handleGetObject(key);
            assertEquals(expResult2.toString(), result2.toString());
        } finally {
            context.release();
        }
    }

    /**
     * Test of getKeys method, of class MultipleFilesResourceBundle.
     */
    @Test
    public void testGetKeys() {
        FacesContext context = ContextMocker.mockFacesContext();
        try {
            Application app = mock(Application.class);
            when(context.getApplication()).thenReturn(app);
            when(app.getDefaultLocale()).thenReturn(Locale.ROOT);
            MultipleFilesResourceBundle instance = new MultipleFilesResourceBundle();
            Enumeration result = instance.getKeys();
            assertNotNull(result);
            assertTrue(result.hasMoreElements());
        } finally {
            context.release();
        }

    }
}
