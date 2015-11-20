package pl.pd.emir.register.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pl.pd.emir.bean.AbstractListBean;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.EventLogManager;
import pl.pd.emir.criteria.ClientSC;
import pl.pd.emir.criteria.TransactionSC;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.entity.Client;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.report.CsvExportService;
import pl.pd.emir.reports.enums.ReportType;
import pl.pd.emir.reports.model.ClientListWrapper;
import pl.pd.emir.reports.model.ParametersWrapper;
import pl.pd.emir.reports.model.ReportData;

@SessionScoped
@ManagedBean(name = "clientListBean")
public class ClientListBean extends AbstractListBean<Client, ClientManager, ClientSC> {

    private static final long serialVersionUID = 4232456L;

    private transient ReportData<ClientListWrapper> reportData;

    private final transient ReportType reportType = ReportType.CONTRACTORS_TABLE;

    @EJB
    private transient CsvExportService csvExportService;

    @EJB
    private transient ClientManager clientManager;

    @EJB
    private transient TransactionManager transactionManager;

    @EJB
    private transient EventLogManager eventLogManager;

    @Override
    public ClientManager getService() {
        return clientManager;
    }

    protected CsvExportService getCsvExportService() {
        return csvExportService;
    }

    protected EventLogManager getEventLogManager() {
        return eventLogManager;
    }

    protected TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public ReportData getReportData() {
        Collection<ClientListWrapper> data = new ArrayList();
        List<Client> clients = getService().findAll(criteria);
        clients.stream().map((client) -> {
            ClientListWrapper wrapper = new ClientListWrapper();
            if (client.getBusinessEntity() != null) {
                BusinessEntity bussinesEntity = client.getBusinessEntity();
                if (bussinesEntity.getSubjectNip() != null) {
                    wrapper.setBusinessEntitySubjectNip(client.getBusinessEntity().getSubjectNip());
                }
            }
            if (client.getBusinessEntity() != null) {
                BusinessEntity bussinesEntity = client.getBusinessEntity();
                if (bussinesEntity.getSubjectRegon() != null) {
                    wrapper.setBusinessEntitySubjectRegon(client.getBusinessEntity().getSubjectRegon());
                }
            }
            wrapper.setClientName(client.getClientName() == null ? "" : client.getClientName());
            if (client.getInstitution() != null) {
                Institution institution = client.getInstitution();
                if (institution.getInstitutionData() != null) {
                    InstitutionData institutionData = institution.getInstitutionData();
                    if (institutionData.getInstitutionId() != null) {
                        wrapper.setInstitutionInstitutionDataInstitutionId(institutionData.getInstitutionId());
                    }
                }
            }
            wrapper.setOriginalId(client.getOriginalId());
            if (client.getValidationStatus() != null) {
                wrapper.setValidationStatusMsgKey(client.getValidationStatus().getMsgKey() == null ? ""
                        : BeanHelper.getMessage(client.getValidationStatus().getMsgKey()));
            }
            return wrapper;
        }).forEach((wrapper) -> {
            data.add(wrapper);
        });

        ParametersWrapper parameters = new ParametersWrapper("c.idConctractors", "c.name", "c.nip", "c.regon", "c.internalId", "c.statusCorrect");
        reportData = new ReportData();
        reportData.setParameters(parameters.getParameters());
        reportData.setReportData(data);
        return reportData;
    }

    public ClientListBean() {
        super(ClientSC.class);
    }

    public ValidationStatus[] getValidationStatus() {
        return ValidationStatus.values();
    }

    @Override
    public void initSearchCriteria() {
    }

    @Override
    public void initSearchCriteriaAfterClean() {
    }

    @Override
    public String getAction() {
        return "clientList";
    }

    public Boolean canRemove(final Long id) {
        TransactionSC criteriaTemp = new TransactionSC();
        criteriaTemp.clear();
        criteriaTemp.setMaxResults(1);

        criteriaTemp.getFitrSort().getFilters().add(FilterStringTO.valueOf("", "client.originalId", "%.%", id.toString()));

        return getTransactionManager().find(criteriaTemp).getData().isEmpty();
    }

    public String removeById(Long id) {
        getService().deleteById(id);
        deleteClientEventLog();
        this.setFirstPage();
        return getAction();
    }

    protected void deleteClientEventLog() {
        getEventLogManager().addEventNonTransactional(EventType.CLIENT_DELETE, null, "");
    }

}
