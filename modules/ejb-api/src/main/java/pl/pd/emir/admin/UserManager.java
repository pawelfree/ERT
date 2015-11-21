package pl.pd.emir.admin;

import java.util.List;
import pl.pd.emir.entity.administration.Group;
import pl.pd.emir.entity.administration.User;
import pl.pd.emir.manager.GenericManager;

public interface UserManager extends GenericManager<User> {

    Group getGroupByName(String appGroup);

    User getCurrentUser();

    String getCurrentUserLogin();

    User getUserByLogin(String userName);

    List<String> getAllRoles();

    List<String> getUserRoles(String username);

    List<Group> getAllGroups();

    List<String> getRolesForGroup(String appGroup);

    void synchronizeUserWithDB(User user);

    String getUserIPAddress();
}
