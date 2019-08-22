package pl.pd.emir.imports.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.ValidationUtils;
import pl.pd.emir.embeddable.BusinessEntity;
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
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.ClearingOblig;
import pl.pd.emir.enums.CommLoadType;
import pl.pd.emir.enums.CommUnderlDtls;
import pl.pd.emir.enums.CommUnderlType;
import pl.pd.emir.enums.CommercialActity;
import pl.pd.emir.enums.Compression;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.enums.ContractType;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CountryCodeEOG;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DeliverType;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.OptionExecStyle;
import pl.pd.emir.enums.OptionType;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.imports.ImportResult;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImportValidationUtils {

    private ImportValidationUtils() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportValidationUtils.class);

    /**
     * ValidateStringField.
     *
     * @param value
     * @param length
     * @param fieldName
     * @param emptyCheck
     * @param importResult
     * @return
     */
    public static String validateFINNONFINField(String value, int length, String fieldName, boolean emptyCheck, ImportResult importResult) {
        if (StringUtil.isEmpty(value) && emptyCheck) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId()))));
        } else if (value.length() > length) {
            importResult.addWarning(new ImportFailLog(String.format("Długość pola %s przekracza dozwoloną wartość równą %s",
                    fieldName, length).concat(getRecordDetails(importResult.getRecordId()))));
        } else if (!(value.startsWith("1") || value.startsWith("2") || value.startsWith("3"))) {
            importResult.addWarning(new ImportFailLog(String.format("Wartość [%s] pola %s jest niepoprawna",
                    value, fieldName).concat(getRecordDetails(importResult.getRecordId()))));
        }
        if (value.startsWith("1")) {
            return "F";
        }
        return "N";
    }

    /**
     * ValidateStringField.
     *
     * @param value
     * @param fieldName
     * @param emptyCheck
     * @param importResult
     * @return
     */
    public static String validateEOGField(String value, String fieldName, boolean emptyCheck, ImportResult importResult) {
        if (StringUtil.isEmpty(value) && emptyCheck) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId()))));
        }
        if (CountryCodeEOG.isFromString(value)) {
            return "N";
        }
        return "Y";
    }

    /**
     * WARNING - Walidacja dlugosci pola
     */
    public static String validateStringField(String value, int length, String fieldName, boolean emptyCheck, String numberField, ImportResult importResult) {
        return validateStringField(value, null, length, fieldName, emptyCheck, numberField, importResult);
    }

    public static String validateStringField(String value, String replacementValue, int length, String fieldName, boolean emptyCheck, String numberField, ImportResult importResult) {
        if (StringUtil.isEmpty(value)) {
            if (StringUtil.isNotEmpty(replacementValue)) {
                return replacementValue;
            } else if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), numberField));
            }
        } else if (value.length() > length) {
            importResult.addWarning(new ImportFailLog(String.format("Długość pola %s przekracza dozwoloną wartość równą %s",
                    fieldName, length).concat(getRecordDetails(importResult.getRecordId()))));
        }
        return value;
    }

    /**
     * WARNING - Walidacja pola typu enum
     */
    public static <E extends Enum> E validateEnumField(Class<E> enumType, String value, String fieldName, boolean emptyCheck, String numberField, ImportResult importResult) {
        return validateEnumField(enumType, null, value, fieldName, emptyCheck, numberField, importResult);
    }

    public static <E extends Enum> E validateEnumField(Class<E> enumType, E value, String fieldName, boolean emptyCheck, String numberField, ImportResult importResult) {
        E resultEnum = value;
        if (Objects.isNull(value) && (emptyCheck)) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), numberField));
        } else if ("ERR".equalsIgnoreCase(value.toString())) {
            importResult.addWarning(new ImportFailLog(String.format("Brak możliwość określenia typu na podstawie wartości z pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), numberField));
        }
        return resultEnum;
    }

    public static <E extends Enum> E validateEnumField(Class<E> enumType, E replacementValue, String value, String fieldName, boolean emptyCheck, String numberField, ImportResult importResult) {
        E resultEnum = null;
        if (StringUtil.isEmpty(value)) {
            if (replacementValue != null) {
                return replacementValue;
            } else if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), numberField));
                try {
                    resultEnum = (E) Enum.valueOf(enumType, "ERR");
                } catch (IllegalArgumentException exception) {
                    LOGGER.warn("Enum doesn't have an ERR value.");
                }
            }
        } else {
            try {
                resultEnum = (E) Enum.valueOf(enumType, value.toUpperCase());
            } catch (IllegalArgumentException exception) {
                importResult.addWarning(new ImportFailLog(String.format("Błędna wartość %s w polu %s ", value, fieldName).concat(getRecordDetails(importResult.getRecordId())), numberField));
                try {
                    resultEnum = (E) Enum.valueOf(enumType, "ERR");
                } catch (IllegalArgumentException innerException) {
                    LOGGER.warn("Enum doesn't have an ERR value.");
                }
            }
        }
        return resultEnum;
    }

    /**
     * WARNING - Walidacja danych podmiotu
     */
    public static BusinessEntity validateBusinessEntity(String[] data, boolean emptyCheck, ImportResult importResult) {
        BusinessEntity entity = new BusinessEntity(data[0], data[1]);
        if (emptyCheck && StringUtil.isEmpty(data[0]) && StringUtil.isEmpty(data[1])) {
            importResult.addWarning(new ImportFailLog("Brak obowiązkowej wartości dla jednego z pól TRRPRTID_PMRYID lub TRRPRTID_SCNDRYID".concat(getRecordDetails(importResult.getRecordId()))));
        }
        return entity;
    }

    /**
     * WARNING - Walidacja danych kontrahenta (banku i klienta)
     */
    public static BusinessEntityData validateBusinessEntityData(String[] data, boolean isBank, ImportResult importResult) {
        return validateBusinessEntityData(data, isBank, null, importResult);
    }

    public static BusinessEntityData validateBusinessEntityData(String[] data, boolean isBank, BusinessEntityData replacementValue, ImportResult importResult) {
        String contractorPrfx;

        if (isBank) {
            contractorPrfx = "B";
        } else {
            contractorPrfx = "K";
        }

        String r1 = null;
        InstitutionIdType r2 = null;
        String r3 = null;
        InstitutionIdType r4 = null;
        String r5 = null;
        String r6 = null;
        InstitutionIdType r7 = null;
        TransactionType r8 = null;
        CommercialActity r9 = null;
        SettlementThreshold r10 = null;
        String r11 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getIdCode();
            r2 = replacementValue.getIdCodeType();
            r3 = replacementValue.getMemberId();
            r4 = replacementValue.getMemberIdType();
            r5 = replacementValue.getSettlingAccout();
            r6 = replacementValue.getBeneficiaryCode();
            r7 = replacementValue.getBeneficiaryCodeType();
            r8 = replacementValue.getTransactionType();
            r9 = replacementValue.getCommercialActity();
            r10 = replacementValue.getSettlementThreshold();
            r11 = replacementValue.getCommWalletCode();
        }

        String f1 = validateStringField(data[0], r1, 50, String.format("%sCTRPTYDTLS_BRKRID_ID", contractorPrfx), false, isBank ? "7" : "18", importResult);
        InstitutionIdType f2 = validateEnumField(InstitutionIdType.class, r2, data[1], String.format("%sCTRPTYDTLS_BRKRID_TP", contractorPrfx), false, isBank ? "8" : "19", importResult);
        //Walidacja kompletności identyfikatora (id + typ id)
        validateIdentifierCompleteness(f1, f2, String.format("%sCTRPTYDTLS_BRKRID_ID", contractorPrfx),
                String.format("%sCTRPTYDTLS_BRKRID_TP", contractorPrfx), isBank ? "7 i 8" : "18 i 19", importResult);

        String f3 = validateStringField(data[2], r3, 50, String.format("%sCTRPTYDTLS_CLRMMBID_ID", contractorPrfx), false, isBank ? "9" : "20", importResult);
        InstitutionIdType f4 = validateEnumField(InstitutionIdType.class, r4, data[3], String.format("%sCTRPTYDTLS_CLRMMBID_TP", contractorPrfx), false, isBank ? "10" : "21", importResult);

        //Walidacja kompletności identyfikatora (id + typ id)
        validateIdentifierCompleteness(f3, f4, String.format("%sCTRPTYDTLS_CLRMMBID_ID", contractorPrfx),
                String.format("%sCTRPTYDTLS_CLRMMBID_TP", contractorPrfx), isBank ? "9 i 10" : "20 i 21", importResult);

        String f5 = validateStringField(data[4], r5, 35, String.format("%sCTRPTYDTLS_CLRACCT", contractorPrfx), false, isBank ? "11" : "22", importResult);

        String f6 = validateStringField(data[5], r6, 50, String.format("%sCTRPTYDTLS_BNFCRYID_ID", contractorPrfx), false, isBank ? "12" : "23", importResult);
        InstitutionIdType f7 = validateEnumField(InstitutionIdType.class, r7, data[6], String.format("%sCTRPTYDTLS_BNFCRYID_TP", contractorPrfx), false, isBank ? "13" : "24", importResult);

        //Walidacja kompletności identyfikatora (id + typ id)
        validateIdentifierCompleteness(f6, f7, String.format("%sCTRPTYDTLS_BNFCRYID_ID", contractorPrfx),
                String.format("%sCTRPTYDTLS_BNFCRYID_TP", contractorPrfx), isBank ? "12 i 13" : "23:24", importResult);

        TransactionType f8 = validateEnumField(TransactionType.class, r8, data[7], String.format("%sCTRPTYDTLS_TRDGCPCTY", contractorPrfx), false, isBank ? "14" : "25", importResult);

        CommercialActity f9 = validateEnumField(CommercialActity.class, r9, data[8], String.format("%sCTRPTYDTLS_CMMRCLACTVTY", contractorPrfx), false, isBank ? "15" : "26", importResult);

        SettlementThreshold f10 = validateEnumField(SettlementThreshold.class, r10, data[9], String.format("%sCTRPTYDTLS_CLRTRSHLD", contractorPrfx), false, isBank ? "16" : "27", importResult);

        String f11 = validateStringField(data[10], r11, 35, String.format("%sCTRPTYDTLS_COLLPRTFL", contractorPrfx), false, "17", importResult);

        BusinessEntityData result = new BusinessEntityData(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11);

        return result;
    }

    public static void validateIdentifierCompleteness(String id, InstitutionIdType idType, String idName,
            String idTypeName, String fieldNumber, ImportResult importResult) {
        if (StringUtil.isEmpty(id)//eliminacja powtarzających się wpisów
                && (idType == null || InstitutionIdType.ERR.equals(idType))
                && idName.contains("BCTRPTYDTLS_BNFCRYID_ID")
                && idTypeName.contains("BCTRPTYDTLS_BNFCRYID_TP")) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s dla rekordu "
                    .concat(getRecordDetails(importResult.getRecordId())), idTypeName), fieldNumber));
            return;
        }
        if ((StringUtil.isEmpty(id) && (idType != null || !InstitutionIdType.ERR.equals(idType))) && idName.contains("BCTRPTYDTLS_BNFCRYID_ID")) {
            return;
        }//koniec
        boolean valid = (StringUtil.isNotEmpty(id) && idType != null && !InstitutionIdType.ERR.equals(idType))
                || (StringUtil.isEmpty(id) && (idType == null || InstitutionIdType.ERR.equals(idType)));
        if (!valid) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości dla jednego z pól %s lub %s"
                    .concat(getRecordDetails(importResult.getRecordId())), idName, idTypeName), fieldNumber));
        }
    }

    public static void validateMoneyCompleteness(BigDecimal value, CurrencyCode currency, String valueName,
            String currencyName, String fieldNumber, ImportResult importResult) {
        boolean valid = ((value != null && currency != null && !CurrencyCode.ERR.equals(currency))
                || value == null && (currency == null || CurrencyCode.ERR.equals(currency)));
        if (!valid) {
            importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości dla jednego z pól %s lub %s"
                    .concat(getRecordDetails(importResult.getRecordId())), valueName, currencyName), fieldNumber));
        }
    }

    /**
     * WARNING - Walidacja instytucji
     *
     * @param data
     * @param importResult
     * @return
     */
    public static Institution validateInstitution(String[] data, ImportResult importResult) {
        InstitutionData instData = validateInstitutionData(data, importResult);

        String f1 = validateStringField(data[2], null, 40, "DMCL_PSTCD", false, "", importResult);
        String f2 = validateStringField(data[3], null, 60, "DMCL_TWNNM", false, "", importResult);
        String f3 = validateStringField(data[4], null, 150, "DMCL_STRTNM", false, "", importResult);
        String f4 = validateStringField(data[5], null, 20, "DMCL_BLDGID", false, "", importResult);
        String f5 = validateStringField(data[6], null, 20, "DMCL_PRMSID", false, "", importResult);
        String f6 = validateStringField(data[7], null, 208, "DMCL_DTLS", false, "", importResult);

        InstitutionAddress address = new InstitutionAddress(f1, f2, f3, f4, f5, f6);

        return new Institution(instData, address);
    }

    /**
     * WARNING - Walidacja danych instytucji
     */
    public static InstitutionData validateInstitutionData(String[] data, ImportResult importResult) {
        String id = validateStringField(data[0], null, 50, "RPRTID_ID", true, "", importResult);
        InstitutionIdType idType = validateEnumField(InstitutionIdType.class, null, data[1], "RPRTID_TP", true, "", importResult);
        InstitutionData result = new InstitutionData(id, idType);

        //Walidacja kompletności identyfikatora (id + typ id)
        validateIdentifierCompleteness(id, idType,
                "RPRTID_ID", "RPRTID_TP", "", importResult);

        return result;
    }

    /**
     * Kontrola wartosci boolean
     *
     * @return true dla poprawnej wartosci pozytywnej, false dla poprawnej wartosci negatywnej, null dla wartości
     * niepoprawnych
     */
    public static Boolean validateBooleanField(String value, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateBooleanField(value, "1", "0", emptyCheck, fieldName, fieldNumber, importResult);
    }

    /**
     * Kontrola wartosci boolean
     *
     * @return true dla poprawnej wartosci pozytywnej, false dla poprawnej wartosci negatywnej, null dla wartości
     * niepoprawnych
     */
    public static Boolean validateBooleanField(String value, String trueString, String falseString, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        Boolean result = null;
        if (StringUtil.isEmpty(value)) {
            if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        } else {
            try {
                result = BooleanUtils.toBoolean(value, trueString, falseString);
            } catch (IllegalArgumentException ex) {
                importResult.addWarning(new ImportFailLog(String.format("Wartość spoza dziedziny wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        }
        return result;
    }

    /**
     * Kontrola wartosci boolean
     *
     * @return value
     */
    public static String validateStringBooleanField(String value, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateStringBooleanField(value, "1", "0", null, emptyCheck, fieldName, fieldNumber, importResult);
    }

    /**
     * Kontrola wartosci boolean
     *
     * @return value
     */
    public static String validateStringBooleanField(String value, String trueString, String falseString, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateStringBooleanField(value, trueString, falseString, null, emptyCheck, fieldName, fieldNumber, importResult);
    }

    public static String validateStringBooleanField(String value, String trueString, String falseString, String replacementValue, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        String result = null;
        if (StringUtil.isEmpty(value)) {
            if (StringUtil.isNotEmpty(replacementValue)) {
                return replacementValue;
            }
            if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        } else {
            try {
                BooleanUtils.toBoolean(value, trueString, falseString);
                result = value;
            } catch (IllegalArgumentException ex) {
                importResult.addWarning(new ImportFailLog(String.format("Wartość spoza dziedziny wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        }
        return result;
    }

    /**
     * Walidacja pola typu data
     */
    public static Date validateDateField(String value, String dateFormat, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateDateField(value, null, dateFormat, emptyCheck, fieldName, fieldNumber, importResult);
    }

    public static Date validateDateField(String value, Date replacementValue, String dateFormat, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        Date result = null;
        if (StringUtil.isEmpty(value)) {
            if (replacementValue != null) {
                return replacementValue;
            } else if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        } else {
            try {
                result = DateUtils.getDateFromString(value, dateFormat);
                String testDate = DateUtils.formatDate(result, dateFormat);
                if (!testDate.equals(value)) {
                    importResult.addWarning(new ImportFailLog(String.format("Niepoprawna data [%s]/[%s] w polu %s", value, testDate, fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
                    result = null;
                }
            } catch (ParseException ex) {
                LOGGER.info("Niepoprawny format daty w polu " + fieldName, ex);
                importResult.addError(new ImportFailLog(String.format("Oczekiwany inny format wartości %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            } catch (IllegalArgumentException ex) {
                LOGGER.info("Niepoprawny wzorzec daty: " + dateFormat, ex);
            }
        }
        return result;
    }

    /**
     * Walidacja formatu numerycznego
     */
    public static BigDecimal validateAmountField(String value, int precision, int scale, boolean emptyCheck, boolean allowNegative, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateAmountField(value, precision, scale, null, emptyCheck, allowNegative, fieldName, fieldNumber, importResult);
    }

    public static BigDecimal validateAmountField(String value, int precision, int scale, BigDecimal replacementValue, boolean emptyCheck, boolean allowNegative, String fieldName, String fieldNumber, ImportResult importResult) {
        BigDecimal amount = null;
        if (StringUtil.isEmpty(value)) {
            if (replacementValue != null) {
                return replacementValue;
            } else if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        } else {
            try {
                amount = new BigDecimal(value.replaceAll(",", "."));
                if (amount.precision() > precision || amount.scale() > scale) {
                    LOGGER.info(String.format("1| Oczekiwany inny format wartosci pola %s | wartosc: %s| emptyCheck: %s", fieldName, value, emptyCheck));
                    importResult.addError(new ImportFailLog(String.format("Oczekiwany inny format wartosci pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
                }
                if (allowNegative && amount.signum() == -1) {
                    LOGGER.info(String.format("Oczekiwana wartość nieujemna w polu %s ", fieldName).concat(getRecordDetails(importResult.getRecordId())));
                    importResult.addError(new ImportFailLog(String.format("Oczekiwana wartość nieujemna w polu %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
                }
            } catch (NumberFormatException ex) {
                LOGGER.info(String.format("2| Oczekiwany inny format wartosci pola %s | wartosc: %s| emptyCheck: %s", fieldName, value, emptyCheck));
                importResult.addError(new ImportFailLog(String.format("Oczekiwany inny format wartosci pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }

        }
        return amount;
    }

    /**
     * Walidacja pola typu Integer
     */
    public static Integer validateIntegerField(String value, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        return validateIntegerField(value, null, emptyCheck, fieldName, fieldNumber, importResult);
    }

    public static Integer validateIntegerField(String value, Integer replacementValue, boolean emptyCheck, String fieldName, String fieldNumber, ImportResult importResult) {
        Integer result = null;
        if (StringUtil.isEmpty(value)) {
            if (replacementValue != null) {
                return replacementValue;
            } else if (emptyCheck) {
                importResult.addWarning(new ImportFailLog(String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        } else {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                LOGGER.info(String.format("3| Oczekiwany inny format wartosci pola %s | wartosc: %s| emptyCheck: %s", fieldName, value, emptyCheck));
                importResult.addError(new ImportFailLog(String.format("Oczekiwany inny format wartosci pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }
        }
        return result;
    }

    /**
     * Walidacja szczegolow transakcji
     */
    public static TransactionDetails validateTransactionDtls(String[] data, ImportResult importResult,
            BigDecimal unitPrice, CurrencyCode unitPriceCurrency, BigDecimal unitPriceRate, BigDecimal tradeNominal,
            Integer tradeMultipl, Integer tradeQty, BigDecimal tradeUpp, Integer tradeAggrVer,
            BigDecimal optionExecPrice, Date tradeExecDate, Date tradeEffDate, Date tradeMattDate, Date tradeTermDate,
            Date tradeSettlDate, Date tradeSettlDate2 ) {
        return validateTransactionDtls(data, null, importResult, unitPrice, unitPriceCurrency, unitPriceRate,
                tradeNominal, tradeMultipl, tradeQty, tradeUpp, tradeAggrVer, optionExecPrice, tradeExecDate,
                tradeEffDate, tradeMattDate, tradeTermDate, tradeSettlDate, tradeSettlDate2);
    }

    public static TransactionDetails validateTransactionDtls(String[] data, TransactionDetails replacementValue, ImportResult importResult,
            BigDecimal unitPrice, CurrencyCode unitPriceCurrency, BigDecimal unitPriceRate, BigDecimal tradeNominal,
            Integer tradeMultipl, Integer tradeQty,
            BigDecimal tradeUpp, Integer tradeAggrVer, BigDecimal optionExecPrice,
            Date tradeExecDate, Date tradeEffDate, Date tradeMattDate, Date tradeTermDate, Date tradeSettlDate, Date tradeSettlDate2) {

        String r1 = null;
        String r2 = null;
        String r3 = null;
        String r4 = null;
        Compression r5 = null;
//        BigDecimal r6 = null; //TRADADDTLINF_AMT
//        CurrencyCode r7 = null; //TRADADDTLINF_AMT_WAL
//        BigDecimal r8 = null; //TRADADDTLINF_PRCNTG
//        BigDecimal r9 = null; //TRADADDTLINF_NMNLAMT
//        Integer r10 = null; //TRADADDTLINF_PRICMLTPLR
//        Integer r11 = null; //TRADADDTLINF_QTY
//        BigDecimal r12 = null; //TRADADDTLINF_UPPMT
        DeliverType r13 = null;
//        Date r14 = null; //TRADADDTLINF_EXECDTTM
//        Date r15 = null; //TRADADDTLINF_FCTYDT
//        Date r16 = null; //TRADADDTLINF_MTRTYDT
//        Date r17 = null; //TRADADDTLINF_TRMNTNDT
//        Date r18 = null; //TRADADDTLINF_STTLMTDT
        String r19 = null;
//        Integer r20 = null; //TRADADDTLINF_MSTRAGRMNTVRSN
        OptionType r21 = null;
        OptionExecStyle r22 = null;
//        BigDecimal r23 = null; //OPTNTRAD_STRKPRIC
        if (replacementValue != null) {
            r1 = replacementValue.getSourceTransId();
            r2 = replacementValue.getPreviousSourceTransId();
            r3 = replacementValue.getSourceTransRefNr();
            r4 = replacementValue.getRealizationVenue();
            r5 = replacementValue.getCompression();
//            r6 = replacementValue.getUnitPrice();
//            r7 = replacementValue.getUnitPriceCurrency();
//            r8 = replacementValue.getUnitPriceRate();
//            r9 = replacementValue.getNominalAmount();
//            r10 = replacementValue.getPriceMultiplier();
//            r11 = replacementValue.getContractCount();
//            r12 = replacementValue.getInAdvanceAmount();
            r13 = replacementValue.getDelivType();
//            r14 = replacementValue.getExecutionDate();
//            r15 = replacementValue.getEffectiveDate();
//            r16 = replacementValue.getMaturityDate();
//            r17 = replacementValue.getTerminationDate();
//            r18 = replacementValue.getSettlementDate();
            r19 = replacementValue.getFrameworkAggrType();
//            r20 = replacementValue.getFrameworkAggrVer();
            r21 = replacementValue.getOptionType();
            r22 = replacementValue.getOptionExecStyle();
//            r23 = replacementValue.getOptionExecPrice();
        }

        String f1 = validateStringField(data[0], r1, 52, "TRADID_ID", true, "29", importResult);
        String f2 = validateStringField(data[1], r2, 52, "PREV_TRADID_ID", false, "30", importResult);
        String f3 = validateStringField(data[2], r3, 40, "TRADID_TRADREFNB", false, "31", importResult);
        String f4 = validateStringField(data[3], r4, 4, "TRADADDTLINF_VENUEOFEXC", false, "40", importResult);
        Compression f5 = validateEnumField(Compression.class, r5, data[4], "TRADADDTLINF_CMPRSSN", false, "41", importResult);
        DeliverType f13 = validateEnumField(DeliverType.class, r13, data[12], "TRADADDTLINF_DLVRYTP", false, "49", importResult);
        String f19 = validateStringField(data[19], r19, 50, "TRADADDTLINF_MSTRAGRMNTTP", false, "56", importResult);
        OptionType f21 = validateEnumField(OptionType.class, r21, data[21], "OPTNTRAD_OPTNTP", false, "87", importResult);
        OptionExecStyle f22 = validateEnumField(OptionExecStyle.class, r22, data[22], "OPTNTRAD_EXRCSTYLE", false, "88", importResult);

        TransactionDetails details = new TransactionDetails(
                f1,
                f2,
                f3,
                f4,
                f5,
                unitPrice, //TRADADDTLINF_AMT
                unitPriceCurrency, //TRADADDTLINF_AMT_WAL
                unitPriceRate, //TRADADDTLINF_PRCNTG
                tradeNominal, //TRADADDTLINF_NMNLAMT
                tradeMultipl, //TRADADDTLINF_PRICMLTPLR
                tradeQty, //TRADADDTLINF_QTY
                tradeUpp, //TRADADDTLINF_UPPMT
                f13,
                tradeExecDate, //TRADADDTLINF_EXECDTTM
                tradeEffDate, //TRADADDTLINF_FCTYDT
                tradeMattDate, //TRADADDTLINF_MTRTYDT
                tradeTermDate, //TRADADDTLINF_TRMNTNDT
                tradeSettlDate, //TRADADDTLINF_STTLMTDT
                tradeSettlDate2, //TRADADDTLINF_STTMTDT
                f19,
                tradeAggrVer, //TRADADDTLINF_MSTRAGRMNTVRSN
                f21,
                f22,
                optionExecPrice //OPTNTRAD_STRKPRIC
        );

        return details;
    }

    /**
     * Walidacja kontraktu - dane szczegolowe
     */
    public static ContractDataDetailed validateContractDetailed(String[] data, ImportResult importResult) {
        return validateContractDetailed(data, null, importResult);
    }

    public static ContractDataDetailed validateContractDetailed(String[] data, ContractDataDetailed replacementValue, ImportResult importResult) {

        ContractData r1 = null;
        CountryCode r2 = null;
        CurrencyCode r3 = null;
        CurrencyCode r4 = null;
        CurrencyCode r5 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getContractData();
            r2 = replacementValue.getUnderlCountryCode();
            r3 = replacementValue.getUnderlCurrency1Code();
            r4 = replacementValue.getUnderlCurrency2Code();
            r5 = replacementValue.getDelivCurrencyCode();
        }

        ContractData f1 = validateContractData(Arrays.copyOfRange(data, 0, 4), r1, importResult);
        CountryCode f2 = validateEnumField(CountryCode.class, r2, data[4], "CNTRCTTP_ISRCTRY", false, "36", importResult);
        CurrencyCode f3 = validateEnumField(CurrencyCode.class, r3, data[5], "CNTRCTTP_NTNLCCY1", true, "37", importResult);
        CurrencyCode f4 = validateEnumField(CurrencyCode.class, r4, data[6], "CNTRCTTP_NTNLCCY2", false, "38", importResult);
        CurrencyCode f5 = validateEnumField(CurrencyCode.class, r5, data[7], "CNTRCTTP_DLVRBLCCY", false, "39", importResult);

        ContractDataDetailed contract = new ContractDataDetailed(f1, f2, f3, f4, f5);

        return contract;
    }

    /**
     * Walidacja kontraktu - dane podstawowe
     */
    public static ContractData validateContractData(String[] data, ImportResult importResult) {
        return validateContractData(data, null, importResult);
    }

    public static ContractData validateContractData(String[] data, ContractData replacementValue, ImportResult importResult) {
        ContractType r1 = null;
        String r2 = null;
        String r3 = null;
        String r4 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getContractType();
            r2 = replacementValue.getProd1Code();
            r3 = replacementValue.getProd2Code();
            r4 = replacementValue.getUnderlyingId();

        }

        ContractType f1 = validateEnumField(ContractType.class, r1, data[0], "CNTRCTTP_TXNM", true, "32", importResult);
        String f2 = validateStringField(data[1], r2, 20, "CNTRCTTP_PRDCTID1", true, "33", importResult);
        String f3 = validateStringField(data[2], r3, 20, "CNTRCTTP_PRDCTID2", false, "34", importResult);
        String f4 = validateStringField(data[3], r4, 20, "CNTRCTTP_UNDRLYG", true, "35", importResult);

        ContractData contract = new ContractData(f1, f2, f3, f4);

        return contract;
    }

    /**
     * Walidacja ryzyka
     */
    public static RiskReduce validateRiskReduce(String[] data, ImportResult importResult, Date riskConfDate) {
        return validateRiskReduce(data, null, importResult, riskConfDate);
    }

    public static RiskReduce validateRiskReduce(String[] data, RiskReduce replacementValue, ImportResult importResult, Date riskConfDate) {

        ConfirmationType r2 = null;
        if (replacementValue != null) {
            r2 = replacementValue.getConfirmationType();

        }

        RiskReduce result = new RiskReduce(
                riskConfDate,
                validateEnumField(ConfirmationType.class, r2, data[1], "RSKMTGTN_CNFRMTNTP", false, "58", importResult));
        return result;
    }

    /**
     * Walidacja rozliczania
     */
    public static TransactionClearing validateTransactionClearing(String[] data, ImportResult importResult, Date settlementDate) {
        return validateTransactionClearing(data, null, importResult, settlementDate);
    }

    public static TransactionClearing validateTransactionClearing(String[] data, TransactionClearing replacementValue, ImportResult importResult, Date settlementDate) {
        ClearingOblig r1 = null;
        Cleared r2 = null;
        String r4 = null;
        IntergropuTrans r5 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getClearingOblig();
            r2 = replacementValue.getCleared();
            r4 = replacementValue.getCcpCode();
            r5 = replacementValue.getIntergropuTrans();

        }

        ClearingOblig f1 = validateEnumField(ClearingOblig.class, r1, data[0], "CLRGINF_CLROBLGTN", true, "59", importResult);
        Cleared f2 = validateEnumField(Cleared.class, r2, data[1], "CLRGINF_CLRD", false, "60", importResult);
        String f4 = validateStringField(data[3], r4, 20, "CLRGINF_CCP", false, "62", importResult);
        IntergropuTrans f5 = validateEnumField(IntergropuTrans.class, r5, data[4], "CLRGINF_INTRGRP", false, "63", importResult);

        TransactionClearing settlement = new TransactionClearing(f1, f2, settlementDate, f4, f5);
        return settlement;
    }

    /**
     * Walidacja stopy procentowej
     */
    public static PercentageRateData validatePercentageRate(String[] data, ImportResult importResult,
            BigDecimal percLeg1, BigDecimal percLeg2) {
        return validatePercentageRate(data, null, importResult, percLeg1, percLeg2);
    }

    public static PercentageRateData validatePercentageRate(String[] data, PercentageRateData replacementValue, ImportResult importResult,
            BigDecimal percLeg1, BigDecimal percLeg2) {

        String r3 = null;
        String r4 = null;
        String r5 = null;
        String r6 = null;
        String r7 = null;
        String r8 = null;
        if (replacementValue != null) {
            r3 = replacementValue.getFixedRateDayCount();
            r4 = replacementValue.getFixedPaymentFreq();
            r5 = replacementValue.getFloatPaymentFreq();
            r6 = replacementValue.getNewPaymentFreq();
            r7 = replacementValue.getFloatRateLeg1();
            r8 = replacementValue.getFloatRateLeg2();
        }

        String f3 = validateStringField(data[2], r3, 10, "IRTRAD_FXDRATEDAYCNT", false, "66", importResult);
        String f4 = validateStringField(data[3], r4, 10, "IRTRAD_FXDLGPMTFRQCY", false, "67", importResult);
        String f5 = validateStringField(data[4], r5, 10, "IRTRAD_FLTGLGPMTFRQCY", false, "68", importResult);
        String f6 = validateStringField(data[5], r6, 10, "IRTRAD_FLTGLGRSTFRQCY", false, "69", importResult);
        String f7 = validateStringField(data[6], r7, 20, "IRTRAD_IRTRAD_FLTGRATELG1", false, "70", importResult);
        String f8 = validateStringField(data[7], r8, 20, "IRTRAD_IRTRAD_FLTGRATELG2", false, "71", importResult);

        PercentageRateData rate = new PercentageRateData(percLeg1, percLeg2, f3, f4, f5, f6, f7, f8);
        return rate;
    }

    /**
     * Walidacja transakcji walutowej
     */
    public static CurrencyTradeData validateCurrencyTrade(String[] data, ImportResult importResult,
            BigDecimal currRate1, BigDecimal currRate2) {
        return validateCurrencyTrade(data, null, importResult, currRate1, currRate2);
    }

    public static CurrencyTradeData validateCurrencyTrade(String[] data, CurrencyTradeData replacementValue, ImportResult importResult,
            BigDecimal currRate1, BigDecimal currRate2) {

        CurrencyCode r1 = null;
        String r4 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getCurrencyTradeCode();
            r4 = replacementValue.getCurrTradeBasis();

        }

        CurrencyTradeData currTrade = new CurrencyTradeData(
                validateEnumField(CurrencyCode.class, r1, data[0], "FXTRAD_CCY2", false, "72", importResult),
                currRate1, //FXTRAD_XCHGRATE1
                currRate2, //FXTRAD_FRWRDXCHGRATE
                validateStringField(data[3], r4, 10, "FXTRAD_XCHGRATEBSIS", false, "75", importResult));
        return currTrade;
    }

    /**
     * Walidacja transakcji towarowej
     */
    public static CommodityTradeData validateCommodityTrade(String[] data, ImportResult importResult,
            Date delivStart, Date delivEnd, BigDecimal delivQty, BigDecimal delivPrice) {
        return validateCommodityTrade(data, null, importResult, delivStart, delivEnd, delivQty, delivPrice);
    }

    public static CommodityTradeData validateCommodityTrade(String[] data, CommodityTradeData replacementValue, ImportResult importResult,
            Date delivStart, Date delivEnd, BigDecimal delivQty, BigDecimal delivPrice) {

        CommUnderlType r1 = null;
        CommUnderlDtls r2 = null;
        String r3 = null;
        String r4 = null;
        CommLoadType r5 = null;
        String r8 = null;
        if (replacementValue != null) {
            r1 = replacementValue.getCommUnderlType();
            r2 = replacementValue.getCommUnderlDtls();
            r3 = replacementValue.getCommVenue();
            r4 = replacementValue.getCommInterconn();
            r5 = replacementValue.getCommLoadType();
            r8 = replacementValue.getCommContractCount();

        }

        CommUnderlType f1 = validateEnumField(CommUnderlType.class, r1, data[0], "CMMDTYTRAD_CMMDTYBASE", false, "76", importResult);
        CommUnderlDtls f2 = validateEnumField(CommUnderlDtls.class, r2, data[1], "CMMDTYTRAD_ CMMDTYDTLS", false, "77", importResult);
        String f3 = validateStringField(data[2], r3, 16, "CMMDTYTRAD_DLVRYPNT", false, "78", importResult);
        String f4 = validateStringField(data[3], r4, 50, "CMMDTYTRAD_INTRCNNCTNPNT", false, "79", importResult);
        CommLoadType f5 = validateEnumField(CommLoadType.class, r5, data[4], "CMMDTYTRAD_LDTP", false, "80", importResult);
        String f8 = validateStringField(data[7], r8, 50, "CMMDTYTRAD_CNTRCTCPCTY", false, "83", importResult);

        CommodityTradeData trade = new CommodityTradeData(f1, f2, f3, f4, f5, delivStart, delivEnd, f8, delivQty, delivPrice);
        return trade;
    }

    /*
     * Walidacja wyceny
     */
    public static ValuationData validateValuationData(String[] data, ImportResult importResult,
            BigDecimal valuationAmount, Date valuationDate, BigDecimal valuationClientAmount,
            ValuationType r4, CurrencyCode r5) {

        //CurrencyCode f2 = validateEnumField(CurrencyCode.class, r5, data[1], "CCY", true, "3", importResult);
        //ValuationType f4 = validateEnumField(ValuationType.class, r4, data[3], "VALTNTP", true, "5", importResult);
        ValuationData result = new ValuationData(valuationAmount, r5, valuationDate, r4, valuationClientAmount);
        return result;
    }

    private static String getRecordDetails(String recordId) {
        if (StringUtil.isNotEmpty(recordId)) {
            return String.format(" dla rekordu %s", recordId);
        } else {
            return "";
        }
    }

    public static void validateREGON(String value, String fieldName, String fieldNumber, ImportResult importResult) {
        if (StringUtil.isNotEmpty(value) && !ValidationUtils.validREGON(value)) {
            importResult.addWarning(new ImportFailLog(String.format("Wartość pola %s jest nieprawidłowa",
                    fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
        }
    }

    public static void validateNIP(String value, String fieldName, String fieldNumber, ImportResult importResult) {
        if (StringUtil.isNotEmpty(value) && !ValidationUtils.validNIP(value)) {
            importResult.addWarning(new ImportFailLog(String.format("Wartość pola %s jest nieprawidłowa",
                    fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
        }
    }

    /**
     * Walidacja formatu numerycznego
     */
    public static void validateAmountField(BigDecimal amount, int precision, int scale, boolean emptyCheck, boolean allowNegative, String fieldName, String fieldNumber, ImportResult importResult) {
        //BigDecimal amount = null;
        if (emptyCheck && amount == null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING, String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
        } else if (amount != null) {
            try {
                //amount = new BigDecimal(value.replaceAll(",", "."));
                if (amount.precision() > precision || amount.scale() > scale) {
                    LOGGER.info(String.format("1| Oczekiwany inny format wartosci pola %s | wartosc: %s| emptyCheck: %s", fieldName, amount.toString(), emptyCheck));
                    importResult.addWarning(new ImportFailLog(ImportStatus.ERROR, String.format("Oczekiwany inny format wartosci pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
                }
                if (allowNegative && amount.signum() == -1) {
                    LOGGER.info(String.format("Oczekiwana wartość nieujemna w polu %s ", fieldName).concat(getRecordDetails(importResult.getRecordId())));
                    importResult.addWarning(new ImportFailLog(ImportStatus.ERROR, String.format("Oczekiwana wartość nieujemna w polu %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
                }
            } catch (NumberFormatException ex) {
                LOGGER.info(String.format("2| Oczekiwany inny format wartosci pola %s | wartosc: %s| emptyCheck: %s", fieldName, amount.toString(), emptyCheck));
                importResult.addError(new ImportFailLog(ImportStatus.ERROR, String.format("Oczekiwany inny format wartosci pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
            }

        }
    }

    /**
     * Walidacja pola typu Integer
     */
    public static void validateLongField(Long value, boolean emptyCheck, boolean allowNegative, String fieldName, String fieldNumber, ImportResult importResult) {

        if (emptyCheck && value == null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING, String.format("Brak obowiązkowej wartości pola %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
        } else if (value != null && allowNegative && value.intValue() > 0) {
            LOGGER.info(String.format("Oczekiwana wartość nieujemna w polu %s ", fieldName).concat(getRecordDetails(importResult.getRecordId())));
            importResult.addError(new ImportFailLog(ImportStatus.ERROR, String.format("Oczekiwana wartość nieujemna w polu %s", fieldName).concat(getRecordDetails(importResult.getRecordId())), fieldNumber));
        }

    }

    /**
     * Walidacja pól związanych z AMT.
     *
     * @param amt
     * @param amtVal
     * @param prcntg
     * @param importResult
     * @return
     */
    public static CurrencyCode validateAmlFields(BigDecimal amt, String amtVal, BigDecimal prcntg, ImportResult importResult) {
        //Walidacja pól TRADADDTLINF_AMT, TRADADDTLINF_AMT_WAL, TRADADDTLINF_PRCNTG
        CurrencyCode validateAmtWal = null;
        if (amt != null && amt.compareTo(BigDecimal.ZERO) == 0 && amtVal == null) {
            return validateAmtWal;
        }
        if (amt != null && amtVal == null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING,
                    "Brak waluty instrumentu pochodnego w polu UNIT_PRICE_CURRENCY / TRADADDTLINF_AMT_WAL wymaganej przez pole UNIT_PRICE / TRADADDTLINF_AMT dla " + getRecordDetails(importResult.getRecordId())));
        } else if (amt == null && amtVal != null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING,
                    "Brak ceny jednostkowej instrumentu pochodnego w polu UNIT_PRICE / TRADADDTLINF_AMT wymaganej przez pole UNIT_PRICE_CURRENCY / TRADADDTLINF_AMT_WAL dla " + getRecordDetails(importResult.getRecordId())));
        }

        if (amt == null && prcntg == null && amtVal != null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING,
                    "Brak obowiązkowej wartości w jednym z pól UNIT_PRICE / TRADADDTLINF_AMT, UNIT_PERCENTAGE_RATE / TRADADDTLINF_PRCNTG dla " + getRecordDetails(importResult.getRecordId())));

        }

        if (amt != null && amtVal != null) {
            validateAmtWal = validateEnumField(CurrencyCode.class, null, amtVal, "UNIT_PRICE_CURRENCY / TRADADDTLINF_AMT_WAL", false, "43", importResult);
        }
        return validateAmtWal;
    }

    public static void validateTerminationDate(OriginalStatus originalStatus, Date terminationDate, ImportResult importResult) {
        if (OriginalStatus.C.equals(originalStatus) && terminationDate == null) {
            importResult.addWarning(new ImportFailLog(ImportStatus.WARRNING,
                    "Brak daty rozwiązania kontraktu w polu TRADADDTLINF_TRMNTNDT / TRMNTNDT dla " + getRecordDetails(importResult.getRecordId())));
        }
    }
}
