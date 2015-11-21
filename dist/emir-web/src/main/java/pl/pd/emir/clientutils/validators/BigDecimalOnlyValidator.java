package pl.pd.emir.clientutils.validators;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

@FacesValidator("pl.pd.emir.clientutils.validators.BigDecimalOnlyValidator")
public class BigDecimalOnlyValidator implements Validator {

    final ResourceBundle BUNDLE = MultipleFilesResourceBundle.getBundle("messages");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            if (Objects.isNull(value)) {
                return;
            }
            BigDecimal temp = new BigDecimal(value.toString());
        } catch (NumberFormatException ex) {
            FacesMessage msg = new FacesMessage(BUNDLE.getString("validator.mustBigDecimal"), BUNDLE.getString("validator.mustBigDecimal"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
