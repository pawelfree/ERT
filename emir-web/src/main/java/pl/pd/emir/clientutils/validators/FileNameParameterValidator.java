package pl.pd.emir.clientutils.validators;

import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;

@FacesValidator("pl.pd.emir.clientutils.validators.FileNameParameterValidator")
public class FileNameParameterValidator implements Validator {
    //„plik danych”+RR-MM-DD hh:mm:ss

    private static final String FILENAME_PATTERN = "\".*?\"\\+[0-9]{2}-[0-1][0-9]-[0-3][0-9] [0-2][0-4]:[0-6][0-9]:[0-6][0-9]";
    private final Pattern pattern;
    private Matcher matcher;

    public FileNameParameterValidator() {
        pattern = Pattern.compile(FILENAME_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            executeNewThrow();

        }
    }

    private void executeNewThrow() {
        try {
            BeanHelper.throwValidatorError("validator.notMatchFileName");
        } catch (MissingResourceException ex) {
            BeanHelper.throwValidatorPlainError("Niepoprawny format nazwy pliku", FacesMessage.SEVERITY_ERROR);
        }
    }
}
