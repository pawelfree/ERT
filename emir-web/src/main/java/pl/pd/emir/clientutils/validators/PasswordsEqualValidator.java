package pl.pd.emir.clientutils.validators;

import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

@FacesValidator("pl.pd.emir.clientutils.validators.PasswordsEqualValidator")
public class PasswordsEqualValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(PasswordsEqualValidator.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            String password = value.toString();

            UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
                    .get("confirmPassword");
            String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                    .toString();

            // Let required="true" do its job.
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                return;
            }

            if (!password.equals(confirmPassword)) {
                uiInputConfirmPassword.setValid(false);
                BeanHelper.throwValidatorError("validator.mustBeSamePassword");
            }

        } catch (NullPointerException ex) {
            LOG.info(ex.getMessage());
            BeanHelper.throwValidatorError("validator.mustBeSamePassword");
        }
    }
}
