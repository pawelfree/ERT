package pl.pd.emir.enums;

public enum ConfirmationType {

    Y,
    N,
    E,
    ERR;
    //TODO
    //Wartości do uzupełnienia!!

    public static ConfirmationType fromString(String value) {
        for (ConfirmationType currCode : ConfirmationType.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }
}
