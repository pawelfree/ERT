package pl.pd.emir.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.commons.NumberUtils;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.criteria.PagedList;
import pl.pd.emir.dao.utils.DaoUtil;
import static pl.pd.emir.dao.utils.DaoUtil.FIELD_DELIMITER;
import pl.pd.emir.dao.utils.FilterSortTO;
import pl.pd.emir.manager.GenericManager;

public abstract class AbstractManagerTemplate<E extends Identifiable<Long>> implements GenericManager<E> {

    @PersistenceContext(unitName = "emir2")
    protected EntityManager entityManager;

    @Resource
    protected SessionContext context;

    protected Class<E> persistentClass;

    private static final Map<String, Object> findRefreshProperties = new HashMap<>();

    public AbstractManagerTemplate(Class<E> clazz) {
        persistentClass = clazz;
        findRefreshProperties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); // QueryHints.CACHE_RETRIEVE_MODE
        findRefreshProperties.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); // QueryHints.CACHE_STORE_MODE
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public E getById(Long id) {
        return entityManager.find(this.persistentClass, id, findRefreshProperties);
    }

    @Override
    public void deleteById(Long id) {
        E entity = entityManager.find(this.persistentClass, id);
        if (entity == null) {
            return;
        }
        delete(entity);
    }

    @Override
    public void delete(E entity) {
        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }
        entityManager.remove(entity);
    }

    @Override
    public PagedList<E> find(final AbstractSearchCriteria criteria) {
        final EntityManager eManager = getEntityManager();
        final DaoUtil<E> daoUtils = new DaoUtil<>();
        final CriteriaQuery query = daoUtils.createWhere(criteria.getFitrSort(), eManager.getCriteriaBuilder(),
                this.persistentClass, getAdditionalPredicates());
        query.distinct(criteria.isDistinct());
        final Query findQuery = eManager.createQuery(query);

        if (criteria.getFirstResult() > -1) {
            findQuery.setFirstResult(criteria.getFirstResult());
        }
        if (criteria.getMaxResults() > -1) {
            findQuery.setMaxResults(criteria.getMaxResults());
        }

        if (Objects.nonNull(criteria.getHintName()) && Objects.nonNull(criteria.getHintValue())) {
            findQuery.setHint(criteria.getHintName(), criteria.getHintValue());
        }

        final List resultList = findQuery.getResultList();
        final PagedList<E> result = new PagedList<>();
        result.setData(resultList);
        result.setPageNumber(criteria.getFirstResult());

        result.setRecordCounter(NumberUtils.safeLongToInt(getRecordCount(criteria)));
        return result;
    }

    @Override
    public long getRecordCount(final AbstractSearchCriteria criteria) {
        final EntityManager eManager = getEntityManager();
        final DaoUtil<E> daoUtils = new DaoUtil<>();
        final CriteriaQuery query = daoUtils.createCount(criteria.getFitrSort(), eManager.getCriteriaBuilder(),
                this.persistentClass, getAdditionalPredicates());
        query.distinct(criteria.isDistinct());
        TypedQuery createQuery = eManager.createQuery(query);
        if (Objects.nonNull(criteria.getHintName()) && Objects.nonNull(criteria.getHintValue())) {
            createQuery.setHint(criteria.getHintName(), criteria.getHintValue());
        }
        return (Long) createQuery.getSingleResult();
    }

    public List<Predicate> getAdditionalPredicates() {
        List<Predicate> result = new ArrayList<>();
        return result;
    }

    @Override
    public E refresh(E entity) {
        if (getEntityManager().contains(entity)) {
            entityManager.refresh(entity);
            return entity;
        } else {
            Object id = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
            return entityManager.find(this.persistentClass, id, findRefreshProperties);
        }
    }

    @Override
    public E save(final E entity) {
        E encja = entityManager.merge(entity);
        if (!context.getRollbackOnly()) {
            // flush() nie sprawdza czy transackja jest aktywna po tym jak wolene jest context.setRollbackOnly() i kazda operacja na encji powoduje exception
            entityManager.flush();
        }
        return encja;
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.persistentClass));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<E> findByIds(AbstractSearchCriteria criteria, List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        EntityManager eManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
        final CriteriaQuery criteriaQuery = createWhereIdsIn(criteria.getFitrSort(), criteriaBuilder, persistentClass, ids);
        Query findQuery = eManager.createQuery(criteriaQuery);
        if (criteria.getFirstResult() > -1) {
            findQuery.setFirstResult(criteria.getFirstResult());
        }
        if (criteria.getMaxResults() > -1) {
            findQuery.setMaxResults(criteria.getMaxResults());
        }
        return findQuery.getResultList();
    }

    @Override
    public List<E> findAll(final AbstractSearchCriteria criteria, int startIndex, final int packageSize) {
        EntityManager eManager = getEntityManager();
        DaoUtil<E> daoUtils = new DaoUtil<>();
        final CriteriaQuery criteriaQuery = daoUtils.createWhere(criteria.getFitrSort(), eManager.getCriteriaBuilder(),
                this.persistentClass, getAdditionalPredicates());

        criteriaQuery.distinct(criteria.isDistinct());
        return eManager.createQuery(criteriaQuery).setFirstResult(startIndex).setMaxResults(packageSize).getResultList();
    }

    @Override
    public List<E> findAll(final AbstractSearchCriteria criteria) {
        EntityManager eManager = getEntityManager();
        DaoUtil<E> daoUtils = new DaoUtil<>();
        final CriteriaQuery criteriaQuery = daoUtils.createWhere(criteria.getFitrSort(), eManager.getCriteriaBuilder(),
                this.persistentClass, getAdditionalPredicates());
        criteriaQuery.distinct(criteria.isDistinct());
        return eManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<E> findWithoutDeselected(AbstractSearchCriteria criteria, List<Long> ids) {
        EntityManager eManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = eManager.getCriteriaBuilder();
        final CriteriaQuery criteriaQuery = createWhereIdsNotIn(criteria.getFitrSort(), criteriaBuilder, this.persistentClass, ids);
        criteriaQuery.distinct(criteria.isDistinct());
        Query findQuery = eManager.createQuery(criteriaQuery);
        if (criteria.getFirstResult() > -1) {
            findQuery.setFirstResult(criteria.getFirstResult());
        }
        if (criteria.getMaxResults() > -1) {
            findQuery.setMaxResults(criteria.getMaxResults());
        }
        return findQuery.getResultList();
    }

    protected CriteriaQuery createWhereIdsNotIn(FilterSortTO filters, CriteriaBuilder criteriaBuilder, Class daoClass, List<Long> ids) {
        DaoUtil<E> daoUtils = new DaoUtil<>();
        CriteriaQuery<Class<E>> criteriaQuery = (CriteriaQuery<Class<E>>) criteriaBuilder.createQuery(daoClass);
        Path<E> from = (Path<E>) criteriaQuery.from(daoClass);
        Predicate[] currentPredicateArray = daoUtils.getPredicateListExt(filters.getFilters(), criteriaBuilder, daoClass, from, getAdditionalPredicates());
        Predicate[] newPredicateArray;
        if (!ids.isEmpty()) {
            newPredicateArray = new Predicate[currentPredicateArray.length + 1];
            System.arraycopy(currentPredicateArray, 0, newPredicateArray, 0, currentPredicateArray.length);
            currentPredicateArray[currentPredicateArray.length - 1] = criteriaBuilder.not(from.get("id").in(ids));
        }
        applySortOrder(criteriaQuery, filters, from, criteriaBuilder, currentPredicateArray);
        return criteriaQuery;
    }

    protected CriteriaQuery createWhereIdsIn(FilterSortTO filters, CriteriaBuilder criteriaBuilder, Class daoClass, List<Long> ids) {
        CriteriaQuery<Class<E>> criteriaQuery = (CriteriaQuery<Class<E>>) criteriaBuilder.createQuery(daoClass);
        Path<E> from = (Path<E>) criteriaQuery.from(daoClass);
        Predicate predicate = from.get("id").in(ids);
        applySortOrder(criteriaQuery, filters, from, criteriaBuilder, predicate);
        return criteriaQuery;
    }

    private void applySortOrder(CriteriaQuery<Class<E>> criteriaQuery, FilterSortTO filters, Path<E> from, CriteriaBuilder criteriaBuilder, Predicate... predicate) {
        criteriaQuery.where(predicate);
        if (filters.getPrimaryKey() != null) {
            from = from.get(filters.getPrimaryKey());
        }
        if ((filters.getSortField() != null) && (filters.getSortField().trim().length() > 0)) {
            if (filters.getSortOrder() == FilterSortTO.SortOrder.ASCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.asc(getPath(from, filters.getSortField())));
            } else if (filters.getSortOrder() == FilterSortTO.SortOrder.DESCENDING) {
                criteriaQuery.orderBy(criteriaBuilder.desc(getPath(from, filters.getSortField())));
            }
        }
    }

    protected final Path<Object> getPath(final Path<E> from, final String sortField) {
        final String[] fields = sortField.split(FIELD_DELIMITER);
        Path<Object> result = from.get(fields[0]);
        if (fields.length > 1) {
            for (int i = 1; i < fields.length; i++) {
                result = result.get(fields[i]);
            }
        }
        return result;
    }
}
