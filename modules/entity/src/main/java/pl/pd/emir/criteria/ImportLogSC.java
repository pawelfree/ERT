package pl.pd.emir.criteria;

import java.util.Date;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.enums.ImportScope;
import pl.pd.emir.enums.ImportStatus;

public class ImportLogSC extends AbstractSearchCriteria {

    private Date importDateFrom;
    private Date importDateTo;
    private String importUser;
    private ImportScope importScope;
    private Date transactionDateFrom;
    private Date transactionDateTo;
    private ImportStatus importStatus;

    public Date getImportDateFrom() {
        return importDateFrom == null ? null : (Date) importDateFrom.clone();
    }

    public void setImportDateFrom(Date importDateFrom) {
        this.importDateFrom = importDateFrom;
    }

    public Date getImportDateTo() {
        return importDateTo == null ? null : (Date) importDateTo.clone();
    }

    public void setImportDateTo(Date importDateTo) {
        this.importDateTo = importDateTo;
    }

    public String getImportUser() {
        return importUser;
    }

    public void setImportUser(String importUser) {
        this.importUser = importUser;
    }

    public ImportScope getImportScope() {
        return importScope;
    }

    public void setImportScope(ImportScope importScope) {
        this.importScope = importScope;
    }

    public Date getTransactionDateFrom() {
        return transactionDateFrom == null ? null : (Date) transactionDateFrom.clone();
    }

    public void setTransactionDateFrom(Date transactionDateFrom) {
        this.transactionDateFrom = transactionDateFrom;
    }

    public Date getTransactionDateTo() {
        return transactionDateTo == null ? null : (Date) transactionDateTo.clone();
    }

    public void setTransactionDateTo(Date transactionDateTo) {
        this.transactionDateTo = transactionDateTo;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ImportStatus importStatus) {
        this.importStatus = importStatus;
    }

    @Override
    public void clear() {
        super.clear();
        importDateFrom = null;
        importDateTo = null;
        importUser = null;
        importScope = null;
        transactionDateFrom = null;
        transactionDateTo = null;
        importStatus = null;
    }

    @Override
    public void addFilters() {
        clearFilters();
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "importStatus", "=", importStatus));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", ">=", transactionDateFrom));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", "<=", transactionDateTo));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "importScope", "=", importScope));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "importUser", "like", importUser));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "importDate", "<=", importDateTo));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "importDate", ">=", importDateFrom));
    }
}
