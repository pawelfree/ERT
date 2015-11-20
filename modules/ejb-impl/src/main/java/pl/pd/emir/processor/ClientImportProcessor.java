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
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.imports.ImportFaillogUtils;
import pl.pd.emir.imports.ImportResult;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.register.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor for CLIENT_E files
 *
 * @author PawelDudek
 */
public class ClientImportProcessor extends ImportProcessor implements IImportProcessor {

    static final Logger LOGGER = LoggerFactory.getLogger(ClientImportProcessor.class);

    private ClientManager clientManager;

    public ClientImportProcessor() {
        try {
            InitialContext ic = new InitialContext();
            clientManager = (ClientManager) ic.lookup("java:global/emir/ejb-impl-1.0-SNAPSHOT/ClientManager");

        } catch (NamingException ex) {
            LOGGER.error("Managers can't be found", ex);
        }
    }

    @Override
    public void process(Reader reader, BaseCsvParser parser, String fileName, Date importFileDate,
            ImportLog importLog, boolean backloading,
            ProcessingWarnings warnings, ImportOverview overview) throws IOException {
        parser.setRowNum(0);
        CSVReader<ImportResult<Client>> csvClientReader = new CSVReaderBuilder<ImportResult<Client>>(reader).entryParser(parser).build();
        List<Client> batch = new ArrayList<>();
        ImportResult<Client> clientRow;
        boolean atLeastOne = false;
        while ((clientRow = csvClientReader.readNext()) != null) {
            atLeastOne = true;
            if (clientRow.hasErrors()) {
                LOGGER.info("Import client failed.");
                importLog.processFailLogs(clientRow.getImportErrors(), clientRow.getImportWarnings());
                continue;
            }
            Client client = clientRow.getExtract();
            client.setValidationStatus(clientRow.getValidationStatus());
            client.setFileName(fileName);
            client.setImportLog(importLog);
            importLog.processFailLogs(clientRow.getImportErrors(), clientRow.getImportWarnings());
            batch.add(client);
            LOGGER.info(String.format("Add new client with id %s to save.", client.getOriginalId()));
            if (batch.size() == BATCH_SIZE) {
                clientManager.saveAllByOrginalId(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            clientManager.saveAllByOrginalId(batch);
        }
        if (!atLeastOne) {
            ImportFailLog log = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(ImportFaillogUtils.ImportFaillogKey.EMPTY_FILE));
            importLog.getFailLogList().add(log);
        }
    }
}
