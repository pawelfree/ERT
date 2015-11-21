package pl.pd.emir.manager;

import pl.pd.emir.commons.interfaces.Identifiable;

public interface GenericManagerControlDate<T extends Identifiable<Long>> extends GenericManager<T> {

    Boolean isExistsItemYesterday(Long itemId);

}
