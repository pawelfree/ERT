package pl.pd.emir.processor;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.utils.BusinessValidationUtils;
import pl.pd.emir.entity.utils.ReflectionValidationUtils;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.ClearingOblig;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for TRANSACTION_E files
 *
 * @author PawelDudek
 */
public class TransactionImportProcessor extends ImportProcessor implements IImportProcessor {

    static final Logger LOGGER = LoggerFactory.getLogger(TransactionImportProcessor.class);

    TransactionManager transactionManager;
    ClientManager clientManager;

    public TransactionImportProcessor() {
        try {
            InitialContext ic = new InitialContext();
            transactionManager = (TransactionManager) ic.lookup("java:global/emir/ejb-impl-1.0-SNAPSHOT/TransactionManager");
            clientManager = (ClientManager) ic.lookup("java:global/emir/ejb-impl-1.0-SNAPSHOT/ClientManager");

        } catch (NamingException ex) {
            LOGGER.error("Managers (client and or transaction) can't be found", ex);
        }
    }

    @Override
    public void process(Reader reader, BaseCsvParser parser, String fileName, Date importFileDate,
            ImportLog importLog, boolean backloading,
            ProcessingWarnings warnings, ImportOverview overview, Set customersToRemoveFromImport, Set transactionsToRemoveFromImport) throws IOException {        
        parser.setRowNum(0);
        CSVReader<ImportResult<Transaction>> csvTransactionReader = new CSVReaderBuilder<ImportResult<Transaction>>(reader).entryParser(parser).build();
        List<Transaction> batch = new ArrayList<>();
        ImportResult<Transaction> transactionRow;
        boolean atLeastOne = false;
        //Wczytanie ekstraktu
        while ((transactionRow = csvTransactionReader.readNext()) != null) {
            atLeastOne = true;
            if (transactionRow.getExtract() != null) {
                validateTransactionDate(transactionRow, transactionRow.getExtract().getTransactionDate(), importFileDate);
            }
            if (transactionRow.hasErrors()) {
                LOGGER.info("Import transaction failed.");
                importLog.processFailLogs(transactionRow.getImportErrors(), transactionRow.getImportWarnings());
                continue;
            }
            Transaction transaction = transactionRow.getExtract();

            transaction.getTransactionClearing().setCleared(Cleared.N);
            transaction.getTransactionClearing().setClearingOblig(ClearingOblig.N);
            transaction.getTransactionClearing().setIntergropuTrans(IntergropuTrans.N);

            Client client = clientManager.getClientByOrginalId(transaction.getOriginalClientId());
            if (client == null) {
                LOGGER.warn(String.format("Can't find client with id %s. Transaction incomplete.", transaction.getOriginalClientId()));
                transactionRow.addWarning(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.CLIENT_DOES_NOT_EXIST, transaction.getOriginalClientId(),
                        transactionRow.getRecordId())));
                transaction.setValidationStatus(ValidationStatus.INCOMPLETE);
            } else {
                if (client.getIntraGroupTransactions()) {
                    transaction.getTransactionClearing().setIntergropuTrans(IntergropuTrans.Y);
                }
            }
            transaction.setClient(client);

            //TODO PAWEL to chyba powinno byc full clone
            Client client2 = clientManager.getClientByOrginalId(transaction.getOriginalClientId2());       
            if (client2 == null) {
                LOGGER.warn(String.format("Can't find client2 with id %s. Transaction incomplete.", transaction.getOriginalClientId2()));
                transactionRow.addWarning(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.CLIENT2_DOES_NOT_EXIST, transaction.getOriginalClientId(),
                        transactionRow.getRecordId())));
                transaction.setValidationStatus(ValidationStatus.INCOMPLETE);
            } 

            //TODO PAWEL jak zrobic intragroup transactions w przypadku GUR???
            transaction.setClient2(client2);
            
            BusinessValidationUtils.validateBankBeneficiaryCode(transaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            BusinessValidationUtils.validateBankTransactionType(transaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            BusinessValidationUtils.validateClientBeneficiaryCode(transaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            BusinessValidationUtils.validateRealizationValue(transaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            BusinessValidationUtils.validateFrameworkAggrVer(transaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            if (!backloading) {
                Transaction prevTrans = transactionManager.findNewestTransaction(transaction.getTransactionDetails().getSourceTransId(), DateUtils.getPreviousWorkingDay(transaction.getTransactionDate(), DateUtils.getFreeDaysAll()), transaction.getTransactionDate());
                BusinessValidationUtils.validateErrorCategory(transaction, prevTrans, transactionRow.getImportWarnings(), transactionRow.getRecordId());
            }
            transaction.setValidationStatus(transactionRow.getValidationStatus());
            if (ValidationStatus.INCOMPLETE.equals(transaction.getValidationStatus())) {
                warnings.setFlagWarningPro(true);
                warnings.setFlagWarningVal(true);
            }
            transaction.setFileName(fileName);
            transaction.setImportLog(importLog);
            transaction.setBackloading(backloading);
            transaction.setValidationStatus(validateTransactionCompletness(transaction));
            if (customersToRemoveFromImport.contains(client.getOriginalId())) {
                System.out.println("!!! --- client id " + client.getOriginalId());
                System.out.println("!!! --- transa id " + transaction.getOriginalId());
                transactionsToRemoveFromImport.add(transaction.getOriginalId());
                LOGGER.debug(String.format("Transaction with id %s on date %s to save removed from import.",
                        transaction.getOriginalId(),
                        DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT)));                
            } else {
                //Walidacje biznesowe nie wplywajace na status kompletnosci transakcji
                //TODO co to robi i po co
                Transaction previousTransaction = transactionManager.getNewestVersion(transaction.getTransactionDate(), transaction.getOriginalId());
                BusinessValidationUtils.validateExtractVersion(transaction, previousTransaction, transactionRow.getImportWarnings(), transactionRow.getRecordId());
                importLog.processFailLogs(transactionRow.getImportErrors(), transactionRow.getImportWarnings());
                batch.add(transaction);
                LOGGER.debug(String.format("Add new transaction with id %s on date %s to save.",
                        transaction.getOriginalId(),
                        DateUtils.formatDate(transaction.getTransactionDate(), DateUtils.DATE_FORMAT)));
            }
            if (batch.size() == BATCH_SIZE) {
                transactionManager.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            transactionManager.saveAll(batch);
        }
        if (!atLeastOne) {
            ImportFailLog log = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(ImportFaillogUtils.ImportFaillogKey.EMPTY_FILE));
            importLog.getFailLogList().add(log);
        }
        overview.setItemsInTransaction(parser.getRowNum());
    }

    protected ValidationStatus validateTransactionCompletness(Transaction transaction) {
        if (ValidationStatus.VALID.equals(transaction.getValidationStatus())) {
            boolean isComplete = ReflectionValidationUtils.isComplete(transaction);
            if (isComplete) {
                return ValidationStatus.VALID;
            } else {
                return ValidationStatus.INCOMPLETE;
            }
        } else {
            return transaction.getValidationStatus();
        }
    }
}
