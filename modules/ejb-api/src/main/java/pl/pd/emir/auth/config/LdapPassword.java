package pl.pd.emir.auth.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LdapPassword implements Serializable {

    @XmlAttribute
    String password;
    @XmlAttribute
    String type;

    public enum Type {

        PLAIN, ENCRYPTED
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
