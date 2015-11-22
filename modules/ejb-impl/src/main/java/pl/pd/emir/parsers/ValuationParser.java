package pl.pd.emir.parsers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.ValuationData;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.imports.utils.ImportValidationUtils;
import static pl.pd.emir.imports.utils.ImportValidationUtils.validateEnumField;

public class ValuationParser extends BaseCsvParser<Valuation> {

    private static final int COLUMN_COUNT = 7;
    private transient BigDecimal valuationAmount, valuationClientAmount;
    private transient Date transactionDate, valuationDate;
    private transient CurrencyCode currencyCode;
    private transient ValuationType valuationType;
    private transient BigDecimal r1 = null;
    private transient BigDecimal r2 = null;
    private transient Date r3 = null;
    private transient ValuationType r4 = null;
    private transient CurrencyCode r5 = null;

    public ValuationParser() {
    }

    /**
     * Przetwarzanie pojedynczego wiersza ekstraktu.
     *
     */
    @Override
    public Valuation parseAndValidateRow(ImportResult importResult, String... data) {

        Valuation valuation = null;

        //walidacja pod kątem błędów
        if (validateErrors(importResult, data)) {
            //walidacja pod kontem ostrzeżeń
            String f1 = ImportValidationUtils.validateStringField(data[0], 50, "ID_TR", true, "1", importResult);
            ValuationData f3 = ImportValidationUtils.validateValuationData(Arrays.copyOfRange(data, 2, 7), importResult, valuationAmount, valuationDate, valuationClientAmount, valuationType, currencyCode);

            valuation = new Valuation(f1, transactionDate, f3);
        }
        return valuation;
    }

    /**
     * Weryfikacja pod kątem błędów
     *
     * @param data dane pojedynczego rekordu z wczytywanego ekstraktu
     * @return false w przypadku wystĂ„â€¦pienia bÄąâ€šĂ„â„˘dÄ‚Ĺ‚w walidacji, w przeciwnym przypadku true
     */
    private boolean validateErrors(ImportResult importResult, String[] data) {
        boolean mandatory = false;
        //pole ID_TR musi byĂ„â€ˇ uzupeÄąâ€šnione
        if (StringUtil.isEmpty(data[0])) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.EMPTY_VALUATION_ID, rowNum)));
        }

        //liczba kolumn w wierszu
        int size = Arrays.asList(data).size();
        if (size != COLUMN_COUNT) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INVALID_COLUMN_COUNT_ERROR, importResult.getRecordId())));
            return false;
        }

        if (!StringUtil.isEmpty(data[2])
                || !StringUtil.isEmpty(data[3])
                || !StringUtil.isEmpty(data[4])
                || !StringUtil.isEmpty(data[5])
                || !StringUtil.isEmpty(data[6])) {
            mandatory = true;
        }

        //kontrola wartosci numerycznych
        valuationAmount = ImportValidationUtils.validateAmountField(data[2], 25, 5, r1, mandatory, false, "MTMVAL", "3", importResult);
        valuationClientAmount = ImportValidationUtils.validateAmountField(data[6], 25, 5, r2, false, false, "MTMVAL_C", "7", importResult);

        //kontrola dat
        transactionDate = ImportValidationUtils.validateDateField(data[1], DateUtils.ISO_DATE_FORMAT, mandatory, "DATA_TR", "2", importResult);
        valuationDate = ImportValidationUtils.validateDateField(data[4], r3, DateUtils.ISO_DATE_TIME_FORMAT, mandatory, "VALTNDTTM", "5", importResult);

        currencyCode = validateEnumField(CurrencyCode.class, r5, data[3], "CCY", mandatory, "4", importResult);
        valuationType = validateEnumField(ValuationType.class, r4, data[5], "VALTNTP", mandatory, "6", importResult);

        return !importResult.hasErrors();
    }

    @Override
    public Integer getRowIdIndex() {
        return 0;
    }
}
