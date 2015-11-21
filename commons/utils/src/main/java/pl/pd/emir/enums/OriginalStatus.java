package pl.pd.emir.enums;

public enum OriginalStatus {

    N, //rejestracja transakcji
    C, //rozwiazanie kontraktu
    M, //transakcja trwajaca
    E, //transakcja anulowana - TYLKO w banku i
    ERR;

    public static OriginalStatus fromString(String value) {
        for (OriginalStatus status : OriginalStatus.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
