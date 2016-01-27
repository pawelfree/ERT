package pl.pd.emir.auth;

import java.util.List;

public interface IUserFacadeRemote {

    /**
     * Sprzwdzanie poprawnosci hasla i loginu w repozytorium wynikajacym z konfiguracji (db, ldap)
     *
     * @param username
     * @param password
     * @return
     */
    boolean isUsernamePasswordValid(String username, String password);

    /**
     * Metoda sprawdzająca czy użytkownik o danym loginie, znajduje się w bazie.
     *
     * @param loginName
     * @return true - jeśli znaliziono użytkownika, false - gdy nie znaleziono użytkownika.
     */
    boolean isLoginRegistered(String loginName);

    /**
     * Wyszukanie uzytkownikow - na potrzeby logowania WAS
     *
     * @param pattern
     * @param size
     * @return
     */
    List<String> getUsers(String pattern, int size);

    /**
     * Metoda synchronizujaca uzytkownika w bazie dannych.
     *
     * @param login - ogin uzytkownika
     */
    void synchronizeUserWithDB(String login);
}
