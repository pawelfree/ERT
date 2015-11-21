package pl.pd.emir.auth.ldap;

public final class LdapErrorCodes {

    public static final LdapErrorCodes ERROR_SUCCESS = new LdapErrorCodes(0);
    public static final LdapErrorCodes ERROR_ACCOUNT_NOT_FOUND = new LdapErrorCodes(1111);
    public static final LdapErrorCodes ERROR_LOGON_FAILURE = new LdapErrorCodes(1326); // 52e
    public static final LdapErrorCodes ERROR_ACCOUNT_DISABLED = new LdapErrorCodes(1331); // 533
    public static final LdapErrorCodes ERROR_ACCOUNT_LOCKED_OUT = new LdapErrorCodes(1909); // 775
    public static final LdapErrorCodes ERROR_INVALID_WORKSTATION = new LdapErrorCodes(1329); // 531
    public static final LdapErrorCodes ERROR_INVALID_DOMAIN = new LdapErrorCodes(11122);
    public static final LdapErrorCodes ERROR_ACCOUNT_EXPIRED = new LdapErrorCodes(1793);
    public static final LdapErrorCodes ERROR_NO_SUCH_USER = new LdapErrorCodes(1317);
    public static final LdapErrorCodes ERROR_UNKNOWN = new LdapErrorCodes(-1);
    private final int value;

    private LdapErrorCodes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getHexValue() {
        return Integer.toHexString(value);
    }

    public static LdapErrorCodes getByValue(int value) {
        switch (value) {
            case 0:
                return ERROR_SUCCESS;
            case 1326:
                return ERROR_LOGON_FAILURE;
            case 1331:
                return ERROR_ACCOUNT_DISABLED;
            case 1909:
                return ERROR_ACCOUNT_LOCKED_OUT;
            case 1329:
                return ERROR_INVALID_WORKSTATION;
            case 1111:
                return ERROR_ACCOUNT_NOT_FOUND;
            case 11122:
                return ERROR_INVALID_DOMAIN;
            case 1793:
                return ERROR_ACCOUNT_EXPIRED;
            case 1317:
                return ERROR_NO_SUCH_USER;
            default:
                return new LdapErrorCodes(value);
        }
    }

    public static LdapErrorCodes getByHexValue(String hexValue) {
        return getByValue(Integer.parseInt(hexValue, 16));
    }

    @Override
    public String toString() {
        //return Integer.toString(this.getValue());
        switch (this.value) {
            //case 0:
            //    return "ERROR_SUCCESS";
            case 1326:
                return "ERROR_LOGON_FAILURE";
            case 1331:
                return "ERROR_ACCOUNT_DISABLED";
            case 1909:
                return "ERROR_ACCOUNT_LOCKED_OUT";
            case 1329:
                return "ERROR_INVALID_WORKSTATION";
            case 1111:
                return "ERROR_ACCOUNT_NOT_FOUND";
            case 11122:
                return "ERROR_INVALID_DOMAIN";
            case 1793:
                return "ERROR_ACCOUNT_EXPIRED";
            case 1317:
                return "ERROR_NO_SUCH_USER";
            default:
                return "ERROR_UNKNOWN";
        }
    }
}
