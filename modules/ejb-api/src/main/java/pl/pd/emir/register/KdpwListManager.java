package pl.pd.emir.register;

import java.util.Date;
import pl.pd.emir.commons.interfaces.Identifiable;
import pl.pd.emir.manager.GenericManager;

public interface KdpwListManager<T extends Identifiable<Long>> extends GenericManager<T> {

    Date getMaxDate();

    Date getMinDate();

}
