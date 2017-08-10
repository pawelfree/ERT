package pl.pd.emir.commons;

public final class Constants {

    private Constants() {
        super();
    }

    public static final String XML_STATUS_OK = "PACK";

    public static final String XML_STATUS_ERROR = "ERRO";
    
    public static final String TR_C = "TR_C";

    public static final String ISO_DATE = "yyyy-MM-dd";

    public static final String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    
    public static final String ISO_DATE_TIME_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    public static final String CLIENT_REPORTED_YES = "Y";

    public static final String CLIENT_REPORTED_NO = "N";

    public static boolean isStatusOk(final String value) {
        return XML_STATUS_OK.equalsIgnoreCase(value);
    }
}
