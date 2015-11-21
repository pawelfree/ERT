package pl.pd.emir.auth;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFormAuthenticator {

    boolean authenticate(String username, String password,
            final HttpServletRequest req, final HttpServletResponse resp);

    Subject getCurrentSubject() throws Exception;

    void logout(final HttpServletRequest req,
            final HttpServletResponse res);

}
