package pl.pd.emir.admin.bean;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.auth.IIDMConfig;
import pl.pd.emir.auth.ILdapHelper;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.client.utils.ValidatorHelper;
import pl.pd.emir.entity.administration.User;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.FormType;
import pl.pd.emir.resources.EventLogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.admin.EventLogManager;

@SessionScoped
@ManagedBean(name = "userFormBean")
public class UserFormBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFormBean.class);
    //private transient final static ResourceBundle BUNDLE = MultipleFilesResourceBundle.getBundle("messages");
    private static final long serialVersionUID = 42L;
    private FormType formType;
    private User entity;
    @EJB
    private transient UserManager userManager;
    @EJB
    private transient ILdapHelper ldapHelper;
    @EJB
    private IIDMConfig idmConfig;
    @EJB
    private transient EventLogManager eventLogManager;

    @ManagedProperty(value = "#{userListBean}")
    private transient UserListBean listBean;

    public UserListBean getListBean() {
        return listBean;
    }

    public void setListBean(UserListBean listBean) {
        this.listBean = listBean;
    }

    /**
     * Get the value of formType
     *
     * @return the value of formType
     */
    public FormType getFormType() {
        return formType;
    }

    /**
     * Set the value of formType
     *
     * @param formType new value of formType
     */
    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public void changeContext(FormType newTyp) {
        setFormType(newTyp);
    }

    public User getEntity() {
        return entity;
    }

    public void setEntity(User entity) {
        this.entity = entity;
    }

    public String init(FormType formType, Long id) {
        entity = userManager.getById(id);
        return init(formType);
    }

    public String init(FormType formType) {
        setFormType(formType);
        switch (formType) {
            case New:
                entity = new User();
                entity.setActive(Boolean.TRUE);
                break;
            case Edit:
            case View:
            default:
                break;
        }
        return getAction();
    }

    private String getAction() {
        return "userForm";
    }

    public class UserLdapValidator implements Validator {

        @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
            if ("DB".equalsIgnoreCase(idmConfig.getConfig().getFirstConnector().getAuthenticationMode())) {
                return;
            }
            if (value != null) {
                User usr = ldapHelper.getUserFromLdap((String) value, true);
                if (null == usr.getLogin()) {
                    entity.setFirstName(null);
                    entity.setLastName(null);
                    ValidatorHelper.throwValidatorError("admin.user.info.notInLdap", value);
                } else {
                    entity.setFirstName(usr.getFirstName());
                    entity.setLastName(usr.getLastName());
                }
            }
        }

    }

    final private UserLdapValidator ldapValidator = new UserLdapValidator();

    public UserLdapValidator getLdapValidator() {
        return ldapValidator;
    }

    public String save() {
        if (entity.getLogin() == null || entity.getLogin().isEmpty()) {
            BeanHelper.addInfoMessageFromResource("admin.user.info.login.empty");
            return null;
        }

        User usr;
        try {
            usr = userManager.save(entity);
            if (formType.equals(FormType.New)) {
                addNewUserEventLog(entity.getLogin());
            }
            if (formType.equals(FormType.Edit)) {
                modUserEventLog(entity.getLogin());
            }
        } catch (Exception ex) {
            if (!BeanHelper.isFacesMessage(FacesMessage.SEVERITY_INFO.getOrdinal())) {
                BeanHelper.addErrorMessageFromResource("admin.user.save.error");
                //BeanHelper.addReportMessageValidation(BUNDLE.getString("commons.MessageDialogType.INFO"), BUNDLE.getString("admin.user.save.error"));
            }
            LOGGER.error("Save user eror {}", ex.getMessage(), ex);
            return null;
        }
        LOGGER.error("PAWEL - zapisane " + usr.getLastName());
        setEntity(userManager.refresh(usr));
        return listBean.init();
    }

    /**
     * Zapis zdarzeń użytkownika do EventLogu.
     */
    private void addNewUserEventLog(String login) {
        EventLogBuilder eventLogBuilder = new EventLogBuilder();
        eventLogBuilder.append(EventLogBuilder.EventDetailsKey.LOGIN, login);
        eventLogManager.addEventNonTransactional(EventType.USER_REGISTRATION, null, eventLogBuilder.toString());
    }

    private void modUserEventLog(String login) {
        EventLogBuilder eventLogBuilder = new EventLogBuilder();
        eventLogBuilder.append(EventLogBuilder.EventDetailsKey.LOGIN, login);
        eventLogManager.addEventNonTransactional(EventType.USER_MODIFICATION, null, eventLogBuilder.toString());
    }

}
