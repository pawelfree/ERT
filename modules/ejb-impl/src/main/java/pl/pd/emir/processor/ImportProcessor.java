package pl.pd.emir.processor;

import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.Extract;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author PawelDudek
 */
public abstract class ImportProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionImportProcessor.class);

    final int BATCH_SIZE = 100;

    protected <E extends Extract> boolean validateTransactionDate(ImportResult<E> row, Date transactionDate, Date importFileDate) {
        if (transactionDate == null || !DateUtils.getDayBegin(importFileDate).equals(DateUtils.getDayBegin(transactionDate))) {
            row.addError(new ImportFailLog(ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.INCOMPATIBILE_TRANSACTION_DATE,
                    DateUtils.formatDate(transactionDate, DateUtils.DATE_FORMAT))));
            return false;
        }
        return true;
    }

    <E extends Extract> E getExtractByVersion(List<E> extracts) {
        E result = null;
        Integer version = 0;
        for (E extract : extracts) {
            if (extract.getExtractVersion() > version) {
                version = extract.getExtractVersion();
                result = extract;
            }
        }
        return result;
    }

}
