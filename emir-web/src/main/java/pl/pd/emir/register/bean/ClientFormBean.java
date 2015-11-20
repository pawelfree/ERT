package pl.pd.emir.register.bean;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.EventLogManager;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.entity.Client;
import pl.pd.emir.enums.ContrPartyIndustry;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.FormType;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.resources.EventLogBuilder;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "clientFormBean")
@SessionScoped
public class ClientFormBean extends AbstractFormBean<Client> {

    private static final long serialVersionUID = 42L;

    private boolean visiblePopup;

    protected String infoWindow;

    @ManagedProperty(value = "#{clientListBean}")
    private transient ClientListBean listBean;

    public ClientFormBean() {
        super(Client.class);
    }

    @EJB
    private transient ClientManager clientManager;

    protected ClientManager getService() {
        return clientManager;
    }

    @EJB
    private transient TransactionManager transactionManager;

    protected TransactionManager getTransactionManager() {
        return transactionManager;
    }

    @EJB
    private transient EventLogManager eventLogManager;

    protected EventLogManager getEventLogManager() {
        return eventLogManager;
    }

    public boolean isVisiblePopup() {
        return visiblePopup;
    }

    public void setVisiblePopup(boolean visiblePopup) {
        this.visiblePopup = visiblePopup;
    }

    public int getClientNameLength() {
        return getSizeValue("clientName");
    }

    public int getNipLength() {
        return getSizeValue(BusinessEntity.class, "subjectNip");
    }

    public int getRegonLength() {
        return getSizeValue(BusinessEntity.class, "subjectRegon");
    }

    public int getInstitutionIdLength() {
        return getSizeValue(InstitutionData.class, "institutionId");
    }

    public int geteOGLength() {
        return getSizeValue("eog");
    }

    public int getAddressPostalCodeLength() {
        return getSizeValue(InstitutionAddress.class, "postalCode");
    }

    public int getAddressCityLength() {
        return getSizeValue(InstitutionAddress.class, "city");
    }

    public int getAddressStreetNameLength() {
        return getSizeValue(InstitutionAddress.class, "streetName");
    }

    public int getAddressBuildingIdLength() {
        return getSizeValue(InstitutionAddress.class, "buildingId");
    }

    public int getAddressPremisesIdLength() {
        return getSizeValue(InstitutionAddress.class, "premisesId");
    }

    public int getAddressDetailsLength() {
        return getSizeValue(InstitutionAddress.class, "details");
    }

    public int getOriginalIdLength() {
        return getSizeValue("originalId");
    }

    public List<SelectItem> getCountryCodes() {
        return BeanHelper.fillSelectList(CountryCode.values());
    }

    public List<SelectItem> getInstitutionIdTypes() {
        return BeanHelper.fillSelectList(InstitutionIdType.values());
    }

    public List<SelectItem> getContrPartyIndustries() {
        return BeanHelper.fillMsgSelectList(ContrPartyIndustry.values());
    }

    @Override
    public void changeContext(FormType newTyp) {
        if (newTyp.equals(FormType.View)) {
            setEntity(getService().getById(getEntity().getId()));
        }
        super.changeContext(newTyp);
    }

    @Override
    protected String getAction() {
        return "clientForm";
    }

    @Override
    protected void initEntity() {
        Client cl;
        cl = new Client();
        Institution inst = new Institution();
        inst.setInstitutionAddr(new InstitutionAddress());
        inst.setInstitutionData(new InstitutionData());
        cl.setInstitution(inst);
        cl.setBusinessEntity(new BusinessEntity());
        cl.setIntraGroupTransactions(false);
        setEntity(cl);
    }

    @Override
    protected void initEntity(Long id) {
        if (id == null) {
            initEntity();
        } else {
            Client cl = getService().getById(id);
            setEntity(cl);
        }
    }

    @Override
    protected void initEntityFields() {
        getEntity().setValidationStatus(ValidationStatus.INCOMPLETE);
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    public void saveStep1() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idNotUniquenessClient() && getFormType().equals(FormType.New)) {
            infoWindow = BeanHelper.getMessage("register.client.duplicateIdOriginal");
            context.execute("PF('confirmDialogError').show();");
            return;
        }
        if (BeanHelper.isFacesMessage(FacesMessage.SEVERITY_WARN.getOrdinal())) {
            getEntity().setValidationStatus(ValidationStatus.INCOMPLETE);
            if (clientHasTransaction(getEntity()) && getFormType().equals(FormType.Edit)) {
                infoWindow = BeanHelper.getMessage("register.client.clientMustBeValid");
                context.execute("PF('confirmDialogError').show();");
            } else {
                context.execute("PF('confirmDialog').show();");
            }
        } else {
            context.execute("document.getElementById('messages_container').style.visibility = 'hidden';");
            getEntity().setValidationStatus(ValidationStatus.VALID);
            context.execute("PF('confirmDialogOk').show();");
        }
    }

    public String saveStep2() {
        getService().save(getEntity());
        if (FormType.New.equals(getFormType())) {
            addClientEventLog(getEntity().getValidationStatus());
        }
        return "clientList";
    }

//    public Boolean getReported() {
//        if (Objects.isNull(getEntity().getReported())) {
//            return BeanHelper.getMessage("commons.select");
//        } else {
//            if (getEntity().getReported().equals("Y") || getEntity().getReported().equals("N")) {
//                return BeanHelper.getMessage("YesNo.") + getEntity().getReported();
//            } else {
//                return getEntity().getReported();
//            }
//        }
//    }
    protected boolean idNotUniquenessClient() {
        if (null != getEntity() && StringUtil.isNotEmpty(getEntity().getOriginalId())) {
            final List<Client> trans = getService().getUniquenessIdOriginal(getEntity().getOriginalId().trim());
            return !trans.isEmpty();
        }
        return false;
    }

    private boolean clientHasTransaction(Client client) {
        final Long clientCount = getTransactionManager().getCountTransactionByClient(client);
        return clientCount != null && clientCount > 1L;
    }

    public String getInfoWindow() {
        return infoWindow;
    }

    private void addClientEventLog(ValidationStatus valueStatus) {
        EventLogBuilder eventLogBuilder = new EventLogBuilder();
        eventLogBuilder.append(EventLogBuilder.EventDetailsKey.VALIDATION_STATUS, valueStatus.getMsgKey(), true);
        getEventLogManager().addEventNonTransactional(EventType.CLIENT_INSERT, null, eventLogBuilder.toString());
    }

    public boolean isClientInfoRequired() {

        if (InstitutionIdType.LEIC.equals(entity.getInstitution().getInstitutionData().getInstitutionIdType())) {
            boolean xmlName = StringUtil.isNotEmpty(entity.getClientName());
            boolean code = entity.getCountryCode() != null;
            boolean city = entity.getInstitution().getInstitutionAddr() != null
                    && StringUtil.isNotEmpty(entity.getInstitution().getInstitutionAddr().getCity());
            return xmlName != code || xmlName != city || code != city;
        } else {
            return true;
        }
    }

    public ClientListBean getListBean() {
        return listBean;
    }

    public void setListBean(ClientListBean listBean) {
        this.listBean = listBean;
    }

}
