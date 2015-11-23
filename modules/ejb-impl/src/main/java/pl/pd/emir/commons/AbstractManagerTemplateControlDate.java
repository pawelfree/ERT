package pl.pd.emir.commons;

import pl.pd.emir.commons.interfaces.Logable;
import pl.pd.emir.dao.utils.DaoUtil;
import pl.pd.emir.manager.GenericManagerControlDate;

public abstract class AbstractManagerTemplateControlDate<E extends Logable<Long>> extends AbstractLogManagerTemplate<E> implements GenericManagerControlDate<E> {

    public AbstractManagerTemplateControlDate(Class<E> clazz) {
        super(clazz);
    }

    @Override
    public Boolean isExistsItemYesterday(Long itemId) {
        return DaoUtil.isExistsItemYesterday(this, itemId);
    }
}
