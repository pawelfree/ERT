package pl.pd.emir.auth.ldap;

import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.auth.IIDMConfig;
import pl.pd.emir.auth.ILdapHelper;
import pl.pd.emir.auth.config.LdapConfig;
import pl.pd.emir.commons.commonutils.PasswordUtils;
import pl.pd.emir.entity.administration.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Local(ILdapHelper.class)
public class LdapHelper implements ILdapHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapHelper.class);

    @EJB
    private IIDMConfig idmConfig;
    @EJB
    private UserManager userManager;

    public LdapHelper() {
    }

    /**
     * Metoda sprawdzajaca czy dany login znajduje sie w drzewie Ldap.
     *
     * @param ldapLogin
     * @return
     */
    @Override
    public LdapErrorCodes checkLdapLogin(String ldapLogin) {

        LdapConfig ldapConfig = idmConfig.getConfig().getLdapConfig();

        Ldap ldap = null;
        LdapErrorCodes result = LdapErrorCodes.ERROR_UNKNOWN;
        try {
            ldap = new Ldap(ldapConfig.getProviderUrl(), ldapConfig.getDomain(), ldapConfig.getAuthType(), ldapConfig.isUseSSL());
            result = ldap.authenticate(ldapConfig.getLdapUserName(), getLdapPassword(ldapConfig));
            if (LdapErrorCodes.ERROR_SUCCESS == result) {
                result = ldap.checkUserLogin(ldapLogin);
            }
        } finally {
            if (ldap != null) {
                ldap.close();
            }
        }

        return result;
    }

    /**
     * Metoda pibierajaca uzytkownika z Ldap.
     *
     * @param ldapLogin
     * @return - uzupelniona encja uzytkownik.
     */
    @Override
    public User getUserFromLdap(String ldapLogin, boolean mapGroups) {
        User user = new User();

        LdapConfig ldapConfig = idmConfig.getConfig().getLdapConfig();
        Ldap ldap = null;
        try {
            ldap = new Ldap(ldapConfig.getProviderUrl(), ldapConfig.getDomain(), ldapConfig.getAuthType(), ldapConfig.isUseSSL());
            LdapErrorCodes result = ldap.authenticate(ldapConfig.getLdapUserName(), getLdapPassword(ldapConfig));
            if (LdapErrorCodes.ERROR_SUCCESS == result) {
                Map<String, List<String>> userAttributes = ldap.getUserAttributes(ldapLogin);
                user = LdapMapper.mapLdapAttributesToUser(userAttributes);
            }
        } catch (Exception e) {
            LOGGER.error("Blad mapowania attrybotów LDAP na encje: ", e);
        } finally {
            if (ldap != null) {
                ldap.close();
            }
        }
        return user;
    }

    private String getLdapPassword(LdapConfig ldapConfig) {
        try {
            return PasswordUtils.decryptData(ldapConfig.getLdapUserPassword() != null ? ldapConfig.getLdapUserPassword().getPassword() : " ");
        } catch (Exception ex) {
            LOGGER.error("Blad dekodowania hasla do LDAP: " + ex.getMessage(), ex);
            return null;
        }
    }

}
