package pl.pd.emir.admin;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.security.auth.Subject;
import pl.pd.emir.auth.AuthUserService;
import pl.pd.emir.auth.IPPrincipal;
import pl.pd.emir.commons.AbstractManagerTemplate;
import pl.pd.emir.entity.administration.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UserManager extends AbstractManagerTemplate<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);
    @Resource
    private SessionContext sessionContext;
    @EJB
    private transient AuthUserService authUserService;

    public UserManager() {
        super(User.class);
    }

    public User getCurrentUser() {
        String currentUserLogin = getCurrentUserLogin();
        return getUserByLogin(currentUserLogin);
    }

    public String getUserIPAddress() {
        String ipAddress = null;
        try {
            Subject subject = authUserService.getFormAuthenticator().getCurrentSubject();
            Set<Principal> principals = subject.getPrincipals();
            for (Principal principal : principals) {
                if (principal.getClass().getName().equals(IPPrincipal.class.getName())) {
                    ipAddress = ((IPPrincipal) principal).getIpAddress();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Blad pobierania principala ", ex);
        }
        return ipAddress;
    }

    public String getCurrentUserLogin() {
        return sessionContext.getCallerPrincipal() != null ? sessionContext.getCallerPrincipal().getName() : "";
    }

    public User getUserByLogin(String userName) {
        List<User> users = entityManager.createNamedQuery("User.getByLogin").setParameter("login", userName).setMaxResults(1).getResultList();
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void synchronizeUserWithDB(User user) {
        User userDB = getUserByLogin(user.getLogin());
        if (null == userDB && user.isActive()) {
            //dodanie userka
            save(user);
        } else if (userDB != null) {
            //update danych
            userDB.setFirstName(user.getFirstName());
            userDB.setLastName(user.getLastName());
            userDB.setActive(user.isActive());
        } else {
            LOGGER.info("synchronizeUserWithDB...null user!");
        }
    }
}
