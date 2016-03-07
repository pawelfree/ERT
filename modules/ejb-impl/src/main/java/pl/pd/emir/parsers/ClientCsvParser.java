package pl.pd.emir.parsers;

import java.util.Arrays;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.enums.ContrPartyIndustry;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.imports.utils.ImportValidationUtils;
import org.apache.commons.lang3.ArrayUtils;

public class ClientCsvParser extends BaseCsvParser<Client> {

    private static final int COLUMN_COUNT = 18;

    /**
     * Przetwarzanie pojedynczego wiersza ekstraktu. *
     */
    @Override
    public Client parseAndValidateRow(ImportResult importResult, String... data) {

        Client client = null;

        //walidacja pod kontem błędów
        if (validateErrors(importResult, data)) {

            //instytucja
            String[] institutionData = ArrayUtils.addAll(Arrays.copyOfRange(data, 5, 7), Arrays.copyOfRange(data, 8, 14));

            //walidacja pod kontem ostrzeżeń
            String f1 = ImportValidationUtils.validateStringField(data[0], 100, "ID_KLIENTA", false, "1", importResult);
            String f2 = ImportValidationUtils.validateStringField(data[1], 100, "NAZWA_KLIENTA", false, "2", importResult);
            CountryCode f3 = ImportValidationUtils.validateEnumField(CountryCode.class, data[2], "COUNTRY", true, "3", importResult);
            BusinessEntity f4 = ImportValidationUtils.validateBusinessEntity(Arrays.copyOfRange(data, 3, 5), false, importResult);
            Boolean f5 = ImportValidationUtils.validateBooleanField(data[7], "Y", "N", true, "RAPORTOWANY", "8", importResult);
            Boolean f51 = true;
            Institution f6 = ImportValidationUtils.validateInstitution(institutionData, importResult);
            ContrPartyIndustry f7 = ImportValidationUtils.validateEnumField(ContrPartyIndustry.class, data[14], "CORPSCTR", false, "15", importResult);
            String f8 = ImportValidationUtils.validateStringField(data[15], 1, "FINNONFIN_ID", true, "16", importResult);
            String f9 = ImportValidationUtils.validateStringBooleanField(data[16], "Y", "N", true, "EOG", "17", importResult);
            Boolean f10 = ImportValidationUtils.validateBooleanField(data[17], "Y", "N", true, "OSOBA_FIZYCZNA", "18", importResult);

            client = new Client(f1, f2, f3, f4, f5, f51, f6, f7, f8, f9, f10, ValidationStatus.VALID);

            client.setValidationStatus(importResult.getValidationStatus());

        }

        return client;
    }

    /**
     * Weryfikacja pod kontem błedów.
     *
     * @param data dane pojedynczego rekordu z wczytywanego ekstraktu
     * @return false w przypadku wystąpienia błędów walidacji, w przeciwnym przypadku true
     */
    private boolean validateErrors(ImportResult importResult, String[] data) {
        //pole ID_KLIENTA musi byc uzupelnione
        if (StringUtil.isEmpty(data[0])) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.EMPTY_CLIENT_ID, rowNum)));
        }

        //ilość kolumn w wierszu
        int size = Arrays.asList(data).size();
        if (size != COLUMN_COUNT) {
            importResult.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INVALID_COLUMN_COUNT_ERROR, importResult.getRecordId())));
        }

        return !importResult.hasErrors();
    }

    @Override
    public Integer getRowIdIndex() {
        return 0;
    }
}
