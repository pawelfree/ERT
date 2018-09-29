package pl.pd.emir.bean;

import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.enums.FormType;
import pl.pd.emir.enums.MsgEnum;
import pl.pd.emir.enums.ProcessingStatus;
import pl.pd.emir.enums.ValidationStatus;
import pl.pd.emir.enums.ValueMsgEnum;

public final class BeanHelper implements Serializable {

    private BeanHelper() {
    }

    public static void addFatalMessage(String message, String details) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, message, details);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addInfoMessage(String message, final String clientId) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    public static void addWarningMessage(String message) {
        addWarningMessage(message, null);
    }

    public static void addWarningMessage(String message, String clientId) {
        addWarningMessage(message, clientId, null);
    }

    public static void addWarningMessage(String message, String clientId, String details) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, details);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    public static void addErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addErrorMessage(String message, String clientId) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    public static void addErrorMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addErrorMessage(msg);
    }

    public static void addFatalMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addFatalMessage(msg, null);
    }

    public static void addErrorMessageFromResource(String key, String clientId, Object... params) {
        final String msg = getMessage(key, params);
        addErrorMessage(msg, clientId);
    }

    public static void addInfoMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addInfoMessage(msg);
    }

    public static void addWarningMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addWarningMessage(msg);
    }

    public static void addWarningMessageFromResource(String key, String clientId, Object... params) {
        final String msg = getMessage(key, params);
        addWarningMessage(msg, clientId);
    }

    public static void addReportMessageValidation(String title, String description) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, description));
    }

    public static String getMessage(String key, Object... params) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("pl.pd.emir.resources.MultipleFilesResourceBundle", locale);

        return StringUtil.getMessage(bundle, key, params);
    }

    public static void throwValidatorError(String msgKey, Object... params) {
        throwValidatorError(msgKey, FacesMessage.SEVERITY_ERROR, params);
    }

    public static void throwValidatorError(String msgKey, FacesMessage.Severity severity, Object... params) {
        final String errMsg = getMessage(msgKey, params);
        throwValidatorPlainError(errMsg, severity);
    }

    public static void throwValidatorPlainError(String errMsg, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage(severity, errMsg, errMsg);
        throw new ValidatorException(facesMessage);
    }

    public static void throwConverterError(String msgKey, Object... params) throws ConverterException {
        final String errMsg = getMessage(msgKey, params);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
        throw new ConverterException(facesMessage);
    }

    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list) {
        return eraseOneFromSelectList(list, getMessage("commons.errFieldsValue"));
    }

    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list, String errValue) {
        if (list == null || list.length == 0) {
            return null;
        }
        List<SelectItem> result = new ArrayList<>();
        for (T obj : list) {
            result.add(new SelectItem(obj.name(), obj.name(), null, obj.name().equals(errValue)));
        }
        return result;
    }

    private static <T extends Enum<T>> List<SelectItem> eraseOneFromSelectList(T[] list, String errValue) {
        if (list == null || list.length == 0) {
            return null;
        }
        List<SelectItem> result = new ArrayList<>();
        for (T obj : list) {
            if (!obj.name().equals(errValue)) {
                result.add(new SelectItem(obj.name(), obj.name(), null, false));
            }
        }
        return result;
    }

    public static <T extends Enum<T> & MsgEnum> List<SelectItem> fillMsgSelectList(T[] list) {
        return fillMsgSelectList(list, getMessage("commons.errFieldsValue"));
    }

    public static <T extends Enum<T> & MsgEnum> List<SelectItem> fillMsgSelectList(T[] list, String errValue) {
        if (list == null || list.length == 0) {
            return null;
        }
        List<SelectItem> result = new ArrayList<>();
        for (T obj : list) {
            result.add(new SelectItem(obj.name(), BeanHelper.getMessage(obj.getMsgKey()), null, obj.name().equals(errValue)));
        }
        return result;
    }
    
    public static <T extends Enum<T> & ValueMsgEnum> List<SelectItem> fillValueMsgSelectList(T[] list) {
        return fillValueMsgSelectList(list, getMessage("commons.errFieldsValue"));
    }

    public static <T extends Enum<T> & ValueMsgEnum> List<SelectItem> fillValueMsgSelectList(T[] list, String errValue) {
        if (list == null || list.length == 0) {
            return null;
        }
        List<SelectItem> result = new ArrayList<>();
        for (T obj : list) {
            result.add(new SelectItem(obj.getValue(), BeanHelper.getMessage(obj.getMsg()), null, obj.name().equals(errValue)));
        }
        return result;
    }

    public static void refreshInputs(UIComponent parent) {
        if (null != parent) {
            if (parent instanceof UIInput) {
                ((UIInput) parent).resetValue();
            }
            Iterator<UIComponent> it = parent.getFacetsAndChildren();
            while (it.hasNext()) {
                UIComponent component = it.next();
                if (component.getChildren().size() > 0 || component.getFacetCount() > 0) {
                    refreshInputs(component);
                }
                if (component instanceof UIInput) {
                    ((UIInput) component).resetValue();
                }
            }
        }
    }

    public static void refreshInputs(String parentId) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent parent = fc.getViewRoot().findComponent(parentId);
        refreshInputs(parent);
    }

    public static String getUserPrincipal() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.getUserPrincipal() == null ? "null" : externalContext.getUserPrincipal().toString();
    }

    public static boolean checkRequiredObject(Object value, String message, String messageDetails) {
        if (value == null) {
            addWarningMessage(message, null, messageDetails);
            return false;
        }
        return true;
    }

    public static boolean checkRequiredObject(Object value, String clientId, String message, String messageDetails) {
        if (value == null) {
            addWarningMessage(message, clientId, messageDetails);
            return false;
        }
        return true;
    }

    public static boolean checkRequiredString(String value, String fieldName) {
        if (StringUtil.isEmpty(value)) {
            addWarningMessageFromResource("register.form.requiredField", (Object) BeanHelper.getMessage(fieldName));
            return false;
        }
        return true;
    }

    public static void sendGlobalFormErrorMessage() {
        addErrorMessageFromResource("form.errorField", "growlError");
    }

    public static boolean isFacesMessage(int minLevel) {
        return FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getMaximumSeverity() != null && FacesContext.getCurrentInstance().getMaximumSeverity().getOrdinal() >= minLevel;
    }

    public static boolean isEditable(FormType typ, ProcessingStatus processingStatus, ValidationStatus validationStatus) {
        return typ == FormType.View && (processingStatus == ProcessingStatus.NEW || processingStatus == ProcessingStatus.REJECTED || validationStatus == ValidationStatus.INCOMPLETE);
    }

}
