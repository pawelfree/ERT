package pl.pd.emir.entity.kdpw;

public enum FileStatus {

    /**
     * WYGENEROWANY - dotyczy pliku wysyłanego do KDPW.
     */
    G_GENERATED("kdpw.file.status.generated"),
    /**
     * PRZYJĘTY - dotyczy pliku, w którym wszystkie komunikaty są w statusie: PRZYJETY.
     */
    F_ACCEPTED("kdpw.file.status.accepted"),
    /**
     * ODRZUCONY - dotyczy pliku, w którym nie ma komunikatów ze statusem PRZYJETY i przynajmniej jedne komunikat ma
     * status ODRZUCONY ( mogą być komunikaty niepowiązane).
     */
    E_REJECTED("kdpw.file.status.rejected"),
    /**
     * NIEPOWIĄZANY - dotyczy pliku, w którym wszystkie komunikaty mają status NIEPOWIĄZANY.
     */
    D_DISCONNECTED("kdpw.file.status.disconnected"),
    /**
     * BŁĄD ODPOWIEDZI - plik odpowiedzi z KDPW jest nipoprawny (zawiera błędy składniowe).
     */
    A_INVALID_RESPONSE("kdpw.file.status.invalidResponse"),
    /**
     * BŁĄD WYSŁANEGO - plik odpowiedzi z KDPW wskazuje na błędy składniowe w pliku wysłanym do KDPW.
     */
    B_INVALID_REQUEST("kdpw.file.status.invalidRequest"),
    /**
     * WYSŁANY - plik wysłany do repozytorium
     */
    R_SENT("repository.file.status.sent");

    private final String msgKey;

    private FileStatus(final String value) {
        this.msgKey = value;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
