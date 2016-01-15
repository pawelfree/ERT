package pl.pd.emir.admin.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import pl.pd.emir.admin.BankManager;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.clientutils.MessageDialogType;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.embeddable.BusinessEntity;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.embeddable.InstitutionAddress;
import pl.pd.emir.embeddable.InstitutionData;
import pl.pd.emir.entity.Bank;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.enums.BankStatus;
import pl.pd.emir.enums.ContrPartyIndustry;
import pl.pd.emir.enums.CountryCode;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.resources.MultipleFilesResourceBundle;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.kdpw.service.BankModificationManager;

@ViewScoped
@ManagedBean(name = "bankBean")
public class BankBean implements Serializable {

    private static final long serialVersionUID = 3L;

    private final transient static MultipleFilesResourceBundle BUNDLE = new MultipleFilesResourceBundle();

    private String infoWindow;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BankBean.class);

    /**
     * Aktualnie modyfikowany rekord instytucji.
     */
    private Bank bank;

    /**
     * Flaga określająca czy bean oraz widok znajdują się w trybie modyfikacji
     */
    private boolean editMode;

    /**
     * Przechowuje informację o ostatnim rezultacie generowania komunikatu do KDPW.
     */
    private boolean sendSuccess;

    /**
     * API managera instytucji.
     */
    @EJB
    private transient BankManager bankManager;

    /**
     * API generatora komunikatów.
     */
    @EJB
    private transient BankModificationManager kdpwBankManager;

    @PostConstruct
    public void init() {
        editMode = false;
        bank = bankManager.getActive();
        sendSuccess = false;
        initBank();
    }

    public void saveStep1() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (BeanHelper.isFacesMessage(FacesMessage.SEVERITY_WARN.getOrdinal())) {
        } else {
            context.execute("PF('confirmDialog').show();");
        }
    }

    public String saveStep2(boolean forDate) {
        modificationExistes(forDate);
        bank.setId(null);
        bank = bankManager.edit(bank);
        bank.setId(null);
        editMode = false;
        return getAction();
    }

    private void modificationExistes(boolean forDate) {
        Bank bankTemp = Objects.isNull(bankManager.getActive()) ? null : bankManager.getActive();
        if (Objects.isNull(bankTemp)) {
            bank.setBankStatus(BankStatus.CORRECTED);
        } else {
            List<ChangeLog> changeLogs = bankTemp.getChangeLogs(bank);
            if (changeLogs.size() >= 2) {
                bank.setBankStatus(BankStatus.CORRECTED);
            } else if (changeLogs.size() == 1) {
                if (!changeLogs.get(0).getFieldName().equals("Data raportowania wyceny")) {
                    bank.setBankStatus(BankStatus.CORRECTED);
                }
            }
        }
    }

    public boolean getEnabledGenerate() {
        return bank != null
                && (BankStatus.REJECTED.equals(bank.getBankStatus())
                || BankStatus.CORRECTED.equals(bank.getBankStatus()));
    }

    public boolean disabledChangeDate() {
        return bankManager.findAll().isEmpty();
    }

    public void saveDate() {
        LOGGER.info("Saving date...");
        saveStep2(true);
        LOGGER.info("Saving date...OK");
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public CountryCode[] getCountryCodes() {
        return CountryCode.values();
    }

    public List<SelectItem> getInstitutionIdTypes() {
        return BeanHelper.fillSelectList(InstitutionIdType.values());
    }

    public List<SelectItem> getContrPartyIndustries() {
        return BeanHelper.fillMsgSelectList(ContrPartyIndustry.values());
    }

    public String getAction() {
        return "bankModification";
    }

    /**
     * Komunikat o rezultacjie generowania komunikatu zmiany danych.
     *
     * @return Komunikat o pomyślnym lub nie wygenerowaniu komunikatu do KDPW.
     */
    public final String getSendingResult() {
        final StringBuilder result = new StringBuilder();
        if (sendSuccess) {
            result.append(BeanHelper.getMessage("bankModification.kdpw.message.result.ok"));
        } else {
            result.append(BeanHelper.getMessage("bankModification.kdpw.message.result.error"));
        }
        return result.toString();
    }

    /**
     * Typ okna z rezultatem generowania komunikatu.
     */
    public final String getSendingResultType() {
        if (sendSuccess) {
            return MessageDialogType.INFO.name();
        } else {
            return MessageDialogType.ERROR.name();
        }
    }

    public final void generateMessage() {
        LOGGER.info("Generate message to KDPW...");
        sendSuccess = kdpwBankManager.generateMessage(bank);
        if (sendSuccess) {
            saveStatus(BankStatus.SENT);//zmiana statusu danych instytucji na WYSŁANA
        }
    }

    public boolean isSendSuccess() {
        return sendSuccess;
    }

    public final boolean isSendToKdpwDisabled() {
        return !kdpwBankManager.bankModificationPossible();
    }

    private void saveStatus(BankStatus bankstatus) {
        LOGGER.info("Saving bank status..." + bankstatus.getMsgKey());
        bank.setId(null);
        bank.setBankStatus(bankstatus);
        bank = bankManager.edit(bank);
        bank.setId(null);
        LOGGER.info("Saving bank status OK");
    }

    public String getInfoWindow() {
        infoWindow = BUNDLE.getString("bankModification.doesntChangeDate");
        return infoWindow;
    }

    private void initBank() {
        if (this.bank == null) {
            this.bank = new Bank();
        }
        if (this.bank.getBusinessEntity() == null) {
            this.bank.setBusinessEntity(new BusinessEntity());
        }
        if (this.bank.getInstitution() == null) {
            this.bank.setInstitution(new Institution(new InstitutionData(null, InstitutionIdType.LEIC),
                    new InstitutionAddress()));
        }
        if (this.bank.getCountryCode() == null) {
            this.bank.setCountryCode(null);
        }
    }
    
}
