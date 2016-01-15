package pl.pd.emir.kdpw.xml.parser;

import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.enums.CountryCode;
import kdpw.xsd.trar_ins_005.CounterpartyAddressAndSectorDetails;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class XmlBankChangeWriterImplTest {

    @Test
    public void testGetCtrPtyAddressAndSector_empty_bank() throws Exception {
        Bank bank = new Bank();
        XmlBankChangeWriter instance = new XmlBankChangeWriter();
        CounterpartyAddressAndSectorDetails result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
    }

    @Test
    public void testGetCtrPtyAddressAndSector_country_code() throws Exception {
        Bank bank = new Bank();
        bank.setCountryCode(CountryCode.PL);
        XmlBankChangeWriter instance = new XmlBankChangeWriter();
        CounterpartyAddressAndSectorDetails result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
    }

    @Test
    public void testGetCtrPtyAddressAndSector_xml_name() throws Exception {
        Bank bank = new Bank();
        bank.setBankName("name");
        XmlBankChangeWriter instance = new XmlBankChangeWriter();
        CounterpartyAddressAndSectorDetails result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
    }

    @Test
    public void testGetCtrPtyAddressAndSector_domicile() throws Exception {
        Bank bank = new Bank();
        bank.setInstitution(new Institution());
        XmlBankChangeWriter instance = new XmlBankChangeWriter();

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress("85-133", null, null, null, null, null));
        CounterpartyAddressAndSectorDetails result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("85-133", result.getDmcl().getPstCd());

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress(null, "Bydgoszcz", null, null, null, null));
        result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("Bydgoszcz", result.getDmcl().getTwnNm());

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress(null, null, "Kasprzaka", null, null, null));
        result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("Kasprzaka", result.getDmcl().getStrtNm());

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress(null, null, null, "4", null, null));
        result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("4", result.getDmcl().getBldgId());

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress(null, null, null, null, "1", null));
        result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("1", result.getDmcl().getPrmsId());

        bank.getInstitution().setInstitutionAddr(new InstitutionAddress(null, null, null, null, null, "details"));
        result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("details", result.getDmcl().getDmclDtls());

    }

    @Test
    public void testGetCtrPtyAddressAndSector_contrPartyIndustry() throws Exception {
        Bank bank = new Bank();
        bank.setContrPartyIndustry("ind");
        XmlBankChangeWriter instance = new XmlBankChangeWriter();
        CounterpartyAddressAndSectorDetails result = instance.getCtrPtyAddressAndSector(bank);
        assertNotNull(result);
        assertEquals("ind", result.getCorpSctr());
    }

}
