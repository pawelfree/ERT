package pl.pd.emir.auth.ldap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import pl.pd.emir.auth.config.LdapAttribute;
import pl.pd.emir.entity.administration.User;

public class LdapMapper {

    /**
     * Metoda przypisujaca atrybuty użytkownika pobrane z LDAP do encji User
     *
     * @param userAttributes - lista atrybutów poprana z LDAP
     * @return
     */
    public static User mapLdapAttributesToUser(Map<String, List<String>> userAttributes) {
        User user = new User();

        user.setLogin(getUserLogin(userAttributes));
        user.setFirstName(getUserFirstName(userAttributes));
        user.setLastName(getUserLastName(userAttributes));
        //user.setEmail(getUserEmail(userAttributes));
        //user.setPhoneNumber(getUserPhoneNumber(userAttributes));

        return user;
    }

    private static String getLdapAttributeValue(Map<String, List<String>> userAttributes, String ldapAttribute) {
        String value = null;
        if (userAttributes != null && !userAttributes.isEmpty()) {

            if (userAttributes.containsKey(ldapAttribute)) {
                List<String> values = userAttributes.get(ldapAttribute);

                if (values != null && !values.isEmpty()) {
                    value = values.get(0);
                }
            }

        }

        return value;
    }

    public static List<String> getUserGroups(Map<String, List<String>> userAttributes) {
        String ldapGroupId = LdapAttribute.MEMBER_OF;
        if (userAttributes != null && !userAttributes.isEmpty() && userAttributes.containsKey(ldapGroupId)) {
            return userAttributes.get(ldapGroupId);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public static String getUserLogin(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.SAM_ACCOUNT_NAME);
    }

    public static String getUserFirstName(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.GIVEN_NAME);
    }

    public static String getUserLastName(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.SN);
    }

    public static String getUserEmail(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.MAIL);
    }

    public static String getUserPhoneNumber(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.TELEPHONE_NUMBER);
    }

    public static String getUserDepartament(Map<String, List<String>> userAttributes) {
        return getLdapAttributeValue(userAttributes, LdapAttribute.DEPARTAMENT);
    }
}
