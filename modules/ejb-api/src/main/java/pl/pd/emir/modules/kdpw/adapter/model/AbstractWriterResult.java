package pl.pd.emir.modules.kdpw.adapter.model;

import java.io.Serializable;

public abstract class AbstractWriterResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;

    public AbstractWriterResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
