package pl.pd.emir.auth.config;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class UserServiceConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dataOdczytu;
    private List<AuthConnector> connector = new LinkedList<>();
    private AuthProvider authProvider;
    private LdapConfig ldapConfig;

    public UserServiceConfiguration() {
        dataOdczytu = new Date();
    }

    public List<AuthConnector> getConnector() {
        return connector;
    }

    
    public LdapConfig getLdapConfig() {
        return ldapConfig;
    }

    public void setLdapConfig(LdapConfig ldapConfig) {
        this.ldapConfig = ldapConfig;
    }

    @XmlTransient
    public Date getDataOdczytu() {
        return dataOdczytu;
    }

    public void setConnector(List<AuthConnector> connector) {
        this.connector = connector;
    }

    public void setDataOdczytu(Date dataOdczytu) {
        this.dataOdczytu = dataOdczytu;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @XmlTransient
    public AuthConnector getFirstConnector() {
        if (connector != null && connector.size() > 0) {
            return connector.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "UserServiceConfiguration{" + "dataOdczytu=" + dataOdczytu + ", connector=" + connector + '}';
    }
}
