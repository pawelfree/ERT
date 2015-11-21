package pl.pd.emir.auth.ldap;

import pl.pd.emir.auth.ldap.LdapErrorCodes;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import pl.pd.emir.auth.config.GroupMap;
import pl.pd.emir.auth.config.LdapAttribute;
import pl.pd.emir.auth.config.LdapConfig;
import pl.pd.emir.commons.commonutils.PasswordUtils;

public class LdapUtils {

    public static List<String> getUserLdapGroups(String username, LdapConfig ldapConfig) throws Exception {
        List<String> ldapGroups = Collections.EMPTY_LIST;
        Ldap ldap = new Ldap(ldapConfig.getProviderUrl(), ldapConfig.getDomain(), ldapConfig.getAuthType(), ldapConfig.isUseSSL());
        String plainPawssword = PasswordUtils.decryptData(ldapConfig.getLdapUserPassword().getPassword());
        LdapErrorCodes result = ldap.authenticate(ldapConfig.getLdapUserName(), plainPawssword);
        if (LdapErrorCodes.ERROR_SUCCESS.equals(result)) {
            Map<String, List<String>> userAttributes = ldap.getUserAttributes(username);
            ldapGroups = userAttributes.get(LdapAttribute.MEMBER_OF);
        }
        return ldapGroups;
    }

    public static String getAppGroup(String ldapGroup, LdapConfig ldapConfig) {
        for (GroupMap groupMap : ldapConfig.getGroupMap()) {
            if (ldapGroup != null && ldapGroup.equals(groupMap.getLdapGroup())) {
                return groupMap.getAppGroup();
            }
        }
        return null;
    }
}
