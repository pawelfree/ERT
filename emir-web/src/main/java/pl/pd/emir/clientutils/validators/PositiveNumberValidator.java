package pl.pd.emir.clientutils.validators;

import java.math.BigDecimal;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

public class PositiveNumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            if (Objects.isNull(value)) {
                return;
            }
            BigDecimal temp = new BigDecimal(value.toString());
            if (temp.compareTo(BigDecimal.ZERO) < 0) {
                BeanHelper.throwValidatorError("validator.positiveNumber");
            }
        } catch (NumberFormatException ex) {
            BeanHelper.throwValidatorError("validator.positiveNumber");
        }
    }
}
