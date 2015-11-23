package pl.pd.emir.exceptions;

public class FileUtilsException extends RuntimeException {

    public FileUtilsException() {
        super();
    }

    public FileUtilsException(String s) {
        super(s);
    }

    public FileUtilsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileUtilsException(Throwable throwable) {
        super(throwable);
    }
}
