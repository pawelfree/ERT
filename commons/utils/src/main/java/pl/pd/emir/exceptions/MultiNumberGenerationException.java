package pl.pd.emir.exceptions;

public class MultiNumberGenerationException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Cannot generate new value";
    }
}
