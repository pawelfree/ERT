package pl.pd.emir.kdpw.xml.parser;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.CommodityTradeData;
import pl.pd.emir.embeddable.ContractData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.MultiGeneratorKey;
import pl.pd.emir.enums.TransactionMsgType;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.kdpw.api.TransactionToRepository;
import pl.pd.emir.kdpw.api.exception.KdpwServiceException;
import pl.pd.emir.kdpw.xml.builder.XmlBuilder;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;
import static pl.pd.emir.kdpw.xml.parser.XmlWriterImpl.nullOnEmpty;
import pl.pd.emir.modules.kdpw.adapter.api.TransactionWriter;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionWriterResult;
import kdpw.xsd.trar_ins_001.ClearingInformation;
import kdpw.xsd.trar_ins_001.CollateralInformation;
import kdpw.xsd.trar_ins_001.CommodityTrade;
import kdpw.xsd.trar_ins_001.ContractType;
import kdpw.xsd.trar_ins_001.CounterpartyAddressAndSectorDetails;
import kdpw.xsd.trar_ins_001.CounterpartyDetails;
import kdpw.xsd.trar_ins_001.CounterpartyInformation;
import kdpw.xsd.trar_ins_001.CountryCodeOrDelete;
import kdpw.xsd.trar_ins_001.CurrencyCodeOrDelete;
import kdpw.xsd.trar_ins_001.DateAndDateTimeChoice;
import kdpw.xsd.trar_ins_001.DateAndDateTimeChoiceOrDelete;
import kdpw.xsd.trar_ins_001.Domicile;
import kdpw.xsd.trar_ins_001.FXTrade;
import kdpw.xsd.trar_ins_001.FunctionOfMessage;
import kdpw.xsd.trar_ins_001.GeneralInformation;
import kdpw.xsd.trar_ins_001.InstitutionCode;
import kdpw.xsd.trar_ins_001.InstitutionCodeOrDelete;
import kdpw.xsd.trar_ins_001.InterestRateTrade;
import kdpw.xsd.trar_ins_001.KDPWDocument;
import kdpw.xsd.trar_ins_001.Linkages;
import kdpw.xsd.trar_ins_001.Max100TextOrDelete;
import kdpw.xsd.trar_ins_001.Max10Dec2OrDelete;
import kdpw.xsd.trar_ins_001.Max10Dec2SignedOrDelete;
import kdpw.xsd.trar_ins_001.Max10Dec5SignedOrDelete;
import kdpw.xsd.trar_ins_001.Max10TextOrDelete;
import kdpw.xsd.trar_ins_001.Max150TextOrDelete;
import kdpw.xsd.trar_ins_001.Max16TextOrDelete;
import kdpw.xsd.trar_ins_001.Max1TextOrDelete;
import kdpw.xsd.trar_ins_001.Max208TextOrDelete;
import kdpw.xsd.trar_ins_001.Max20Dec5OrDelete;
import kdpw.xsd.trar_ins_001.Max20Dec5SignedOrDelete;
import kdpw.xsd.trar_ins_001.Max20TextOrDelete;
import kdpw.xsd.trar_ins_001.Max2TextOrDelete;
import kdpw.xsd.trar_ins_001.Max35TextOrDelete;
import kdpw.xsd.trar_ins_001.Max40TextOrDelete;
import kdpw.xsd.trar_ins_001.Max4IntOrDelete;
import kdpw.xsd.trar_ins_001.Max50TextOrDelete;
import kdpw.xsd.trar_ins_001.Max60TextOrDelete;
import kdpw.xsd.trar_ins_001.ObjectFactory;
import kdpw.xsd.trar_ins_001.OptionTrade;
import kdpw.xsd.trar_ins_001.PriceChoice;
import kdpw.xsd.trar_ins_001.RiskMitigation;
import kdpw.xsd.trar_ins_001.TRInstitutionCode;
import kdpw.xsd.trar_ins_001.TRInstitutionCode2;
import kdpw.xsd.trar_ins_001.TradeAdditionalInformation;
import kdpw.xsd.trar_ins_001.TradeDetails;
import kdpw.xsd.trar_ins_001.TradeIdentification;
import kdpw.xsd.trar_ins_001.TradeReference;
import kdpw.xsd.trar_ins_001.TrarIns00102;
import kdpw.xsd.trar_ins_001.UnderlyingDefinition;
import kdpw.xsd.trar_ins_001.ValuationAndCollateralInformation;
import kdpw.xsd.trar_ins_001.ValuationInformation;
import kdpw.xsd.trar_ins_001.YesNoIndicator;
import kdpw.xsd.trar_ins_001.validators.ClearingInformationValidator;
import kdpw.xsd.trar_ins_001.validators.CommodityTradeValidator;
import kdpw.xsd.trar_ins_001.validators.CounterpartyDetailsValidator;
import kdpw.xsd.trar_ins_001.validators.FXTradeValidator;
import kdpw.xsd.trar_ins_001.validators.InterestRateTradeValidator;
import kdpw.xsd.trar_ins_001.validators.OptionTradeValidator;
import kdpw.xsd.trar_ins_001.validators.PriceChoiceValidator;
import kdpw.xsd.trar_ins_001.validators.RiskMitigationValidator;
import kdpw.xsd.trar_ins_001.validators.TradeAdditionalInformationValidator;

@Stateless
@Local(TransactionWriter.class)
public class XmlTransactionWriterImpl extends XmlWriterImpl implements TransactionWriter<TransactionToRepository> {

    @Override
    public TransactionWriterResult write(List<TransactionToRepository> list, String institutionId, String InstitutionIdType) {

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

                final TrarIns00102 trar = new TrarIns00102();

                trar.setGnlInf(getGeneralInfo(transToRepo, institutionId, InstitutionIdType));

                final Client client = transToRepo.getRegistable().getClient();
                final Client client2 = transToRepo.getRegistable().getClient2();
                final TransactionMsgType msgType = transToRepo.getMsgType();

                if (msgType.isNew() || msgType.isModification()) {
                    trar.getCtrPtyInf().add(getBankCounterPtyInfo(transToRepo.getRegistable().getTransaction(),
                            client2,
                            client,
                            transToRepo.getMsgType()));
                }
                final ValuationAndCollateralInformation valAndCollInfo = getValtnAndCollInf(transToRepo.getRegistable().getTransaction(),
                        transToRepo.getMsgType(), false);
                if (Objects.nonNull(valAndCollInfo)) {
                    trar.addValtnAndCollInfo(valAndCollInfo);
                }

                if (client.getReported() && (msgType.isModification() || msgType.isNew())) {
                    trar.getCtrPtyInf().add(getClientCounterPtyInfo(transToRepo.getRegistable().getTransaction(),
                            client,
                            client2,
                            transToRepo.getMsgType()));
                }

                if (isValuationClientInfoRequired(transToRepo.getMsgType())
                        || (transToRepo.getMsgType().isNew() && client.getReported())) {
                    final ValuationAndCollateralInformation valAndCollInfo1 = getValtnAndCollInf(
                            transToRepo.getRegistable().getTransaction(),
                            transToRepo.getMsgType(),
                            true);
                    if (Objects.nonNull(valAndCollInfo1)) {
                        trar.addValtnAndCollInfo(valAndCollInfo1);
                    }
                }

                if (msgType.isNew() || msgType.isModification() || msgType.equals(TransactionMsgType.C) || msgType.equals(TransactionMsgType.Z)) {
                    trar.setTradDtls(getTransactionDetails(transToRepo.getRegistable().getTransaction(), transToRepo.getMsgType()));
                }

                document.getTrarIns00102().add(trar);
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

    protected final String getSndrMsgRef(Date date, Long newNumber) {
        return String.format("%s%s", DateUtils.formatDate(date, "yyMMdd"), newNumber);
    }

    protected final GeneralInformation getGeneralInfo(final TransactionToRepository item, final String institutionId, final String institutionIdType) {
        final GeneralInformation result = new GeneralInformation();

        result.setTRRprtId(getTrInstitutionCode(institutionId, institutionIdType));
        result.setSndrMsgRef(item.getSndrMsgRef());
        result.setFuncOfMsg(FunctionOfMessage.NEWM);
        result.setActnTp(item.getMsgType().getValue());
        Max50TextOrDelete actnTpDtls = new Max50TextOrDelete();
        actnTpDtls.setValue(item.getRegistable().getTransaction().getActionTypeDetails());
        actnTpDtls.setDeltnInd(YesNoIndicator.N);
        if (item.getMsgType().isOther() || (item.getMsgType().isModification() && actnTpDtls.getValue() != null && !"".equalsIgnoreCase(actnTpDtls.getValue()))) {
            //puste jeśli brak danych dla isOther
            result.setActnTpDtls(actnTpDtls);
        }
        result.setCreDtTm(getCreDtTm());
        result.setEligDt(XmlUtils.formatDate(item.getRegistable().getTransaction().getTransactionDate(), Constants.ISO_DATE));
        result.setLnk(getLnk(item));

        return result;
    }

    protected final TRInstitutionCode getTrInstitutionCode(final String institutionId, final String institutionIdType) {
        final TRInstitutionCode result = new TRInstitutionCode();
        result.setId(institutionId);
        result.setTp(institutionIdType);
        return result;
    }

    protected final DateAndDateTimeChoice getCreDtTm() {
        final DateAndDateTimeChoice result = new DateAndDateTimeChoice();
        result.setDtTm(XmlUtils.formatDate(new Date(), Constants.ISO_DATE_TIME));
        return result;
    }

    protected final Linkages getLnk(TransactionToRepository kdpwItem) {
        Linkages result = null;
        final TransactionMsgType msgType = kdpwItem.getMsgType();

        if (msgType.isModification() || msgType.isCancelation() || msgType.isCompleted() || msgType.isZipped()
                || msgType.isValuation()) {

            result = new Linkages();
            if (msgType.isModification() || msgType.isValuation() || msgType.isCompleted() || msgType.isZipped()
                    || (msgType.isCancelation() && !kdpwItem.isCancelMutation()) || msgType.isCompleted()) {

                result.getTradRefId().add(getTradeRefId(kdpwItem,
                        kdpwItem.getRegistable().getClient2().getInstitution(),
                        kdpwItem.getRegistable().getClient().getInstitution(),
                        kdpwItem.getRegistable().getTransaction().getTransactionParty()));

                if (kdpwItem.getRegistable().getClient().getReported()) {
                    // raportowanie w imieniu klienta
                    result.getTradRefId().add(getTradeRefId(kdpwItem,
                            kdpwItem.getRegistable().getClient().getInstitution(),
                            kdpwItem.getRegistable().getClient2().getInstitution(),
                            kdpwItem.getRegistable().getTransaction().getTransactionParty().opposite()));
                }
            }
        }
        return result;
    }

    
    //TODO PAWEL dwie ponizsze powinny byc jedną metodą i przerobione na dowolne party
    protected final CounterpartyInformation getBankCounterPtyInfo(final Transaction transaction, final Client client2,
            final Client client, final TransactionMsgType msgType) {
        final CounterpartyInformation result = new CounterpartyInformation();
        result.setCtrPtyTRId(getTrInstCode(client2.getInstitution()));
        result.setCtrPtySd(XmlUtils.enumName(getTransactionParty(transaction.getTransactionParty(), Boolean.FALSE)));
        result.setCtrPtyAdrAndSctr(getCounterPtyAddressAndDetails(client2));
        result.setCtrPtyDtls(getCounterPtyDetails(transaction.getClient2Data(), client2.getContrPartyType(), msgType));
        result.setOthrCtrPtyTRId(getOthrCtrPtyTrId(client.getInstitution()));
        result.setOthrCtrPtyInd(XmlUtils.booleanAsYorN(client.getNaturalPerson()));
        result.setNonEEACtrPty(nullOnEmpty(client.getEog()));

        return result;
    }

    protected CounterpartyInformation getClientCounterPtyInfo(final Transaction transaction, final Client client,
            final Client client2, TransactionMsgType msgType) {
        final CounterpartyInformation result = new CounterpartyInformation();
        result.setCtrPtyTRId(getTrInstCode(client.getInstitution()));
        result.setCtrPtySd(XmlUtils.enumName(getTransactionParty(transaction.getTransactionParty(), Boolean.TRUE)));
        result.setCtrPtyAdrAndSctr(getCounterPtyAddressAndDetails(client));
        result.setCtrPtyDtls(getCounterPtyDetails(transaction.getClientData(), client.getContrPartyType(), msgType));
        result.setOthrCtrPtyTRId(getOthrCtrPtyTrId(client2.getInstitution()));
        result.setOthrCtrPtyInd(XmlUtils.booleanAsYorN(Boolean.FALSE));
        result.setNonEEACtrPty("N");

        return result;
    }

    protected final TRInstitutionCode getTrInstCode(final Institution institution) {
        final TRInstitutionCode result = new TRInstitutionCode();
        if (null == institution || null == institution.getInstitutionData()) {
            logMappingError("Institution.institutionData");
        } else {
            if (StringUtil.isEmpty(institution.getInstitutionData().getInstitutionId())) {
                logMappingError("Institution.institutionData.institutionId");
            } else {
                result.setId(institution.getInstitutionData().getInstitutionId());
            }
            if (null == institution.getInstitutionData().getInstitutionIdType()) {
                logMappingError("Institution.institutionData.institutionIdType");
            } else {
                result.setTp(XmlUtils.enumName(institution.getInstitutionData().getInstitutionIdType()));
            }
        }
        return result;
    }

    protected final CounterpartyAddressAndSectorDetails getCounterPtyAddressAndDetails(final Client client) {
        final CounterpartyAddressAndSectorDetails result = new CounterpartyAddressAndSectorDetails();
        result.setNm((null == nullOnEmpty(client.getClientName()) ? null : new Max100TextOrDelete(nullOnEmpty(client.getClientName()))));
        result.setDmcl(getDomicile(client.getCountryCode(), client.getInstitution()));
        result.setCorpSctr(null == XmlUtils.enumName(client.getContrPartyIndustry()) ? null : new Max1TextOrDelete(XmlUtils.enumName(client.getContrPartyIndustry())));
        return result;
    }

    protected final Domicile getDomicile(final CountryCode code, final Institution institution) {
        final Domicile result = new Domicile();
        result.setCtry(new CountryCodeOrDelete(XmlUtils.enumName(code)));
        if (institution != null && null != institution.getInstitutionAddr()) {
            InstitutionAddress address = institution.getInstitutionAddr();
            result.setPstCd((null == nullOnEmpty(address.getPostalCode()) ? null : new Max40TextOrDelete(nullOnEmpty(address.getPostalCode()))));
            result.setTwnNm((null == nullOnEmpty(address.getCity()) ? null : new Max60TextOrDelete(nullOnEmpty(address.getCity()))));
            result.setStrtNm((null == nullOnEmpty(address.getStreetName()) ? null : new Max150TextOrDelete(nullOnEmpty(address.getStreetName()))));
            result.setBldgId((null == nullOnEmpty(address.getBuildingId()) ? null : new Max20TextOrDelete(nullOnEmpty(address.getBuildingId()))));
            result.setPrmsId((null == nullOnEmpty(address.getPremisesId()) ? null : new Max20TextOrDelete(nullOnEmpty(address.getPremisesId()))));
            result.setDmclDtls((null == nullOnEmpty(address.getDetails()) ? null : new Max208TextOrDelete(nullOnEmpty(address.getDetails()))));
        }
        return result;
    }

    protected final CounterpartyDetails getCounterPtyDetails(final BusinessEntityData businessData, final String contrPartyType, final TransactionMsgType msgType) {

        final CounterpartyDetails result = new CounterpartyDetails();
        result.setBrkrId(getBrkrIden(businessData));
        result.setClrMmbId(getClrMmbIden(businessData));
        if (Objects.nonNull(businessData)) {
            result.setClrAcct(null == nullOnEmpty(businessData.getSettlingAccout()) ? null : new Max35TextOrDelete(nullOnEmpty(businessData.getSettlingAccout())));
            result.setCmmrclActvty(null == XmlUtils.enumName(businessData.getCommercialActity()) ? null : new Max1TextOrDelete(XmlUtils.enumName(businessData.getCommercialActity())));
            result.setClrTrshld(null == XmlUtils.enumName(businessData.getSettlementThreshold()) ? null : new Max1TextOrDelete(XmlUtils.enumName(businessData.getSettlementThreshold())));
            result.setCollPrtfl(null == nullOnEmpty(businessData.getCommWalletCode()) ? null : new Max35TextOrDelete(nullOnEmpty(businessData.getCommWalletCode())));
        }

        if (msgType.isNew()) {
            Objects.requireNonNull(businessData, getClass().getName().concat(" - businessData - getCounterPtyDetails"));
            result.setBnfcryId(getBenefInstCode(businessData, true));
            Objects.requireNonNull(businessData.getTransactionType(), getClass().getName().concat(" - transactionType - getCounterPtyDetails"));
            result.setTrdgCpcty(XmlUtils.enumName(businessData.getTransactionType()));
            Objects.requireNonNull(contrPartyType, getClass().getName().concat(" - contrPartyType - getCounterPtyDetails"));
            result.setFinNonFinInd(nullOnEmpty(contrPartyType));
        } else if (msgType.isModification()) {
            result.setBnfcryId(getBenefInstCode(businessData, false));
            if (Objects.nonNull(businessData)) {
                result.setTrdgCpcty(XmlUtils.enumName(businessData.getTransactionType()));
            }
            result.setFinNonFinInd(nullOnEmpty(contrPartyType));
        }
        return new CounterpartyDetailsValidator().nullOnEmpty(result);
    }

    protected final InstitutionCodeOrDelete getBrkrIden(final BusinessEntityData businessData) {
        InstitutionCodeOrDelete result = null;
        if (null != businessData && notAllEmpty(businessData.getIdCode(),
                XmlUtils.enumName(businessData.getIdCodeType()))) {
            result = new InstitutionCodeOrDelete();
            // Identyfikator instytucji
            result.setId(businessData.getIdCode());
            // Rodzaj uĹĽytego identyfikatora
            result.setTp(XmlUtils.enumName(businessData.getIdCodeType()));
            result.setDeltnInd(YesNoIndicator.N);
        }
        return result;
    }

    protected final InstitutionCodeOrDelete getClrMmbIden(final BusinessEntityData businessData) {
        InstitutionCodeOrDelete result = null;
        if (businessData != null && notAllEmpty(businessData.getMemberId(),
                XmlUtils.enumName(businessData.getMemberIdType()))) {
            result = new InstitutionCodeOrDelete();
            result.setId(businessData.getMemberId());
            result.setTp(XmlUtils.enumName(businessData.getMemberIdType()));
            result.setDeltnInd(YesNoIndicator.N);
        }
        return result;
    }

    protected final InstitutionCode getBenefInstCode(final BusinessEntityData businessData, boolean required) {
        InstitutionCode result = null;
        if (null != businessData && notAllEmpty(businessData.getBeneficiaryCode(), businessData.getBeneficiaryCodeType())) {
            result = new InstitutionCode();
            result.setId(businessData.getBeneficiaryCode());
            result.setTp(XmlUtils.enumName(businessData.getBeneficiaryCodeType()));
        } else if (required) {
            logMappingError("TRANSAKCJE_E.?CTRPTYDTLS_BNFCRYID_ID | TRANSAKCJE_E.?CTRPTYDTLS_BNFCRYID_ID");
        }
        return result;
    }

    protected final TRInstitutionCode2 getOthrCtrPtyTrId(Institution institution) {
        final TRInstitutionCode2 result = new TRInstitutionCode2();
        if (null == institution || null == institution.getInstitutionData()) {
            logMappingError("KLIENT_E.RPRTID_ID & KLIENT_E.RPRTID_TP");
        } else {
            if (StringUtil.isEmpty(institution.getInstitutionData().getInstitutionId())) {
                logMappingError("KLIENT_E.RPRTID_ID");
            } else {
                result.setId(institution.getInstitutionData().getInstitutionId());
            }
            if (null == institution.getInstitutionData().getInstitutionIdType()) {
                logMappingError("KLIENT_E.RPRTID_TP");
            } else {
                result.setTp(institution.getInstitutionData().getInstitutionIdType().name());
            }
        }
        return result;
    }

    protected final InstitutionCode getOthrCtrptyId(final Institution institution) {
        InstitutionCode result = null;
        if (null != (institution) && null != (institution.getInstitutionData())) {
            final InstitutionData data = institution.getInstitutionData();
            if (notAllEmpty(data.getInstitutionId(),
                    data.getInstitutionIdType())) {
                result = new InstitutionCode();
                result.setId(data.getInstitutionId());
                result.setTp(XmlUtils.enumName(data.getInstitutionIdType()));
            }
        }
        return result;
    }

    protected ValuationAndCollateralInformation getValtnAndCollInf(final Transaction transaction,
            final TransactionMsgType msgType,
            final boolean clientSide) {
        ValuationAndCollateralInformation result = null;
        if (msgType == TransactionMsgType.N || msgType.isValuation() || msgType.isOther()) {
            final ValuationInformation valtnInf = getValtnInf(transaction.getValuation(), clientSide);
            final CollateralInformation collateralInfo = getCollateralInfo(transaction.getProtection(), clientSide);
            final String ctrPtySd = XmlUtils.enumName(getTransactionParty(transaction.getTransactionParty(), clientSide));

            if (notAllEmpty(valtnInf, collateralInfo, ctrPtySd)) {
                result = new ValuationAndCollateralInformation();
                result.setValtnInf(valtnInf);
                result.setCollInf(getCollateralInfo(transaction.getProtection(), clientSide));
                result.setCtrPtySd(ctrPtySd);
            }
        }
        return result;
    }

    protected final ValuationInformation getValtnInf(final Valuation valuation, final boolean clientSide) {
        ValuationInformation result = null;
        if (null != valuation && null != valuation.getValuationData()) {
            final ValuationData valuationData = valuation.getValuationData();
            if (clientSide) {
                if (allNotEmpty(valuationData.getClientAmount(), valuationData.getCurrencyCode(),
                        valuationData.getValuationDate(), valuationData.getValuationType())) {
                    result = new ValuationInformation();
                } else {
                    LOGGER.debug("Client data getValtnInf - not all empty");
                }
            } else if (allNotEmpty(valuationData.getAmount(), valuationData.getCurrencyCode(),
                    valuationData.getValuationDate(), valuationData.getValuationType())) {
                result = new ValuationInformation();
            } else {
                LOGGER.debug("ERROR data getValtnInf - not all empty");
            }

            if (null != result) {
                if (clientSide) {
                    result.setMtMVal(valuationData.getClientAmount());
                } else {
                    result.setMtMVal(valuationData.getAmount());
                }
                result.setCcy(XmlUtils.enumName(valuationData.getCurrencyCode()));
                if (null != valuationData.getValuationDate()) {
                    result.setValtnDtTm(XmlUtils.formatDate(valuationData.getValuationDate(), Constants.ISO_DATE_TIME));
                }
                result.setValtnTp(XmlUtils.enumName(valuationData.getValuationType()));
            }
        }
        return result;
    }

    protected final CollateralInformation getCollateralInfo(final Protection protection, final boolean clientSide) {
        CollateralInformation result = null;
        if (null != protection
                && (notAllEmpty(protection.getProtection(), protection.getWalletProtection(), protection.getWalletId(),
                        protection.getCurrencyCode())
                || (clientSide && null != protection.getClientAmount()) || (!clientSide && null != protection.getAmount()))) {
            result = new CollateralInformation();
            if (null != protection.getProtection()) {
                result.setColltn(XmlUtils.enumName(protection.getProtection()));
            }
            result.setPrtfColl(new Max1TextOrDelete(XmlUtils.enumName(protection.getWalletProtection())));
            result.setPrtfId((null == nullOnEmpty(protection.getWalletId()) ? null : new Max35TextOrDelete(nullOnEmpty(protection.getWalletId()))));
            if (clientSide) {
                result.setCollVal(null == protection.getClientAmount() ? null : new Max20Dec5OrDelete(protection.getClientAmount()));
            } else {
                result.setCollVal(null == protection.getAmount() ? null : new Max20Dec5OrDelete(protection.getAmount()));
            }
            result.setCollCcy(getCurrencyCodeOrNull(protection.getCurrencyCode()));
        }
        return result;
    }

    protected final TradeDetails getTransactionDetails(final Transaction transaction, final TransactionMsgType msgType) {
        final TradeDetails result = new TradeDetails();

        result.setTradId(getTradeIdentification(transaction.getTransactionDetails(), msgType));
        result.setCntrctTp(getContractType(transaction, msgType));
        result.setTradAddtlInf(getTradeAdditionalInfo(transaction, msgType));
        result.setRskMtgtn(getRiskMitigation(transaction, msgType));
        result.setClrgInf(getClearingInformation(transaction, msgType));
        result.setIRTrad(getInterestRateTrade(transaction, msgType));
        result.setFXTrad(getFxTrade(transaction, msgType));
        result.setCmmdtyTrad(getCommodityTrade(transaction, msgType));
        result.setOptnTrad(getOptionTrade(transaction, msgType));
        return result;
    }

    protected final TradeIdentification getTradeIdentification(final TransactionDetails details, final TransactionMsgType msgType) {
        if (msgType.isNew()) {
            Objects.requireNonNull(details, getClass().getName().concat(" - transactionDetails - getTradeIdentification"));
            return getTradeIdentification(details);
        } else if (msgType.isModification() && null != details) {
            return getTradeIdentification(details);
        }
        return null;
    }

    private TradeIdentification getTradeIdentification(TransactionDetails details) {
        TradeIdentification result = new TradeIdentification();
        result.setId(details.getSourceTransId());
        result.setPrvsId(null); // TODO - brak informacji w dokumentacji
        result.setTradRefNb(nullOnEmpty(details.getSourceTransRefNr()));
        return result;
    }

    protected final ContractType getContractType(final Transaction transaction, final TransactionMsgType msgType) {

        ContractType result = new ContractType();

        if (msgType.isNew() || msgType.isModification()) {
            final ContractDataDetailed contractDetailedData = transaction.getContractDetailedData();
            Objects.requireNonNull(contractDetailedData, getClass().getName().concat(" - getContractType - null ContractDataDetailed"));
            final ContractData contractData = contractDetailedData.getContractData();
            Objects.requireNonNull(contractData, getClass().getName().concat(" - getContractType - null ContractData"));
            String taxonomy = XmlUtils.enumName(contractData.getContractType());
            result.setTxnm(taxonomy);
            String product1Code = contractData.getProd1Code();
            result.setPrdctId1(nullOnEmpty(product1Code));
            result.setPrdctId2(nullOnEmpty(contractData.getProd2Code()));

            //TODO refactor condition and line length
            if (!"CO".equalsIgnoreCase(product1Code) && !"CU".equalsIgnoreCase(product1Code) && !"IR".equalsIgnoreCase(product1Code)) {
                throw new UnsupportedOperationException("Underlying. Ten przypadek nie jest obslugiwany. Product1Code = " + product1Code);
            } else if (("CO".equalsIgnoreCase(product1Code) || "CU".equalsIgnoreCase(product1Code) || "IR".equalsIgnoreCase(product1Code)) && "E".equalsIgnoreCase(taxonomy)) {
                //Should remain empty
            } else {
                //TODO do zmiany - NA albo czytać z bazy? underlying type?
                result.setUndrlyg(new UnderlyingDefinition("NA", "N"));
            }

            result.setIssrCtry(XmlUtils.enumName(contractDetailedData.getUnderlCountryCode()) == null ? null : new CountryCodeOrDelete(XmlUtils.enumName(contractDetailedData.getUnderlCountryCode())));
            result.setNtnlCcy1(XmlUtils.enumName(contractDetailedData.getUnderlCurrency1Code()));
            result.setNtnlCcy2(getCurrencyCodeOrNull(contractDetailedData.getUnderlCurrency2Code()));
            result.setDlvrblCcy(getCurrencyCodeOrNull(contractDetailedData.getDelivCurrencyCode()));
        }
        return result;
    }

    /**
     * Converts CurrencyCode to CurrencyCodeOrDelete. Delete indicator is not set.
     *
     * @param value - CurrencyCode to be converted to CurrencyCodeOrDelete
     * @return CurrencyCodeOrDelete or null if CurrencyCode is null or currency is not defined.
     */
    private CurrencyCodeOrDelete getCurrencyCodeOrNull(CurrencyCode value) {
        String currencyCode = XmlUtils.enumName(value);
        if (Objects.nonNull(currencyCode)) {
            return new CurrencyCodeOrDelete(currencyCode);
        } else {
            return null;
        }
    }

    protected final TradeAdditionalInformation getTradeAdditionalInfo(final Transaction transaction, final TransactionMsgType msgType) {
        final TradeAdditionalInformation result = new TradeAdditionalInformation();
        final TransactionDetails transactionDetails = transaction.getTransactionDetails();
        if (msgType.isNew()) {
            Objects.requireNonNull(transactionDetails, getClass().getName().concat(" - transactionDetails - getTradeAdditionalInfo"));
        }
        if (Objects.nonNull(transactionDetails)) {
            if (msgType.isNew() || msgType.isModification()) {
                result.setVenueOfExc(transactionDetails.getRealizationVenue());
                result.setPric(getPriceChoice(transactionDetails));
                result.setPricMltplr(NumberUtils.integerToBigDecimal(transactionDetails.getPriceMultiplier()));
                result.setUpPmt(null == transactionDetails.getInAdvanceAmount() ? null : new Max10Dec2SignedOrDelete(transactionDetails.getInAdvanceAmount()));
                result.setDlvryTp(XmlUtils.enumName(transactionDetails.getDelivType()));
                result.setExecDtTm(getDateTimeChoice(transactionDetails.getExecutionDate()));
                result.setFctvDt(getDateTimeChoice(transactionDetails.getEffectiveDate()));
                result.setMtrtyDt(getDateTimeChoiceOrDelete(transactionDetails.getMaturityDate()));
                result.setSttlmtDt(getDateTimeChoiceOrDelete(transactionDetails.getSettlementDate()));
                result.setMstrAgrmntTp((null == nullOnEmpty(transactionDetails.getFrameworkAggrType()) ? null : new Max50TextOrDelete(nullOnEmpty(transactionDetails.getFrameworkAggrType()))));
                result.setMstrAgrmntVrsn(null == NumberUtils.integerToBigInteger(transactionDetails.getFrameworkAggrVer()) ? null : (new Max4IntOrDelete(NumberUtils.integerToBigInteger(transactionDetails.getFrameworkAggrVer()))));
            }
            if (msgType.isNew() || msgType.isModification() || msgType.isZipped()) {
                result.setCmprssn(XmlUtils.enumName(transactionDetails.getCompression()));
            }
            if (msgType.isNew() || msgType.isModification() || msgType.isZipped() || msgType.isCompleted()) {
                result.setNmnlAmt(transactionDetails.getNominalAmount());
                result.setQty(NumberUtils.integerToBigInteger(transactionDetails.getContractCount()));
            }
            if (msgType.isCompleted() || msgType.isZipped()) {
                result.setTrmntnDt(getDateTimeChoiceOrDelete(transactionDetails.getTerminationDate()));
            }
        }
        return new TradeAdditionalInformationValidator().nullOnEmpty(result);
    }

    protected final PriceChoice getPriceChoice(final TransactionDetails transactionDetails) {
        final PriceChoice result = new PriceChoice();
        BigDecimal priceOrRate = transactionDetails.getUnitPrice();
        String priceNot = XmlUtils.enumName(transactionDetails.getUnitPriceCurrency());
        if (null == priceOrRate || null == priceNot) {
            priceOrRate = transactionDetails.getUnitPriceRate();
            priceNot = "100";
            if (null == priceOrRate) {
                priceOrRate = new BigDecimal("999999999999999.99999");
                priceNot = "NA";
            }
        }
        result.setPricRt(priceOrRate);
        result.setPricNot(priceNot);
        return new PriceChoiceValidator().nullOnEmpty(result);
    }

    protected final RiskMitigation getRiskMitigation(final Transaction transaction, final TransactionMsgType msgType) {
        final RiskMitigation result = new RiskMitigation();
        final RiskReduce riskReduce = transaction.getRiskReduce();
        if (msgType.isNew()) {
            Objects.requireNonNull(riskReduce, getClass().getName().concat(" - getRiskMitigation"));
        }
        if ((msgType.isNew() || msgType.isModification()) && Objects.nonNull(riskReduce)) {
            result.setCnfrmtnDtTm(getDateTimeChoiceOrDelete(riskReduce.getConfirmationDate()));
            result.setCnfrmtnTp(XmlUtils.enumName(riskReduce.getConfirmationType()));
            if (!msgType.isModification() && (riskReduce.getConfirmationDate() == null || riskReduce.getConfirmationType() == null)) {
                result.setCnfrmtnTp(ConfirmationType.N.name());
                result.setCnfrmtnDtTm(getDateTimeChoiceOrDelete(new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime()));
            }
        }
        return new RiskMitigationValidator().nullOnEmpty(result);
    }

    protected final ClearingInformation getClearingInformation(final Transaction transaction, final TransactionMsgType msgType) {
        final ClearingInformation result = new ClearingInformation();
        final TransactionClearing clearing = transaction.getTransactionClearing();
        if (msgType.isNew()) {
            Objects.requireNonNull(clearing);
        }
        if (msgType.isNew() || (msgType.isModification() && (null != clearing))) {
            result.setClrOblgtn(XmlUtils.enumName(clearing.getClearingOblig()));
            result.setClrd(XmlUtils.enumName(clearing.getCleared()));
            result.setClrDtTm(getDateTimeChoiceOrDelete(clearing.getClearingDate()));
            result.setCCP((null == nullOnEmpty(clearing.getCcpCode()) ? null : new Max20TextOrDelete(nullOnEmpty(clearing.getCcpCode()))));
            result.setIntrgrp(null == XmlUtils.enumName(clearing.getIntergropuTrans()) ? null : new Max1TextOrDelete(XmlUtils.enumName(clearing.getIntergropuTrans())));
        }
        return new ClearingInformationValidator().nullOnEmpty(result);
    }

    protected final InterestRateTrade getInterestRateTrade(final Transaction transaction,
            final TransactionMsgType msgType) {
        final InterestRateTrade result = new InterestRateTrade();
        final PercentageRateData rateData = transaction.getPercentageRateData();
        if ((msgType.isNew() || msgType.isModification()) && Objects.nonNull(rateData)) {
            result.setFxdRateLg1(null == rateData.getFixedRateLeg1() ? null : new Max20Dec5SignedOrDelete(rateData.getFixedRateLeg1()));
            result.setFxdRateLg2(null == rateData.getFixedRateLeg2() ? null : new Max20Dec5SignedOrDelete(rateData.getFixedRateLeg2()));
            result.setFxdRateDayCnt(null == nullOnEmpty(rateData.getFixedRateDayCount()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getFixedRateDayCount())));
            result.setFxdLgPmtFrqcy(null == nullOnEmpty(rateData.getFixedPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getFixedPaymentFreq())));
            result.setFltgLgPmtFrqcy(null == nullOnEmpty(rateData.getFloatPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getFloatPaymentFreq())));
            result.setFltgRateRstFrqcy(null == nullOnEmpty(rateData.getNewPaymentFreq()) ? null : new Max10TextOrDelete(nullOnEmpty(rateData.getNewPaymentFreq())));
            result.setFltgRateLg1(null == nullOnEmpty(rateData.getFloatRateLeg1()) ? null : new Max20TextOrDelete(nullOnEmpty(rateData.getFloatRateLeg1())));
            result.setFltgRateLg2(null == nullOnEmpty(rateData.getFloatRateLeg2()) ? null : new Max20TextOrDelete(nullOnEmpty(rateData.getFloatRateLeg2())));
        }
        return new InterestRateTradeValidator().nullOnEmpty(result);
    }

    protected final FXTrade getFxTrade(final Transaction transaction, final TransactionMsgType msgType) {
        final FXTrade result = new FXTrade();
        final CurrencyTradeData tradeData = transaction.getCurrencyTradeData();
        if ((msgType.isNew() || msgType.isModification()) && Objects.nonNull(tradeData)) {
            result.setCcy2(XmlUtils.enumName(tradeData.getCurrencyTradeCode()));
            result.setXchgRate1(null == tradeData.getCurrTradeExchRate() ? null : new Max10Dec5SignedOrDelete(tradeData.getCurrTradeExchRate()));
            result.setFrwrdXchgRate(null == tradeData.getCurrTradeFrwdRate() ? null : new Max10Dec5SignedOrDelete(tradeData.getCurrTradeFrwdRate()));
            result.setXchgRateBsis(nullOnEmpty(tradeData.getCurrTradeBasis()));
        }
        return new FXTradeValidator().nullOnEmpty(result);
    }

    protected final CommodityTrade getCommodityTrade(final Transaction transaction, final TransactionMsgType msgType) {
        final CommodityTrade result = new CommodityTrade();
        final CommodityTradeData data = transaction.getCommodityTradeData();
        if (null != data && (msgType.isNew() || msgType.isModification())) {
            result.setCmmdtyBase(XmlUtils.enumName(data.getCommUnderlType()));
            result.setCmmdtyDtls(XmlUtils.enumName(data.getCommUnderlDtls()));
            result.setDlvryPnt(Objects.isNull(data.getCommVenue()) ? null : new Max16TextOrDelete(data.getCommVenue()));
            result.setIntrcnnctnPnt(Objects.isNull(data.getCommInterconn()) ? null : new Max50TextOrDelete(data.getCommInterconn()));
            result.setLdTp(Objects.isNull(XmlUtils.enumName(data.getCommLoadType())) ? null : new Max2TextOrDelete(XmlUtils.enumName(data.getCommLoadType())));
            result.setDlvryStartDtTm(getDateTimeChoiceOrDelete(data.getCommDelivStartFrom()));
            result.setDlvryEndDtTm(getDateTimeChoiceOrDelete(data.getCommDelivEndFrom()));
            result.setCntrctCpcty(Objects.isNull(data.getCommContractCount()) ? null : new Max50TextOrDelete(data.getCommContractCount()));
            result.setQty(Objects.isNull(data.getCommRateCount()) ? null : new Max10Dec2OrDelete(data.getCommRateCount()));
            result.setPric(Objects.isNull(data.getCommRataCount()) ? null : new Max10Dec2SignedOrDelete(data.getCommRataCount()));
        }
        return new CommodityTradeValidator().nullOnEmpty(result);
    }

    protected final OptionTrade getOptionTrade(final Transaction transaction, final TransactionMsgType msgType) {
        final OptionTrade result = new OptionTrade();
        final TransactionDetails details = transaction.getTransactionDetails();
        if ((msgType.isNew() || msgType.isModification()) && Objects.nonNull(details)) {
            result.setOptnTp(XmlUtils.enumName(details.getOptionType()));
            result.setExrcStyle(XmlUtils.enumName(details.getOptionExecStyle()));
            result.setStrkPric(details.getOptionExecPrice());
        }
        return new OptionTradeValidator().nullOnEmpty(result);
    }

    @Override
    protected String getActpTp() {
        return null; // NIE uzywane przekazywane jako parametr
    }

    protected final DateAndDateTimeChoice getDateTimeChoice(final Date date) {
        DateAndDateTimeChoice result = null;
        if (null != date) {
            result = new DateAndDateTimeChoice();
            result.setDtTm(XmlUtils.formatDate(date, Constants.ISO_DATE_TIME));
        }
        return result;
    }

    protected final DateAndDateTimeChoiceOrDelete getDateTimeChoiceOrDelete(final Date date) {
        DateAndDateTimeChoiceOrDelete result = null;
        if (null != date) {
            result = new DateAndDateTimeChoiceOrDelete();
            result.setDtTm(XmlUtils.formatDate(date, Constants.ISO_DATE_TIME));
        }
        return result;
    }

    protected final boolean isValuationClientInfoRequired(final TransactionMsgType changeType) {
        return TransactionMsgType.VR.equals(changeType);
    }

    private TradeReference getTradeRefId(TransactionToRepository kdpwItem,
            Institution firstInstitution, Institution secondInstitution, TransactionParty transactionParty) {
        TradeReference result = new TradeReference();

        final Transaction transaction = kdpwItem.getRegistable().getTransaction();

        result.setTradId(transaction.getTransactionDetails().getSourceTransId());
        if (null != firstInstitution && null != firstInstitution.getInstitutionData()
                && StringUtil.isNotEmpty(firstInstitution.getInstitutionData().getInstitutionId())) {
            result.setCtrPtyTRId(firstInstitution.getInstitutionData().getInstitutionId());
        }
        if (null != secondInstitution
                && null != secondInstitution.getInstitutionData()
                && StringUtil.isNotEmpty(secondInstitution.getInstitutionData().getInstitutionId())) {
            result.setOthrCtrPtyTRId(secondInstitution.getInstitutionData().getInstitutionId());
        }
        result.setCtrPtySd(XmlUtils.enumName(transactionParty));
        return result;
    }

    private TransactionParty getTransactionParty(TransactionParty transactionParty, boolean clientSide) {
        if (transactionParty == null) {
            logMappingError("TransactionParty");
        } else if (clientSide) {
            return transactionParty.opposite();
        }
        return transactionParty;
    }

    private String logMappingError(String fieldName) {
        final String msg = String.format("Field %s cannot be NULL !", fieldName);
        LOGGER.error(msg);
        return msg;
    }

}
