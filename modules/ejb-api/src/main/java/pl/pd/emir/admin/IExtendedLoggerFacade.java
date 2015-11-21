package pl.pd.emir.admin;

import pl.pd.emir.entity.administration.EventLog;

public interface IExtendedLoggerFacade {

    public void addEvent(EventLog event);
}
