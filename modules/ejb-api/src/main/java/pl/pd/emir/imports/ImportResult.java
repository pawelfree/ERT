package pl.pd.emir.imports;

import java.util.ArrayList;
import java.util.List;
import pl.pd.emir.entity.Extract;
import pl.pd.emir.entity.ImportFailLog;
import static pl.pd.emir.entity.utils.BaseValidationUtils.addErrorToErrorList;
import static pl.pd.emir.entity.utils.BaseValidationUtils.addWarningToWarningList;
import pl.pd.emir.enums.ValidationStatus;

public class ImportResult<T extends Extract> {

    private T extract;

    private String recordId;

    private List<ImportFailLog> importErrors = new ArrayList<>();

    private List<ImportFailLog> importWarnings = new ArrayList<>();

    public T getExtract() {
        return extract;
    }

    public void setExtract(T extract) {
        this.extract = extract;
    }

    public List<ImportFailLog> getImportErrors() {
        return importErrors;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setImportErrors(List<ImportFailLog> importErrors) {
        this.importErrors = importErrors;
    }

    public List<ImportFailLog> getImportWarnings() {
        return importWarnings;
    }

    public void setImportWarnings(List<ImportFailLog> importWarnings) {
        this.importWarnings = importWarnings;
    }

    public void addError(ImportFailLog log) {
        addErrorToErrorList(log, this.importErrors);
    }

    public void addWarning(ImportFailLog log) {
        addWarningToWarningList(log, this.importWarnings);
    }

    public boolean hasErrors() {
        return !this.importErrors.isEmpty();
    }

    public boolean hasWarnings() {
        return !this.importWarnings.isEmpty();
    }

    public ValidationStatus getValidationStatus() {
        if (hasErrors()) {
            return ValidationStatus.INVALID;
        } else if (hasWarnings()) {
            return ValidationStatus.INCOMPLETE;
        } else {
            return ValidationStatus.VALID;
        }
    }

    public void clear() {
        importErrors = new ArrayList<>();
        importWarnings = new ArrayList<>();
    }
}
