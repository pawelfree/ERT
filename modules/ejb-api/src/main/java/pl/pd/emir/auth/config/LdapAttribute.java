package pl.pd.emir.auth.config;

public class LdapAttribute {

    /*
     * Nie ustawiać final, bo pola są modyfikowane przy starcie systemu
     */
    public static final String SN = "sn";
    public static final String GIVEN_NAME = "givenName";
    public static final String MAIL = "mail";
    public static final String USER_PRINCIPAL_NAME = "userPrincipalName";
    public static final String SAM_ACCOUNT_NAME = "sAMAccountName";
    public static final String DISTINGUISHED_NAME = "distinguishedName";
    public static final String DEPARTAMENT = "department";
    public static final String TELEPHONE_NUMBER = "telephoneNumber";
    public static final String MEMBER_OF = "memberOf";

    public static String[] toArray() {
        final String returnedAtts[] = {LdapAttribute.SN, LdapAttribute.GIVEN_NAME, LdapAttribute.MAIL, LdapAttribute.USER_PRINCIPAL_NAME,
            LdapAttribute.SAM_ACCOUNT_NAME, LdapAttribute.DISTINGUISHED_NAME, LdapAttribute.DEPARTAMENT, LdapAttribute.TELEPHONE_NUMBER,
            LdapAttribute.MEMBER_OF};
        return returnedAtts;
    }
}
