package pl.pd.emir.kdpw.api.exception;

public class RepositoryServiceException extends RuntimeException {

    public RepositoryServiceException() {
    }

    public RepositoryServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return String.format("Błąd usługi komunikacji z repozytorium: %s", super.getMessage());
    }

}
