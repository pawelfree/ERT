package pl.pd.emir.entity;

import java.util.List;
import pl.pd.emir.entity.administration.ChangeLog;

public interface Historable<E extends Historable> {

    List<ChangeLog> getChangeLogs(E newEntity);
}
