package pl.pd.emir.enums;

public enum Cleared {

    Y,
    N,
    ERR;

    public static Cleared fromString(String value) {
        for (Cleared currCode : Cleared.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }
}
