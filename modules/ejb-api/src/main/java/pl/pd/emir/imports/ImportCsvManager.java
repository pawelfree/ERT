package pl.pd.emir.imports;

import java.util.Date;
import java.util.List;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.exceptions.EMIRException;

public interface ImportCsvManager {

    ImportOverview importCsv(List<ImportScope> importScope, Date importFileDate, boolean backloading) throws EMIRException;
}
