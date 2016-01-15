package pl.pd.emir.parsers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.ArrayUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.BusinessEntityData;
import pl.pd.emir.embeddable.CommodityTradeData;
import pl.pd.emir.embeddable.ContractDataDetailed;
import pl.pd.emir.embeddable.CurrencyTradeData;
import pl.pd.emir.embeddable.PercentageRateData;
import pl.pd.emir.embeddable.RiskReduce;
import pl.pd.emir.embeddable.TransactionClearing;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.imports.utils.ImportValidationUtils;
import static pl.pd.emir.imports.utils.ImportValidationUtils.validateAmountField;
import static pl.pd.emir.imports.utils.ImportValidationUtils.validateEnumField;

public class TransactionCsvParser extends BaseCsvParser<Transaction> {

    protected static final int COLUMN_COUNT = 88;

    protected transient TransactionParty transactionPartyField4;

    protected transient Date transactionDateField1,
            tradeExecDateField49, tradeEffDateField50, tradeMattDateField51, tradeTermDateField52, tradeSettlDateField53,
            riskConfDateField56,
            settlementDateField60,
            delivStartField80, delivEndField81;

    protected transient Integer tradeMultiplField45, tradeQtyField46, tradeAggrVerField55;

    protected transient BigDecimal unitPriceField41, unitPriceRateField43, tradeNominalField44, tradeUppField47,
            currRate1Field72, currRate2Field73,
            delivQtyField83, delivPriceField84,
            percLeg1Field63, percLeg2Field64,
            optionExecPriceField87;

    protected transient CurrencyCode unitPriceCurrencyField42;

    public TransactionCsvParser() {
    }

    /**
     * Przetwarzanie pojedynczego wiersza ekstraktu.
     *
     */
    @Override
    public Transaction parseAndValidateRow(ImportResult importResult, String... data) {

        Transaction transaction = null;

        //walidacja pod kontem błędów
        if (validateErrors(importResult, data)) {

            String[] transactionDtls = ArrayUtils.addAll(Arrays.copyOfRange(data, 28, 31), Arrays.copyOfRange(data, 39, 56)); //szczegoly transakcji
            transactionDtls = ArrayUtils.addAll(transactionDtls, Arrays.copyOfRange(data, 85, 88)); //szczegoly transakcji

            //Wczytanie wartości z szablonu transakcji
            OriginalStatus r3 = null; //STATUS_TR
            String r4 = null; //ID_KLIENTA
            String r41 = null; //ID_KLIENTA2
            String r5 = null; //POTWIERDZONA dla BTMU powinno być wymagane
            BusinessEntityData r6 = null; //kontrahent: dane klienta2 np BTMU
            BusinessEntityData r7 = null; //kontrahent: dane klienta
            ContractDataDetailed r8 = null; //kontrakt 4.2
            TransactionDetails r9 = null;
            RiskReduce r10 = null; //ryzyko 4.4
            TransactionClearing r11 = null; //rozliczanie 4.5
            PercentageRateData r12 = null;
            CurrencyTradeData r13 = null; //transakcje walut.
            CommodityTradeData r14 = null;

            //walidacja pod kontem ostrzeżeń
            String f1 = ImportValidationUtils.validateStringField(data[0], null, 50, "ID_TR", true, "1", importResult); //ID_TR
            OriginalStatus f3 = ImportValidationUtils.validateEnumField(OriginalStatus.class, r3, data[2], "STATUS_TR", true, "3", importResult); //STATUS_TR
            //TODO chwilowe bo kontrachent i klient to nie to samo
            String f4  = ImportValidationUtils.validateStringField(data[3],  r4, 100, "ID_KLIENTA", true, "4", importResult); //ID_KLIENTA
            String f41 = ImportValidationUtils.validateStringField(data[11], r41, 100, "ID_KLIENTA2",true, "12", importResult); 
            String f5 = ImportValidationUtils.validateStringBooleanField(data[5], "1", "0", r5, false, "POTWIERDZONA", "6", importResult); //POTWIERDZONA dla BTMU powinno być wymagane
            BusinessEntityData f6 = ImportValidationUtils.validateBusinessEntityData(Arrays.copyOfRange(data, 6, 17), true, r6, importResult); //kontrahent2 dane klienta np BTMU
            BusinessEntityData f7 = ImportValidationUtils.validateBusinessEntityData(Arrays.copyOfRange(data, 17, 28), false, r7, importResult); //kontrahent dane klienta
            ContractDataDetailed f8 = ImportValidationUtils.validateContractDetailed(Arrays.copyOfRange(data, 31, 39), r8, importResult); //kontrakt 4.2
            TransactionDetails f9 = ImportValidationUtils.validateTransactionDtls(transactionDtls, r9, importResult,
                    unitPriceField41, unitPriceCurrencyField42, unitPriceRateField43,
                    tradeNominalField44, tradeMultiplField45, tradeQtyField46,
                    tradeUppField47, tradeAggrVerField55, optionExecPriceField87,
                    tradeExecDateField49, tradeEffDateField50, tradeMattDateField51,
                    tradeTermDateField52, tradeSettlDateField53);
            RiskReduce f10 = ImportValidationUtils.validateRiskReduce(Arrays.copyOfRange(data, 56, 58), r10, importResult, riskConfDateField56); //ryzyko 4.4
            TransactionClearing f11 = ImportValidationUtils.validateTransactionClearing(Arrays.copyOfRange(data, 58, 63), r11, importResult, settlementDateField60); //rozliczanie 4.5
            PercentageRateData f12 = ImportValidationUtils.validatePercentageRate(Arrays.copyOfRange(data, 63, 71), r12,
                    importResult, //stopa procent.
                    percLeg1Field63, percLeg2Field64);
            CurrencyTradeData f13 = ImportValidationUtils.validateCurrencyTrade(Arrays.copyOfRange(data, 71, 75), r13, importResult,
                    currRate1Field72, currRate2Field73); //transakcje walut.
            CommodityTradeData f14 = ImportValidationUtils.validateCommodityTrade(Arrays.copyOfRange(data, 75, 85), r14, importResult,
                    delivStartField80, delivEndField81, delivQtyField83, delivPriceField84);

            //walidacja zależności pomiędzy ceną a symbolem waluty
            ImportValidationUtils.validateMoneyCompleteness(unitPriceField41, unitPriceCurrencyField42, "TRADADDTLINF_AMT", "TRADADDTLINF_AMT_WAL", "43", importResult);

            //TODO: usunąć DataType bo to jest to samo co OriginalStatus
            DataType dataType = DataType.NEW;
            if (OriginalStatus.M.equals(f3)) {
                dataType = DataType.ONGOING;
            } else if (OriginalStatus.C.equals(f3)) {
                dataType = DataType.COMPLETED;
            }

            transaction = new Transaction(f1, transactionDateField1, f3, f4, transactionPartyField4, f41 ,f5, f6, f7, f8, f9,
                    f10, f11, f12, f13, f14, dataType, ProcessingStatus.NEW, ValidationStatus.VALID, new Client(), new Client());

            transaction.setValidationStatus(importResult.getValidationStatus());

        }

        return transaction;
    }

    /**
     * Weryfikacja pod kontem błedów.
     *
     * @param data dane pojedynczego rekordu z wczytywanego ekstraktu
     * @return false w przypadku wystąpienia błędów walidacji, w przeciwnym przypadku true
     */
    protected boolean validateErrors(ImportResult importResult, String[] data) {
        //pole ID_TR musi byc uzupelnione
        if (StringUtil.isEmpty(data[0])) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.EMPTY_TRANSACTION_ID, rowNum)));
        }

        //ilosc kolumn w wierszu
        int size = Arrays.asList(data).size();
        if (size != COLUMN_COUNT) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INVALID_COLUMN_COUNT_ERROR, importResult.getRecordId())));
            return false;
        }

        //wczytanie wartości z szablonu transakcji
        //wartości numeryczne
        TransactionParty r4 = null;
        BigDecimal r41 = null;
        CurrencyCode r42 = null;
        BigDecimal r43 = null;
        BigDecimal r44 = null;
        Integer r45 = null;
        Integer r46 = null;
        BigDecimal r47 = null;
        Integer r55 = null;
        BigDecimal r63 = null;
        BigDecimal r64 = null;
        BigDecimal r72 = null;
        BigDecimal r73 = null;
        BigDecimal r83 = null;
        BigDecimal r84 = null;
        BigDecimal r87 = null;
        //daty
        Date r49 = null;
        Date r50 = null;
        Date r51 = null;
        Date r52 = null;
        Date r53 = null;
        Date r56 = null;
        Date r60 = null;
        Date r80 = null;
        Date r81 = null;

        //kontrola wartosci numerycznych
        transactionPartyField4 = ImportValidationUtils.validateEnumField(TransactionParty.class, r4, data[4], "STRONA_TR", true, "5", importResult);
        unitPriceField41 = validateAmountField(data[41], 25, 5, r41, false, true, "TRADADDTLINF_AMT", "42", importResult);
        unitPriceCurrencyField42 = validateEnumField(CurrencyCode.class, r42, data[42], "TRADADDTLINF_AMT_WAL", false, "43", importResult);
        unitPriceRateField43 = validateAmountField(data[43], 25, 5, r43, false, true, "TRADADDTLINF_PRCNTG", "44", importResult);
        tradeNominalField44 = ImportValidationUtils.validateAmountField(data[44], 22, 2, r44, true, true, "TRADADDTLINF_NMNLAMT", "45", importResult);
        tradeMultiplField45 = ImportValidationUtils.validateIntegerField(data[45], r45, false, "TRADADDTLINF_PRICMLTPLR", "46", importResult);
        tradeQtyField46 = ImportValidationUtils.validateIntegerField(data[46], r46, true, "TRADADDTLINF_QTY", "47", importResult);
        tradeUppField47 = ImportValidationUtils.validateAmountField(data[47], 12, 2, r47, false, true, "TRADADDTLINF_UPPMT", "48", importResult);
        tradeAggrVerField55 = ImportValidationUtils.validateIntegerField(data[55], r55, false, "TRADADDTLINF_MSTRAGRMNTVRSN", "56", importResult);
        percLeg1Field63 = ImportValidationUtils.validateAmountField(data[63], 25, 5, r63, false, true, "IRTRAD_FXDRATELG1", "64", importResult);
        percLeg2Field64 = ImportValidationUtils.validateAmountField(data[64], 25, 5, r64, false, true, "IRTRAD_FXDRATELG2", "65", importResult);
        currRate1Field72 = ImportValidationUtils.validateAmountField(data[72], 15, 5, r72, false, true, "FXTRAD_XCHGRATE1", "73", importResult);
        currRate2Field73 = ImportValidationUtils.validateAmountField(data[73], 15, 5, r73, false, true, "FXTRAD_FRWRDXCHGRATE", "74", importResult);
        delivQtyField83 = ImportValidationUtils.validateAmountField(data[83], 12, 2, r83, false, false, "CMMDTYTRAD_QTY", "84", importResult);
        delivPriceField84 = ImportValidationUtils.validateAmountField(data[84], 12, 2, r84, false, false, "CMMDTYTRAD_PRIC", "85", importResult);
        optionExecPriceField87 = ImportValidationUtils.validateAmountField(data[87], 12, 2, r87, false, false, "OPTNTRAD_STRKPRIC", "88", importResult);

        //kontrola dat
        transactionDateField1 = ImportValidationUtils.validateDateField(data[1], null, DateUtils.ISO_DATE_FORMAT, true, "DATA_TR", "2", importResult);
        tradeExecDateField49 = ImportValidationUtils.validateDateField(data[49], r49, DateUtils.ISO_DATE_TIME_FORMAT, true, "TRADADDTLINF_EXECDTTM", "50", importResult);
        tradeEffDateField50 = ImportValidationUtils.validateDateField(data[50], r50, DateUtils.ISO_DATE_TIME_FORMAT, false, "TRADADDTLINF_FCTYDT", "51", importResult);
        tradeMattDateField51 = ImportValidationUtils.validateDateField(data[51], r51, DateUtils.ISO_DATE_TIME_FORMAT, false, "TRADADDTLINF_MTRTYDT", "52", importResult);
        tradeTermDateField52 = ImportValidationUtils.validateDateField(data[52], r52, DateUtils.ISO_DATE_TIME_FORMAT, "C".equalsIgnoreCase(data[2]), "TRADADDTLINF_TRMNTNDT", "53", importResult);
        tradeSettlDateField53 = ImportValidationUtils.validateDateField(data[53], r53, DateUtils.ISO_DATE_TIME_FORMAT, false, "TRADADDTLINF_STTLMTDT", "54", importResult);
        riskConfDateField56 = ImportValidationUtils.validateDateField(data[56], r56, DateUtils.ISO_DATE_TIME_FORMAT, false, "RSKMTGTN_CNFRMTNDTTM", "57", importResult);
        settlementDateField60 = ImportValidationUtils.validateDateField(data[60], r60, DateUtils.ISO_DATE_TIME_FORMAT, false, "CLRGINF_CLRDTTM", "61", importResult);
        delivStartField80 = ImportValidationUtils.validateDateField(data[80], r80, DateUtils.ISO_DATE_TIME_FORMAT, false, "CMMDTYTRAD_DLVRYSTARTDTTM", "81", importResult);
        delivEndField81 = ImportValidationUtils.validateDateField(data[81], r81, DateUtils.ISO_DATE_TIME_FORMAT, false, "CMMDTYTRAD_DLVRYENDDTTM", "82", importResult);

        return !importResult.hasErrors();
    }

    @Override
    public Integer getRowIdIndex() {
        return 0;
    }

}
