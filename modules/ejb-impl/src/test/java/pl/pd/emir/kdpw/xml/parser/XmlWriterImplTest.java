package pl.pd.emir.kdpw.xml.parser;

import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.enums.InstitutionIdType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class XmlWriterImplTest {

    public XmlWriterImplTest() {
        super();
    }

    @Test
    public void testGetBankRprtId_emptyData() {
        Bank bank = new Bank();
        XmlWriterImpl instance = new XmlWriterImplImpl();
        String result = instance.getBankOrClientRprtId(bank.getInstitution());
        assertNull(result);
    }

    @Test
    public void testGetBankRprtId_success() {
        Bank bank = new Bank();
        InstitutionData instData = new InstitutionData("insID", InstitutionIdType.LEIC);
        Institution institution = new Institution(instData, null);
        bank.setInstitution(institution);
        XmlWriterImpl instance = new XmlWriterImplImpl();
        String result = instance.getBankOrClientRprtId(bank.getInstitution());
        assertEquals("insID", result);
    }

    @Test
    public void testGetBankRprtIdType_emptyData() {
        Bank bank = new Bank();
        XmlWriterImpl instance = new XmlWriterImplImpl();
        String result = instance.getBankOrClientRprtIdType(bank.getInstitution());
        assertNull(result);
    }

    @Test
    public void testGetBankRprtIdType_success() {
        Bank bank = new Bank();
        InstitutionData instData = new InstitutionData("insID", InstitutionIdType.LEIC);
        Institution institution = new Institution(instData, null);
        bank.setInstitution(institution);
        XmlWriterImpl instance = new XmlWriterImplImpl();
        String result = instance.getBankOrClientRprtIdType(bank.getInstitution());
        assertEquals("LEIC", result);
    }

    @Test
    public void testGetDtlLvl() {
        String expResult = "S";
        String result = XmlWriterImpl.getDtlLvl();
        assertEquals(expResult, result);
    }

    @Test
    public void testNotEmpty_allEmpty() {
        String var1 = null;
        Integer var2 = null;
        boolean result = XmlWriterImpl.allNotEmpty(var2, null, null, var1);
        assertFalse(result);
    }

    @Test
    public void testNotEmpty_singleParameter() {
        Integer var1 = null;
        boolean result = XmlWriterImpl.allNotEmpty(var1);
        assertFalse(result);

        String var2 = "";
        result = XmlWriterImpl.allNotEmpty(var2);
        assertFalse(result);

        String var3 = "abc";
        result = XmlWriterImpl.allNotEmpty(var3);
        assertTrue(result);
    }

    @Test
    public void testNotEmpty_multi_failure() {
        String var1 = "1234";
        Integer var2 = null;
        boolean result = XmlWriterImpl.allNotEmpty(var2, null, null, var1);
        assertFalse(result);
    }

    @Test
    public void testNotEmpty_multi_success() {
        String var1 = "1234";
        Integer var2 = 1;
        boolean result = XmlWriterImpl.allNotEmpty(var2, "a", 10L, var1);
        assertTrue(result);
    }

    @Test
    public void testNullOnEmpty_resultNull() {
        StringBuilder builder = new StringBuilder();
        String result = XmlWriterImpl.nullOnEmpty(builder);
        assertNull(result);
    }

    @Test
    public void testNullOnEmpty_resultSuccess() {
        StringBuilder builder = new StringBuilder();
        builder.append("test");
        String result = XmlWriterImpl.nullOnEmpty(builder);
        assertNotNull(result);
        assertEquals("test", result);
    }

    class XmlWriterImplImpl extends XmlWriterImpl {

        @Override
        public String getActpTp() {
            return "";
        }
    }
}
