package pl.pd.emir.register.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
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
import pl.pd.emir.enums.Instrument;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.report.enums.InstrumentType;
import pl.pd.emir.reports.model.EucTradeDataWrapper;

@SessionScoped
@ManagedBean(name = "transactionListBean")
public class TransactionListBTMBean extends AbstractTransactionListBaseBean {

    @EJB
    private transient TransactionManager service;
    private transient final ReportData<RegistrationTransactionWrapper> reportData = new ReportData<>();
    private transient BigDecimal rate = BigDecimal.ONE;

    public TransactionListBTMBean() {
        super();
    }

    @Override
    public TransactionManager getService() {
        return service;
    }

    public ReportData getReportData() {
        Collection<RegistrationTransactionWrapper> data = new ArrayList<>();
        List<Transaction> transactions = service.findAll(criteria);
        for (Transaction trans : transactions) {
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

    public ReportData getEucReportData() {
        ReportData<EucTradeDataWrapper> eucReportData = new ReportData<>();
        Collection<EucTradeDataWrapper> data = new ArrayList<>();
        String prod1_code, prod2_code, productType, amount1, amount2;

        List<Transaction> transactions = service.findAll(criteria);
        for (Transaction transaction : transactions) {
            prod1_code = transaction.getContractDetailedData().getContractData().getProd1Code();
            prod2_code = transaction.getContractDetailedData().getContractData().getProd2Code();

            if (prod1_code.equalsIgnoreCase(InstrumentType.INTERESTRATE.getRealName()) && prod2_code.equalsIgnoreCase(Instrument.SWAP.getRealName())) {
                productType = "IRS";
                amount1 = amount2 = transaction.getTransactionDetails().getNominalAmount().toPlainString();
            } else if (prod1_code.equalsIgnoreCase(InstrumentType.CURRENCY.getRealName()) && prod2_code.equalsIgnoreCase(Instrument.FORWARD.getRealName())) {
                productType = "FX Forward";
                amount1 = transaction.getTransactionDetails().getNominalAmount().toPlainString();

                if (transaction.getCurrencyTradeData().getCurrTradeBasis().substring(0,2).equalsIgnoreCase(transaction.getContractDetailedData().getUnderlCurrency2Code().toString())) {
                    amount2 = transaction.getTransactionDetails().getNominalAmount().multiply(transaction.getTransactionDetails().getUnitPrice()).toPlainString();
                } else {
                    amount2 = transaction.getTransactionDetails().getNominalAmount().divide(transaction.getTransactionDetails().getUnitPrice(),4,RoundingMode.HALF_UP).toPlainString();
                }

            } else {
                productType = amount1 = amount2 = "";
            }
            
            if (!productType.isEmpty()) {

                EucTradeDataWrapper wrapper = new EucTradeDataWrapper(
                        transaction.getOriginalId(),
                        transaction.getClient().getOriginalId(),
                        productType,
                        transaction.getContractDetailedData().getUnderlCurrency1Code().toString(), amount1,
                        transaction.getContractDetailedData().getUnderlCurrency2Code().toString(), amount2,
                        transaction.getTransactionDetails().getExecutionDate(),
                        transaction.getTransactionDetails().getEffectiveDate(),
                        transaction.getTransactionDetails().getMaturityDate(),
                        transaction.getValuation().getValuationData().getAmount().abs().divide(rate, 4, RoundingMode.HALF_UP).toPlainString());

                data.add(wrapper);
            }
        }

        eucReportData.setReportData(data);

        ParametersWrapper parameters = new ParametersWrapper();
        eucReportData.setParameters(parameters.getParameters());

        return eucReportData;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public ReportType getReportType() {
        return ReportType.REGISTRATION_TRANSACTION_TABLE;
    }

    public ReportType getEucReportType() {
        return ReportType.EUC_TRADE_DATA;
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
