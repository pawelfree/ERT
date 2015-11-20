package pl.pd.emir.register;

import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplateControlDate;
import pl.pd.emir.entity.Protection;

@Stateless
public class ProtectionManager extends AbstractManagerTemplateControlDate<Protection> {

    public ProtectionManager() {
        super(Protection.class);
    }
}
