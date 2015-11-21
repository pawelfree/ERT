package pl.pd.emir.auth.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;

public class GroupMap implements Serializable {

    @XmlAttribute
    private String ldapGroup;
    @XmlAttribute
    private String appGroup;

    public GroupMap() {
    }

    public GroupMap(String ldapGroup, String appGroup) {
        this.ldapGroup = ldapGroup;
        this.appGroup = appGroup;
    }

    public String getAppGroup() {
        return appGroup;
    }

    public String getLdapGroup() {
        return ldapGroup;
    }
}
