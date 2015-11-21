package pl.pd.emir.enums;

public enum OptionType {

    P,
    C,
    ERR;

    public static OptionType fromString(String value) {
        for (OptionType idType : OptionType.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }
}
