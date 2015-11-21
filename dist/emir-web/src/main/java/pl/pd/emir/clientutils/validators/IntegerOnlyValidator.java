package pl.pd.emir.clientutils.validators;

import java.util.MissingResourceException;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

@FacesValidator("pl.pd.emir.clientutils.validators.IntegerOnlyValidator")
public class IntegerOnlyValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (Objects.isNull(value)) {
            return;
        }
        String temp = (String) value.toString();
        if (!temp.matches("\\d*")) {
            try {
                BeanHelper.throwValidatorError("validator.mustInteger");
            } catch (MissingResourceException ex) {
                BeanHelper.throwValidatorPlainError("Wartość w polu musi być liczbą całkowitą", FacesMessage.SEVERITY_ERROR);
            }
        }
    }
}
