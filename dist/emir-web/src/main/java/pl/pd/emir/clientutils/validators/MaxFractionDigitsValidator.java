package pl.pd.emir.clientutils.validators;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

public class MaxFractionDigitsValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(MaxFractionDigitsValidator.class.getName());
    public int maxFractionDigits = 5;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (Objects.isNull(value)) {
            return;
        }
        maxFractionDigits = getAnttribute(component, "maxFractionDigits");
        String temp = (String) value.toString().trim();
        temp = temp.replaceAll(",", ".");
        if (temp.contains(".")) {
            temp = temp.replaceAll("[ \u00a0]+", "");
            int lengthFraction = temp.length() - temp.indexOf(".");
            LOG.log(Level.FINE, "MaxFractionDigitsValidator:lengthFraction: {0}", lengthFraction);
            if (lengthFraction > maxFractionDigits + 1) {
                LOG.fine("MaxFractionDigitsValidator:validatorException start");
                try {
                    BeanHelper.throwValidatorError("validator.maxFraction");
                } catch (MissingResourceException ex) {
                    BeanHelper.throwValidatorPlainError("Przekroczona maksymalna liczba cyfr po przecinku", FacesMessage.SEVERITY_ERROR);
                }
            } else {
                LOG.fine("MaxFractionDigitsValidator:validatorException end");
            }
        }
    }

    private int getAnttribute(UIComponent component, String name) {
        int ret;
        try {
            ret = Integer.parseInt((String) component.getAttributes().get(name));
        } catch (NullPointerException ex) {
            ret = 5;
        }
        return ret;
    }
}
