package pl.pd.emir.register;

import pl.pd.emir.register.ValuationManager;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplateControlDate;
import pl.pd.emir.entity.Valuation;

@Stateless
@Local(ValuationManager.class)
public class ValuationManagerImpl extends AbstractManagerTemplateControlDate<Valuation> implements ValuationManager {

    public ValuationManagerImpl() {
        super(Valuation.class);
    }

}
