package pl.pd.emir.kdpw.api;

import java.io.InputStream;
import pl.pd.emir.kdpw.api.to.ImportResult;

public interface KdpwImportManager {

    void importFile(InputStream stream, String fileName);

    void processFile(String fileName, String fileAsString, String userLogin);

    void logError(final ImportResult result);
}
