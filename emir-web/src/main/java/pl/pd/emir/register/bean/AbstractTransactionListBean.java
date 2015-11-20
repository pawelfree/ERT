package pl.pd.emir.register.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.criteria.TransactionSC;
import pl.pd.emir.entity.kdpw.KdpwMsgItem;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.manager.GenericManager;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

public abstract class AbstractTransactionListBean<T extends Selectable<Long>, M extends GenericManager<T>, S extends TransactionSC>
        extends AbstractListBean<T, M, S> {

    /**
     * Akcja.
     */
    protected static final String ACTION = "transactionList";

    /**
     * Powiązane komunikaty z transakcją.
     */
    protected transient List<KdpwMsgItem> msgItems = new ArrayList<>();

    /**
     * SearchCriteria.
     */
    protected transient S mainCriteria;

    /**
     * Bundle.
     */
    protected final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();

    /**
     * Id ostatnio dodanej transakcji.
     */
    private Long lastAddedTransId;

    /**
     * Konstruktor.
     *
     * @param criteriaClazz
     */
    protected AbstractTransactionListBean(final Class<S> criteriaClazz) {
        super(criteriaClazz);
    }

    /**
     * Akcja na rozwijanie tabelki komunikatów powiązanych z transakcją.
     *
     * @param event
     */
    public abstract void onRowToggle(final ToggleEvent event);

    public abstract DataType[] getDataType();

    protected abstract String getDefaultSortField();

    public List<KdpwMsgItem> getMsgItems() {
        return msgItems;
    }

    public final void restoreSearchCriteria() {
        //disableRelatedView();
        criteria = mainCriteria;
        initSearchCriteria();
        setFirstPage();
    }

    @Override
    public void initSearchCriteria() {
        initCriteria();
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        initCriteria();
    }

    @Override
    public final String getAction() {
        return ACTION;
    }

    public ProcessingStatus[] getProcessingStatus() {
        return ProcessingStatus.values();
    }

    public ValidationStatus[] getValidationStatus() {
        return ValidationStatus.values();
    }

    public ConfirmedStatus[] getConfirmedStatus() {
        return ConfirmedStatus.values();
    }

    public YesNo[] getYesNo() {
        return YesNo.values();
    }

    protected final void initCriteria() {
        setSortField(getDefaultSortField());
        setSortOrder(SortOrder.DESCENDING);
        criteria.setDateStart(DateUtils.getDayBegin(DateUtils.getPreviousWorkingDayWithFreeDays(new Date())));
        criteria.setDateEnd(DateUtils.getDayEnd(DateUtils.getPreviousWorkingDayWithFreeDays(new Date())));
        setPageSize(10);
        setFirstPage();
    }

    public void setLastAddedTransId(final Long value) {
        this.lastAddedTransId = value;
    }

    public final void addedTransactionInfo() {
        if (isLastTransactionNotVisible()) {
            BeanHelper.addInfoMessage(BeanHelper.getMessage("register.transactionList.addedTransactionVisible"));
        }
        lastAddedTransId = null;
    }

    /**
     * Informacja czy ostatnio dodana transakcja jest dostepna z widoku listy transakcji z biezacymi kryteriami
     * wyszukiwania.
     */
    private boolean isLastTransactionNotVisible() {
        if (lastAddedTransId == null) {
            return false;
        }
        final int firstResult = criteria.getFirstResult();
        final int maxResults = criteria.getMaxResults();
        criteria.setId(lastAddedTransId);
        final long size = getService().getRecordCount(criteria);

        // przywrocenie kryteriów wyszukiwania
        criteria.setId(null);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        return size == 0;
    }
}
