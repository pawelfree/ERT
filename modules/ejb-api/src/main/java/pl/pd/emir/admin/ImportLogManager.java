package pl.pd.emir.admin;

import java.util.List;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.manager.GenericManager;

public interface ImportLogManager extends GenericManager<ImportLog> {

    List<ImportFailLog> getImportFailLog(Long id, int indexStart, int indexEnd);
}
