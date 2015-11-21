package pl.pd.emir.processor;

import pl.pd.emir.imports.ImportOverview;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.parsers.BaseCsvParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for BANK_E files
 *
 * @author PawelDudek
 */
public class BankImportProcessor extends ImportProcessor implements IImportProcessor {

    static final Logger LOGGER = LoggerFactory.getLogger(BankImportProcessor.class);

    @Override
    public void process(Reader reader, BaseCsvParser parser,
            String fileName, Date importFileDate, ImportLog importLog, boolean backloading,
            ProcessingWarnings warnings, ImportOverview overview) throws IOException {
        parser.setRowNum(0);
        CSVReader<ImportResult<Bank>> csvBankReader = new CSVReaderBuilder<ImportResult<Bank>>(reader).entryParser(parser).build();
        List<Bank> batch = new ArrayList<>();
        ImportResult<Bank> bankRow;
        boolean atLeastOne = false;
        while ((bankRow = csvBankReader.readNext()) != null) {
            atLeastOne = true;
            if (bankRow.hasErrors()) {
                LOGGER.info("Import institution data failed.");
                importLog.processFailLogs(bankRow.getImportErrors(), bankRow.getImportWarnings());
                continue;
            }
            Bank bank = bankRow.getExtract();
            bank.setFileName(fileName);
            bank.setImportLog(importLog);
            importLog.processFailLogs(bankRow.getImportErrors(), bankRow.getImportWarnings());
            batch.add(bank);
            LOGGER.info("Add new institution data to save.");
            if (batch.size() == BATCH_SIZE) {
                bankManager.saveBankList(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            bankManager.saveBankList(batch);
        }

        if (!atLeastOne) {
            ImportFailLog log = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(ImportFaillogUtils.ImportFaillogKey.EMPTY_FILE));
            importLog.getFailLogList().add(log);
        }
    }
}
