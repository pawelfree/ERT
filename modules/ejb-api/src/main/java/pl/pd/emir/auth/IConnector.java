package pl.pd.emir.auth;

public interface IConnector {

    /**
     * sprawdzenie loginu i hasla
     *
     * @param userName
     * @param password
     * @return false - jesli bledne haslo lub user, true jesli poprawne
     */
    boolean checkUsernamePassword(String userName, String password);

}
