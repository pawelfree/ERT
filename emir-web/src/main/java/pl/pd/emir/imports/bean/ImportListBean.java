package pl.pd.emir.imports.bean;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.admin.ImportLogManager;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.criteria.ImportLogSC;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ImportStatus;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ImportListWrapper;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.ReportData;
import pl.pd.emir.resources.MultipleFilesResourceBundle;
import org.primefaces.model.SortOrder;

@SessionScoped
@ManagedBean(name = "importListBean")
public class ImportListBean extends AbstractListBean<ImportLog, ImportLogManager, ImportLogSC> {

    private static final long serialVersionUID = 1l;
    private ImportLog importLog;
    @EJB
    private transient ImportLogManager importLogFacade;
    private final transient ReportData<ImportListWrapper> reportData = new ReportData<>();
    private final transient ReportType reportType = ReportType.IMPORT_LIST;
    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();
    private final String DEFAULT_SORT_FIELD = "importDate";

    public ReportType getReportType() {
        return reportType;
    }

    public ReportData getReportData() {
        Collection<ImportListWrapper> data = importLogFacade
                .findAll(criteria)
                .stream()
                .map((dateLog) -> {
                    ImportListWrapper wrapper = new ImportListWrapper();
                    wrapper.setImportDate(dateLog.getImportDate());
                    wrapper.setImportDateSupply(dateLog.getImportDate());
                    if (dateLog.getImportScope() != null) {
                        wrapper.setImportScopeMsgKey(dateLog.getImportScope().getMsgKey() == null ? "" : BUNDLE.getString(dateLog.getImportScope().getMsgKey()));
                    }
                    if (dateLog.getImportStatus() != null) {
                        wrapper.setImportStatusMsgKey(dateLog.getImportStatus().getMsgKey() == null ? "" : BUNDLE.getString(dateLog.getImportStatus().getMsgKey()));
                    }
                    wrapper.setImportUser(dateLog.getImportUser() == null ? "" : dateLog.getImportUser());
                    return wrapper;
                })
                .collect(Collectors.toList());

        ParametersWrapper parameters = new ParametersWrapper("c.dataTimeSupply", "c.author", "c.rangeData", "c.dateData", "c.status");
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    public ImportListBean() {
        super(ImportLogSC.class);
    }

    @Override
    public ImportLogManager getService() {
        return importLogFacade;
    }

    /**
     * Inicjalizacja kryteriów wyszukiwania wykonywana w czasie inicjalizacji bean widoku.
     */
    @Override
    public void initSearchCriteria() {
        initCriteria();
    }

    /**
     * Inicjalizacja kryteriów wyszukiwania wykonywana po wyczyszczeniu.
     */
    @Override
    public void initSearchCriteriaAfterClean() {
        initCriteria();
    }

    @Override
    public String getAction() {
        return "importList";
    }

    private void initCriteria() {
        setSortField(DEFAULT_SORT_FIELD);
        setSortOrder(SortOrder.DESCENDING);
        criteria.setImportDateFrom(DateUtils.getDayBegin(new Date()));
        criteria.setImportDateTo(DateUtils.getDayEnd(new Date()));
    }

    public ImportStatus[] getImportStatus() {
        return ImportStatus.values();
    }

    public ImportScope[] getImportScope() {
        ImportScope[] importScope = {ImportScope.CLIENT_E,
            ImportScope.PROTECTION_E,
            ImportScope.TRANSACTION_E,
            ImportScope.VALUATION_E};
        return importScope;
    }

    public ImportLog getImportLog() {
        return importLog;
    }

    public void setImportLog(ImportLog importLog) {
        this.importLog = importLog;
    }
}
