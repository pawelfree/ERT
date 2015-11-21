package pl.pd.emir.kdpw.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import pl.pd.emir.commons.CollectionsUtils;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.commons.StringUtil;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.criteria.TransactionToKdpwSC;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.criteria.PagedList;
import pl.pd.emir.entity.Transaction;
import pl.pd.emir.register.TransactionToCancelManager;

@Stateless
@Local(TransactionToCancelManager.class)
public class TransactionToCancelManagerImpl extends AbstractManagerTemplate<Transaction> implements TransactionToCancelManager {

    private static final String FROM_QUERY = " WHERE"
            + " ("
            + " t.processingStatus = pl.pd.emir.enums.ProcessingStatus.CONFIRMED"
            + " OR t.processingStatus = pl.pd.emir.enums.ProcessingStatus.PARTLY_CANCELED"
            + " )"
            + " AND"
            + " (SELECT COUNT(t2.id) FROM Transaction t2 "
            + " WHERE t2.originalId = t.originalId"
            + " AND ("
            + " (t2.transactionDate = t.transactionDate AND t2.extractVersion > t.extractVersion) "
            + " OR t2.transactionDate > t.transactionDate"
            + " ) AND ("
            + " t2.processingStatus = pl.pd.emir.enums.ProcessingStatus.CONFIRMED"
            + " OR"
            + " t2.processingStatus = pl.pd.emir.enums.ProcessingStatus.SENT"
            + " )"
            + ") = 0";

    private static final String TRANSACTION_DATE = "transactionDate";

    private static final String DATA_TYPES = "dataTypes";

    private static final String ORIGINAL_ID = "originalId";

    private static final String FROM_ID = "fromId";

    public TransactionToCancelManagerImpl() {
        super(Transaction.class);
    }

    @Override
    public final PagedList<Transaction> find(final AbstractSearchCriteria abstractCriteria) {
        if (abstractCriteria instanceof TransactionToKdpwSC) {
            final TransactionToKdpwSC criteria = (TransactionToKdpwSC) abstractCriteria;
            final PagedList<Transaction> result = new PagedList<>();
            final StringBuilder queryBuilder = new StringBuilder();

            queryBuilder.append("SELECT t FROM Transaction t").append(FROM_QUERY);

            if (CollectionsUtils.isNotEmpty(criteria.getDataTypeList())) {
                // filtry
                applyFilters(queryBuilder, criteria);

                // sortowanie
                applySortOrder(queryBuilder, criteria);
                Query query = getQuery(queryBuilder, criteria);
                if (StringUtil.isNotEmpty(criteria.getSourceTransId())) {
                    query.setParameter(ORIGINAL_ID, "%" + criteria.getSourceTransId() + "%");
                }
                if (null != criteria.getFromId()) {
                    query.setParameter(FROM_ID, criteria.getFromId());
                }

                // stronicowanie
                if (criteria.getFirstResult() > -1) {
                    query.setFirstResult(criteria.getFirstResult());
                }
                if (criteria.getMaxResults() > -1) {
                    query.setMaxResults(criteria.getMaxResults());
                }

                // rezultat
                result.setData(query.getResultList());
                result.setPageNumber(criteria.getFirstResult());
                result.setRecordCounter(NumberUtils.safeLongToInt(getRecordCount(criteria)));

            } else {
                result.setData(new ArrayList<>());
                result.setRecordCounter(0);
                result.setPageNumber(1);
            }

            return result;
        } else {
            throw new IllegalArgumentException("Criteria have to be " + TransactionToKdpwSC.class);
        }
    }

    @Override
    public long getRecordCount(final AbstractSearchCriteria abstractCriteria) {
        if (abstractCriteria instanceof TransactionToKdpwSC) {
            final TransactionToKdpwSC criteria = (TransactionToKdpwSC) abstractCriteria;

            if (CollectionsUtils.isNotEmpty(criteria.getDataTypeList())) {

                final StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT COUNT(t.id) FROM Transaction t").append(FROM_QUERY);

                // filtry
                applyFilters(queryBuilder, criteria);
                Query query = getQuery(queryBuilder, criteria);
                if (StringUtil.isNotEmpty(criteria.getSourceTransId())) {
                    query.setParameter(ORIGINAL_ID, "%" + criteria.getSourceTransId() + "%");
                }
                if (null != criteria.getFromId()) {
                    query.setParameter(FROM_ID, criteria.getFromId());
                }
                return (Long) query.getSingleResult();
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException("Criteria have to be " + TransactionToKdpwSC.class);
        }
    }

    @Override
    public final Date getMaxDate() {
        Date result = null;
        final Query query = getEntityManager().createQuery(String.format("%s%s",
                "SELECT MAX(t.transactionDate) FROM Transaction t",
                FROM_QUERY));
        try {
            result = (Date) query.getSingleResult();
        } catch (NoResultException nre) {
            // nic nie rob
        }
        return result;
    }

    @Override
    public final Date getMinDate() {
        Date result = null;
        final Query query = getEntityManager().createQuery(String.format("%s%s",
                "SELECT MIN(t.transactionDate) FROM Transaction t",
                FROM_QUERY));
        try {
            result = (Date) query.getSingleResult();
        } catch (NoResultException nre) {
            // nic nie rob
        }
        return result;
    }

    @Override
    public final List<Transaction> findWithoutDeselected(final AbstractSearchCriteria abstractCriteria, final List<Long> ids) {
        if (abstractCriteria instanceof TransactionToKdpwSC) {
            final TransactionToKdpwSC criteria = (TransactionToKdpwSC) abstractCriteria;
            if (CollectionsUtils.isNotEmpty(criteria.getDataTypeList())) {
                final StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT t FROM Transaction t").append(FROM_QUERY);
                if (CollectionsUtils.isNotEmpty(ids)) {
                    queryBuilder.append(" AND t.id NOT IN ").append(StringUtil.generateCollection(ids));
                }

                // filtry
                applyFilters(queryBuilder, criteria);
                Query query = getQuery(queryBuilder, criteria);
                if (StringUtil.isNotEmpty(criteria.getSourceTransId())) {
                    query.setParameter(ORIGINAL_ID, "%" + criteria.getSourceTransId() + "%");
                }
                if (null != criteria.getFromId()) {
                    query.setParameter(FROM_ID, criteria.getFromId());
                }

                return query.getResultList();
            } else {
                return new ArrayList<>();
            }
        } else {
            throw new IllegalArgumentException("Criteria have to be " + TransactionToKdpwSC.class);
        }
    }

    private Query getQuery(final StringBuilder queryBuilder, final TransactionToKdpwSC criteria) {
        final Query query = getEntityManager().createQuery(queryBuilder.toString());
        query.setParameter(TRANSACTION_DATE, criteria.getTransactionDate());
        query.setParameter(DATA_TYPES, criteria.getDataTypeList());
        return query;
    }

    private void applyFilters(final StringBuilder queryBuilder, final TransactionToKdpwSC criteria) {
        queryBuilder.append(" AND t.dataType IN :" + DATA_TYPES);
        queryBuilder.append(" AND t.transactionDate = :" + TRANSACTION_DATE);
        if (null != criteria.getFromId()) {
            queryBuilder.append(" AND t.id > :" + FROM_ID);
        }
        if (StringUtil.isNotEmpty(criteria.getSourceTransId())) {
            queryBuilder.append(" AND t.transactionDetails.sourceTransId LIKE :" + ORIGINAL_ID);
        }
    }

    protected void applySortOrder(final StringBuilder queryBuilder, final TransactionToKdpwSC criteria) {
        if (Objects.nonNull(criteria.getFitrSort().getSortOrder())) {
            switch (criteria.getFitrSort().getSortOrder()) {
                case ASCENDING:
                    queryBuilder.append(" ORDER BY").append(" t.").append(criteria.getFitrSort().getSortField());
                    queryBuilder.append(" ASC");
                    break;
                case DESCENDING:
                    queryBuilder.append(" ORDER BY").append(" t.").append(criteria.getFitrSort().getSortField());
                    queryBuilder.append(" DESC");
                    break;
                default:
                    break;
            }
        }
    }
}
