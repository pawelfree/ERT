package pl.pd.emir.register;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.manager.GenericManager;

public interface ClientManager extends GenericManager<Client> {

//    Client getFirstOrNewClientByOrginalId(String orginalId);
    Client getClientByOrginalId(String orginalId);

    Client saveByOrginalId(Client client);

    List<Client> saveAllByOrginalId(List<Client> clientList);

    HashMap<String, Client> getAllKeyOriginalId();

    List<Client> getClientByFilenameAndDate(String filename, Date date);

    List<Client> getClientBatchByOrginalId(List<String> originalIdList);

    List<Client> getUniquenessIdOriginal(final String id);

    List<Client> getClientByImportLog(final ImportLog idImportLog);
}
