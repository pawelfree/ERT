package pl.pd.emir.auth.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pl.pd.emir.auth.enums.RoleMapper;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LdapConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<GroupMap> groupMap = new ArrayList<>();
    private List<UserMap> userMap = new ArrayList<>();
    /**
     * Nazwa abrybutu grupy w LDAP, określającego uczestników grupy.
     */
    private String groupMember;
    /**
     * url do serwera ldap ldap://pl.nazwa.com:387
     */
    private String providerUrl;
    /**
     * nazwa domeny np. pl.nazwa.com
     */
    private String domain;
    /**
     * Lokalizacja grup w drzewie LDAP np. cn=app,cn=groups,o=odzial,c=pl
     */
    private String groups;
    /**
     * Okresla czy dla polaczenia uzywany jest SSL. Domyslnie - false, ssl wylaczony
     */
    private boolean useSSL;
    /*
     * Okresla mechznizm autentykacji, np. simple, DIGEST-MD5
     */
    private String authType;
    /**
     * okresla czy role za mapowane z bazy danych czy z LDAP domyslnie role pobierane z DB
     */
    private final RoleMapper roleMapper = RoleMapper.DB;
    /**
     * Nazwa uzytkwonika wykorzystywanego do laczenia z LDAP
     */
    private String ldapUserName;
    /**
     * Haslo uzytkownika wykorzystanego do laczenia z LDAP
     */
    private LdapPassword ldapUserPassword;

    public List<GroupMap> getGroupMap() {
        return groupMap;
    }

    public void setGroupMap(List<GroupMap> groupMap) {
        this.groupMap = groupMap;
    }

    public List<UserMap> getUserMap() {
        return userMap;
    }

    public void setUserMap(List<UserMap> userMap) {
        this.userMap = userMap;
    }

    public String getDomain() {
        return domain;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public String getLdapUserName() {
        return ldapUserName;
    }

    public LdapPassword getLdapUserPassword() {
        return ldapUserPassword;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public String getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

}
