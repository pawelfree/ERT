package pl.pd.emir.enums;

public enum CommercialActity {

    Y,
    N,
    ERR;

    public static CommercialActity fromString(String value) {
        for (CommercialActity status : CommercialActity.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
