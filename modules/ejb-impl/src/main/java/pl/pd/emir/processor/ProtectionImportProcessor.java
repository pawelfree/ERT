package pl.pd.emir.processor;

import pl.pd.emir.imports.ImportOverview;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.utils.BusinessValidationUtils;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.register.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author PawelDudek
 */
public class ProtectionImportProcessor extends ImportProcessor implements IImportProcessor {

    static final Logger LOGGER = LoggerFactory.getLogger(ProtectionImportProcessor.class);

    private TransactionManager transactionManager;

    public ProtectionImportProcessor() {
        try {
            InitialContext ic = new InitialContext();
            transactionManager = (TransactionManager) ic.lookup("java:global/emir/ejb-impl-1.0-SNAPSHOT/TransactionManagerImpl");

        } catch (NamingException ex) {
            LOGGER.error("Managers can't be found", ex);
        }
    }

    @Override
    public void process(Reader reader, BaseCsvParser parser, String fileName, Date importFileDate,
            ImportLog importLog, boolean backloading,
            ProcessingWarnings warnings, ImportOverview overview) throws IOException {
        parser.setRowNum(0);
        CSVReader<ImportResult<Protection>> csvProtectionReader = new CSVReaderBuilder<ImportResult<Protection>>(reader).entryParser(parser).build();
        List<Transaction> batch = new ArrayList<>();
        ImportResult<Protection> protectionRow;
        boolean atLeastOne = false;
        while ((protectionRow = csvProtectionReader.readNext()) != null) {
            atLeastOne = true;
            if (protectionRow.getExtract() != null) {
                validateTransactionDate(protectionRow, protectionRow.getExtract().getTransactionDate(), importFileDate);
            }
            if (protectionRow.hasErrors()) {
                LOGGER.info("Import protection failed.");
                importLog.processFailLogs(protectionRow.getImportErrors(), protectionRow.getImportWarnings());
                continue;
            }
            Protection protection = protectionRow.getExtract();
            protection.setFileName(fileName);
            protection.setImportLog(importLog);
            List<Transaction> transactions = transactionManager.getByDateAndOriginalId(protection.getOriginalId(), protection.getTransactionDate());
            Transaction transaction = getExtractByVersion(transactions);
            if (transaction == null) {
                LOGGER.warn(String.format("Import protection with id %s and date %s failed - transaction doesn't exist.",
                        protection.getOriginalId(),
                        DateUtils.formatDate(protection.getTransactionDate(), DateUtils.DATE_FORMAT)));
                protectionRow.clear();
                protectionRow.addError(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.TRANSACTION_DOES_NOT_EXIST,
                        protection.getOriginalId(),
                        DateUtils.formatDate(protection.getTransactionDate(), DateUtils.DATE_FORMAT))));
                importLog.processFailLogs(protectionRow.getImportErrors(), protectionRow.getImportWarnings());
            } else if (transaction.getProtection() != null && !ProcessingStatus.NEW.equals(transaction.getProcessingStatus())) {
                LOGGER.warn(String.format("Import protection with id %s and date %s failed - transaction allready has protection.",
                        protection.getOriginalId(),
                        DateUtils.formatDate(protection.getTransactionDate(), DateUtils.DATE_FORMAT)));
                protectionRow.clear();
                protectionRow.addError(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.TRANSACTION_ALREADY_HAS_PROTECTION,
                        protection.getOriginalId(),
                        DateUtils.formatDate(protection.getTransactionDate(), DateUtils.DATE_FORMAT))));
                importLog.processFailLogs(protectionRow.getImportErrors(), protectionRow.getImportWarnings());

            } else {
                LOGGER.debug(String.format("Add protection with id %s and date %s to save.",
                        protection.getOriginalId(),
                        DateUtils.formatDate(protection.getTransactionDate(), DateUtils.DATE_FORMAT)));
                transaction.setProtection(protection);
                transaction.setValidationStatus(validateProtectionCompletness(transaction, warnings));
                importLog.processFailLogs(protectionRow.getImportErrors(), protectionRow.getImportWarnings());
                batch.add(transaction);

                if (batch.size() == BATCH_SIZE) {
                    transactionManager.saveAll(batch);
                    batch.clear();
                }
            }
        }

        if (!batch.isEmpty()) {
            transactionManager.saveAll(batch);
        }

        if (!atLeastOne) {
            ImportFailLog log = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(ImportFaillogUtils.ImportFaillogKey.EMPTY_FILE));
            importLog.getFailLogList().add(log);
        }
        overview.setItemsInProtection(parser.getRowNum());
    }

    private ValidationStatus validateProtectionCompletness(Transaction transaction, ProcessingWarnings warnings) {
        boolean isComplete;
        if (getValuationReporting(transaction.getTransactionDate())) {
            isComplete = BusinessValidationUtils.isProtectionComplete(transaction, getValuationReporting(transaction.getTransactionDate()))
                    && BusinessValidationUtils.isValuationComplete(transaction, getValuationReporting(transaction.getTransactionDate()))
                    && !warnings.isFlagWarningPro();
            warnings.setFlagWarningPro(false);
        } else {
            if (ValidationStatus.VALID.equals(transaction.getValidationStatus())) {
                isComplete = BusinessValidationUtils.isProtectionComplete(transaction, getValuationReporting(transaction.getTransactionDate()));
            } else {
                return transaction.getValidationStatus();
            }
        }
        if (isComplete) {
            return ValidationStatus.VALID;
        } else {
            return ValidationStatus.INCOMPLETE;
        }
    }

}
