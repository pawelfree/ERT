package pl.pd.emir.auth;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessagePolicy;
import pl.pd.emir.util.RolesInTime;
import net.java.spnego.SpnegoServerAuthModule;

public class SSOAuthenticator extends SpnegoServerAuthModule {

    private static final Logger LOG = Logger.getLogger(SSOAuthenticator.class.getName());
    private static final String JNDI_IDM_PATH = "jndiIdmPath";
    private String jndiName;

    private static final Map<String, RolesInTime> SESSION_MAP = new HashMap<>();
    private static final int CACHE_TIME = 300; //5 min.

    @Override
    public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
        super.initialize(requestPolicy, responsePolicy, handler, options);
        if (options != null) {
            jndiName = (String) options.get(JNDI_IDM_PATH);
            LOG.log(Level.INFO, "IDM Remote Facade JNDI path: {0}", jndiName);
        }
    }

    @Override
    public String[] getGroupsForCaller(Principal caller) {
        boolean refresh = false;
        LOG.log(Level.INFO, "Get groups for {0}", caller);
        RolesInTime rolesInTime;
        String login = getUserLogin(caller);
        synchronized (SSOAuthenticator.class) {
            rolesInTime = SESSION_MAP.get(login);
            if (null != rolesInTime) {
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rolesInTime.getTime());
                calendar.add(Calendar.SECOND, CACHE_TIME); //odświeżamy co 10 sekumdy
                Date date = calendar.getTime();
                if (date.before(now)) {
                    refresh = true;
                }
            } else {
                refresh = true;
            }
        }

        if (refresh) {
            try {
                IUserFacadeRemote userFacade = locateUserFacade();
                LOG.log(Level.INFO, "User login {0}", login);
                List<String> userRoles = userFacade.getUserRoles(login);
                userRoles.stream().forEach((r) -> {
                    LOG.log(Level.INFO, "Commit role {0}", r);
                });
                String[] roles = userRoles.toArray(new String[userRoles.size()]);
                if (roles.length > 0) {
                    userFacade.synchronizeUserWithDB(login);
                }
                SESSION_MAP.put(login, new RolesInTime(roles, new Date()));
                return roles;
            } catch (NamingException ex) {
                Logger.getLogger(SSOAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } else {
            return rolesInTime != null ? rolesInTime.getRoles() : null;
        }

    }

    public IUserFacadeRemote locateUserFacade() throws NamingException {
        IUserFacadeRemote userFacade;
        InitialContext ctx = new InitialContext();
        userFacade = (IUserFacadeRemote) ctx.lookup(jndiName);
        LOG.log(Level.INFO, "Referencja do remote EJB znaleziona: {0}", (userFacade != null));
        return userFacade;
    }

    private String getUserLogin(Principal caller) {
        String name = caller != null ? caller.getName() : "";
        String[] split = name.split("\\@");
        return split[0];
    }

}
