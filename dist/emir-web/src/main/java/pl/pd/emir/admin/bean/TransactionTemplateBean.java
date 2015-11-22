package pl.pd.emir.admin.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import pl.pd.emir.admin.TransactionTemplateManager;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.entity.administration.TransactionTemplate;
import pl.pd.emir.enums.Cleared;
import pl.pd.emir.enums.ClearingOblig;
import pl.pd.emir.enums.CommLoadType;
import pl.pd.emir.enums.CommUnderlDtls;
import pl.pd.emir.enums.CommUnderlType;
import pl.pd.emir.enums.CommercialActity;
import pl.pd.emir.enums.Compression;
import pl.pd.emir.enums.ConfirmationType;
import pl.pd.emir.enums.ConfirmedStatus;
import pl.pd.emir.enums.ContractType;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.CurrencyCode;
import pl.pd.emir.enums.DeliverType;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.FormType;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.OptionExecStyle;
import pl.pd.emir.enums.OptionType;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.enums.YesNo;

@SessionScoped
@ManagedBean(name = "transactionTemplateBean")
public class TransactionTemplateBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private transient TransactionTemplateManager transactionTemplateManager;
    private TransactionTemplate entity;
    private FormType formType;
    private ConfirmedStatus confirmedStatus;

    @PostConstruct
    public void init() {
        List<TransactionTemplate> templates = transactionTemplateManager.findAll();
        if (templates.isEmpty()) {
            entity = new TransactionTemplate();
        } else {
            entity = templates.get(0);
        }
        entity.initFields();
    }

    public void save() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!BeanHelper.isFacesMessage(FacesMessage.SEVERITY_INFO.getOrdinal())) {
            if (confirmedStatus.equals(ConfirmedStatus.EMPTY)) {
                getEntity().setConfirmed(null);
            } else {
                getEntity().setConfirmed(confirmedStatus);
            }
            entity = transactionTemplateManager.save(entity);
            context.execute("PF('confirmDialog').show();");
        }
    }

    public String getAction() {
        return "transactionTemplate";
    }

    public TransactionTemplate getEntity() {
        return entity;
    }

    public void setEntity(TransactionTemplate entity) {
        this.entity = entity;
    }

    public FormType getFormType() {
        formType = FormType.Edit;
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public String getConfirmedStatus() {
        return (entity.getConfirmed() == null ? null : entity.getConfirmed().name());
    }

    public void setConfirmedStatus(String status) {
        if (status == null) {
            this.confirmedStatus = null;
        }
        this.confirmedStatus = ConfirmedStatus.valueOf(status);
    }

    public List<SelectItem> getImportCountryCode() {
        return BeanHelper.fillSelectList(CountryCode.values());
    }

    public List<SelectItem> getImportCurrencyCode() {
        return BeanHelper.fillSelectList(CurrencyCode.values());
    }

    public List<SelectItem> getImportInstitutionIdType() {
        return BeanHelper.fillSelectList(InstitutionIdType.values());
    }

    public List<SelectItem> getImportConfirmedStatus() {

        return BeanHelper.fillMsgSelectList(ConfirmedStatus.values());
    }

    public List<SelectItem> getImportYesNo() {

        return BeanHelper.fillMsgSelectList(YesNo.values());
    }

    public List<SelectItem> getImportTransactionType() {
        return BeanHelper.fillSelectList(TransactionType.values());
    }

    public List<SelectItem> getImportCommercialActity() {
        return BeanHelper.fillSelectList(CommercialActity.values());
    }

    public List<SelectItem> getImportSettlementThreshold() {
        return BeanHelper.fillSelectList(SettlementThreshold.values());
    }

    public List<SelectItem> getImportCompression() {
        return BeanHelper.fillSelectList(Compression.values());
    }

    public List<SelectItem> getImportDeliverType() {
        return BeanHelper.fillSelectList(DeliverType.values());
    }

    public List<SelectItem> getImportClearingOblig() {
        return BeanHelper.fillSelectList(ClearingOblig.values());
    }

    public List<SelectItem> getImportIntergropuTrans() {
        return BeanHelper.fillSelectList(IntergropuTrans.values());
    }

    public List<SelectItem> getImportConfirmationType() {
        return BeanHelper.fillSelectList(ConfirmationType.values());
    }

    public List<SelectItem> getImportCleared() {
        return BeanHelper.fillSelectList(Cleared.values());
    }

    public List<SelectItem> getImportOptionType() {
        return BeanHelper.fillSelectList(OptionType.values());
    }

    public List<SelectItem> getImportOptionExecStyle() {
        return BeanHelper.fillSelectList(OptionExecStyle.values());
    }

    public List<SelectItem> getImportValuationType() {
        return BeanHelper.fillSelectList(ValuationType.values());
    }

    public List<SelectItem> getImportDoProtection() {
        return BeanHelper.fillSelectList(DoProtection.values());
    }

    public List<SelectItem> getImportCommUnderlType() {
        return BeanHelper.fillSelectList(CommUnderlType.values());
    }

    public List<SelectItem> getImportCommUnderlDtls() {
        return BeanHelper.fillSelectList(CommUnderlDtls.values());
    }

    public List<SelectItem> getImportCommLoadType() {
        return BeanHelper.fillSelectList(CommLoadType.values());
    }

    public List<SelectItem> getImportTransactionParty() {
        return BeanHelper.fillSelectList(TransactionParty.values());
    }

    public List<SelectItem> getImportContractType() {
        return BeanHelper.fillSelectList(new ContractType[]{ContractType.U, ContractType.I, ContractType.E, ContractType.ERR});
    }
}
