package pl.pd.emir.auth;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormAuthenticator implements IFormAuthenticator {

    private static final Logger LOGGER = Logger.getLogger(FormAuthenticator.class.getName());

    @Override
    public boolean authenticate(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.login(username, password);
            return true;

        } catch (ServletException ex) {
            LOGGER.log(Level.SEVERE, "Blad autentykacji", ex);
        }
        return false;

    }

    @Override
    public Subject getCurrentSubject() throws Exception {
        Subject callerSubject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
        return callerSubject;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        try {
            req.logout();
        } catch (ServletException ex) {
            LOGGER.log(Level.SEVERE, "Blad wylogowania", ex);
        }
    }

}
