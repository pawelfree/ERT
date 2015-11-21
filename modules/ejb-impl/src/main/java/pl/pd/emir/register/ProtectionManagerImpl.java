package pl.pd.emir.register;

import pl.pd.emir.register.ProtectionManager;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplateControlDate;
import pl.pd.emir.entity.Protection;

@Stateless
@Local(ProtectionManager.class)
public class ProtectionManagerImpl extends AbstractManagerTemplateControlDate<Protection> implements ProtectionManager {

    public ProtectionManagerImpl() {
        super(Protection.class);
    }
}
