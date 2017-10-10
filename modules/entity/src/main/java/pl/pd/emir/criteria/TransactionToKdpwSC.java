package pl.pd.emir.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterEnumTO;
import pl.pd.emir.dao.utils.FilterLongListTO;
import pl.pd.emir.dao.utils.FilterLongTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;

public class TransactionToKdpwSC extends AbstractSearchCriteria {

    private static final long serialVersionUID = 1068579378636599678L;

    /**
     * Id bazodanowe. Na potrzeby stronicowania.
     */
    protected Long id;

    /**
     * ID bazodanowe > transakcje bedaczytane.
     */
    protected Long fromId;

    /**
     * TradID.
     */
    protected String sourceTransId;

    ;
    /**
     * Data transakcji.
     */
    protected Date transactionDate;

    /**
     * Data transakcji do.
     */
    protected Date transactionDateTo;

    /**
     * Status.
     */
    protected List<ProcessingStatus> statuses;

    /**
     * Poprawność.
     */
    protected ValidationStatus validationStatus;

    /**
     * Typ danych.
     */
    protected final List<DataType> dataTypes = new ArrayList<>();

    protected boolean neww;

    protected boolean ongoing;

    protected boolean completed;

    protected Boolean newestVersion;

    private transient List<Long> selectedIds = new ArrayList<>();

    private transient List<Long> deselectedIds = new ArrayList<>();

    protected Boolean dataToSend;

    public final Date getTransactionDate() {
        return transactionDate == null ? null : (Date) transactionDate.clone();
    }

    public final void setTransactionDate(final Date value) {
        this.transactionDate = value;
    }

    public final List<ProcessingStatus> getProcessingStatuses() {
        return statuses;
    }

    public final void setProcessingStatuses(final List<ProcessingStatus> value) {
        this.statuses = value;
    }

    public final ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public final void setValidationStatus(final ValidationStatus value) {
        this.validationStatus = value;
    }

    public final boolean isNeww() {
        return neww;
    }

    public final void setNeww(final boolean neww) {
        this.neww = neww;
    }

    public final boolean isOngoing() {
        return ongoing;
    }

    public final void setOngoing(final boolean value) {
        this.ongoing = value;
    }

    public final boolean isCompleted() {
        return completed;
    }

    public final void setCompleted(final boolean value) {
        this.completed = value;
    }

    public final Date getTransactionDateTo() {
        return transactionDateTo;
    }

    public final void setTransactionDateTo(final Date value) {
        this.transactionDateTo = value;
    }

    public final boolean isNewestVersion() {
        return newestVersion;
    }

    public final void setNewestVersion(final boolean value) {
        this.newestVersion = value;
    }

    public List<DataType> getDataTypes() {
        dataTypes.clear();
        if (isNeww()) {
            dataTypes.add(DataType.NEW);
        }
        if (isOngoing()) {
            dataTypes.add(DataType.ONGOING);
        }
        if (isCompleted()) {
            dataTypes.add(DataType.COMPLETED);
        }
        return dataTypes;
    }

    @Override
    public void addFilters() {
        clearFilters();
        final List<DataType> dataTypeList = getDataTypeList();
        // jeżeli nie zaznaczono zadnego typu
        if (dataTypeList.isEmpty()) {
            getFitrSort().getFilters().add(FilterLongTO.valueOf("", "id", "=", -1L));
        } else {
            getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "dataToSend", "=", dataToSend));
            getFitrSort().getFilters().add(FilterLongTO.valueOf("", "id", "=", id));
            getFitrSort().getFilters().add(FilterLongTO.valueOf("", "id", ">", fromId));
            getFitrSort().getFilters().add(FilterStringTO.valueOf("", "transactionDetails.sourceTransId", "%.%", sourceTransId));
            getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", "=", transactionDate));
            getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", "<=", transactionDateTo));
            getFitrSort().getFilters().add(FilterEnumTO.valueOf("", "processingStatus", "IN", statuses));
            getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "validationStatus", "=", validationStatus));
            getFitrSort().getFilters().add(FilterEnumTO.valueOf("", "dataType", "IN", dataTypeList));
            getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "newestVersion", "=", newestVersion));

            if (CollectionsUtils.isNotEmpty(selectedIds)) {
                getFitrSort().getFilters().add(FilterLongListTO.valueOf("", "id", "IN", selectedIds));
            }
        }
    }

    public List<DataType> getDataTypeList() {
        final List<DataType> result = new ArrayList<>();
        if (neww) {
            result.add(DataType.NEW);
        }
        if (ongoing) {
            result.add(DataType.ONGOING);
        }
        if (completed) {
            result.add(DataType.COMPLETED);
        }
        return result;
    }

    public String getSourceTransId() {
        return sourceTransId;
    }

    public void setSourceTransId(String sourceTransId) {
        this.sourceTransId = sourceTransId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getDeselectedIds() {
        return deselectedIds;
    }

    public List<Long> getSelectedIds() {
        return selectedIds;
    }

    public void setDeselectedIds(List<Long> deselectedIds) {
        this.deselectedIds = deselectedIds;
    }

    public void setSelectedIds(List<Long> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public void setDataToSend(Boolean dataToSend) {
        this.dataToSend = dataToSend;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getFromId() {
        return fromId;
    }

}
