package pl.pd.emir.connector;

import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import pl.pd.emir.auth.IUserFacadeRemote;

public class IDMLoginModule extends AppservPasswordLoginModule {

    private static final Logger LOGGER = Logger.getLogger(IDMLoginModule.class.getName());

    public IDMLoginModule() {
        LOGGER.log(Level.INFO, "Initalize IDMLoginModule...");
    }

    @Override
    protected void authenticateUser() throws LoginException {
        LOGGER.log(Level.SEVERE, "IDM Login module authenticate user: {0}", _username);

        if (!(_currentRealm instanceof IDMRealm)) {
            LOGGER.log(Level.SEVERE, "IDMRealm not found. Check 'login.conf'.");
            throw new LoginException("EmirRealm not found. Check 'login.conf'.");
        }

        IDMRealm idmRealm = (IDMRealm) _currentRealm;
  
        doAuthentication(_username, new String(_passwd), idmRealm);

        try {
            LOGGER.log(Level.INFO, "commit, added principal: {0}", _username);

            Enumeration<String> enumeration = idmRealm.getGroupNames(_username);
            ArrayList<String> list = Collections.list(enumeration);
            String[] authenticatedGroups = list.toArray(new String[list.size()]);
            if (authenticatedGroups.length > 0) {
                for (String role : authenticatedGroups) {
                    LOGGER.log(Level.INFO, "commit, adding role {0}", role);
                }
            } else {
                LOGGER.log(Level.INFO, "Brak r\u00f3l dla u\u017cytkownika: {0}", _username);
            }

            commitUserAuthentication(authenticatedGroups);

        } catch (InvalidOperationException e) {
            throw new LoginException("InvalidOperationException was thrown for getGroupNames() on IDMRealm " + e.getMessage());
        } catch (NoSuchUserException e) {
            throw new LoginException("Brak uzytkownika w bazie danych " + _username);
        }

        LOGGER.log(Level.INFO, "Poprawne zalogowanie w aplikacji uzytkownika: {1}", _username);

    }

    private void doAuthentication(String user, String password, IDMRealm realm) throws LoginException {
        try {
            IUserFacadeRemote userFacadeRemote = realm.locateUserFacade();
            boolean valid = userFacadeRemote.isUsernamePasswordValid(user, password);
            if (!valid) {
                throw new LoginException("Blad logowania uzytkownika: " + user);
            }
        } catch (NamingException ex) {
            LOGGER.log(Level.SEVERE, "Error checking user name and password: {0} info: {1}", new Object[]{user, ex.getMessage()});
            LoginException le = new LoginException("Blad wyszukiwania JNDI.");
            le.initCause(ex);
            throw le;
        }
    }
}
