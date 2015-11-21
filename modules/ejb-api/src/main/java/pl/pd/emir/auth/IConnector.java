package pl.pd.emir.auth;

import java.util.List;

public interface IConnector {

    /**
     * sprawdzenie loginu i hasla
     *
     * @param userName
     * @param password
     * @return false - jesli bledne haslo lub user, true jesli poprawne
     */
    boolean checkUsernamePassword(String userName, String password);

    /**
     * Pobranie listy rol, role po przemapowaniu przez tabele grupa_rola
     *
     * @param username
     * @return
     */
    List<String> getUserRoles(String username);
}
