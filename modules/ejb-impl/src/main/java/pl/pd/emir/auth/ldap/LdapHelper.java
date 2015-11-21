package pl.pd.emir.auth.ldap;

import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.auth.IIDMConfig;
import pl.pd.emir.auth.ILdapHelper;
import pl.pd.emir.auth.config.LdapConfig;
import pl.pd.emir.auth.enums.RoleMapper;
import pl.pd.emir.commons.commonutils.PasswordUtils;
import pl.pd.emir.entity.administration.Group;
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
                if (mapGroups) {
                    List<Group> userGroups = this.mapLdapGroupToAppGroup(ldapLogin);
                    if (userGroups != null && userGroups.size() > 0) {
                        user.setGroups(userGroups);
                    }
                }
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

    /**
     * Pobranie rol dla uzytkownika. w zaleznosci od konfiguracji role pobierane sa z LDAP i mapowane na role
     * aplikacyjne lub pobierane sa z bazy danych tak jak dla DBConnectora
     *
     * @param username
     * @return
     */
    @Override
    public List<String> getUserRoles(String username) {
        List<String> userRoles = Collections.EMPTY_LIST;
        RoleMapper roleMapper = idmConfig.getConfig().getLdapConfig().getRoleMapper();
        if (RoleMapper.DB.equals(roleMapper)) {
            userRoles = userManager.getUserRoles(username);
        } else if (RoleMapper.LDAP.equals(roleMapper)) {
            try {
                LdapConfig ldapConfig = idmConfig.getConfig().getLdapConfig();
                List<String> ldapGroups = LdapUtils.getUserLdapGroups(username, ldapConfig);
                ldapGroups.stream().forEach((g) -> {
                    LOGGER.info("Grupy LDAP uzytkownika {}, {}", username, g);
                });
                userRoles = mapLdapGroupToAppRole(ldapGroups);
            } catch (Exception e) {
                LOGGER.error("Blad pobierania rol z ldap", e);
            }
        }

        return userRoles;
    }

    /**
     * Przemapowanie grup z ldam na grupy z app i pobranie rol odpowiadajacym grupom aplikacyjnym
     *
     * @param userRoles
     * @param ldapGroups
     * @return
     */
    private List<String> mapLdapGroupToAppRole(List<String> ldapGroups) {
        List<String> userRoles = new ArrayList<>();
        if (ldapGroups != null) {
            LdapConfig ldapConfig = idmConfig.getConfig().getLdapConfig();
            ldapGroups.stream()
                    .map((ldapGroup) -> LdapUtils.getAppGroup(ldapGroup, ldapConfig))
                    .filter((appGroup) -> (appGroup != null))
                    .forEach(appGroup -> userRoles.add(appGroup));
        }
        return userRoles;
    }

    /**
     * Przemapowanie grup z ldam na grupy z aplikacji.
     *
     * @param userName
     * @return grupy aplikacyjne
     */
    private List<Group> mapLdapGroupToAppGroup(String userName) throws Exception {
        List<Group> userGroups = Collections.EMPTY_LIST;
        LdapConfig ldapConfig = idmConfig.getConfig().getLdapConfig();
        List<String> ldapGroups = LdapUtils.getUserLdapGroups(userName, ldapConfig);
        if (ldapGroups != null) {
            userGroups = new ArrayList<>();
            for (String ldapGroup : ldapGroups) {

                String appGroup = LdapUtils.getAppGroup(ldapGroup, ldapConfig);
                Group group = userManager.getGroupByName(appGroup);
                if (group != null) {
                    userGroups.add(group);
                }
            }
        }
        return userGroups;
    }
}
