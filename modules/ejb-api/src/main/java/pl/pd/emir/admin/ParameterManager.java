package pl.pd.emir.admin;

import pl.pd.emir.entity.administration.Parameter;
import pl.pd.emir.enums.ParameterKey;
import pl.pd.emir.manager.GenericManager;

public interface ParameterManager extends GenericManager<Parameter> {

    Parameter get(ParameterKey key);

    String getValue(ParameterKey key);

}
