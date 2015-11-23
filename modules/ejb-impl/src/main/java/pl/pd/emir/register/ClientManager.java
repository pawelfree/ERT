package pl.pd.emir.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.embeddable.Institution;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.enums.EventType;
import org.slf4j.Logger;
import pl.pd.emir.admin.EventLogManager;

@Stateless
public class ClientManager extends AbstractManagerTemplate<Client>  {

    @Inject
    private transient Logger log;

    @EJB
    protected transient EventLogManager eventLogManager;

    public ClientManager() {
        super(Client.class);
    }

    public Client getClientByOrginalId(String orginalId) {
        Query query = entityManager.createNamedQuery("Client.getByOriginalId");
        query.setParameter("originalId", orginalId);
        try {
            return (Client) query.getSingleResult();
        } catch (NoResultException e) {
            log.info(String.format("Client with original ID %s does not exist.", orginalId));
            return null;
        }
    }

    public Client saveByOrginalId(Client client) {
        Client currentClient = getClientByOrginalId(client.getOriginalId());
        if (currentClient != null) {
            client.setId(currentClient.getId());
        }
        return save(client);
    }


    public List<Client> saveAllByOrginalId(List<Client> clientList) {
        List<Client> resultList = new ArrayList<>();
        clientList.stream().forEach((client) -> {
            resultList.add(saveByOrginalId(client));
        });
        return resultList;
    }

    public List<Client> getClientByFilenameAndDate(String filename, Date date) {
        return entityManager.createNamedQuery("Client.importRaport")
                .setParameter("file", filename)
                .getResultList();
    }

    @Override
    public Client save(Client entity) {
        Client mergedEntity;
        if (entity.getId() != null) {
            Client oldEntity = getById(entity.getId());
            checkEntityChanges(entity, oldEntity);
            List<ChangeLog> changeLogs = oldEntity.getChangeLogs(entity);
            mergedEntity = super.save(entity);
            logModificationEvents(changeLogs, entity.getId());
            mergedEntity.setChangeComment("");
        } else {
            entity.setClientVersion(1);
            mergedEntity = super.save(entity);
        }
        return mergedEntity;
    }

    private void logModificationEvents(List<ChangeLog> logs, Long clientId) {
        if (logs != null && logs.size() > 0) {
            eventLogManager.addEventTransactional(EventType.CLIENT_MODIFICATION, clientId, null);
            logs.stream().forEach((log) -> {
                eventLogManager.addEventTransactional(EventType.CLIENT_MODIFICATION_CHANGELOG, clientId, null, log);
            });
        }
    }

    public List<Client> getClientBatchByOrginalId(List<String> originalIdList) {
        return entityManager.createNamedQuery("Client.getClientBatchByOriginalId")
                .setParameter("originalIdList", originalIdList)
                .getResultList();
    }

    private void checkEntityChanges(Client newClient, Client oldClient) {
        if (newClient.getId() != null) {
            Institution oldInstitution = oldClient.getInstitution();
            Institution newInstitution = newClient.getInstitution();
            Integer actualVersion = oldClient.getClientVersion() == null ? 1 : oldClient.getClientVersion();
            boolean isChange = isFieldNotEquals(oldInstitution.getInstitutionData().getInstitutionId(), newInstitution.getInstitutionData().getInstitutionId())
                    || isFieldNotEquals(oldInstitution.getInstitutionData().getInstitutionIdType(), newInstitution.getInstitutionData().getInstitutionIdType())
                    || isFieldNotEquals(oldClient.getContrPartyIndustry(), newClient.getContrPartyIndustry())
                    || isFieldNotEquals(oldClient.getContrPartyType(), newClient.getContrPartyType())
                    || isFieldNotEquals(oldClient.getEog(), newClient.getEog())
                    || isFieldNotEquals(oldClient.getNaturalPerson(), newClient.getNaturalPerson())
                    || isFieldNotEquals(oldClient.getReported(), newClient.getReported())
                    || isFieldNotEquals(oldClient.getContrPartyType(), newClient.getContrPartyType())
                    || isFieldNotEquals(oldClient.getCountryCode(), newClient.getCountryCode())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getPostalCode(), newInstitution.getInstitutionAddr().getPostalCode())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getCity(), newInstitution.getInstitutionAddr().getCity())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getStreetName(), newInstitution.getInstitutionAddr().getStreetName())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getBuildingId(), newInstitution.getInstitutionAddr().getBuildingId())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getPremisesId(), newInstitution.getInstitutionAddr().getPremisesId())
                    || isFieldNotEquals(oldInstitution.getInstitutionAddr().getDetails(), newInstitution.getInstitutionAddr().getDetails());
            if (isChange) {
                newClient.setClientVersion(actualVersion + 1);
            } else {
                newClient.setClientVersion(actualVersion);
            }
            log.info("checkEntityChanges...client: {}, isChange: {}, actualClientVersion: {}", new Object[]{newClient.getOriginalId(), isChange, newClient.getClientVersion()});
        }
    }

    private boolean isFieldNotEquals(Object oldValue, Object newValue) {
        log.info("isFieldEquals: oldValue: {}, newValue: {}", oldValue, newValue);
        return oldValue != null && !oldValue.equals(newValue);
    }

    public List<Client> getUniquenessIdOriginal(final String id) {
        return getEntityManager().createNamedQuery("Client.getByOriginalId")
                .setParameter("originalId", id)
                .getResultList();
    }

    public List<Client> getClientByImportLog(final ImportLog idImportLog) {
        return getEntityManager().createNamedQuery("Client.getClientByImportLog")
                .setParameter("importLog", idImportLog)
                .getResultList();
    }

    public HashMap<String, Client> getAllKeyOriginalId() {
        List<Client> resultAll = getEntityManager().createNamedQuery("Client.getAll")
                .getResultList();
        HashMap<String, Client> result = new HashMap<>();
        resultAll.stream().forEach((client) -> {
            result.put(client.getOriginalId(), client);
        });
        return result;
    }
}
