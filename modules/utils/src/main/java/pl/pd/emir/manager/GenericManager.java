package pl.pd.emir.manager;

import java.io.Serializable;
import java.util.List;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;
import pl.pd.emir.dao.criteria.PagedList;

public interface GenericManager<T extends Identifiable<Long>> extends Serializable {

    /**
     * Return the persistent instance of {@link Person} with the given unique property value username, or null if there
     * is no such persistent instance.
     *
     * @param Id
     * @return the corresponding {@link Person} persistent instance or null
     */
    T getById(Long Id);

    /**
     * Delete a T using the unique column.
     *
     * @param Id
     */
    void deleteById(Long Id);

    /**
     * Delete Entity.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Refresh the passed entity with up to date data.
     *
     * @param entity the entity to refresh.
     * @return
     */
    T refresh(T entity);

    /**
     * Save or update the passed entity.
     *
     * @param entity the entity to save or update.
     * @return
     */
    T save(T entity);

    /**
     * Search.
     *
     * @param criteria
     * @return @link PagedList
     */
    PagedList<T> find(AbstractSearchCriteria criteria);

    <T extends Identifiable<Long>> long getRecordCount(AbstractSearchCriteria criteria);

    /**
     * .
     * @return
     */
    List<T> findAll();

    /**
     * .
     * @param criteria
     * @param ids
     * @return
     */
    List<T> findWithoutDeselected(AbstractSearchCriteria criteria, List<Long> ids);

    /**
     * .
     * @param criteria
     * @param ids
     * @return
     */
    List<T> findByIds(AbstractSearchCriteria criteria, List<Long> ids);

    /**
     * .
     * @param criteria
     * @return
     */
    List<T> findAll(AbstractSearchCriteria criteria);

    /**
     * .
     * @param criteria
     * @param startIndex
     * @param packageSize
     * @return
     */
    List<T> findAll(final AbstractSearchCriteria criteria, int startIndex, final int packageSize);
}
