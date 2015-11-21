package pl.pd.emir.exceptions;

public class EMIRException extends Exception {

    public EMIRException() {
        super();
    }

    public EMIRException(String message) {
        super(message);
    }

    public EMIRException(Throwable cause) {
        super(cause);
    }

    public EMIRException(String message, Throwable cause) {
        super(message, cause);
    }
}
