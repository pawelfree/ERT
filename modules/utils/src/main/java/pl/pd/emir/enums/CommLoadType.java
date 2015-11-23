package pl.pd.emir.enums;

public enum CommLoadType {

    BL, PL, OP, BH, OT,
    ERR;

    public static CommLoadType fromString(String value) {
        for (CommLoadType currCode : CommLoadType.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }
}
