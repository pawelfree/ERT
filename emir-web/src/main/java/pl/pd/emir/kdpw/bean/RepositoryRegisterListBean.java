package pl.pd.emir.kdpw.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.criteria.TransactionToKdpwSC;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.register.TransactionToSendManager;

/**
 * Obsługa wysyłki transakcji do KDPW.
 */
@ViewScoped
@ManagedBean(name = "kdpwTransRegisterListBean")
public class RepositoryRegisterListBean extends AbstractKdpwListBean<Transaction, TransactionToSendManager, TransactionToKdpwSC> {

    @EJB
    private transient TransactionToSendManager service;

    @EJB
    private ParameterManager parameterManager;

    private boolean backloadingOptionShown;

    public RepositoryRegisterListBean() {
        super(TransactionToKdpwSC.class);
    }

    @Override
    public final TransactionToSendManager getService() {
        return service;
    }

    @Override
    public final String getFilterDateSame() {
        if (backloadingOptionShown) {
            return "transaction.kdpw.sc.transactionDate.info.register.single.backloading";
        } else {
            return "transaction.kdpw.sc.transactionDate.info.register.single";
        }
    }

    @Override
    public final String getFilterDateScope() {
        if (backloadingOptionShown) {
            return "transaction.kdpw.sc.transactionDate.info.register.multi.backloading";
        } else {
            return "transaction.kdpw.sc.transactionDate.info.register.multi";
        }
    }

    @Override
    public final String getFilterDateNoResults() {
        return "transaction.kdpw.sc.transactionDate.info.register.noResults";
    }

    @Override
    protected void initDefaultCriteria() {
        super.initDefaultCriteria();
        criteria.setSourceTransId(null);
        criteria.setDataToSend(Boolean.TRUE);
        backloadingOptionShown = isBackloadingEnabled();
    }

    public boolean isBackloadingOptionShown() {
        return backloadingOptionShown;
    }

    public void setBackloadingOptionShown(boolean backloadingOptionShown) {
        this.backloadingOptionShown = backloadingOptionShown;
    }

    private Long reportsCount = 0L;

    /**
     * Generuje komunikat(y) do KDWP z danymi transakcji/żadanie anulowania
     * transakcji.
     */
    public final void generateRegistration() {
        addIdsToCriteria();
        final TransactionToKdpwSC tmp = storeCriteria(criteria);
        sendingResult = kdpwTransManager.generateRegistration(criteria);
        reportsCount = kdpwTransManager.getKdpwReportsCount(criteria);
        restorCriteria(tmp);
        LOGGER.debug("Generate messages result: {}", sendingResult);
        setFirstPage();
        defineDefaultDateInfo();
    }

    public boolean isBackloadingEnabled() {
        String value = parameterManager.getValue(ParameterKey.ENABLE_BACKLOADING);
        return value == null ? false : "true".equals(value.toLowerCase());
    }

    public Long getReports() {
        return reportsCount;
    }
}
