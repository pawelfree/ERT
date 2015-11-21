package pl.pd.emir.commons;

import pl.pd.emir.commons.ValidationUtils;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ValidationUtilsTest {

    @Test
    public void testValidREGON_valid() {
        String regon = "017389170";
        boolean result = ValidationUtils.validREGON(regon);
        assertTrue(result);
    }

    @Test
    public void testValidREGON_invalid() {
        String regon = "017389171";
        boolean result = ValidationUtils.validREGON(regon);
        assertFalse(result);
    }

    @Test
    public void testValidNIP1() {
        String nip = " 9531542419";
        assertTrue(ValidationUtils.validNIP(nip));
    }

    @Test
    public void testValidNIP2() {
        String nip = " a531542419";
        assertFalse(ValidationUtils.validNIP(nip));
    }
}
