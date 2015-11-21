package pl.pd.emir.auth;

import pl.pd.emir.auth.IIDMConfig;
import pl.pd.emir.auth.IConnector;
import pl.pd.emir.auth.IUserFacadeRemote;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.auth.config.AuthConnector;
import pl.pd.emir.entity.administration.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Remote(IUserFacadeRemote.class)
public class UserFacadeRemote implements IUserFacadeRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeRemote.class);

    @EJB
    private IIDMConfig resourceBinder;

    @EJB
    private UserManager userManager;

    @Override
    @PermitAll
    public boolean isUsernamePasswordValid(String userName, String password) {
        LOGGER.info("isUsernamePasswordValid");
        LOGGER.info("Data konfiguracji : " + resourceBinder.getConfig().getDataOdczytu());
        AuthConnector authConnector = resourceBinder.getConfig().getFirstConnector();
        IConnector connector = authConnector.getConnector();
        return connector.checkUsernamePassword(userName, password);
    }

    @Override
    public boolean isLoginRegistered(String loginName) {
        User user = userManager.getUserByLogin(loginName);
        return user != null;
    }

    @Override
    public List<String> getAllRoles() {
        return userManager.getAllRoles();
    }

    @Override
    @PermitAll
    public List<String> getUserRoles(String username) {
        LOGGER.info("getUserRoles {}", username);
        return resourceBinder.getConfig().getFirstConnector().getConnector().getUserRoles(username);
    }

    @Override
    public List<String> getUsers(String pattern, int size) {
        List<String> users = new ArrayList<>();

        List<User> usersList = userManager.findAll();
        usersList.stream().forEach((u) -> {
            users.add(u.getLogin());
        });

        return users;
    }

    @Override
    @PermitAll
    public void synchronizeUserWithDB(String login) {
        LOGGER.debug("Synchronizacja użytkownika w Bazie Danych");
        User user = new User();
        user.setLogin(login);
        user.setActive(true);
        userManager.synchronizeUserWithDB(user);
    }
}
