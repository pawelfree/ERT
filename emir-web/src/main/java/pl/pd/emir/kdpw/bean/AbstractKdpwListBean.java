package pl.pd.emir.kdpw.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.clientutils.MessageDialogType;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.commons.interfaces.Selectable;
import pl.pd.emir.criteria.TransactionToKdpwSC;
import pl.pd.emir.kdpw.api.KdpwTransactionManager;
import pl.pd.emir.kdpw.api.ResultItem;
import pl.pd.emir.kdpw.api.SendingResult;
import pl.pd.emir.register.KdpwListManager;
import org.primefaces.context.RequestContext;

public abstract class AbstractKdpwListBean<T extends Selectable<Long>, M extends KdpwListManager<T>, C extends TransactionToKdpwSC> extends AbstractListBean<T, M, C> {

    /**
     * Akcja wywołania widoku.
     */
    private static final String VIEW_ACTION = "transactionsToKdpw";

    /**
     * Obsługa wysyłki.
     */
    @EJB
    protected transient KdpwTransactionManager kdpwTransManager;

    protected transient ActionMode actionMode;

    protected SendingResult sendingResult;

    protected Date dateFrom;

    protected Date dateTo;

    protected enum ActionMode {

        NEW("transaction.kdpw.message.confirmation"),
        CANCEL_TRANSACTION("transaction.kdpw.message.confirmation.cancelTransaction"),
        CANCEL_MUTATION("transaction.kdpw.message.confirmation.cancelMutation");

        private final String confirmMsg;

        private ActionMode(String confirmMsg) {
            this.confirmMsg = confirmMsg;
        }

        public String getConfirmMsg() {
            return confirmMsg;
        }
    }

    /**
     * default constructor
     */
    public AbstractKdpwListBean(final Class<C> criteriaClazz) {
        super(criteriaClazz);
    }

    @PostConstruct
    protected void initialize() {
        init();
        actionMode = ActionMode.NEW;
        defineDefaultSelectAll();
    }

    protected void defineDefaultFilters() {
        // zakres dostepnych komunikatów
        criteria.setTransactionDateTo(DateUtils.getPreviousWorkingDayWithFreeDays(new Date()));
    }

    protected final void defineDefaultDateFilters() {
        defineDefaultDateInfo();
        if (Objects.nonNull(dateFrom)) {
            criteria.setTransactionDate(dateFrom);
        } else {
            criteria.setTransactionDate(DateUtils.getPreviousWorkingDayWithFreeDays(new Date()));
        }
        LOGGER.info("Criteria.transactionDate = {}", criteria.getTransactionDate());
    }

    protected final void defineDefaultDateInfo() {
        dateFrom = getService().getMinDate();
        dateTo = getService().getMaxDate();
    }

    protected final void defineDefaultSelectAll() {
        // domyslne zaznaczenie 'SelectAll'
        final long recordCount = getService().getRecordCount(criteria);
        recordCounter = NumberUtils.safeLongToInt(recordCount);
        selectAllCheckBoxValue = selectAll = (recordCount > 0);
    }

    @Override
    public void initSearchCriteria() {
        initDefaultCriteria();
    }

    @Override
    public void initSearchCriteriaAfterClean() {
        initDefaultCriteria();
    }

    protected void initDefaultCriteria() {
        criteria.setNeww(true);
        criteria.setOngoing(true);
        criteria.setCompleted(true);
        defineDefaultFilters();
        defineDefaultDateFilters();
    }

    @Override
    public void setFirstPage() {
        super.setFirstPage();
        super.selectAllListener();
    }

    @Override
    public final String getAction() {
        return VIEW_ACTION;
    }

    public final void createNewMode() {
        actionMode = ActionMode.NEW;
        showDialog("registerConfirmDialog", "noSelectedDialog");
    }

    public final void cancelTransactionMode() {
        actionMode = ActionMode.CANCEL_TRANSACTION;
        showDialog("cancelConfirmDialog", "noSelectedDialog");
    }

    public final void cancelMutationMode() {
        actionMode = ActionMode.CANCEL_MUTATION;
        showDialog("cancelConfirmDialog", "noSelectedDialog");
    }

    public final boolean isCreateNewDisabled() {
        return !anySelected();
    }

    public final boolean isCancelDisabled() {
        return !anySelected();
    }

    public final boolean isModifyDisabled() {
        return !anySelected();
    }

    public final String getConfirmationMessage() {
        return BeanHelper.getMessage(actionMode.getConfirmMsg(), getSelectedCount());
    }

    public final boolean isAnySelected() {
        return getSelectedCount() > 0;
    }

    public final String getResultMessage() {
        final StringBuilder result = new StringBuilder();
        if (Objects.nonNull(sendingResult)) {
            if (sendingResult.isGenerateError()) {
                // BŁAD WYSYŁKI
                result.append(BeanHelper.getMessage("transaction.kdpw.message.result.failure",
                        formatDate(criteria.getTransactionDate())));
            } else {
                if (CollectionsUtils.isNotEmpty(sendingResult.getSentList())) {
                    // przynajmniej jedna wysłana
                    if (ActionMode.NEW.equals(actionMode)) {
                        result.append(BeanHelper.getMessage("transaction.kdpw.message.result.success.register",
                                formatDate(criteria.getTransactionDate())));
                    } else if (ActionMode.CANCEL_MUTATION.equals(actionMode) || ActionMode.CANCEL_TRANSACTION.equals(actionMode)) {
                        result.append(BeanHelper.getMessage("transaction.kdpw.message.result.success.cancell",
                                formatDate(criteria.getTransactionDate())));
                    } else {
                        result.append(BeanHelper.getMessage("transaction.kdpw.message.result.success.modify", formatDate(criteria.getTransactionDate())));
                    }
                } else // nie wysłano zadnej transakji
                if (ActionMode.NEW.equals(actionMode)) {
                    result.append(BeanHelper.getMessage("transaction.kdpw.message.result.failure.register.noFile",
                            formatDate(criteria.getTransactionDate())));
                } else if (ActionMode.CANCEL_MUTATION.equals(actionMode) || ActionMode.CANCEL_TRANSACTION.equals(actionMode)) {
                    result.append(BeanHelper.getMessage("transaction.kdpw.message.result.failure.cancell.noFile",
                            formatDate(criteria.getTransactionDate())));
                }

                if (sendingResult.anyUnsent()) {
                    result.append(" ").append(BeanHelper.getMessage("transaction.kdpw.message.result.unsent"));
                }
                if (sendingResult.anyUnprocessed()) {
                    result.append(" ").append(BeanHelper.getMessage("transaction.kdpw.message.result.unprocessed"));
                }
            }
        }
        return result.toString();
    }

    public final boolean isAnyError() {
        if (null != sendingResult) {
            LOGGER.info("generate error: " + sendingResult.isGenerateError());
            LOGGER.info("anyUnsent: " + sendingResult.anyUnsent());
            LOGGER.info("anyUnprocessed: " + sendingResult.anyUnprocessed());
            LOGGER.info("++isAnyError++: " + (Objects.nonNull(sendingResult)
                    && !sendingResult.isGenerateError()
                    && (sendingResult.anyUnsent()
                    || sendingResult.anyUnprocessed())));

        }
        return Objects.nonNull(sendingResult)
                && !sendingResult.isGenerateError()
                && (sendingResult.anyUnsent()
                || sendingResult.anyUnprocessed());
    }

    public final List<ResultItem> getSendResultList() {
        final List<ResultItem> result = new ArrayList<>();
        if (null != sendingResult) {
            result.addAll(sendingResult.getUnprocessed());
            result.addAll(sendingResult.getUnsentList());
        }
        return result;
    }

    public final String getGenerationResultType() {
        return MessageDialogType.INFO.name();
    }

    protected final String formatDate(final Date date) {
        return DateUtils.formatDate(date, DateUtils.DATE_FORMAT);
    }

    public final String getTransactionDateScopeMsg() {
        final StringBuilder result = new StringBuilder();
        if (Objects.nonNull(dateFrom)) {
            if (DateUtils.isSameDay(dateFrom, dateTo)) {
                result.append(BeanHelper.getMessage(getFilterDateSame(),
                        DateUtils.formatDate(dateFrom, DateUtils.DATE_FORMAT)));
            } else {
                result.append(BeanHelper.getMessage(getFilterDateScope(),
                        DateUtils.formatDate(dateFrom, DateUtils.DATE_FORMAT),
                        DateUtils.formatDate(dateTo, DateUtils.DATE_FORMAT)));
            }
        } else {
            result.append(BeanHelper.getMessage(getFilterDateNoResults()));
        }
        return result.toString();
    }

    public abstract String getFilterDateSame();

    public abstract String getFilterDateScope();

    public abstract String getFilterDateNoResults();

    /**
     * Ustawienie informacji na SC o zaznaczonych/odznaczonych rekordach.
     */
    protected final void addIdsToCriteria() {
        criteria.setSelectedIds(getSelectedIds());
        criteria.setDeselectedIds(getDeselectedIds());
    }

    public void showDialog(final String dialogId, final String errorDialog) {
        if (isAnySelected()) {
            RequestContext.getCurrentInstance().execute("PF('" + dialogId + "').show()");
        } else {
            RequestContext.getCurrentInstance().execute("PF('" + errorDialog + "').show()");
        }
    }

    public void hideDialog(final String dialogId) {
        RequestContext.getCurrentInstance().execute("PF('" + dialogId + "').hide()");
    }

    protected TransactionToKdpwSC storeCriteria(TransactionToKdpwSC criteria) {
        TransactionToKdpwSC result = new TransactionToKdpwSC();
        result.setFirstResult(0);
        result.setMaxResults(criteria.getMaxResults());
        result.getFitrSort().setSortField(criteria.getFitrSort().getSortField());
        result.getFitrSort().setSortOrder(criteria.getFitrSort().getSortOrder());
        return result;
    }

    protected void restorCriteria(TransactionToKdpwSC stored) {
        criteria.setFirstResult(stored.getFirstResult());
        criteria.setMaxResults(stored.getMaxResults());
        criteria.getFitrSort().setSortField(stored.getFitrSort().getSortField());
        criteria.getFitrSort().setSortOrder(criteria.getFitrSort().getSortOrder());
        // czyszczenie
        criteria.setSelectedIds(new ArrayList<>());
        criteria.setDeselectedIds(new ArrayList<>());
        criteria.setFromId(null);
    }
}
