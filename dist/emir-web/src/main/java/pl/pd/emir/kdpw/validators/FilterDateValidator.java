package pl.pd.emir.kdpw.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

@FacesValidator("pl.pd.emir.kdpw.validators.FilterDateValidator")
public class FilterDateValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            BeanHelper.throwValidatorError("transaction.kdpw.sc.transactionDate.validatorMessage");
        }
    }
}
