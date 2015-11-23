package pl.pd.emir.enums;

public enum OptionExecStyle {

    A,
    B,
    E,
    S,
    ERR;
    //TODO
    //Wartości do uzupełnienia!!!!

    public static OptionExecStyle fromString(String value) {
        for (OptionExecStyle idType : OptionExecStyle.values()) {
            if (idType.toString().equals(value)) {
                return idType;
            }
        }
        return null;
    }
}
