package pl.pd.emir.clientutils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SecurityUtil {

    public static boolean isUserInRole(String role) {
        ExternalContext externatCtx = getExternalCtx();
        return externatCtx.isUserInRole(role);
    }

    public static boolean isUserInAllRoles(String... roles) {
        ExternalContext externatCtx = getExternalCtx();
        for (String role : roles) {
            if (!externatCtx.isUserInRole(role.trim()))
                return false;
        }
        return true;
    }

    public static boolean isUserInAnyRole(String... roles) {
        ExternalContext externatCtx = getExternalCtx();
        for (String role : roles) {
            if (externatCtx.isUserInRole(role.trim()))
                return true;
        }
        return false;
    }

    private static ExternalContext getExternalCtx() {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ExternalContext externatCtx = facesCtx.getExternalContext();
        return externatCtx;
    }
    
    public static String getCurrentUser() {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if (null == user) {
            user = "kazuhiro_abe";
        }
        return user;
   }   
    
}
