package pl.pd.emir.register.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.embeddable.TransactionDetails;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.RegistrationTransactionWrapper;
import pl.pd.emir.reports.model.ReportData;
import org.primefaces.context.RequestContext;

@SessionScoped
@ManagedBean(name = "transactionListBean")
public class TransactionListBTMBean extends AbstractTransactionListBaseBean {

    @EJB
    private transient TransactionManager service;
    private transient final ReportData<RegistrationTransactionWrapper> reportData = new ReportData<>();
    private transient final ReportType reportType = ReportType.REGISTRATION_TRANSACTION_TABLE;

    public TransactionListBTMBean() {
        super();
    }

    @Override
    public TransactionManager getService() {
        return service;
    }

    public ReportData getReportData() {
        Collection<RegistrationTransactionWrapper> data = new ArrayList<>();
        List<Transaction> eventLog = service.findAll(criteria);
        for (Transaction trans : eventLog) {
            RegistrationTransactionWrapper wrapper = new RegistrationTransactionWrapper();
            if (trans.getDataType() != null) {
                wrapper.setDataTypeDescription(trans.getDataType().getMsgKey() == null ? "" : BUNDLE.getString(trans.getDataType().getMsgKey()));
            }
            wrapper.setOriginalClientId(trans.getClient() == null || trans.getClient().getOriginalId() == null ? "" : trans.getClient().getOriginalId());
            wrapper.setOriginalId(trans.getOriginalId() == null ? "" : trans.getOriginalId());
            if (trans.getProcessingStatus() != null) {
                wrapper.setProcessingStatusDescription(trans.getProcessingStatus().getMsgKey() == null ? "" : BUNDLE.getString(trans.getProcessingStatus().getMsgKey()));
            }
            wrapper.setTerminationDateSupply(trans.getDateSupply());
            wrapper.setTransactionDate(trans.getTransactionDate());
            if (trans.getTransactionDetails() != null) {
                TransactionDetails transDetails = trans.getTransactionDetails();
                wrapper.setTransactionDetailsSourceTransId(transDetails.getSourceTransId() == null ? "" : transDetails.getSourceTransId());
            }
            if (trans.getValidationStatus() != null) {
                wrapper.setValidationStatusDescription(trans.getValidationStatus().getMsgKey() == null ? "" : BUNDLE.getString(trans.getValidationStatus().getMsgKey()));
            }
            if (trans.getConfirmed() != null) {
                wrapper.setComfirmed(trans.getConfirmed().getMsgKey() == null ? "" : BUNDLE.getString(trans.getConfirmed().getMsgKey()));
            }
            data.add(wrapper);
        }

        ParametersWrapper parameters = new ParametersWrapper("c.idTransaction", "c.internalIdTransaction", "c.date", "c.idConctractors", "c.typeDate", "c.statusProcessing", "c.statusCorrect", "c.confirmed", "c.dataTimeSupply");
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    public ReportType getReportType() {
        return reportType;
    }

    @Override
    public String removeById(Long id, String originalId) {
        if (isPossibilityDeleteTransaction(id, originalId)) {
            getService().deleteById(id);
            getAction();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmDialogError').show();");
        }
        return null;
    }

    @Override
    public boolean isPossibilityDeleteTransaction(Long id, String originalId) {
        List<Object[]> trans = service.isPossibilityDeleteTransaction(originalId);
        if (trans == null) {
            return true;
        }
        Object[] identificator = trans.get(0);
        return id.equals((Long) identificator[0]);
    }
}
