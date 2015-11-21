package pl.pd.emir.imports;

import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.imports.ImportCsvManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.pd.emir.admin.ImportLogManager;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.admin.TransactionTemplateManager;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.exceptions.FileUtilsException;
import pl.pd.emir.parsers.BankCsvParser;
import pl.pd.emir.parsers.BaseCsvParser;
import pl.pd.emir.parsers.ClientCsvParser;
import pl.pd.emir.parsers.ProtectionParser;
import pl.pd.emir.parsers.TransactionCsvParserTmb;
import pl.pd.emir.parsers.ValuationParser;
import pl.pd.emir.processor.BankImportProcessor;
import pl.pd.emir.processor.ClientImportProcessor;
import pl.pd.emir.processor.IImportProcessor;
import pl.pd.emir.processor.ProcessingWarnings;
import pl.pd.emir.processor.ProtectionImportProcessor;
import pl.pd.emir.processor.TransactionImportProcessor;
import pl.pd.emir.processor.ValuationImportProcessor;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.resources.ResourceMask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Local(ImportCsvManager.class)
public class ImportCsvManagerImpl implements ImportCsvManager {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ImportCsvManagerImpl.class);

    protected static final int BATCH_SIZE = 100;

    @EJB
    private ParameterManager parameterManager;

    @EJB
    private ImportLogManager importLogManager;

    @EJB
    private UserManager userManager;

    @EJB
    private ClientManager clientManager;

    @EJB
    private TransactionTemplateManager transactionTemplateManager;

    @EJB
    TransactionManager transactionManager;

    protected Map<ImportScope, IImportProcessor> processors = new EnumMap<>(ImportScope.class);

    protected Map<ImportScope, BaseCsvParser> parsers = new EnumMap<>(ImportScope.class);

    private boolean caseSensitive;
    private boolean backloading;
    private Date importFileDate;
    private Path inputDirectory;
    private ProcessingWarnings warnings;
    private ImportOverview overview;

    public ImportCsvManagerImpl() {
        processors.put(ImportScope.BANK_E, new BankImportProcessor());
        processors.put(ImportScope.CLIENT_E, new ClientImportProcessor());
        processors.put(ImportScope.TRANSACTION_E, new TransactionImportProcessor());
        processors.put(ImportScope.PROTECTION_E, new ProtectionImportProcessor());
        processors.put(ImportScope.VALUATION_E, new ValuationImportProcessor());
    }

    protected void initParsers() {
        parsers.put(ImportScope.BANK_E, new BankCsvParser());
        parsers.put(ImportScope.CLIENT_E, new ClientCsvParser());
        parsers.put(ImportScope.TRANSACTION_E, new TransactionCsvParserTmb(transactionTemplateManager.getById(1L), clientManager));
        parsers.put(ImportScope.PROTECTION_E, new ProtectionParser(transactionTemplateManager.getById(1L)));
        parsers.put(ImportScope.VALUATION_E, new ValuationParser(transactionTemplateManager.getById(1L)));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public ImportOverview importCsv(List<ImportScope> importScope, Date importFileDate, boolean backloading) {
        inputDirectory = Paths.get(parameterManager.getValue(ParameterKey.IMPORT_INPUT_URI));
        LOGGER.info(String.format("Import CSV from %s", inputDirectory.toString()));

        caseSensitive = getCaseSensitiveFlag();
        this.backloading = backloading;
        this.importFileDate = importFileDate;

        initParsers();

        warnings = new ProcessingWarnings();
        overview = new ImportOverview();

        //kolejność musi zostać
        if (importScope.contains(ImportScope.BANK_E)) {
            importFiles(ImportScope.BANK_E, new ResourceMask(parameterManager.getValue(ParameterKey.IMPORT_INPUT_MASK_BANK)));
        }
        if (importScope.contains(ImportScope.CLIENT_E)) {
            importFiles(ImportScope.CLIENT_E, new ResourceMask(parameterManager.getValue(ParameterKey.IMPORT_INPUT_MASK_CLIENT)));
        }
        if (importScope.contains(ImportScope.TRANSACTION_E)) {
            importFiles(ImportScope.TRANSACTION_E, new ResourceMask(parameterManager.getValue(ParameterKey.IMPORT_INPUT_MASK_TRANSACTION)));
        }
        if (importScope.contains(ImportScope.PROTECTION_E)) {
            importFiles(ImportScope.PROTECTION_E, new ResourceMask(parameterManager.getValue(ParameterKey.IMPORT_INPUT_MASK_PROTECTION)));
        }
        if (importScope.contains(ImportScope.VALUATION_E)) {
            importFiles(ImportScope.VALUATION_E, new ResourceMask(parameterManager.getValue(ParameterKey.IMPORT_INPUT_MASK_VALUATION)));
        }

        overview.setExistingTransactions(transactionManager.getTransactionsCountForADay(importFileDate));
        overview.setNewTransactions(transactionManager.getNewTransactionsCountForADay(importFileDate));
        overview.setExpiredTransactions(transactionManager.getMaturedTransactionsCountForADay(importFileDate));

        return overview;
    }

    private boolean getCaseSensitiveFlag() {
        return "true".contains(parameterManager.getValue(ParameterKey.IMPORT_INPUT_CASE_SENSITIVE).toLowerCase());
    }

    private List<Path> list(ResourceMask dataResourceMask, boolean caseSensitive) {
        List<Path> result = new ArrayList<>();
        if (Files.isDirectory(inputDirectory)) {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(inputDirectory)) {
                for (Path file : files) {
                    if (dataResourceMask.matches(file, caseSensitive)) {
                        result.add(file);
                    }
                }
            } catch (IOException ex) {
                LOGGER.error("Error while listing files from directory {}", inputDirectory.toString(), ex);
                throw new FileUtilsException(ex);
            }
        } else if (dataResourceMask.matches(inputDirectory, caseSensitive)) {
            result.add(inputDirectory);
        }
        return result;
    }

    private List<Path> getFiles(ResourceMask mask) {
        return list(mask, caseSensitive)
                .stream()
                .filter((file) -> (mask.checkAccordingToDate(file, importFileDate)))
                .collect(Collectors.toList());
    }

    private void importFiles(ImportScope extractType, ResourceMask mask) {

        LOGGER.info(String.format("Import files for %s mask", mask));
        List<Path> resourceList = getFiles(mask);

        if (resourceList.isEmpty()) {
            LOGGER.warn(String.format("Files for %s mask and %s date doesn't exist.", mask, DateUtils.formatDate(importFileDate, DateUtils.DATE_FORMAT)));
            ImportFailLog failLog = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(
                    ImportFaillogUtils.ImportFaillogKey.FILE_DOESNT_EXIST, DateUtils.formatDate(importFileDate, DateUtils.DATE_FORMAT)));
            ImportLog failImportLog = getFailImportLog(failLog, extractType);
            importLogManager.save(failImportLog);
        }

        for (Path resource : resourceList) {
            try (Reader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(resource), "UTF-8"));) {
                String fileName = resource.getFileName().toString();
                try {
                    String userLogin = userManager.getCurrentUserLogin();
                    ImportLog importLog = new ImportLog(new Date(), userLogin, extractType, importFileDate, ImportStatus.DURING_PROCESSING, null);
                    importLog.setFileName(fileName);
                    importLog = importLogManager.save(importLog);
                    importExtract(reader, extractType, fileName, importLog);
                    importLog.determineStatus();
                    importLogManager.save(importLog);
                } catch (IOException ex) {
                    ImportFailLog failLog = new ImportFailLog(ImportStatus.ERROR, ImportFaillogUtils.getString(
                            ImportFaillogUtils.ImportFaillogKey.IO_EXCEPION, fileName));
                    ImportLog failImportLog = getFailImportLog(failLog, extractType);
                    failImportLog.setFileName(fileName);
                    importLogManager.save(failImportLog);
                    LOGGER.error(String.format("Import failed for extract %s.", fileName), ex);
                }
            } catch (IOException ex) {
                LOGGER.error(String.format("Error while opening input stream to resource %s: ", resource.toString()), ex);
            }
        }
    }

    private void importExtract(Reader reader, ImportScope extractType, String fileName, ImportLog importLog) throws IOException {
        BaseCsvParser parser = parsers.get(extractType);
        processors.get(extractType).process(reader, parser, fileName, importFileDate, importLog, backloading, warnings, overview);
    }

    private ImportLog getFailImportLog(ImportFailLog failLog, ImportScope extractType) {
        List<ImportFailLog> failLogs = new ArrayList<>();
        failLogs.add(failLog);
        ImportLog log = new ImportLog(new Date(), userManager.getCurrentUserLogin(), extractType, importFileDate,
                ImportStatus.ERROR, failLogs);
        return log;
    }
}
