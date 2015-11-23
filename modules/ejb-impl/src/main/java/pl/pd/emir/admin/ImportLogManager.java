package pl.pd.emir.admin;

import java.util.List;
import javax.ejb.Stateless;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.ImportFailLog;
import pl.pd.emir.entity.ImportLog;

@Stateless
public class ImportLogManager extends AbstractManagerTemplate<ImportLog> {

    public ImportLogManager() {
        super(ImportLog.class);
    }

    public List<ImportFailLog> getImportFailLog(Long id, int indexStart, int packageSize) {
        return entityManager.createNamedQuery("ImportLog.getFailLogList")
                .setParameter("id", id)
                .setFirstResult(indexStart)
                .setMaxResults(packageSize)
                .getResultList();
    }

}
