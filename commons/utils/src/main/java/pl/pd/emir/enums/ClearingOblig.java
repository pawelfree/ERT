package pl.pd.emir.enums;

public enum ClearingOblig {

    Y,
    N,
    ERR;

    public static ClearingOblig fromString(String value) {
        for (ClearingOblig currCode : ClearingOblig.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }
}
