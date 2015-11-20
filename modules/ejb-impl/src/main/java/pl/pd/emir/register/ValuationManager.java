package pl.pd.emir.register;

import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplateControlDate;
import pl.pd.emir.entity.Valuation;

@Stateless
public class ValuationManager extends AbstractManagerTemplateControlDate<Valuation> {

    public ValuationManager() {
        super(Valuation.class);
    }

}
