package pl.pd.emir.auth;

import java.util.List;
import pl.pd.emir.entity.administration.EventLog;

public interface IMZUManager {

    String getUserName(String login);

    List<String> getUserRoles(String login);

    List<String> getUsers(String pattern, int i);

    public void addEvent(EventLog event);
}
