package pl.pd.emir.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import pl.pd.emir.clientutils.SecurityUtil;

@ManagedBean
@ApplicationScoped
public class SecurityBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public final boolean isUserInRole(final String role) {
        return SecurityUtil.isUserInRole(role);
    }

    public final boolean isUserInAllRoles(final String roles) {
        return SecurityUtil.isUserInAllRoles(roles.split(","));
    }

    public final boolean isUserInAnyRole(final String roles) {
        return SecurityUtil.isUserInAnyRole(roles.split(","));
    }
}
