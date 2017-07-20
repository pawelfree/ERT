package pl.pd.emir.auth;

import pl.pd.emir.auth.ldap.LdapErrorCodes;

import pl.pd.emir.entity.administration.User;

public interface ILdapHelper {

    /**
     * Metoda sprawdzajaca czy dany login znajduje sie w drzewie Ldap.
     *
     * @param ldapLogin - login uzytkownika
     * @return kod błędu
     */
    LdapErrorCodes checkLdapLogin(String ldapLogin);

    /**
     * Metoda pibierajaca uzytkownika z Ldap.
     *
     * @param ldapLogin - login domenowy uzytkownika
     * @param mapGroups - mapowanie grup z ldap na aplikacyjne
     * @return - uzupelniona encja uzytkownik.
     */
    User getUserFromLdap(String ldapLogin, boolean mapGroups);


}
