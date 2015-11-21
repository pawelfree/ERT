package pl.pd.emir.kdpw.api.to;

import java.io.InputStream;
import java.io.Serializable;

public class InputFileItem implements Serializable {

    private static final long serialVersionUID = 2012244120757941399L;

    private final InputStream stream;

    private final String fileName;

    public InputFileItem(InputStream stream, String fileName) {
        this.stream = stream;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public InputStream getStream() {
        return stream;
    }
}
