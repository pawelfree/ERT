package pl.pd.emir.register.bean.validators;

import javax.faces.validator.FacesValidator;

@FacesValidator(value = "pl.pd.emir.clientutils.validators.PositiveNumberValidator")
public class PositiveNumberValidator extends pl.pd.emir.clientutils.validators.PositiveNumberValidator {

}
