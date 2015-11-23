package pl.pd.emir.dao;

import pl.pd.emir.commons.interfaces.Identifiable;

public abstract class AbstractGenericDaoImpl<E extends Identifiable<PK>, PK extends Long> implements
        GenericDao<E, PK> {

    protected AbstractGenericDaoImpl() {
        super();
    }
}
