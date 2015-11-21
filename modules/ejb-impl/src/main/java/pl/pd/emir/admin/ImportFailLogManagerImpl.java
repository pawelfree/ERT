package pl.pd.emir.admin;

import pl.pd.emir.admin.ImportFailLogManager;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.ImportFailLog;

@Stateless
@Local(ImportFailLogManager.class)
public class ImportFailLogManagerImpl extends AbstractManagerTemplate<ImportFailLog> implements ImportFailLogManager {

    public ImportFailLogManagerImpl() {
        super(ImportFailLog.class);
    }
}
