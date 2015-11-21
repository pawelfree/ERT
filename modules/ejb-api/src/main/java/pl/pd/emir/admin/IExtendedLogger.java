package pl.pd.emir.admin;

import pl.pd.emir.entity.administration.EventLog;

public interface IExtendedLogger {

    void addEvent(EventLog event);
}
