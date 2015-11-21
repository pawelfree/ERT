package pl.pd.emir.processor;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.entity.utils.BusinessValidationUtils;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.register.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author PawelDudek
 */
public class ValuationImportProcessor extends ImportProcessor implements IImportProcessor {

    static final Logger LOGGER = LoggerFactory.getLogger(ProtectionImportProcessor.class);

    @EJB
    protected TransactionManager transactionManager;

    public ValuationImportProcessor() {
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
        //Wyzerowanie licznika wierszy w parserze
        parser.setRowNum(0);

        //Utworzenie obiektu parsującego
        CSVReader<ImportResult<Valuation>> csvValuationReader = new CSVReaderBuilder<ImportResult<Valuation>>(reader).entryParser(parser).build();
        //Utworzenie bufora zapisu (paczkowanie)
        List<Transaction> batch = new ArrayList<>();

        //Rezultat importu pojedynczego rekordu
        ImportResult<Valuation> valuationRow;
        //Flaga informująca czy w pliku znajduje się przynajmniej jeden rekord
        boolean atLeastOne = false;
        //Wczytanie ekstraktu
        while ((valuationRow = csvValuationReader.readNext()) != null) {

            atLeastOne = true;

            //Walidacja zgodności daty transakcji z datą całego ekstraktu
            if (valuationRow.getExtract() != null) {
                validateTransactionDate(valuationRow, valuationRow.getExtract().getTransactionDate(), importFileDate);
            }

            //Pominięcie błędnego rekordu
            if (valuationRow.hasErrors()) {
                LOGGER.info("Import valuation failed.");
                //Zapisanie błędów i ostrzeżeń w logu
                importLog.processFailLogs(valuationRow.getImportErrors(), valuationRow.getImportWarnings());
                continue;
            }
            Valuation valuation = valuationRow.getExtract();

            //Zapamiętanie nazwy pliku ekstraktu
            valuation.setFileName(fileName);
            valuation.setImportLog(importLog);

            //Znalezienie transakcji odpowiadających wczytywanej wycenie
            List<Transaction> transactions = transactionManager.getByDateAndOriginalId(valuation.getOriginalId(), valuation.getTransactionDate());
            //Wybranie najnowszej transakcji
            Transaction transaction = getExtractByVersion(transactions);

            if (transaction == null) {
                //W przypadku braku transakcji o wskazanym id i dacie, logowana jest pojedyncza informacja o
                //błędzie a wycena nie jest zapisywana
                LOGGER.warn(String.format("Import valuation with id %s and date %s failed - transaction doesn't exist.",
                        valuation.getOriginalId(),
                        DateUtils.formatDate(valuation.getTransactionDate(), DateUtils.DATE_FORMAT)));
                valuationRow.clear();
                valuationRow.addError(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.TRANSACTION_DOES_NOT_EXIST,
                        valuation.getOriginalId(),
                        DateUtils.formatDate(valuation.getTransactionDate(), DateUtils.DATE_FORMAT))));

                //Zapisanie błędów i ostrzeżeń w logu
                importLog.processFailLogs(valuationRow.getImportErrors(), valuationRow.getImportWarnings());

            } else if (transaction.getValuation() != null && !ProcessingStatus.NEW.equals(transaction.getProcessingStatus())) {
                //W przypadku gdy znaleziona transakcja posiada już przypisaną wycenę oraz status inny
                //niż nowa logowana jest pojedyncza informacja o błędzie a nowa wycena nie jest zapisywana
                LOGGER.warn(String.format("Import valuation with id %s and date %s failed - transaction already has valuation.",
                        valuation.getOriginalId(),
                        DateUtils.formatDate(valuation.getTransactionDate(), DateUtils.DATE_FORMAT)));
                valuationRow.clear();
                valuationRow.addError(new ImportFailLog(ImportFaillogUtils.getString(
                        ImportFaillogUtils.ImportFaillogKey.TRANSACTION_ALREADY_HAS_VALUATION,
                        valuation.getOriginalId(),
                        DateUtils.formatDate(valuation.getTransactionDate(), DateUtils.DATE_FORMAT))));

                //Zapisanie błędów i ostrzeżeń w logu
                importLog.processFailLogs(valuationRow.getImportErrors(), valuationRow.getImportWarnings());

            } else {
                //przypisanie wyceny do transakcji i zapisanie
                LOGGER.debug(String.format("Add valuation with id %s and date %s to save.",
                        valuation.getOriginalId(),
                        DateUtils.formatDate(valuation.getTransactionDate(), DateUtils.DATE_FORMAT)));
                transaction.setValuation(valuation);

                //Weryfikacja kompletności transakcji zależnie od flagi valuationReporting w danych instytucji
                //raportującej
                transaction.setValidationStatus(validateValuationCompletness(transaction, warnings));
                //Zapisanie błędów i ostrzeżeń w logu
                importLog.processFailLogs(valuationRow.getImportErrors(), valuationRow.getImportWarnings());
                //Dodanie encji do bufora zapisu
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
        overview.setItemsInValuation(parser.getRowNum());
    }

    private ValidationStatus validateValuationCompletness(Transaction transaction, ProcessingWarnings warnings) {
        boolean isComplete;
        if (getValuationReporting(transaction.getTransactionDate())) {
            isComplete = BusinessValidationUtils.isValuationComplete(transaction, getValuationReporting(transaction.getTransactionDate()))
                    && BusinessValidationUtils.isProtectionComplete(transaction, getValuationReporting(transaction.getTransactionDate()))
                    && !warnings.isFlagWarningVal();
            warnings.setFlagWarningVal(false);
        } else if (ValidationStatus.VALID.equals(transaction.getValidationStatus())) {
            isComplete = BusinessValidationUtils.isValuationComplete(transaction, getValuationReporting(transaction.getTransactionDate()));
        } else {
            return transaction.getValidationStatus();
        }
        if (isComplete) {
            return ValidationStatus.VALID;
        } else {
            return ValidationStatus.INCOMPLETE;
        }
    }

}
