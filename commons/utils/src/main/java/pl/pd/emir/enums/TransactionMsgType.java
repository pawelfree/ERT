package pl.pd.emir.enums;

public enum TransactionMsgType {

    /**
     * Rejestracja transakcji.
     */
    N("N"),
    /**
     * Rejestracja transakcji - BEZ WALUACJI.
     */
    NNV("N"),
    /**
     * Rozwiązanie kontraktu.
     */
    C("C"),
    /**
     * Aktualizacja wyceny kontraktu.
     */
    V("V"),
    /**
     * Aktualizacja wyceny kontraktu + raportowany.
     */
    VR("V"),
    /**
     * Zmiana szczegółów informacji.
     */
    M("M"),
    /**
     * Zmiana szczegółów informacji + modyfikacja danych kontrahenta.
     */
    MC("M"),
    /**
     * Inne zmiany.
     */
    O("O"),
    /**
     * Kompresja kontraktu.
     */
    Z("Z"),
    /**
     * Anulowanie zgłoszenia.
     */
    E("E");

    private final String value;

    private TransactionMsgType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isNew() {
        return N.equals(this) || NNV.equals(this);
    }

    public boolean isModification() {
        return M.equals(this) || MC.equals(this);
    }

    public boolean isOther() {
        return O.equals(this);
    }

    public boolean isValuation() {
        return V.equals(this) || VR.equals(this);
    }

    public boolean isCancelation() {
        return E.equals(this);
    }

    public boolean isCompleted() {
        return C.equals(this);
    }

    public boolean isZipped() {
        return Z.equals(this);
    }

    public boolean hasBankData() {
        return isNew() || isModification() || isValuation() || O.equals(this);
    }
}
