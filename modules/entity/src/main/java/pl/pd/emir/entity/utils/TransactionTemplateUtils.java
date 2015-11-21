package pl.pd.emir.entity.utils;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.CommodityTradeData;
import pl.pd.emir.embeddable.ContractData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.entity.administration.TransactionTemplate;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;

public class TransactionTemplateUtils {

    private static final Logger LOG = Logger.getLogger(TransactionTemplateUtils.class.getName());

    public static Transaction createTransaction(TransactionTemplate template) {
        return createTransaction(template, DateUtils.getDayBegin(new Date()), null, DataType.NEW, ProcessingStatus.NEW, true);
    }

    public static Transaction createTransaction(TransactionTemplate template, Date transactionDate, String originalId,
            DataType dataType, ProcessingStatus processingStatus, boolean initEmptyFields) {
        Transaction transaction = new Transaction();
        if (initEmptyFields) {
            transaction.initFields();
        }

        if (template == null) {
            return transaction;
        }

        transaction.setTransactionDate(transactionDate);
        transaction.setOriginalId(originalId);
        transaction.setDataType(dataType);
        transaction.setProcessingStatus(processingStatus);

        transaction.setBankData(template.getBankData());

        Client client = new Client();
        if (initEmptyFields) {
            client.initFields();
        }
        client.setOriginalId(template.getOriginalClientId() == null ? "" : template.getOriginalClientId());
        transaction.setClient(client);

        transaction.setClientData(template.getClientData() == null ? new BusinessEntityData() : template.getClientData());
        transaction.setCommodityTradeData(template.getCommodityTradeData() == null ? new CommodityTradeData() : template.getCommodityTradeData());
        transaction.setContractDetailedData(template.getContractDetailedData() == null ? new ContractDataDetailed() : template.getContractDetailedData());
        transaction.setCurrencyTradeData(template.getCurrencyTradeData() == null ? new CurrencyTradeData() : template.getCurrencyTradeData());
        transaction.setDateSupply(template.getDateSupply());
        transaction.setOriginalClientId(template.getOriginalClientId() == null ? "" : template.getOriginalClientId());
        transaction.setOriginalStatus(template.getOriginalStatus());
        transaction.setPercentageRateData(template.getPercentageRateData() == null ? new PercentageRateData() : template.getPercentageRateData());

        Protection protection = new Protection();
        if (initEmptyFields) {
            protection.initFields();
        }
        if (template.getProtection() != null) {
            protection.setAmount(template.getProtection().getAmount());
            protection.setClientAmount(template.getProtection().getClientAmount());
            protection.setCurrencyCode(template.getProtection().getCurrencyCode());
            protection.setProtection(template.getProtection().getIsProtection());
            protection.setWalletId(template.getProtection().getWalletId() == null ? "" : template.getProtection().getWalletId());
            protection.setWalletProtection(template.getProtection().getWalletProtection());
        }
        transaction.setProtection(protection);

        RiskReduce riskReduce = new RiskReduce();
        if (template.getRiskReduce() != null) {
            riskReduce.setConfirmationDate(template.getRiskReduce().getConfirmationDate());
            riskReduce.setConfirmationType(template.getRiskReduce().getConfirmationType());
        }
        transaction.setRiskReduce(riskReduce);

        transaction.setTransactionClearing(template.getTransactionClearing() == null ? new TransactionClearing() : template.getTransactionClearing());
        transaction.setTransactionParty(template.getTransactionParty());

        Valuation valuation = new Valuation();
        ValuationData valuationData = new ValuationData();
        if (initEmptyFields) {
            valuation.initFields();
            valuationData.initFields();
        }
        valuationData.setAmount(template.getAmount());
        valuationData.setClientAmount(template.getClientAmount());
        valuationData.setCurrencyCode(template.getCurrencyCode());
        valuationData.setValuationDate(template.getValuationDate());
        valuationData.setValuationType(template.getValuationType());
        valuation.setValuationData(valuationData);
        transaction.setValuation(valuation);

        transaction.setConfirmed(template.getConfirmed() == null ? ConfirmedStatus.EMPTY : template.getConfirmed());

        TransactionDetails transactionDetails = new TransactionDetails();
        if (initEmptyFields) {
            transactionDetails.initFields();
        }
        if (template.getTransactionDetails() != null) {
            transactionDetails.setCompression(template.getTransactionDetails().getCompression());
            transactionDetails.setContractCount(template.getTransactionDetails().getContractCount());
            transactionDetails.setDelivType(template.getTransactionDetails().getDelivType());
            transactionDetails.setEffectiveDate(template.getTransactionDetails().getEffectiveDate());
            transactionDetails.setExecutionDate(template.getTransactionDetails().getExecutionDate());
            transactionDetails.setFrameworkAggrType(template.getTransactionDetails().getFrameworkAggrType());
            transactionDetails.setFrameworkAggrVer(template.getTransactionDetails().getFrameworkAggrVer());
            transactionDetails.setInAdvanceAmount(template.getTransactionDetails().getInAdvanceAmount());
            transactionDetails.setMaturityDate(template.getTransactionDetails().getMaturityDate());
            transactionDetails.setNominalAmount(template.getTransactionDetails().getNominalAmount());
            transactionDetails.setOptionExecPrice(template.getTransactionDetails().getOptionExecPrice());
            transactionDetails.setOptionExecStyle(template.getTransactionDetails().getOptionExecStyle());
            transactionDetails.setOptionType(template.getTransactionDetails().getOptionType());
            transactionDetails.setPriceMultiplier(template.getTransactionDetails().getPriceMultiplier());
            transactionDetails.setRealizationVenue(template.getTransactionDetails().getRealizationVenue());
            transactionDetails.setSettlementDate(template.getTransactionDetails().getSettlementDate());
            transactionDetails.setSourceTransId(template.getTransactionDetails().getSourceTransId());
            transactionDetails.setSourceTransRefNr(template.getTransactionDetails().getSourceTransRefNr());
            transactionDetails.setTerminationDate(template.getTransactionDetails().getTerminationDate());
            transactionDetails.setUnitPrice(template.getTransactionDetails().getUnitPrice());
            transactionDetails.setUnitPriceCurrency(template.getTransactionDetails().getUnitPriceCurrency());
            transactionDetails.setUnitPriceRate(template.getTransactionDetails().getUnitPriceRate());
        }
        transaction.setTransactionDetails(transactionDetails);

        return transaction;
    }

    public static Transaction refillTransaction(Transaction transaction, TransactionTemplate template) {

        if (template == null) {
            return transaction;
        }

        transaction.setBankData(template.getBankData());

        transaction.setClientData(template.getClientData() == null ? new BusinessEntityData() : template.getClientData());
        transaction.setCommodityTradeData(template.getCommodityTradeData() == null ? new CommodityTradeData() : template.getCommodityTradeData());
        transaction.setContractDetailedData(template.getContractDetailedData() == null ? new ContractDataDetailed() : template.getContractDetailedData());
        transaction.setCurrencyTradeData(template.getCurrencyTradeData() == null ? new CurrencyTradeData() : template.getCurrencyTradeData());
        transaction.setDateSupply(template.getDateSupply());
        transaction.setOriginalClientId(template.getOriginalClientId() == null ? "" : template.getOriginalClientId());
        transaction.setOriginalStatus(template.getOriginalStatus());
        transaction.setPercentageRateData(template.getPercentageRateData() == null ? new PercentageRateData() : template.getPercentageRateData());

        Protection protection = transaction.getProtection() == null ? new Protection() : transaction.getProtection();
        if (template.getProtection() != null) {
            protection.setAmount(template.getProtection().getAmount());
            protection.setClientAmount(template.getProtection().getClientAmount());
            protection.setCurrencyCode(template.getProtection().getCurrencyCode());
            protection.setProtection(template.getProtection().getIsProtection());
            protection.setWalletId(template.getProtection().getWalletId() == null ? "" : template.getProtection().getWalletId());
            protection.setWalletProtection(template.getProtection().getWalletProtection());
        }
        transaction.setProtection(protection);

        RiskReduce riskReduce = transaction.getRiskReduce() == null ? new RiskReduce() : transaction.getRiskReduce();
        if (template.getRiskReduce() != null) {
            riskReduce.setConfirmationDate(template.getRiskReduce().getConfirmationDate());
            riskReduce.setConfirmationType(template.getRiskReduce().getConfirmationType());
        }
        transaction.setRiskReduce(riskReduce);

        transaction.setTransactionClearing(template.getTransactionClearing() == null ? new TransactionClearing() : template.getTransactionClearing());
        transaction.setTransactionParty(template.getTransactionParty());

        Valuation valuation = transaction.getValuation() == null ? new Valuation() : transaction.getValuation();
        ValuationData valuationData = transaction.getValuation().getValuationData() == null ? new ValuationData() : transaction.getValuation().getValuationData();
        valuationData.setAmount(template.getAmount());
        valuationData.setClientAmount(template.getClientAmount());
        valuationData.setCurrencyCode(template.getCurrencyCode());
        valuationData.setValuationDate(template.getValuationDate());
        valuationData.setValuationType(template.getValuationType());
        valuation.setValuationData(valuationData);
        transaction.setValuation(valuation);

        transaction.setConfirmed(template.getConfirmed() == null ? ConfirmedStatus.EMPTY : template.getConfirmed());

        TransactionDetails transactionDetails = transaction.getTransactionDetails() == null ? new TransactionDetails() : transaction.getTransactionDetails();
        if (template.getTransactionDetails() != null) {
            transactionDetails.setCompression(template.getTransactionDetails().getCompression());
            transactionDetails.setContractCount(template.getTransactionDetails().getContractCount());
            transactionDetails.setDelivType(template.getTransactionDetails().getDelivType());
            transactionDetails.setEffectiveDate(template.getTransactionDetails().getEffectiveDate());
            transactionDetails.setExecutionDate(template.getTransactionDetails().getExecutionDate());
            transactionDetails.setFrameworkAggrType(template.getTransactionDetails().getFrameworkAggrType());
            transactionDetails.setFrameworkAggrVer(template.getTransactionDetails().getFrameworkAggrVer());
            transactionDetails.setInAdvanceAmount(template.getTransactionDetails().getInAdvanceAmount());
            transactionDetails.setMaturityDate(template.getTransactionDetails().getMaturityDate());
            transactionDetails.setNominalAmount(template.getTransactionDetails().getNominalAmount());
            transactionDetails.setOptionExecPrice(template.getTransactionDetails().getOptionExecPrice());
            transactionDetails.setOptionExecStyle(template.getTransactionDetails().getOptionExecStyle());
            transactionDetails.setOptionType(template.getTransactionDetails().getOptionType());
            transactionDetails.setPriceMultiplier(template.getTransactionDetails().getPriceMultiplier());
            transactionDetails.setRealizationVenue(template.getTransactionDetails().getRealizationVenue());
            transactionDetails.setSettlementDate(template.getTransactionDetails().getSettlementDate());
            if (transactionDetails.getSourceTransId() == null) {
                transactionDetails.setSourceTransId(template.getTransactionDetails().getSourceTransId());
            }
            if (transactionDetails.getSourceTransRefNr() == null) {
                transactionDetails.setSourceTransRefNr(template.getTransactionDetails().getSourceTransRefNr());
            }
            transactionDetails.setTerminationDate(template.getTransactionDetails().getTerminationDate());
            transactionDetails.setUnitPrice(template.getTransactionDetails().getUnitPrice());
            transactionDetails.setUnitPriceCurrency(template.getTransactionDetails().getUnitPriceCurrency());
            transactionDetails.setUnitPriceRate(template.getTransactionDetails().getUnitPriceRate());
        }
        transaction.setTransactionDetails(transactionDetails);

        return transaction;
    }

    public static Transaction refillWithoutOverwriting(Transaction transaction, TransactionTemplate template) {

        LOG.info("Start method: refillWithoutOverwriting");
        if (template == null) {
            return transaction;
        }
        LOG.info("TransactionTemplate is not NULL");
        transaction.setBankData(template.getBankData());
        Client client;
        if (Objects.isNull(transaction.getClient())) {
            client = new Client();
            client.initFields();
        } else {
            client = transaction.getClient();
        }
        client.setOriginalId(template.getOriginalClientId() == null ? "" : template.getOriginalClientId());
        transaction.setClient(client);

        BusinessEntityData bankData = transaction.getBankData() == null ? new BusinessEntityData() : transaction.getBankData();
        if (Objects.nonNull(template.getBankData())) {
            if (isEmpty(bankData.getBeneficiaryCode())) {
                bankData.setBeneficiaryCode(template.getBankData().getBeneficiaryCode());
            }
            if (isEmpty(bankData.getBeneficiaryCodeType())) {
                bankData.setBeneficiaryCodeType(template.getBankData().getBeneficiaryCodeType());
            }
            if (isEmpty(bankData.getCommWalletCode())) {
                bankData.setCommWalletCode(template.getBankData().getCommWalletCode());
            }
            if (isEmpty(bankData.getCommercialActity())) {
                bankData.setCommercialActity(template.getBankData().getCommercialActity());
            }
            if (isEmpty(bankData.getIdCode())) {
                bankData.setIdCode(template.getBankData().getIdCode());
            }
            if (isEmpty(bankData.getIdCodeType())) {
                bankData.setIdCodeType(template.getBankData().getIdCodeType());
            }
            if (isEmpty(bankData.getMemberId())) {
                bankData.setMemberId(template.getBankData().getMemberId());
            }
            if (isEmpty(bankData.getMemberIdType())) {
                bankData.setMemberIdType(template.getBankData().getMemberIdType());
            }
            if (isEmpty(bankData.getSettlementThreshold())) {
                bankData.setSettlementThreshold(template.getBankData().getSettlementThreshold());
            }
            if (isEmpty(bankData.getSettlingAccout())) {
                bankData.setSettlingAccout(template.getBankData().getSettlingAccout());
            }
            if (isEmpty(bankData.getTransactionType())) {
                bankData.setTransactionType(template.getBankData().getTransactionType());
            }
        }
        transaction.setBankData(bankData);

        BusinessEntityData clientData = transaction.getClientData() == null ? new BusinessEntityData() : transaction.getClientData();
        if (Objects.nonNull(template.getClientData())) {
            if (isEmpty(clientData.getBeneficiaryCode())) {
                clientData.setBeneficiaryCode(template.getClientData().getBeneficiaryCode());
            }
            if (isEmpty(clientData.getBeneficiaryCodeType())) {
                clientData.setBeneficiaryCodeType(template.getClientData().getBeneficiaryCodeType());
            }
            if (isEmpty(clientData.getCommWalletCode())) {
                clientData.setCommWalletCode(template.getClientData().getCommWalletCode());
            }
            if (isEmpty(clientData.getCommercialActity())) {
                clientData.setCommercialActity(template.getClientData().getCommercialActity());
            }
            if (isEmpty(clientData.getIdCode())) {
                clientData.setIdCode(template.getClientData().getIdCode());
            }
            if (isEmpty(clientData.getIdCodeType())) {
                clientData.setIdCodeType(template.getClientData().getIdCodeType());
            }
            if (isEmpty(clientData.getMemberId())) {
                clientData.setMemberId(template.getClientData().getMemberId());
            }
            if (isEmpty(clientData.getMemberIdType())) {
                clientData.setMemberIdType(template.getClientData().getMemberIdType());
            }
            if (isEmpty(clientData.getSettlementThreshold())) {
                clientData.setSettlementThreshold(template.getClientData().getSettlementThreshold());
            }
            if (isEmpty(clientData.getSettlingAccout())) {
                clientData.setSettlingAccout(template.getClientData().getSettlingAccout());
            }
            if (isEmpty(clientData.getTransactionType())) {
                clientData.setTransactionType(template.getClientData().getTransactionType());
            }
        }
        transaction.setClientData(clientData);

        CommodityTradeData commodityTradeData = transaction.getCommodityTradeData() == null ? new CommodityTradeData() : transaction.getCommodityTradeData();
        if (Objects.nonNull(template.getCommodityTradeData())) {
            if (isEmpty(commodityTradeData.getCommContractCount())) {
                commodityTradeData.setCommContractCount(template.getCommodityTradeData().getCommContractCount());
            }
            if (isEmpty(commodityTradeData.getCommDelivEndFrom())) {
                commodityTradeData.setCommDelivEndFrom(template.getCommodityTradeData().getCommDelivEndFrom());
            }
            if (isEmpty(commodityTradeData.getCommDelivStartFrom())) {
                commodityTradeData.setCommDelivStartFrom(template.getCommodityTradeData().getCommDelivStartFrom());
            }
            if (isEmpty(commodityTradeData.getCommInterconn())) {
                commodityTradeData.setCommInterconn(template.getCommodityTradeData().getCommInterconn());
            }
            if (isEmpty(commodityTradeData.getCommLoadType())) {
                commodityTradeData.setCommLoadType(template.getCommodityTradeData().getCommLoadType());
            }
            if (isEmpty(commodityTradeData.getCommRataCount())) {
                commodityTradeData.setCommRataCount(template.getCommodityTradeData().getCommRataCount());
            }
            if (isEmpty(commodityTradeData.getCommRateCount())) {
                commodityTradeData.setCommRateCount(template.getCommodityTradeData().getCommRateCount());
            }
            if (isEmpty(commodityTradeData.getCommUnderlDtls())) {
                commodityTradeData.setCommUnderlDtls(template.getCommodityTradeData().getCommUnderlDtls());
            }
            if (isEmpty(commodityTradeData.getCommUnderlType())) {
                commodityTradeData.setCommUnderlType(template.getCommodityTradeData().getCommUnderlType());
            }
            if (isEmpty(commodityTradeData.getCommVenue())) {
                commodityTradeData.setCommVenue(template.getCommodityTradeData().getCommVenue());
            }

        }
        transaction.setCommodityTradeData(commodityTradeData);
        ContractData contractData;
        if (Objects.nonNull(transaction.getContractDetailedData())) {
            contractData = transaction.getContractDetailedData().getContractData() == null ? new ContractData() : transaction.getContractDetailedData().getContractData();
            if (Objects.nonNull(template.getContractDetailedData())) {
                if (isEmpty(contractData.getContractType())) {
                    contractData.setContractType(template.getContractDetailedData().getContractData().getContractType());
                }
                if (isEmpty(contractData.getProd1Code())) {
                    contractData.setProd1Code(template.getContractDetailedData().getContractData().getProd1Code());
                }
                if (isEmpty(contractData.getProd2Code())) {
                    contractData.setProd2Code(template.getContractDetailedData().getContractData().getProd2Code());
                }
                if (isEmpty(contractData.getUnderlyingId())) {
                    contractData.setUnderlyingId(template.getContractDetailedData().getContractData().getUnderlyingId());
                }
                ContractDataDetailed contractDetailedData = transaction.getContractDetailedData() == null ? new ContractDataDetailed() : transaction.getContractDetailedData();
                contractDetailedData.setContractData(contractData);
                transaction.setContractDetailedData(contractDetailedData);
            }

        }

        ContractDataDetailed contractDetailedData = transaction.getContractDetailedData() == null ? new ContractDataDetailed() : transaction.getContractDetailedData();
        if (Objects.nonNull(template.getContractDetailedData())) {

            if (isEmpty(contractDetailedData.getContractData())) {
                contractDetailedData.setContractData(template.getContractDetailedData().getContractData());
            }
            if (isEmpty(contractDetailedData.getDelivCurrencyCode())) {
                contractDetailedData.setDelivCurrencyCode(template.getContractDetailedData().getDelivCurrencyCode());
            }
            if (isEmpty(contractDetailedData.getUnderlCountryCode())) {
                contractDetailedData.setUnderlCountryCode(template.getContractDetailedData().getUnderlCountryCode());
            }
            if (isEmpty(contractDetailedData.getUnderlCurrency1Code())) {
                contractDetailedData.setUnderlCurrency1Code(template.getContractDetailedData().getUnderlCurrency1Code());
            }
            if (isEmpty(contractDetailedData.getUnderlCurrency2Code())) {
                contractDetailedData.setUnderlCurrency2Code(template.getContractDetailedData().getUnderlCurrency2Code());
            }
        }
        transaction.setContractDetailedData(contractDetailedData);

        CurrencyTradeData currencyTradeData = transaction.getCurrencyTradeData() == null ? new CurrencyTradeData() : transaction.getCurrencyTradeData();
        if (Objects.nonNull(template.getCurrencyTradeData())) {
            if (isEmpty(currencyTradeData.getCurrTradeBasis())) {
                currencyTradeData.setCurrTradeBasis(template.getCurrencyTradeData().getCurrTradeBasis());
            }
            if (isEmpty(currencyTradeData.getCurrTradeExchRate())) {
                currencyTradeData.setCurrTradeExchRate(template.getCurrencyTradeData().getCurrTradeExchRate());
            }
            if (isEmpty(currencyTradeData.getCurrTradeFrwdRate())) {
                currencyTradeData.setCurrTradeFrwdRate(template.getCurrencyTradeData().getCurrTradeFrwdRate());
            }
            if (isEmpty(currencyTradeData.getCurrencyTradeCode())) {
                currencyTradeData.setCurrencyTradeCode(template.getCurrencyTradeData().getCurrencyTradeCode());
            }
        }
        transaction.setCurrencyTradeData(currencyTradeData);

        if (isEmpty(transaction.getDateSupply())) {
            transaction.setDateSupply(template.getDateSupply());
        }
        if (isEmpty(transaction.getOriginalClientId())) {
            transaction.setOriginalClientId(template.getOriginalClientId());
        }
        if (isEmpty(transaction.getOriginalStatus())) {
            transaction.setOriginalStatus(template.getOriginalStatus());
        }
        if (isEmpty(transaction.getPercentageRateData())) {
            transaction.setPercentageRateData(template.getPercentageRateData());
        }
        PercentageRateData percentageRateData = transaction.getPercentageRateData() == null ? new PercentageRateData() : transaction.getPercentageRateData();
        if (Objects.nonNull(template.getPercentageRateData())) {
            if (isEmpty(percentageRateData.getFixedPaymentFreq())) {
                percentageRateData.setFixedPaymentFreq(template.getPercentageRateData().getFixedPaymentFreq());
            }
            if (isEmpty(percentageRateData.getFixedRateDayCount())) {
                percentageRateData.setFixedRateDayCount(template.getPercentageRateData().getFixedRateDayCount());
            }
            if (isEmpty(percentageRateData.getFixedRateLeg1())) {
                percentageRateData.setFixedRateLeg1(template.getPercentageRateData().getFixedRateLeg1());
            }
            if (isEmpty(percentageRateData.getFixedRateLeg2())) {
                percentageRateData.setFixedRateLeg2(template.getPercentageRateData().getFixedRateLeg2());
            }
            if (isEmpty(percentageRateData.getFloatPaymentFreq())) {
                percentageRateData.setFloatPaymentFreq(template.getPercentageRateData().getFloatPaymentFreq());
            }
            if (isEmpty(percentageRateData.getFloatRateLeg1())) {
                percentageRateData.setFloatRateLeg1(template.getPercentageRateData().getFloatRateLeg1());
            }
            if (isEmpty(percentageRateData.getFloatRateLeg2())) {
                percentageRateData.setFloatRateLeg2(template.getPercentageRateData().getFloatRateLeg2());
            }
            if (isEmpty(percentageRateData.getNewPaymentFreq())) {
                percentageRateData.setNewPaymentFreq(template.getPercentageRateData().getNewPaymentFreq());
            }
        }

        if (Objects.nonNull(transaction.getProtection())) {
            if (Objects.nonNull(template.getProtection())) {
                Protection protection = transaction.getProtection();
                if (isEmpty(protection.getAmount())) {
                    protection.setAmount(template.getProtection().getAmount());
                }
                if (isEmpty(protection.getClientAmount())) {
                    protection.setClientAmount(template.getProtection().getClientAmount());
                }
                if (isEmpty(protection.getCurrencyCode())) {
                    protection.setCurrencyCode(template.getProtection().getCurrencyCode());
                }
                if (isEmpty(protection.getProtection())) {
                    protection.setProtection(template.getProtection().getIsProtection());
                }
                if (isEmpty(protection.getWalletId())) {
                    protection.setWalletId(template.getProtection().getWalletId());
                }
                if (isEmpty(protection.getWalletProtection())) {
                    protection.setWalletProtection(template.getProtection().getWalletProtection());
                }
                transaction.setProtection(protection);
            }
        } else if (Objects.nonNull(template.getProtection())) {
            Protection protection = new Protection();
            if (isEmpty(protection.getAmount())) {
                protection.setAmount(template.getProtection().getAmount());
            }
            if (isEmpty(protection.getClientAmount())) {
                protection.setClientAmount(template.getProtection().getClientAmount());
            }
            if (isEmpty(protection.getCurrencyCode())) {
                protection.setCurrencyCode(template.getProtection().getCurrencyCode());
            }
            if (isEmpty(protection.getProtection())) {
                protection.setProtection(template.getProtection().getIsProtection());
            }
            if (isEmpty(protection.getWalletId())) {
                protection.setWalletId(template.getProtection().getWalletId());
            }
            if (isEmpty(protection.getWalletProtection())) {
                protection.setWalletProtection(template.getProtection().getWalletProtection());
            }
            transaction.setProtection(protection);
        }

        RiskReduce riskReduce = transaction.getRiskReduce() == null ? new RiskReduce() : transaction.getRiskReduce();
        if (Objects.nonNull(template.getRiskReduce())) {
            if (isEmpty(riskReduce.getConfirmationDate())) {
                riskReduce.setConfirmationDate(template.getRiskReduce().getConfirmationDate());
            }
            if (isEmpty(riskReduce.getConfirmationType())) {
                riskReduce.setConfirmationType(template.getRiskReduce().getConfirmationType());
            }
        }
        transaction.setRiskReduce(riskReduce);

        TransactionClearing transactionClearing = transaction.getTransactionClearing() == null ? new TransactionClearing() : transaction.getTransactionClearing();
        if (Objects.nonNull(template.getTransactionClearing())) {
            if (isEmpty(transactionClearing.getCcpCode())) {
                transactionClearing.setCcpCode(template.getTransactionClearing().getCcpCode());
            }
            if (isEmpty(transactionClearing.getCleared())) {
                transactionClearing.setCleared(template.getTransactionClearing().getCleared());
            }
            if (isEmpty(transactionClearing.getClearingDate())) {
                transactionClearing.setClearingDate(template.getTransactionClearing().getClearingDate());
            }
            if (isEmpty(transactionClearing.getClearingOblig())) {
                transactionClearing.setClearingOblig(template.getTransactionClearing().getClearingOblig());
            }
            if (isEmpty(transactionClearing.getIntergropuTrans())) {
                transactionClearing.setIntergropuTrans(template.getTransactionClearing().getIntergropuTrans());
            }
        }
        transaction.setTransactionClearing(transactionClearing);

        if (isEmpty(transaction.getTransactionParty())) {
            transaction.setTransactionParty(template.getTransactionParty());
        }

        if (Objects.nonNull(transaction.getValuation())) {
            Valuation valuation = transaction.getValuation();
            if (Objects.nonNull(transaction.getValuation().getValuationData())) {
                ValuationData valuationData = transaction.getValuation().getValuationData();
                if (isEmpty(valuationData.getAmount())) {
                    valuationData.setAmount(template.getAmount());
                }
                if (isEmpty(valuationData.getClientAmount())) {
                    valuationData.setClientAmount(template.getClientAmount());
                }
                if (isEmpty(valuationData.getCurrencyCode())) {
                    valuationData.setCurrencyCode(template.getCurrencyCode());
                }
                if (isEmpty(valuationData.getValuationDate())) {
                    valuationData.setValuationDate(template.getValuationDate());
                }
                if (isEmpty(valuationData.getValuationType())) {
                    valuationData.setValuationType(template.getValuationType());
                }
                valuation.setValuationData(valuationData);
                transaction.setValuation(valuation);
            }
        } else {
            Valuation valuation = new Valuation();
            if (Objects.nonNull(template.getValuationDate())) {
                ValuationData valuationData = new ValuationData();
                if (isEmpty(valuationData.getAmount())) {
                    valuationData.setAmount(template.getAmount());
                }
                if (isEmpty(valuationData.getClientAmount())) {
                    valuationData.setClientAmount(template.getClientAmount());
                }
                if (isEmpty(valuationData.getCurrencyCode())) {
                    valuationData.setCurrencyCode(template.getCurrencyCode());
                }
                if (isEmpty(valuationData.getValuationDate())) {
                    valuationData.setValuationDate(template.getValuationDate());
                }
                if (isEmpty(valuationData.getValuationType())) {
                    valuationData.setValuationType(template.getValuationType());
                }
                valuation.setValuationData(valuationData);
                transaction.setValuation(valuation);
            }
        }

        transaction.setConfirmed(template.getConfirmed());

        TransactionDetails transactionDetails = transaction.getTransactionDetails() == null ? new TransactionDetails() : transaction.getTransactionDetails();
        if (template.getTransactionDetails() != null) {
            if (isEmpty(transactionDetails.getCompression())) {
                transactionDetails.setCompression(template.getTransactionDetails().getCompression());
            }
            if (isEmpty(transactionDetails.getContractCount())) {
                transactionDetails.setContractCount(template.getTransactionDetails().getContractCount());
            }
            if (isEmpty(transactionDetails.getDelivType())) {
                transactionDetails.setDelivType(template.getTransactionDetails().getDelivType());
            }
            if (isEmpty(transactionDetails.getEffectiveDate())) {
                transactionDetails.setEffectiveDate(template.getTransactionDetails().getEffectiveDate());
            }
            if (isEmpty(transactionDetails.getExecutionDate())) {
                transactionDetails.setExecutionDate(template.getTransactionDetails().getExecutionDate());
            }
            if (isEmpty(transactionDetails.getFrameworkAggrType())) {
                transactionDetails.setFrameworkAggrType(template.getTransactionDetails().getFrameworkAggrType());
            }
            if (isEmpty(transactionDetails.getFrameworkAggrVer())) {
                transactionDetails.setFrameworkAggrVer(template.getTransactionDetails().getFrameworkAggrVer());
            }
            if (isEmpty(transactionDetails.getInAdvanceAmount())) {
                transactionDetails.setInAdvanceAmount(template.getTransactionDetails().getInAdvanceAmount());
            }
            if (isEmpty(transactionDetails.getMaturityDate())) {
                transactionDetails.setMaturityDate(template.getTransactionDetails().getMaturityDate());
            }
            if (isEmpty(transactionDetails.getNominalAmount())) {
                transactionDetails.setNominalAmount(template.getTransactionDetails().getNominalAmount());
            }
            if (isEmpty(transactionDetails.getOptionExecPrice())) {
                transactionDetails.setOptionExecPrice(template.getTransactionDetails().getOptionExecPrice());
            }
            if (isEmpty(transactionDetails.getOptionExecStyle())) {
                transactionDetails.setOptionExecStyle(template.getTransactionDetails().getOptionExecStyle());
            }
            if (isEmpty(transactionDetails.getOptionType())) {
                transactionDetails.setOptionType(template.getTransactionDetails().getOptionType());
            }
            if (isEmpty(transactionDetails.getPriceMultiplier())) {
                transactionDetails.setPriceMultiplier(template.getTransactionDetails().getPriceMultiplier());
            }
            if (isEmpty(transactionDetails.getRealizationVenue())) {
                transactionDetails.setRealizationVenue(template.getTransactionDetails().getRealizationVenue());
            }
            if (isEmpty(transactionDetails.getSettlementDate())) {
                transactionDetails.setSettlementDate(template.getTransactionDetails().getSettlementDate());
            }
            if (isEmpty(transactionDetails.getUnitPriceCurrency())) {
                transactionDetails.setUnitPriceCurrency(template.getTransactionDetails().getUnitPriceCurrency());
            }
            if (isEmpty(transactionDetails.getPreviousSourceTransId())) {
                transactionDetails.setPreviousSourceTransId(template.getTransactionDetails().getPreviousSourceTransId());
            }
            if (transactionDetails.getSourceTransId() == null) {
                if (isEmpty(transactionDetails.getSourceTransId())) {
                    transactionDetails.setSourceTransId(template.getTransactionDetails().getSourceTransId());
                }
            }
            if (transactionDetails.getSourceTransRefNr() == null) {
                if (isEmpty(transactionDetails.getSourceTransRefNr())) {
                    transactionDetails.setSourceTransRefNr(template.getTransactionDetails().getSourceTransRefNr());
                }
            }
            if (isEmpty(transactionDetails.getTerminationDate())) {
                transactionDetails.setTerminationDate(template.getTransactionDetails().getTerminationDate());
            }
            if (isEmpty(transactionDetails.getUnitPrice())) {
                transactionDetails.setUnitPrice(template.getTransactionDetails().getUnitPrice());
            }
            if (isEmpty(transactionDetails.getUnitPriceCurrency())) {
                transactionDetails.setUnitPriceCurrency(template.getTransactionDetails().getUnitPriceCurrency());
            }
            if (isEmpty(transactionDetails.getUnitPriceRate())) {
                transactionDetails.setUnitPriceRate(template.getTransactionDetails().getUnitPriceRate());
            }
        }
        transaction.setTransactionDetails(transactionDetails);

        return transaction;
    }

    private static boolean isEmpty(Object field) {
        if (Objects.isNull(field)) {
            return true;
        }
        return field.toString().isEmpty();
    }
}
