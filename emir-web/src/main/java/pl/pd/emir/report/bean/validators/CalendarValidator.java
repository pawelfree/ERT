package pl.pd.emir.report.bean.validators;

import java.util.Objects;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

@FacesValidator("pl.pd.emir.report.bean.validators.CalendarValidator")
public class CalendarValidator implements Validator {

    final ResourceBundle BUNDLE = MultipleFilesResourceBundle.getBundle("messages");

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (Objects.isNull(o)) {
            FacesMessage msg = new FacesMessage(BUNDLE.getString("validator.calendarNotNull"), BUNDLE.getString("validator.calendarNotNull"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
