package pl.pd.emir.util;

import java.util.Date;

public class RolesInTime {

    private String[] roles;
    private Date time;

    public RolesInTime() {
    }

    public RolesInTime(String[] roles, Date time) {
        this.roles = roles;
        this.time = time;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
