package pl.pd.emir.entity.utils;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.enums.ImportStatus;

public class BaseValidationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseValidationUtils.class);

    public static void addErrorToErrorList(ImportFailLog log, List<ImportFailLog> importErrors) {
        if (log.getErrorCategory() != null && !ImportStatus.ERROR.equals(log.getErrorCategory())) {
            LOGGER.info(String.format("Change import faillog status from %s to %s.", log.getErrorCategory().name(),
                    ImportStatus.ERROR.name()));
        }
        log.setErrorCategory(ImportStatus.ERROR);
        importErrors.add(log);
    }

    public static void addWarningToWarningList(ImportFailLog log, List<ImportFailLog> importWarnings) {
        if (log.getErrorCategory() != null && !ImportStatus.WARRNING.equals(log.getErrorCategory())) {
            LOGGER.info(String.format("Change import faillog status from %s to %s.", log.getErrorCategory().name(),
                    ImportStatus.WARRNING.name()));
        }
        log.setErrorCategory(ImportStatus.WARRNING);
        importWarnings.add(log);
    }

}
