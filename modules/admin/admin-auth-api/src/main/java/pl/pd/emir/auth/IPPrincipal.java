package pl.pd.emir.auth;

import java.io.Serializable;
import java.security.Principal;

public class IPPrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 1L;
    String ipAddress;

    public IPPrincipal() {
    }

    public IPPrincipal(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String getName() {
        return "IPPrincipal";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
