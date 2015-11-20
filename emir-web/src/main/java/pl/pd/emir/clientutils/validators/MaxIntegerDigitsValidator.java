package pl.pd.emir.clientutils.validators;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

public class MaxIntegerDigitsValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(MaxIntegerDigitsValidator.class.getName());
    final ResourceBundle BUNDLE = MultipleFilesResourceBundle.getBundle("messages");
    public int maxIntegerDigits = 10;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (Objects.isNull(value)) {
            return;
        }
        maxIntegerDigits = getAnttribute(component, "maxIntegerDigits") - getAnttribute(component, "maxFractionDigits");
        String temp = (String) value.toString().trim();
        temp = temp.replaceAll("[ \u00a0]+", "");
        temp = temp.replaceAll(",", ".");
        LOG.log(Level.FINE, "MaxIntegerDigitsValidator:validate: {0}", temp);
        if (temp.contains(".")) {
            int indexPoint = temp.indexOf(".");
            if (temp.contains("-")) {
                indexPoint--;
            }
            if (indexPoint > maxIntegerDigits) {
                executeNewThrow();
            }
        } else {
            int lenghtTemp = temp.length();
            if (temp.contains("-")) {
                lenghtTemp--;
            }
            if (lenghtTemp > maxIntegerDigits) {
                executeNewThrow();
            }
        }
    }

    private void executeNewThrow() {
        try {
            BeanHelper.throwValidatorError("validator.tooBigInteger");
        } catch (MissingResourceException ex) {
            BeanHelper.throwValidatorPlainError("Przekroczona liczba cyfr przed przecinkiem", FacesMessage.SEVERITY_ERROR);
        }
    }

    private int getAnttribute(UIComponent component, String name) {
        int ret;
        try {
            ret = Integer.parseInt((String) component.getAttributes().get(name));
        } catch (NullPointerException ex) {
            ret = 10;
        }
        return ret;
    }
}
