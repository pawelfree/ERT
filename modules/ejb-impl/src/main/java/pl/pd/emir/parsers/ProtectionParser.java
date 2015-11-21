package pl.pd.emir.parsers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.administration.TransactionTemplate;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.imports.utils.ImportValidationUtils;

public class ProtectionParser extends BaseCsvParser<Protection> {

    private static final int COLUMN_COUNT = 8;
    private transient BigDecimal protectionAmount, protectionClientAmount;
    private transient Date transactionDate;
    private transient TransactionTemplate transactionTemplate;
    private BigDecimal r1 = null;
    private BigDecimal r2 = null;

    public ProtectionParser(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Protection parseAndValidateRow(ImportResult importResult, String... data) {

        Protection protection = null;

        if (transactionTemplate == null) {
            transactionTemplate = new TransactionTemplate(false);
        }
        CurrencyCode r3 = null;
        DoProtection r4 = null;
        String r5 = null;
        YesNo r6 = null;

        if (Objects.nonNull(transactionTemplate) && Objects.nonNull(transactionTemplate.getProtection())) {
            r1 = transactionTemplate.getProtection().getAmount();
            r2 = transactionTemplate.getProtection().getClientAmount();
            r3 = transactionTemplate.getProtection().getCurrencyCode();
            r4 = transactionTemplate.getProtection().getIsProtection();
            r5 = transactionTemplate.getProtection().getWalletId();
            r6 = transactionTemplate.getProtection().getWalletProtection();
        }

        //walidacja pod kontem błędów
        if (validateErrors(importResult, data)) {

            //walidacja pod kontem ostrzeżeń
            String f1 = ImportValidationUtils.validateStringField(data[0], 50, "ID_TR", true, "1", importResult);
            DoProtection f3 = ImportValidationUtils.validateEnumField(DoProtection.class, r4, data[2], "COLLTN", true, "3", importResult);
            YesNo f4 = ImportValidationUtils.validateEnumField(YesNo.class, r6, data[3], "PRTFCOLL", false, "4", importResult);
            String f5 = ImportValidationUtils.validateStringField(data[4], r5, 35, "PRTFID", false, "5", importResult);
            CurrencyCode f7 = ImportValidationUtils.validateEnumField(CurrencyCode.class, r3, data[6], "COLLCCY", false, "7", importResult);
            protection = new Protection(f1, transactionDate, f3, f4, f5, protectionAmount, f7, protectionClientAmount);
            importResult.setExtract(protection);
        }

        return protection;
    }

    /**
     * Weryfikacja pod kontem błedów.
     *
     * @param data dane pojedynczego rekordu z wczytywanego ekstraktu
     * @return false w przypadku wystąpienia błędów walidacji, w przeciwnym przypadku true
     */
    private boolean validateErrors(ImportResult importResult, String[] data) {
        //pole ID_TR musi być usupełnione
        if (StringUtil.isEmpty(data[0])) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.EMPTY_VALUATION_ID, rowNum)));
        }

        //ilosc kolumn w wierszu
        int size = Arrays.asList(data).size();
        if (size != COLUMN_COUNT) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INVALID_COLUMN_COUNT_ERROR, importResult.getRecordId())));
            return false;
        }

        //kontrola wartosci numerycznych
        protectionAmount = ImportValidationUtils.validateAmountField(data[5], 25, 5, r1, false, true, "COLLVAL", "6", importResult);
        protectionClientAmount = ImportValidationUtils.validateAmountField(data[7], 25, 5, r2, false, true, "COLLVAL_C", "8", importResult);

        //kontrola dat
        transactionDate = ImportValidationUtils.validateDateField(data[1], DateUtils.ISO_DATE_FORMAT, true, "DATA_TR", "2", importResult);

        return !importResult.hasErrors();
    }

    @Override
    public Integer getRowIdIndex() {
        return 0;
    }
}
