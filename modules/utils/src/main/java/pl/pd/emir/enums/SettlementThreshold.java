package pl.pd.emir.enums;

public enum SettlementThreshold {

    Y,
    N,
    ERR;

    public static SettlementThreshold fromString(String value) {
        for (SettlementThreshold enumItem : SettlementThreshold.values()) {
            if (enumItem.toString().equals(value)) {
                return enumItem;
            }
        }
        return null;
    }
}
