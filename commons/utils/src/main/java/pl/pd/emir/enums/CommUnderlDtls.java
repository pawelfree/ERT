package pl.pd.emir.enums;

public enum CommUnderlDtls {

    AG, GO, DA, LI, FO, SO, OI, NG, CO, EL, IE, PR, NP, WE, EM,
    ERR;

    public static CommUnderlDtls fromString(String value) {
        for (CommUnderlDtls currCode : CommUnderlDtls.values()) {
            if (currCode.toString().equals(value)) {
                return currCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name();
    }

}
