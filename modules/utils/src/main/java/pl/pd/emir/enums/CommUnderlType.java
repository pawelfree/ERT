package pl.pd.emir.enums;

public enum CommUnderlType {

    AG,
    EN,
    FR,
    ME,
    IN,
    EV,
    EX,
    ERR;

    public static CommUnderlType fromString(String value) {
        for (CommUnderlType currCode : CommUnderlType.values()) {
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
