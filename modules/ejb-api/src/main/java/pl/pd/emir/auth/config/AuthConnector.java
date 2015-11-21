package pl.pd.emir.auth.config;

import java.io.Serializable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pd.emir.auth.IConnector;

public class AuthConnector implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthConnector.class);

    public AuthConnector() {
    }
    @XmlAttribute
    private String jndiPath;
    @XmlAttribute
    private String authenticationMode;

    public AuthConnector(String jndiPath) {
        this.jndiPath = jndiPath;
    }

    @XmlTransient
    public IConnector getConnector() {
        return locateConnector(jndiPath);
    }

    public String getJndiPath() {
        return jndiPath;
    }

    public String getAuthenticationMode() {
        return authenticationMode;
    }

    private IConnector locateConnector(String jndiName) {
        IConnector con = null;
        try {
            LOGGER.info("jndiName: " + jndiName);
            InitialContext ctx = new InitialContext();
            con = (IConnector) ctx.lookup(jndiName);
        } catch (NamingException ne) {
            LOGGER.error("Blad podczas wyszukiwania ejb", ne);
        }
        return con;
    }

    @Override
    public String toString() {
        return "AuthConnector{" + "jndiPath=" + jndiPath + '}';
    }
}
