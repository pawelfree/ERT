package pl.pd.emir.auth;

import java.security.Principal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessagePolicy;
import net.java.spnego.SpnegoServerAuthModule;

public class SSOAuthenticator extends SpnegoServerAuthModule {

    private static final Logger LOG = Logger.getLogger(SSOAuthenticator.class.getName());
    private static final String JNDI_IDM_PATH = "jndiIdmPath";
    private String jndiName;

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
        return null;

    }

    public IUserFacadeRemote locateUserFacade() throws NamingException {
        IUserFacadeRemote userFacade;
        InitialContext ctx = new InitialContext();
        userFacade = (IUserFacadeRemote) ctx.lookup(jndiName);
        LOG.log(Level.FINE, "Referencja do remote EJB znaleziona: {0}", (userFacade != null));
        return userFacade;
    }
}
