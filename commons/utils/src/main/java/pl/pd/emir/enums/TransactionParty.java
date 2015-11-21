package pl.pd.emir.enums;

public enum TransactionParty {

    B,
    S,
    ERR,;

    public static TransactionParty fromString(String value) {
        for (TransactionParty status : TransactionParty.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }
        return null;
    }

    public TransactionParty opposite() {
        if (B.equals(this)) {
            return S;
        }
        if (S.equals(this)) {
            return B;
        }
        return ERR;
    }

}
