package pl.pd.emir.enums;

public enum DeliverType {

    C,
    P,
    O,
    ERR;

    public static DeliverType fromString(String value) {
        for (DeliverType idType : DeliverType.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }
}
