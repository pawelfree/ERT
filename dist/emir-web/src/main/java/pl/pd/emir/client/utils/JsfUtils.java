package pl.pd.emir.client.utils;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class JsfUtils {

    public static boolean inSet(String value, String set) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String[] values = set.split(",");
        for (String v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T getSubmittedValue(String componentId) {
        UIInput input = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
        if (input != null) {
            return (T) input.getSubmittedValue();
        }
        return null;
    }
}
