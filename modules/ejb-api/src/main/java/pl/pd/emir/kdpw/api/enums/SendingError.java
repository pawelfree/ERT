package pl.pd.emir.kdpw.api.enums;

public enum SendingError {

    /**
     * Istnieje transakcja o typie: NOWA w statusie WYSŁANA lub PRZYJETA.
     */
    EXISTS_DATA_NEW_SENT_OR_CONFIRMED("transaction.kdpw.send.dataNew.sent.confirmed"),
    /**
     * Istnieje transakcja o typie: ZAKONCZONA w statusie WYSŁANA lub PRZYJETA.
     */
    EXISTS_DATA_COMPLETED_SENT_OR_CONFIRMED("transaction.kdpw.send.dataCompleted.sent.confirmed"),
    /**
     * Instnieje innna transakcja niewysłana do KDPW.
     */
    EXISTS_OTHER_UNSENT("transaction.kdpw.send.dataOngoing.new"),
    /**
     * Dla TRWAJACEJ nie znaleziono poprzedzajacej przyjetej przez KDPW.
     */
    PREVIOUS_KDPW_VERSION_NOT_FOUND("transaction.kdpw.send.dataOngoing.confirmedNotFound"),
    /**
     * Dla TRWAJACEJ : brak zmian;
     */
    ONGOING_NO_CHANGES("transaction.kdpw.send.dataOngoing.noChanges"),
    /**
     * Dla ANULOWANEJ istnieje inna anulowana ze statusem WYSŁANA lub PRZYJĘTA.
     */
    CANCELLED_P_SENT_CONFIRMED("transaction.kdpw.send.dataCancelled.sent.confirmed"),
    /**
     * Nieokreslony typ.
     */
    NO_TYPE("transaction.kdpw.send.noTypeDefined"),
    /**
     * W rejestrze jest inna wersja transakcji przekazana do KDPW.
     */
    OTHER_VERSION_SENT("transaction.kdpw.send.otherVersionInKdpw"),
    /**
     * Brak uzupełnionych informacji o wycenie lub zabezpieczeniach.
     */
    EMPTY_VALUATION_OR_PROTECTION_INFO("transaction.kdpw.send.noValuationProtectionInfo"),
    /**
     * Niekompletne dane klienta.
     */
    NO_CLIENT("transaction.kdpw.send.incompleteClientData"),
    /**
     * Transakcja (Zakonczona) przetwarzana przez KDPW.
     */
    KDPW_PROCESSED("transaction.kdpw.send.kdpwProcessed"),;

    private final String msg;

    private SendingError(final String value) {
        this.msg = value;
    }

    public final String getMsg() {
        return msg;
    }
}
