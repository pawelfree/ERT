package pl.pd.emir.enums;

public enum ValuationType {

    M, O, ERR;

    public static ValuationType fromString(String value) {
        for (ValuationType type : ValuationType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
