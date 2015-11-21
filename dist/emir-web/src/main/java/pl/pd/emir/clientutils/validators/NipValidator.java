/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pd.emir.clientutils.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pl.pd.emir.bean.BeanHelper;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.ValidationUtils;

@FacesValidator("pl.pd.emir.clientutils.validators.NipValidator")
public class NipValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(StringUtil.isNotEmpty((String) value) && !ValidationUtils.validNIP((String)value)){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, BeanHelper.getMessage("javax.faces.validator.nipValidator.error"), null);
            throw new ValidatorException(msg);
        }
    }
}
