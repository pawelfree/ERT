package pl.pd.emir.kdpw.api.exception;

public class CsvParseException extends Exception {

    public CsvParseException() {
    }

    public CsvParseException(String message) {
        super(message);
    }

    public CsvParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvParseException(Throwable cause) {
        super(cause);
    }

}
