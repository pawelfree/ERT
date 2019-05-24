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
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.imports.ImportOverview;
import pl.pd.emir.imports.ImportCsvManager;

@ViewScoped
@ManagedBean(name = "importBean")
public class ImportBean implements Serializable {

    @EJB
    private transient ImportCsvManager importCsvManager;

    @ManagedProperty(value = "#{importListBean}")
    private transient ImportListBean importListBean;

    private List<ImportScope> transactionSelectedScope;
    private List<ImportScope> transactionDisplayedScope;
    private Date extractDate;

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
        importOverview = new ImportOverview();
    }

    public void importExtract() {
        List<ImportScope> allSelected = new ArrayList<>();
        allSelected.addAll(transactionSelectedScope);
        importOverview = importCsvManager.importCsv(allSelected, DateUtils.getDayBegin(extractDate));

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

    public UIInput getTransactionScopeInput() {
        return transactionScopeInput;
    }

    public void setTransactionScopeInput(UIInput transactionScopeInput) {
        this.transactionScopeInput = transactionScopeInput;
    }

    public Validator getScopeCrossValidator() {
        return (FacesContext fc, UIComponent uic, Object o) -> {
            Object transactionScope = transactionScopeInput.getSubmittedValue();
            String[] transactionScopeArray = (String[]) transactionScope;
            if (transactionScope == null || transactionScopeArray.length == 0) {
                BeanHelper.throwValidatorError("pl.pd.emir.validator.ManyCheckboxCrossValidator.EMPTY");
            }
        };
    }
}
