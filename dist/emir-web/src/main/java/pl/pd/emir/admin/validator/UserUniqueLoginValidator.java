/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pd.emir.admin.validator;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.client.utils.ValidatorHelper;
import pl.pd.emir.entity.administration.User;

@ManagedBean
@RequestScoped
public class UserUniqueLoginValidator implements Validator {

    @EJB
    private UserManager userManager;

    private boolean isUserRegistered(String login) {
        User usr = userManager.getUserByLogin(login);
        return usr != null;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (isUserRegistered((String) value)) {
            ValidatorHelper.throwValidatorError("admin.user.info.duplicateUsers", value);
        }
    }
}
