package pl.pd.emir.register.bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
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
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.DeliverType;
import pl.pd.emir.enums.DoProtection;
import pl.pd.emir.enums.FormType;
import pl.pd.emir.enums.InstitutionIdType;
import pl.pd.emir.enums.IntergropuTrans;
import pl.pd.emir.enums.OptionExecStyle;
import pl.pd.emir.enums.OptionType;
import pl.pd.emir.enums.OriginalStatus;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.SettlementThreshold;
import pl.pd.emir.enums.TransactionParty;
import pl.pd.emir.enums.TransactionType;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.enums.ValuationType;
import pl.pd.emir.enums.YesNo;
import pl.pd.emir.register.ClientManager;
import pl.pd.emir.register.ProtectionManager;
import pl.pd.emir.register.TransactionManager;
import pl.pd.emir.register.ValuationManager;
import org.primefaces.context.RequestContext;
import pl.pd.emir.enums.UnderlyingType;

public class TransactionFormBean extends AbstractFormBean<Transaction> {

    protected static final Logger LOG = Logger.getLogger(TransactionFormBean.class.getName());

    @EJB
    private transient ClientManager clientManager;

    @EJB
    protected transient TransactionManager transactionManager;

    @EJB
    private transient ProtectionManager protectionManager;

    @EJB
    private transient ValuationManager valuationManager;

    @EJB
    private ParameterManager parameterManager;

    private static final long serialVersionUID = 1L;

    protected String infoWindow;

    protected transient boolean mutation;

    protected transient long idMutation;

    protected transient boolean valuation;

    protected transient long idValuation;

    private Date primaryTransactionDate;

    private boolean showVersion = false;

    public TransactionFormBean() {
        super(Transaction.class);
    }

    public List<Client> getAllClients() {
        return clientManager.findAll();
    }

    @Override
    public void changeContext(FormType newTyp) {
        if (newTyp.equals(FormType.View)) {
            if (mutation) {
                setEntity(transactionManager.getById(idMutation));
            } else if (valuation) {
                setEntity(transactionManager.getById(idValuation));
            } else {
                setEntity(transactionManager.getById(getEntity().getId()));
            }
            mutation = false;
            idMutation = 0;
            valuation = false;
            idValuation = 0;
        } else if (newTyp.equals(FormType.Edit)) {

            if (getEntity().getProtection() == null) {
                Protection newProtection = new Protection();
                newProtection.setFileName(getEntity().getFileName() == null ? "" : getEntity().getFileName());
                newProtection.setOriginalId(getEntity().getOriginalId() == null ? "" : getEntity().getOriginalId());
                newProtection = protectionManager.save(newProtection);
                getEntity().setProtection(newProtection);
                if (getEntity().getValuation() == null) {
                    Valuation newValuation = new Valuation();
                    newValuation.setFileName(getEntity().getFileName() == null ? "" : getEntity().getFileName());
                    newValuation.setOriginalId(getEntity().getOriginalId() == null ? "" : getEntity().getOriginalId());
                    newValuation = valuationManager.save(newValuation);
                    getEntity().setValuation(newValuation);
                }
                setEntity(transactionManager.save(getEntity(), mutation, valuation));
            }
        }
        super.changeContext(newTyp);
    }

    protected void fillContractorBeneficiary() {
        Client client = clientManager.getClientByOrginalId(entity.getClient().getOriginalId());
        if (client != null) {
            entity.getClientData().setBeneficiaryCode(client.getInstitutionId());
            entity.getClientData().setBeneficiaryCodeType(client.getInstitutionIdType());
        }
        //TODO PAWEL KONIECZNIE DO zmiany - ustawiany przy imporcie z pliku a przy dodawaniu recznym musi byc robione jak ponizej - powinno byc jakos sparametryzowane
        entity.getClient2().setOriginalId("200005");
        client = clientManager.getClientByOrginalId(entity.getClient2().getOriginalId());
        if (client != null) {
            entity.getClient2Data().setBeneficiaryCode(client.getInstitutionId());
            entity.getClient2Data().setBeneficiaryCodeType(client.getInstitutionIdType());
        }
    }

    public boolean isBeneficiaryCodeEditingDisabled() {
        String value = parameterManager.getValue(ParameterKey.DISABLE_MANUAL_TRANSACTION_BENEFICIARY_CODE_EDITING);
        return value == null ? true : "true".equals(value.toLowerCase());
    }

    @Override
    public String getAction() {
        valuation = false;
        mutation = false;
        showVersion = checkShowVersionParameter();
        return "transactionForm";
    }

    @Override
    public void initEntity() {
        final Transaction transaction = new Transaction();
        transaction.initFields();
        setEntity(transaction);
    }

    @Override
    protected void initEntityFields() {
        final Transaction transaction = getEntity();
        transaction.setDataType(DataType.NEW);
        transaction.setProcessingStatus(ProcessingStatus.NEW);
        transaction.setOriginalStatus(OriginalStatus.N);
        transaction.setTransactionDate(DateUtils.getPreviousWorkingDayWithFreeDays(new Date()));
        setEntity(transaction);
        mutation = false;
        valuation = false;
        idMutation = 0;
        idValuation = 0;
    }

    @Override
    public boolean isEditable() {
        return BeanHelper.isEditable(getFormType(), getEntity().getProcessingStatus(), getEntity().getValidationStatus());
    }

    @Override
    protected void initEntity(Long id) {
        if (id == null) {
            initEntity();
        } else {
            setEntity(transactionManager.getById(id));
        }
        getEntity().initFields();
        mutation = false;
        idMutation = 0;
    }

    public void saveStep1() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (StringUtil.isEmpty(getEntity().getClient().getOriginalId())) { //sprawdzenie obligatoryjności pola ze względu na klucz obcy
            infoWindow = BeanHelper.getMessage("register.transaction.emptyClientStatus");
            context.execute("PF('confirmDialogError').show();");
        } else if (checkExistsClient(getEntity().getClient().getOriginalId()) == false) { //sprawdzenie czy istnieje klient o podanym id
            infoWindow = BeanHelper.getMessage("register.transaction.notExistsClient");
            context.execute("PF('confirmDialogError').show();");
        } else if ((getFormType() != FormType.Edit) && idNotUniquenessTransaction()) { //sprawdzenie unikalności transakcji ze względu na originalId
            infoWindow = BeanHelper.getMessage("register.transaction.uniquenessOriginaIdAndTransactionDate");
            context.execute("PF('confirmDialogError').show();");
        } else if (!isExistsDuplicate()) {
            if (FormType.New.equals(getFormType())) { //rejestracja nowej transakcji
                getEntity().setDateSupply(new Date());
                getEntity().setProcessingStatus(ProcessingStatus.NEW);
                getEntity().setDataType(DataType.NEW);
            } else if (FormType.Edit.equals(getFormType())) { //edycja transackji bądź rejestracja mutacji albo wyceny i zabezpieczen
                if (mutation || valuation) {
                    getEntity().setDateSupply(new Date());
                    getEntity().setProcessingStatus(ProcessingStatus.NEW);
                    getEntity().setDataType(DataType.ONGOING);
                }
                if (!ProcessingStatus.NEW.equals(getEntity().getProcessingStatus()) && !valuation) {
                    getEntity().setProcessingStatus(ProcessingStatus.CORRECTED);
                }
            }
            if (Objects.isNull(getEntity().getTransactionDetails().getPreviousSourceTransId())) {
                getEntity().getTransactionDetails().setPreviousSourceTransId("");
            }
            //sprawdzenie czy transakcja jest ZAKOŃCZONA (data rozwiązania równa dacie transakcji)
            if (getEntity().getTransactionDetails().getTerminationDate() != null) {
                //data zakończenia
                Date terminationDate = DateUtils.getDayBegin(getEntity().getTransactionDetails().getTerminationDate());
                //data transakcji
                Date transactionDate = DateUtils.getDayBegin(getEntity().getTransactionDate());
                if (terminationDate.equals(transactionDate)) {
                    //ustawienie typu danych transakcji na ZAKOŃCZONA
                    getEntity().setDataType(DataType.COMPLETED);
                } else if (terminationDate.after(transactionDate) && DataType.COMPLETED.equals(getEntity().getDataType())) {
                    //powrót do typu danych TRWAJĄCA
                    getEntity().setDataType(DataType.ONGOING);
                }
            }
            //Określenie statusu walidacji
            if (BeanHelper.isFacesMessage(FacesMessage.SEVERITY_WARN.getOrdinal())) {
                getEntity().setValidationStatus(ValidationStatus.INCOMPLETE);
                context.execute("PF('confirmDialogIncomplete').show();");
            } else {
                getEntity().setValidationStatus(ValidationStatus.VALID);
                context.execute("PF('confirmDialogOk').show();");
            }
        }

    }

    public String saveStep2() {
        setEntity(transactionManager.save(getEntity(), mutation, valuation));
        valuation = false;
        mutation = false;
        return "transactionList";
    }

    protected boolean checkExistsClient(String originalId) {
        Client client = clientManager.getClientByOrginalId(originalId);
        return client != null;
    }

    public boolean isEditingPossible() {
        return getEntity().getProcessingStatus().equals(ProcessingStatus.NEW)
                || getEntity().getProcessingStatus().equals(ProcessingStatus.CORRECTED)
                || getEntity().getProcessingStatus().equals(ProcessingStatus.PARTLY_REJECTED)
                || getEntity().getProcessingStatus().equals(ProcessingStatus.REJECTED);
    }

    public boolean isAddingValuationPossible() {
        return isAddingMutationPossible();
    }

    public boolean isAddingValuationCheckDate() {
        return isAddingMutationCheckDate();
    }

    public boolean isAddingMutationPossible() {
        LOG.log(Level.FINE, "isAddingMutationPossible : transaction processing status must bo one of the : {0} {1} {2}",
                new Object[]{ProcessingStatus.CONFIRMED, ProcessingStatus.SENT, ProcessingStatus.UNSENT});
        boolean result = getEntity().getProcessingStatus().equals(ProcessingStatus.CONFIRMED)
                || getEntity().getProcessingStatus().equals(ProcessingStatus.SENT)
                || getEntity().getProcessingStatus().equals(ProcessingStatus.UNSENT);
        LOG.log(Level.FINE, "isAddingMutationPossible result : {0}", result);
        return result;
    }

    public boolean isAddingMutationCheckDate() {
        LOG.fine("isAddingMutationCheckDate : transaction date must not be grater than previous working day");
        if (getEntity().getTransactionDate() == null) {
            LOG.fine("isAddingMutationCheckDate - transaction date is null");
            return false;
        }
        Date transactionDate = getEntity().getTransactionDate();
        Date maxDate = DateUtils.getPreviousWorkingDayWithFreeDays(new Date());
        LOG.log(Level.FINE, "isAddingMutationCheckDate - transaction date : {0} , previous working day : {1}",
                new Object[]{transactionDate, maxDate});
        boolean result = transactionDate.compareTo(maxDate) == 0 || transactionDate.before(maxDate);
        LOG.log(Level.FINE, "isAddingMutationCheckDate - result : {0} ", result);
        return result;
    }

    public void refreshForm() {
        refreshForm(false, false);
    }

    public void refreshForm(boolean isMutation, boolean isValuation) {
        Transaction core;
        if (isMutation) {
            core = transactionManager.getById(idMutation);
        } else if (isValuation) {
            core = transactionManager.getById(idValuation);
        } else {
            core = transactionManager.getById(getEntity().getId());
        }
        setEntity(isMutation || isValuation ? core.fullClone() : core);
        Transaction tmp = getEntity();
        tmp.initFields();
        primaryTransactionDate = tmp.getTransactionDate();
        if (isMutation) {
            tmp.setTransactionDate(DateUtils.getNextWorkingDay(tmp.getTransactionDate()));
        }
        if (isValuation) {
            tmp.setTransactionDate(DateUtils.getPreviousWorkingDayWithFreeDays(new Date()));
        }
        setEntity(tmp);
        BeanHelper.refreshInputs("form1:tv:transactionBaseDataPg1");
        BeanHelper.refreshInputs("form1:tv:valuationPg1");
        BeanHelper.refreshInputs("form1:tv:protectionPg1");
        BeanHelper.refreshInputs("form1:tv:generalDataPg1");
        BeanHelper.refreshInputs("form1:tv:generalDataPg2");
        BeanHelper.refreshInputs("form1:tv:contractTypePg1");
        BeanHelper.refreshInputs("form1:tv:derivativesPg1");
    }

    public boolean isExistsYoungerMutation() {
        LOG.log(Level.FINE, "ExistsYoungerMutation : transaction must be valid and no younger mutation exists");
        boolean result = getEntity().getValidationStatus() != ValidationStatus.VALID || transactionManager.isYoungerMutation(getEntity().getOriginalId(), getEntity().getTransactionDate(), getEntity().getExtractVersion());
        LOG.log(Level.FINE, "ExistsYoungerMutation - result : {0} ", result);
        return result;
    }

    public boolean isNotMutation() {
        return !transactionManager.isYoungerMutation(getEntity().getOriginalId(), getEntity().getTransactionDate(), getEntity().getExtractVersion());
    }

    private void addModificationStep1(final boolean isMutation) {
        if (getEntity().getProcessingStatus() != null) {
            if (getEntity().getProcessingStatus().equals(ProcessingStatus.NEW)
                    || getEntity().getProcessingStatus().equals(ProcessingStatus.SENT)) {
                RequestContext context = RequestContext.getCurrentInstance();
                String message, dialogName;
                dialogName = isMutation ? "PF('confirmDialogAddMutation').show();" : "PF('confirmDialogAddValuation').show();";
                if (getEntity().getProcessingStatus().equals(ProcessingStatus.NEW)) {
                    message = isMutation ? "register.transaction.addMutation.exceptionNew" : "register.transaction.addValuation.exceptionNew";
                    infoWindow = BeanHelper.getMessage(message);
                    context.execute(dialogName);
                }
                if (getEntity().getProcessingStatus().equals(ProcessingStatus.SENT)) {
                    message = isMutation ? "register.transaction.addMutation.exceptionSent" : "register.transaction.addValuation.exceptionSent";
                    infoWindow = BeanHelper.getMessage(message);
                    context.execute(dialogName);
                }
            } else if (isMutation) {
                addMutationStep2();
            } else {
                addValuationStep2();
            }
        }
    }

    public void addMutationStep1() {
        addModificationStep1(true);
    }

    public void addValuationStep1() {
        addModificationStep1(false);
    }

    private String addModificationStep2() {
        changeContext(FormType.Edit);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("transactionForm.xhtml");
        } catch (IOException ex) {
            getAction();
        }
        return null;
    }

    public String addValuationStep2() {
        valuation = true;
        mutation = false;
        idValuation = getEntity().getId();
        refreshForm(false, true);
        return addModificationStep2();
    }

    public String addMutationStep2() {
        mutation = true;
        valuation = false;
        idMutation = getEntity().getId();
        refreshForm(true, false);
        return addModificationStep2();

    }

    protected boolean isExistsDuplicate() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (StringUtil.isNotEmpty(getEntity().getOriginalId())) {
            if (((getFormType().equals(FormType.New) && transactionManager.isExistsItemYesterday(getEntity().getId(), getEntity().getOriginalId()))
                    || (getFormType().equals(FormType.Edit) && transactionManager.isNewerVersion(getEntity())))
                    && !mutation) {
                infoWindow = BeanHelper.getMessage("register.errorDuplicate");
                context.execute("PF('confirmDialogError').show();");
                return true;
            }
            //sprawdzenie czy istniej późniejsza/ młodsza mutacja
            if (mutation && transactionManager.isYoungerMutation(getEntity().getOriginalId(), getEntity().getTransactionDate(), getEntity().getExtractVersion())) {
                infoWindow = BeanHelper.getMessage("register.errorDuplicateMutation");
                context.execute("PF('confirmDialogError').show();");
                return true;
            }
        }
        return false;
    }

    public String dateAfterTransactonDate(Date transactionDate) {
        String date = DateUtils.formatDate(transactionDate, DateUtils.DATE_FORMAT);
        return date;
    }

    //TODO PAWEL dwie metody do unifikacji
    public boolean getRequiredValuaProtectInputs() {
        return true;
    }

    public boolean checkRequiredValuaProtectInputs() {
        return true;
    }

    protected boolean idNotUniquenessTransaction() {
        List<Transaction> trans = transactionManager.getUniquenessIdOriginal(getEntity().getOriginalId().trim());
        return !trans.isEmpty();
    }

    public String getConfirmedStatus() {
        if (ConfirmedStatus.CONFIRMED.equals(entity.getConfirmed())) {
            return ConfirmedStatus.CONFIRMED.name();
        } else if (ConfirmedStatus.UNCONFIRMED.equals(entity.getConfirmed())) {
            return ConfirmedStatus.UNCONFIRMED.name();
        }
        return null;
    }
    
    public String getInfoWindow() {
        return infoWindow;
    }

    public void setInfoWindow(String infoWindow) {
        this.infoWindow = infoWindow;
    }

    public boolean isMutation() {
        return mutation;
    }

    public boolean isValuation() {
        return valuation;
    }

    public boolean checkShowVersionParameter() {
        return true;
    }

    public Date getPrimaryTransactionDate() {
        return primaryTransactionDate;
    }

    public void setPrimaryTransactionDate(Date primaryTransactionDate) {
        this.primaryTransactionDate = primaryTransactionDate;
    }

    public List<SelectItem> getImportCountryCode() {
        return BeanHelper.fillSelectList(CountryCode.values());
    }
    
    public List<SelectItem> getImportUnderlyingType() {
        return BeanHelper.fillSelectList(UnderlyingType.values());
    }

    public List<SelectItem> getImportCurrencyCode() {
        return BeanHelper.fillSelectList(CurrencyCode.values());
    }

    public List<SelectItem> getImportInstitutionIdType() {
        return BeanHelper.fillSelectList(InstitutionIdType.values());
    }

    public List<ConfirmedStatus> getImportConfirmedStatus() {
        return Arrays.asList(ConfirmedStatus.values());
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

    public boolean checkMutationInDB() {
        return transactionManager.isMutation(getEntity());
    }

    public boolean getShowVersion() {
        return showVersion;
    }

    public boolean isViewMode() {
        return FormType.View.equals(formType);
    }

    public boolean isClientReported() {
        if (null != entity.getClient() && StringUtil.isNotEmpty(entity.getClient().getOriginalId())) {
            final Client client = clientManager.getClientByOrginalId(entity.getClient().getOriginalId());
            return null != client && client.getReported();
        }
        return false;
    }

}
