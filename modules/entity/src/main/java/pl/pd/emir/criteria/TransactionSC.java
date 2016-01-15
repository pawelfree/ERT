package pl.pd.emir.criteria;

import java.util.Date;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.enums.YesNo;

public class TransactionSC extends AbstractSearchCriteria {

    private Long id;

    protected String transactionId;

    protected String originalClientId;

    protected Date dateStart;

    protected Date dateEnd;

    protected DataType dataType;

    protected ProcessingStatus processingStatus;

    protected ValidationStatus validationStatus;

    /**
     * Dane do wysyłki.
     */
    protected YesNo dataToSend;

    private ConfirmedStatus confirmed;

    public void setId(Long id) {
        this.id = id;
    }

    public final YesNo getDataToSend() {
        return dataToSend;
    }

    public final void setDataToSend(final YesNo value) {
        this.dataToSend = value;
    }

    public final String getTransactionId() {
        return transactionId;
    }

    public final void setTransactionId(final String value) {
        this.transactionId = value;
    }

    public final String getOriginalClientId() {
        return originalClientId;
    }

    public final void setOriginalClientId(final String value) {
        this.originalClientId = value;
    }

    public final Date getDateStart() {
        return dateStart == null ? null : (Date) dateStart.clone();
    }

    public final void setDateStart(final Date value) {
        this.dateStart = value;
    }

    public final Date getDateEnd() {
        return dateEnd == null ? null : (Date) dateEnd.clone();
    }

    public final void setDateEnd(final Date value) {
        this.dateEnd = value;
    }

    public final DataType getDataType() {
        return dataType;
    }

    public final void setDataType(final DataType value) {
        this.dataType = value;
    }

    public final ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public final void setProcessingStatus(final ProcessingStatus value) {
        this.processingStatus = value;
    }

    public final ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public final void setValidationStatus(final ValidationStatus value) {
        this.validationStatus = value;
    }

    public final ConfirmedStatus getConfirmed() {
        return confirmed;
    }

    public final void setConfirmed(final ConfirmedStatus value) {
        this.confirmed = value;
    }

    @Override
    public void addFilters() {
        clearFilters();
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "id", "=", id));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "validationStatus", "=", validationStatus));
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "processingStatus", "=", processingStatus));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", ">=", dateStart));
        getFitrSort().getFilters().add(FilterDateTO.valueOf("", "transactionDate", "<=", dateEnd));
        if (ConfirmedStatus.EMPTY != confirmed) {
            getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "confirmed", "=", confirmed));
        }
        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "dataType", "=", dataType));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "client.originalId", "%.%", originalClientId));
        getFitrSort().getFilters().add(FilterStringTO.valueOf("", "transactionDetails.sourceTransId", "%.%", transactionId));

        getFitrSort().getFilters().add(FilterObjectTO.valueOf("", "dataToSend", "=", dataToSend == null ? null : dataToSend.getLogical()));
    }

    @Override
    public void clear() {
        super.clear();
        transactionId = null;
        originalClientId = null;
        dateStart = null;
        dateEnd = null;
        dataType = null;
        processingStatus = null;
        validationStatus = null;
        confirmed = null;
        dataToSend = null;
    }
}
