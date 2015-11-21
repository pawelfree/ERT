package pl.pd.emir.admin;

import pl.pd.emir.admin.ImportLogManager;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;

@Stateless
@Local(ImportLogManager.class)
public class ImportLogManagerImpl extends AbstractManagerTemplate<ImportLog> implements ImportLogManager {

    public ImportLogManagerImpl() {
        super(ImportLog.class);
    }

    @Override
    public List<ImportFailLog> getImportFailLog(Long id, int indexStart, int packageSize) {
        return entityManager.createNamedQuery("ImportLog.getFailLogList")
                .setParameter("id", id)
                .setFirstResult(indexStart)
                .setMaxResults(packageSize)
                .getResultList();
    }

}
