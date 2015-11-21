package pl.pd.emir.auth;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.entity.administration.User;

@Stateless
@Local(IConnector.class)
public class DBConnector implements IConnector {

    @EJB
    private UserManager userManager;

    @Override
    public boolean checkUsernamePassword(String userName, String password) {
        User userByLogin = userManager.getUserByLogin(userName);
        return userByLogin != null;
    }

    @Override
    public List<String> getUserRoles(String username) {
        return userManager.getUserRoles(username);
    }
}
