package pl.pd.emir.kdpw.xml.builder;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.enums.InstitutionIdType;
import kdpw.xsd.IKDPWDocument;
import kdpw.xsd.trar_ins_002.CounterpartyAndProductIdentification;
import kdpw.xsd.trar_ins_002.CounterpartyIdentification;
import kdpw.xsd.trar_ins_002.FunctionOfMessage;
import kdpw.xsd.trar_ins_002.GeneralInformation;
import kdpw.xsd.trar_ins_002.InstitutionCode;
import kdpw.xsd.trar_ins_002.KDPWDocument;
import kdpw.xsd.trar_ins_002.ProductIdentification;
import kdpw.xsd.trar_ins_002.TRInstitutionCode;
import kdpw.xsd.trar_ins_002.TrarIns00202;
import kdpw.xsd.trar_ins_002.ValuationDetails;
import kdpw.xsd.trar_ins_002.ValuationInformation;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class XmlBuilderTest {

    private static final String TEST_RSC = "src/test/resources/";

    private static final String BUILD_01 = "builder/build01.xml";
    private static final String ERROR_01 = "builder/error01.xml";

    @Test
    public void testBuild() throws Exception {
        IKDPWDocument kdpwDocument = initKDPWDocument();

        String expResult = StringUtil.stripWhitespaces(FileUtils.readFileToString(new File(String.format("%s%s", TEST_RSC, BUILD_01))));
        String result = StringUtil.stripWhitespaces(XmlBuilder.build(kdpwDocument));
    }

    @Test
    public void testReadMessage() throws Exception {
        String xmlMessage = FileUtils.readFileToString(new File(String.format("%s%s", TEST_RSC, BUILD_01)));
        KDPWDocument expResult = (KDPWDocument) initKDPWDocument();
        IKDPWDocument result = XmlBuilder.readMessage(xmlMessage);
        assertNotNull(result);
        assertTrue(result instanceof KDPWDocument);
        assertEquals(expResult.getTrarIns00202().size(), ((KDPWDocument) result).getTrarIns00202().size());
        assertEquals(1, ((KDPWDocument) result).getTrarIns00202().size());
        assertEquals("A", ((KDPWDocument) result).getTrarIns00202().get(0).getGnlInf().getActnTp());
    }

    //Test
    @SuppressWarnings("CallToThreadDumpStack")
    public void testReadMessage2() throws Exception {
        String xmlMessage = FileUtils.readFileToString(new File(String.format("%s%s", TEST_RSC, ERROR_01)));
        IKDPWDocument result = null;
        try {
            result = XmlBuilder.readMessage(xmlMessage);
            assertTrue(true);
        } catch (XmlParseException ex) {
            System.out.println("EX " + ex.getMessage());
            assertTrue(ex.getCause().getMessage(), false);
        }
    }

    /**
     * Test of loadSchema method, of class XmlBuilder.
     */
    @Test
    public void testLoadSchema_failure() throws Exception {
        IKDPWDocument kdpwDocument = (IKDPWDocument) new KDPWDocument();
        XmlBuilder.loadSchema(XmlBuilder.getValidationSchemaPath(kdpwDocument));
    }

    /**
     * Test of getValidationSchemaPath method, of class XmlBuilder.
     */
    @Test(expected = XmlParseException.class)
    public void testGetValidationSchemaPath_nullObject() throws Exception {
        IKDPWDocument kdpwDocument = null;
        XmlBuilder.getValidationSchemaPath(kdpwDocument);
    }

    /**
     * Test of getValidationSchemaPath method, of class XmlBuilder.
     */
    @Test
    public void testGetValidationSchemaPath_success() throws Exception {
        IKDPWDocument kdpwDocument = (IKDPWDocument) new KDPWDocument();
        String expResult = "schema/trar.ins.002.02.xsd";
        String result = XmlBuilder.getValidationSchemaPath(kdpwDocument);

        assertEquals(expResult, result);
    }

    /**
     * Test of getValidationSchemaProperty method, of class XmlBuilder.
     */
    @Test
    public void testGetValidationSchemaProperty_success() throws Exception {
        Properties validationSchemaProperty = XmlBuilder.getValidationSchemaProperty();
        assertNotNull(validationSchemaProperty);
    }

    private GeneralInformation initGeneralInformation() {
        GeneralInformation result = new GeneralInformation();
        TRInstitutionCode trRptId = new TRInstitutionCode();
        trRptId.setId("PmryId");
        trRptId.setTp(InstitutionIdType.LEIC.name());
        result.setTRRprtId(trRptId);
        result.setSndrMsgRef("sndrMsgRef");
        result.setFuncOfMsg(FunctionOfMessage.NEWM);
        result.setActnTp("A");
        result.setEligDt(XmlUtils.formatDate(new Date(), Constants.ISO_DATE));
        result.setDtlLvl("1");

        return result;
    }

    private ValuationDetails initValuationDeatils() {
        ValuationDetails result = new ValuationDetails();
        CounterpartyAndProductIdentification ctrPtyAndPrdctInf = new CounterpartyAndProductIdentification();
        CounterpartyIdentification ctrPtyInf = new CounterpartyIdentification();
        TRInstitutionCode ctrPtyTRId = new TRInstitutionCode();
        ctrPtyTRId.setId("PmryId");
        ctrPtyTRId.setTp(InstitutionIdType.OTHR.name());
        ctrPtyInf.setCtrPtyTRId(ctrPtyTRId);
        InstitutionCode clrMmbId = new InstitutionCode();
        clrMmbId.setId("cId");
        clrMmbId.setTp("TP02");
        ctrPtyInf.setClrMmbId(clrMmbId);
        ctrPtyAndPrdctInf.setCtrPtyInf(ctrPtyInf);

        ProductIdentification prdctInf = new ProductIdentification();
        prdctInf.setTxnm("T");
        prdctInf.setPrdctId1("PrdctId1");
        prdctInf.setTechUndrlyg("Undrlyg");
        ctrPtyAndPrdctInf.setPrdctInf(prdctInf);

        result.setCtrPtyAndPrdctInf(ctrPtyAndPrdctInf);

        ValuationInformation valtnInf = new ValuationInformation();
        valtnInf.setMtMVal(BigDecimal.ONE);
        valtnInf.setCcy("PLN");
//        valtnInf.setValtnDtTm(XmlUtils.formatDate(new Date("2013-08-27T00:00:15"), Constants.ISO_DATE_TIME));
        valtnInf.setValtnDtTm(XmlUtils.formatDate(new Date(), Constants.ISO_DATE_TIME));
        valtnInf.setValtnTp("V");
        result.setValtnInf(valtnInf);

        return result;
    }

    private IKDPWDocument initKDPWDocument() {
        KDPWDocument result = new KDPWDocument();
        result.setRcvr("rcvr");
        result.setSndr("sndr");
        TrarIns00202 trarIns00202 = new TrarIns00202();
        result.getTrarIns00202().add(trarIns00202);
        GeneralInformation gnlInf = initGeneralInformation();
        trarIns00202.setGnlInf(gnlInf);
        ValuationDetails vaItnDtls = initValuationDeatils();
        trarIns00202.setValtnDtls(vaItnDtls);
        return (IKDPWDocument) result;
    }
}
