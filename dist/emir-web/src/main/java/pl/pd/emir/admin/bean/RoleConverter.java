package pl.pd.emir.admin.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import pl.pd.emir.admin.UserManager;
import pl.pd.emir.entity.administration.Role;

@ManagedBean
@RequestScoped
public class RoleConverter implements Converter {

    @EJB
    private transient UserManager service;

    @Override
    public Object getAsObject(FacesContext facesContext,
            UIComponent component, String value) {
        if (value == null || "null".equals(value)) {
            return null;
        }
        return "";//service.getRoleByName(value);
    }

    @Override
    public String getAsString(FacesContext facesContext,
            UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        Role role = (Role) object;
        return role.getName().name();
    }
}
