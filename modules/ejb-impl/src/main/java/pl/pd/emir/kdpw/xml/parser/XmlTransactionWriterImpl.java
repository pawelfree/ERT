package pl.pd.emir.kdpw.xml.parser;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import kdpw.xsd.trar_ins_001.ActiveCurrencyAnd20Amount;
import kdpw.xsd.trar_ins_001.ActiveCurrencyAnd20AmountN;
import kdpw.xsd.trar_ins_001.ActiveOrHistoricCurrencyAnd20AmountNegative;
import kdpw.xsd.trar_ins_001.ClearingObligationCode;
import kdpw.xsd.trar_ins_001.CollateralisationType1Code;
import kdpw.xsd.trar_ins_001.CommonTradeDataReport171;
import kdpw.xsd.trar_ins_001.ContractDetailsTRN;
import kdpw.xsd.trar_ins_001.ContractType31;
import kdpw.xsd.trar_ins_001.ContractValuationDataTRN;
import kdpw.xsd.trar_ins_001.CounterpartyOtherTRN;
import kdpw.xsd.trar_ins_001.CounterpartySpecificDataTRN;
import kdpw.xsd.trar_ins_001.CounterpartyTRN;
import kdpw.xsd.trar_ins_001.CounterpartyTradeNatureTR;
import kdpw.xsd.trar_ins_001.CurrencyExchange101;
import kdpw.xsd.trar_ins_001.ExchangeRateBasis1;
import kdpw.xsd.trar_ins_001.FinancialInstrumentContractType2Code;
import kdpw.xsd.trar_ins_001.FixedRateTR;
import kdpw.xsd.trar_ins_001.FloatingRateTR;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.entity.Client;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.MultiGeneratorKey;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.kdpw.api.TransactionToRepository;
import pl.pd.emir.kdpw.api.exception.KdpwServiceException;
import pl.pd.emir.kdpw.xml.builder.XmlBuilder;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.modules.kdpw.adapter.api.TransactionWriter;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionWriterResult;
import kdpw.xsd.trar_ins_001.GeneralInformation;
import kdpw.xsd.trar_ins_001.InterestRate11Choice1;
import kdpw.xsd.trar_ins_001.InterestRateLegs41;
import kdpw.xsd.trar_ins_001.KDPWDocument;
import kdpw.xsd.trar_ins_001.MasterAgreementTR;
import kdpw.xsd.trar_ins_001.ObjectFactory;
import kdpw.xsd.trar_ins_001.OptionParty1Code;
import kdpw.xsd.trar_ins_001.OrganisationIdentification3Choice1;
import kdpw.xsd.trar_ins_001.PhysicalTransferType4Code;
import kdpw.xsd.trar_ins_001.ProductClassification1Choice;
import kdpw.xsd.trar_ins_001.ProductType4Code1;
import kdpw.xsd.trar_ins_001.RegulationIndicator;
import kdpw.xsd.trar_ins_001.SecuritiesTransactionPrice7ChoiceTR;
import kdpw.xsd.trar_ins_001.TradeClearingTR;
import kdpw.xsd.trar_ins_001.TradeCollateralReportTRN;
import kdpw.xsd.trar_ins_001.TradeConfirmationTR;
import kdpw.xsd.trar_ins_001.TradeConfirmationTypeRT;
import kdpw.xsd.trar_ins_001.TradeNewTransactionTR;
import kdpw.xsd.trar_ins_001.TradeReportChoiceTR;
import kdpw.xsd.trar_ins_001.TradeTransaction101;
import kdpw.xsd.trar_ins_001.TradeTransactionReportChoiceTR;
import kdpw.xsd.trar_ins_001.TradingCapacity7Code;
import kdpw.xsd.trar_ins_001.TrarIns00103;
import kdpw.xsd.trar_ins_001.ValuationType1Code;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.embeddable.ContractData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.CommercialActity;
import pl.pd.emir.enums.Compression;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;

@Stateless
@Local(TransactionWriter.class)
public class XmlTransactionWriterImpl extends XmlWriterImpl implements TransactionWriter<TransactionToRepository> {

    @Override
    public TransactionWriterResult write(List<TransactionToRepository> list, String institutionId) {

        LOGGER.info("Write xml for transactions: {}", list.size());
        final KDPWDocument document = new KDPWDocument();
        document.setSndr(getSenderParameter());
        document.setRcvr(getReceiverParameter());

        final Date date = new Date();
        final List<Long> msgIds = numberGenerator.getNumbers(new Date(), MultiGeneratorKey.KDPW_MSG_NUMBER, list.size());
        int i = 0;
        for (TransactionToRepository transToRepo : list) {
            transToRepo.setSndrMsgRef(getSndrMsgRef(date, msgIds.get(i)));
            i++;
        }

        for (TransactionToRepository transToRepo : list) {
            if (transToRepo.getMsgType() != null) {
                LOGGER.debug("Generate item for type: {}", transToRepo.getMsgType());

                final TrarIns00103 trar = new TrarIns00103();

                trar.setGnlInf(getGeneralInfo(transToRepo.getSndrMsgRef(), institutionId));
                
                final TransactionMsgType msgType = transToRepo.getMsgType();
                if (msgType.isNew()) {
                    trar.setRpt(getNewTransactionReportChoice(transToRepo));
                } 
//
//                if (msgType.isNew() || msgType.isModification() || msgType.equals(TransactionMsgType.C) || msgType.equals(TransactionMsgType.Z)) {
//                    trar.setTradDtls(getTransactionDetails(transToRepo.getRegistable().getTransaction(), transToRepo.getMsgType()));
//                }

                document.getTrarIns00103().add(trar);
                //TODO czy to nie nadpisuje fora z poczatku metody?
                transToRepo.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());
            }
        }
        TransactionWriterResult result = null;
        String build;
        try {
            build = XmlBuilder.build(new ObjectFactory().createKDPWDocument(document).getValue());
            result = new TransactionWriterResult(list, build);
        } catch (XmlParseException ex) {
            LOGGER.error("Błąd generowania xml: " + ex);
            throw new KdpwServiceException(ex);
        }
        return result;
    }
    
    protected final TradeReportChoiceTR getNewTransactionReportChoice(final TransactionToRepository item) {
        final TradeReportChoiceTR result = new TradeReportChoiceTR();
        result.setTx(getNewTransactionReport(item));       
        return result;
    }
    
    protected final String getSndrMsgRef(Date date, Long newNumber) {
        return String.format("%s%s", DateUtils.formatDate(date, "yyMMdd"), newNumber);
    }

    protected final GeneralInformation getGeneralInfo(final String senderMessageReference, final String institutionId) {
        final GeneralInformation result = new GeneralInformation();
        //TODO jesli wysyłamy tylko wlasne transakcje to to pole pozostaje niewypełnione/puste
        //result.setRptgNtty(institutionId);
        result.setSndrMsgRef(senderMessageReference);
        return result;
    }
    
    protected final TradeTransactionReportChoiceTR getNewTransactionReport(TransactionToRepository item) {
        TradeTransactionReportChoiceTR result = new TradeTransactionReportChoiceTR();
        result.setNew(getNewTransaction(item));        
        return result;
    }
    
    protected final TradeNewTransactionTR getNewTransaction(TransactionToRepository item) {
        TradeNewTransactionTR result = new TradeNewTransactionTR();
        result.setRglntInd(RegulationIndicator.E);
        final Client client = item.getRegistable().getClient();
        //COMMENT bank(client2) i ten drugi(client)
        result.getCtrPtySpcfcData().add(getBankCounterPartySpecificData(item));
        //COMMENT rozdzielic na dwa raporty?
        if (client.getReported()) {
            //COMMENT ten drugi potem bank
            result.getCtrPtySpcfcData().add(getClientCounterPartySpecificData(item));
        }        
        result.setCmonTradData(getNewCmonTradeData(item.getRegistable().getTransaction()));
        return result;
    }
    
    protected final CommonTradeDataReport171 getNewCmonTradeData(final Transaction transaction) {
        CommonTradeDataReport171 result = new CommonTradeDataReport171();
        result.setCtrctData(getNewCtrctData(transaction));
        result.setTxData(getTxData(transaction));
        return result;
    }
    
    protected final ContractType31 getNewCtrctData(final Transaction transaction) {
        ContractType31 result = new ContractType31();
        final ContractDataDetailed contractDetailedData = transaction.getContractDetailedData();
        final ContractData contractData = contractDetailedData.getContractData();
        result.setCtrctTp(FinancialInstrumentContractType2Code.fromValue(contractData.getProd2Code()));
        //TODO tylko CU i IR 
        result.setAsstClss(ProductType4Code1.fromValue(contractData.getProd1Code())); 
        result.setCtrctDtls(getNewConntractDetails(contractDetailedData));
        return result;
    }
    
    protected final ContractDetailsTRN getNewConntractDetails(ContractDataDetailed contractDetailedData) {
        ContractDetailsTRN result = new ContractDetailsTRN();
        result.setPdctClssfctn(getPdctClssfctn());
        //TODO to chyba jest w MUFG puste
        result.setPdctId(null);
        //TODO underlying jest puste lub NA MUFG nie uzywa - refactor condition and line length
        result.setUndrlygInstrm(null);
        //TODO technical underlying nie jest uzywane
        result.setTechUndrlyg(null);
        result.setNtnlCcyFrstLeg(XmlUtils.enumName(contractDetailedData.getUnderlCurrency1Code()));
        result.setNtnlCcyScndLeg(XmlUtils.enumName(contractDetailedData.getUnderlCurrency2Code()));
        result.setDlvrblCcy(XmlUtils.enumName(contractDetailedData.getDelivCurrencyCode()));
        return result;
    }
    
    protected final ProductClassification1Choice getPdctClssfctn() {
        ProductClassification1Choice result = new ProductClassification1Choice();
        //TODO do zmiany
        result.setClssfctnFinInstrm("ABCDEF");
        return result; 
    } 
    
    protected final TradeTransaction101 getTxData(final Transaction transaction) {
        TradeTransaction101 result = new TradeTransaction101();
        TransactionDetails transactionDetails = transaction.getTransactionDetails();
        //TODO trzy linijki z poprzedniego xmla
        // result.setId(details.getSourceTransId());
        // result.setPrvsId(null); // TODO - brak informacji w dokumentacji
        // result.setTradRefNb(nullOnEmpty(details.getSourceTransRefNr()));
        //TODO remove
        System.out.println("!!! --- setUnqTradIdr " + transactionDetails.getSourceTransId());
        result.setUnqTradIdr(transactionDetails.getSourceTransId());
        //TODO w MUFG puste? 
        //result.setRptTrckgNb(null);
        //TODO w MUFG puste?
        //result.setCmplxTradId(null);
        //TODO to chyba mialo byc inaczej niz do tej pory - sprawdzic typy
        result.setTradgVn(transactionDetails.getRealizationVenue());
        //TODO zamienic typy
        result.setCmprssn(transactionDetails.getCompression() == Compression.Y);
        result.setPric(getNewPric(transactionDetails));
        result.setNtnlAmt(transactionDetails.getNominalAmount());
        result.setPricMltplr(NumberUtils.integerToBigDecimal(transactionDetails.getPriceMultiplier()));
        result.setQty(NumberUtils.integerToBigDecimal(transactionDetails.getContractCount()));
        result.setUpFrntPmt(null == transactionDetails.getInAdvanceAmount() ? null : transactionDetails.getInAdvanceAmount());
        //TODO zweryfikowac czy sa takie same enumy i czy potrzebne
        result.setDlvryTp(PhysicalTransferType4Code.fromValue(transactionDetails.getDelivType().toString()));
        //TODO czy te daty nie moga byc puste?
        result.setExctnDtTm(XmlUtils.formatDate(getUTCDate(transactionDetails.getExecutionDate()), Constants.ISO_DATE_TIME_Z));
        result.setFctvDt(XmlUtils.formatDate(transactionDetails.getEffectiveDate(), Constants.ISO_DATE));
        result.setMtrtyDt(XmlUtils.formatDate(transactionDetails.getMaturityDate(), Constants.ISO_DATE));
        result.getSttlmDt().add(XmlUtils.formatDate(transactionDetails.getSettlementDate(), Constants.ISO_DATE));
        result.setMstrAgrmt(getNewMstrAgrmt(transaction.getTransactionDetails()));
        result.setTradConf(getNewTradConf(transaction.getRiskReduce()));
        result.setTradClr(getNewTradClr(transaction.getTransactionClearing()));
        //TODO a to ciekawe - return new TradeAdditionalInformationValidator().nullOnEmpty(result);
        //TODO to powinno być inaczej rozpoznawane
        //if (null != transaction.getPercentageRateData())
        //    result.setIntrstRate(getNewIntrestRate(transaction.getPercentageRateData()));
        if (null != transaction.getCurrencyTradeData())
            result.setCcy(getNewCcy(transaction.getCurrencyTradeData()));

        return result;
    }
  
    protected final InterestRateLegs41 getNewIntrestRate(PercentageRateData percentageRateData) {
        InterestRateLegs41 result = new InterestRateLegs41();
        Objects.nonNull(percentageRateData);
        //TODO czy zawsze mamy obie nogi
        result.setFrstLeg(getNewFrstLeg(percentageRateData));
        result.setScndLeg(getNewScndLeg(percentageRateData));
        return result;
    }
    
    protected final InterestRate11Choice1 getNewFrstLeg(PercentageRateData percentageRateData) {
        InterestRate11Choice1 result = new InterestRate11Choice1();
        result.setFxd(getNewFxd1(percentageRateData));
        result.setFltg(getNewFltg1(percentageRateData));
        return result;
    }

    protected final InterestRate11Choice1 getNewScndLeg(PercentageRateData percentageRateData) {
        InterestRate11Choice1 result = new InterestRate11Choice1();
        result.setFxd(getNewFxd2(percentageRateData));
        result.setFltg(getNewFltg2(percentageRateData));
        return result;
    }    
    
    protected final FixedRateTR getNewFxd1(PercentageRateData percentageRateData) {
        FixedRateTR result = new FixedRateTR();
        result.setRate(percentageRateData.getFixedRateLeg1());
        result.setDayCnt(percentageRateData.getFixedRateDayCount());
        //TODO mamy to - percentageRateData.getFixedPaymentFreq()
        //result.setPmtFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setPmtFrqcyMltplr(BigDecimal.ONE);        
        return result;
    }
    
    protected final FixedRateTR getNewFxd2(PercentageRateData percentageRateData) {
        FixedRateTR result = new FixedRateTR();
        result.setRate(percentageRateData.getFixedRateLeg2());
        result.setDayCnt(percentageRateData.getFixedRateDayCount());
        //TODO mamy to - percentageRateData.getFixedPaymentFreq()
        //result.setPmtFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setPmtFrqcyMltplr(BigDecimal.ONE);        
        return result;
    }
    
    protected final FloatingRateTR getNewFltg1(PercentageRateData percentageRateData) {
        FloatingRateTR result = new FloatingRateTR();
        result.setRate(percentageRateData.getFloatRateLeg1());
        //TODO mamy tylko dwie linijki ponizsze
        //result.setFltgLgPmtFrqcy(null == nullOnEmpty(rateData.getFloatPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getFloatPaymentFreq())));
        //result.setFltgRateRstFrqcy(null == nullOnEmpty(rateData.getNewPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getNewPaymentFreq())));      
        
        //result.setRefFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setRefFrqcyMltplr(BigDecimal.ONE);
        //result.setPmtFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setPmtFrqcyMltplr(BigDecimal.ONE);
        //result.setRstFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setRstFrqcyMltplr(BigDecimal.ONE);
        return result;
    }
    
    protected final FloatingRateTR getNewFltg2(PercentageRateData percentageRateData) {
        FloatingRateTR result = new FloatingRateTR();
        result.setRate(percentageRateData.getFloatRateLeg2());
        //TODO mamy tylko dwie linijki ponizsze
        //result.setFltgLgPmtFrqcy(null == nullOnEmpty(rateData.getFloatPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getFloatPaymentFreq())));
        //result.setFltgRateRstFrqcy(null == nullOnEmpty(rateData.getNewPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getNewPaymentFreq())));     
        
        //result.setRefFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setRefFrqcyMltplr(BigDecimal.ONE);
        //result.setPmtFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setPmtFrqcyMltplr(BigDecimal.ONE);
        //result.setRstFrqcyTmPrd(RateBasis1CodeTR.D);
        //result.setRstFrqcyMltplr(BigDecimal.ONE);
        return result;
    }
    
    protected final CurrencyExchange101 getNewCcy(CurrencyTradeData currencyTradeData) {
        CurrencyExchange101 result = new CurrencyExchange101();
        Objects.nonNull(currencyTradeData);
        //TODO bylo ccy2
        result.setDlvrblCrossCcy(currencyTradeData.getCurrencyTradeCode().toString());
        result.setXchgRate(null == currencyTradeData.getCurrTradeExchRate() ? null : currencyTradeData.getCurrTradeExchRate());
        result.setFwdXchgRate(null == currencyTradeData.getCurrTradeFrwdRate() ? null : currencyTradeData.getCurrTradeFrwdRate());
        //TODO skad to wziac?
        result.setXchgRateBsis(getNewXchgRateBasis(currencyTradeData));
        return result;
    }
    
    protected final ExchangeRateBasis1 getNewXchgRateBasis(CurrencyTradeData currencyTradeData) {
        ExchangeRateBasis1 result = new ExchangeRateBasis1();
        //TODO jedno z nich jest zle
        //result.setBaseCcy(currencyTradeData.getCurrTradeBasis());
        //result.setQtdCcy(currencyTradeData.getCurrTradeBasis());
        result.setBaseCcy("EUR");
        result.setQtdCcy("PLN");
        return result;        
    }
    
    protected final SecuritiesTransactionPrice7ChoiceTR getNewPric(final TransactionDetails transactionDetails) {
        SecuritiesTransactionPrice7ChoiceTR result = new SecuritiesTransactionPrice7ChoiceTR();
        //TODO to wymaga wiekszego przemyslenia 
//        BigDecimal priceOrRate = transactionDetails.getUnitPrice();
//        String priceNot = XmlUtils.enumName(transactionDetails.getUnitPriceCurrency());        
//        if (null == priceOrRate || null == priceNot) {
//            priceOrRate = transactionDetails.getUnitPriceRate();
//            priceNot = "100";
//            if (null == priceOrRate) {
//                priceOrRate = new BigDecimal("999999999999999.99999");
//                priceNot = "NA";
//            }
//        }
//        result.setPricRt(priceOrRate);
//        result.setPricNot(priceNot);    
        //TODO tylko jedno z czterech
        result.setMntryVal(getNewMntry(transactionDetails));
        //TODO tego chyba nie uzywamy
        //result.setPctg(null);
        //TODO tego chyba nie uzywamy
        //result.setYld(null);
        //TODO co to za cudo
        //result.setPdgPric(null);
        //TODO a to ciekawe - return new PriceChoiceValidator().nullOnEmpty(result);
        return result;
    }
    
    protected final ActiveOrHistoricCurrencyAnd20AmountNegative getNewMntry(final TransactionDetails transactionDetails) {
        ActiveOrHistoricCurrencyAnd20AmountNegative result = new ActiveOrHistoricCurrencyAnd20AmountNegative();
        result.setValue(transactionDetails.getUnitPrice());
        result.setCcy(transactionDetails.getUnitPriceCurrency().toString());
        return result;
    }
    
    protected final MasterAgreementTR getNewMstrAgrmt(final TransactionDetails transactionDetails) {
        MasterAgreementTR result = null;
        //TODO przejzec wszystkie allNot i notAll
        if (allNotEmpty(transactionDetails.getFrameworkAggrType(), transactionDetails.getFrameworkAggrVer())) {
            result = new MasterAgreementTR();
            result.setTp(transactionDetails.getFrameworkAggrType());
            //TODO do poprawki
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(transactionDetails.getFrameworkAggrVer(), 0, 0, 0, 0, 0);
            Date date = cal.getTime(); // get back a Date object
            result.setVrsn(XmlUtils.formatDate(date, Constants.ISO_DATE));
        }
        return result;
    }
    
    //TODO dla modyfikacji bylo inaczej niz dla nowej transakcji
    protected final TradeConfirmationTR getNewTradConf(RiskReduce riskReduce) {
        TradeConfirmationTR result = new TradeConfirmationTR();
        Objects.requireNonNull(riskReduce, getClass().getName().concat(" - getTradConf"));
        //TODO przekonwertowac enumy
        result.setTp(null == riskReduce.getConfirmationType() ? TradeConfirmationTypeRT.N : TradeConfirmationTypeRT.fromValue(riskReduce.getConfirmationType().toString()));        
        result.setTmStmp(null == riskReduce.getConfirmationDate() ? null : XmlUtils.formatDate(getUTCDate(riskReduce.getConfirmationDate()), Constants.ISO_DATE_TIME_Z));
        //TODO to ciekawe - return new RiskMitigationValidator().nullOnEmpty(result);        
        return result;
    }

    protected final TradeClearingTR getNewTradClr( final TransactionClearing transactionClearing) {
        TradeClearingTR result = new TradeClearingTR();
        Objects.requireNonNull(transactionClearing);
        //TODO zmienic typ ? po co tyle enumów
        result.setClrOblgtn(ClearingObligationCode.fromValue(transactionClearing.getClearingOblig().toString()));
        //TODO zmienić typ ? po co tyle enumów
        result.setClrd(transactionClearing.getCleared() == Cleared.Y);
        result.setClrDtTm(null == transactionClearing.getClearingDate() ? null : XmlUtils.formatDate(getUTCDate(transactionClearing.getClearingDate()), Constants.ISO_DATE_TIME_Z));
        //TODO może byc tylko LEI
        //TODO null or ""
        //result.setCCP(transactionClearing.getCcpCode());
        //TODO zmienic typ - i wyeliminiowac ERR?
        result.setIntraGrp(transactionClearing.getIntergropuTrans() == null ? null : (transactionClearing.getIntergropuTrans() == IntergropuTrans.Y));
        //TODO to ciekawe - return new ClearingInformationValidator().nullOnEmpty(result);
        return result;
    }
    
    protected final ActiveCurrencyAnd20Amount createValue(BigDecimal amount, CurrencyCode currency) {
        ActiveCurrencyAnd20Amount result = null;
        if (null != currency && null != amount) {
            result = new ActiveCurrencyAnd20Amount();
            result.setValue(amount);
            result.setCcy(currency.name());
        }
        return result;
    }
            
    protected final Date getUTCDate(final Date date) {
        //TODO to chyba jest zbyt skomplikowane - napisac test
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date result = null;
        try {
            result = DateFormat.getInstance().parse(sdf.format(date));
        } catch (ParseException ex) {
            Logger.getLogger(XmlTransactionWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    protected final OrganisationIdentification3Choice1 getPartyId(BusinessEntityData businessData) {
        //TODO musi być przerobka danych - na razie zakladam, ze to jest lei
        Objects.requireNonNull(businessData, getClass().getName().concat(" - businessData - getId"));
        OrganisationIdentification3Choice1 organisation = new OrganisationIdentification3Choice1();
        if (businessData.getBeneficiaryCodeType().equals(InstitutionIdType.LEIC)) {
            organisation.setLEI(businessData.getBeneficiaryCode());
        } else {
            //TODO - tu chyba nie moze byc dowolna wartosc
            organisation.setClntId(businessData.getBeneficiaryCode());
        }
        return organisation;
    }
    
    @Override
    protected String getActpTp() {
        return null; //COMMENT - NIE uzywane przekazywane jako parametr
    }

    protected final boolean getCommercialActivity(final BusinessEntityData businessData) {
        //TODO - zamienic baze danych na boolean ze stringa
        //TODO obsłużyc nulla
        return !CommercialActity.N.equals(businessData.getCommercialActity());
    }
        
    protected final String getBrkr(final BusinessEntityData businessData) {
        String result = null;
        if (Objects.nonNull(businessData)) {
            result = businessData.getIdCode();
            //TODO - te dane są już zbędne - tylko LEI dopuszczalne
            //result.setTp(XmlUtils.enumName(businessData.getIdCodeType()));
        }
        return result;
    }

    protected final String getClrMmb(final BusinessEntityData businessData) {
        String result = null;
        if (Objects.nonNull(businessData)) {
            result = businessData.getMemberId();
            //TODO - te dane są już zbędne - tylko LEI dopuszczalne
            //result.setTp(XmlUtils.enumName(businessData.getMemberIdType()));
        }
        return result;
    }
    
    protected final TradingCapacity7Code getTradgCpcty(final BusinessEntityData businessData) {
        //TODO zamienic na wlasciwy enum w bazie
        TransactionType type  = businessData.getTransactionType();
        return type == TransactionType.A ? TradingCapacity7Code.A : TradingCapacity7Code.P; 
    }

    protected final boolean getClrTrshld(final BusinessEntityData businessData) {
        //TODO obsluzyc null
        return SettlementThreshold.Y.equals(businessData.getSettlementThreshold());
    }
    
    protected final CounterpartySpecificDataTRN getBankCounterPartySpecificData(TransactionToRepository item) {
        CounterpartySpecificDataTRN result = new CounterpartySpecificDataTRN();
        result.setCtrPty(getBankCounterparty(item));
        //TODO - change
        result.setValtn(getValtn(item.getRegistable().getTransaction().getValuation(), false));
        result.setColl(getColl(item.getRegistable().getTransaction().getProtection(), false));
        return result;
    }
    
    protected final TradeCollateralReportTRN getColl(final Protection protection, final boolean clientSide) {
        TradeCollateralReportTRN result = null;
        if (null != protection
                && (notAllEmpty(protection.getProtection(), protection.getWalletProtection(), protection.getWalletId(),
                        protection.getCurrencyCode())
                || (clientSide && null != protection.getClientAmount()) || (!clientSide && null != protection.getAmount())))  {
            result = new TradeCollateralReportTRN();
            if (null != protection.getProtection()) {
                result.setCollstn(CollateralisationType1Code.fromValue(protection.getProtection().toString()));
            }
            result.setPrtflColl(protection.getWalletProtection().getLogical());
            //pusty portfel wywala xml null lub "" zweryfikowac format danych zrodlowych
            //result.setPrtfl(protection.getWalletId());
            result.setInitlMrgnPstd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
            result.setVartnMrgnPstd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
            result.setInitlMrgnRcvd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
            result.setVartnMrgnRcvd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
            result.setXcssCollPstd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
            result.setXcssCollRcvd(createValue(new BigDecimal("999999"),CurrencyCode.PLN));
        }
        return result;
    }
        
    protected final ContractValuationDataTRN getValtn(final Valuation valuation, final boolean clientSide) {
        ContractValuationDataTRN result = null;
        if (null != valuation && null != valuation.getValuationData()) {
            final ValuationData valuationData = valuation.getValuationData();
            if (clientSide) {
                if (allNotEmpty(valuationData.getClientAmount(), valuationData.getCurrencyCode(),valuationData.getValuationDate(), valuationData.getValuationType())) {
                    result = new ContractValuationDataTRN();
                }
                else {
                    LOGGER.debug("Client data empty - getValtn");                
                }
            } else {
                if (allNotEmpty(valuationData.getAmount(), valuationData.getCurrencyCode(), valuationData.getValuationDate(), valuationData.getValuationType())) {
                result = new ContractValuationDataTRN();
                } else {
                    LOGGER.debug("Data empty - getValtn");
                }                
            }
            if (result != null){
                ActiveCurrencyAnd20AmountN amount = new ActiveCurrencyAnd20AmountN();
                amount.setCcy(XmlUtils.enumName(valuationData.getCurrencyCode()));
                if (clientSide) {
                    amount.setValue(valuationData.getClientAmount());
                } else  {
                    amount.setValue(valuationData.getAmount());
                }
                result.setCtrctVal(amount);
                result.setTmStmp(XmlUtils.formatDate(getUTCDate(valuationData.getValuationDate()), Constants.ISO_DATE_TIME_Z));
                //TODO dostosować enum do nowego typu bo zbedna konwersja
                result.setTp(ValuationType1Code.fromValue(XmlUtils.enumName(valuationData.getValuationType())));
            }
        }

        return result;
    } 
    
    protected final CounterpartySpecificDataTRN getClientCounterPartySpecificData(TransactionToRepository item) {
        CounterpartySpecificDataTRN result = new CounterpartySpecificDataTRN();
        result.setCtrPty(getClientCounterparty(item));
        //TODO - czy to jest zbędne dla klienta
        result.setValtn(getValtn(item.getRegistable().getTransaction().getValuation(), true));
        result.setColl(getColl(item.getRegistable().getTransaction().getProtection(), true));
        return result;
    }
    
    protected final CounterpartyTradeNatureTR getClientNature(String type) {
        return CounterpartyTradeNatureTR.fromValue(type);
    }
            
    protected final CounterpartyTRN getBankCounterparty(TransactionToRepository item) {
        CounterpartyTRN result = new CounterpartyTRN();
        Client client = item.getRegistable().getClient2();
        result.setRptgCtrPtyId(client.getInstitutionId());
        result.setCtrPtySd(getTransactionParty(item.getRegistable().getTransaction().getTransactionParty(), Boolean.FALSE));
//TODO to musi byc wypelnione i nie tak jak u nas max 3 literki (w rzeczywistosci jedna)
//TODO to trzeba przedyskutowac 
        //TODO dla MUFG "C"
        result.getSctr().add(client.getContrPartyIndustry());
        //TODO dla mufg "F"
        result.setNtr(getClientNature(client.getContrPartyType()));
        BusinessEntityData businessData = item.getRegistable().getTransaction().getClient2Data();
        //TODO null wywala walidacje
        //result.setBrkr(getBrkr(businessData));
        //result.setClrMmb(getClrMmb(businessData));
        result.setBnfcry(getPartyId(businessData));
        result.setTradgCpcty(getTradgCpcty(businessData));
        //TODO jesli kontrachent finansowy to tego sie nie wypełnia
        //result.setCmmrclActvty(getCommercialActivity(businessData));
        //TODO jesli kontrachent finansowy to tego sie nie wypełnia
        //result.setClrTrshld(getClrTrshld(businessData));
        result.setOthrCtrPty(getOthrCtrPty(item.getRegistable().getTransaction().getClient().getCountryCode(), item.getRegistable().getTransaction().getClientData(), !item.getRegistable().getTransaction().getClient().getNaturalPerson()));
        
        return result;
    }
    
    protected final CounterpartyTRN getClientCounterparty(TransactionToRepository item) {
        //TODO identyczna prawie jak powyzej
        CounterpartyTRN result = new CounterpartyTRN();
        Client client = item.getRegistable().getClient();
        result.setRptgCtrPtyId(client.getInstitutionId());
        result.setCtrPtySd(getTransactionParty(item.getRegistable().getTransaction().getTransactionParty(), Boolean.TRUE));
//TODO to musi byc wypelnione i nie tak jak u nas max 3 literki (w rzeczywistosci jedna)
//TODO to trzeba przedyskutowac w zaleznowci od Ntr
        result.getSctr().add(client.getContrPartyIndustry());
        result.setNtr(getClientNature(client.getContrPartyType()));        
        BusinessEntityData businessData = item.getRegistable().getTransaction().getClientData();
        //TODO null wywala walidacje
        //result.setBrkr(getBrkr(businessData));
        //result.setClrMmb(getClrMmb(businessData));
        result.setBnfcry(getPartyId(businessData));
        result.setTradgCpcty(getTradgCpcty(businessData));
        result.setCmmrclActvty(getCommercialActivity(businessData));
        result.setClrTrshld(getClrTrshld(businessData));
        result.setOthrCtrPty(getOthrCtrPty(item.getRegistable().getTransaction().getClient2().getCountryCode(), item.getRegistable().getTransaction().getClient2Data(),false));
         
        return result;
    }

    protected final CounterpartyOtherTRN getOthrCtrPty(CountryCode country, BusinessEntityData businessEntity, boolean emirOblgtn) {
        CounterpartyOtherTRN result = new CounterpartyOtherTRN();
        result.setId(getPartyId(businessEntity));
        result.setCtry(country.toString());
        result.setEMIROblgtn(emirOblgtn);
        return result;
    }
    
    private OptionParty1Code getTransactionParty(TransactionParty transactionParty, boolean clientSide) {
        //TODO - wyeliminowac transaction party - napisac test
        TransactionParty result = TransactionParty.ERR;
        if (transactionParty == null) {
            logMappingError("TransactionParty");
        } else if (clientSide) {
            result = transactionParty.opposite();
            return result == TransactionParty.B ? OptionParty1Code.B : OptionParty1Code.S;
        }
        result = transactionParty;
        return result == TransactionParty.B ? OptionParty1Code.B : OptionParty1Code.S;
    }

    private String logMappingError(String fieldName) {
        final String msg = String.format("Field %s cannot be NULL !", fieldName);
        LOGGER.error(msg);
        return msg;
    }

}
