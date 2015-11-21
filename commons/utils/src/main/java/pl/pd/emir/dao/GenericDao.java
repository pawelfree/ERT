package pl.pd.emir.dao;

import java.io.Serializable;
import java.util.List;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.dao.criteria.AbstractSearchCriteria;

public interface GenericDao<E extends Identifiable<PK>, PK extends Long> extends Serializable {

    E get(E entity);

    void refresh(E entity);

    void save(E entity);

    E merge(E entity);

    void save(Iterable<E> entities);

    void delete(E entity);

    void delete(Iterable<E> entities);

    List<E> find(E entity, AbstractSearchCriteria criteria);

    int findCount(E entity, AbstractSearchCriteria criteria);
}
