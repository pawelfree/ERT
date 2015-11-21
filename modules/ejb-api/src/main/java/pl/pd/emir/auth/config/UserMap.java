package pl.pd.emir.auth.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;

public class UserMap implements Serializable {

    @XmlAttribute(name = "ldapAttr")
    private String ldapAttr;
    @XmlAttribute(name = "appAttr")
    private String appAttr;

    public UserMap() {
    }

    public UserMap(String ldapAttr, String appAttr) {
        this.ldapAttr = ldapAttr;
        this.appAttr = appAttr;
    }

    public String getAppAttr() {
        return appAttr;
    }

    public String getLdapAttr() {
        return ldapAttr;
    }
}
