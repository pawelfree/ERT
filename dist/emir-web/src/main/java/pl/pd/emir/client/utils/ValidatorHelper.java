package pl.pd.emir.client.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

public final class ValidatorHelper {

    public static FacesMessage getFacesMessageRequierWarn(String componentName, String componentNameDetail) {
        return getFacesMessage(componentName, componentNameDetail, FacesMessage.SEVERITY_WARN);
    }

    public static FacesMessage getFacesMessage(String componentName, String componentNameDetail, FacesMessage.Severity severity) {
        return new FacesMessage(FacesMessage.SEVERITY_WARN, BeanHelper.getMessage("register.form.requiredFieldSummary", componentName), BeanHelper.getMessage("register.form.requiredField", componentNameDetail));
    }

    public static String getComponentName(UIComponent component) {
        String name = (String) component.getAttributes().get("label");
        if (name == null) {
            name = (String) component.getAttributes().get("title");
        }
        if (name == null) {
            name = component.getId();
        }
        return name;
    }
    
    public static String getComponentNameDetail(UIComponent component) {
        String name = (String) component.getAttributes().get("title");
        if (name == null) {
            name = (String) component.getAttributes().get("label");
        }
        if (name == null) {
            name = component.getId();
        }
        return name;
    }
    
    public static void throwValidatorError(final String errMsg) {        
        final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
        throw new ValidatorException(facesMessage);
    }
    
        
    public static void throwValidatorError(final String msgKey, final Object... params) {
        final String errMsg = BeanHelper.getMessage(msgKey, params);
        throwValidatorError(errMsg);
    }
    
    public static String getSenderLabel(final UIComponent component) {
        String result = (String) component.getAttributes().get("label");
        if (result == null) {
            result = component.getId();
        }
        return result;
    }
}
