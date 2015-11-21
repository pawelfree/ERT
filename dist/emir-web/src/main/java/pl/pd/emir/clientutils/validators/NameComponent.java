package pl.pd.emir.clientutils.validators;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("pl.pd.emir.clientutils.validators.NameComponent")
public class NameComponent implements Validator {

    private static final Logger LOG = Logger.getLogger(NameComponent.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOG.log(Level.INFO, "ID:{0}", component.getId());
        LOG.log(Level.INFO, "PARENT:{0}", component.getParent());
        LOG.log(Level.INFO, "CHILDREN:{0}", component.getChildren());
        LOG.log(Level.INFO, "DAMILY:{0}", component.getFamily());
        LOG.log(Level.INFO, "ANNTRIBUTES:{0}", component.getAttributes());
        LOG.log(Level.INFO, "CLIENTID:{0}", component.getClientId());
    }
}
