package pl.pd.emir.auth.ldap;

import java.util.*;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.auth.config.LdapAttribute;

public class Ldap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ldap.class);
    @SuppressWarnings("UseOfObsoleteCollectionType")
    protected Hashtable env = null;
    protected LdapContext ctx = null;
    private String domainSufix;
    private String securityAuth;
    private final Map<String, List<String>> userAttributes = new HashMap<>();

    @SuppressWarnings("UseOfObsoleteCollectionType")
    public Ldap(String providerUrl, String domainSufix, String securityAuth, boolean useSsl) {
        this.domainSufix = domainSufix;
        this.env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, providerUrl);
        if (securityAuth != null) {
            this.securityAuth = securityAuth;
            env.put(Context.SECURITY_AUTHENTICATION, securityAuth);
        } else {
            this.securityAuth = "simple";
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
        }

        //wlaczenie authentication plus integrity protection
        //parametr wykorzystywane jesli do autentykacji wykorzystywany jest mechznizm SASL
        //np. DIGEST-MD5
        if (securityAuth != null && !securityAuth.equalsIgnoreCase("simple")) {
            env.put("javax.security.sasl.qop", "auth-int");
        }

        if (useSsl) {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }
    }

    public Ldap(String providerUrl, String domainSufix, String securityAuth) {
        this(providerUrl, domainSufix, securityAuth, false);
    }

    public Ldap(String providerUrl, String domainSufix) {
        this(providerUrl, domainSufix, "simple");
    }

    public Ldap(String providerUrl, String domainSufix, boolean useSSL) {
        this(providerUrl, domainSufix, "simple", useSSL);
    }

    public LdapContext getLdapContext() {
        return this.ctx;
    }

    public LdapErrorCodes authenticate(String login, String password) {
        if (ctx != null) {
            this.close();
        }
        if (securityAuth != null && securityAuth.equalsIgnoreCase("simple")) {
            login = login + "@" + domainSufix;
        }

        LdapErrorCodes res =  this.open(login, password);
        
        System.out.println("!!!!!! - ldap open result " + res.toString() + " (" + login + " " + password + ")");        
        return res;
    }

    public LdapErrorCodes checkUserLogin(String login) {
        if (ctx == null) {
            return LdapErrorCodes.ERROR_LOGON_FAILURE;
        }

        String searchBase = getRootAD(this.domainSufix);

        boolean userExists = false;
        String returnedAtts[] = LdapAttribute.toArray();

        String searchFilter = "(&(objectClass=user)(" + LdapAttribute.SAM_ACCOUNT_NAME + "=" + login + "))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);

        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        try {
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                userExists = true;
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();

                if (attrs != null) {

                    NamingEnumeration ne = attrs.getAll();
                    LOGGER.debug("Active Directory - Atrybuty");
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();

                        List<String> list = new ArrayList<>();
                        for (Enumeration el = attr.getAll(); el.hasMoreElements();) {
                            String attributeName = getCN(el.nextElement().toString());
                            list.add(attributeName);
                            LOGGER.debug(attr.getID() + ": " + attributeName);
                        }
                        userAttributes.put(attr.getID(), list);
                    }
                    ne.close();
                }

            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        if (userExists) {
            return LdapErrorCodes.ERROR_SUCCESS;
        } else {
            return LdapErrorCodes.ERROR_ACCOUNT_NOT_FOUND;
        }
    }

    public Map<String, List<String>> getUserAttributes(String login) {
        LdapErrorCodes result = checkUserLogin(login);
        if (LdapErrorCodes.ERROR_SUCCESS == result) {
            return userAttributes;
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    public LdapErrorCodes open(String login, String password) {

        if (ctx == null) {
            try {
                env.put(Context.SECURITY_PRINCIPAL, login);
                env.put(Context.SECURITY_CREDENTIALS, password);

                ctx = new InitialLdapContext(env, null);
                return LdapErrorCodes.ERROR_SUCCESS;
            } catch (AuthenticationException e) {
                /*
                 * Zwracany kod bledu Active Directory nale�y skonwertowa� z hex -->
                 * dec. Lista kodow znajduje sie na:
                 * http://msdn.microsoft.com/library/default.asp?url=/library/en-us/debug/base/system_error_codes.asp
                 */

                /*
                 * 0 - (0) - ERROR_SUCCESS - The operation completed successfully.
                 * 52e - (1326) - ERROR_LOGON_FAILURE - Logon failure: unknown user
                 * name or bad password. 533 - (1331) - ERROR_ACCOUNT_DISABLED -
                 * 1331 Logon failure: account currently disabled.
                 */
                String expl = e.getExplanation();
                int startIndex = expl.indexOf("data") + 5;
                int endIndex = expl.indexOf(",", startIndex);
                String errorCode = expl.substring(startIndex, endIndex);

                LOGGER.error("Kod bledu hex: '" + errorCode);
                LOGGER.error(e.getMessage(), e);
                System.out.println("!!!!!!!!!!! - ldap auth " + e.getMessage());
                System.out.println("!!!!!!!!!!! - ldap auth " + e.getExplanation());                
                return LdapErrorCodes.getByHexValue(errorCode);
            } catch (NamingException e) {
                System.out.println("!!!!!!!!!!! - ldap naming " + e);
                System.out.println("!!!!!!!!!!! - ldap naming " + e.getExplanation());
                LOGGER.error(e.getMessage(), e);
                return LdapErrorCodes.ERROR_INVALID_DOMAIN;
            }
        } else {
            return LdapErrorCodes.ERROR_INVALID_DOMAIN;
        }
    }

    public void close() {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                ctx = null;
            }
        }
    }

    public void reconnect(String login, String password) {
        if (ctx != null) {
            try {
                ctx.reconnect(null);
            } catch (NamingException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            this.open(login, password);
        }
    }

    public static String getCN(String cnName) {
        if (cnName != null && cnName.toUpperCase().startsWith("CN=")) {
            cnName = cnName.substring(3);
        }
        int position = cnName.indexOf(',');
        if (position == -1) {
            return cnName;
        } else {
            return cnName.substring(0, position);
        }
    }

    public static String getRootAD(String domain) {
        String tab[] = domain.split("\\.");
        StringBuilder ret = new StringBuilder();
        ret.append("DC=");
        for (int i = 0; i < tab.length; i++) {
            if (i < tab.length - 1) {
                ret.append(tab[i]);
                ret.append(",DC=");
            } else {
                ret.append(tab[i]);
            }
        }
        return ret.toString();
    }
}
