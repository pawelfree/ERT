package pl.pd.emir.kdpw.api.exception;

public class KdpwServiceException extends RuntimeException {

    public KdpwServiceException() {
    }

    public KdpwServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return String.format("Błąd komunikacji z KDPW: %s", super.getMessage());
    }

}
