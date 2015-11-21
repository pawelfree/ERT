package pl.pd.emir.commons.interfaces;

import pl.pd.emir.enums.EventType;

public interface Logable<PK extends Long> extends Identifiable<PK> {

    EventType getDeleteEventType();

    EventType getInsertEventType();

    EventType getModifyEventType();
}
