package pl.pd.emir.imports.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.imports.ImportCsvManager;

@ViewScoped
@ManagedBean(name = "importBean")
public class ImportBean implements Serializable {

    @EJB
    private transient ImportCsvManager importCsvManager;

    @EJB
    private ParameterManager parameterManager;
    @ManagedProperty(value = "#{importListBean}")
    private transient ImportListBean importListBean;

    private List<ImportScope> transactionSelectedScope;
    private List<ImportScope> transactionDisplayedScope;
    private Date extractDate;
    private boolean backloading;
    private boolean backloadingOptionShown;

    private UIInput clientScopeInput;
    private UIInput transactionScopeInput;

    private ImportOverview importOverview;

    public ImportOverview getImportOverview() {
        return importOverview;
    }

    @PostConstruct
    public void init() {
        transactionSelectedScope = new ArrayList<>();
        transactionSelectedScope.add(ImportScope.VALUATION_E);
        transactionSelectedScope.add(ImportScope.PROTECTION_E);
        transactionSelectedScope.add(ImportScope.TRANSACTION_E);
        transactionDisplayedScope = new ArrayList<>();
        transactionDisplayedScope.add(ImportScope.VALUATION_E);
        transactionDisplayedScope.add(ImportScope.PROTECTION_E);
        transactionDisplayedScope.add(ImportScope.TRANSACTION_E);
        extractDate = DateUtils.getDayBegin(DateUtils.getPreviousWorkingDayWithFreeDays(new Date()));
        backloading = false;
        backloadingOptionShown = isBackloadingEnabled();
        importOverview = new ImportOverview();
    }

    public void importExtract() {
        List<ImportScope> allSelected = new ArrayList<>();
        allSelected.addAll(transactionSelectedScope);
        importOverview = importCsvManager.importCsv(allSelected, DateUtils.getDayBegin(extractDate), backloading);

    }

    public String finishImport() {
        return importListBean.init();
    }

    public ImportListBean getImportListBean() {
        return importListBean;
    }

    public void setImportListBean(ImportListBean importListBean) {
        this.importListBean = importListBean;
    }

    public List<ImportScope> getTransactionSelectedScope() {
        return transactionSelectedScope;
    }

    public void setTransactionSelectedScope(List<ImportScope> transactionSelectedScope) {
        this.transactionSelectedScope = transactionSelectedScope;
    }

    public List<ImportScope> getTransactionDisplayScope() {
        return transactionDisplayedScope;
    }

    public void setTransactionDisplayScope(List<ImportScope> transactionDisplayScope) {
        this.transactionDisplayedScope = transactionDisplayScope;
    }

    public Date getExtractDate() {
        return extractDate;
    }

    public void setExtractDate(Date extractDate) {
        this.extractDate = extractDate;
    }

    public boolean isBackloading() {
        return backloading;
    }

    public void setBackloading(boolean backloading) {
        this.backloading = backloading;
    }

    public boolean isBackloadingOptionShown() {
        return backloadingOptionShown;
    }

    public void setBackloadingOptionShown(boolean backloadingOptionShown) {
        this.backloadingOptionShown = backloadingOptionShown;
    }

    public UIInput getClientScopeInput() {
        return clientScopeInput;
    }

    public void setClientScopeInput(UIInput clientScopeInput) {
        this.clientScopeInput = clientScopeInput;
    }

    public UIInput getTransactionScopeInput() {
        return transactionScopeInput;
    }

    public void setTransactionScopeInput(UIInput transactionScopeInput) {
        this.transactionScopeInput = transactionScopeInput;
    }

    public Validator getScopeCrossValidator() {
        return (FacesContext fc, UIComponent uic, Object o) -> {
            Object clientScope = clientScopeInput.getSubmittedValue();
            Object transactionScope = transactionScopeInput.getSubmittedValue();
            String[] clientScopeArray = (String[]) clientScope;
            String[] transactionScopeArray = (String[]) transactionScope;
            if ((clientScope == null || clientScopeArray.length == 0) && (transactionScope == null || transactionScopeArray.length == 0)) {
                BeanHelper.throwValidatorError("pl.pd.emir.validator.ManyCheckboxCrossValidator.EMPTY");
            }
        };
    }

    public boolean isBackloadingEnabled() {
        String value = parameterManager.getValue(ParameterKey.ENABLE_BACKLOADING);
        return value == null ? false : "true".equals(value.toLowerCase());
    }
}
