package pl.pd.emir.parsers;

import pl.pd.emir.imports.utils.ImportValidationUtils;
import java.util.Arrays;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;

public class BankCsvParser extends BaseCsvParser<Bank> {

    private static final int COLUMN_COUNT = 18;

    /**
     * Przetwarzanie pojedynczego wiersza ekstraktu.
     *
     * @param data czytany wiersz
     * @return rezultat importu pojedynczego ekstraktu
     */
    @Override
    public Bank parseAndValidateRow(ImportResult importResult, String... data) {

        Bank bank = null;

        //walidacja pod kontem błędów
        if (validateErrors(importResult, data)) {
            //walidacja pod kontem ostrzeżeń
            String f1 = ImportValidationUtils.validateStringField(data[0], 8, "NR_BANKU", false, "1", importResult);
            String f2 = ImportValidationUtils.validateStringField(data[1], 100, "NAZWA_BANKU", false, "2", importResult);
            //TODO PAWEL - F3 jest nieuzywane XML_BANK_NAME
            //String f3 = ImportValidationUtils.validateStringField(data[2], 100, "NAZWA_BANKU_KOMUNIKAT", true, "3", importResult);
            String f4 = ImportValidationUtils.validateStringField(data[3], 4, "SENDER_ID", false, "4", importResult);
            String f5 = ImportValidationUtils.validateStringField(data[4], 4, "SENDER_ID_KDPW", true, "5", importResult);
            CountryCode f6 = ImportValidationUtils.validateEnumField(CountryCode.class, data[5], "COUNTRY", true, "6", importResult);
            BusinessEntity f7 = ImportValidationUtils.validateBusinessEntity(Arrays.copyOfRange(data, 6, 8), false, importResult);
            Institution f8 = ImportValidationUtils.validateInstitution(Arrays.copyOfRange(data, 8, 16), importResult);
            String f9 = ImportValidationUtils.validateStringField(data[16], 1, "CORPSCTR", false, "17", importResult);
            String f10 = ImportValidationUtils.validateStringField(data[17], 1, "FINNONFIN_ID", true, "18", importResult);

            bank = new Bank(f1, f2, f4, f5, f6, f7, f8, f9, f10);

        }

        return bank;
    }

    /**
     * Weryfikacja pod kontem błedów.
     *
     * @param data dane pojedynczego rekordu z wczytywanego ekstraktu
     * @return false w przypadku wystąpienia błędów walidacji, w przeciwnym przypadku true
     */
    private boolean validateErrors(ImportResult importResult, String[] data) {
        //ilość kolumn w wierszu
        int size = data.length;
        if (size != COLUMN_COUNT) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INVALID_COLUMN_COUNT_ERROR, importResult.getRecordId())));
        }

        return !importResult.hasErrors();
    }

    @Override
    public Integer getRowIdIndex() {
        return null; //tylko numer wiersza bez id
    }
}
