package pl.pd.emir.connector;

import com.sun.appserv.security.AppservRealm;
import static com.sun.appserv.security.AppservRealm.JAAS_CONTEXT_PARAM;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pl.pd.emir.auth.IUserFacadeRemote;

public class IDMRealm extends AppservRealm {

    private static final Logger LOGGER = Logger.getLogger(IDMRealm.class.getName());
    private static final String JNDI_IDM_PATH = "jndiIdmPath";
    private String jndiName;

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        LOGGER.log(Level.INFO, "Initalize IDM Realm...");

        String propJaasContext = props.getProperty(JAAS_CONTEXT_PARAM);
        if (propJaasContext != null) {
            LOGGER.log(Level.INFO, "Setting property " + JAAS_CONTEXT_PARAM + " for IDM Realm: {0}", propJaasContext);
            setProperty(JAAS_CONTEXT_PARAM, propJaasContext);
        }
        else {
            LOGGER.log(Level.WARNING, "Setting property " + JAAS_CONTEXT_PARAM + " for IDM Realm failed. Using default property value : idmRealm ");
            setProperty(JAAS_CONTEXT_PARAM, "idmRealm");
        } 
           
        jndiName = props.getProperty(JNDI_IDM_PATH);
        LOGGER.log(Level.INFO, "IDM Remote Facade JNDI path: {0}", jndiName);
    }

    @Override
    public Enumeration getGroupNames(String user) throws InvalidOperationException, NoSuchUserException {
        LOGGER.log(Level.INFO, "Get Group Names for user: {0}", user);

        List<String> vector = new ArrayList<>();
        try {
            IUserFacadeRemote userFacadeRemote = locateUserFacade();
            boolean loginRegistered = userFacadeRemote.isLoginRegistered(user);
            if (!loginRegistered) {
                LOGGER.log(Level.WARNING, "User not found in data base: {0}", user);
                throw new NoSuchUserException("User not found in data base: " + user);
            } else {
                LOGGER.log(Level.INFO, "User {0} found in DB", user);
                vector.add("AUTHENTICATED_USER");
            }

        } catch (NamingException ex) {
            LOGGER.log(Level.SEVERE, "Error checking user name: {0} info: {1}", new Object[]{user, ex.getMessage()});
            throw new InvalidOperationException("Error checking user name: " + user + " info: " + ex.getMessage());
        }

        return Collections.enumeration(vector);
    }

    @Override
    public String getAuthType() {
        return "idmRealm";
    }

    @Override
    public boolean supportsUserManagement() {
        return false;
    }

    public IUserFacadeRemote locateUserFacade() throws NamingException {
        LOGGER.log(Level.SEVERE, "jndiName: {0}", jndiName);
        IUserFacadeRemote userFacade;
        InitialContext ctx = new InitialContext();
        userFacade = (IUserFacadeRemote) ctx.lookup(jndiName);
        LOGGER.log(Level.INFO, "Referencja do remote EJB znaleziona:{0}", (userFacade != null));
        return userFacade;
    }

}
