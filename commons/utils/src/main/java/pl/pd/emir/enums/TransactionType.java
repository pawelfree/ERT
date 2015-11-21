package pl.pd.emir.enums;

public enum TransactionType {

    P,
    A,
    ERR;

    public static TransactionType fromString(String value) {
        for (TransactionType status : TransactionType.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
