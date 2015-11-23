package pl.pd.emir.entity.utils;

import java.util.List;
import java.util.Objects;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.Transaction;
import static pl.pd.emir.entity.utils.BaseValidationUtils.addWarningToWarningList;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.enums.ValuationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessValidationUtils extends BaseValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessValidationUtils.class);

    // ==================== BANK ====================
    public static void validateBankCountry(Bank bank, List<ImportFailLog> warnings, String recordId) {
        validateBankCountry(bank, warnings, recordId, "COUNTRY");
    }

    public static void validateBankCountry(Bank bank, List<ImportFailLog> warnings, String recordId, String fieldName) {
        if (bank.getCountryCode() == null
                || CountryCode.ERR.equals(bank.getCountryCode())) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    // ==================== CLIENT ====================
    public static void validateClientCountry(Client client, List<ImportFailLog> warnings, String recordId) {
        validateClientCountry(client, warnings, recordId, "COUNTRY");
    }

    public static void validateClientCountry(Client client, List<ImportFailLog> warnings, String recordId, String fieldName) {
        if (client.getCountryCode() == null
                || CountryCode.ERR.equals(client.getCountryCode())) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    // ==================== TRANSACTION ====================
    public static void validateBankBeneficiaryCode(Transaction transaction, List<ImportFailLog> warnings, String recordId) {
        validateBankBeneficiaryCode(transaction, warnings, "BCTRPTYDTLS_BNFCRYID_ID", recordId);
    }

    public static void validateBankBeneficiaryCode(Transaction transaction, List<ImportFailLog> warnings, String fieldName, String recordId) {
        if (OriginalStatus.N.equals(transaction.getOriginalStatus())
                && (transaction.getBankData() == null
                || StringUtil.isEmpty(transaction.getBankData().getBeneficiaryCode()))) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static void validateBankTransactionType(Transaction transaction, List<ImportFailLog> warnings, String recordId) {
        validateBankTransactionType(transaction, warnings, "BCTRPTYDTLS_TRDGCPCTY", recordId);
    }

    public static void validateBankTransactionType(Transaction transaction, List<ImportFailLog> warnings, String fieldName, String recordId) {
        if (OriginalStatus.N.equals(transaction.getOriginalStatus())
                && (transaction.getBankData() == null
                || transaction.getBankData().getTransactionType() == null
                || TransactionType.ERR.equals(transaction.getBankData().getTransactionType()))) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static void validateClientBeneficiaryCode(Transaction transaction, List<ImportFailLog> warnings, String recordId) {
        validateClientBeneficiaryCode(transaction, warnings, "KCTRPTYDTLS_BNFCRYID_ID", recordId);
    }

    public static void validateClientBeneficiaryCode(Transaction transaction, List<ImportFailLog> warnings, String fieldName, String recordId) {
        Client client = transaction.getClient();
        if (client == null) {
            LOGGER.warn("Validation for transaction {} on {} failed - client is null", transaction.getOriginalId(),
                    DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT));
            return;
        }

        if (client.getReported()
                && (transaction.getClientData() == null
                || StringUtil.isEmpty(transaction.getClientData().getBeneficiaryCode()))) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static void validateRealizationValue(Transaction transaction, List<ImportFailLog> warnings, String recordId) {
        validateRealizationValue(transaction, warnings, "TRADADDTLINF_VENUEOFEXC", recordId);
    }

    public static void validateRealizationValue(Transaction transaction, List<ImportFailLog> warnings, String fieldName, String recordId) {
        if (OriginalStatus.N.equals(transaction.getOriginalStatus())
                && (transaction.getTransactionDetails() == null
                || StringUtil.isEmpty(transaction.getTransactionDetails().getRealizationVenue()))) {
            ImportFailLog log = new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu %s", fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static void validateFrameworkAggrVer(Transaction transaction, List<ImportFailLog> warnings, String recordId) {
        validateFrameworkAggrVer(transaction, warnings, "TRADADDTLINF_MSTRAGRMNTVRSN", recordId);
    }

    public static void validateFrameworkAggrVer(Transaction transaction, List<ImportFailLog> warnings, String fieldName, String recordId) {
        if (transaction.getTransactionDetails() == null) {
            return;
        }
        Integer frameworkAggrVer = transaction.getTransactionDetails().getFrameworkAggrVer();
        if (frameworkAggrVer != null && (frameworkAggrVer < 1000 || frameworkAggrVer > 9999)) {
            ImportFailLog log = new ImportFailLog(
                    String.format("Wartość w polu %s dla rekordu %s musi zawierać się w przedziale od 1000 do 9999",
                            fieldName, recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static void validateExtractVersion(Transaction transaction, Transaction previousTransaction, List<ImportFailLog> warnings, String recordId) {
        if (previousTransaction != null && !ProcessingStatus.NEW.equals(previousTransaction.getProcessingStatus())) {
            ImportFailLog log = new ImportFailLog(String.format("Dane źródłowe dla rekordu %s na podaną datę danych "
                    + "zostały już wcześniej zaimportowane i wysłane do KDPW. Została założona nowa wersja danych dla "
                    + "podanej daty", recordId));
            addWarningToWarningList(log, warnings);
        }
    }

    public static boolean validateErrorCategory(Transaction transacion, Transaction previousTrans, List<ImportFailLog> warnings, String recordId) {
        boolean ret = false;
        if (Objects.nonNull(transacion)) {
            if (transacion.getOriginalStatus().equals(OriginalStatus.C) || transacion.getOriginalStatus().equals(OriginalStatus.M)) {
                if (Objects.isNull(previousTrans)
                        || Objects.isNull(previousTrans.getTransactionDetails())
                        || StringUtil.isEmpty(previousTrans.getTransactionDetails().getSourceTransId())) {
                    ImportFailLog log = new ImportFailLog(ImportStatus.NOT_EXIST, String.format("Brak ciągłości transakcji o identyfikatorze " + transacion.getOriginalId() + ". Nie znaleziono wcześniejszej wersji transakcji na bieżący lub poprzedni dzień roboczy", recordId));
                    addWarningToWarningList(log, warnings);
                    ret = true;
                }
            }
        }
        return ret;
    }

    public static boolean isProtectionComplete(Object extract, boolean valuationReporting) {
        LOGGER.debug("isProtectionComplete " + valuationReporting);
        if (extract instanceof Transaction) {
            Transaction transTem = (Transaction) extract;
            if (valuationReporting == true) {//jest wymagana
                if (Objects.nonNull(transTem.getProtection())
                        && (Objects.nonNull(transTem.getProtection().getProtection()) && !transTem.getProtection().getProtection().equals(DoProtection.ERR))) {
                    LOGGER.debug("Protection is complete,valuationReporting true ");
                    return true;
                }
            } else if (Objects.isNull(transTem.getProtection())
                    || (Objects.nonNull(transTem.getProtection().getProtection()) && !transTem.getProtection().getProtection().equals(DoProtection.ERR))) {
                LOGGER.debug("Protection is complete, valuationReporting false");
                return true;
            }
        }
        LOGGER.debug("Protection isn't complete");
        return false;
    }

    public static boolean isValuationComplete(Object extract, boolean valuationReporting) {
        if (extract instanceof Transaction) {
            LOGGER.debug("ValuationReporting true" + valuationReporting);
            Transaction transTem = (Transaction) extract;
            if (valuationReporting == true) {//jest wymagana
                if (Objects.nonNull(transTem.getValuation())
                        && (Objects.nonNull(transTem.getValuation().getValuationData().getValuationType()) && !transTem.getValuation().getValuationData().getValuationType().equals(ValuationType.ERR))
                        && Objects.nonNull(transTem.getValuation().getValuationData().getValuationDate())
                        && (Objects.nonNull(transTem.getValuation().getValuationData().getCurrencyCode()) && !transTem.getValuation().getValuationData().getCurrencyCode().equals(CurrencyCode.ERR))
                        && Objects.nonNull(transTem.getValuation().getValuationData().getAmount())) {
                    LOGGER.debug("Valuation is complete, valuationReporting true");
                    return true;
                }
            } else if (Objects.isNull(transTem.getValuation())
                    || (Objects.nonNull(transTem.getValuation().getValuationData())
                    && (Objects.nonNull(transTem.getValuation().getValuationData().getValuationType()) && !transTem.getValuation().getValuationData().getValuationType().equals(ValuationType.ERR))
                    && Objects.nonNull(transTem.getValuation().getValuationData().getValuationDate())
                    && (Objects.nonNull(transTem.getValuation().getValuationData().getCurrencyCode()) && !transTem.getValuation().getValuationData().getCurrencyCode().equals(CurrencyCode.ERR))
                    && Objects.nonNull(transTem.getValuation().getValuationData().getAmount()))) {
                LOGGER.debug("Valuation is complete, , valuationReporting false");

                return true;
            }
        }
        LOGGER.debug("Valuation isn't complete");
        return false;
    }
}
