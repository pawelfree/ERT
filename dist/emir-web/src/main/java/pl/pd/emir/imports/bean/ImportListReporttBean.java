package pl.pd.emir.imports.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.admin.ImportLogManager;
import pl.pd.emir.report.CsvExportService;
import pl.pd.emir.reports.model.ImportCsvReportWrapper;
import pl.pd.emir.reports.utils.PrintUtils;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

@SessionScoped
@ManagedBean(name = "importListReportBean")
public class ImportListReporttBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ImportListReporttBean.class.getName());
    @EJB
    private transient CsvExportService exportService;
    private static final long serialVersionUID = 13432l;
    @EJB
    private transient ImportLogManager importLogFacade;
    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();
    private transient ImportLog entityImportLog = new ImportLog();
    private transient List<ImportFailLog> entitiesImportFailLog = new ArrayList<>();
    private transient String importScopeInput;
    private transient String importStatus;

    public String getReportDataCsv() {
        exportService.clear();
        int startIndex = 0;
        List<ImportFailLog> importFailLogs;
        List<ImportCsvReportWrapper> importListWrapper;
        List<String> header = new ArrayList<>();
        header.add("Data zasilenia");
        header.add("Zakres danych");
        header.add("Kategoria nieprawidłowości");
        header.add("Stwierdzone nieprawidłowości");
        header.add("Rekord");
        exportService.addHeader(header);
        do {
            importFailLogs = importLogFacade.getImportFailLog(entityImportLog.getId(), startIndex, exportService.getPackageSize());
            LOG.log(Level.INFO, "getReportDataCSV getImportFailLog size:{0}", importFailLogs.size());
            importListWrapper = new ArrayList<>();
            for (ImportFailLog importFailLog : importFailLogs) {
                ImportCsvReportWrapper importWrapper = new ImportCsvReportWrapper();
                importWrapper.setImportDate(DateUtils.formatDate(entityImportLog.getImportDate(), DateUtils.DATE_TIME_FORMAT));
                importWrapper.setImportScope(entityImportLog.getImportScope() == null ? "" : translatefromBundle(entityImportLog.getImportScope().getMsgKey()));
                if (Objects.nonNull(importFailLog)) {
                    importWrapper.setErrorCategory(importFailLog.getErrorCategory().getMsgKey() == null ? "" : translatefromBundle(importFailLog.getErrorCategory().getMsgKey()));
                    importWrapper.setErrorDescription(importFailLog.getErrorDescription());
                    importWrapper.setErrorNr(PrintUtils.getRecordFromLine(importFailLog.getErrorDescription()));
                }
                importListWrapper.add(importWrapper);
            }
            exportService.generateCSV((List<Object>) (Object) importListWrapper);
            importListWrapper.clear();
            startIndex += exportService.getPackageSize();
        } while (importFailLogs.size() == exportService.getPackageSize());
        return exportService.getExportContent();
    }

    public String initReportDetails(Long id) {
        entityImportLog = importLogFacade.getById(id);
        entitiesImportFailLog = entityImportLog.getFailLogList();
        if (entityImportLog.getImportScope() != null) {
            this.importScopeInput = translatefromBundle(entityImportLog.getImportScope() == null ? "" : entityImportLog.getImportScope().getMsgKey());
        }
        if (entityImportLog.getImportStatus() != null) {
            this.importStatus = translatefromBundle(entityImportLog.getImportStatus() == null ? "" : entityImportLog.getImportStatus().getMsgKey());
        }
        return getAction();
    }

    public String getAction() {
        return "importListReport";
    }

    private String translatefromBundle(String key) {
        String ret;
        try {
            ret = BUNDLE.getString(key);
        } catch (MissingResourceException ex) {
            ret = "";
        }
        return ret;
    }

    public ImportLog getEntityImportLog() {
        return entityImportLog;
    }

    public void setEntityImportLog(ImportLog entityImportLog) {
        this.entityImportLog = entityImportLog;
    }

    public List<ImportFailLog> getEntitiesImportFailLog() {
        return entitiesImportFailLog;
    }

    public void setEntitiesImportFailLog(List<ImportFailLog> entitiesImportFailLog) {
        this.entitiesImportFailLog = entitiesImportFailLog;
    }

    public String closeListReport() {
        return "backToImportList";
    }

    public String getImportScopeInput() {
        return importScopeInput;
    }

    public void setImportScopeInput(String importScopeInput) {
        this.importScopeInput = importScopeInput;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
}
