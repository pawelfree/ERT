package pl.pd.emir.register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.DateUtils;
import pl.pd.emir.commons.PropertyUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.AbstractManagerTemplateControlDate;
import pl.pd.emir.dao.utils.AbstractFilterTO;
import pl.pd.emir.dao.utils.DaoUtil;
import pl.pd.emir.dao.utils.FilterDateTO;
import pl.pd.emir.dao.utils.FilterLongTO;
import pl.pd.emir.dao.utils.FilterObjectTO;
import pl.pd.emir.dao.utils.FilterStringTO;
import pl.pd.emir.entity.Client;
import pl.pd.emir.entity.ImportLog;
import pl.pd.emir.entity.Protection;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.entity.Valuation;
import pl.pd.emir.entity.administration.ChangeLog;
import pl.pd.emir.enums.DataType;
import pl.pd.emir.enums.EventType;
import pl.pd.emir.enums.ProcessingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class TransactionManager extends AbstractManagerTemplateControlDate<Transaction> {

    //TODO repair
    //@Inject
    private transient Logger LOGGER = LoggerFactory.getLogger(TransactionManager.class);

    private static final String PROPERTY_FILE = "history-details.properties";

    private static final Properties PROPERTIES = PropertyUtils.getProperties(TransactionManager.class, PROPERTY_FILE);

    @EJB
    private transient ClientManager clientService;

    public TransactionManager() {
        super(Transaction.class);
    }

    @Override
    public Transaction save(final Transaction transaction) {
        return this.save(transaction, false, false);
    }

    public List<Transaction> saveAll(final List<Transaction> transactionList) {
        List<Transaction> resultList = new ArrayList<>();
        transactionList.stream().forEach((transaction) -> {
            resultList.add(save(transaction));
        });
        return resultList;
    }

    public Transaction update(Transaction transaction) {
        setClientVersion(transaction);
        processEmptyObjects(transaction);
        return super.save(transaction);
    }

    public Transaction updateOnlyMerge(Transaction transaction) {
        return getEntityManager().merge(transaction);
    }

    public Transaction getNewestVersion(Date transactionDate, String originalId) {
        try {
            return (Transaction) getEntityManager().createNamedQuery("Transaction.getByNewestVersion")
                    .setParameter("transactionDate", transactionDate)
                    .setParameter("originalId", originalId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.debug("No transaction found with originalId = " + originalId + ", transactionDate = " + transactionDate);
            return null;
        }
    }

    public final Transaction save(final Transaction transaction, final boolean isMutation, final boolean isValuation) {
        setClientVersion(transaction);
        LOGGER.debug("Saving transaction. mutation - " + isMutation + ", valuation - " + isValuation
                + ". Transaction date - " + transaction.getTransactionDate() + ", id - " + transaction.getOriginalId());

        if (isMutation) {
            final Transaction newest = getNewestVersion(transaction.getTransactionDate(), transaction.getOriginalId());
            if (Objects.isNull(newest)) {
                transaction.setNewestVersion(Boolean.TRUE);
                transaction.setExtractVersion(1); // nowa transakcja
            } else {
                newest.setNewestVersion(Boolean.FALSE);
                transaction.setNewestVersion(Boolean.TRUE);
                transaction.setExtractVersion(newest.getExtractVersion() + 1);
            }
        } else if (Objects.isNull(transaction.getId())) {
            LOGGER.debug("not mutation and transaction.id = null");
            final Transaction newest = getNewestVersion(transaction.getTransactionDate(), transaction.getOriginalId());
            if (Objects.isNull(newest)) {
                transaction.setNewestVersion(Boolean.TRUE);
                transaction.setExtractVersion(1); // nowa transakcja
            } else if (ProcessingStatus.NEW.equals(newest.getProcessingStatus())) {
                transaction.setNewestVersion(Boolean.TRUE);
                transaction.setExtractVersion(newest.getExtractVersion());
                deleteTransaction(newest);
            } else {
                newest.setNewestVersion(Boolean.FALSE);
                transaction.setNewestVersion(Boolean.TRUE);
                transaction.setExtractVersion(newest.getExtractVersion() + 1);
            }
        }
        return saveTransaction(transaction, isMutation, isValuation);
    }

    protected void deleteTransaction(Transaction transaction) {
        super.delete(transaction);
    }

    public Transaction saveTransaction(Transaction entity, boolean isMutation, boolean isValuation) {
        boolean logAddTransactionFlag = false;

        LOGGER.debug("Save transaction [original id = " + entity.getOriginalId()
                + "],[date = " + entity.getTransactionDate() + "],"
                + " [extractVersion = " + entity.getExtractVersion() + "],"
                + " [newestVersion = " + entity.getNewestVersion() + "]");

        processEmptyObjects(entity);

        if (entity.getId() == null) {
            logAddTransactionFlag = true; // ustawienie flagi dotyczącej zapisu informacji o dodaniu transakcji
        } else {
            logChanges(entity); // zapis do dziennika zdarzen informacji o zmianach transakcji
        }

        if (entity.getClient() != null && entity.getClient().getOriginalId() != null) {
            final Client dbClient = clientService.getClientByOrginalId(entity.getClient().getOriginalId());
            entity.setClient(dbClient);
            setClientVersion(entity);
        }

        if (entity.getProtection() != null) {
            Protection protection = entity.getProtection();
            if (protection.getId() != null) {
                entityManager.merge(protection);
            } else {
                entityManager.persist(protection);
                entity.getProtection().setId(protection.getId());
            }
        }

        if (entity.getValuation() != null) {
            Valuation valuation = entity.getValuation();
            if (valuation.getId() != null) {
                entityManager.merge(valuation);
            } else {
                entityManager.persist(valuation);
                entity.getValuation().setId(valuation.getId());
            }
        }

        if (entity.getId() != null) {
            LOGGER.debug("+++ MERGE transaction +++");
            entity = entityManager.merge(entity);
        } else {
            if (entity.getClient() != null) {
                entity.setClientVersion(entity.getClient().getClientVersion());
            }
            LOGGER.debug("+++ PERSIST transaction +++");
            entityManager.persist(entity);
        }

        if (!context.getRollbackOnly()) {
            // flush() nie sprawdza czy transackja jest aktywna po tym jak wolene jest context.setRollbackOnly() i kazda operacja na encji powoduje exception
            entityManager.flush();
        }
        LOGGER.debug("Save eventLog transaction");

        checkAddLogTransaction(logAddTransactionFlag, entity, isMutation, isValuation);
        LOGGER.debug("End of saveTransaction");
        return entity;
    }

    public Boolean isExistsItemYesterday(Long itemId, String innerId) {
        List<AbstractFilterTO> filtersTemp = new ArrayList<>();
        filtersTemp.add(FilterStringTO.valueOf("", "originalId", "=", innerId));
        return DaoUtil.isExistsItemYesterday(this, itemId, filtersTemp);
    }

    public Boolean isExistsItem(Long itemId, String innerId, Date transactionDate, Integer extractVersion) {
        List<AbstractFilterTO> filtersTemp = new ArrayList<>();
        filtersTemp.add(FilterStringTO.valueOf("", "originalId", "=", innerId));
        filtersTemp.add(FilterDateTO.valueOf("", "transactionDate", "=", transactionDate));
        filtersTemp.add(FilterObjectTO.valueOf("", "extractVersion", ">", extractVersion));
        filtersTemp.add(FilterLongTO.valueOf("", "id", "!=", itemId));
        return DaoUtil.isExistsItem(this, filtersTemp);
    }

    public boolean isYoungerMutation(String innerId, Date transactionDate, Integer extractVersion) {
        boolean result = false;
        final Query query = entityManager.createNamedQuery("Transaction.getNewerMutationsCount")
                .setParameter("originalId", innerId)
                .setParameter("transactionDate", transactionDate)
                .setParameter("extractVersion", extractVersion)
                .setParameter("processingStatus", ProcessingStatus.CANCELED);
        try {
            result = ((Long) query.getSingleResult()) > 0L;
        } catch (NoResultException nre) {
            LOGGER.info("getNewerMutationsCount: no results.");
        }
        return result;
    }

    public boolean isNewerVersion(Transaction transaction) {
        boolean result = false;
        final Query query = entityManager.createNamedQuery("Transaction.getNewerVersionCount")
                .setParameter("originalId", transaction.getOriginalId())
                .setParameter("transactionDate", transaction.getTransactionDate())
                .setParameter("extractVersion", transaction.getExtractVersion())
                .setParameter("processingStatus", ProcessingStatus.CANCELED);
        try {
            Long count = (Long) query.getSingleResult();
            result = count > 0;
        } catch (NoResultException nre) {
            LOGGER.info("getNewerVersionCount: no results.");
        }
        return result;
    }

    public Transaction findNewestConfirmed(String sourceTransId, Long currentId) {
        Transaction result = null;
        final Query query = entityManager.createNamedQuery("Transaction.findNewestConfirmed")
                .setParameter("sourceTransId", sourceTransId)
                .setParameter("currentId", currentId)
                .setMaxResults(1);
        try {
            result = (Transaction) query.getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.info("findNewestConfirmed: no other entity with sourceId = {} was founded. ", sourceTransId);
        }
        return result;
    }

    public Long countDistinctTransactionForDay(Long idClient, String instrumentType, Date day) {
        return (Long) entityManager.createNamedQuery("Transaction.countDistinctTransactionForDay")
                .setParameter("idClient", idClient)
                .setParameter("instrumentType", instrumentType)
                .setParameter("day", day).getSingleResult();
    }

    public List<Object[]> sumAmountTransactionForDay(Long idClient, String instrumentType, Date day) {
        return entityManager.createNamedQuery("Transaction.sumAmountNewestTransactionForDay")
                .setParameter("idClient", idClient)
                .setParameter("instrumentType", instrumentType)
                .setParameter("day", day).getResultList();
    }

    public List<Transaction> getByDateAndOriginalId(String originalId, Date transactionDate) {
        return entityManager.createNamedQuery("Transaction.getByDateAndOriginalId")
                .setParameter("originalId", originalId)
                .setParameter("transactionDateFrom", DateUtils.getDayBegin(transactionDate))
                .setParameter("transactionDateTo", DateUtils.getDayEnd(transactionDate))
                .getResultList();
    }

    public List<Transaction> getByDate(Date transactionDate, Integer startIndex, Integer resultSize) {
        Query query = entityManager.createNamedQuery("Transaction.getByDate")
                .setParameter("transactionDateFrom", DateUtils.getDayBegin(transactionDate))
                .setParameter("transactionDateTo", DateUtils.getDayEnd(transactionDate));

        if (startIndex != null) {
            query = query.setFirstResult(startIndex);
        }
        if (resultSize != null) {
            query = query.setMaxResults(resultSize);
        }
        return query.getResultList();
    }

    public List<Transaction> getTransactionByImportLog(final ImportLog idImportLog) {
        return entityManager.createNamedQuery("Transaction.getTransactionByImportLog")
                .setParameter("importLog", idImportLog)
                .getResultList();
    }

    public Long getCountTransactionByClient(Client client) {
        Long count = null;
        try {
            count = (Long) entityManager.createNamedQuery("Transaction.getCountTransactionByClient")
                    .setParameter("client", client).getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.info("findDataReport: no other entity with clientID= {} was founded. ", client.getId());
        }
        return count;
    }

    public List<Transaction> getTransactionByFilenameAndDate(String filename, Date date) {
        return entityManager.createNamedQuery("Transaction.importRaport")
                .setParameter("file", filename)
                .setParameter("date", date)
                .getResultList();
    }

    private void logModificationEvents(List<ChangeLog> logs, Transaction trans) {
        if (logs != null && logs.size() > 0) {
            logManager.addEventTransactional(EventType.TRANSACTION_MODIFICATION, trans.getId(),
                    PropertyUtils.getString(PROPERTIES, trans.getValidationStatus().getMsgKey()));
            logs.stream().forEach((log) -> {
                logManager.addEventTransactional(EventType.TRANSACTION_MODIFICATION_CHANGELOG, trans.getId(), null, log);
            });
        }
    }

    public final Transaction findOtherProcessingNew(final Long currentId, final String sourceTransId, final Date maxDate) {
        final Query query = getEntityManager().createNamedQuery("Transaction.findOtherProcessingNew");
        query.setParameter("currentId", currentId);
        query.setParameter("sourceTransId", sourceTransId);
        query.setParameter("transactionDate", maxDate);
        return getSingle(query);
    }

    public final Transaction findNewestOtherVersion(final Long currentId, final String sourceTransId,
            final Date maxDate, final ProcessingStatus... statuses) {
        final Query query = getEntityManager().createQuery("SELECT t FROM Transaction t"
                + " WHERE t.id != :currentId"
                + " AND t.transactionDetails.sourceTransId = :sourceTransId"
                + " AND t.processingStatus IN " + StringUtil.generateCollection(Arrays.asList(statuses))
                + " AND t.transactionDate <= :maxDate"
                + " ORDER BY t.transactionDate DESC, t.extractVersion DESC")
                .setParameter("currentId", currentId)
                .setParameter("sourceTransId", sourceTransId)
                .setParameter("maxDate", maxDate)
                .setMaxResults(1);
        return getSingle(query);
    }

    public final List<Transaction> getByOriginalId(final String originalId) {
        return getEntityManager().createNamedQuery("Transaction.getByOriginalId")
                .setParameter("originalId", originalId)
                .getResultList();
    }

    public final Transaction getByOriginalIdMaxVersion(final String originalId) {
        final Query query = getEntityManager().createNamedQuery("Transaction.getByOriginalIdMaxVersion").setParameter("originalId", originalId);
        Transaction result = null;
        try {
            result = (Transaction) query.getSingleResult();
        } catch (NoResultException nre) {
            // do nothing
        } catch (NonUniqueResultException nure) {
            throw new IllegalStateException("More than one transaction version by id: " + originalId);
        }
        return result;
    }

    public final Transaction getBySourceNrRefMaxVersion(final String sourceNrRef) {
        final Query query = getEntityManager().createNamedQuery("Transaction.getBySourceNrRefMaxVersion").setParameter("sourceNrRef", sourceNrRef);
        Transaction result = null;
        try {
            result = (Transaction) query.getSingleResult();
        } catch (NoResultException nre) {
            // do nothing
        } catch (NonUniqueResultException nure) {
            throw new IllegalStateException("More than one transaction version by id: " + sourceNrRef);
        }
        return result;
    }

    public Transaction getSentByOriginalIdAndDate(final String originalId, final Date transactionDate) {
        final Query query = getEntityManager().createNamedQuery("Transaction.getSentByOriginalIdAndDate")
                .setParameter("originalId", originalId)
                .setParameter("transactionDate", transactionDate);
        Transaction result = null;
        try {
            result = (Transaction) query.getSingleResult();
        } catch (NoResultException nre) {
            // do nothing
        } catch (NonUniqueResultException nure) {
            throw new IllegalStateException("More than one transaction version in status: SENT");
        }
        return result;
    }

    public Transaction findForVersion(final String originalId, final Date transactionDate,
            final DataType dataType, final ProcessingStatus... statuses) {
        final String queryString = "SELECT t FROM Transaction t"
                + " WHERE t.originalId = :originalId"
                + " AND t.transactionDate = :transactionDate"
                + " AND t.dataType = :dataType"
                + " AND t.processingStatus IN " + StringUtil.generateCollection(Arrays.asList(statuses));
        final Query query = getEntityManager().createQuery(queryString)
                .setParameter("originalId", originalId)
                .setParameter("transactionDate", transactionDate)
                .setParameter("dataType", dataType);
        return getSingle(query);
    }

    public Transaction findOtherVersion(final Long currentId, final String originalId, final Date transactionDate,
            final DataType dataType, final ProcessingStatus... statuses) {
        final String queryString = "SELECT t FROM Transaction t"
                + " WHERE t.id != :currentId"
                + " AND t.originalId = :originalId"
                + " AND t.transactionDate = :transactionDate"
                + " AND t.dataType = :dataType"
                + " AND t.processingStatus IN " + StringUtil.generateCollection(Arrays.asList(statuses));
        final Query query = getEntityManager().createQuery(queryString)
                .setParameter("currentId", currentId)
                .setParameter("originalId", originalId)
                .setParameter("transactionDate", transactionDate)
                .setParameter("dataType", dataType);
        return getSingle(query);
    }

    public List<Transaction> getUniquenessIdOriginal(final String originalId) {
        return getEntityManager().createNamedQuery("Transaction.getByOriginalId")
                .setParameter("originalId", originalId)
                .getResultList();
    }

    public Integer getNewestVersionTransaction(final String originalId, final Date transactionDate) {
        return (Integer) getEntityManager().createNamedQuery("Transaction.getNewestVersionTransaction")
                .setParameter("originalId", originalId)
                .setParameter("transactionDate", transactionDate)
                .getSingleResult();
    }

    public List<Object[]> isPossibilityDeleteTransaction(final String originalId) {
        return entityManager.createNamedQuery("Transaction.isPossibilityDeleteTransaction")
                .setParameter("originalId", originalId)
                .getResultList();
    }

    private Transaction getSingle(final Query query) {
        final List<Transaction> list = query.getResultList();
        if (CollectionsUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    protected void processEmptyObjects(final Transaction transaction) {
        if (Objects.nonNull(transaction.getValuation()) && transaction.getValuation().isEmpty()) {
            LOGGER.info("Transaction valuation is EMPTY. Set NULL on it.");
            transaction.setValuation(null);
        }
        if (Objects.nonNull(transaction.getProtection()) && transaction.getProtection().isEmpty()) {
            LOGGER.info("Transaction protection is EMPTY. Set NULL on it.");
            transaction.setProtection(null);
        }
    }

    public boolean isMutation(Transaction transaction) {
        Integer count = (Integer) getEntityManager().createNamedQuery("Transaction.getNewestVersionTransaction")
                .setParameter("originalId", transaction.getOriginalId())
                .setParameter("transactionDate", transaction.getTransactionDate())
                .getSingleResult();
        return count == null ? false : count > 0;
    }

    protected void superDelete(Transaction transaction) {
        super.delete(transaction);
    }

    @Override
    public final void delete(final Transaction entity) {
        final boolean isNewest = entity.isNewestExtractVersion();
        super.delete(entity);
        if (isNewest) {
            try {
                final Transaction newest = (Transaction) getEntityManager().createNamedQuery("Transaction.getByMaxVersion")
                        .setParameter("transactionDate", entity.getTransactionDate())
                        .setParameter("originalId", entity.getOriginalId())
                        .getSingleResult();
                newest.setNewestVersion(Boolean.TRUE);
            } catch (NoResultException nre) {
            }
        }
    }

    private void setClientVersion(Transaction transaction) {
        if (transaction.getClient() != null) {
            transaction.setClientVersion(transaction.getClient().getClientVersion());
        }
    }

    private void checkAddLogTransaction(boolean start, final Transaction newEntity, final boolean isMutation, final boolean isValuation) {
        if (start) {
            logManager.addEventTransactional(
                    isMutation ? newEntity.getAddMutationEventType()
                            : (isValuation ? newEntity.getAddValuationEventType()
                                    : newEntity.getInsertEventType()),
                    newEntity.getId(),
                    PropertyUtils.getString(PROPERTIES, newEntity.getValidationStatus().getMsgKey()));
            if (newEntity.getProtection() != null) {
                logManager.addEventTransactional(newEntity.getProtection().getInsertEventType(),
                        newEntity.getProtection().getId(),
                        PropertyUtils.getString(PROPERTIES, newEntity.getValidationStatus().getMsgKey()));
            }
            if (newEntity.getValuation() != null) {
                logManager.addEventTransactional(newEntity.getValuation().getInsertEventType(),
                        newEntity.getValuation().getId(),
                        PropertyUtils.getString(PROPERTIES, newEntity.getValidationStatus().getMsgKey()));
            }
        }
    }

    private void logChanges(final Transaction newEntity) {
        if (null != newEntity.getId()) {
            final Transaction oldEntity = getById(newEntity.getId());
            List<ChangeLog> changeLogs = oldEntity.getChangeLogs(newEntity);
            logModificationEvents(changeLogs, newEntity);
        }
    }

    public List<Transaction> findProcessedByKDPW(String tradIdId, Date eligDate) {
        return getEntityManager().createNamedQuery("Transaction.findProcessedByKDPW")
                .setParameter("tradeIdId", tradIdId)
                .setParameter("transactionDate", eligDate)
                .getResultList();
    }

    public boolean checkForStatusTr(Transaction transaction, Date reportingDate) {
        List<ProcessingStatus> statusList = new ArrayList<>();
        statusList.add(ProcessingStatus.REJECTED);
        statusList.add(ProcessingStatus.CANCELED);
        statusList.add(ProcessingStatus.CANCELLATION_SENT);

        Query query = getEntityManager().createNamedQuery("Transaction.getForStatusTr")
                //.setParameter("id", transaction.getId())
                .setParameter("originalId", transaction.getOriginalId())
                .setParameter("reportingDate", reportingDate)
                .setParameter("statusList", statusList);

        Long count;

        try {
            count = (Long) query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.info(String.format("checkForStatusTr: no entity with id != %s, originalId = %s, reportingDate <= %s was founded.",
                    transaction.getId(), transaction.getOriginalId(), reportingDate));
            return false;
        }

        return count > 0L;
    }

    public Transaction findNewestTransaction(String tradIdId, Date eligDate, Date eligDate1) {
        try {
            Query query = getEntityManager().createNamedQuery("Transaction.findNewestVersion")
                    .setParameter("tradeIdId", tradIdId)
                    .setParameter("transactionDate", eligDate)
                    .setParameter("transactionDate1", eligDate1).setMaxResults(1);

            return (Transaction) query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.debug("findNewestTransaction no result");
        }
        return null;
    }

    public Long getTransactionsCountForADay(Date transactionDate) {
        return (Long) entityManager.createNamedQuery("Transaction.countImported")
                .setParameter("transactionDate", transactionDate)
                .getSingleResult();
    }

    public Long getNewTransactionsCountForADay(Date transactionDate) {
        return (Long) entityManager.createNamedQuery("Transaction.countImportedNew")
                .setParameter("transactionDate", transactionDate)
                .getSingleResult();
    }

    public Long getMaturedTransactionsCountForADay(Date transactionDate) {
        return (Long) entityManager.createNamedQuery("Transaction.countImportedMature")
                .setParameter("transactionDate", transactionDate)
                .getSingleResult();
    }

    public Long getKdpwReportsCountForADay(Date transactionDate) {
        Long count = (Long) entityManager.createNamedQuery("Transaction.countKdpwClientReports")
                .setParameter("transactionDate", transactionDate).getSingleResult();
        count += (Long) entityManager.createNamedQuery("Transaction.countKdpwBankReports")
                .setParameter("transactionDate", transactionDate).getSingleResult();
        return count;
    }
}
