package pl.pd.emir.imports;

import pl.pd.emir.imports.ImportFaillogUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ImportFaillogUtilsTest {

    public ImportFaillogUtilsTest() {
    }

    @Test
    public void testGetString_ImportFaillogUtilsImportFaillogKey() {
        ImportFaillogUtils.ImportFaillogKey key = ImportFaillogUtils.ImportFaillogKey.EMPTY_CLIENT_ID;
        String expResult = "Brak obowiązkowej wartości pola ID_KLIENTA dla linii: %s";
        String result = ImportFaillogUtils.getString(key);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetString_ImportFaillogUtilsImportFaillogKey_ObjectArr() {
        Object[] params = new Object[]{"test"};
        ImportFaillogUtils.ImportFaillogKey key = ImportFaillogUtils.ImportFaillogKey.EMPTY_CLIENT_ID;
        String expResult = "Brak obowiązkowej wartości pola ID_KLIENTA dla linii: test";
        String result = ImportFaillogUtils.getString(key, params);
        assertEquals(expResult, result);
    }

}
