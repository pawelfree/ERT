package pl.pd.emir.auth;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(AuthUserService.class)
public class AuthUserServiceBean implements AuthUserService {

    @Override
    public IFormAuthenticator getFormAuthenticator() {
        return new FormAuthenticator();
    }
}
