package pl.pd.emir.bean;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.auth.AuthUserService;
import pl.pd.emir.auth.IFormAuthenticator;
import pl.pd.emir.clientutils.SecurityUtil;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);
    @EJB
    private transient AuthUserService authUserService;
    private String loginTo;
    private String password;

    public String getLoginTo() {
        return loginTo;
    }

    public void setLoginTo(String loginTo) {
        this.loginTo = loginTo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Logowanie do aplikacji.
     *
     * @return redirectPage
     */
    public final String login() {
        LOGGER.info("Start login: {}", loginTo);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        IFormAuthenticator authenticator = authUserService.getFormAuthenticator();

        
        if (authenticator.authenticate(loginTo, password, request, response)) {
            LOGGER.info("Zalogowano poprawnie, uzytkownika {}", loginTo);
            return "home";

        } else {
            BeanHelper.addErrorMessageFromResource("login.failed", "loginForm:usernameInput");
            logout();
            return null;
        }

    }

    /**
     * Logowanie do aplikacji.
     *
     * @return redirectPage
     */
    public final String logout() {
        LOGGER.info("Start logout");
        IFormAuthenticator authenticator = authUserService.getFormAuthenticator();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        authenticator.logout(request, response);
        LOGGER.info("End logout");
        return "login";
    }

    public void reset() {
        loginTo = "";
        password = "";
    }

    public String getLoginUser() {
        return SecurityUtil.getCurrentUser();
    }

}
