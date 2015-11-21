package pl.pd.emir.kdpw.xml.parser;

import pl.pd.emir.kdpw.xml.parser.XmlTransactionWriterImpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.ContractData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.ClearingOblig;
import pl.pd.emir.enums.CommercialActity;
import pl.pd.emir.enums.Compression;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.enums.ContractType;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DeliverType;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.kdpw.api.TransactionToRepository;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;
import kdpw.xsd.trar_ins_001.CollateralInformation;
import kdpw.xsd.trar_ins_001.CounterpartyAddressAndSectorDetails;
import kdpw.xsd.trar_ins_001.PriceChoice;
import kdpw.xsd.trar_ins_001.RiskMitigation;
import kdpw.xsd.trar_ins_001.ValuationAndCollateralInformation;
import kdpw.xsd.trar_ins_001.ValuationInformation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class XmlTransactionWriterImplTest {

    private static final Date DATE_01_10_2013 = DateUtils.createSpecifiedDate(2013, Calendar.OCTOBER, 1);

    private static final Date DATE_02_10_2013 = DateUtils.createSpecifiedDate(2013, Calendar.OCTOBER, 2);

    private static final Date DATE_11_10_2013 = DateUtils.createSpecifiedDate(2013, Calendar.OCTOBER, 11);

    private static final Date DATE_05_11_2013 = DateUtils.createSpecifiedDate(2013, Calendar.NOVEMBER, 5);

    private static final long MSG_NUMBER = 10000000001l;

    private final XmlTransactionWriterImpl instance = new XmlTransactionWriterImpl();

    @Test
    public void defaultDateGeneration() {
        Transaction transaction = new Transaction();
        RiskMitigation rm = instance.getRiskMitigation(transaction, TransactionMsgType.N);

        assertEquals(XmlUtils.formatDate(new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime(), Constants.ISO_DATE_TIME), rm.getCnfrmtnDtTm().getDtTm());
    }

    @Test
    public void testGetCounterPtyAddressAndDetails_bank_empty() {
        Bank bank = new Bank();
        CounterpartyAddressAndSectorDetails result = instance.getCounterPtyAddressAndDetails(bank);

        assertNotNull(result);
    }

    @Test
    public void testGetCounterPtyAddressAndDetails_bank_countryCode() {
        Bank bank = new Bank();
        bank.setCountryCode(CountryCode.PL);
        CounterpartyAddressAndSectorDetails result = instance.getCounterPtyAddressAndDetails(bank);

        assertEquals("PL", result.getDmcl().getCtry().getValue());
        assertNull(result.getCorpSctr());
        assertNull(result.getNm());
        assertNull(result.getDmcl().getTwnNm());
    }

    @Test
    public void testGetCounterPtyAddressAndDetails_client_empty() {
        Client client = new Client();
        CounterpartyAddressAndSectorDetails result = instance.getCounterPtyAddressAndDetails(client);

        assertNotNull(result);
    }

    @Test
    public void testGetCounterPtyAddressAndDetails_client_countryCode() {
        Client client = new Client();
        client.setCountryCode(CountryCode.ES);
        CounterpartyAddressAndSectorDetails result = instance.getCounterPtyAddressAndDetails(client);

        assertEquals("ES", result.getDmcl().getCtry().getValue());
        assertNull(result.getCorpSctr());
        assertNull(result.getNm());
        assertNull(result.getDmcl().getTwnNm());
    }

    @Test
    public void testGetValtnInf_no_data() {
        Valuation valuation = null;
        ValuationInformation result = instance.getValtnInf(valuation, false);
        assertNull(result);

        valuation = new Valuation();
        result = instance.getValtnInf(valuation, false);
        assertNull(result);
    }

    @Test
    public void testGetValtnInf_bankSide() {
        Valuation valuation = new Valuation();
        ValuationData valuationData = new ValuationData();
        valuation.setValuationData(valuationData);
        ValuationInformation result;

        valuationData.setAmount(BigDecimal.ONE);
        valuationData.setClientAmount(BigDecimal.ZERO);
        result = instance.getValtnInf(valuation, false);
        assertNull(result);

        valuationData.setCurrencyCode(CurrencyCode.CAD);
        result = instance.getValtnInf(valuation, false);
        assertNull(result);

        valuationData.setValuationDate(DATE_01_10_2013);
        result = instance.getValtnInf(valuation, false);
        assertNull(result);

        valuationData.setValuationType(ValuationType.M);
        result = instance.getValtnInf(valuation, false);
        assertNotNull(result);

    }

    @Test
    public void testGetValtnInf_clientSide() {
        Valuation valuation = new Valuation();
        ValuationData valuationData = new ValuationData();
        valuation.setValuationData(valuationData);
        ValuationInformation result;

        valuationData.setAmount(BigDecimal.ONE);
        valuationData.setClientAmount(BigDecimal.ZERO);
        result = instance.getValtnInf(valuation, true);
        assertNull(result);

        valuationData.setCurrencyCode(CurrencyCode.CAD);
        result = instance.getValtnInf(valuation, true);
        assertNull(result);

        valuationData.setValuationDate(DATE_01_10_2013);
        result = instance.getValtnInf(valuation, true);
        assertNull(result);

        valuationData.setValuationType(ValuationType.M);
        result = instance.getValtnInf(valuation, true);
        assertNotNull(result);
    }

    @Test
    public void testGetCollateralInfo_no_data() {
        Protection protection = null;
        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertNull(result);
    }

    @Test
    public void testGetCollateralInfo_length1() {
        Protection protection = new Protection();
        protection.setProtection(DoProtection.U);

        CollateralInformation collateralInfo = instance.getCollateralInfo(protection, true);
        assertEquals("U", collateralInfo.getColltn());
    }

    @Test
    public void testGetCollateralInfo_length2() {
        Protection protection = new Protection();
        protection.setProtection(DoProtection.FC);

        CollateralInformation collateralInfo = instance.getCollateralInfo(protection, true);
        assertEquals("FC", collateralInfo.getColltn());
    }

    @Test
    public void testGetCollateralInfo_collVal_bank() {
        Protection protection = new Protection();
        protection.setProtection(DoProtection.U);
        protection.setAmount(BigDecimal.ZERO);
        protection.setClientAmount(BigDecimal.TEN);

        CollateralInformation collateralInfo = instance.getCollateralInfo(protection, false);
        assertEquals(BigDecimal.ZERO, collateralInfo.getCollVal().getValue());
    }

    @Test
    public void testGetCollateralInfo_collVal_bankData_clientSide() {
        Protection protection = new Protection();
        protection.setAmount(BigDecimal.ZERO);
        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertNull(result);
    }

    @Test
    public void testGetCollateralInfo_collVal_clientData_bankSide() {
        Protection protection = new Protection();
        protection.setClientAmount(BigDecimal.ONE);
        CollateralInformation result = instance.getCollateralInfo(protection, false);
        assertNull(result);
    }

    @Test
    public void testGetCollateralInfo_collVal_client() {
        Protection protection = new Protection();
        protection.setProtection(DoProtection.U);
        protection.setAmount(BigDecimal.ZERO);
        protection.setClientAmount(BigDecimal.TEN);

        CollateralInformation collateralInfo = instance.getCollateralInfo(protection, true);
        assertEquals(BigDecimal.TEN, collateralInfo.getCollVal().getValue());
    }

    @Test
    public void testGetCollateralInfo_protection() {
        Protection protection = new Protection();
        protection.setProtection(null);

        CollateralInformation collateralInfo = instance.getCollateralInfo(protection, true);
        assertNull(collateralInfo);
    }

    @Test
    public void testGetCollateralInfo_protectection() {
        Protection protection = new Protection();
        protection.setProtection(DoProtection.U);

        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertEquals("U", result.getColltn());
    }

    @Test
    public void testGetCollateralInfo_walletProtection() {
        Protection protection = new Protection();
        protection.setWalletProtection(YesNo.Y);

        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertEquals("Y", result.getPrtfColl().getValue());
    }

    @Test
    public void testGetCollateralInfo_walletId() {
        Protection protection = new Protection();
        protection.setWalletId("id");

        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertEquals("id", result.getPrtfId().getValue());
    }

    @Test
    public void testGetCollateralInfo_currency_code() {
        Protection protection = new Protection();
        protection.setCurrencyCode(CurrencyCode.ALL);

        CollateralInformation result = instance.getCollateralInfo(protection, true);
        assertEquals("ALL", result.getCollCcy().getValue());
    }

    @Test
    public void testGetValtnAndCollInf_no_data() {
        Transaction transaction = new Transaction();
        ValuationAndCollateralInformation result = instance.getValtnAndCollInf(transaction, TransactionMsgType.N, true);
        assertNull(result);
    }

    @Test
    public void testGetValtnAndCollInf_any_valuation() {
        Transaction transaction = new Transaction();
        transaction.setValuation(new Valuation(null, null, new ValuationData(BigDecimal.ZERO, CurrencyCode.CAD, DATE_01_10_2013, ValuationType.M)));
        ValuationAndCollateralInformation result = instance.getValtnAndCollInf(transaction, TransactionMsgType.N, false);
        assertNotNull(result);

        assertNotNull(result.getValtnInf());
        assertNull(result.getCollInf());
        assertNull(result.getCtrPtySd());
    }

    @Test
    public void testGetValtnAndCollInf_any_protection() {
        Transaction transaction = new Transaction();
        transaction.setProtection(new Protection(BANK_NIP, DATE_01_10_2013, DoProtection.U, YesNo.Y, BANK_NIP, BigDecimal.ZERO, CurrencyCode.CAD, BigDecimal.ZERO));
        ValuationAndCollateralInformation result = instance.getValtnAndCollInf(transaction, TransactionMsgType.N, true);
        assertNotNull(result);

        assertNull(result.getValtnInf());
        assertNotNull(result.getCollInf());
        assertNull(result.getCtrPtySd());
    }

    @Test
    public void testGetValtnAndCollInf_transaction_party() {
        Transaction transaction = new Transaction();
        transaction.setTransactionParty(TransactionParty.B);
        ValuationAndCollateralInformation result = instance.getValtnAndCollInf(transaction, TransactionMsgType.N, true);
        assertNotNull(result);

        assertNull(result.getValtnInf());
        assertNull(result.getCollInf());
        assertEquals("S", result.getCtrPtySd());
    }

    @Test
    public void getPriceChoiceEmpty() {
        TransactionDetails details = new TransactionDetails();
        PriceChoice priceChoice = instance.getPriceChoice(details);
        assertEquals("NA", priceChoice.getPricNot());
        assertEquals(new BigDecimal("999999999999999.99999"), priceChoice.getPricRt());
    }

    @Test
    public void getPriceChoicePrice() {
        TransactionDetails details = new TransactionDetails();
        details.setUnitPrice(BigDecimal.TEN);
        details.setUnitPriceCurrency(CurrencyCode.PLN);
        PriceChoice priceChoice = instance.getPriceChoice(details);
        assertEquals(XmlUtils.enumName(CurrencyCode.PLN), priceChoice.getPricNot());
        assertEquals(BigDecimal.TEN, priceChoice.getPricRt());
    }

    @Test
    public void getPriceChoiceRate() {
        TransactionDetails details = new TransactionDetails();
        details.setUnitPriceRate(new BigDecimal("4.63000"));
        PriceChoice priceChoice = instance.getPriceChoice(details);
        assertEquals("100", priceChoice.getPricNot());
        assertEquals(new BigDecimal(new BigInteger("463000"), 5), priceChoice.getPricRt());

    }

    private List<TransactionToRepository> getTransListTypeN() {
        final List<TransactionToRepository> result = new ArrayList<>();

        final Transaction transaction = new Transaction();

        transaction.setTransactionDate(DATE_01_10_2013);
        transaction.setTransactionParty(TransactionParty.B);

        transaction.setClient(getClientN());

        BusinessEntityData bankData = new BusinessEntityData();
        bankData.setIdCode("transNIdCode");
        bankData.setIdCodeType(InstitutionIdType.BICC);
        bankData.setMemberId("transNMemberId");
        bankData.setMemberIdType(InstitutionIdType.PLEI);
        bankData.setSettlingAccout("transSellAccount");
        bankData.setBeneficiaryCode("transBenefCode");
        bankData.setBeneficiaryCodeType(InstitutionIdType.LEIC);
        bankData.setTransactionType(TransactionType.A);
        bankData.setCommercialActity(CommercialActity.Y);
        bankData.setSettlementThreshold(SettlementThreshold.Y);
        transaction.setBankData(bankData);

        Valuation valuation = new Valuation();
        ValuationData valuationData = new ValuationData(BigDecimal.TEN, CurrencyCode.GBP, DATE_01_10_2013, ValuationType.M, BigDecimal.ZERO);
        valuation.setValuationData(valuationData);
        transaction.setValuation(valuation);

        Protection protection = new Protection(null, DATE_01_10_2013, DoProtection.U, YesNo.Y, "protWallId", BigDecimal.ONE, CurrencyCode.EUR, BigDecimal.ZERO);
        transaction.setProtection(protection);

        TransactionDetails transDetails = new TransactionDetails();
        transDetails.setSourceTransId("transSourceID001");
        transDetails.setSourceTransRefNr("transSourceREFERENCE");
        transDetails.setRealizationVenue("aBcD");
        transDetails.setCompression(Compression.N);
        transDetails.setUnitPrice(new BigDecimal("123.50"));
        transDetails.setUnitPriceCurrency(CurrencyCode.PLN);
        transDetails.setUnitPriceRate(new BigDecimal("1000"));
        transDetails.setNominalAmount(BigDecimal.ZERO);
        transDetails.setPriceMultiplier(33);
        transDetails.setContractCount(2);
        transDetails.setInAdvanceAmount(BigDecimal.TEN);
        transDetails.setDelivType(DeliverType.P);
        transDetails.setExecutionDate(DATE_02_10_2013);
        transDetails.setEffectiveDate(DateUtils.nextDay(DATE_02_10_2013));
        transDetails.setMaturityDate(DATE_11_10_2013);
        transDetails.setTerminationDate(DATE_11_10_2013);
        transDetails.setSettlementDate(DateUtils.prevDay(DATE_11_10_2013));
        transDetails.setFrameworkAggrType("agrrType");
        transDetails.setFrameworkAggrVer(12);

        transaction.setTransactionDetails(transDetails);

        ContractDataDetailed contrDetData = new ContractDataDetailed();
        contrDetData.setUnderlCountryCode(CountryCode.ES);
        contrDetData.setUnderlCurrency1Code(CurrencyCode.ALL);
        contrDetData.setUnderlCurrency2Code(CurrencyCode.AMD);
        contrDetData.setDelivCurrencyCode(CurrencyCode.PEN);
        ContractData contrData = new ContractData(ContractType.I, "code1", "code2", "3code3");
        contrDetData.setContractData(contrData);
        transaction.setContractDetailedData(contrDetData);

        RiskReduce riskRecude = new RiskReduce(DATE_05_11_2013, ConfirmationType.E);
        transaction.setRiskReduce(riskRecude);

        TransactionClearing clearing = new TransactionClearing();
        clearing.setClearingOblig(ClearingOblig.Y);
        clearing.setCleared(Cleared.Y);
        clearing.setClearingDate(DATE_02_10_2013);
        clearing.setCcpCode("clearingCODE");
        clearing.setIntergropuTrans(IntergropuTrans.Y);
        transaction.setTransactionClearing(clearing);

        result.add(new TransactionToRepository(transaction, TransactionMsgType.N));
        return result;
    }

    private static final String BANK_NIP = "7762659238";

    private static final String BANK_REGON = "357095320";

    private Bank getBank() {
        final Bank result = new Bank();

        result.setCountryCode(CountryCode.PL);

        BusinessEntity businessEntity = new BusinessEntity(BANK_NIP, BANK_REGON);
        result.setBusinessEntity(businessEntity);

        Institution institution = new Institution();
        InstitutionData institutionData = new InstitutionData("bankInstDataId", InstitutionIdType.OTHR);
        institution.setInstitutionData(institutionData);
        InstitutionAddress institutionAddress = new InstitutionAddress("85-133", "Bydgoszcz", "Piekna", "45", "9", "details");
        institution.setInstitutionAddr(institutionAddress);
        result.setInstitution(institution);

        result.setXmlBankName("xmlBankName123");

        result.setContrPartyIndustry("Q");
        result.setContrPartyType("P");
        result.setSenderIdKdpw("0001");

        return result;
    }

    private String concat(Object... list) {
        StringBuilder result = new StringBuilder();
        for (Object object : list) {
            result.append(object);
        }
        return result.toString();
    }

    private static final String CLIENT_NIP = "5528271859";

    private static final String CLIENT_REGON = "796463255";

    private Client getClientN() {
        final Client result = new Client();
        result.setCountryCode(CountryCode.FR);
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setSubjectNip(CLIENT_NIP);
        businessEntity.setSubjectRegon(CLIENT_REGON);
        result.setBusinessEntity(businessEntity);

        Institution institution = new Institution();
        institution.setInstitutionData(new InstitutionData("clientInstDataId", InstitutionIdType.PLEI));
        result.setInstitution(institution);

        result.setNaturalPerson(Boolean.TRUE);
        result.setEog("A");

        return result;
    }
}
